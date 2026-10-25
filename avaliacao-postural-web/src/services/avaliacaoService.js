import api from '../lib/api'

export async function criarAvaliacao(alunoId, dados) {
    const response = await api.post(`/api/alunos/${alunoId}/avaliacoes`, dados)
    return response.data
}

export async function listarAvaliacoesPorAluno(alunoId) {
    const response = await api.get(`/api/alunos/${alunoId}/avaliacoes`)
    return response.data
}

export async function listarMinhasAvaliacoes() {
    const response = await api.get('/api/alunos/me/avaliacoes')
    return response.data
}

export async function obterDetalheAvaliacao(avaliacaoId) {
    const response = await api.get(`/api/avaliacoes/${avaliacaoId}`)
    return response.data
}

export async function compararAvaliacoes(inicialId, finalId) {
    const response = await api.get('/api/avaliacoes/comparar', { params: { inicial: inicialId, final: finalId } })
    return response.data
}

export async function editarAvaliacao(avaliacaoId, dados) {
    const response = await api.put(`/api/avaliacoes/${avaliacaoId}`, dados)
    return response.data
}

export async function removerAvaliacao(avaliacaoId) {
    await api.delete(`/api/avaliacoes/${avaliacaoId}`)
}
