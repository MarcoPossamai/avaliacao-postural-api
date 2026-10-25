import { useEffect, useState } from "react";
import { useParams, useLocation, useNavigate } from "react-router-dom";
import {
  listarFotografias,
  enviarFotografia,
  removerFotografia,
  buscarArquivoFotografia,
} from "../../services/fotografiaService";
import {
  listarDesvios,
  registrarDesvios,
  removerDesvio,
} from "../../services/desvioService";
import ModalSucesso from "../../components/ModalSucesso";
import ModalFotoSimetrografo from "../../components/ModalFotoSimetrografo";
import ModalConfirmacao from "../../components/ModalConfirmacao";
import { formatarData } from "../../lib/formatadores";
import "./Avaliacoes.css";
import "./AvaliacaoDetalhe.css";
import {
  obterDetalheAvaliacao,
  removerAvaliacao,
} from "../../services/avaliacaoService";

const REGIOES = [
  { value: "PES", label: "Pés" },
  { value: "TIBIA", label: "Tíbia" },
  { value: "JOELHO", label: "Joelho" },
  { value: "QUADRIL_PELVE", label: "Quadril/Pelve" },
  { value: "ESCAPULA", label: "Escápula" },
  { value: "OMBRO", label: "Ombro" },
  { value: "COLUNA", label: "Coluna" },
  { value: "CABECA", label: "Cabeça" },
];

const SEVERIDADES = [
  { value: "LEVE", label: "Leve" },
  { value: "MODERADA", label: "Moderada" },
  { value: "ACENTUADA", label: "Acentuada" },
];

function labelRegiao(codigo) {
  return REGIOES.find((r) => r.value === codigo)?.label ?? codigo;
}

function labelSeveridade(codigo) {
  return SEVERIDADES.find((s) => s.value === codigo)?.label ?? codigo;
}

