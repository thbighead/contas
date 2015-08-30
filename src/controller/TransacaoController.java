package controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TreeMap;

import model.Transacao;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class TransacaoController {
	public static XSSFSheet planilhaBase = PlanilhaController.arq_base
			.getSheet("categoria");
	public static XSSFSheet planilhaPasta = PlanilhaController.arq_pasta
			.getSheet("conta");
	private static Calendar c = Calendar.getInstance();

	public static void cadastrarCategoria(String nome) {
		ArrayList<String> listaTodasCategorias = listarCategorias(null);
		if (!listaTodasCategorias.contains(nome)) {
			XSSFRow novaLinha = planilhaBase.createRow(planilhaBase
					.getLastRowNum() + 1);
			XSSFCellStyle estiloCell = planilhaBase.getRow(1).getCell(0)
					.getCellStyle();

			novaLinha.createCell(0);
			novaLinha.getCell(0).setCellStyle(estiloCell);
			novaLinha.getCell(0).setCellValue(nome);

			PlanilhaController.salvarBase();
		}
	}

	public static String[] listarCategorias() {
		int i = 1;
		ArrayList<String> colTipo = new ArrayList<String>();
		XSSFRow row = planilhaBase.getRow(i);
		XSSFCell cell;

		colTipo.add(null);

		while (row != null) {
			cell = row.getCell(0);
			colTipo.add(cell.getStringCellValue());
			i++;
			row = planilhaBase.getRow(i);
		}

		return colTipo.toArray(new String[colTipo.size()]);
	}

	private static ArrayList<String> listarCategorias(Object a) {
		int i = 1;
		ArrayList<String> colTipo = new ArrayList<String>();
		XSSFRow row = planilhaBase.getRow(i);
		XSSFCell cell;

		while (row != null) {
			cell = row.getCell(0);
			colTipo.add(cell.getStringCellValue());
			i++;
			row = planilhaBase.getRow(i);
		}

		return colTipo;
	}

	public static XSSFRow buscarCategoria(String nome) {
		int i = 1;
		XSSFRow row = planilhaBase.getRow(i);
		XSSFCell cell;

		while (row != null) {
			cell = row.getCell(0);
			if (nome.equals(cell.getStringCellValue())) {
				return row;
			}
			i++;
			row = planilhaBase.getRow(i);
		}

		return null;
	}

	public static void deletarCategoria(XSSFRow toDelete) {
		if (toDelete != null) {
			XSSFRow ultimaLinha = planilhaBase.getRow(planilhaBase
					.getLastRowNum());
			toDelete.getCell(0).setCellValue(
					ultimaLinha.getCell(0).getStringCellValue());
			planilhaBase.removeRow(ultimaLinha);

			PlanilhaController.salvarBase();
		}
	}

	public static void cadastrar(Transacao nova, String dataUtil) {
		ArrayList<String> listaTodasTransacoes = listar();
		if (!listaTodasTransacoes.contains(nova.data + " " + nova.categoria
				+ " " + nova.descricao + " " + nova.valor)) {
			XSSFRow novaLinha = planilhaPasta.createRow(planilhaPasta
					.getLastRowNum() + 1);
			XSSFCellStyle estiloCell = planilhaPasta.getWorkbook()
					.createCellStyle();
			CreationHelper createHelper = planilhaPasta.getWorkbook()
					.getCreationHelper();

			novaLinha.createCell(0);
			novaLinha.getCell(0).setCellType(Cell.CELL_TYPE_NUMERIC);
			estiloCell.setAlignment(CellStyle.ALIGN_RIGHT);
			estiloCell.setDataFormat(createHelper.createDataFormat().getFormat(
					"dd/mm/yyyy"));
			novaLinha.getCell(0).setCellStyle(estiloCell);
			novaLinha.getCell(0).setCellValue(nova.data.getTime());

			estiloCell = planilhaPasta.getWorkbook().createCellStyle();
			novaLinha.createCell(1);
			novaLinha.getCell(1).setCellType(Cell.CELL_TYPE_NUMERIC);
			estiloCell.setAlignment(CellStyle.ALIGN_RIGHT);
			estiloCell.setDataFormat(createHelper.createDataFormat().getFormat(
					"dd/mm/yyyy"));
			novaLinha.getCell(1).setCellStyle(estiloCell);
			novaLinha.getCell(1).setCellValue(
					DataController.stringToCalendar(dataUtil).getTime());

			estiloCell = planilhaPasta.getWorkbook().createCellStyle();
			novaLinha.createCell(3);
			novaLinha.getCell(3).setCellType(Cell.CELL_TYPE_FORMULA);
			estiloCell.setAlignment(CellStyle.ALIGN_CENTER);
			novaLinha.getCell(3).setCellStyle(estiloCell);
			novaLinha.getCell(3).setCellFormula(
					"WEEKDAY(A" + (novaLinha.getRowNum() + 1) + ",1)");

			estiloCell = planilhaPasta.getWorkbook().createCellStyle();
			novaLinha.createCell(4);
			novaLinha.getCell(4).setCellType(Cell.CELL_TYPE_NUMERIC);
			estiloCell.setAlignment(CellStyle.ALIGN_RIGHT);
			novaLinha.getCell(4).setCellStyle(estiloCell);
			novaLinha.getCell(4).setCellValue(nova.valor);

			estiloCell = planilhaPasta.getWorkbook().createCellStyle();
			novaLinha.createCell(5);
			novaLinha.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
			estiloCell.setAlignment(CellStyle.ALIGN_LEFT);
			novaLinha.getCell(5).setCellStyle(estiloCell);
			novaLinha.getCell(5).setCellValue(nova.descricao);

			estiloCell = planilhaPasta.getWorkbook().createCellStyle();
			novaLinha.createCell(6);
			novaLinha.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
			estiloCell.setAlignment(CellStyle.ALIGN_LEFT);
			novaLinha.getCell(6).setCellStyle(estiloCell);
			novaLinha.getCell(6).setCellValue(nova.categoria);

			for (int j = 0; j < 7; j++) {
				planilhaPasta.autoSizeColumn(j);
			}

			PlanilhaController.salvarPasta();
		}
	}

	public static void alterar(XSSFRow toUpdate, Transacao nova, String dataUtil) {
		ArrayList<String> listaTodasTransacoes = listar();
		listaTodasTransacoes.remove(nova.data + " " + nova.categoria + " "
				+ nova.descricao + " " + nova.valor);
		if ((toUpdate != null)
				&& (!listaTodasTransacoes.contains(nova.data + " "
						+ nova.categoria + " " + nova.descricao + " "
						+ nova.valor))) {
			if (!DataController.calendarToString(nova.data).isEmpty()) {
				toUpdate.getCell(0).setCellValue(nova.data.getTime());
			}

			if (!dataUtil.isEmpty()) {
				toUpdate.getCell(1).setCellValue(
						DataController.stringToCalendar(dataUtil).getTime());
			}
			// toUpdate.getCell(3).setCellValue();
			if (nova.valor != null) {
				toUpdate.getCell(4).setCellValue(nova.valor);
			}

			if (!nova.descricao.isEmpty()) {
				toUpdate.getCell(5).setCellValue(nova.descricao);
			}

			if (!nova.categoria.isEmpty()) {
				toUpdate.getCell(6).setCellValue(nova.categoria);
			}

			for (int j = 0; j < 7; j++) {
				planilhaPasta.autoSizeColumn(j);
			}

			PlanilhaController.salvarPasta();
		}
	}

	private static ArrayList<String> listar() {
		int i = 1;
		ArrayList<String> transacoes = new ArrayList<String>();
		String transacao = null;
		XSSFRow row = planilhaPasta.getRow(i);

		while (row != null) {
			c.setTime(row.getCell(0).getDateCellValue());
			transacao = DataController.calendarToString(c) + " "
					+ row.getCell(6).getStringCellValue() + " "
					+ row.getCell(5).getStringCellValue() + " "
					+ row.getCell(4).getNumericCellValue();
			transacoes.add(transacao);
			i++;
			row = planilhaPasta.getRow(i);
		}

		return transacoes;
	}

	public static TreeMap<String, Integer> listar(String data,
			String categoria, String descricao, Integer parcelas,
			String nomeCartao) {
		int i = 1;
		boolean teste = true;
		TreeMap<String, Integer> transacoes = new TreeMap<String, Integer>();
		XSSFRow row = planilhaPasta.getRow(i);

		while (row != null) {
			c.setTime(row.getCell(0).getDateCellValue());
			if ((data != null) && (categoria != null) && (descricao != null)
					&& (parcelas != null) && (nomeCartao != null)) {
				System.out.println("Tudo preenchido");
				if ((DataController.calendarToString(c).equals(data))
						&& (row.getCell(6).getStringCellValue()
								.equals(categoria))
						&& (descricao.contains(row.getCell(5)
								.getStringCellValue()))
						&& (quantasParcelas(row.getCell(5).getStringCellValue()) == parcelas)
						&& (seTransaCartao(row.getCell(5).getStringCellValue())
								.equals(nomeCartao))) {
					System.out.println("E consegui encontrar");
					transacoes.put(row.getCell(5).getStringCellValue(), i);
				}
			} else if ((categoria == null) && (descricao == null)
					&& (parcelas == null) && (nomeCartao == null)) {
				// System.out
				// .println("Nenhum preenchido, vou pegar tudo dessa data");
				if ((data != null)
						&& (DataController.calendarToString(c).equals(data))) {
					transacoes.put(row.getCell(5).getStringCellValue(), i);
				}
			} else {
				// System.out.println("Pelo menos um preenchido, não todos");
				if ((data != null)
						&& (DataController.calendarToString(c).equals(data))) {
				} else {
					System.out.println(data);
					// System.out.print(i + " Mas a data não correspondeu...");
					teste = false;
				}
				if (teste && (categoria != null)) {
					if (row.getCell(6).getStringCellValue().equals(categoria)) {
					} else {
						// System.out.print("Mas a categoria não correspondeu...");
						teste = false;
					}
				}
				if (teste && (descricao != null)) {
					if (row.getCell(5).getStringCellValue().contains(descricao)) {
					} else {
						// System.out.print("Mas a descrição não correspondeu...");
						teste = false;
					}
				}
				if (teste && (parcelas != null)) {
					if (quantasParcelas(row.getCell(5).getStringCellValue()) == parcelas) {
					} else {
						// System.out
						// .print("Mas a quantidade de parcelas não correspondeu...");
						teste = false;
					}
				}
				if (teste && (nomeCartao != null)) {
					if (seTransaCartao(row.getCell(5).getStringCellValue())
							.equals(nomeCartao)) {
					} else {
						// System.out.print("Mas o cartão não correspondeu...");
						teste = false;
					}
				}
				if (teste) {
					// System.out.println("E eu consegui achar!");
					transacoes.put(row.getCell(5).getStringCellValue(), i);
				}

				teste = true;
			}
			i++;
			row = planilhaPasta.getRow(i);
		}

		return transacoes;
	}

	public static void deletar(XSSFRow toDelete) {
		if (toDelete != null) {
			XSSFRow ultimaLinha = planilhaPasta.getRow(planilhaPasta
					.getLastRowNum());
			toDelete.getCell(0).setCellValue(
					ultimaLinha.getCell(0).getNumericCellValue());
			toDelete.getCell(1).setCellValue(
					ultimaLinha.getCell(1).getNumericCellValue());
			toDelete.getCell(3).setCellValue(
					ultimaLinha.getCell(3).getCellFormula());
			toDelete.getCell(4).setCellValue(
					ultimaLinha.getCell(4).getNumericCellValue());
			toDelete.getCell(5).setCellValue(
					ultimaLinha.getCell(5).getStringCellValue());
			toDelete.getCell(6).setCellValue(
					ultimaLinha.getCell(6).getStringCellValue());
			planilhaPasta.removeRow(ultimaLinha);

			PlanilhaController.salvarPasta();
		}
	}

	public static XSSFRow buscar(int linhaPos) {
		return planilhaPasta.getRow(linhaPos);
	}

	public static boolean seTransParcelada(String descricao) {
		if (descricao.contains("/")) {
			return true;
		}
		return false;
	}

	public static int quantasParcelas(String descricao) {
		if (seTransParcelada(descricao)) {
			String[] descricaoDividida = descricao.split(" ");
			System.out
					.println(Integer
							.parseInt(descricaoDividida[descricaoDividida.length - 1]
									.substring(
											0,
											descricaoDividida[descricaoDividida.length - 1]
													.indexOf("/"))));
			return Integer
					.parseInt(descricaoDividida[descricaoDividida.length - 1]
							.substring(
									0,
									descricaoDividida[descricaoDividida.length - 1]
											.indexOf("/")));
		}
		return 1;
	}

	public static String seTransaCartao(String descricao) {
		String[] cartoes = CartaoController.listar(0, 0);
		for (String cartao : cartoes) {
			if (descricao.contains("-")) {
				if ((descricao.substring(0, descricao.indexOf("-"))
						.equals(cartao))) {
					return cartao;
				}
			}
		}
		return null;
	}

	// public static void main(String[] args) {
	// System.out.println(planilhaPasta.getRow(0).getCell(4)
	// .getStringCellValue());
	// System.out.println(planilhaPasta.getRow(0).getCell(5)
	// .getStringCellValue());
	// listar();
	// listar(null, null, null, null, null);
	// }
}