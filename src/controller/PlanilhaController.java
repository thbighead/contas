package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PlanilhaController {
	public static XSSFWorkbook arq_base = carregaPlanilhaBase();
	public static XSSFWorkbook arq_pasta = carregaPasta();
	static String nome_arq_pasta = descobreNome();

	public static String nomeMaker(int i) {
		return "Pasta" + i + ".xlsx";
	}

	private static XSSFWorkbook carregaPasta() {
		int i = 1;
		String nomeArq = null;
		nomeArq = nomeMaker(i);
		File arquivo = new File(nomeArq);
		XSSFWorkbook arq_xlsx = null;

		while (arquivo.exists()) {
			arq_xlsx = abrePlanilha(arquivo);
			if (arq_xlsx.getSheetAt(0).getPhysicalNumberOfRows() > 201) {
				i++;
				nomeArq = nomeMaker(i);
				arquivo = new File(nomeArq);
				try {
					arq_xlsx.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				break;
			}
		}
		if (!arquivo.exists()) {
			arq_xlsx = criaPlanilha(arquivo);
		}

		return arq_xlsx;
	}
	
	private static String descobreNome() {
		int i = 1;
		String nomeArq = nomeMaker(i);
		File arquivo = new File(nomeMaker(i));

		while (arquivo.exists()) {
			nomeArq = nomeMaker(i);
			i++;
			arquivo = new File(nomeMaker(i));
		}
		
		return nomeArq;
	}

	public static XSSFWorkbook criaPlanilha(File arquivo) {
		XSSFWorkbook arq_xlsx = new XSSFWorkbook();
		arq_xlsx.createSheet("conta");
		try {
			arq_xlsx.write(new FileOutputStream(arquivo));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return arq_xlsx;
	}

	public static XSSFWorkbook abrePlanilha(File arquivo) {
		XSSFWorkbook arq_xlsx = null;
		try {
			arq_xlsx = new XSSFWorkbook(new FileInputStream(arquivo));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return arq_xlsx;
	}

	private static XSSFWorkbook carregaPlanilhaBase() {
		return abrePlanilha(new File("listas desp.xlsx"));
	}

	public static void fechaPlanilha() {
		try {
			arq_base.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void salvarBase() {
		try {
			arq_base.write(new FileOutputStream(new File("listas desp.xlsx")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void salvarPasta() {
		try {
			arq_pasta.write(new FileOutputStream(new File(nome_arq_pasta)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		System.out.println(arq_pasta.getSheetAt(0).getRow(0));
		System.out.println(arq_pasta.getSheetAt(0).getRow(0).getCell(4));
	}
}
