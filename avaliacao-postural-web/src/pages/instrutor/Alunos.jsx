import { useEffect, useState } from "react"
import { cadastrarAluno, listarAlunos, removerAluno } from "../../services/alunoService"
import { calcularIdade, formatarSexo } from "../../lib/formatadores"
import ModalNovoAluno from "./ModalNovoAluno"
import './Alunos.css'
import ModalConfirmacao from "../../components/ModalConfirmacao"
import { useNavigate } from "react-router-dom"
import usePaginacao from "../../hooks/usePaginacao"
import Paginacao from "../../components/Paginacao"

function iniciais(nome){
    const partes = nome.trim().split(' ')
    const primeira = partes[0]?.[0] ?? ''
    const ultima = partes.length > 1 ? partes[partes.length - 1][0] : ''
    return (primeira + ultima).toUpperCase()
}

export default function Alunos(){
    const [alunos, setAlunos] = useState([])
    const [carregando, setCarregando] = useState(true)
    const [erro, setErro] = useState('')
    const [busca, setBusca] = useState('')
    const [modalAberto, setModalAberto] = useState(false)
    const [alunoParaRemover, setAlunoParaRemover] = useState(null)
    const [removendo, setRemovendo] = useState(false)
    const navigate = useNavigate()

    useEffect(() => {
        carregarAlunos()
    }, [])

    async function carregarAlunos() {
        setCarregando(true)
        setErro('')
        try {
            const dados = await listarAlunos()
            setAlunos(dados)
        } catch {
            setErro('Não foi possível carregar os alunos.')
        } finally {
            setCarregando(false)
        }
    }

    async function handleSalvar(dadosFormulario) {
        await cadastrarAluno(dadosFormulario)
        setModalAberto(false)
        await carregarAlunos()
    }

    async function confirmarRemocao(){
        setRemovendo(true)
        try {
            await removerAluno(alunoParaRemover.id)
            setAlunoParaRemover(null)
            await carregarAlunos()
        } finally {
            setRemovendo(false)
        }
    }

    const alunosFiltrados = alunos.filter((aluno) => {
        const termo = busca.toLowerCase()
        return aluno.nome.toLowerCase().includes(termo) || (aluno.objetivos ?? '').toLowerCase().includes(termo)
    })

    const { itensPagina: alunosPagina, paginaAtual, totalPaginas, definirPagina } = usePaginacao(alunosFiltrados, 10)

    return (
        <div className="alunos-page">
            <div className="alunos-topo">            
                <span className="alunos-eyebrow">Gestão de alunos</span>
                <h1>Alunos</h1>
                <p>Cadastre, encontre e acompanhe seus alunos.</p>
            </div>

            <div className="alunos-busca">
                <input type="text" placeholder="Buscar por nome ou objetivo..." value={busca} onChange={(e) => setBusca(e.target.value)}/>
                <span>{alunosFiltrados.length} aluno(s) encontrado(s)</span>
                <button type="button" className="botao-primario" onClick={() => setModalAberto(true)}>+ Novo Aluno</button>
            </div>

            {carregando && <p>Carregando...</p>}
            {erro && <p className="alunos-erro">{erro}</p>}

            {!carregando && !erro && (
                <>
                    <table className="alunos-tabela">
                        <thead>
                            <tr>
                                <th>Aluno</th>
                                <th>E-mail</th>
                                <th>Telefone</th>
                                <th>Objetivo</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            {alunosPagina.map((aluno) => (
                                <tr key={aluno.id} className="alunos-linha-clicavel" onClick={() => navigate(`/alunos/${aluno.id}`)}>
                                    <td>
                                        <div className="alunos-nome-celula">
                                            <span className="alunos-avatar">{iniciais(aluno.nome)}</span>
                                            <div>
                                                <strong>{aluno.nome}</strong>
                                                <span>{calcularIdade(aluno.dataNascimento)} anos • {formatarSexo(aluno.sexo)}</span>
                                            </div>
                                        </div>
                                    </td>
                                    <td>{aluno.email}</td>
                                    <td>{aluno.telefone}</td>
                                    <td>{aluno.objetivos || '-'}</td>
                                    <td>
                                        <button type="button" className="botao-remover" onClick={(e) => { e.stopPropagation(); setAlunoParaRemover(aluno) }}>Remover</button>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>

                    <Paginacao paginaAtual={paginaAtual} totalPaginas={totalPaginas} definirPagina={definirPagina} />
                </>
            )}

            {modalAberto && (
                <ModalNovoAluno onFechar={() => setModalAberto(false)} onSalvar={handleSalvar} />
            )}

            {alunoParaRemover && (
                <ModalConfirmacao
                    titulo="Remover aluno"
                    mensagem={`Tem certeza que deseja remover ${alunoParaRemover.nome}? Essa ação não pode ser desfeita.`}
                    confirmando={removendo}
                    onConfirmar={confirmarRemocao}
                    onCancelar={() => setAlunoParaRemover(null)}
                />
            )}
        </div>
    )
}