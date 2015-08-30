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
	private JTextField textNome;

	/**
	 * Launch the application.
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
		setTitle(operacao + " cartão");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 375, 175);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNomeDoCartao = new JLabel("Nome do Cartão:");
		lblNomeDoCartao.setBounds(10, 11, 114, 14);
		contentPane.add(lblNomeDoCartao);

		textNome = new JTextField();
		textNome.setBounds(134, 8, 140, 20);
		contentPane.add(textNome);
		textNome.setColumns(10);
		if ((operacao.equals("Alterar")) && (linha.getCell(0) != null)) {
			textNome.setText(linha.getCell(0).getStringCellValue());
		}

		JLabel lblDiaDeVirada = new JLabel("Dia de Virada:");
		lblDiaDeVirada.setBounds(10, 36, 114, 14);
		contentPane.add(lblDiaDeVirada);

		JComboBox<Integer> comboDiaVirada = new JComboBox<Integer>(
				CartaoController.dias);
		comboDiaVirada.setBounds(134, 33, 50, 20);
		contentPane.add(comboDiaVirada);
		if ((operacao.equals("Alterar"))
				&& (linha.getCell(1).getNumericCellValue() > 0)) {
			comboDiaVirada.setSelectedIndex((int) linha.getCell(1)
					.getNumericCellValue());
		}

		JLabel lblDiaDeVencimento = new JLabel("Dia de Vencimento:");
		lblDiaDeVencimento.setBounds(10, 61, 114, 14);
		contentPane.add(lblDiaDeVencimento);

		JComboBox<Integer> comboDiaVencimento = new JComboBox<Integer>(
				CartaoController.dias);
		comboDiaVencimento.setBounds(134, 58, 50, 20);
		contentPane.add(comboDiaVencimento);
		if ((operacao.equals("Alterar"))
				&& (linha.getCell(2).getNumericCellValue() > 0)) {
			comboDiaVencimento.setSelectedIndex((int) linha.getCell(2)
					.getNumericCellValue());
		}

		JButton btnSalvar = new JButton(operacao);
		btnSalvar.setBounds(254, 103, 95, 23);
		contentPane.add(btnSalvar);
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textNome.getText().isEmpty()
						|| comboDiaVirada.getSelectedItem() == null
						|| comboDiaVencimento.getSelectedItem() == null) {
					JOptionPane.showMessageDialog(null,
							"Preencha os campos em branco");
				} else {
					Cartao novo = new Cartao(textNome.getText(), Integer
							.parseInt(comboDiaVirada.getSelectedItem()
									.toString()), Integer
							.parseInt(comboDiaVencimento.getSelectedItem()
									.toString()));
					if (operacao.equals("Alterar")) {
						CartaoController.alterar(linha, novo);
					} else {
						CartaoController.cadastrar(novo);
					}
					JOptionPane.showMessageDialog(null,
							"Operação realizada com sucesso!");
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
				textNome.setText("");
				comboDiaVencimento.setSelectedItem(null);
				comboDiaVirada.setSelectedItem(null);
			}
		});
	}
}