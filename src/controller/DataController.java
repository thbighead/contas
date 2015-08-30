package controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;

public class DataController {
	final static DateFormat formatador = DateFormat
			.getDateInstance(DateFormat.MEDIUM);
	final static String[] datasFeriado = { "01/01", "21/04", "23/04", "01/05",
			"07/09", "12/10", "02/11", "15/11", "20/11", "25/12" };

	public static String[] listar() {
		int i = 1;
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

		return datas.toArray(new String[datas.size()]);
	}

	public static Calendar primeiroDiaUtilDepoisDe(Calendar data) {
		Calendar dataUtil = data;
		while ((dataUtil.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
				|| (dataUtil.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
				|| (ehFeriado(dataUtil))) {
			dataUtil.add(Calendar.DAY_OF_MONTH, 1);
		}

		return dataUtil;
	}

	public static boolean ehFeriado(Calendar data) {
		String strData = calendarToString(data);
		String strFeriado;

		for (String feriado : datasFeriado) {
			strFeriado = putAno(feriado, data.get(Calendar.YEAR));
			if (strFeriado.contentEquals(strData)) {
				return true;
			}
		}

		return false;
	}

	public static int dayOfWeekInMonth(int pos, String diaDaSemana, int mes,
			int ano) {
		Calendar c = Calendar.getInstance();

		if (mes > 0) {
			mes--;
		} else if ((mes < 0) || (mes > 12)) {
			mes = 0;
		}

		if (ano <= 0) {
			ano = getAnoAtual();
		}

		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.MONTH, mes);
		c.set(Calendar.YEAR, ano);
		c.set(Calendar.DAY_OF_WEEK, dayOfWeekValue(diaDaSemana));

		if ((pos > 3) || (pos < 1)) {
			pos = 0;
		} else {
			pos--;
		}

		for (int i = 0; i < pos; i++) {
			c.add(Calendar.DAY_OF_MONTH, 7);
		}

		return c.get(Calendar.DAY_OF_MONTH);
	}

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

	public static String putAno(String data, int ano) {
		return data + "/" + ano;
	}

	public static int getAnoAtual() {
		Calendar hoje = Calendar.getInstance();

		return hoje.get(Calendar.YEAR);
	}

	public static String calendarToString(Calendar data) {
		return "" + formatador.format(data.getTime());
	}

	public static Calendar stringToCalendar(String data) {
		Calendar ret = Calendar.getInstance();
		try {
			ret.setTime(formatador.parse(data));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
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
