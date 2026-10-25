import { useEffect, useState } from 'react'
import { useParams, useLocation, useNavigate } from 'react-router-dom'
import { listarGruposMusculares, listarExercicios } from '../../services/exercicioService'
import { editarFicha } from '../../services/fichaService'
import { formatarData } from '../../lib/formatadores'
import './Avaliacoes.css'
import './NovaFicha.css'
import { listarAvaliacoesPorAluno } from '../../services/avaliacaoService'
import { listarDesvios } from '../../services/desvioService'

const REGIOES = [
  { value: 'PES', label: 'Pés' },
  { value: 'TIBIA', label: 'Tíbia' },
  { value: 'JOELHO', label: 'Joelho' },
  { value: 'QUADRIL_PELVE', label: 'Quadril/Pelve' },
  { value: 'ESCAPULA', label: 'Escápula' },
  { value: 'OMBRO', label: 'Ombro' },
  { value: 'COLUNA', label: 'Coluna' },
  { value: 'CABECA', label: 'Cabeça' },
]

const SEVERIDADES = [
  { value: 'LEVE', label: 'Leve' },
  { value: 'MODERADA', label: 'Moderada' },
  { value: 'ACENTUADA', label: 'Acentuada' },
]

function labelRegiao(codigo) {
  return REGIOES.find((r) => r.value === codigo)?.label ?? codigo
}

function labelSeveridade(codigo) {
  return SEVERIDADES.find((s) => s.value === codigo)?.label ?? codigo
}

