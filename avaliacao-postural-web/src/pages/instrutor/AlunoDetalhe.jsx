import { useEffect, useState } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import { listarAlunos } from '../../services/alunoService'
import { listarAvaliacoesPorAluno } from '../../services/avaliacaoService'
import { listarFichasPorAluno } from '../../services/fichaService'
import { obterRelatorioAluno } from '../../services/relatorioService'
import { calcularIdade, formatarSexo, formatarData } from '../../lib/formatadores'
import './Avaliacoes.css'
import './NovaFicha.css'
import '../alunos/Treino.css'
import './Alunos.css'
import './AlunoDetalhe.css'

function iniciais(nome) {
  const partes = nome.trim().split(' ')
  const primeira = partes[0]?.[0] ?? ''
  const ultima = partes.length > 1 ? partes[partes.length - 1][0] : ''
  return (primeira + ultima).toUpperCase()
}

function hoje() {
  return new Date().toISOString().slice(0, 10)
}

function seisMesesAtras() {
  const d = new Date()
  d.setMonth(d.getMonth() - 6)
  return d.toISOString().slice(0, 10)
}

function fichaVigenteHoje(ficha) {
  const hojeStr = hoje()
  const depoisDoInicio = ficha.dataInicio <= hojeStr
  const antesDoFim = !ficha.dataFim || ficha.dataFim >= hojeStr
  return depoisDoInicio && antesDoFim
}

