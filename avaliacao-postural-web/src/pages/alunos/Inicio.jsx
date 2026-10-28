import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { useAuth } from '../../context/AuthContext'
import { listarMinhasFichas } from '../../services/fichaService'
import { listarMinhasExecucoes } from '../../services/execucaoService'
import './Inicio.css'
import { dataLocalIso } from '../../lib/formatadores'

function fichaVigenteHoje(ficha) {
  const hojeStr = dataLocalIso()
  const depoisDoInicio = ficha.dataInicio <= hojeStr
  const antesDoFim = !ficha.dataFim || ficha.dataFim >= hojeStr
  return depoisDoInicio && antesDoFim
}

export default function Inicio() {
  const { usuario } = useAuth()
  const navigate = useNavigate()
  const [fichasAtivas, setFichasAtivas] = useState([])
  const [execucoes, setExecucoes] = useState([])
  const [carregando, setCarregando] = useState(true)

  useEffect(() => {
    Promise.all([listarMinhasFichas(), listarMinhasExecucoes()])
      .then(([fichas, execucoesLista]) => {
        setFichasAtivas(fichas.filter(fichaVigenteHoje))
        setExecucoes(execucoesLista)
      })
      .catch(() => {})
      .finally(() => setCarregando(false))
  }, [])

  const totalItens = fichasAtivas.reduce((soma, f) => soma + f.itens.length, 0)
  const totalSeries = fichasAtivas.reduce((soma, f) => soma + f.itens.reduce((s, i) => s + i.series, 0), 0)
  const idsItensAtivos = new Set(fichasAtivas.flatMap((f) => f.itens.map((i) => i.id)))
  const seriesFeitas = execucoes.filter((e) => idsItensAtivos.has(e.itemFichaId)).length

  return (
    <div className="inicio-page">
      <h1>Olá, {usuario?.nome}</h1>
      <p className="inicio-subtitulo">Acompanhe seu progresso e seu treino.</p>

      {!carregando && (
        <div className="inicio-card-treino">
          {fichasAtivas.length === 0 ? (
            <>
              <h2>Nenhum treino vigente</h2>
              <p>Você não tem nenhuma ficha de treino ativa no momento.</p>
            </>
          ) : (
            <>
              <h2>{fichasAtivas.length > 1 ? 'Seus treinos' : (fichasAtivas[0].nome || 'Seu treino')}</h2>
              <p>{seriesFeitas}/{totalSeries} séries concluídas • {totalItens} exercício(s)</p>
              <button type="button" className="botao-primario" onClick={() => navigate('/aluno/treino')}>Ir para o treino</button>
            </>
          )}
        </div>
      )}

      <div className="inicio-atalhos">
        <button type="button" className="inicio-atalho" onClick={() => navigate('/aluno/avaliacoes')}>
          <strong>Avaliações</strong>
          <span>Ver minhas avaliações</span>
        </button>
        <button type="button" className="inicio-atalho" onClick={() => navigate('/aluno/evolucao')}>
          <strong>Evolução</strong>
          <span>Acompanhar progresso</span>
        </button>
        <button type="button" className="inicio-atalho" onClick={() => navigate('/aluno/perfil')}>
          <strong>Perfil</strong>
          <span>Meus dados</span>
        </button>
      </div>
    </div>
  )
}
