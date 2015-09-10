package controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;

public class DataController {
	/**
	 * Para evitar um erro estranho (provavelmente do Apache POI) onde se
	 * comecarmos pelo indice 0 (primeira linha) o codigo simplesmente nao
	 * conseguia percorrer a planilha mesmo com o incremento feito no indice,
	 * comecamos pelo indice 1 (segunda linha) e por isso essa constante estah
	 * aqui. Caso queira redefinir por onde deve comecar a percorrer a planilha,
	 * mude o valor desta constante
	 */
	private final static int indice0 = 1;
	/**
	 * formato de data esperado (dd/mm/aaaa).
	 */
	final static DateFormat formatador = DateFormat
			.getDateInstance(DateFormat.MEDIUM);
	/**
	 * lista com todos os feriados que importam para transacoes bancarias no
	 * Brasil
	 */
	final static String[] datasFeriado = { "01/01", "21/04", "23/04", "01/05",
			"07/09", "12/10", "02/11", "15/11", "20/11", "25/12" };

	/**
	 * Lista todas as datas reais (coluna 0) das transacoes cadastradas na
	 * planilha pasta atual e devolve num ArrayList<String>
	 */
	public static ArrayList<String> listar() {
		int i = indice0;
		Calendar c = Calendar.getInstance();
		ArrayList<String> datas = new ArrayList<String>();
		XSSFRow row = TransacaoController.planilhaPasta.getRow(i);
		XSSFCell cell;

		while (row != null) {
			cell = row.getCell(0);
			c.setTime(cell.getDateCellValue());
			if (!datas.contains(DataController.calendarToString(c))) {
				datas.add(DataController.calendarToString(c));
			}
			i++;
			row = TransacaoController.planilhaPasta.getRow(i);
		}

		return datas;
	}

	/**
	 * Lista todas as datas reais (coluna 0) das transacoes cadastradas na
	 * planilha pasta atual e devolve num String[]. O parametro Object nothing
	 * serve apenas para manter o nome "listar" para este metodo, nothing nao eh
	 * usado pelo metodo, portanto fique a vontade de passar o que quiser como
	 * parametro. Valor recomendado para nothing: null
	 */
	public static String[] listar(Object nothing) {
		ArrayList<String> datas = listar();

		return datas.toArray(new String[datas.size()]);
	}

