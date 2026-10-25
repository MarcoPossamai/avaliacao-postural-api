import { Navigate } from 'react-router-dom'
import { useAuth } from "../context/AuthContext";

export default function RotaProtegida({perfilPermitido, children}){
    const { usuario } = useAuth()

    if(!usuario){
        return <Navigate to="/login" replace />
    }

    if(perfilPermitido && usuario.perfil !== perfilPermitido){
        const destino = usuario.perfil === 'ALUNO' ? '/aluno' : '/'
        return <Navigate to={destino} replace/>
    }

    return children
}