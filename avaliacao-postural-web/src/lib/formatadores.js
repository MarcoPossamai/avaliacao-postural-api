export function calcularIdade(dataNascimento){
    if (!dataNascimento) return null
    const hoje = new Date()
    const nascimento = new Date(dataNascimento)
    let idade = hoje.getFullYear() - nascimento.getFullYear()
    const aindaNaoFezAniversario = hoje.getMonth() < nascimento.getMonth() || 
        (hoje.getMonth() === nascimento.getMonth() && hoje.getDate() < nascimento.getDate())
    if(aindaNaoFezAniversario) idade--
    return idade
}

export function formatarSexo(sexo){
    if(!sexo) return '-'
    const mapa = {M: 'Masculino', F: 'Feminino', O: 'Outra'}
    return mapa[sexo] ?? sexo
}

export function formatarData(dataIso) {
    if (!dataIso) return '-'
    const [ano, mes, dia] = dataIso.split('-')
    return `${dia}/${mes}/${ano}`
}
