import { useState } from "react"

const OBJETIVOS = ['Correção postural', 'Mobilidade', 'Fortalecimento', 'Performance']

export default function ModalNovoAluno({onFechar, onSalvar}){
    const [nome, setNome] = useState('')
    const [email, setEmail] = useState('')
    const [senha, setSenha] = useState('')
    const [telefone, setTelefone] = useState('')
    const [sexo, setSexo] = useState('F')
    const [dataNascimento, setDataNascimento] = useState('')
    const [objetivos, setObjetivos] = useState(OBJETIVOS[0])
    const [observacoes, setObservacoes] = useState('')
    const [erro, setErro] = useState('')
    const [salvando, setSalvando] = useState(false)

    async function handleSubmit(event) {
        event.preventDefault()
        setErro('')
        setSalvando(true)
        try {
            await onSalvar({nome, email, senha, telefone, sexo, dataNascimento, objetivos, observacoes})
        } catch {
            setErro('Não foi possível cadastrar o aluno. Confira os dados e tente novamente.')
        } finally {
            setSalvando(false)
        }
    }

    return(
        <div className="modal-overlay">
            <div className="modal-caixa">
                <span className="modal-eyebrow">Novo cadastro</span>
                <h2>Adicionar aluno</h2>
                <p className="modal-subtitulo">Preencha os dados básicos para criar o perfil.</p>

                <form onSubmit={handleSubmit}>
                    <div>
                        <label htmlFor="nome">Nome completo</label>
                        <input id="nome" value={nome} onChange={(e) => setNome(e.target.value)} placeholder="Nome do aluno" required />
                    </div>
                    <div>
                        <label htmlFor="observacoes">Observacoes</label>
                        <textarea id="observacoes" value={observacoes} onChange={(e) => setObservacoes(e.target.value)} placeholder="Observações gerais sobre o aluno (opcional)" rows={3}></textarea>
                    </div>
                    
                    <div className="modal-linha">
                        <div>
                            <label htmlFor="email">E-mail</label>
                            <input id="email" type="email" value={email} onChange={(e) => setEmail(e.target.value)} placeholder="aluno@email.com" required/>
                        </div>
                        <div>
                            <label htmlFor="telefone">Telefone</label>
                            <input id="telefone" value={telefone} onChange={(e) => setTelefone(e.target.value)} placeholder="(00) 00000-0000" required />
                        </div>
                    </div>

                    <div className="modal-linha">
                        <div>
                            <label htmlFor="senha">Senha inicial</label>
                            <input id="senha" type="password" value={senha} onChange={(e) => setSenha (e.target.value)} placeholder="mín. 8 caracteres" required minLength={8}/>
                        </div>
                        <div>
                            <label htmlFor="sexo">Sexo</label>
                            <select id="sexo" value={sexo} onChange={(e) => setSexo(e.target.value)}>
                                <option value="F">Feminino</option>
                                <option value="M">Masculino</option>
                                <option value="O">Outra</option>
                            </select>
                        </div>
                    </div>

                    <div className="modal-linha">
                        <div>
                            <label htmlFor="dataNascimento">Data de Nascimento</label>
                            <input id="dataNascimento" type="date" value={dataNascimento} onChange={(e) => setDataNascimento(e.target.value)} required />
                        </div>
                        <div>
                            <label htmlFor="objetivos">Objetivo principal</label>
                            <select id="objetivos" value={objetivos} onChange={(e) => setObjetivos(e.target.value)}>
                                {OBJETIVOS.map((o) => <option key={o} value={o}>{o}</option>)}
                            </select>
                        </div>
                    </div>

                    {erro && <p className="modal-erro">{erro}</p>}

                    <div className="modal-acoes">
                        <button type="button" className="botao-secundario" onClick={onFechar}>Cancelar</button>
                        <button type="submit" className="botao-primario" disabled={salvando}>
                            {salvando ? 'Salvando...' : 'Salvar aluno'}
                        </button>
                    </div>
                </form>
            </div>
        </div>
    )
}