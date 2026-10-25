export default function ModalConfirmacao({titulo, mensagem, onConfirmar, onCancelar, confirmando}){
    return (
        <div className="modal-overlay">
            <div className="modal-caixa modal-confirmacao">
                <h2>{titulo}</h2>
                <p>{mensagem}</p>
                <div className="modal-acoes">
                    <button type="button" className="botao-secundario" onClick={onCancelar}>Cancelar</button>
                    <button type="button" className="botao-perigo" onClick={onConfirmar} disabled={confirmando}>
                        {confirmando ? 'Removendo...' : 'Remover'}
                    </button>
                </div>
            </div>
        </div>
    )
}