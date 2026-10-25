import api from '../lib/api'

export async function registrarMedida(avaliacaoId, dados) {
    const response = await api.post(`/api/avaliacoes/${avaliacaoId}/medida`, dados)
    return response.data
}

export async function editarMedida(avaliacaoId, dados) {
    const response = await api.put(`/api/avaliacoes/${avaliacaoId}/medida`, dados)
    return response.data
}
