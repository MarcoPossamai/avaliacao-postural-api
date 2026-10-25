import { useEffect, useState } from 'react'
import { useNavigate, useParams, useLocation } from 'react-router-dom'
import { listarAvaliacoesPorAluno } from '../../services/avaliacaoService'
import './Avaliacoes.css'

export default function ConsultarAvaliacoes() {
  const { alunoId } = useParams()
  const location = useLocation()
  const navigate = useNavigate()
  const [avaliacoes, setAvaliacoes] = useState([])
  const [carregando, setCarregando] = useState(true)
  const [erro, setErro] = useState('')

  const nomeAluno = location.state?.nomeAluno

  useEffect(() => {
    listarAvaliacoesPorAluno(alunoId)
      .then(setAvaliacoes)
      .catch(() => setErro('Não foi possível carregar as avaliações.'))
      .finally(() => setCarregando(false))
  }, [alunoId])

  return (
    <div className="avaliacoes-page">
      <span className="avaliacoes-eyebrow">Avaliações</span>
      <h1>{nomeAluno ? `Avaliações de ${nomeAluno}` : 'Avaliações do aluno'}</h1>

      {erro && <p className="avaliacoes-erro">{erro}</p>}
      {carregando && <p>Carregando...</p>}

      {!carregando && !erro && (
        <div className="avaliacao-card">
          {avaliacoes.length === 0 && <p>Nenhuma avaliação registrada ainda.</p>}
          <div className="avaliacao-lista-alunos">
            {avaliacoes.map((avaliacao) => (
              <button
                type="button"
                key={avaliacao.id}
                className="avaliacao-item-aluno"
                onClick={() => navigate(`/avaliacoes/${avaliacao.id}`)}
              >
                {avaliacao.dataAvaliacao}
                {avaliacao.observacoes && ` — ${avaliacao.observacoes}`}
              </button>
            ))}
          </div>
          <div className="avaliacao-acoes">
            <button type="button" className="botao-secundario" onClick={() => navigate('/avaliacoes')}>Voltar</button>
          </div>
        </div>
      )}
    </div>
  )
}
