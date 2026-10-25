import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { listarAlunos } from '../../services/alunoService'
import { listarAvaliacoesPorAluno } from '../../services/avaliacaoService'
import usePaginacao from '../../hooks/usePaginacao'
import Paginacao from '../../components/Paginacao'
import './Avaliacoes.css'

export default function Avaliacoes() {
  const [alunos, setAlunos] = useState([])
  const [contagemAvaliacoes, setContagemAvaliacoes] = useState({})
  const [buscaAluno, setBuscaAluno] = useState('')
  const [alunoSelecionado, setAlunoSelecionado] = useState(null)
  const [erro, setErro] = useState('')
  const navigate = useNavigate()

  useEffect(() => {
    listarAlunos()
      .then(async (dados) => {
        setAlunos(dados)
        const pares = await Promise.all(
          dados.map(async (a) => [a.id, (await listarAvaliacoesPorAluno(a.id)).length])
        )
        setContagemAvaliacoes(Object.fromEntries(pares))
      })
      .catch(() => setErro('Não foi possível carregar os alunos.'))
  }, [])

  const alunosFiltrados = buscaAluno
    ? alunos.filter((a) => a.nome.toLowerCase().includes(buscaAluno.toLowerCase()))
    : alunos

  const { itensPagina: alunosPagina, paginaAtual, totalPaginas, definirPagina } = usePaginacao(alunosFiltrados, 10)

  return (
    <div className="avaliacoes-page">
      <span className="avaliacoes-eyebrow">Avaliações</span>
      <h1>Avaliação postural e antropométrica</h1>

      {erro && <p className="avaliacoes-erro">{erro}</p>}

      {!alunoSelecionado && (
        <div className="avaliacao-card">
          <h2>Selecione o aluno</h2>
          <p>Escolha um aluno para criar uma nova avaliação ou consultar as já existentes.</p>
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
                className="avaliacao-item-aluno avaliacao-item-aluno-contagem"
                onClick={() => setAlunoSelecionado(aluno)}
              >
                <span>{aluno.nome}</span>
                <span className="avaliacao-contagem-badge">{contagemAvaliacoes[aluno.id] ?? 0} avaliação(ões)</span>
              </button>
            ))}
            {alunosFiltrados.length === 0 && <p>Nenhum aluno encontrado.</p>}
          </div>
          <Paginacao paginaAtual={paginaAtual} totalPaginas={totalPaginas} definirPagina={definirPagina} />
        </div>
      )}

      {alunoSelecionado && (
        <div className="avaliacao-card">
          <div className="avaliacao-aluno-selecionado">
            <div>
              <strong>{alunoSelecionado.nome}</strong>
              <span>Selecionado</span>
            </div>
            <button type="button" className="botao-secundario" onClick={() => setAlunoSelecionado(null)}>Trocar</button>
          </div>

          <div className="avaliacao-opcoes">
            <button
              type="button"
              className="botao-primario"
              onClick={() => navigate(`/avaliacoes/nova/${alunoSelecionado.id}`, { state: { nomeAluno: alunoSelecionado.nome } })}
            >
              + Criar nova avaliação
            </button>
            <button
              type="button"
              className="botao-secundario"
              onClick={() => navigate(`/avaliacoes/aluno/${alunoSelecionado.id}`, { state: { nomeAluno: alunoSelecionado.nome } })}
            >
              Consultar avaliações
            </button>
          </div>
        </div>
      )}
    </div>
  )
}
