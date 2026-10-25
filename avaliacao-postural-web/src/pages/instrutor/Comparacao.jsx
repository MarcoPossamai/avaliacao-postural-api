import { useEffect, useState } from "react";
import { listarAlunos } from "../../services/alunoService";
import {
  listarAvaliacoesPorAluno,
  compararAvaliacoes,
} from "../../services/avaliacaoService";
import { buscarArquivoFotografia } from "../../services/fotografiaService";
import { formatarData } from "../../lib/formatadores";
import ModalEscolherFotos from "../../components/ModalEscolherFotos";
import ModalCompararFotos from "../../components/ModalCompararFotos";
import "./Avaliacoes.css";
import "./NovaFicha.css";
import "./Relatorios.css";
import { useLocation } from "react-router-dom";
import usePaginacao from "../../hooks/usePaginacao";
import Paginacao from "../../components/Paginacao";

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

const SITUACAO_LABELS = {
  APARECEU: "Novo",
  DESAPARECEU: "Corrigido",
  MANTIDO: "Mantido",
  AGRAVOU: "Agravou",
  MELHOROU: "Melhorou",
};

function labelRegiao(codigo) {
  return REGIOES.find((r) => r.value === codigo)?.label ?? codigo;
}

function labelSeveridade(codigo) {
  return SEVERIDADES.find((s) => s.value === codigo)?.label ?? codigo;
}

function formatarNumero(valor) {
  return valor == null ? "-" : Number(valor).toFixed(1);
}

