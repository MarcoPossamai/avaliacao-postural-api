import { useState } from 'react'
import { cadastrarInstrutor } from '../../services/instrutorService'
import ModalNovoInstrutor from './ModalNovoInstrutor'
import ModalSucesso from '../../components/ModalSucesso'
import './Alunos.css'
import './Avaliacoes.css'

export default function Instrutores() {
  const [modalAberto, setModalAberto] = useState(false)
  const [mostrarSucesso, setMostrarSucesso] = useState(false)
  const [erro, setErro] = useState('')

  async function handleSalvar(dados) {
    try {
      await cadastrarInstrutor(dados)
      setModalAberto(false)
      setMostrarSucesso(true)
    } catch (e) {
      setErro('Não foi possível cadastrar o instrutor.')
      throw e
    }
  }

  return (
    <div className="alunos-page">
      <div className="alunos-topo">
        <span className="alunos-eyebrow">Gestão de instrutores</span>
        <h1>Instrutores</h1>
        <p>Cadastre outros instrutores com acesso ao sistema.</p>
      </div>

      {erro && <p className="alunos-erro">{erro}</p>}

      <div className="avaliacao-card">
        <h2>Novo instrutor</h2>
        <p>Crie um acesso para outro profissional usar o sistema.</p>
        <button type="button" className="botao-primario" onClick={() => setModalAberto(true)}>+ Novo instrutor</button>
      </div>

      {modalAberto && (
        <ModalNovoInstrutor onFechar={() => setModalAberto(false)} onSalvar={handleSalvar} />
      )}

      {mostrarSucesso && (
        <ModalSucesso
          titulo="Instrutor cadastrado!"
          mensagem="O novo instrutor já pode fazer login no sistema com o e-mail e senha cadastrados."
          onFechar={() => setMostrarSucesso(false)}
        />
      )}
    </div>
  )
}
