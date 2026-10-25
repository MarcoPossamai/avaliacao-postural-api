import { useEffect, useState } from 'react'
import { listarExercicios, listarGruposMusculares, cadastrarExercicio } from '../../services/exercicioService'
import ModalNovoExercicio from './ModalNovoExercicio'
import usePaginacao from '../../hooks/usePaginacao'
import Paginacao from '../../components/Paginacao'
import './Exercicios.css'

export default function Exercicios() {
  const [exercicios, setExercicios] = useState([])
  const [grupos, setGrupos] = useState([])
  const [busca, setBusca] = useState('')
  const [carregando, setCarregando] = useState(true)
  const [erro, setErro] = useState('')
  const [modalAberto, setModalAberto] = useState(false)
  const [grupoFiltro, setGrupoFiltro] = useState('TODOS')

  useEffect(() => {
    carregar()
  }, [])

  async function carregar() {
    setCarregando(true)
    setErro('')
    try {
      const [listaExercicios, listaGrupos] = await Promise.all([
        listarExercicios(),
        listarGruposMusculares(),
      ])
      setExercicios(listaExercicios)
      setGrupos(listaGrupos)
    } catch {
      setErro('Não foi possível carregar os exercícios.')
    } finally {
      setCarregando(false)
    }
  }

  function nomeGrupo(grupoMuscularId) {
    return grupos.find((g) => g.id === grupoMuscularId)?.nome ?? '-'
  }

  async function handleSalvar(dados) {
    await cadastrarExercicio(dados)
    setModalAberto(false)
    await carregar()
  }

  const exerciciosFiltrados = exercicios
    .filter((e) => grupoFiltro === 'TODOS' || e.grupoMuscularId === Number(grupoFiltro))
    .filter((e) => e.nome.toLowerCase().includes(busca.toLowerCase()))
    .sort((a, b) => b.id - a.id)

  const { itensPagina: exerciciosPagina, paginaAtual, totalPaginas, definirPagina } = usePaginacao(exerciciosFiltrados, 10)

  return (
    <div className="exercicios-page">
      <div className="exercicios-topo">
        <span className="exercicios-eyebrow">Biblioteca de exercícios</span>
        <h1>Exercícios</h1>
        <p>Cadastre os exercícios usados na prescrição de treinos.</p>
      </div>

      <div className="exercicios-busca">
        <select value={grupoFiltro} onChange={(e) => setGrupoFiltro(e.target.value)} className="exercicios-filtro-grupo">
          <option value="TODOS">Todos os grupos</option>
          {grupos.map((grupo) => (
            <option key={grupo.id} value={grupo.id}>{grupo.nome}</option>
          ))}
        </select>
        <input type="text" placeholder="Buscar por nome..." value={busca} onChange={(e) => setBusca(e.target.value)} />
        <span>{exerciciosFiltrados.length} exercício(s) encontrado(s)</span>
        <button type="button" className="botao-primario" onClick={() => setModalAberto(true)}>+ Novo exercício</button>
      </div>

      {carregando && <p>Carregando...</p>}
      {erro && <p className="exercicios-erro">{erro}</p>}

      {!carregando && !erro && (
        <>
          <table className="exercicios-tabela">
            <thead>
              <tr>
                <th>Nome</th>
                <th>Grupo muscular</th>
              </tr>
            </thead>
            <tbody>
              {exerciciosPagina.map((exercicio) => (
                <tr key={exercicio.id}>
                  <td>{exercicio.nome}</td>
                  <td>{nomeGrupo(exercicio.grupoMuscularId)}</td>
                </tr>
              ))}
            </tbody>
          </table>

          <Paginacao paginaAtual={paginaAtual} totalPaginas={totalPaginas} definirPagina={definirPagina} />
        </>
      )}

      {modalAberto && (
        <ModalNovoExercicio grupos={grupos} onFechar={() => setModalAberto(false)} onSalvar={handleSalvar} />
      )}
    </div>
  )
}
