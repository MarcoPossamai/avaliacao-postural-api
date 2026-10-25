import { useState } from 'react'
import './ModalFotoSimetrografo.css'

const COLUNAS = 12
const LINHAS = 16

export default function ModalFotoSimetrografo({ foto, onFechar }) {
  const [ativo, setAtivo] = useState(true)
  const [centro, setCentro] = useState({ x: 50, y: 50 })

  function handleClickImagem(event) {
    if (!ativo) return
    const rect = event.currentTarget.getBoundingClientRect()
    const x = ((event.clientX - rect.left) / rect.width) * 100
    const y = ((event.clientY - rect.top) / rect.height) * 100
    setCentro({ x, y })
  }

  return (
    <div className="modal-overlay">
      <div className="modal-caixa modal-foto">
        <div className="modal-foto-topo">
          <button
            type="button"
            className={`botao-secundario ${ativo ? 'simetrografo-ativo' : ''}`}
            onClick={() => setAtivo(!ativo)}
          >
            {ativo ? 'Simetrógrafo ativo' : 'Simetrógrafo desativado'}
          </button>
          <button type="button" className="botao-secundario" onClick={() => setCentro({ x: 50, y: 50 })}>
            Centralizar
          </button>
          <button type="button" className="botao-secundario" onClick={onFechar}>Fechar</button>
        </div>

        <div className="simetrografo-container" onClick={handleClickImagem}>
          <img src={foto.blobUrl} alt="Foto da avaliação" />
          {ativo && (
            <div className="simetrografo-overlay">
              {Array.from({ length: COLUNAS - 1 }).map((_, i) => (
                <div key={`v-${i}`} className="simetrografo-grade-vertical" style={{ left: `${((i + 1) / COLUNAS) * 100}%` }} />
              ))}
              {Array.from({ length: LINHAS - 1 }).map((_, i) => (
                <div key={`h-${i}`} className="simetrografo-grade-horizontal" style={{ top: `${((i + 1) / LINHAS) * 100}%` }} />
              ))}
              <div className="simetrografo-vertical" style={{ left: `${centro.x}%` }} />
              <div className="simetrografo-horizontal-centro" style={{ top: `${centro.y}%` }} />
            </div>
          )}
        </div>

        <p className="simetrografo-dica">
          Clique na imagem para posicionar o centro do simetrógrafo sobre a linha média do corpo.
        </p>
      </div>
    </div>
  )
}
