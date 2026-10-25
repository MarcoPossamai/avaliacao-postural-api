import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { listarMinhasAvaliacoes } from '../../services/avaliacaoService'
import { formatarData } from '../../lib/formatadores'
import './AvaliacoesAluno.css'

export default function AvaliacoesAluno() {
  const navigate = useNavigate()
  const [avaliacoes, setAvaliacoes] = useState([])
  const [carregando, setCarregando] = useState(true)
  const [erro, setErro] = useState('')

  useEffect(() => {
    listarMinhasAvaliacoes()
      .then((lista) => {
        const ordenadas = [...lista].sort((a, b) => b.dataAvaliacao.localeCompare(a.dataAvaliacao))
        setAvaliacoes(ordenadas)
      })
      .catch(() => setErro('Não foi possível carregar suas avaliações.'))
      .finally(() => setCarregando(false))
  }, [])

  return (
    <div className="avaliacoes-aluno-page">
      <h1>Minhas avaliações</h1>

      {erro && <p className="perfil-erro">{erro}</p>}
      {carregando && <p>Carregando...</p>}

      {!carregando && !erro && (
        <div className="avaliacoes-aluno-lista">
          {avaliacoes.length === 0 && <p>Nenhuma avaliação registrada ainda.</p>}
          {avaliacoes.map((avaliacao) => (
            <button
              type="button"
              key={avaliacao.id}
              className="avaliacao-aluno-item"
              onClick={() => navigate(`/aluno/avaliacoes/${avaliacao.id}`)}
            >
              <strong>{formatarData(avaliacao.dataAvaliacao)}</strong>
              {avaliacao.observacoes && <span>{avaliacao.observacoes}</span>}
            </button>
          ))}
        </div>
      )}
    </div>
  )
}