	/**
	 * Retorna o primeiro dia util encontrado a partir da data passada.
	 * Parametro e retorno sao do tipo Calendar
	 */
	public static Calendar primeiroDiaUtilAPartirDe(Calendar data) {
		Calendar dataUtil = data;
		while ((dataUtil.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
				|| (dataUtil.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
				|| (ehFeriado(dataUtil))) {
			dataUtil.add(Calendar.DAY_OF_MONTH, 1);
		}

		return dataUtil;
	}

	/**
	 * Retorna o primeiro dia util encontrado a partir da data passada.
	 * Parametro e retorno sao do tipo String
	 */
	public static String primeiroDiaUtilAPartirDe(String data) {
		return calendarToString(primeiroDiaUtilAPartirDe(stringToCalendar(data)));
	}

	/**
	 * Testa a data (tipo: Calendar) e retorna verdadeiro caso ela seja um
	 * feriado contido dentro da lista de feriados (datasFeriado) desta classe
	 */
	public static boolean ehFeriado(Calendar data) {
		String strData = calendarToString(data);
		String strFeriado;

		for (String feriado : datasFeriado) {
			strFeriado = putAno(feriado, data.get(Calendar.YEAR));
			if (strFeriado.equals(strData)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Testa a data (tipo: String) e retorna verdadeiro caso ela seja um feriado
	 * contido dentro da lista de feriados (datasFeriado) desta classe
	 */
	public static boolean ehFeriado(String data) {
		String strFeriado;

		for (String feriado : datasFeriado) {
			strFeriado = putAno(feriado,
					stringToCalendar(data).get(Calendar.YEAR));
			if (strFeriado.equals(data)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Encontra o dia da semana = diaDaSemana de ocorrencia pos no mes = mes e
	 * no ano = ano
	 */
	public static int dayOfWeekInMonth(int pos, String diaDaSemana, int mes,
			int ano) {
		Calendar c = Calendar.getInstance();

		/**
		 * Valida o valor int para mes, lembrando que para Calendar mes = 0
		 * equivale a janeiro, o que nao eh nada intuitivo. Para resolver isso,
		 * qualquer valor colocado entre 1 e 12 (inclusive) eh subtraido de 1,
		 * entrando no padrao para a classe Calendar. Caso um valor invalido
		 * seja colocado, mes terah valor 0 (zero)
		 */
		if ((mes > 0) && (mes <= 12)) {
			mes--;
		} else if ((mes < 0) || (mes > 12)) {
			mes = 0;
		}

		/**
		 * Valores negativos para ano fazem com que o seja escolhido o valor do
		 * ano atual
		 */
		if (ano <= 0) {
			ano = getAnoAtual();
		}

		// seta a data de c para o primeiro dia do mes e ano passados
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.MONTH, mes);
		c.set(Calendar.YEAR, ano);
		/**
		 * seta o dia de c para a primeira ocorrencia de diaDaSemana a partir da
		 * data jah setada em c (primeiro dia do mes = mes no ano = ano)
		 */
		c.set(Calendar.DAY_OF_WEEK, dayOfWeekValue(diaDaSemana));

		/**
		 * soh eh garantido que um dado dia da semana apareca 4 vezes em um mes.
		 * Portanto pos nao pode ter valores negativos nem maiores que 4
		 */
		if ((pos > 4) || (pos < 1)) {
			pos = 0;
		} else {
			pos--;
		}

		// soma 7 dias a data de c pos vezes
		for (int i = 0; i < pos; i++) {
			c.add(Calendar.DAY_OF_MONTH, 7);
		}

		return c.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Retorna o valor int equivalente ao dia da semana passado como parametro
	 * para a classe Calendar. São aceitos todos os formatos de escrita para o
	 * dia da semana: separados por hifen ou espaco, apreviados em 3 letras, com
	 * e sem acentuacao e cedilha. O retorno padrao eh o de domingo, portanto
	 * qualquer string que nao se enquadre nos padroes aceitos dos outros 6 dias
	 * da semana serah retornarah um resultado para domingo
	 */
	public static int dayOfWeekValue(String diaDaSemana) {
		diaDaSemana = diaDaSemana.toLowerCase();

		if ((diaDaSemana == "seg") || (diaDaSemana == "segunda")
				|| (diaDaSemana == "segunda feira")
				|| (diaDaSemana == "segunda-feira")) {
			return Calendar.MONDAY;
		}
		if ((diaDaSemana == "ter") || (diaDaSemana == "terça")
				|| (diaDaSemana == "terça feira")
				|| (diaDaSemana == "terça-feira") || (diaDaSemana == "terca")
				|| (diaDaSemana == "terca feira")
				|| (diaDaSemana == "terca-feira")) {
			return Calendar.TUESDAY;
		}
		if ((diaDaSemana == "qua") || (diaDaSemana == "quarta")
				|| (diaDaSemana == "quarta feira")
				|| (diaDaSemana == "quarta-feira")) {
			return Calendar.WEDNESDAY;
		}
		if ((diaDaSemana == "qui") || (diaDaSemana == "quinta")
				|| (diaDaSemana == "quinta feira")
				|| (diaDaSemana == "quinta-feira")) {
			return Calendar.THURSDAY;
		}
		if ((diaDaSemana == "sex") || (diaDaSemana == "sexta")
				|| (diaDaSemana == "sexta feira")
				|| (diaDaSemana == "sexta-feira")) {
			return Calendar.FRIDAY;
		}
		if ((diaDaSemana == "sáb") || (diaDaSemana == "sab")
				|| (diaDaSemana == "sábado") || (diaDaSemana == "sabado")) {
			return Calendar.SATURDAY;
		}

		return Calendar.SUNDAY;
	}

	/**
	 * Transforma um valor inteiro em um String de 3 caracteres acentuada
	 * equivalente ao dia da semana para aquele valor numa planilha do Excel. O
	 * valor padrao eh "dom", portanto qualquer valor invalido (ou 1) serah lido
	 * como "dom".
	 */
	public static String valueToDayOfWeek(int value) {
		switch (value) {
		case 2:
			return "seg";
		case 3:
			return "ter";
		case 4:
			return "qua";
		case 5:
			return "qui";
		case 6:
			return "sex";
		case 7:
			return "sáb";
		default:
			return "dom";
		}
	}

	/**
	 * Coloca o ano na data dada (data deve ser uma String no formato "dd/mm")
	 */
	public static String putAno(String data, int ano) {
		return data + "/" + ano;
	}

	/**
	 * Retorna o valor int do ano atual
	 */
	public static int getAnoAtual() {
		Calendar hoje = Calendar.getInstance();

		return hoje.get(Calendar.YEAR);
	}

	/**
	 * Transforma um Calendar data num String data jah formatado pelo formato de
	 * formatador
	 */
	public static String calendarToString(Calendar data) {
		return "" + formatador.format(data.getTime());
	}

	/**
	 * Transforma um String data (jah formatado pelo formato de formatador) num
	 * Calendar data
	 */
	public static Calendar stringToCalendar(String data) {
		Calendar ret = Calendar.getInstance();
		try {
			ret.setTime(formatador.parse(data));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * Formata uma String data
	 */
	public static String formataData(String data) {
		return calendarToString(stringToCalendar(data));
	}

	// public static void main(String[] args) {
	// Calendar c = Calendar.getInstance();
	// System.out.println(calendarToString(c));
	// c = stringToCalendar("12/10/2010");
	// System.out.println(calendarToString(c));
	// System.out.println(ehFeriado(c));
	// c = primeiroDiaUtilDepoisDe(c);
	// System.out.println(calendarToString(c));
	// }
}
