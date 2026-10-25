import { useEffect, useState } from 'react'
import { useNavigate, useParams, useLocation } from 'react-router-dom'
import { listarFichasPorAluno } from '../../services/fichaService'
import './Avaliacoes.css'

export default function ConsultarFichas() {
  const { alunoId } = useParams()
  const location = useLocation()
  const navigate = useNavigate()
  const [fichas, setFichas] = useState([])
  const [carregando, setCarregando] = useState(true)
  const [erro, setErro] = useState('')

  const nomeAluno = location.state?.nomeAluno

  useEffect(() => {
    listarFichasPorAluno(alunoId)
      .then(setFichas)
      .catch(() => setErro('Não foi possível carregar as fichas.'))
      .finally(() => setCarregando(false))
  }, [alunoId])

  return (
    <div className="avaliacoes-page">
      <span className="avaliacoes-eyebrow">Treinos</span>
      <h1>{nomeAluno ? `Fichas de ${nomeAluno}` : 'Fichas do aluno'}</h1>

      {erro && <p className="avaliacoes-erro">{erro}</p>}
      {carregando && <p>Carregando...</p>}

      {!carregando && !erro && (
        <div className="avaliacao-card">
          {fichas.length === 0 && <p>Nenhuma ficha registrada ainda.</p>}
          <div className="avaliacao-lista-alunos">
            {fichas.map((ficha) => (
              <button
                type="button"
                key={ficha.id}
                className="avaliacao-item-aluno"
                onClick={() => navigate(`/treinos/ficha/${ficha.id}`, { state: { ficha, nomeAluno } })}
              >
                <strong>{ficha.nome || 'Treino sem nome'}</strong> — {ficha.dataInicio} {ficha.dataFim ? `até ${ficha.dataFim}` : ''} — {ficha.itens.length} exercício(s)
              </button>
            ))}
          </div>
          <div className="avaliacao-acoes">
            <button type="button" className="botao-secundario" onClick={() => navigate('/treinos')}>Voltar</button>
          </div>
        </div>
      )}
    </div>
  )
}
