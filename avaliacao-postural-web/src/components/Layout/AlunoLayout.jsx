import { NavLink, Outlet } from "react-router-dom";
import { useAuth } from "../../context/AuthContext";
import './AlunoLayout.css'

export default function AlunoLayout(){
    const { usuario } = useAuth()

    return (
        <div className="aluno-layout">
            <header className="aluno-header">
                <span>Olá, {usuario?.nome}</span>
            </header>

            <main className="aluno-content">
                <Outlet/>
            </main>

            <nav className="aluno-bottom-nav">
                <NavLink to="/aluno" end>Início</NavLink>
                <NavLink to="/aluno/avaliacoes">Avaliações</NavLink>
                <NavLink to="/aluno/treino">Treino</NavLink>
                <NavLink to="/aluno/evolucao">Evolução</NavLink>
                <NavLink to="/aluno/perfil">Perfil</NavLink>
            </nav>
        </div>
    )
}