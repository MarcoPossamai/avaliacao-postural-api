import { useEffect, useState } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import { obterDetalheAvaliacao } from '../../services/avaliacaoService'
import { buscarArquivoFotografia } from '../../services/fotografiaService'
import { formatarData } from '../../lib/formatadores'
import '../instrutor/Avaliacoes.css'
import '../instrutor/NovaFicha.css'
import './AvaliacoesAluno.css'

const REGIOES = [
  { value: 'PES', label: 'Pés' },
  { value: 'TIBIA', label: 'Tíbia' },
  { value: 'JOELHO', label: 'Joelho' },
  { value: 'QUADRIL_PELVE', label: 'Quadril/Pelve' },
  { value: 'ESCAPULA', label: 'Escápula' },
  { value: 'OMBRO', label: 'Ombro' },
  { value: 'COLUNA', label: 'Coluna' },
  { value: 'CABECA', label: 'Cabeça' },
]

const SEVERIDADES = [
  { value: 'LEVE', label: 'Leve' },
  { value: 'MODERADA', label: 'Moderada' },
  { value: 'ACENTUADA', label: 'Acentuada' },
]

function labelRegiao(codigo) {
  return REGIOES.find((r) => r.value === codigo)?.label ?? codigo
}

function labelSeveridade(codigo) {
  return SEVERIDADES.find((s) => s.value === codigo)?.label ?? codigo
}

export default function AvaliacaoDetalheAluno() {
  const { avaliacaoId } = useParams()
  const navigate = useNavigate()
  const [detalhe, setDetalhe] = useState(null)
  const [fotos, setFotos] = useState([])
  const [carregando, setCarregando] = useState(true)
  const [erro, setErro] = useState('')

  useEffect(() => {
    carregar()
  }, [avaliacaoId])

  async function carregar() {
    setCarregando(true)
    setErro('')
    try {
      const dados = await obterDetalheAvaliacao(avaliacaoId)
      setDetalhe(dados)
      const fotosComBlob = await Promise.all(
        dados.fotografias.map(async (foto) => ({
          ...foto,
          blobUrl: await buscarArquivoFotografia(avaliacaoId, foto.id),
        }))
      )
      setFotos(fotosComBlob)
    } catch {
      setErro('Não foi possível carregar essa avaliação.')
    } finally {
      setCarregando(false)
    }
  }

  if (carregando) {
    return <div className="avaliacoes-aluno-page"><p>Carregando...</p></div>
  }

  if (erro || !detalhe) {
    return (
      <div className="avaliacoes-aluno-page">
        <p className="perfil-erro">{erro || 'Avaliação não encontrada.'}</p>
        <button type="button" className="botao-secundario" onClick={() => navigate('/aluno/avaliacoes')}>Voltar</button>
      </div>
    )
  }

  const { avaliacao, medidaCorporal, desvios } = detalhe

  return (
    <div className="avaliacoes-aluno-page">
      <span className="avaliacoes-eyebrow">Avaliação</span>
      <h1>{formatarData(avaliacao.dataAvaliacao)}</h1>
      {avaliacao.observacoes && <p className="avaliacao-aluno-observacao">{avaliacao.observacoes}</p>}

      {medidaCorporal && (
        <div className="avaliacao-card">
          <h2>Medidas</h2>
          <div className="avaliacao-grid">
            <div><label>Peso</label><span>{medidaCorporal.peso ?? '-'} kg</span></div>
            <div><label>Altura</label><span>{medidaCorporal.altura ?? '-'} m</span></div>
            <div><label>IMC</label><span>{medidaCorporal.imc?.toFixed(1) ?? '-'}</span></div>
            <div><label>% Gordura</label><span>{medidaCorporal.percentualGordura ?? '-'}%</span></div>
          </div>

          {medidaCorporal.circunferencias?.length > 0 && (
            <>
              <h3>Circunferências</h3>
              <div className="avaliacao-grid">
                {medidaCorporal.circunferencias.map((c, i) => (
                  <div key={i}><label>{c.tipo}</label><span>{c.valorCm} cm</span></div>
                ))}
              </div>
            </>
          )}
        </div>
      )}

      {desvios?.length > 0 && (
        <div className="avaliacao-card">
          <h2>Desvios posturais</h2>
          <div className="ficha-desvios-lista">
            {desvios.map((d) => (
              <span key={d.id} className="ficha-desvio-tag">
                {labelRegiao(d.regiao)} — {d.tipo} ({labelSeveridade(d.severidade)})
              </span>
            ))}
          </div>
        </div>
      )}

      {fotos.length > 0 && (
        <div className="avaliacao-card">
          <h2>Fotografias</h2>
          <div className="avaliacao-fotos-grid">
            {fotos.map((foto) => (
              <div key={foto.id} className="avaliacao-foto-item">
                <img src={foto.blobUrl} alt="Foto da avaliação" />
              </div>
            ))}
          </div>
        </div>
      )}

      <button type="button" className="botao-secundario avaliacao-aluno-voltar" onClick={() => navigate('/aluno/avaliacoes')}>Voltar</button>
    </div>
  )
}
