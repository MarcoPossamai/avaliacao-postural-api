import api from '../lib/api'

export async function prescreverFicha(alunoId, dados) {
  const response = await api.post(`/api/alunos/${alunoId}/fichas`, dados)
  return response.data
}

export async function listarFichasPorAluno(alunoId) {
  const response = await api.get(`/api/alunos/${alunoId}/fichas`)
  return response.data
}

export async function editarFicha(fichaId, dados) {
  const response = await api.put(`/api/fichas/${fichaId}`, dados)
  return response.data
}

export async function listarMinhasFichas() {
  const response = await api.get('/api/alunos/me/fichas')
  return response.data
}
