import { useState } from "react"

export default function ModalNovoInstrutor({ onFechar, onSalvar }) {
  const [nome, setNome] = useState('')
  const [email, setEmail] = useState('')
  const [senha, setSenha] = useState('')
  const [telefone, setTelefone] = useState('')
  const [sexo, setSexo] = useState('F')
  const [dataNascimento, setDataNascimento] = useState('')
  const [cref, setCref] = useState('')
  const [erro, setErro] = useState('')
  const [salvando, setSalvando] = useState(false)

  async function handleSubmit(event) {
    event.preventDefault()
    setErro('')
    setSalvando(true)
    try {
      await onSalvar({ nome, email, senha, telefone, sexo, dataNascimento, cref })
    } catch {
      setErro('Não foi possível cadastrar o instrutor. Confira os dados e tente novamente.')
    } finally {
      setSalvando(false)
    }
  }

  return (
    <div className="modal-overlay">
      <div className="modal-caixa">
        <span className="modal-eyebrow">Novo cadastro</span>
        <h2>Adicionar instrutor</h2>
        <p className="modal-subtitulo">Preencha os dados para criar o acesso.</p>

        <form onSubmit={handleSubmit}>
          <div>
            <label htmlFor="nome">Nome completo</label>
            <input id="nome" value={nome} onChange={(e) => setNome(e.target.value)} placeholder="Nome do instrutor" required />
          </div>

          <div className="modal-linha">
            <div>
              <label htmlFor="email">E-mail</label>
              <input id="email" type="email" value={email} onChange={(e) => setEmail(e.target.value)} placeholder="instrutor@email.com" required />
            </div>
            <div>
              <label htmlFor="telefone">Telefone</label>
              <input id="telefone" value={telefone} onChange={(e) => setTelefone(e.target.value)} placeholder="(00) 00000-0000" required />
            </div>
          </div>

          <div className="modal-linha">
            <div>
              <label htmlFor="senha">Senha inicial</label>
              <input id="senha" type="password" value={senha} onChange={(e) => setSenha(e.target.value)} placeholder="mín. 8 caracteres" required minLength={8} />
            </div>
            <div>
              <label htmlFor="sexo">Sexo</label>
              <select id="sexo" value={sexo} onChange={(e) => setSexo(e.target.value)}>
                <option value="F">Feminino</option>
                <option value="M">Masculino</option>
                <option value="O">Outra</option>
              </select>
            </div>
          </div>

          <div className="modal-linha">
            <div>
              <label htmlFor="dataNascimento">Data de nascimento</label>
              <input id="dataNascimento" type="date" value={dataNascimento} onChange={(e) => setDataNascimento(e.target.value)} required />
            </div>
            <div>
              <label htmlFor="cref">CREF</label>
              <input id="cref" value={cref} onChange={(e) => setCref(e.target.value)} placeholder="000000-G/SC" required />
            </div>
          </div>

          {erro && <p className="modal-erro">{erro}</p>}

          <div className="modal-acoes">
            <button type="button" className="botao-secundario" onClick={onFechar}>Cancelar</button>
            <button type="submit" className="botao-primario" disabled={salvando}>
              {salvando ? 'Salvando...' : 'Salvar instrutor'}
            </button>
          </div>
        </form>
      </div>
    </div>
  )
}