export default function FichaDetalhe() {
  const { fichaId } = useParams()
  const location = useLocation()
  const navigate = useNavigate()
  const fichaOriginal = location.state?.ficha
  const nomeAluno = location.state?.nomeAluno

  const [dataFim, setDataFim] = useState(fichaOriginal?.dataFim ?? '')
  const [frequenciaSemanal, setFrequenciaSemanal] = useState(
    fichaOriginal?.frequenciaSemanal ? String(fichaOriginal.frequenciaSemanal) : ''
  )
  const [grupos, setGrupos] = useState([])
  const [exercicios, setExercicios] = useState([])
  const [grupoSelecionado, setGrupoSelecionado] = useState(null)
  const [buscaExercicio, setBuscaExercicio] = useState('')
  const [itens, setItens] = useState([])
  const [erro, setErro] = useState('')
  const [salvando, setSalvando] = useState(false)
  const [avaliacoes, setAvaliacoes] = useState([])
  const [avaliacaoSelecionadaId, setAvaliacaoSelecionadaId] = useState(null)
  const [desvios, setDesvios] = useState([])
  const [carregandoDesvios, setCarregandoDesvios] = useState(true)
  const [nome, setNome] = useState(fichaOriginal?.nome ?? '')

  useEffect(() => {
    if (!fichaOriginal) return
    Promise.all([listarGruposMusculares(), listarExercicios()])
      .then(([listaGrupos, listaExercicios]) => {
        setGrupos(listaGrupos)
        setExercicios(listaExercicios)
        if (listaGrupos.length > 0) setGrupoSelecionado(listaGrupos[0].id)

        const itensIniciais = fichaOriginal.itens.map((item) => {
          const exercicio = listaExercicios.find((e) => e.id === item.exercicioId)
          return {
            id: crypto.randomUUID(),
            exercicioId: item.exercicioId,
            nomeExercicio: exercicio?.nome ?? `Exercício #${item.exercicioId}`,
            ordem: item.ordem,
            series: item.series,
            repeticoesMin: item.repeticoesMin,
            repeticoesMax: item.repeticoesMax,
            descanso: item.descanso,
          }
        })
        setItens(itensIniciais)
      })
      .catch(() => setErro('Não foi possível carregar os exercícios.'))
  }, [])

  useEffect(() => {
    if (!fichaOriginal) return
    listarAvaliacoesPorAluno(fichaOriginal.alunoId)
      .then((lista) => {
        const ordenadas = [...lista].sort((a, b) => b.dataAvaliacao.localeCompare(a.dataAvaliacao))
        setAvaliacoes(ordenadas)
        if (fichaOriginal.avaliacaoReferenciaId) {
          setAvaliacaoSelecionadaId(fichaOriginal.avaliacaoReferenciaId)
        } else if (ordenadas.length > 0) {
          setAvaliacaoSelecionadaId(ordenadas[0].id)
        } else {
          setCarregandoDesvios(false)
        }
      })
      .catch(() => setCarregandoDesvios(false))
  }, [])

  useEffect(() => {
    if (!avaliacaoSelecionadaId) return
    setCarregandoDesvios(true)
    listarDesvios(avaliacaoSelecionadaId)
      .then(setDesvios)
      .catch(() => setDesvios([]))
      .finally(() => setCarregandoDesvios(false))
  }, [avaliacaoSelecionadaId])

  if (!fichaOriginal) {
    return (
      <div className="avaliacoes-page">
        <p className="avaliacoes-erro">
          Não foi possível abrir essa ficha diretamente. Volte para "Consultar fichas" e selecione novamente.
        </p>
      </div>
    )
  }

  const exerciciosDoGrupo = exercicios
    .filter((e) => e.grupoMuscularId === grupoSelecionado)
    .filter((e) => e.nome.toLowerCase().includes(buscaExercicio.toLowerCase()))

  function selecionarGrupo(id) {
    setGrupoSelecionado(id)
    setBuscaExercicio('')
  }

  function adicionarItem(exercicio) {
    const novoItem = {
      id: crypto.randomUUID(),
      exercicioId: exercicio.id,
      nomeExercicio: exercicio.nome,
      ordem: itens.length + 1,
      series: 3,
      repeticoesMin: 8,
      repeticoesMax: 12,
      descanso: 60,
    }
    setItens([...itens, novoItem])
  }

  function atualizarItem(id, campo, valor) {
    setItens(itens.map((item) => (item.id === id ? { ...item, [campo]: valor } : item)))
  }

  function removerItem(id) {
    setItens(itens.filter((item) => item.id !== id))
  }

  async function handleSalvar() {
    if (itens.length === 0) {
      setErro('A ficha precisa ter pelo menos um exercício.')
      return
    }
    setErro('')
    setSalvando(true)
    try {
      await editarFicha(fichaId, {
        nome,
        dataFim: dataFim || null,
        frequenciaSemanal: frequenciaSemanal ? Number(frequenciaSemanal) : null,
        avaliacaoReferenciaId: avaliacaoSelecionadaId ? Number(avaliacaoSelecionadaId) : null,
        itens: itens.map((item) => ({
          exercicioId: item.exercicioId,
          ordem: Number(item.ordem),
          series: Number(item.series),
          repeticoesMin: Number(item.repeticoesMin),
          repeticoesMax: Number(item.repeticoesMax),
          descanso: Number(item.descanso),
        })),
      })
      navigate(`/treinos/aluno/${fichaOriginal.alunoId}`, { state: { nomeAluno } })
    } catch {
      setErro('Não foi possível salvar as alterações.')
    } finally {
      setSalvando(false)
    }
  }

  return (
    <div className="ficha-page">
      <span className="avaliacoes-eyebrow">Editar ficha</span>
      <h1>{nomeAluno ? `Ficha de treino — ${nomeAluno}` : 'Ficha de treino'}</h1>

      <div className="ficha-resumo">
        <div className="ficha-resumo-dados">
          <div className="ficha-campo-inline">
            <label>Nome</label>
            <input type="text" value={nome} onChange={(e) => setNome(e.target.value)} placeholder="ex: Treino A" />
          </div>
          <span>Data Início: {formatarData(fichaOriginal.dataInicio)}</span>
          <div className="ficha-campo-inline">
            <label>Data Fim</label>
            <input type="date" value={dataFim} onChange={(e) => setDataFim(e.target.value)} />
          </div>
          <div className="ficha-campo-inline">
            <label>Frequência</label>
            <select value={frequenciaSemanal} onChange={(e) => setFrequenciaSemanal(e.target.value)}>
              <option value="">Selecione...</option>
              <option value="1">1x por semana</option>
              <option value="2">2x por semana</option>
              <option value="3">3x por semana</option>
              <option value="4">4x por semana</option>
              <option value="5">5x por semana</option>
            </select>
          </div>
        </div>
      </div>

      {avaliacoes.length === 0 && !carregandoDesvios && (
        <div className="ficha-aviso-sem-avaliacao">
          Este aluno ainda não possui avaliação postural registrada. Recomendamos avaliar antes de prescrever um treino corretivo.
        </div>
      )}

      {avaliacoes.length > 0 && (
        <div className="avaliacao-card ficha-painel-desvios">
          <div className="ficha-painel-desvios-topo">
            <h2>Desvios posturais identificados</h2>
            <select value={avaliacaoSelecionadaId ?? ''} onChange={(e) => setAvaliacaoSelecionadaId(e.target.value)}>
              {avaliacoes.map((a) => (
                <option key={a.id} value={a.id}>{formatarData(a.dataAvaliacao)}</option>
              ))}
            </select>
          </div>

          {carregandoDesvios && <p>Carregando...</p>}
          {!carregandoDesvios && desvios.length === 0 && <p>Nenhum desvio registrado nessa avaliação.</p>}
          {!carregandoDesvios && desvios.length > 0 && (
            <div className="ficha-desvios-lista">
              {desvios.map((d) => (
                <span key={d.id} className="ficha-desvio-tag">
                  {labelRegiao(d.regiao)} — {d.tipo} ({labelSeveridade(d.severidade)})
                </span>
              ))}
            </div>
          )}
        </div>
      )}

      {erro && <p className="avaliacoes-erro">{erro}</p>}

      <div className="ficha-colunas">
        <div className="avaliacao-card ficha-coluna-grupos">
          <h2>Grupos</h2>
          <div className="ficha-lista-grupos">
            {grupos.map((grupo) => (
              <button
                type="button"
                key={grupo.id}
                className={`ficha-botao-grupo ${grupoSelecionado === grupo.id ? 'ficha-botao-grupo-ativo' : ''}`}
                onClick={() => selecionarGrupo(grupo.id)}
              >
                {grupo.nome}
              </button>
            ))}
          </div>
        </div>

        <div className="avaliacao-card ficha-coluna-exercicios">
          <h2>Exercícios</h2>
          <input
            type="text"
            className="ficha-busca-exercicio"
            placeholder="Buscar exercício..."
            value={buscaExercicio}
            onChange={(e) => setBuscaExercicio(e.target.value)}
          />
          <div className="ficha-lista-exercicios">
            {exerciciosDoGrupo.map((exercicio) => (
              <div key={exercicio.id} className="ficha-exercicio-item">
                <span>{exercicio.nome}</span>
                <button type="button" className="botao-secundario" onClick={() => adicionarItem(exercicio)}>+ Adicionar</button>
              </div>
            ))}
            {exerciciosDoGrupo.length === 0 && <p>Nenhum exercício encontrado.</p>}
          </div>
        </div>

        <div className="avaliacao-card ficha-coluna-itens">
          <h2>Exercícios da ficha</h2>
          {itens.length === 0 && <p className="avaliacao-desvio-vazio">Nenhum exercício adicionado ainda.</p>}
          {itens.map((item) => (
            <div key={item.id} className="ficha-item-linha">
              <strong>{item.nomeExercicio}</strong>
              <div className="ficha-item-campos">
                <div>
                  <label>Ordem</label>
                  <input type="number" min="1" value={item.ordem} onChange={(e) => atualizarItem(item.id, 'ordem', e.target.value)} />
                </div>
                <div>
                  <label>Séries</label>
                  <input type="number" min="1" value={item.series} onChange={(e) => atualizarItem(item.id, 'series', e.target.value)} />
                </div>
                <div>
                  <label>Reps. mín</label>
                  <input type="number" min="1" value={item.repeticoesMin} onChange={(e) => atualizarItem(item.id, 'repeticoesMin', e.target.value)} />
                </div>
                <div>
                  <label>Reps. máx</label>
                  <input type="number" min="1" value={item.repeticoesMax} onChange={(e) => atualizarItem(item.id, 'repeticoesMax', e.target.value)} />
                </div>
                <div>
                  <label>Descanso (s)</label>
                  <input type="number" min="0" value={item.descanso} onChange={(e) => atualizarItem(item.id, 'descanso', e.target.value)} />
                </div>
              </div>
              <button type="button" className="avaliacao-desvio-remover" onClick={() => removerItem(item.id)}>Remover</button>
            </div>
          ))}

          <div className="avaliacao-acoes">
            <button type="button" className="botao-primario" onClick={handleSalvar} disabled={salvando}>
              {salvando ? 'Salvando...' : 'Salvar alterações'}
            </button>
          </div>
        </div>
      </div>
    </div>
  )
}
