import api from '../lib/api'

export async function obterMeuPerfil() {
    const response = await api.get('/api/instrutores/me')
    return response.data
}

export async function editarMeuPerfil(dados) {
    const response = await api.put('/api/instrutores/me', dados)
    return response.data
}

export async function cadastrarInstrutor(dados) {
    const response = await api.post('/api/instrutores', dados)
    return response.data
}
