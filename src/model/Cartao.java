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

		if (diaVirada > diaVencimento) {
			cDataCobranca.add(Calendar.MONTH, 1);
			if ((cDataCobranca.get(Calendar.MONTH) == Calendar.FEBRUARY)
					&& (Integer.parseInt(dataCompra.substring(0, 2)) > 27)) {
				cDataCobranca.set(Calendar.DAY_OF_MONTH, 28);
			}
		}

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
		 * Essas instancias de Calendar funcionam como ponteiros, para evitar
		 * que duas variaveis Calendar apontem para o mesmo objeto, foi
		 * necessario instanciar uma nova e copiar a data da outra. Isso evita o
		 * bug em que as duas datas (real e efetiva) sejam alteradas mesmo
		 * quando o esperado eh que apenas uma seja.
		 */
		Calendar dataCobranca = Calendar.getInstance();
		dataCobranca.setTime(dataCompra.getTime());

		if (diaVirada > diaVencimento) {
			dataCobranca.add(Calendar.MONTH, 1);
			if ((dataCobranca.get(Calendar.MONTH) == Calendar.FEBRUARY)
					&& (dataCompra.get(Calendar.DAY_OF_MONTH) > 27)) {
				dataCobranca.set(Calendar.DAY_OF_MONTH, 28);
			}
		}

		/**
		 * Se a compra foi efetuada no dia da virada do cartao ou depois, ela
		 * soh serah cobrada na proxima fatura, ou seja...
		 */
		if (dataCobranca.get(Calendar.DAY_OF_MONTH) >= diaVirada) {
			dataCobranca.add(Calendar.MONTH, 1); // ... no proximo mes.
		}
		/**
		 * A compra eh cobrada no dia do vencimento do cartao
		 */
		dataCobranca.set(Calendar.DAY_OF_MONTH, diaVencimento);

		return dataCobranca;
	}
}