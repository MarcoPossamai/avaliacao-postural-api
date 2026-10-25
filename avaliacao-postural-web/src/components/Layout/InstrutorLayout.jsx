import { NavLink, Outlet, useNavigate } from "react-router-dom";
import { useAuth } from "../../context/AuthContext";
import './InstrutorLayout.css'

export default function InstrutorLayout(){
    const { usuario, logout } = useAuth()
    const navigate = useNavigate()

    function handleLogout(){
        logout()
        navigate('/login')
    }

    return (
        <div className="instrutor-layout">
            <aside className="instrutor-sidebar">
                <div className="instrutor-sidebar-header">
                    <strong>Ritmo Vital</strong>
                    <span>Avaliação & Movimento</span>
                </div>

                <nav className="instrutor-nav">
                    <NavLink to="/" end>Visão geral</NavLink>
                    <NavLink to="/alunos">Alunos</NavLink>
                    <NavLink to="/avaliacoes">Avaliações</NavLink>
                    <NavLink to="/treinos">Treinos</NavLink>
                    <NavLink to="/exercicios">Exercícios</NavLink>
                    <NavLink to="/relatorios">Relatórios</NavLink>
                    <NavLink to="/comparacao">Comparação</NavLink>
                    <NavLink to="/instrutores">Instrutores</NavLink>
                </nav>

                <div className="instrutor-sidebar-footer">
                    <span className="instrutor-sidebar-nome" onClick={() => navigate('/perfil')}>{usuario?.nome}</span>
                    <button type="button" onClick={handleLogout}>Sair</button>
                </div>

            </aside>

            <main className="instrutor-content">
                <Outlet/>
            </main>
        </div>
    )
}