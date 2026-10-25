import api from '../lib/api'

export async function obterMeuRelatorio(inicio, fim) {
  const response = await api.get('/api/alunos/me/relatorio-evolucao', { params: { inicio, fim } })
  return response.data
}

export async function obterRelatorioAluno(alunoId, inicio, fim) {
  const response = await api.get(`/api/alunos/${alunoId}/relatorio-evolucao`, { params: { inicio, fim } })
  return response.data
}
