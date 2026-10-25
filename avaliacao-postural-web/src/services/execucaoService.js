import api from '../lib/api'

export async function registrarExecucao(fichaId, dados) {
  const response = await api.post(`/api/fichas/${fichaId}/execucoes`, dados)
  return response.data
}

export async function listarMinhasExecucoes() {
  const response = await api.get('/api/alunos/me/execucoes')
  return response.data
}
