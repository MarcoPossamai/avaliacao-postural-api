import { useEffect, useState } from "react";
import { obterMeuRelatorio } from "../../services/relatorioService";
import { listarMinhasFichas } from "../../services/fichaService";
import { listarExercicios } from "../../services/exercicioService";
import { formatarData } from "../../lib/formatadores";
import "../instrutor/Avaliacoes.css";
import "../instrutor/NovaFicha.css";
import "./Evolucao.css";

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

function hoje() {
  return new Date().toISOString().slice(0, 10);
}

function seisMesesAtras() {
  const d = new Date();
  d.setMonth(d.getMonth() - 6);
  return d.toISOString().slice(0, 10);
}

function construirNomePorItem(fichas, exercicios) {
  const mapa = {};
  fichas.forEach((f) => {
    f.itens.forEach((item) => {
      const exercicio = exercicios.find((e) => e.id === item.exercicioId);
      mapa[item.id] = exercicio?.nome ?? `Exercício #${item.exercicioId}`;
    });
  });
  return mapa;
}

function agruparCargasPorDia(cargas, nomePorItem) {
  const porDia = {};
  cargas.forEach((c) => {
    const dia = c.data.slice(0, 10);
    if (!porDia[dia]) porDia[dia] = {};
    const nome = nomePorItem[c.itemFichaId] ?? `Exercício #${c.itemFichaId}`;
    if (!porDia[dia][nome]) porDia[dia][nome] = [];
    porDia[dia][nome].push({
      carga: c.cargaUtilizada,
      repeticoes: c.repeticoes,
    });
  });
  return Object.entries(porDia)
    .sort((a, b) => b[0].localeCompare(a[0]))
    .map(([dia, exerciciosDoDia]) => ({ dia, exerciciosDoDia }));
}

