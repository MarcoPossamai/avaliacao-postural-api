import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { listarAlunos } from '../../services/alunoService'
import usePaginacao from '../../hooks/usePaginacao'
import Paginacao from '../../components/Paginacao'
import './Avaliacoes.css'

export default function Treinos() {
  const [alunos, setAlunos] = useState([])
  const [buscaAluno, setBuscaAluno] = useState('')
  const [alunoSelecionado, setAlunoSelecionado] = useState(null)
  const [erro, setErro] = useState('')
  const navigate = useNavigate()

  useEffect(() => {
    listarAlunos().then(setAlunos).catch(() => setErro('Não foi possível carregar os alunos.'))
  }, [])

  const alunosFiltrados = buscaAluno
    ? alunos.filter((a) => a.nome.toLowerCase().includes(buscaAluno.toLowerCase()))
    : alunos

  const { itensPagina: alunosPagina, paginaAtual, totalPaginas, definirPagina } = usePaginacao(alunosFiltrados, 10)

  return (
    <div className="avaliacoes-page">
      <span className="avaliacoes-eyebrow">Treinos</span>
      <h1>Prescrição de treino</h1>

      {erro && <p className="avaliacoes-erro">{erro}</p>}

      {!alunoSelecionado && (
        <div className="avaliacao-card">
          <h2>Selecione o aluno</h2>
          <p>Escolha um aluno para criar uma nova ficha de treino ou consultar as já existentes.</p>
          <input
            type="text"
            placeholder="Buscar aluno por nome..."
            value={buscaAluno}
            onChange={(e) => setBuscaAluno(e.target.value)}
          />
          <div className="avaliacao-lista-alunos avaliacao-lista-alunos-paginada">
            {alunosPagina.map((aluno) => (
              <button type="button" key={aluno.id} className="avaliacao-item-aluno" onClick={() => setAlunoSelecionado(aluno)}>
                {aluno.nome}
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
              onClick={() => navigate(`/treinos/nova/${alunoSelecionado.id}`, { state: { nomeAluno: alunoSelecionado.nome } })}
            >
              + Criar nova ficha
            </button>
            <button
              type="button"
              className="botao-secundario"
              onClick={() => navigate(`/treinos/aluno/${alunoSelecionado.id}`, { state: { nomeAluno: alunoSelecionado.nome } })}
            >
              Consultar fichas
            </button>
          </div>
        </div>
      )}
    </div>
  )
}
