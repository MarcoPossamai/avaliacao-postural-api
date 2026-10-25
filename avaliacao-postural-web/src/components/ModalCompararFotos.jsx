import PainelFotoComGrade from './PainelFotoComGrade'
import './ModalCompararFotos.css'

export default function ModalCompararFotos({ fotoInicial, fotoFinal, legendaInicial, legendaFinal, onFechar }) {
  return (
    <div className="modal-overlay">
      <div className="modal-caixa modal-comparar-fotos">
        <div className="modal-comparar-fotos-topo">
          <h2>Comparar com simetrógrafo</h2>
          <button type="button" className="botao-secundario" onClick={onFechar}>Fechar</button>
        </div>

        <div className="modal-comparar-fotos-colunas">
          <PainelFotoComGrade foto={fotoInicial} legenda={legendaInicial} />
          <PainelFotoComGrade foto={fotoFinal} legenda={legendaFinal} />
        </div>

        <p className="simetrografo-dica">Clique em cada foto para posicionar a cruz na linha média do corpo. As duas grades são independentes.</p>
      </div>
    </div>
  )
}
