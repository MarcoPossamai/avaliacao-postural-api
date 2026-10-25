import api from '../lib/api'

export async function listarAlunos() {
    const response = await api.get('/api/alunos')
    return response.data.sort((a, b) => a.nome.localeCompare(b.nome, 'pt-BR'))
}

export async function cadastrarAluno(dados){
    const response = await api.post('/api/alunos', dados)
    return response.data
}

export async function removerAluno(id) {
    await api.delete(`/api/alunos/${id}`)
}

export async function obterMeuPerfil() {
    const response = await api.get('/api/alunos/me')
    return response.data
}

export async function editarMeuPerfil(dados) {
    const response = await api.put('/api/alunos/me', dados)
    return response.data
}
