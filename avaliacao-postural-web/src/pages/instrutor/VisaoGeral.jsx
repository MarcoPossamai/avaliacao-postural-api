import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { useAuth } from '../../context/AuthContext'
import { listarAlunos } from '../../services/alunoService'
import { listarAvaliacoesPorAluno } from '../../services/avaliacaoService'
import { listarFichasPorAluno } from '../../services/fichaService'
import './Avaliacoes.css'
import './AlunoDetalhe.css'
import '../alunos/Inicio.css'

function fichaVigenteHoje(ficha) {
  const hojeStr = new Date().toISOString().slice(0, 10)
  const depoisDoInicio = ficha.dataInicio <= hojeStr
  const antesDoFim = !ficha.dataFim || ficha.dataFim >= hojeStr
  return depoisDoInicio && antesDoFim
}

export default function VisaoGeral() {
  const { usuario } = useAuth()
  const navigate = useNavigate()
  const [totalAlunos, setTotalAlunos] = useState(0)
  const [avaliacoesMes, setAvaliacoesMes] = useState(0)
  const [fichasAtivas, setFichasAtivas] = useState(0)
  const [carregando, setCarregando] = useState(true)
  const [erro, setErro] = useState('')

  useEffect(() => {
    carregar()
  }, [])

  async function carregar() {
    setCarregando(true)
    setErro('')
    try {
      const alunos = await listarAlunos()
      const [todasAvaliacoes, todasFichas] = await Promise.all([
        Promise.all(alunos.map((a) => listarAvaliacoesPorAluno(a.id))),
        Promise.all(alunos.map((a) => listarFichasPorAluno(a.id))),
      ])

      const mesAtual = new Date().toISOString().slice(0, 7)
      const avaliacoesEsteMes = todasAvaliacoes.flat().filter((a) => a.dataAvaliacao.slice(0, 7) === mesAtual).length
      const totalFichasAtivas = todasFichas.flat().filter(fichaVigenteHoje).length

      setTotalAlunos(alunos.length)
      setAvaliacoesMes(avaliacoesEsteMes)
      setFichasAtivas(totalFichasAtivas)
    } catch {
      setErro('Não foi possível carregar os dados.')
    } finally {
      setCarregando(false)
    }
  }

  return (
    <div className="avaliacoes-page">
      <span className="avaliacoes-eyebrow">Visão geral</span>
      <h1>Olá, {usuario?.nome}</h1>
      <p className="inicio-subtitulo">Resumo rápido do seu dia a dia como instrutor.</p>

      {erro && <p className="avaliacoes-erro">{erro}</p>}

      {!carregando && (
        <div className="aluno-detalhe-dados-grid">
          <div className="avaliacao-card aluno-detalhe-dado">
            <span>Total de alunos</span>
            <strong>{totalAlunos}</strong>
          </div>
          <div className="avaliacao-card aluno-detalhe-dado">
            <span>Avaliações este mês</span>
            <strong>{avaliacoesMes}</strong>
          </div>
          <div className="avaliacao-card aluno-detalhe-dado">
            <span>Fichas ativas hoje</span>
            <strong>{fichasAtivas}</strong>
          </div>
        </div>
      )}

      <div className="inicio-atalhos">
        <button type="button" className="inicio-atalho" onClick={() => navigate('/alunos')}>
          <strong>Alunos</strong>
          <span>Cadastrar ou consultar</span>
        </button>
        <button type="button" className="inicio-atalho" onClick={() => navigate('/avaliacoes')}>
          <strong>Avaliações</strong>
          <span>Nova avaliação postural</span>
        </button>
        <button type="button" className="inicio-atalho" onClick={() => navigate('/treinos')}>
          <strong>Treinos</strong>
          <span>Prescrever ficha de treino</span>
        </button>
      </div>
    </div>
  )
}
