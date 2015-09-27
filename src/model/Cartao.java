package model;

import java.util.Calendar;

import controller.DataController;

public class Cartao {
	public String nome;
	public int diaVirada, diaVencimento;

	public Cartao(String nome, int diaVirada, int diaVencimento) {
		this.nome = nome;
		this.diaVirada = diaVirada;
		this.diaVencimento = diaVencimento;
	}

	/**
	 * Calcula a data que o cartao vai fazer a cobranca partir da data real em
	 * que a compra foi feita passada como um parametro String.
	 */
	public Calendar calcDataCobranca(String dataCompra) {
		Calendar cDataCobranca = DataController.stringToCalendar(dataCompra);

		/**
		 * Se a compra foi efetuada no dia da virada do cartao ou depois, ela
		 * soh serah cobrada na proxima fatura, ou seja...
		 */
		if (cDataCobranca.get(Calendar.DAY_OF_MONTH) >= diaVirada) {
			cDataCobranca.add(Calendar.MONTH, 1); // ... no proximo mes.
		}
		/**
		 * A compra eh cobrada no dia do vencimento do cartao
		 */
		cDataCobranca.set(Calendar.DAY_OF_MONTH, diaVencimento);

		return cDataCobranca;
	}

	/**
	 * Calcula a data que o cartao vai fazer a cobranca partir da data real em
	 * que a compra foi feita passada como um parametro Calendar.
	 */
	public Calendar calcDataCobranca(Calendar dataCompra) {
		/**
		 * Se a compra foi efetuada no dia da virada do cartao ou depois, ela
		 * soh serah cobrada na proxima fatura, ou seja...
		 */
		if (dataCompra.get(Calendar.DAY_OF_MONTH) >= diaVirada) {
			dataCompra.add(Calendar.MONTH, 1); // ... no proximo mes.
		}
		/**
		 * A compra eh cobrada no dia do vencimento do cartao
		 */
		dataCompra.set(Calendar.DAY_OF_MONTH, diaVencimento);

		return dataCompra;
	}
}