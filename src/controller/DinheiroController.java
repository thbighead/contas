package controller;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class DinheiroController {
	public static DecimalFormat formatador = new DecimalFormat("#.##");

	public static double[] calcParcelas(double valor, int qtdParcelas) {
		double[] parcelas = new double[qtdParcelas];
		double parcela = formatDinheiro(valor / qtdParcelas);

		for (int i = 0; i < parcelas.length; i++) {
			parcelas[i] = parcela;
		}

		if (valor != (parcela * qtdParcelas)) {
			parcela = formatDinheiro(parcela + formatDinheiro(0.01));
			parcelas[qtdParcelas - 1] = parcela;
		}

		return parcelas;
	}

	public static double formatDinheiro(double valor) {
		return stringToDinheiro(dinheiroToString(valor));
	}
	
	public static String formatDinheiro(String valor) {
		return dinheiroToString(stringToDinheiro(valor));
	}

	public static String dinheiroToString(double valor) {
		formatador.setRoundingMode(RoundingMode.DOWN);
		return formatador.format(valor).replace(".", ",");
	}

	public static double stringToDinheiro(String valor) {
		valor = valor.replace(",", ".");
		return Double.valueOf(valor);
	}

	// public static void main(String[] args) {
	// String herp = "8000,01";
	// herp = formatDinheiro(herp);
	// double[] coiso = calcParcelas(stringToDinheiro(herp), 10);
	// System.out.println(dinheiroToString(34.567));
	// System.out.println(stringToDinheiro("-24,767"));
	// System.out.println(formatDinheiro(24.467));
	// System.out.println(formatDinheiro("24,467"));
	// for (double d : coiso) {
	// System.out.println(d);
	// }
	// }
}
