package controller;

import java.util.ArrayList;

import model.Cartao;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class CartaoController {
	public static Integer[] dias = { null, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
			12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28 };
	public static XSSFSheet planilha = PlanilhaController.arq_base
			.getSheet("cartoes");

	public static void cadastrar(Cartao novo) {
		ArrayList<String> listaTodosCartoes = listar();
		if (!listaTodosCartoes.contains(novo.nome)) {
			XSSFRow novaLinha = planilha
					.createRow(planilha.getLastRowNum() + 1);
			XSSFCellStyle estiloCell = planilha.getRow(1).getCell(0)
					.getCellStyle();

			novaLinha.createCell(0);
			novaLinha.getCell(0).setCellStyle(estiloCell);
			novaLinha.getCell(0).setCellValue(novo.nome);
			novaLinha.createCell(1);
			estiloCell = planilha.getRow(1).getCell(1).getCellStyle();
			novaLinha.getCell(1).setCellStyle(estiloCell);
			novaLinha.getCell(1).setCellValue(novo.diaVirada);
			novaLinha.createCell(2);
			estiloCell = planilha.getRow(1).getCell(2).getCellStyle();
			novaLinha.getCell(2).setCellStyle(estiloCell);
			novaLinha.getCell(2).setCellValue(novo.diaVencimento);

			PlanilhaController.salvarBase();
		}
	}

	private static ArrayList<String> listar() {
		int i = 1;
		ArrayList<String> colCartoes = new ArrayList<String>();
		XSSFRow row = planilha.getRow(i);
		XSSFCell cell;

		while (row != null) {
			cell = row.getCell(0);
			colCartoes.add(cell.getStringCellValue());
			i++;
			row = planilha.getRow(i);
		}

		return colCartoes;
	}

	public static String[] listar(int diaVirada, int diaVencimento) {
		int i = 1;
		ArrayList<String> colCartoes = new ArrayList<String>();
		XSSFRow row = planilha.getRow(i);
		XSSFCell cell;

		colCartoes.add(null);

		while (row != null) {
			cell = row.getCell(0);
			if ((diaVirada > 0) && (diaVencimento > 0)) {
				// System.out.println("Os dois estão preenchidos");
				if ((row.getCell(1).getNumericCellValue() == diaVirada)
						&& (row.getCell(2).getNumericCellValue() == diaVencimento)) {
					// System.out.println("E achei aqui");
					colCartoes.add(cell.getStringCellValue());
				}
			} else if ((diaVirada == 0) && (diaVencimento == 0)) {
				// System.out.println("Os dois são vazios, vou pegar tudo");
				colCartoes.add(cell.getStringCellValue());
			} else {
				// System.out.println("Apenas um está preenchido");
				if ((diaVirada > 0)
						&& (row.getCell(1).getNumericCellValue() == diaVirada)) {
					// System.out.println("É o dia de virada, até achei ele aqui");
					colCartoes.add(cell.getStringCellValue());
				}
				if ((diaVencimento > 0)
						&& (row.getCell(2).getNumericCellValue() == diaVencimento)) {
					// System.out.println("É o dia de vencimento, até achei ele aqui");
					colCartoes.add(cell.getStringCellValue());
				}
			}
			i++;
			row = planilha.getRow(i);
		}

		return colCartoes.toArray(new String[colCartoes.size()]);
	}

	public static void alterar(XSSFRow toUpdate, Cartao novo) {
		ArrayList<String> listaTodosCartoes = listar();
		listaTodosCartoes.remove(toUpdate.getCell(0).getStringCellValue());
		if ((toUpdate != null) && (!listaTodosCartoes.contains(novo.nome))) {
			if (!novo.nome.isEmpty()) {
				toUpdate.getCell(0).setCellValue(novo.nome);
			}

			if (novo.diaVirada > 0) {
				toUpdate.getCell(1).setCellValue(novo.diaVirada);
			}

			if (novo.diaVencimento > 0) {
				toUpdate.getCell(2).setCellValue(novo.diaVencimento);
			}

			PlanilhaController.salvarBase();
		}
	}

	public static void deletar(XSSFRow toDelete) {
		if (toDelete != null) {
			XSSFRow ultimaLinha = planilha.getRow(planilha.getLastRowNum());
			toDelete.getCell(0).setCellValue(
					ultimaLinha.getCell(0).getStringCellValue());
			toDelete.getCell(1).setCellValue(
					ultimaLinha.getCell(1).getNumericCellValue());
			toDelete.getCell(2).setCellValue(
					ultimaLinha.getCell(2).getNumericCellValue());
			planilha.removeRow(ultimaLinha);

			PlanilhaController.salvarBase();
		}
	}

	public static XSSFRow buscar(String nome) {
		int i = 1;
		XSSFRow row = planilha.getRow(i);
		XSSFCell cell;

		while (row != null) {
			cell = row.getCell(0);
			if (nome.equals(cell.getStringCellValue())) {
				return row;
			}
			i++;
			row = planilha.getRow(i);
		}

		return null;
	}

	public static Cartao getCartao(XSSFRow linha) {
		Cartao card = new Cartao(linha.getCell(0).getStringCellValue(),
				(int) linha.getCell(1).getNumericCellValue(), (int) linha
						.getCell(2).getNumericCellValue());
		return card;
	}

	// public static void main(String[] args) {
	// System.out.println("alok-sevirabi".substring(0,
	// "alok-sevirabi".indexOf("-")));
	//
	// // String[] cartoes = listar(0, 0);
	// // for (String cartao : cartoes) {
	// // System.out.println(cartao);
	// // }
	// //
	// // alterar(buscar("aeho"), "itau", 0, 20);
	// //
	// // cartoes = listar(0, 0);
	// // for (String cartao : cartoes) {
	// // System.out.println(cartao);
	// // }
	// //
	// // deletar(buscar("aehoo"));
	// //
	// // cartoes = listar(0, 0);
	// // for (String cartao : cartoes) {
	// // System.out.println(cartao);
	// // }
	// // cadastrar(null, 0, 0);
	// // System.out.println("cadastrei");
	// }
}
