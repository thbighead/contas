package model;

import java.util.Calendar;

import controller.DataController;
import controller.DinheiroController;

public class Transacao {
	public String categoria;
	public String descricao;
	public Calendar data;
	public Double valor;

	public Transacao(String categoria, String descricao, String data,
			double valor) {
		this.categoria = categoria;
		this.descricao = descricao;
		this.data = DataController.stringToCalendar(data);
		this.valor = valor;
	}

	public Transacao[] parcelar(int qtdParcelas) {
		Calendar c = this.data;
		Transacao[] transacaoParcelada = new Transacao[qtdParcelas];
		double[] valores = DinheiroController.calcParcelas(this.valor,
				qtdParcelas);

		for (int i = 0; i < valores.length; i++) {
			transacaoParcelada[i] = new Transacao(this.categoria,
					this.descricao + " " + qtdParcelas + "/" + (i + 1),
					DataController.calendarToString(c), valores[i]);
			c.add(Calendar.MONTH, 1);
		}

		return transacaoParcelada;
	}

	public void transaCartao(Cartao cartao) {
		this.descricao = cartao.nome + "-" + this.descricao;
		this.data = cartao.calcDataCobranca(DataController
				.calendarToString(this.data));
	}

	// public static void main(String[] args) {
	// Transacao t = new Transacao("aehooooooooo", "teste", "12/04/2015", 23.4);
	//
	// System.out.println(t.categoria);
	// System.out.println(t.descricao);
	// System.out.println(DataController.calendarToString(t.data));
	// System.out.println(t.valor);
	// Cartao c = new Cartao("merdacard", 10, 15);
	//
	// t.transaCartao(c);
	//
	// System.out.println(t.categoria);
	// System.out.println(t.descricao);
	// System.out.println(DataController.calendarToString(t.data));
	// System.out.println(t.valor);
	//
	// System.out.println(t.seTransParcelada());
	//
	// Transacao[] parcelas = t.parcelar(5);
	//
	// for (Transacao parcela : parcelas) {
	// System.out.println(parcela.categoria);
	// System.out.println(parcela.descricao);
	// System.out.println(DataController.calendarToString(parcela.data));
	// System.out.println(parcela.valor);
	// System.out.println(parcela.seTransParcelada());
	// }
	//
	// System.out.println(parcelas[2].quantasParcelas());
	// }
}
