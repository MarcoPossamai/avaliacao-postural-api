import { useState } from 'react'
import './ModalEscolherFotos.css'

export default function ModalEscolherFotos({ fotosIniciais, fotosFinais, legendaInicial, legendaFinal, onConfirmar, onFechar }) {
  const [fotoInicial, setFotoInicial] = useState(null)
  const [fotoFinal, setFotoFinal] = useState(null)

  return (
    <div className="modal-overlay">
      <div className="modal-caixa modal-escolher-fotos">
        <div className="modal-escolher-fotos-topo">
          <h2>Escolher fotos para comparar</h2>
          <button type="button" className="botao-secundario" onClick={onFechar}>Fechar</button>
        </div>

        <div className="modal-escolher-fotos-corpo">
          <div className="modal-escolher-fotos-colunas">
            <div>
              <h3>{legendaInicial}</h3>
              <div className="escolher-fotos-grid">
                {fotosIniciais.map((f) => (
                  <button
                    type="button"
                    key={f.id}
                    className={`escolher-foto-item ${fotoInicial?.id === f.id ? 'escolher-foto-selecionada' : ''}`}
                    onClick={() => setFotoInicial(f)}
                  >
                    <img src={f.blobUrl} alt="Foto inicial" />
                  </button>
                ))}
                {fotosIniciais.length === 0 && <p>Sem fotos.</p>}
              </div>
            </div>

            <div>
              <h3>{legendaFinal}</h3>
              <div className="escolher-fotos-grid">
                {fotosFinais.map((f) => (
                  <button
                    type="button"
                    key={f.id}
                    className={`escolher-foto-item ${fotoFinal?.id === f.id ? 'escolher-foto-selecionada' : ''}`}
                    onClick={() => setFotoFinal(f)}
                  >
                    <img src={f.blobUrl} alt="Foto final" />
                  </button>
                ))}
                {fotosFinais.length === 0 && <p>Sem fotos.</p>}
              </div>
            </div>
          </div>
        </div>

        <div className="modal-escolher-fotos-rodape">
          <button
            type="button"
            className="botao-primario"
            disabled={!fotoInicial || !fotoFinal}
            onClick={() => onConfirmar(fotoInicial, fotoFinal)}
          >
            Comparar fotos selecionadas
          </button>
        </div>
      </div>
    </div>
  )
}
