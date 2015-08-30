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

	public Calendar calcDataCobranca(String dataCompra) {
		System.out.println("A data original era: " + dataCompra);
		Calendar cDataCobranca = DataController.stringToCalendar(dataCompra);

		if (cDataCobranca.get(Calendar.DAY_OF_MONTH) >= diaVirada) {
			cDataCobranca.add(Calendar.MONTH, 1);
		}
		cDataCobranca.set(Calendar.DAY_OF_MONTH, diaVencimento);
		
		System.out.println("A data final ficou: " + DataController.calendarToString(cDataCobranca));

		return cDataCobranca;
	}
}