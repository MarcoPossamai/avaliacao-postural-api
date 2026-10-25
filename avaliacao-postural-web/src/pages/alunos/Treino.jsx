import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { listarMinhasFichas } from '../../services/fichaService'
import { listarExercicios } from '../../services/exercicioService'
import { registrarExecucao, listarMinhasExecucoes } from '../../services/execucaoService'
import ModalSucesso from '../../components/ModalSucesso'
import './Treino.css'

function agora() {
  const d = new Date()
  const pad = (n) => String(n).padStart(2, '0')
  return `${d.getFullYear()}-${pad(d.getMonth() + 1)}-${pad(d.getDate())}T${pad(d.getHours())}:${pad(d.getMinutes())}:${pad(d.getSeconds())}`
}

function hojeLocal() {
  const d = new Date()
  const pad = (n) => String(n).padStart(2, '0')
  return `${d.getFullYear()}-${pad(d.getMonth() + 1)}-${pad(d.getDate())}`
}

function fichaVigenteHoje(ficha) {
  const hojeStr = new Date().toISOString().slice(0, 10)
  const depoisDoInicio = ficha.dataInicio <= hojeStr
  const antesDoFim = !ficha.dataFim || ficha.dataFim >= hojeStr
  return depoisDoInicio && antesDoFim
}

function chaveEncerradosHoje() {
  return `treino_encerrado_${hojeLocal()}`
}

function carregarFichasEncerradasHoje() {
  try {
    const bruto = localStorage.getItem(chaveEncerradosHoje())
    return bruto ? JSON.parse(bruto) : []
  } catch {
    return []
  }
}

