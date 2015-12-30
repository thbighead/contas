package model;

import java.util.Calendar;

import controller.DataController;
import controller.DinheiroController;

public class Transacao {
	public String categoria;
	public String descricao;
	public Calendar dataReal;
	public Calendar dataEfetiva;
	public Double valor;

	/**
	 * Construtores: dataReal e dataEfetiva aceitam valores do tipo String ou
	 * Calendar e os construtores aceitam qualquer combinacao desses tipos
	 */
	public Transacao(String categoria, String descricao, String dataReal,
			String dataEfetiva, double valor) {
		this.categoria = categoria;
		this.descricao = descricao;
		this.dataReal = DataController.stringToCalendar(dataReal);
		this.dataEfetiva = DataController.stringToCalendar(dataEfetiva);
		this.valor = valor;
	}

	public Transacao(String categoria, String descricao, Calendar dataReal,
			Calendar dataEfetiva, double valor) {
		this.categoria = categoria;
		this.descricao = descricao;
		this.dataReal = dataReal;
		this.dataEfetiva = dataEfetiva;
		this.valor = valor;
	}

	public Transacao(String categoria, String descricao, String dataReal,
			Calendar dataEfetiva, double valor) {
		this.categoria = categoria;
		this.descricao = descricao;
		this.dataReal = DataController.stringToCalendar(dataReal);
		this.dataEfetiva = dataEfetiva;
		this.valor = valor;
	}

	public Transacao(String categoria, String descricao, Calendar dataReal,
			String dataEfetiva, double valor) {
		this.categoria = categoria;
		this.descricao = descricao;
		this.dataReal = dataReal;
		this.dataEfetiva = DataController.stringToCalendar(dataEfetiva);
		this.valor = valor;
	}

	/**
	 * Parcela a instância da Transacao e retorna as transacoes novas num array
	 * de Transacao
	 */
	public Transacao[] parcelar(int qtdParcelas) {
		Calendar c = this.dataEfetiva;
		Transacao[] transacaoParcelada = new Transacao[qtdParcelas];
		double[] valores = DinheiroController.calcParcelas(this.valor,
				qtdParcelas);

		/**
		 * Adiciona as parcelas a descricao e atualiza a data efetiva de cada
		 * parcela
		 */
		for (int i = 0; i < valores.length; i++) {
			transacaoParcelada[i] = new Transacao(this.categoria,
					this.descricao + " " + qtdParcelas + "/" + (i + 1),
					this.dataReal, (Calendar) c.clone(), valores[i]);
			c.add(Calendar.MONTH, 1);
		}

		return transacaoParcelada;
	}

	/**
	 * Versao de parcelar com qtdParcelas do tipo String para tornar seu codigo
	 * menos verbose
	 */
	public Transacao[] parcelar(String qtdParcelas) {
		return parcelar(Integer.parseInt(qtdParcelas));
	}

	/**
	 * Faz as modificacoes para tornar essa instancia uma transacao ddo cartao
	 * passado como parametro
	 */
	public void transaCartao(Cartao cartao) {
		this.descricao = cartao.nome + "-" + this.descricao;
		this.dataEfetiva = cartao.calcDataCobranca(this.dataEfetiva);
	}

	/**
	 * Retorna em um array de Transacao as parcelas de uma transacao do cartao
	 * passado como parametro. As parcelas sao gravadas em ordem crescente no
	 * array
	 */
	public Transacao[] transaCartaoParcelada(Cartao cartao, int qtdParcelas) {
		/**
		 * Transforma a transacao numa transacao do cartao passado
		 */
		this.transaCartao(cartao);

		/**
		 * Divide o valor nas parcelas e coloca em ordem crescente no array
		 */
		Calendar c = this.dataEfetiva;
		Transacao[] transacaoParcelada = new Transacao[qtdParcelas];
		double[] valores = DinheiroController.calcParcelas(this.valor,
				qtdParcelas);

		/**
		 * Adiciona as parcelas a descricao e atualiza a data efetiva de cada
		 * parcela, calculando o primeiro dia util para cada data efetiva
		 */
		for (int i = 0; i < valores.length; i++) {
			transacaoParcelada[i] = new Transacao(this.categoria,
					this.descricao + " " + qtdParcelas + "/" + (i + 1),
					this.dataReal,
					DataController.primeiroDiaUtilAPartirDe((Calendar) c
							.clone()), valores[i]);
			c.add(Calendar.MONTH, 1);
		}

		return transacaoParcelada;
	}

	/**
	 * Versao de transaCartaoParcelada com qtdParcelas do tipo String para
	 * tornar seu codigo menos verbose
	 */
	public Transacao[] transaCartaoParcelada(Cartao cartao, String qtdParcelas) {
		return transaCartaoParcelada(cartao, Integer.parseInt(qtdParcelas));
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
