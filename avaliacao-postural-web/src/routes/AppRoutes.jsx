import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'
import Login from '../pages/Login/Login'
import RotaProtegida from './RotaProtegida'
import InstrutorLayout from '../components/Layout/InstrutorLayout'
import VisaoGeral from '../pages/instrutor/VisaoGeral'
import Alunos from '../pages/instrutor/Alunos'
import Avaliacoes from '../pages/instrutor/Avaliacoes'
import Treinos from '../pages/instrutor/Treinos'
import Relatorios from '../pages/instrutor/Relatorios'
import AlunoLayout from '../components/Layout/AlunoLayout'
import Inicio from '../pages/alunos/Inicio'
import AvaliacoesAluno from '../pages/alunos/Avaliacoes'
import TreinoAluno from '../pages/alunos/Treino'
import Evolucao from '../pages/alunos/Evolucao'
import Perfil from '../pages/alunos/Perfil'
import ConsultarAvaliacoes from '../pages/instrutor/ConsultarAvaliacoes'
import NovaAvaliacao from '../pages/instrutor/NovaAvaliacao'
import AvaliacaoDetalhe from '../pages/instrutor/AvaliacaoDetalhe'
import Exercicios from '../pages/instrutor/Exercicios'
import NovaFicha from '../pages/instrutor/NovaFicha'
import ConsultarFichas from '../pages/instrutor/ConsultarFichas'
import FichaDetalhe from '../pages/instrutor/FichaDetalhe'
import AvaliacaoDetalheAluno from '../pages/alunos/AvaliacaoDetalhe'
import Comparacao from '../pages/instrutor/Comparacao'
import AlunoDetalhe from '../pages/instrutor/AlunoDetalhe'
import PerfilInstrutor from '../pages/instrutor/Perfil'
import Instrutores from '../pages/instrutor/Instrutores'

export default function AppRoutes(){
    return(
        <BrowserRouter>
            <Routes>
                <Route path="/login" element={<Login />}/>
                
                <Route path='/' element={
                    <RotaProtegida perfilPermitido="INSTRUTOR">
                        <InstrutorLayout/>
                    </RotaProtegida>
                }>
                    <Route index element={<VisaoGeral/>}></Route>
                    <Route path="alunos" element={<Alunos/>}></Route>
                    <Route path="alunos" element={<Alunos/>}></Route>
                    <Route path="alunos/:alunoId" element={<AlunoDetalhe/>}></Route>
                    <Route path="avaliacoes" element={<Avaliacoes/>}></Route>
                    <Route path="avaliacoes/nova/:alunoId" element={<NovaAvaliacao/>}></Route>
                    <Route path="avaliacoes/aluno/:alunoId" element={<ConsultarAvaliacoes/>}></Route>
                    <Route path="avaliacoes/:avaliacaoId" element={<AvaliacaoDetalhe/>}></Route>
                    <Route path="avaliacoes/:avaliacaoId/editar" element={<NovaAvaliacao/>}></Route>
                    <Route path="treinos" element={<Treinos/>}></Route>
                    <Route path="treinos/nova/:alunoId" element={<NovaFicha/>}></Route>
                    <Route path="treinos/aluno/:alunoId" element={<ConsultarFichas/>}></Route>
                    <Route path="treinos/ficha/:fichaId" element={<FichaDetalhe/>}></Route>
                    <Route path="exercicios" element={<Exercicios/>}></Route>
                    <Route path="relatorios" element={<Relatorios/>}></Route>
                    <Route path="comparacao" element={<Comparacao/>}></Route>
                    <Route path="instrutores" element={<Instrutores/>}></Route>
                    <Route path="perfil" element={<PerfilInstrutor/>}></Route>
                </Route>

                <Route path="/aluno" element={
                    <RotaProtegida perfilPermitido="ALUNO">
                        <AlunoLayout/>
                    </RotaProtegida>
                }>
                    <Route index element={<Inicio/>}></Route>
                    <Route path="avaliacoes" element={<AvaliacoesAluno/>}></Route>
                    <Route path="avaliacoes/:avaliacaoId" element={<AvaliacaoDetalheAluno/>}></Route>
                    <Route path="treino" element={<TreinoAluno/>}></Route>
                    <Route path="evolucao" element={<Evolucao/>}></Route>
                    <Route path="perfil" element={<Perfil/>}></Route>
                </Route>

                <Route path="*" element={<Navigate to="/login"/>}/>
            </Routes>
        </BrowserRouter>
    )
}