export default function AvaliacaoDetalhe() {
  const { avaliacaoId } = useParams();
  const location = useLocation();
  const navigate = useNavigate();
  const [fotoSelecionada, setFotoSelecionada] = useState(null);
  const [fotografias, setFotografias] = useState([]);
  const [desviosSalvos, setDesviosSalvos] = useState([]);
  const [novosDesvios, setNovosDesvios] = useState([]);
  const [carregando, setCarregando] = useState(true);
  const [erro, setErro] = useState("");
  const [enviandoFoto, setEnviandoFoto] = useState(false);
  const [salvandoDesvios, setSalvandoDesvios] = useState(false);
  const [mostrarSucesso, setMostrarSucesso] = useState(
    Boolean(location.state?.recemCriada),
  );
  const [medida, setMedida] = useState(null);
  const [avaliacao, setAvaliacao] = useState(null);
  const [confirmandoRemocao, setConfirmandoRemocao] = useState(false);
  const [removendo, setRemovendo] = useState(false);

  useEffect(() => {
    carregar();
  }, [avaliacaoId]);

  async function carregar() {
    setCarregando(true);
    setErro("");
    try {
      const [listaFotos, listaDesvios, detalhe] = await Promise.all([
        listarFotografias(avaliacaoId),
        listarDesvios(avaliacaoId),
        obterDetalheAvaliacao(avaliacaoId),
      ]);
      const fotosComBlob = await Promise.all(
        listaFotos.map(async (foto) => ({
          ...foto,
          blobUrl: await buscarArquivoFotografia(avaliacaoId, foto.id),
        })),
      );
      setFotografias(fotosComBlob);
      setDesviosSalvos(listaDesvios);
      setMedida(detalhe.medidaCorporal);
      setAvaliacao(detalhe.avaliacao);
    } catch {
      setErro("Não foi possível carregar a avaliação.");
    } finally {
      setCarregando(false);
    }
  }

  async function handleUploadFoto(event) {
    const arquivo = event.target.files[0];
    if (!arquivo) return;
    setErro("");
    setEnviandoFoto(true);
    try {
      const fotografia = await enviarFotografia(avaliacaoId, arquivo);
      const blobUrl = await buscarArquivoFotografia(avaliacaoId, fotografia.id);
      setFotografias((atuais) => [...atuais, { ...fotografia, blobUrl }]);
    } catch {
      setErro("Não foi possível enviar a foto.");
    } finally {
      setEnviandoFoto(false);
      event.target.value = "";
    }
  }

  async function handleRemoverFoto(foto) {
    try {
      await removerFotografia(avaliacaoId, foto.id);
      URL.revokeObjectURL(foto.blobUrl);
      setFotografias((atuais) => atuais.filter((f) => f.id !== foto.id));
    } catch {
      setErro("Não foi possível remover a foto.");
    }
  }

  function adicionarLinhaDesvio() {
    setNovosDesvios([
      ...novosDesvios,
      { regiao: "PES", tipo: "", severidade: "LEVE" },
    ]);
  }

  function atualizarLinhaDesvio(index, campo, valor) {
    const copia = [...novosDesvios];
    copia[index] = { ...copia[index], [campo]: valor };
    setNovosDesvios(copia);
  }

  function removerLinhaDesvio(index) {
    setNovosDesvios(novosDesvios.filter((_, i) => i !== index));
  }

  async function handleSalvarNovosDesvios() {
    const preenchidos = novosDesvios.filter((d) => d.tipo.trim() !== "");
    if (preenchidos.length === 0) return;
    setErro("");
    setSalvandoDesvios(true);
    try {
      const criados = await registrarDesvios(avaliacaoId, preenchidos);
      setDesviosSalvos((atuais) => [...atuais, ...criados]);
      setNovosDesvios([]);
    } catch {
      setErro("Não foi possível salvar os desvios.");
    } finally {
      setSalvandoDesvios(false);
    }
  }

  async function handleRemoverDesvioSalvo(desvioId) {
    try {
      await removerDesvio(avaliacaoId, desvioId);
      setDesviosSalvos((atuais) => atuais.filter((d) => d.id !== desvioId));
    } catch {
      setErro("Não foi possível remover o desvio.");
    }
  }

  async function confirmarRemocaoAvaliacao() {
    setRemovendo(true);
    try {
      await removerAvaliacao(avaliacaoId);
      navigate(`/alunos/${avaliacao.alunoId}`);
    } catch {
      setErro("Não foi possível remover a avaliação.");
      setConfirmandoRemocao(false);
    } finally {
      setRemovendo(false);
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
    <div className="avaliacoes-page detalhe-page">
      <span className="avaliacoes-eyebrow">Avaliação</span>
      <h1>
        {location.state?.nomeAluno
          ? `Avaliação de ${location.state.nomeAluno}`
          : "Detalhe da avaliação"}
      </h1>

      {erro && <p className="avaliacoes-erro">{erro}</p>}

      {avaliacao && (
        <div className="avaliacao-card detalhe-cabecalho">
          <div>
            <strong>Data: {formatarData(avaliacao.dataAvaliacao)}</strong>
            {avaliacao.observacoes && <p>{avaliacao.observacoes}</p>}
          </div>
          <div className="detalhe-cabecalho-acoes">
            <button
              type="button"
              className="botao-secundario"
              onClick={() =>
                navigate(`/avaliacoes/${avaliacaoId}/editar`, {
                  state: { nomeAluno: location.state?.nomeAluno },
                })
              }
            >
              Editar avaliação
            </button>

            <button
              type="button"
              className="botao-perigo"
              onClick={() => setConfirmandoRemocao(true)}
            >
              Excluir avaliação
            </button>
          </div>
        </div>
      )}

      <div className="detalhe-colunas">
        <div className="avaliacao-card">
          <h2>Fotografias</h2>
          <label className="avaliacao-upload-botao">
            {enviandoFoto ? "Enviando..." : "+ Adicionar foto"}
            <input
              type="file"
              accept="image/*"
              onChange={handleUploadFoto}
              disabled={enviandoFoto}
              hidden
            />
          </label>

          {fotografias.length > 0 && (
            <p className="detalhe-fotos-dica">
              Clique em uma foto para visualizar o simetrógrafo.
            </p>
          )}

          <div className="detalhe-fotos">
            {fotografias.map((foto) => (
              <div key={foto.id} className="detalhe-foto-item">
                <img
                  src={foto.blobUrl}
                  alt="Foto da avaliação"
                  onClick={() => setFotoSelecionada(foto)}
                  style={{ cursor: "pointer" }}
                />
                <button type="button" onClick={() => handleRemoverFoto(foto)}>
                  Remover
                </button>
              </div>
            ))}
            {fotografias.length === 0 && <p>Nenhuma foto adicionada ainda.</p>}
          </div>
        </div>

        <div className="avaliacao-card">
          <h2>Alterações posturais identificadas</h2>

          {desviosSalvos.length > 0 && (
            <div className="detalhe-desvios-salvos">
              {desviosSalvos.map((desvio) => (
                <div className="detalhe-desvio-salvo" key={desvio.id}>
                  <span>
                    {labelRegiao(desvio.regiao)} — {desvio.tipo} (
                    {labelSeveridade(desvio.severidade)})
                  </span>
                  <button
                    type="button"
                    onClick={() => handleRemoverDesvioSalvo(desvio.id)}
                  >
                    Remover
                  </button>
                </div>
              ))}
            </div>
          )}

          <h3>Adicionar desvios</h3>
          {novosDesvios.length === 0 && (
            <p className="avaliacao-desvio-vazio">
              Nenhum desvio novo adicionado ainda.
            </p>
          )}

          {novosDesvios.map((desvio, index) => (
            <div className="avaliacao-desvio-linha" key={index}>
              <div>
                <label>Região</label>
                <select
                  value={desvio.regiao}
                  onChange={(e) =>
                    atualizarLinhaDesvio(index, "regiao", e.target.value)
                  }
                >
                  {REGIOES.map((r) => (
                    <option key={r.value} value={r.value}>
                      {r.label}
                    </option>
                  ))}
                </select>
              </div>
              <div>
                <label>Tipo</label>
                <input
                  type="text"
                  value={desvio.tipo}
                  onChange={(e) =>
                    atualizarLinhaDesvio(index, "tipo", e.target.value)
                  }
                  placeholder="ex: Elevação do ombro direito"
                />
              </div>
              <div>
                <label>Severidade</label>
                <select
                  value={desvio.severidade}
                  onChange={(e) =>
                    atualizarLinhaDesvio(index, "severidade", e.target.value)
                  }
                >
                  {SEVERIDADES.map((s) => (
                    <option key={s.value} value={s.value}>
                      {s.label}
                    </option>
                  ))}
                </select>
              </div>
              <button
                type="button"
                className="avaliacao-desvio-remover"
                onClick={() => removerLinhaDesvio(index)}
              >
                Remover
              </button>
            </div>
          ))}

          <div className="detalhe-desvios-acoes">
            <button
              type="button"
              className="botao-secundario"
              onClick={adicionarLinhaDesvio}
            >
              + Adicionar linha
            </button>
            <button
              type="button"
              className="botao-primario"
              onClick={handleSalvarNovosDesvios}
              disabled={salvandoDesvios}
            >
              {salvandoDesvios ? "Salvando..." : "Salvar desvios"}
            </button>
          </div>
        </div>
      </div>

      {medida && (
        <div className="avaliacao-card detalhe-medidas-card">
          <h2>Medidas</h2>
          <div className="avaliacao-grid">
            <div>
              <label>Peso</label>
              <span>{medida.peso ?? "-"} kg</span>
            </div>
            <div>
              <label>Altura</label>
              <span>{medida.altura ?? "-"} m</span>
            </div>
            <div>
              <label>IMC</label>
              <span>{medida.imc?.toFixed(1) ?? "-"}</span>
            </div>
            <div>
              <label>% Gordura</label>
              <span>{medida.percentualGordura ?? "-"}%</span>
            </div>
          </div>

          {medida.circunferencias?.length > 0 && (
            <>
              <h3>Circunferências</h3>
              <div className="avaliacao-grid">
                {medida.circunferencias.map((c, i) => (
                  <div key={i}>
                    <label>{c.tipo}</label>
                    <span>{c.valorCm} cm</span>
                  </div>
                ))}
              </div>
            </>
          )}

          {medida.dobraCutaneas?.length > 0 && (
            <>
              <h3>Dobras cutâneas</h3>
              <div className="avaliacao-grid">
                {medida.dobraCutaneas.map((d, i) => (
                  <div key={i}>
                    <label>{d.tipo}</label>
                    <span>{d.valorMm} mm</span>
                  </div>
                ))}
              </div>
            </>
          )}
        </div>
      )}

      {mostrarSucesso && (
        <ModalSucesso
          titulo="Avaliação cadastrada!"
          mensagem="Os dados da avaliação foram salvos com sucesso. Agora você pode anotar os desvios posturais com base nas fotos."
          onFechar={() => setMostrarSucesso(false)}
        />
      )}

      {fotoSelecionada && (
        <ModalFotoSimetrografo
          foto={fotoSelecionada}
          onFechar={() => setFotoSelecionada(null)}
        />
      )}

      {confirmandoRemocao && (
        <ModalConfirmacao
          titulo="Excluir avaliação"
          mensagem="Tem certeza que deseja excluir esta avaliação? Medidas, fotografias e desvios registrados nela também serão removidos. Essa ação não pode ser desfeita."
          confirmando={removendo}
          onConfirmar={confirmarRemocaoAvaliacao}
          onCancelar={() => setConfirmandoRemocao(false)}
        />
      )}
    </div>
  );
}