function GraficoLinha({ titulo, pontos, formatarValor }) {
  const [hoverIndex, setHoverIndex] = useState(null);
  const [mostrarTabela, setMostrarTabela] = useState(false);

  const largura = 600;
  const altura = 220;
  const margem = { topo: 16, baixo: 16, esquerda: 16, direita: 16 };
  const areaLargura = largura - margem.esquerda - margem.direita;
  const areaAltura = altura - margem.topo - margem.baixo;

  let caminho = "";
  let x = () => 0;
  let y = () => 0;

  if (pontos.length > 0) {
    const valores = pontos.map((p) => p.valor);
    const minValor = Math.min(...valores);
    const maxValor = Math.max(...valores);
    const folga = (maxValor - minValor) * 0.2 || 1;
    const yMin = minValor - folga;
    const yMax = maxValor + folga;

    x = (i) =>
      margem.esquerda +
      (pontos.length === 1
        ? areaLargura / 2
        : (i / (pontos.length - 1)) * areaLargura);
    y = (valor) =>
      margem.topo + areaAltura - ((valor - yMin) / (yMax - yMin)) * areaAltura;
    caminho = pontos
      .map((p, i) => `${i === 0 ? "M" : "L"} ${x(i)} ${y(p.valor)}`)
      .join(" ");
  }

  return (
    <div className="avaliacao-card">
      <div className="evolucao-grafico-topo">
        <h2>{titulo}</h2>
        {pontos.length > 0 && (
          <button
            type="button"
            className="botao-secundario"
            onClick={() => setMostrarTabela(!mostrarTabela)}
          >
            {mostrarTabela ? "Ver gráfico" : "Ver tabela"}
          </button>
        )}
      </div>

      {pontos.length === 0 && <p>Sem dados suficientes nesse período.</p>}

      {pontos.length > 0 && !mostrarTabela && (
        <div className="grafico-linha-container">
          <svg
            viewBox={`0 0 ${largura} ${altura}`}
            className="grafico-linha-svg"
          >
            {[0, 0.5, 1].map((fracao) => {
              const yPos = margem.topo + areaAltura * fracao;
              return (
                <line
                  key={fracao}
                  x1={margem.esquerda}
                  x2={largura - margem.direita}
                  y1={yPos}
                  y2={yPos}
                  className="grafico-linha-grade"
                />
              );
            })}

            <path
              d={caminho}
              fill="none"
              stroke="#2563eb"
              strokeWidth="2"
              strokeLinecap="round"
              strokeLinejoin="round"
            />

            {pontos.map((p, i) => (
              <g
                key={i}
                onMouseEnter={() => setHoverIndex(i)}
                onMouseLeave={() => setHoverIndex(null)}
              >
                <circle cx={x(i)} cy={y(p.valor)} r="10" fill="transparent" />
                <circle cx={x(i)} cy={y(p.valor)} r="4" fill="#2563eb" />
              </g>
            ))}

            {hoverIndex !== null && (
              <line
                x1={x(hoverIndex)}
                x2={x(hoverIndex)}
                y1={margem.topo}
                y2={altura - margem.baixo}
                className="grafico-linha-crosshair"
              />
            )}
          </svg>

          {hoverIndex !== null && (
            <div
              className="grafico-linha-tooltip"
              style={{ left: `${(x(hoverIndex) / largura) * 100}%` }}
            >
              <strong>{formatarValor(pontos[hoverIndex].valor)}</strong>
              <span>{formatarData(pontos[hoverIndex].data)}</span>
            </div>
          )}
        </div>
      )}

      {pontos.length > 0 && mostrarTabela && (
        <table className="evolucao-tabela">
          <thead>
            <tr>
              <th>Data</th>
              <th>Valor</th>
            </tr>
          </thead>
          <tbody>
            {pontos.map((p, i) => (
              <tr key={i}>
                <td>{formatarData(p.data)}</td>
                <td>{formatarValor(p.valor)}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}

export default function Evolucao() {
  const [aba, setAba] = useState("peso");
  const [inicio, setInicio] = useState(seisMesesAtras());
  const [fim, setFim] = useState(hoje());
  const [relatorio, setRelatorio] = useState(null);
  const [carregando, setCarregando] = useState(true);
  const [erro, setErro] = useState("");

  const [fichas, setFichas] = useState([]);
  const [fichaSelecionadaId, setFichaSelecionadaId] = useState("");
  const [nomePorItem, setNomePorItem] = useState({});
  const [relatorioTreinos, setRelatorioTreinos] = useState(null);
  const [carregandoTreinos, setCarregandoTreinos] = useState(true);

  useEffect(() => {
    carregar();
    carregarFichasETreinos();
  }, []);

  async function carregar() {
    setCarregando(true);
    setErro("");
    try {
      const dados = await obterMeuRelatorio(inicio, fim);
      setRelatorio(dados);
    } catch {
      setErro("Não foi possível carregar sua evolução.");
    } finally {
      setCarregando(false);
    }
  }

  async function carregarFichasETreinos() {
    setCarregandoTreinos(true);
    try {
      const [fichasList, exerciciosList] = await Promise.all([
        listarMinhasFichas(),
        listarExercicios(),
      ]);
      setFichas(fichasList);
      setNomePorItem(construirNomePorItem(fichasList, exerciciosList));

      const maisRecente = [...fichasList].sort((a, b) =>
        b.dataInicio.localeCompare(a.dataInicio),
      )[0];
      if (maisRecente) {
        setFichaSelecionadaId(maisRecente.id);
        const dados = await obterMeuRelatorio(
          maisRecente.dataInicio,
          maisRecente.dataFim || hoje(),
        );
        setRelatorioTreinos(dados);
      }
    } catch {
      setErro("Não foi possível carregar o histórico de treinos.");
    } finally {
      setCarregandoTreinos(false);
    }
  }

  async function handleSelecionarFicha(fichaIdSelecionado) {
    setFichaSelecionadaId(fichaIdSelecionado);
    const ficha = fichas.find((f) => f.id === Number(fichaIdSelecionado));
    if (!ficha) return;

    setCarregandoTreinos(true);
    setErro("");
    try {
      const dados = await obterMeuRelatorio(
        ficha.dataInicio,
        ficha.dataFim || hoje(),
      );
      setRelatorioTreinos(dados);
    } catch {
      setErro("Não foi possível carregar o histórico de treinos.");
    } finally {
      setCarregandoTreinos(false);
    }
  }

  if (carregando) {
    return (
      <div className="evolucao-page">
        <p>Carregando...</p>
      </div>
    );
  }

  const pontosPeso = (relatorio?.evolucaoMedidas ?? [])
    .filter((p) => p.peso != null)
    .map((p) => ({ data: p.data, valor: p.peso }));

  const pontosImc = (relatorio?.evolucaoMedidas ?? [])
    .filter((p) => p.imc != null)
    .map((p) => ({ data: p.data, valor: p.imc }));

  const pontosGordura = (relatorio?.evolucaoMedidas ?? [])
    .filter((p) => p.percentualGordura != null)
    .map((p) => ({ data: p.data, valor: p.percentualGordura }));

  const tiposCircunferencia = [
    ...new Set((relatorio?.evolucaoCircunferencias ?? []).map((c) => c.tipo)),
  ];
  const tiposDobra = [
    ...new Set((relatorio?.evolucaoDobras ?? []).map((d) => d.tipo)),
  ];

  const fichaSelecionada = fichas.find(
    (f) => f.id === Number(fichaSelecionadaId),
  );
  const idsItensDaFicha = new Set(
    (fichaSelecionada?.itens ?? []).map((item) => item.id),
  );
  const cargasDaFicha = (relatorioTreinos?.evolucaoCargas ?? []).filter((c) =>
    idsItensDaFicha.has(c.itemFichaId),
  );
  const sessoesTreino = agruparCargasPorDia(cargasDaFicha, nomePorItem);

  return (
    <div className="evolucao-page">
      <h1>Minha evolução</h1>

      <div className="evolucao-abas">
        <button
          type="button"
          className={`treino-aba ${aba === "peso" ? "treino-aba-ativa" : ""}`}
          onClick={() => setAba("peso")}
        >
          Peso/Gordura
        </button>
        <button
          type="button"
          className={`treino-aba ${aba === "circunferencias" ? "treino-aba-ativa" : ""}`}
          onClick={() => setAba("circunferencias")}
        >
          Circunferências
        </button>
        <button
          type="button"
          className={`treino-aba ${aba === "dobras" ? "treino-aba-ativa" : ""}`}
          onClick={() => setAba("dobras")}
        >
          Dobras cutâneas
        </button>
        <button
          type="button"
          className={`treino-aba ${aba === "desvios" ? "treino-aba-ativa" : ""}`}
          onClick={() => setAba("desvios")}
        >
          Desvios
        </button>
        <button
          type="button"
          className={`treino-aba ${aba === "treinos" ? "treino-aba-ativa" : ""}`}
          onClick={() => setAba("treinos")}
        >
          Treinos
        </button>
      </div>

      {aba !== "treinos" && (
        <div className="avaliacao-card evolucao-filtro">
          <div>
            <label>De</label>
            <input
              type="date"
              value={inicio}
              onChange={(e) => setInicio(e.target.value)}
            />
          </div>
          <div>
            <label>Até</label>
            <input
              type="date"
              value={fim}
              onChange={(e) => setFim(e.target.value)}
            />
          </div>
          <button type="button" className="botao-primario" onClick={carregar}>
            Atualizar
          </button>
        </div>
      )}

      {aba === "treinos" && (
        <div className="avaliacao-card evolucao-filtro">
          <div>
            <label>Treino</label>
            <select
              value={fichaSelecionadaId}
              onChange={(e) => handleSelecionarFicha(e.target.value)}
              disabled={fichas.length === 0}
            >
              {fichas.length === 0 && (
                <option value="">Nenhuma ficha cadastrada</option>
              )}
              {fichas.map((f) => (
                <option key={f.id} value={f.id}>
                  {f.nome || "Ficha sem nome"} ({formatarData(f.dataInicio)}
                  {f.dataFim
                    ? ` até ${formatarData(f.dataFim)}`
                    : " em andamento"}
                  )
                </option>
              ))}
            </select>
          </div>
        </div>
      )}

      {erro && <p className="perfil-erro">{erro}</p>}

      {relatorio && aba === "peso" && (
        <>
          <GraficoLinha
            titulo="Peso (kg)"
            pontos={pontosPeso}
            formatarValor={(v) => `${v} kg`}
          />
          <GraficoLinha
            titulo="IMC"
            pontos={pontosImc}
            formatarValor={(v) => v.toFixed(1)}
          />
          <GraficoLinha
            titulo="% de gordura"
            pontos={pontosGordura}
            formatarValor={(v) => `${v}%`}
          />
        </>
      )}

      {relatorio && aba === "circunferencias" && (
        <>
          {tiposCircunferencia.length === 0 && (
            <div className="avaliacao-card">
              <p>Nenhuma circunferência registrada nesse período.</p>
            </div>
          )}
          {tiposCircunferencia.map((tipo) => {
            const pontos = relatorio.evolucaoCircunferencias
              .filter((c) => c.tipo === tipo)
              .map((c) => ({ data: c.data, valor: c.valorCm }));
            return (
              <GraficoLinha
                key={tipo}
                titulo={tipo}
                pontos={pontos}
                formatarValor={(v) => `${v} cm`}
              />
            );
          })}
        </>
      )}

      {relatorio && aba === "dobras" && (
        <>
          {tiposDobra.length === 0 && (
            <div className="avaliacao-card">
              <p>Nenhuma dobra cutânea registrada nesse período.</p>
            </div>
          )}
          {tiposDobra.map((tipo) => {
            const pontos = relatorio.evolucaoDobras
              .filter((d) => d.tipo === tipo)
              .map((d) => ({ data: d.data, valor: d.valorMm }));
            return (
              <GraficoLinha
                key={tipo}
                titulo={tipo}
                pontos={pontos}
                formatarValor={(v) => `${v} mm`}
              />
            );
          })}
        </>
      )}

      {relatorio && aba === "desvios" && (
        <div className="avaliacao-card">
          <h2>Desvios ao longo do período</h2>
          {relatorio.evolucaoDesvios.length === 0 && (
            <p>Nenhum desvio registrado nesse período.</p>
          )}
          {relatorio.evolucaoDesvios.length > 0 && (
            <div className="ficha-desvios-lista">
              {relatorio.evolucaoDesvios.map((d, i) => (
                <span key={i} className="ficha-desvio-tag">
                  {formatarData(d.data)} • {labelRegiao(d.regiao)} — {d.tipo} (
                  {labelSeveridade(d.severidade)})
                </span>
              ))}
            </div>
          )}
        </div>
      )}

      {aba === "treinos" && (
        <>
          {carregandoTreinos && (
            <div className="avaliacao-card">
              <p>Carregando...</p>
            </div>
          )}
          {!carregandoTreinos && sessoesTreino.length === 0 && (
            <div className="avaliacao-card">
              <p>Nenhuma série registrada nesse período.</p>
            </div>
          )}
          {!carregandoTreinos &&
            sessoesTreino.map(({ dia, exerciciosDoDia }) => (
              <div key={dia} className="avaliacao-card">
                <h2>{formatarData(dia)}</h2>
                <div className="evolucao-treino-lista">
                  {Object.entries(exerciciosDoDia).map(([nome, series]) => (
                    <div key={nome} className="evolucao-treino-exercicio">
                      <strong>{nome}</strong>
                      <div className="evolucao-treino-series">
                        {series.map((s, i) => (
                          <span key={i} className="evolucao-treino-serie-tag">
                            {s.carga != null ? `${s.carga}kg` : "-"} ×{" "}
                            {s.repeticoes ?? "-"}
                          </span>
                        ))}
                      </div>
                    </div>
                  ))}
                </div>
              </div>
            ))}
        </>
      )}
    </div>
  );
}
