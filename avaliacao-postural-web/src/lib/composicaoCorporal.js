const DOBRAS_PERCENTUAL_GORDURA = ["Tríceps", "Subescapular", "Suprailíaca", "Panturrilha"];

export function idadeNaData(dataNascimento, dataReferencia) {
  if (!dataNascimento || !dataReferencia) return null;
  const [anoNasc, mesNasc, diaNasc] = dataNascimento.split("-").map(Number);
  const [anoRef, mesRef, diaRef] = dataReferencia.split("-").map(Number);
  let idade = anoRef - anoNasc;
  if (mesRef < mesNasc || (mesRef === mesNasc && diaRef < diaNasc)) idade--;
  return idade;
}

export function calcularPercentualGordura({ sexo, idade, peso, altura, dobras }) {
  if ((sexo !== "M" && sexo !== "F") || idade == null || !peso || !altura) return null;

  const valores = DOBRAS_PERCENTUAL_GORDURA.map((nome) => Number(dobras[nome]));
  if (valores.some((valor) => !valor || valor <= 0)) return null;
  const soma = valores.reduce((total, valor) => total + valor, 0);

  const densidade =
    sexo === "M"
      ? 1.10726863 - 0.00081201 * soma + 0.00000212 * soma ** 2 - 0.00041761 * idade
      : 1.029002361 - 0.00067159 * soma + 0.00000242 * soma ** 2 - 0.00026073 * idade
        - 0.00056009 * peso + 0.00054649 * altura * 100;

  return (4.95 / densidade - 4.5) * 100;
}