function GraficoLinha({ titulo, pontos, formatarValor }) {
  const [hoverIndex, setHoverIndex] = useState(null)
  const [mostrarTabela, setMostrarTabela] = useState(false)

  const largura = 600
  const altura = 220
  const margem = { topo: 16, baixo: 16, esquerda: 16, direita: 16 }
  const areaLargura = largura - margem.esquerda - margem.direita
  const areaAltura = altura - margem.topo - margem.baixo

  let caminho = ''
  let x = () => 0
  let y = () => 0

  if (pontos.length > 0) {
    const valores = pontos.map((p) => p.valor)
    const minValor = Math.min(...valores)
    const maxValor = Math.max(...valores)
    const folga = (maxValor - minValor) * 0.2 || 1
    const yMin = minValor - folga
    const yMax = maxValor + folga

    x = (i) => margem.esquerda + (pontos.length === 1 ? areaLargura / 2 : (i / (pontos.length - 1)) * areaLargura)
    y = (valor) => margem.topo + areaAltura - ((valor - yMin) / (yMax - yMin)) * areaAltura
    caminho = pontos.map((p, i) => `${i === 0 ? 'M' : 'L'} ${x(i)} ${y(p.valor)}`).join(' ')
  }

  return (
    <div className="avaliacao-card">
      <div className="evolucao-grafico-topo">
        <h2>{titulo}</h2>
        {pontos.length > 0 && (
          <button type="button" className="botao-secundario" onClick={() => setMostrarTabela(!mostrarTabela)}>
            {mostrarTabela ? 'Ver gráfico' : 'Ver tabela'}
          </button>
        )}
      </div>

      {pontos.length === 0 && <p>Sem dados suficientes nesse período.</p>}

      {pontos.length > 0 && !mostrarTabela && (
        <div className="grafico-linha-container">
          <svg viewBox={`0 0 ${largura} ${altura}`} className="grafico-linha-svg">
            {[0, 0.5, 1].map((fracao) => {
              const yPos = margem.topo + areaAltura * fracao
              return <line key={fracao} x1={margem.esquerda} x2={largura - margem.direita} y1={yPos} y2={yPos} className="grafico-linha-grade" />
            })}

            <path d={caminho} fill="none" stroke="#2563eb" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round" />

            {pontos.map((p, i) => (
              <g key={i} onMouseEnter={() => setHoverIndex(i)} onMouseLeave={() => setHoverIndex(null)}>
                <circle cx={x(i)} cy={y(p.valor)} r="10" fill="transparent" />
                <circle cx={x(i)} cy={y(p.valor)} r="4" fill="#2563eb" />
              </g>
            ))}

            {hoverIndex !== null && (
              <line x1={x(hoverIndex)} x2={x(hoverIndex)} y1={margem.topo} y2={altura - margem.baixo} className="grafico-linha-crosshair" />
            )}
          </svg>

          {hoverIndex !== null && (
            <div className="grafico-linha-tooltip" style={{ left: `${(x(hoverIndex) / largura) * 100}%` }}>
              <strong>{formatarValor(pontos[hoverIndex].valor)}</strong>
              <span>{formatarData(pontos[hoverIndex].data)}</span>
            </div>
          )}
        </div>
      )}

      {pontos.length > 0 && mostrarTabela && (
        <table className="evolucao-tabela">
          <thead><tr><th>Data</th><th>Valor</th></tr></thead>
          <tbody>
            {pontos.map((p, i) => (
              <tr key={i}><td>{formatarData(p.data)}</td><td>{formatarValor(p.valor)}</td></tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  )
}

export default function AlunoDetalhe() {
  const { alunoId } = useParams()
  const navigate = useNavigate()

  const [aluno, setAluno] = useState(null)
  const [avaliacoes, setAvaliacoes] = useState([])
  const [fichas, setFichas] = useState([])
  const [relatorio, setRelatorio] = useState(null)
  const [aba, setAba] = useState('visao-geral')
  const [carregando, setCarregando] = useState(true)
  const [erro, setErro] = useState('')

  useEffect(() => {
    carregar()
  }, [alunoId])

  async function carregar() {
    setCarregando(true)
    setErro('')
    try {
      const [todosAlunos, listaAvaliacoes, listaFichas, dadosRelatorio] = await Promise.all([
        listarAlunos(),
        listarAvaliacoesPorAluno(alunoId),
        listarFichasPorAluno(alunoId),
        obterRelatorioAluno(alunoId, seisMesesAtras(), hoje()),
      ])
      const encontrado = todosAlunos.find((a) => a.id === Number(alunoId))
      if (!encontrado) {
        setErro('Aluno não encontrado.')
      } else {
        setAluno(encontrado)
        setAvaliacoes([...listaAvaliacoes].sort((a, b) => b.dataAvaliacao.localeCompare(a.dataAvaliacao)))
        setFichas(listaFichas)
        setRelatorio(dadosRelatorio)
      }
    } catch {
      setErro('Não foi possível carregar os dados do aluno.')
    } finally {
      setCarregando(false)
    }
  }

  const pontosPeso = (relatorio?.evolucaoMedidas ?? [])
    .filter((p) => p.peso != null)
    .map((p) => ({ data: p.data, valor: p.peso }))

  const medidaMaisRecente = relatorio?.evolucaoMedidas?.[relatorio.evolucaoMedidas.length - 1]
  const fichasAtivas = fichas.filter(fichaVigenteHoje)

  if (carregando) return <div className="avaliacoes-page"><p>Carregando...</p></div>
  if (erro) return <div className="avaliacoes-page"><p className="avaliacoes-erro">{erro}</p></div>
  if (!aluno) return null

  return (
    <div className="avaliacoes-page">
      <button type="button" className="aluno-detalhe-voltar" onClick={() => navigate('/alunos')}>← Voltar para alunos</button>

      <div className="avaliacao-card aluno-detalhe-cabecalho">
        <div className="aluno-detalhe-identificacao">
          <span className="alunos-avatar aluno-detalhe-avatar">{iniciais(aluno.nome)}</span>
          <div>
            <h1>{aluno.nome}</h1>
            <p>{calcularIdade(aluno.dataNascimento)} anos • {formatarSexo(aluno.sexo)}</p>
            <p className="aluno-detalhe-contato">{aluno.email} • {aluno.telefone}</p>
          </div>
        </div>
      </div>

      <div className="evolucao-abas">
        <button type="button" className={`treino-aba ${aba === 'visao-geral' ? 'treino-aba-ativa' : ''}`} onClick={() => setAba('visao-geral')}>Visão geral</button>
        <button type="button" className={`treino-aba ${aba === 'avaliacoes' ? 'treino-aba-ativa' : ''}`} onClick={() => setAba('avaliacoes')}>Avaliações ({avaliacoes.length})</button>
        <button type="button" className={`treino-aba ${aba === 'treinos' ? 'treino-aba-ativa' : ''}`} onClick={() => setAba('treinos')}>Treinos ({fichas.length})</button>
        <button type="button" className={`treino-aba ${aba === 'observacoes' ? 'treino-aba-ativa' : ''}`} onClick={() => setAba('observacoes')}>Observações</button>
      </div>

      {aba === 'visao-geral' && (
        <>
          <GraficoLinha titulo="Resumo de evolução (peso)" pontos={pontosPeso} formatarValor={(v) => `${v} kg`} />

          <div className="aluno-detalhe-dados-grid">
            <div className="avaliacao-card aluno-detalhe-dado">
              <span>Peso</span>
              <strong>{medidaMaisRecente?.peso != null ? `${medidaMaisRecente.peso} kg` : '-'}</strong>
            </div>
            <div className="avaliacao-card aluno-detalhe-dado">
              <span>IMC</span>
              <strong>{medidaMaisRecente?.imc != null ? medidaMaisRecente.imc.toFixed(1) : '-'}</strong>
            </div>
            <div className="avaliacao-card aluno-detalhe-dado">
              <span>Gordura corporal</span>
              <strong>{medidaMaisRecente?.percentualGordura != null ? `${medidaMaisRecente.percentualGordura}%` : '-'}</strong>
            </div>
            <div className="avaliacao-card aluno-detalhe-dado">
              <span>Fichas ativas</span>
              <strong>{fichasAtivas.length}</strong>
            </div>
          </div>

          <div className="avaliacao-card">
            <div className="aluno-detalhe-historico-topo">
              <h2>Histórico de avaliações</h2>
              <button type="button" className="botao-secundario" onClick={() => navigate('/comparacao', { state: { aluno } })}>Comparar avaliações</button>
            </div>
            {avaliacoes.length === 0 && <p>Nenhuma avaliação registrada.</p>}
            <div className="aluno-detalhe-lista-avaliacoes">
              {avaliacoes.slice(0, 3).map((a) => (
                <button
                  type="button"
                  key={a.id}
                  className="aluno-detalhe-item-avaliacao"
                  onClick={() => navigate(`/avaliacoes/${a.id}`, { state: { nomeAluno: aluno.nome } })}
                >
                  <span>{formatarData(a.dataAvaliacao)}</span>
                  <span>{a.observacoes || 'Sem observações'}</span>
                  <span>→</span>
                </button>
              ))}
            </div>
          </div>
        </>
      )}

      {aba === 'avaliacoes' && (
        <div className="avaliacao-card">
          <div className="aluno-detalhe-historico-topo">
            <h2>Todas as avaliações</h2>
            <button type="button" className="botao-primario" onClick={() => navigate(`/avaliacoes/nova/${alunoId}`, { state: { nomeAluno: aluno.nome } })}>+ Nova avaliação</button>
          </div>
          {avaliacoes.length === 0 && <p>Nenhuma avaliação registrada.</p>}
          <div className="aluno-detalhe-lista-avaliacoes">
            {avaliacoes.map((a) => (
              <button
                type="button"
                key={a.id}
                className="aluno-detalhe-item-avaliacao"
                onClick={() => navigate(`/avaliacoes/${a.id}`, { state: { nomeAluno: aluno.nome } })}
              >
                <span>{formatarData(a.dataAvaliacao)}</span>
                <span>{a.observacoes || 'Sem observações'}</span>
                <span>→</span>
              </button>
            ))}
          </div>
        </div>
      )}

      {aba === 'treinos' && (
        <div className="avaliacao-card">
          <div className="aluno-detalhe-historico-topo">
            <h2>Fichas de treino</h2>
            <button type="button" className="botao-primario" onClick={() => navigate(`/treinos/nova/${alunoId}`, { state: { nomeAluno: aluno.nome } })}>+ Nova ficha</button>
          </div>
          {fichas.length === 0 && <p>Nenhuma ficha cadastrada.</p>}
          <div className="aluno-detalhe-lista-avaliacoes">
            {fichas.map((f) => (
              <button
                type="button"
                key={f.id}
                className="aluno-detalhe-item-avaliacao"
                onClick={() => navigate(`/treinos/ficha/${f.id}`, { state: { ficha: f, nomeAluno: aluno.nome } })}
              >
                <span>{f.nome || 'Ficha sem nome'}</span>
                <span>{formatarData(f.dataInicio)}{f.dataFim ? ` até ${formatarData(f.dataFim)}` : ''}</span>
                <span>→</span>
              </button>
            ))}
          </div>
        </div>
      )}

      {aba === 'observacoes' && (
        <div className="avaliacao-card">
          <h2>Objetivo</h2>
          <p>{aluno.objetivos || 'Nenhum objetivo registrado.'}</p>
          <h2>Observações</h2>
          <p>{aluno.observacoes || 'Nenhuma observação registrada.'}</p>
        </div>
      )}
    </div>
  )
}
