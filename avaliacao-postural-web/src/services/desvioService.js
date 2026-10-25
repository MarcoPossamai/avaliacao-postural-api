import api from '../lib/api'

export async function registrarDesvios(avaliacaoId, desvios) {
  const response = await api.post(`/api/avaliacoes/${avaliacaoId}/desvios`, { desvios })
  return response.data
}

export async function listarDesvios(avaliacaoId) {
  const response = await api.get(`/api/avaliacoes/${avaliacaoId}/desvios`)
  return response.data
}

export async function removerDesvio(avaliacaoId, desvioId) {
  await api.delete(`/api/avaliacoes/${avaliacaoId}/desvios/${desvioId}`)
}
