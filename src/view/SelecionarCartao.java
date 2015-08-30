package view;

//import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import controller.CartaoController;

public class SelecionarCartao extends JFrame {
	private CadastroCartao alterarCartao = null;

	/**
	 * 
	 */
	private static final long serialVersionUID = -3843989953756019138L;
	private JPanel contentPane;
	JComboBox<String> comboCartao;
	int diaVencimento = 0;
	int diaVirada = 0;

	/**
	 * Launch the application.
	 */
	// public static void main(String[] args) {
	// EventQueue.invokeLater(new Runnable() {
	// public void run() {
	// try {
	// SelecionarCartao frame = new SelecionarCartao("Alterar");
	// // SelecionarCartao frame = new SelecionarCartao("Excluir");
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
	public SelecionarCartao(String operacao) {
		setTitle("Selecionar Cartão para " + operacao);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 470, 155);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblEscolhaUmCartao = new JLabel("Escolha um Cartão:");
		lblEscolhaUmCartao.setBounds(10, 11, 114, 14);
		contentPane.add(lblEscolhaUmCartao);

		comboCartao = new JComboBox<String>(CartaoController.listar(diaVirada,
				diaVencimento));
		comboCartao.setBounds(134, 8, 180, 20);
		contentPane.add(comboCartao);

		JLabel lblFiltros = new JLabel("Filtros:");
		lblFiltros.setBounds(20, 36, 46, 14);
		contentPane.add(lblFiltros);

		JLabel lblDiaDeVirada = new JLabel("Dia de Virada:");
		lblDiaDeVirada.setBounds(20, 61, 114, 14);
		contentPane.add(lblDiaDeVirada);

		JComboBox<Integer> comboDiaVirada = new JComboBox<Integer>(
				CartaoController.dias);
		comboDiaVirada.setBounds(144, 58, 55, 20);
		contentPane.add(comboDiaVirada);
		comboDiaVirada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboDiaVirada.getSelectedItem() != null) {
					diaVirada = Integer.parseInt(comboDiaVirada
							.getSelectedItem().toString());
				} else {
					diaVirada = 0;
				}
				comboCartao.setVisible(false);
				comboCartao = new JComboBox<String>(CartaoController.listar(
						diaVirada, diaVencimento));
				comboCartao.setBounds(134, 8, 180, 20);
				contentPane.add(comboCartao);
				comboCartao.setVisible(true);
			}
		});

		JLabel lblDiaDeVencimento = new JLabel("Dia de Vencimento:");
		lblDiaDeVencimento.setBounds(20, 86, 114, 14);
		contentPane.add(lblDiaDeVencimento);

		JComboBox<Integer> comboDiaVencimento = new JComboBox<Integer>(
				CartaoController.dias);
		comboDiaVencimento.setBounds(144, 83, 55, 20);
		contentPane.add(comboDiaVencimento);
		comboDiaVencimento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboDiaVencimento.getSelectedItem() != null) {
					diaVencimento = Integer.parseInt(comboDiaVencimento
							.getSelectedItem().toString());
				} else {
					diaVencimento = 0;
				}
				comboCartao.setVisible(false);
				comboCartao = new JComboBox<String>(CartaoController.listar(
						diaVirada, diaVencimento));
				comboCartao.setBounds(134, 8, 180, 20);
				contentPane.add(comboCartao);
				comboCartao.setVisible(true);
			}
		});

		JButton btnSelecionar = new JButton(operacao);
		btnSelecionar.setBounds(355, 11, 89, 23);
		contentPane.add(btnSelecionar);
		btnSelecionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (operacao.equals("Alterar")) {
					if (alterarCartao == null) {
						alterarCartao = new CadastroCartao(operacao,
								CartaoController.buscar(comboCartao
										.getSelectedItem().toString()));
					}
					alterarCartao.setVisible(true);
				} else {
					int selectedOption = JOptionPane.showConfirmDialog(null,
							"Tem certeza de que quer excluir o cartão "
									+ comboCartao.getSelectedItem().toString()
									+ "?", "Confirmar exclusão",
							JOptionPane.YES_NO_OPTION);
					if (selectedOption == JOptionPane.YES_OPTION) {
						CartaoController.deletar(CartaoController
								.buscar(comboCartao.getSelectedItem()
										.toString()));
						JOptionPane.showMessageDialog(null,
								"Operação realizada com sucesso!");
						dispose();
					}
				}
			}
		});

		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.setBounds(355, 42, 89, 23);
		contentPane.add(btnLimpar);
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboCartao.setSelectedItem(null);
				comboDiaVencimento.setSelectedItem(null);
				comboDiaVirada.setSelectedItem(null);
			}
		});

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(355, 75, 89, 23);
		contentPane.add(btnCancelar);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}

}
