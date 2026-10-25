import api from '../lib/api'

export async function enviarFotografia(avaliacaoId, arquivo) {
    const formData = new FormData()
    formData.append('arquivo', arquivo)
    const response = await api.post(`/api/avaliacoes/${avaliacaoId}/fotografias`, formData)
    return response.data
}

export async function removerFotografia(avaliacaoId, fotografiaId) {
    await api.delete(`/api/avaliacoes/${avaliacaoId}/fotografias/${fotografiaId}`)
}

export async function buscarArquivoFotografia(avaliacaoId, fotografiaId) {
    const response = await api.get(`/api/avaliacoes/${avaliacaoId}/fotografias/${fotografiaId}/arquivo`,{
        responseType: 'blob',
    })
    return URL.createObjectURL(response.data)
}

export async function listarFotografias(avaliacaoId) {
  const response = await api.get(`/api/avaliacoes/${avaliacaoId}/fotografias`)
  return response.data
}
