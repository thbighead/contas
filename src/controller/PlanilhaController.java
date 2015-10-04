package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PlanilhaController {
	/**
	 * A criacao das pastas comeca sua contagem por 1. Caso queira redefinir por
	 * onde deve comecar a contagem das Pastas, mude o valor desta constante
	 */
	private final static int indice0 = 1;
	/**
	 * Planilha com a lista de cartoes de credito e categorias.
	 */
	public static XSSFWorkbook arq_base = carregaPlanilhaBase();
	/**
	 * Planilha onde estao sendo cadastradas as transacoes. Em varios outros
	 * momentos vamos nos referir a ela como "Pasta" ou "pasta", pois seus nome
	 * pode variar (Pastai.xlsx, onde i = 1, 2, 3, 4, ...)
	 */
	public static XSSFWorkbook arq_pasta = carregaPasta();
	/**
	 * Nome da Pasta atualmente usada.
	 */
	static String nome_arq_pasta = descobreNome();

	/**
	 * Monta uma String com um nome para uma Pasta.
	 */
	public static String nomeMaker(int i) {
		return "Pasta" + i + ".xlsx";
	}

	/**
	 * Carrega o arquivo xlsx da Pasta a ser utilizada. Caso nenhum arquivo
	 * Pasta exista ou o atual tenha 200 ou mais cadastros de transacoes, cria
	 * uma nova Pasta para evitar que o arquivo fique muito grande e nao seja
	 * mais possivel acessa-lo.
	 */
	public static XSSFWorkbook carregaPasta() {
		int i = indice0;
		String nomeArq = nomeMaker(i);
		File arquivo = new File(nomeArq);
		XSSFWorkbook arq_xlsx = null;
		/**
		 * Enquanto existirem arquivos pasta para abrir devemos testah-los a fim
		 * de saber se ele jah tem 200 ou mais cadastros de transacoes
		 */
		while (arquivo.exists()) {
			arq_xlsx = abrePlanilha(arquivo);

			if (arq_xlsx.getSheetAt(0).getPhysicalNumberOfRows() > 201) {
				/**
				 * Caso a pasta aberta tenha muitos cadastros de transacoes,
				 * devemos tentar abrir a proxima Pastai.xlsx
				 */
				i++;
				nomeArq = nomeMaker(i);
				arquivo = new File(nomeArq);
				try {
					arq_xlsx.close();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
					e.printStackTrace();
				}
			} else {
				/**
				 * Caso a pasta aberta nao tenha tantos cadastros de transacoes
				 * assim, utilizamos ela mesma e podemos, portanto, sair do loop
				 */
				break;
			}
		}
		/**
		 * Se sairmos do loop sem conseguir abrir nenhuma Pasta, pois todas
		 * estao cheias ou por nao existir nenhuma, criamos uma nova Pasta.
		 */
		if (!arquivo.exists()) {
			arq_xlsx = criaPlanilha(arquivo);
		}

		return arq_xlsx;
	}

	/**
	 * Como o metodo para abrir a Pasta retorna o proprio arquivo xlsx, pode ser
	 * que em algum dado momento precisemos saber qual o nome da pasta atual.
	 * Este metodo descobre o nome da Pasta que estah sendo usada atualmente
	 */
	private static String descobreNome() {
		int i = indice0;
		String nomeArq = nomeMaker(i);
		File arquivo = new File(nomeMaker(i));

		while (arquivo.exists()) {
			nomeArq = nomeMaker(i);
			i++;
			arquivo = new File(nomeMaker(i));
		}

		return nomeArq;
	}

	/**
	 * Cria um novo arquivo xlsx com uma pagina de planilha nomeada "conta". (ou
	 * seja, serve apenas para a criacao de planilhas Pastas)
	 */
	public static XSSFWorkbook criaPlanilha(File arquivo) {
		XSSFWorkbook arq_xlsx = new XSSFWorkbook();
		arq_xlsx.createSheet("conta");
		try {
			arq_xlsx.write(new FileOutputStream(arquivo));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
		return arq_xlsx;
	}

	/**
	 * Abre um arquivo xlsx existente.
	 */
	public static XSSFWorkbook abrePlanilha(File arquivo) {
		XSSFWorkbook arq_xlsx = null;
		try {
			arq_xlsx = new XSSFWorkbook(new FileInputStream(arquivo));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
		return arq_xlsx;
	}

	/**
	 * Carrega a planilha base com os cartoes e categorias cadastrados. O nome
	 * da planilha deve ser "listas desp.xlsx".
	 */
	public static XSSFWorkbook carregaPlanilhaBase() {
		return abrePlanilha(new File("listas desp.xlsx"));
	}

	/**
	 * Descarrega todas as planilhas da memoria
	 */
	public static void fechaPlanilhas() {
		try {
			arq_base.close();
			arq_pasta.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Salva as alteracoes feitas na base.
	 */
	public static void salvarBase() {
		try {
			arq_base.write(new FileOutputStream(new File("listas desp.xlsx")));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Salva as alteracoes feitas na Pasta.
	 */
	public static void salvarPasta() {
		try {
			arq_pasta.write(new FileOutputStream(new File(nome_arq_pasta)));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}

	// public static void main(String[] args) {
	// System.out.println(arq_pasta.getSheetAt(0).getRow(0));
	// System.out.println(arq_pasta.getSheetAt(0).getRow(0).getCell(4));
	// }
}
