import { useState } from 'react'

export default function ModalNovoExercicio({ grupos, onFechar, onSalvar }) {
  const [nome, setNome] = useState('')
  const [descricao, setDescricao] = useState('')
  const [grupoMuscularId, setGrupoMuscularId] = useState(grupos[0]?.id ?? '')
  const [erro, setErro] = useState('')
  const [salvando, setSalvando] = useState(false)

  async function handleSubmit(event) {
    event.preventDefault()
    setErro('')
    setSalvando(true)
    try {
      await onSalvar({ nome, descricao, grupoMuscularId: Number(grupoMuscularId) })
    } catch {
      setErro('Não foi possível cadastrar o exercício.')
    } finally {
      setSalvando(false)
    }
  }

  return (
    <div className="modal-overlay">
      <div className="modal-caixa">
        <span className="modal-eyebrow">Novo cadastro</span>
        <h2>Adicionar exercício</h2>
        <p className="modal-subtitulo">Preencha os dados do exercício.</p>

        <form onSubmit={handleSubmit}>
          <div>
            <label htmlFor="nome">Nome</label>
            <input id="nome" value={nome} onChange={(e) => setNome(e.target.value)} placeholder="Nome do exercício" required />
          </div>

          <div>
            <label htmlFor="grupoMuscularId">Grupo muscular</label>
            <select id="grupoMuscularId" value={grupoMuscularId} onChange={(e) => setGrupoMuscularId(e.target.value)} required>
              {grupos.map((g) => <option key={g.id} value={g.id}>{g.nome}</option>)}
            </select>
          </div>

          <div>
            <label htmlFor="descricao">Descrição (opcional)</label>
            <textarea id="descricao" value={descricao} onChange={(e) => setDescricao(e.target.value)} rows={3} placeholder="Como executar o exercício" />
          </div>

          {erro && <p className="modal-erro">{erro}</p>}

          <div className="modal-acoes">
            <button type="button" className="botao-secundario" onClick={onFechar}>Cancelar</button>
            <button type="submit" className="botao-primario" disabled={salvando}>
              {salvando ? 'Salvando...' : 'Salvar exercício'}
            </button>
          </div>
        </form>
      </div>
    </div>
  )
}