export default function Comparacao() {
  const [alunos, setAlunos] = useState([]);
  const [buscaAluno, setBuscaAluno] = useState("");
  const [alunoSelecionado, setAlunoSelecionado] = useState(null);
  const [avaliacoesDoAluno, setAvaliacoesDoAluno] = useState([]);
  const [avaliacaoInicialId, setAvaliacaoInicialId] = useState("");
  const [avaliacaoFinalId, setAvaliacaoFinalId] = useState("");
  const [comparativo, setComparativo] = useState(null);
  const [fotosIniciais, setFotosIniciais] = useState([]);
  const [fotosFinais, setFotosFinais] = useState([]);
  const [escolhendoFotos, setEscolhendoFotos] = useState(false);
  const [comparandoFotos, setComparandoFotos] = useState(false);
  const [fotoInicialEscolhida, setFotoInicialEscolhida] = useState(null);
  const [fotoFinalEscolhida, setFotoFinalEscolhida] = useState(null);
  const [erro, setErro] = useState("");
  const location = useLocation();

  useEffect(() => {
    listarAlunos()
      .then(setAlunos)
      .catch(() => setErro("Não foi possível carregar os alunos."));
    if (location.state?.aluno) {
      selecionarAluno(location.state.aluno);
    }
  }, []);

  const alunosFiltrados = buscaAluno
    ? alunos.filter((a) =>
        a.nome.toLowerCase().includes(buscaAluno.toLowerCase()),
      )
    : alunos;

  const {
    itensPagina: alunosPagina,
    paginaAtual,
    totalPaginas,
    definirPagina,
  } = usePaginacao(alunosFiltrados, 10);

  async function selecionarAluno(aluno) {
    setAlunoSelecionado(aluno);
    setComparativo(null);
    try {
      const lista = await listarAvaliacoesPorAluno(aluno.id);
      setAvaliacoesDoAluno(lista);
    } catch {
      setAvaliacoesDoAluno([]);
    }
  }

  async function handleComparar() {
    setErro("");
    setComparativo(null);
    try {
      const dados = await compararAvaliacoes(
        avaliacaoInicialId,
        avaliacaoFinalId,
      );
      setComparativo(dados);
      const [blobsIniciais, blobsFinais] = await Promise.all([
        Promise.all(
          dados.avaliacaoInicial.fotografias.map(async (f) => ({
            ...f,
            blobUrl: await buscarArquivoFotografia(
              dados.avaliacaoInicial.avaliacao.id,
              f.id,
            ),
          })),
        ),
        Promise.all(
          dados.avaliacaoFinal.fotografias.map(async (f) => ({
            ...f,
            blobUrl: await buscarArquivoFotografia(
              dados.avaliacaoFinal.avaliacao.id,
              f.id,
            ),
          })),
        ),
      ]);
      setFotosIniciais(blobsIniciais);
      setFotosFinais(blobsFinais);
    } catch {
      setErro("Não foi possível comparar essas avaliações.");
    }
  }

  return (
    <div className="avaliacoes-page">
      <span className="avaliacoes-eyebrow">Comparação</span>
      <h1>Comparar avaliações</h1>

      {erro && <p className="avaliacoes-erro">{erro}</p>}

      {!alunoSelecionado && (
        <div className="avaliacao-card">
          <h2>Selecione o aluno</h2>
          <input
            type="text"
            placeholder="Buscar aluno por nome..."
            value={buscaAluno}
            onChange={(e) => setBuscaAluno(e.target.value)}
          />
          <div className="avaliacao-lista-alunos avaliacao-lista-alunos-paginada">
            {alunosPagina.map((aluno) => (
              <button
                type="button"
                key={aluno.id}
                className="avaliacao-item-aluno"
                onClick={() => selecionarAluno(aluno)}
              >
                {aluno.nome}
              </button>
            ))}
            {alunosFiltrados.length === 0 && <p>Nenhum aluno encontrado.</p>}
          </div>
          <Paginacao
            paginaAtual={paginaAtual}
            totalPaginas={totalPaginas}
            definirPagina={definirPagina}
          />
        </div>
      )}

      {alunoSelecionado && (
        <>
          <div className="avaliacao-aluno-selecionado">
            <div>
              <strong>{alunoSelecionado.nome}</strong>
              <span>Selecionado</span>
            </div>
            <button
              type="button"
              className="botao-secundario"
              onClick={() => {
                setAlunoSelecionado(null);
                setComparativo(null);
              }}
            >
              Trocar aluno
            </button>
          </div>

          <div className="avaliacao-card">
            <h2>Comparar avaliações</h2>
            <div className="modal-linha">
              <div>
                <label>Avaliação inicial</label>
                <select
                  value={avaliacaoInicialId}
                  onChange={(e) => setAvaliacaoInicialId(e.target.value)}
                >
                  <option value="">Selecione...</option>
                  {avaliacoesDoAluno.map((a) => (
                    <option key={a.id} value={a.id}>
                      {formatarData(a.dataAvaliacao)}
                    </option>
                  ))}
                </select>
              </div>
              <div>
                <label>Avaliação final</label>
                <select
                  value={avaliacaoFinalId}
                  onChange={(e) => setAvaliacaoFinalId(e.target.value)}
                >
                  <option value="">Selecione...</option>
                  {avaliacoesDoAluno.map((a) => (
                    <option key={a.id} value={a.id}>
                      {formatarData(a.dataAvaliacao)}
                    </option>
                  ))}
                </select>
              </div>
            </div>
            <button
              type="button"
              className="botao-primario comparacao-botao-comparar"
              onClick={handleComparar}
              disabled={!avaliacaoInicialId || !avaliacaoFinalId}
            >
              Comparar
            </button>
          </div>

          {comparativo && (
            <>
              <div className="avaliacao-card">
                <h2>Medidas gerais</h2>
                <div className="comparacao-medidas-lista">
                  {comparativo.variacoesMedidas
                    .filter(
                      (v) =>
                        !v.descricao.startsWith("Circunferência") &&
                        !v.descricao.startsWith("Dobra"),
                    )
                    .map((v, i) => (
                      <div key={i} className="comparacao-medida-linha">
                        <span className="comparacao-medida-nome">
                          {v.descricao}
                        </span>
                        <div className="comparacao-medida-valores">
                          <span>{formatarNumero(v.valorInicial)}</span>
                          <span className="comparacao-seta">→</span>
                          <span>{formatarNumero(v.valorFinal)}</span>
                          {v.diferenca != null && (
                            <span className="comparacao-diferenca">
                              ({v.diferenca > 0 ? "+" : ""}
                              {formatarNumero(v.diferenca)})
                            </span>
                          )}
                        </div>
                      </div>
                    ))}
                </div>
              </div>

              <div className="avaliacao-card">
                <h2>Circunferências</h2>
                <div className="comparacao-medidas-lista">
                  {comparativo.variacoesMedidas
                    .filter((v) => v.descricao.startsWith("Circunferência"))
                    .map((v, i) => (
                      <div key={i} className="comparacao-medida-linha">
                        <span className="comparacao-medida-nome">
                          {v.descricao.replace("Circunferência - ", "")}
                        </span>
                        <div className="comparacao-medida-valores">
                          <span>{formatarNumero(v.valorInicial)}</span>
                          <span className="comparacao-seta">→</span>
                          <span>{formatarNumero(v.valorFinal)}</span>
                          {v.diferenca != null && (
                            <span className="comparacao-diferenca">
                              ({v.diferenca > 0 ? "+" : ""}
                              {formatarNumero(v.diferenca)})
                            </span>
                          )}
                        </div>
                      </div>
                    ))}
                </div>
              </div>

              <div className="avaliacao-card">
                <h2>Dobras cutâneas</h2>
                <div className="comparacao-medidas-lista">
                  {comparativo.variacoesMedidas
                    .filter((v) => v.descricao.startsWith("Dobra"))
                    .map((v, i) => (
                      <div key={i} className="comparacao-medida-linha">
                        <span className="comparacao-medida-nome">
                          {v.descricao.replace("Dobra - ", "")}
                        </span>
                        <div className="comparacao-medida-valores">
                          <span>{formatarNumero(v.valorInicial)}</span>
                          <span className="comparacao-seta">→</span>
                          <span>{formatarNumero(v.valorFinal)}</span>
                          {v.diferenca != null && (
                            <span className="comparacao-diferenca">
                              ({v.diferenca > 0 ? "+" : ""}
                              {formatarNumero(v.diferenca)})
                            </span>
                          )}
                        </div>
                      </div>
                    ))}
                </div>
              </div>

              <div className="avaliacao-card">
                <h2>Desvios</h2>
                {comparativo.variacoesDesvios.length === 0 && (
                  <p>Nenhum desvio registrado.</p>
                )}
                <div className="ficha-desvios-lista">
                  {comparativo.variacoesDesvios.map((v, i) => (
                    <span
                      key={i}
                      className={`comparacao-desvio-tag comparacao-desvio-${v.situacao.toLowerCase()}`}
                    >
                      {labelRegiao(v.regiao)} — {v.tipo}:{" "}
                      {SITUACAO_LABELS[v.situacao]}
                    </span>
                  ))}
                </div>
              </div>

              <div className="avaliacao-card">
                <h2>Fotos</h2>
                {fotosIniciais.length > 0 && fotosFinais.length > 0 ? (
                  <button
                    type="button"
                    className="botao-primario comparacao-botao-simetrografo"
                    onClick={() => setEscolhendoFotos(true)}
                  >
                    Comparar com simetrógrafo
                  </button>
                ) : (
                  <p>
                    Pelo menos uma das avaliações não possui fotos registradas.
                  </p>
                )}
              </div>
            </>
          )}
        </>
      )}

      {escolhendoFotos && comparativo && (
        <ModalEscolherFotos
          fotosIniciais={fotosIniciais}
          fotosFinais={fotosFinais}
          legendaInicial={`Antes (${formatarData(comparativo.avaliacaoInicial.avaliacao.dataAvaliacao)})`}
          legendaFinal={`Depois (${formatarData(comparativo.avaliacaoFinal.avaliacao.dataAvaliacao)})`}
          onFechar={() => setEscolhendoFotos(false)}
          onConfirmar={(fotoInicial, fotoFinal) => {
            setFotoInicialEscolhida(fotoInicial);
            setFotoFinalEscolhida(fotoFinal);
            setEscolhendoFotos(false);
            setComparandoFotos(true);
          }}
        />
      )}

      {comparandoFotos && comparativo && (
        <ModalCompararFotos
          fotoInicial={fotoInicialEscolhida}
          fotoFinal={fotoFinalEscolhida}
          legendaInicial={`Antes (${formatarData(comparativo.avaliacaoInicial.avaliacao.dataAvaliacao)})`}
          legendaFinal={`Depois (${formatarData(comparativo.avaliacaoFinal.avaliacao.dataAvaliacao)})`}
          onFechar={() => setComparandoFotos(false)}
        />
      )}
    </div>
  );
}
