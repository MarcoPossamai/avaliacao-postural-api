import { useState } from "react";
import { useAuth } from "../../context/AuthContext";
import { useNavigate } from "react-router-dom";
import { login as loginRequest } from "../../services/authService";
import logoImg from "../../assets/logo.jpg";
import "./Login.css";

export default function Login() {
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const [erro, setErro] = useState("");
  const { login } = useAuth();
  const navigate = useNavigate();

  async function handleSubmit(event) {
    event.preventDefault();
    setErro("");
    try {
      const dados = await loginRequest(email, senha);
      login(dados.token, { perfil: dados.perfil, nome: dados.nome });
      navigate(dados.perfil === 'ALUNO' ? '/aluno' : '/');
    } catch {
      setErro("E-mail ou senha inválidos");
    }
  }

  return (
    <div className="login-page">
      <div className="login-panel-form">
        <div className="login-header">
          <div className="login-logo-block">
            <img src={logoImg} alt="Academia Ritmo Vital" className="login-logo"/>
            <span className="login-eyebrow">Acesso ao sistema</span>
          </div>

          <h1>Bem-vindo de volta</h1>
          <p className="login-subtitle">Acompanhe avaliações, evolução e treinos em um só lugar.</p>
        </div>
        <form onSubmit={handleSubmit}>
          <label htmlFor="email">E-mail</label>
          <input
            id="email"
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            placeholder="seu@email.com"
          />

          <label htmlFor="senha">Senha</label>
          <input
            id="senha"
            type="password"
            value={senha}
            onChange={(e) => setSenha(e.target.value)}
            placeholder="••••••••"
          />

          {erro && <p className="login-erro">{erro}</p>}

          <button type="submit">Entrar no sistema</button>
        </form>

        <span className="login-footer">
          Seus dados são protegidos e tratados com segurança
        </span>
      </div>

      <div className="login-panel-hero">
        <span className="login-hero-eyebrow">Academia Ritmo Vital</span>
        <h2>Movimento, cuidado e evolução</h2>
        <p>Tecnologia a serviço de um acompanhamento mais preciso e humano.</p>
        <div className="login-hero-stats">
          <div>
            <strong>360°</strong>
            <span>Visão da evolução</span>
          </div>
          <div>
            <strong>100%</strong>
            <span>Treinos revisados</span>
          </div>
        </div>
      </div>
    </div>
  );
}
