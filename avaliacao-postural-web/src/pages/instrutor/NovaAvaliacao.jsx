import { useState, useEffect } from "react";
import { useParams, useLocation, useNavigate } from "react-router-dom";
import {
  criarAvaliacao,
  editarAvaliacao,
  obterDetalheAvaliacao,
} from "../../services/avaliacaoService";
import { registrarMedida, editarMedida } from "../../services/medidaService";
import { enviarFotografia } from "../../services/fotografiaService";
import "./Avaliacoes.css";

function hoje() {
  return new Date().toISOString().slice(0, 10);
}

const CIRCUNFERENCIAS = [
  "Pescoço",
  "Ombros",
  "Tórax",
  "Antebraço direito",
  "Antebraço esquerdo",
  "Bíceps direito relaxado",
  "Bíceps esquerdo relaxado",
  "Bíceps direito contraído",
  "Bíceps esquerdo contraído",
  "Cintura",
  "Abdômen",
  "Quadril",
  "Coxa direita (10 cm)",
  "Coxa esquerda (10 cm)",
  "Coxa direita (22 cm)",
  "Coxa esquerda (22 cm)",
  "Panturrilha direita",
  "Panturrilha esquerda",
];

const DOBRAS = [
  "Tríceps",
  "Bíceps",
  "Subescapular",
  "Peitoral",
  "Axilar média",
  "Suprailíaca",
  "Abdominal",
  "Coxa",
  "Panturrilha",
];

function estadoVazio(lista) {
  return Object.fromEntries(lista.map((nome) => [nome, ""]));
}

