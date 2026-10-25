import './Paginacao.css'

export default function Paginacao({ paginaAtual, totalPaginas, definirPagina }) {
  if (totalPaginas <= 1) return null

  return (
    <div className="paginacao">
      <button type="button" className="botao-secundario" onClick={() => definirPagina(paginaAtual - 1)} disabled={paginaAtual === 1}>
        ← Anterior
      </button>
      <span>Página {paginaAtual} de {totalPaginas}</span>
      <button type="button" className="botao-secundario" onClick={() => definirPagina(paginaAtual + 1)} disabled={paginaAtual === totalPaginas}>
        Próxima →
      </button>
    </div>
  )
}
