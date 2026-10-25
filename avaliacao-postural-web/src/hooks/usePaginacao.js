import { useState, useEffect } from 'react'

export default function usePaginacao(itens, itensPorPagina = 10) {
  const [pagina, setPagina] = useState(1)

  useEffect(() => {
    setPagina(1)
  }, [itens.length])

  const totalPaginas = Math.max(1, Math.ceil(itens.length / itensPorPagina))
  const paginaAtual = Math.min(pagina, totalPaginas)
  const inicio = (paginaAtual - 1) * itensPorPagina
  const itensPagina = itens.slice(inicio, inicio + itensPorPagina)

  return { itensPagina, paginaAtual, totalPaginas, definirPagina: setPagina }
}