export default function TreinoAluno() {
  const navigate = useNavigate()
  const [todasFichas, setTodasFichas] = useState([])
  const [fichasAtivas, setFichasAtivas] = useState([])
  const [fichaSelecionadaId, setFichaSelecionadaId] = useState(null)
  const [exercicios, setExercicios] = useState([])
  const [execucoes, setExecucoes] = useState([])
  const [carregando, setCarregando] = useState(true)
  const [erro, setErro] = useState('')
  const [camposPorItem, setCamposPorItem] = useState({})
  const [enviandoItem, setEnviandoItem] = useState(null)
  const [resumoEncerramento, setResumoEncerramento] = useState(null)
  const [fichasEncerradas, setFichasEncerradas] = useState(() => carregarFichasEncerradasHoje())

  useEffect(() => {
    carregar()
  }, [])

  async function carregar() {
    setCarregando(true)
    setErro('')
    try {
      const [fichas, listaExercicios, listaExecucoes] = await Promise.all([
        listarMinhasFichas(),
        listarExercicios(),
        listarMinhasExecucoes(),
      ])
      const ativas = fichas.filter(fichaVigenteHoje)
      setTodasFichas(fichas)
      setFichasAtivas(ativas)
      setFichaSelecionadaId(ativas[0]?.id ?? null)
      setExercicios(listaExercicios)
      setExecucoes(listaExecucoes)
    } catch {
      setErro('Não foi possível carregar seu treino.')
    } finally {
      setCarregando(false)
    }
  }

  function nomeExercicio(exercicioId) {
    return exercicios.find((e) => e.id === exercicioId)?.nome ?? `Exercício #${exercicioId}`
  }

  function execucoesDoItemHoje(itemFichaId) {
    return execucoes.filter((e) => e.itemFichaId === itemFichaId && e.horaInicio?.slice(0, 10) === hojeLocal())
  }

  function atualizarCampo(itemFichaId, campo, valor) {
    setCamposPorItem((atuais) => ({
      ...atuais,
      [itemFichaId]: { ...atuais[itemFichaId], [campo]: valor },
    }))
  }

  async function handleRegistrar(ficha, item) {
    const campos = camposPorItem[item.id] ?? {}
    const jaFeitas = execucoesDoItemHoje(item.id).length
    setEnviandoItem(item.id)
    setErro('')
    try {
      const nova = await registrarExecucao(ficha.id, {
        itemFichaId: item.id,
        numeroSerie: jaFeitas + 1,
        cargaUtilizada: campos.carga ? Number(campos.carga) : null,
        repeticoes: campos.repeticoes ? Number(campos.repeticoes) : null,
        horaInicio: agora(),
      })
      setExecucoes((atuais) => [...atuais, nova])
      setCamposPorItem((atuais) => ({ ...atuais, [item.id]: { carga: '', repeticoes: '' } }))
    } catch {
      setErro('Não foi possível registrar essa série.')
    } finally {
      setEnviandoItem(null)
    }
  }

  function handleEncerrarTreino() {
    const totalFeitas = itensOrdenados.reduce((soma, item) => soma + execucoesDoItemHoje(item.id).length, 0)
    const totalPrescritas = itensOrdenados.reduce((soma, item) => soma + item.series, 0)
    const atualizadas = [...fichasEncerradas, ficha.id]
    setFichasEncerradas(atualizadas)
    try {
      localStorage.setItem(chaveEncerradosHoje(), JSON.stringify(atualizadas))
    } catch {
      // ignora falha de localStorage (ex: navegação privada)
    }
    setResumoEncerramento({ totalFeitas, totalPrescritas })
  }

  if (carregando) {
    return <div className="treino-page"><p>Carregando...</p></div>
  }

  if (fichasAtivas.length === 0) {
    return (
      <div className="treino-page">
        <h1>Treino</h1>
        <p>
          {todasFichas.length === 0
            ? 'Você ainda não tem uma ficha de treino prescrita.'
            : 'Você não tem nenhum treino vigente no momento.'}
        </p>
      </div>
    )
  }

  const ficha = fichasAtivas.find((f) => f.id === fichaSelecionadaId) ?? fichasAtivas[0]
  const itensOrdenados = [...ficha.itens].sort((a, b) => a.ordem - b.ordem)
  const treinoEncerradoHoje = fichasEncerradas.includes(ficha.id)

  return (
    <div className="treino-page">
      <h1>Treino</h1>

      {fichasAtivas.length > 1 && (
        <div className="treino-abas">
          {fichasAtivas.map((f) => (
            <button
              type="button"
              key={f.id}
              className={`treino-aba ${f.id === ficha.id ? 'treino-aba-ativa' : ''}`}
              onClick={() => setFichaSelecionadaId(f.id)}
            >
              {f.nome || 'Treino sem nome'}
            </button>
          ))}
        </div>
      )}

      <p className="treino-info">
        {ficha.nome && <strong>{ficha.nome} • </strong>}
        Início: {ficha.dataInicio}{ficha.dataFim && ` até ${ficha.dataFim}`}
        {ficha.frequenciaSemanal && ` • ${ficha.frequenciaSemanal}x por semana`}
      </p>

      {erro && <p className="perfil-erro">{erro}</p>}

      {treinoEncerradoHoje && <p className="treino-encerrado-aviso">Treino já encerrado hoje. Volte amanhã para registrar novas séries.</p>}

      {itensOrdenados.map((item) => {
        const feitas = execucoesDoItemHoje(item.id)
        const campos = camposPorItem[item.id] ?? { carga: '', repeticoes: '' }
        const completo = feitas.length >= item.series

        return (
          <div key={item.id} className={`treino-item ${completo ? 'treino-item-completo' : ''}`}>
            <div className="treino-item-topo">
              <h2>{nomeExercicio(item.exercicioId)}</h2>
              <span>{feitas.length}/{item.series} séries</span>
            </div>
            <p className="treino-item-prescricao">
              {item.series} séries de {item.repeticoesMin}-{item.repeticoesMax} repetições • {item.descanso}s de descanso
            </p>

            {!completo && !treinoEncerradoHoje && (
              <div className="treino-item-registro">
                <input
                  type="number"
                  step="0.5"
                  placeholder="Carga (kg)"
                  value={campos.carga}
                  onChange={(e) => atualizarCampo(item.id, 'carga', e.target.value)}
                />
                <input
                  type="number"
                  placeholder="Repetições"
                  value={campos.repeticoes}
                  onChange={(e) => atualizarCampo(item.id, 'repeticoes', e.target.value)}
                />
                <button
                  type="button"
                  className="botao-primario"
                  onClick={() => handleRegistrar(ficha, item)}
                  disabled={enviandoItem === item.id}
                >
                  {enviandoItem === item.id ? 'Salvando...' : `Concluir série ${feitas.length + 1}`}
                </button>
              </div>
            )}

            {completo && <p className="treino-item-concluido">Exercício concluído ✓</p>}
          </div>
        )
      })}

      {!treinoEncerradoHoje && (
        <button type="button" className="botao-primario treino-botao-encerrar" onClick={handleEncerrarTreino}>
          Encerrar treino
        </button>
      )}

      {resumoEncerramento && (
        <ModalSucesso
          titulo="Treino encerrado!"
          mensagem={`Você concluiu ${resumoEncerramento.totalFeitas}/${resumoEncerramento.totalPrescritas} séries hoje. Bom trabalho!`}
          onFechar={() => navigate('/aluno')}
        />
      )}
    </div>
  )
}
