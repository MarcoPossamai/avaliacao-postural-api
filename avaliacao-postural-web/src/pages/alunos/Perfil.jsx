import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { useAuth } from '../../context/AuthContext'
import { obterMeuPerfil, editarMeuPerfil } from '../../services/alunoService'
import { calcularIdade, formatarSexo, formatarData } from '../../lib/formatadores'
import './Perfil.css'

export default function Perfil() {
  const { logout } = useAuth()
  const navigate = useNavigate()

  const [perfil, setPerfil] = useState(null)
  const [carregando, setCarregando] = useState(true)
  const [erro, setErro] = useState('')
  const [editando, setEditando] = useState(false)
  const [salvando, setSalvando] = useState(false)

  const [nome, setNome] = useState('')
  const [email, setEmail] = useState('')
  const [telefone, setTelefone] = useState('')
  const [sexo, setSexo] = useState('F')
  const [dataNascimento, setDataNascimento] = useState('')
  const [objetivos, setObjetivos] = useState('')
  const [observacoes, setObservacoes] = useState('')

  useEffect(() => {
    carregar()
  }, [])

  async function carregar() {
    setCarregando(true)
    setErro('')
    try {
      const dados = await obterMeuPerfil()
      setPerfil(dados)
      preencherFormulario(dados)
    } catch {
      setErro('Não foi possível carregar seu perfil.')
    } finally {
      setCarregando(false)
    }
  }

  function preencherFormulario(dados) {
    setNome(dados.nome)
    setEmail(dados.email)
    setTelefone(dados.telefone)
    setSexo(dados.sexo)
    setDataNascimento(dados.dataNascimento)
    setObjetivos(dados.objetivos ?? '')
    setObservacoes(dados.observacoes ?? '')
  }

  function cancelarEdicao() {
    preencherFormulario(perfil)
    setEditando(false)
    setErro('')
  }

  async function handleSalvar(event) {
    event.preventDefault()
    setErro('')
    setSalvando(true)
    try {
      const atualizado = await editarMeuPerfil({ nome, email, telefone, sexo, dataNascimento, objetivos, observacoes })
      setPerfil(atualizado)
      setEditando(false)
    } catch {
      setErro('Não foi possível salvar as alterações. Confira os dados e tente novamente.')
    } finally {
      setSalvando(false)
    }
  }

  function handleSair() {
    logout()
    navigate('/login')
  }

  if (carregando) {
    return <div className="perfil-page"><p>Carregando...</p></div>
  }

  return (
    <div className="perfil-page">
      <h1>Meu perfil</h1>

      {erro && <p className="perfil-erro">{erro}</p>}

      {!editando && perfil && (
        <div className="perfil-card">
          <div className="perfil-avatar">{perfil.nome?.[0]?.toUpperCase()}</div>
          <h2>{perfil.nome}</h2>
          <span className="perfil-subtitulo">{calcularIdade(perfil.dataNascimento)} anos • {formatarSexo(perfil.sexo)}</span>

          <div className="perfil-dados">
            <div>
              <label>E-mail</label>
              <span>{perfil.email}</span>
            </div>
            <div>
              <label>Telefone</label>
              <span>{perfil.telefone}</span>
            </div>
            <div>
              <label>Data de nascimento</label>
              <span>{formatarData(perfil.dataNascimento)}</span>
            </div>
            <div>
              <label>Objetivo</label>
              <span>{perfil.objetivos || '-'}</span>
            </div>
            <div>
              <label>Observações</label>
              <span>{perfil.observacoes || '-'}</span>
            </div>
          </div>

          <button type="button" className="botao-secundario" onClick={() => setEditando(true)}>Editar dados</button>
        </div>
      )}

      {editando && (
        <form className="perfil-card" onSubmit={handleSalvar}>
          <div>
            <label htmlFor="nome">Nome completo</label>
            <input id="nome" value={nome} onChange={(e) => setNome(e.target.value)} required />
          </div>
          <div>
            <label htmlFor="email">E-mail</label>
            <input id="email" type="email" value={email} onChange={(e) => setEmail(e.target.value)} required />
          </div>
          <div>
            <label htmlFor="telefone">Telefone</label>
            <input id="telefone" value={telefone} onChange={(e) => setTelefone(e.target.value)} required />
          </div>
          <div>
            <label htmlFor="sexo">Sexo</label>
            <select id="sexo" value={sexo} onChange={(e) => setSexo(e.target.value)}>
              <option value="F">Feminino</option>
              <option value="M">Masculino</option>
              <option value="O">Outra</option>
            </select>
          </div>
          <div>
            <label htmlFor="dataNascimento">Data de nascimento</label>
            <input id="dataNascimento" type="date" value={dataNascimento} onChange={(e) => setDataNascimento(e.target.value)} required />
          </div>
          <div>
            <label htmlFor="objetivos">Objetivo</label>
            <input id="objetivos" value={objetivos} onChange={(e) => setObjetivos(e.target.value)} />
          </div>
          <div>
            <label htmlFor="observacoes">Observações</label>
            <textarea id="observacoes" value={observacoes} onChange={(e) => setObservacoes(e.target.value)} rows={3} />
          </div>

          <div className="perfil-acoes-form">
            <button type="button" className="botao-secundario" onClick={cancelarEdicao}>Cancelar</button>
            <button type="submit" className="botao-primario" disabled={salvando}>
              {salvando ? 'Salvando...' : 'Salvar alterações'}
            </button>
          </div>
        </form>
      )}

      <button type="button" className="perfil-sair" onClick={handleSair}>Sair do sistema</button>
    </div>
  )
}