export default function NovaAvaliacao() {
  const { alunoId, avaliacaoId } = useParams();
  const modoEdicao = Boolean(avaliacaoId);
  const location = useLocation();
  const navigate = useNavigate();
  const nomeAluno = location.state?.nomeAluno;

  const [etapa, setEtapa] = useState("aluno");
  const [dataAvaliacao, setDataAvaliacao] = useState(hoje());
  const [observacoes, setObservacoes] = useState("");
  const [erro, setErro] = useState("");
  const [salvando, setSalvando] = useState(false);

  const [peso, setPeso] = useState("");
  const [altura, setAltura] = useState("");
  const [percentualGordura, setPercentualGordura] = useState("");
  const [circunferencias, setCircunferencias] = useState(
    estadoVazio(CIRCUNFERENCIAS),
  );
  const [dobras, setDobras] = useState(estadoVazio(DOBRAS));

  const [fotosPendentes, setFotosPendentes] = useState([]);
  const [carregando, setCarregando] = useState(modoEdicao);

  useEffect(() => {
    if (!modoEdicao) return;
    obterDetalheAvaliacao(avaliacaoId)
      .then(({ avaliacao, medidaCorporal }) => {
        setDataAvaliacao(avaliacao.dataAvaliacao);
        setObservacoes(avaliacao.observacoes ?? "");
        if (medidaCorporal) {
          setPeso(String(medidaCorporal.peso ?? ""));
          setAltura(String(medidaCorporal.altura ?? ""));
          setPercentualGordura(
            medidaCorporal.percentualGordura != null
              ? String(medidaCorporal.percentualGordura)
              : "",
          );
          setCircunferencias({
            ...estadoVazio(CIRCUNFERENCIAS),
            ...Object.fromEntries(
              medidaCorporal.circunferencias.map((c) => [
                c.tipo,
                String(c.valorCm),
              ]),
            ),
          });
          setDobras({
            ...estadoVazio(DOBRAS),
            ...Object.fromEntries(
              medidaCorporal.dobraCutaneas.map((d) => [
                d.tipo,
                String(d.valorMm),
              ]),
            ),
          });
        }
      })
      .catch(() => setErro("Não foi possível carregar a avaliação."))
      .finally(() => setCarregando(false));
  }, [avaliacaoId]);

  const imcCalculado =
    peso && altura ? (Number(peso) / Number(altura) ** 2).toFixed(1) : null;

  function handleContinuarAluno(event) {
    event.preventDefault();
    setEtapa("medidas");
  }

  function handleContinuarMedidas(event) {
    event.preventDefault();
    if (modoEdicao) {
      handleSalvarEdicao();
      return;
    }
    setEtapa("fotos");
  }

  function handleVoltarInicio() {
    if (modoEdicao) {
      navigate(`/avaliacoes/${avaliacaoId}`, { state: { nomeAluno } });
      return;
    }
    navigate("/avaliacoes");
  }

  function handleSelecionarFoto(event) {
    const arquivo = event.target.files[0];
    if (!arquivo) return;
    const novaFoto = {
      id: crypto.randomUUID(),
      arquivo,
      previewUrl: URL.createObjectURL(arquivo),
    };
    setFotosPendentes((atuais) => [...atuais, novaFoto]);
    event.target.value = "";
  }

  function removerFotoPendente(id) {
    setFotosPendentes((atuais) => {
      const alvo = atuais.find((f) => f.id === id);
      if (alvo) URL.revokeObjectURL(alvo.previewUrl);
      return atuais.filter((f) => f.id !== id);
    });
  }

  function montarMedida() {
    const circunferenciasPreenchidas = Object.entries(circunferencias)
      .filter(([, valor]) => valor !== "")
      .map(([tipo, valor]) => ({ tipo, valorCm: Number(valor) }));

    const dobrasPreenchidas = Object.entries(dobras)
      .filter(([, valor]) => valor !== "")
      .map(([tipo, valor]) => ({ tipo, valorMm: Number(valor) }));

    return {
      peso: Number(peso),
      altura: Number(altura),
      percentualGordura: percentualGordura ? Number(percentualGordura) : null,
      circunferencias: circunferenciasPreenchidas,
      dobrasCutaneas: dobrasPreenchidas,
    };
  }

  async function handleSalvarEdicao() {
    setErro("");
    setSalvando(true);
    try {
      await editarAvaliacao(avaliacaoId, { dataAvaliacao, observacoes });
      await editarMedida(avaliacaoId, montarMedida());
      navigate(`/avaliacoes/${avaliacaoId}`, { state: { nomeAluno } });
    } catch {
      setErro("Não foi possível salvar as alterações.");
    } finally {
      setSalvando(false);
    }
  }

  async function handleFinalizar() {
    setErro("");
    setSalvando(true);
    try {
      const avaliacao = await criarAvaliacao(alunoId, {
        dataAvaliacao,
        observacoes,
      });

      await registrarMedida(avaliacao.id, montarMedida());

      for (const pendente of fotosPendentes) {
        await enviarFotografia(avaliacao.id, pendente.arquivo);
      }

      navigate(`/avaliacoes/${avaliacao.id}`, {
        state: { recemCriada: true, nomeAluno },
      });
    } catch {
      setErro("Não foi possível salvar a avaliação. Tente novamente.");
    } finally {
      setSalvando(false);
    }
  }

  if (carregando) {
    return (
      <div className="avaliacoes-page">
        <p>Carregando...</p>
      </div>
    );
  }

  return (
    <div className="avaliacoes-page">
      <span className="avaliacoes-eyebrow">
        {modoEdicao ? "Editar avaliação" : "Nova avaliação"}
      </span>
      <h1>Avaliação postural e antropométrica</h1>

      {erro && <p className="avaliacoes-erro">{erro}</p>}

      {etapa === "aluno" && (
        <div className="avaliacao-card">
          <div className="avaliacao-aluno-selecionado">
            <div>
              <strong>{nomeAluno}</strong>
              <span>Selecionado</span>
            </div>
          </div>

          <form onSubmit={handleContinuarAluno}>
            <div>
              <label htmlFor="dataAvaliacao">Data da avaliação</label>
              <input
                id="dataAvaliacao"
                type="date"
                value={dataAvaliacao}
                onChange={(e) => setDataAvaliacao(e.target.value)}
                required
              />
            </div>
            <div>
              <label htmlFor="observacoes">Observações</label>
              <textarea
                id="observacoes"
                value={observacoes}
                onChange={(e) => setObservacoes(e.target.value)}
                rows={3}
                placeholder="Observações gerais (opcional)"
              />
            </div>

            <div className="avaliacao-acoes">
              <button
                type="button"
                className="botao-secundario avaliacao-voltar"
                onClick={handleVoltarInicio}
              >
                Voltar
              </button>
              <button type="submit" className="botao-primario">
                Continuar
              </button>
            </div>
          </form>
        </div>
      )}

      {etapa === "medidas" && (
        <div className="avaliacao-card">
          <h2>Medidas antropométricas</h2>
          <p>Registre os dados coletados nesta avaliação.</p>

          <form onSubmit={handleContinuarMedidas}>
            <div className="avaliacao-grid">
              <div>
                <label htmlFor="peso">Peso (kg)</label>
                <input
                  id="peso"
                  type="number"
                  step="0.1"
                  value={peso}
                  onChange={(e) => setPeso(e.target.value)}
                  onWheel={(e) => e.target.blur()}
                  required
                />
              </div>
              <div>
                <label htmlFor="altura">Altura (m)</label>
                <input
                  id="altura"
                  type="number"
                  step="0.01"
                  value={altura}
                  onChange={(e) => setAltura(e.target.value)}
                  onWheel={(e) => e.target.blur()}
                  placeholder="ex: 1.65"
                  required
                />
              </div>
              <div>
                <label htmlFor="percentualGordura">
                  % de gordura (opcional)
                </label>
                <input
                  id="percentualGordura"
                  type="number"
                  step="0.1"
                  value={percentualGordura}
                  onChange={(e) => setPercentualGordura(e.target.value)}
                  onWheel={(e) => e.target.blur()}
                />
              </div>
            </div>

            {imcCalculado && (
              <div className="avaliacao-indicador">
                IMC calculado: <strong>{imcCalculado}</strong>
              </div>
            )}

            <h3>Circunferências (cm)</h3>
            <div className="avaliacao-grid">
              {CIRCUNFERENCIAS.map((nome) => (
                <div key={nome}>
                  <label htmlFor={`circ-${nome}`}>{nome}</label>
                  <input
                    id={`circ-${nome}`}
                    type="number"
                    step="0.1"
                    min="0.1"
                    value={circunferencias[nome]}
                    onChange={(e) =>
                      setCircunferencias({
                        ...circunferencias,
                        [nome]: e.target.value,
                      })
                    }
                    onWheel={(e) => e.target.blur()}
                    required
                  />
                </div>
              ))}
            </div>

            <h3>Dobras cutâneas (mm)</h3>
            <div className="avaliacao-grid">
              {DOBRAS.map((nome) => (
                <div key={nome}>
                  <label htmlFor={`dobra-${nome}`}>{nome}</label>
                  <input
                    id={`dobra-${nome}`}
                    type="number"
                    step="0.1"
                    min="0.1"
                    value={dobras[nome]}
                    onChange={(e) =>
                      setDobras({ ...dobras, [nome]: e.target.value })
                    }
                    onWheel={(e) => e.target.blur()}
                    required
                  />
                </div>
              ))}
            </div>

            <div className="avaliacao-acoes">
              <button
                type="button"
                className="botao-secundario avaliacao-voltar"
                onClick={() => setEtapa("aluno")}
              >
                Voltar
              </button>
              <button
                type="submit"
                className="botao-primario"
                disabled={salvando}
              >
                {modoEdicao
                  ? salvando
                    ? "Salvando..."
                    : "Salvar alterações"
                  : "Continuar"}
              </button>
            </div>
          </form>
        </div>
      )}

      {etapa === "fotos" && (
        <div className="avaliacao-card">
          <h2>Registro fotográfico</h2>
          <p>
            Adicione as fotos da avaliação (opcional). Elas só são enviadas
            quando você clicar em "Salvar avaliação".
          </p>

          <label className="avaliacao-upload-botao">
            + Adicionar foto
            <input
              type="file"
              accept="image/*"
              onChange={handleSelecionarFoto}
              hidden
            />
          </label>

          <div className="avaliacao-fotos-grid">
            {fotosPendentes.map((foto) => (
              <div key={foto.id} className="avaliacao-foto-item">
                <img src={foto.previewUrl} alt="Pré-visualização da foto" />
                <button
                  type="button"
                  onClick={() => removerFotoPendente(foto.id)}
                >
                  Remover
                </button>
              </div>
            ))}
          </div>

          <div className="avaliacao-acoes">
            <button
              type="button"
              className="botao-secundario avaliacao-voltar"
              onClick={() => setEtapa("medidas")}
            >
              Voltar
            </button>
            <button
              type="button"
              className="botao-primario"
              onClick={handleFinalizar}
              disabled={salvando}
            >
              {salvando ? "Salvando..." : "Salvar avaliação"}
            </button>
          </div>
        </div>
      )}
    </div>
  );
}
