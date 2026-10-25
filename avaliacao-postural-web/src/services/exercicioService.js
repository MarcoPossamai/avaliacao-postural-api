import api from '../lib/api'

export async function listarExercicios() {
  const response = await api.get('/api/exercicios')
  return response.data
}

export async function listarGruposMusculares() {
  const response = await api.get('/api/grupos-musculares')
  return response.data
}

export async function cadastrarExercicio(dados) {
  const response = await api.post('/api/exercicios', dados)
  return response.data
}
