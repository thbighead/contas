package controller;

import java.util.ArrayList;

import model.Cartao;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class CartaoController {
	/**
	 * Todas as solucoes aqui implementadas percorrem a planilha buscando,
	 * testando, cadastrando, alterando ou deletando. Para evitar um erro
	 * estranho (provavelmente do Apache POI) onde se comecarmos pelo indice 0
	 * (primeira linha) o codigo simplesmente nao conseguia percorrer a planilha
	 * mesmo com o incremento feito no indice, comecamos pelo indice 1 (segunda
	 * linha) e por isso essa constante estah aqui. Caso queira redefinir por
	 * onde deve comecar a percorrer a planilha, mude o valor desta constante
	 */
	private final static int indice0 = 1;

	/**
	 * Lista de dias para cartoes. Para nao haver confusoes, ateh os bancos
	 * costumam evitar dias 29, 30 e 31, portanto essa lista eh mais que o
	 * suficiente na hora de escolher o dia de virada e o dia de vencimento para
	 * o cartao a cadastrar/cadastrado
	 */
	public static Integer[] dias = { null, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11,
			12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28 };
	/**
	 * Planilha exata (sheet) onde ficam os cartoes cadastrados
	 */
	public static XSSFSheet planilha = PlanilhaController.arq_base
			.getSheet("cartoes");

	/**
	 * Cadastra uma nova instancia de Cartao em 'planilha'. Eh garantido que
	 * dois cartoes de mesmo nome nao sejam cadastrados na mesma planilha.
	 */
	public static void cadastrar(Cartao novo) {
		ArrayList<String> listaTodosCartoes = listarTodos();
		if (!listaTodosCartoes.contains(novo.nome)) {
			XSSFRow novaLinha = planilha
					.createRow(planilha.getLastRowNum() + 1);

			// Coluna com nome do cartao
			XSSFCellStyle estiloCell = planilha.getRow(1).getCell(0)
					.getCellStyle();
			novaLinha.createCell(0);
			novaLinha.getCell(0).setCellStyle(estiloCell);
			novaLinha.getCell(0).setCellValue(novo.nome);

			// Coluna com dia de virada do cartao
			novaLinha.createCell(1);
			estiloCell = planilha.getRow(1).getCell(1).getCellStyle();
			novaLinha.getCell(1).setCellStyle(estiloCell);
			novaLinha.getCell(1).setCellValue(novo.diaVirada);

			// Coluna com dia de vencimento do cartao
			novaLinha.createCell(2);
			estiloCell = planilha.getRow(1).getCell(2).getCellStyle();
			novaLinha.getCell(2).setCellStyle(estiloCell);
			novaLinha.getCell(2).setCellValue(novo.diaVencimento);

			// Salva as alteracoes na planilha
			PlanilhaController.salvarBase();
		}
	}

	/**
	 * Listar todos os cartoes cadastrados em 'planilha'. O retorno eh dado em
	 * ArrayList<String>, caso esteja esperando por um String[], use o metodo
	 * listar(0, 0) que estah declarado logo abaixo
	 */
	public static ArrayList<String> listarTodos() {
		int i = indice0;
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

	/**
	 * Listar cartoes cadastrados com filtros de dia de virada e vencimento.
	 * Este metod eh String(int, int)
	 */
	public static String[] listar(int diaVirada, int diaVencimento) {
		int i = indice0;
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

	/**
	 * Listar cartoes cadastrados com filtros de dia de virada e vencimento.
	 * Este metodo eh ArrayList<String>(Integer, Integer)
	 */
	public static ArrayList<String> listar(Integer diaVirada,
			Integer diaVencimento) {
		int i = indice0;
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

		return colCartoes;
	}

	/**
	 * Alterar um cadastro numa certa linha da planilha por um novo cartao
	 */
	public static void alterar(XSSFRow toUpdate, Cartao novo) {
		ArrayList<String> listaTodosCartoes = listarTodos();
		listaTodosCartoes.remove(toUpdate.getCell(0).getStringCellValue());
		// testa se o cartao novo ja nao existe na planilha
		if ((toUpdate != null) && (!listaTodosCartoes.contains(novo.nome))) {
			// Altera o nome do cartao se nao for o mesmo nome antigo
			if (!novo.nome.isEmpty()) {
				toUpdate.getCell(0).setCellValue(novo.nome);
			}

			// Altera o dia de virada do cartao se nao for o mesmo que o antigo
			if (novo.diaVirada > 0) {
				toUpdate.getCell(1).setCellValue(novo.diaVirada);
			}

			// Altera o dia de vencimento do cartao se nao for o mesmo que o
			// antigo
			if (novo.diaVencimento > 0) {
				toUpdate.getCell(2).setCellValue(novo.diaVencimento);
			}

			// salva as alteracoes na planilha
			PlanilhaController.salvarBase();
		}
	}

	/**
	 * Encapsulamento da funcao alterar e buscar, para que seu codigo fique
	 * menos verbose
	 */
	public static void alterar(String toUpdate, Cartao novo) {
		alterar(buscar(toUpdate), novo);
	}

	/**
	 * Buscar por um cartao cadastrado com o nome dado. Caso nenhum cartao seja
	 * encontrado 'null' serah retornado (rimou!)
	 */
	public static XSSFRow buscar(String nome) {
		int i = indice0;
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

	/**
	 * Gera uma instancia de Cartao a partir de um cartao cadastrado numa dada
	 * linha da planilha
	 */
	public static Cartao getCartao(XSSFRow linha) {
		Cartao card = new Cartao(linha.getCell(0).getStringCellValue(),
				(int) linha.getCell(1).getNumericCellValue(), (int) linha
						.getCell(2).getNumericCellValue());
		return card;
	}

	/**
	 * Encapsulamento da funcao getCartao e buscar, para que seu codigo fique
	 * menos verbose
	 */
	public static Cartao getCartao(String nome) {
		return getCartao(buscar(nome));
	}

	/**
	 * Excluir o cartao cadastrado em uma certa linha da planilha. Isso farah
	 * com que a ultima linha da planilha venha parar no lugar da linha
	 * deletada, ou seja, esta solucao muda a ordem das linhas para sempre se o
	 * seu elemento nao for o ultimo da lista. Essa solucao soh foi implementada
	 * assim porque o Apache POI nao fornece nenhum algoritmo para o rearranjo
	 * de linhas.
	 */
	public static void deletar(XSSFRow toDelete) {
		if (toDelete != null) { // testa se a linha ja nao eh nula
			XSSFRow ultimaLinha = planilha.getRow(planilha.getLastRowNum());

			// sobrescreve a linha a ser deletada com os valores da ultima linha
			toDelete.getCell(0).setCellValue(
					ultimaLinha.getCell(0).getStringCellValue());
			toDelete.getCell(1).setCellValue(
					ultimaLinha.getCell(1).getNumericCellValue());
			toDelete.getCell(2).setCellValue(
					ultimaLinha.getCell(2).getNumericCellValue());

			// remove a ultima linha
			planilha.removeRow(ultimaLinha);

			// salva as alteracoes na planilha
			PlanilhaController.salvarBase();
		}
	}

	/**
	 * Encapsulamento da funcao deletar e buscar, para que seu codigo fique
	 * menos verbose
	 */
	public static void deletar(String toDelete) {
		deletar(buscar(toDelete));
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
