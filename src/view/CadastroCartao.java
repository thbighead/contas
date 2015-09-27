package view;

//import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;

import model.Cartao;

import org.apache.poi.xssf.usermodel.XSSFRow;

import controller.CartaoController;

public class CadastroCartao extends JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = -1851790112798500254L;
	private JPanel contentPane;
	/**
	 * Components
	 */
	private JTextField textNome;
	private JComboBox<Integer> comboDiaVirada = new JComboBox<Integer>(
			CartaoController.dias);
	private JComboBox<Integer> comboDiaVencimento = new JComboBox<Integer>(
			CartaoController.dias);

	/**
	 * Test the application.
	 */
	// public static void main(String[] args) {
	// EventQueue.invokeLater(new Runnable() {
	// public void run() {
	// try {
	// // CadastroCartao frame = new CadastroCartao("Cadastrar",
	// // null);
	// CadastroCartao frame = new CadastroCartao("Alterar",
	// CartaoController.planilha.getRow(3));
	// frame.setVisible(true);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// });
	// }

	/**
	 * Create the frame.
	 */
	public CadastroCartao(String operacao, XSSFRow linha) {
		/**
		 * Setting the window
		 */
		setTitle(operacao + " cartão"); // define a operacao como titulo
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 375, 175);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Nome do Cartao
		JLabel lblNomeDoCartao = new JLabel("Nome do Cartão:");
		lblNomeDoCartao.setBounds(10, 11, 114, 14);
		contentPane.add(lblNomeDoCartao);

		textNome = new JTextField();
		textNome.setBounds(134, 8, 140, 20);
		contentPane.add(textNome);
		textNome.setColumns(10);

		// Dia de Virada
		JLabel lblDiaDeVirada = new JLabel("Dia de Virada:");
		lblDiaDeVirada.setBounds(10, 36, 114, 14);
		contentPane.add(lblDiaDeVirada);

		comboDiaVirada.setBounds(134, 33, 50, 20);
		contentPane.add(comboDiaVirada);

		// Dia de Vencimento
		JLabel lblDiaDeVencimento = new JLabel("Dia de Vencimento:");
		lblDiaDeVencimento.setBounds(10, 61, 114, 14);
		contentPane.add(lblDiaDeVencimento);

		comboDiaVencimento.setBounds(134, 58, 50, 20);
		contentPane.add(comboDiaVencimento);

		// Alterar?
		alterarLoad(operacao, linha);

		// Botoes
		JButton btnSalvar = new JButton(operacao);
		btnSalvar.setBounds(254, 103, 95, 23);
		contentPane.add(btnSalvar);
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (seAlgumCampoEmBranco()) {
					alertMessage("Preencha os campos em branco");
				} else {
					/**
					 * Monta o novo cartao, seja para cadastrar ou para alterar
					 */
					Cartao novo = new Cartao(textNome.getText(),
							getComboSelectedItemAsInt(comboDiaVirada),
							getComboSelectedItemAsInt(comboDiaVencimento));

					if (operacao.equals("Alterar")) { // altera
						CartaoController.alterar(linha, novo);
					} else { // cadastra
						CartaoController.cadastrar(novo);
					}

					CartaoController.recarregarBase(); // recarrega a base
					alertMessage("Operação realizada com sucesso!");
					// depois de alterar nao tem pq manter a janela
					if (operacao.equals("Alterar")) {
						limparCampos();
						dispose();
					}
				}
			}
		});

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(134, 103, 89, 23);
		contentPane.add(btnCancelar);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.setBounds(10, 103, 89, 23);
		contentPane.add(btnLimpar);
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
	}

	/**
	 * Carrega dados caso seja uma operacao de alteracao
	 */
	private void alterarLoad(String operacao, XSSFRow linha) {
		if (operacao.equals("Alterar")) {
			if (linha.getCell(0) != null) {
				textNome.setText(linha.getCell(0).getStringCellValue());
			}

			if (linha.getCell(1).getNumericCellValue() > 0) {
				comboDiaVirada.setSelectedIndex((int) linha.getCell(1)
						.getNumericCellValue());
			}

			if (linha.getCell(2).getNumericCellValue() > 0) {
				comboDiaVencimento.setSelectedIndex((int) linha.getCell(2)
						.getNumericCellValue());
			}
		}
	}

	/**
	 * Testa se tem algum campo obrigatorio em branco
	 */
	private boolean seAlgumCampoEmBranco() {
		return textNome.getText().isEmpty()
				|| comboDiaVirada.getSelectedItem() == null
				|| comboDiaVencimento.getSelectedItem() == null;
	}

	/**
	 * Mostra mensagem de alerta com a opcao "OK"
	 */
	private void alertMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	/**
	 * Limpa todos os campos da janela
	 */
	private void limparCampos() {
		textNome.setText("");
		comboDiaVencimento.setSelectedItem(null);
		comboDiaVirada.setSelectedItem(null);
	}

	/**
	 * Pega o valor inteiro do item selecionado na combobox passada como
	 * parametro
	 */
	private int getComboSelectedItemAsInt(JComboBox<Integer> combo) {
		return Integer.parseInt(combo.getSelectedItem().toString());
	}
}