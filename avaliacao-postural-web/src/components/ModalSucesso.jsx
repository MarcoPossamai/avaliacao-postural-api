export default function ModalSucesso({ titulo, mensagem, onFechar }) {
  return (
    <div className="modal-overlay">
      <div className="modal-caixa modal-confirmacao">
        <h2>{titulo}</h2>
        <p>{mensagem}</p>
        <div className="modal-acoes">
          <button type="button" className="botao-primario" onClick={onFechar}>Fechar</button>
        </div>
      </div>
    </div>
  )
}
