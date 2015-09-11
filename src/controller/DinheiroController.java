package controller;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class DinheiroController {
	/**
	 * formato de valores monetarios esperado '...00000.00'
	 */
	public static DecimalFormat formatador = new DecimalFormat("#.##");

	/**
	 * Calcula os valores das parcelas fazendo com que divisoes quebradas deixem
	 * os ultimos centavos para a ultima parcela. Todas as parcelas sao
	 * retornadas num double[] em ordem.
	 */
	public static double[] calcParcelas(double valor, int qtdParcelas) {
		double[] parcelas = new double[qtdParcelas]; // array de retorno
		/**
		 * Divide o valor e o formata, neste momento, pode ser que a divisao
		 * resulte em valores com mais de duas casas decimais, fazendo com que a
		 * soma dos valores apos a formatacao nao resulte no valor total da
		 * compra antes de parcelada.
		 */
		double parcela = formatDinheiro(valor / qtdParcelas);

		for (int i = 0; i < parcelas.length; i++) {
			parcelas[i] = parcela;
		}

		/**
		 * Testa se o valor de todas as parcelas somadas eh igual ao valor
		 * original e atualiza o ultimo valor do array caso nao sejam.
		 */
		if (valor != (parcela * qtdParcelas)) {
			parcela = formatDinheiro(valor - parcela * (qtdParcelas - 1));
			parcelas[qtdParcelas - 1] = parcela;
		}

		return parcelas;
	}

	/**
	 * Formata um valor double qualquer com o formatador colocando-o no padrao
	 * '...00000.00' e devolve o resultado em double. O arredondamento dos
	 * valores eh feito para baixo sempre.
	 */
	public static double formatDinheiro(double valor) {
		return stringToDinheiro(dinheiroToString(valor));
	}

	/**
	 * Formata um valor String qualquer com o formatador colocando-o no padrao
	 * '...00000.00' e devolve o resultado em String. O arredondamento dos
	 * valores eh feito para baixo sempre.
	 */
	public static String formatDinheiro(String valor) {
		return dinheiroToString(stringToDinheiro(valor));
	}

	/**
	 * Formata um valor double qualquer para o padrao '...00000.00' e o converte
	 * para uma String, trocando '.' por ','
	 */
	public static String dinheiroToString(double valor) {
		formatador.setRoundingMode(RoundingMode.DOWN);
		return formatador.format(valor).replace(".", ",");
	}

	/**
	 * Troca ',' por '.' numa String e a converte para um double. Este valor nao
	 * eh formatado para o padrao.
	 */
	public static double stringToDinheiro(String valor) {
		valor = valor.replace(",", ".");
		return Double.valueOf(valor);
	}

	// public static void main(String[] args) {
	// String herp = "847,36";
	// herp = formatDinheiro(herp);
	// double[] coiso = calcParcelas(stringToDinheiro(herp), 7);
	// System.out.println(dinheiroToString(34.567));
	// System.out.println(stringToDinheiro("-24,767"));
	// System.out.println(formatDinheiro(24.467));
	// System.out.println(formatDinheiro("24,467"));
	// for (double d : coiso) {
	// System.out.println(d);
	// }
	// }
}
