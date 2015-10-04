package view;

//import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import javax.swing.ButtonGroup;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JButton;

import controller.CartaoController;
import controller.DataController;
import controller.TransacaoController;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class SelecionarTransacao extends JFrame {
	private CadastroTransacao alterarTransacao = null;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1285054817073386796L;
	private JPanel contentPane;
	private JTextField textDescricao;
	private JComboBox comboTransacao;
	private JFormattedTextField formattedTextQuantasVezes;
	private JComboBox<String> comboData;
	private String data = null, categoria = null, descricao = null,
			nomeCartao = null;
	private ArrayList<String> sorter;
	private FocusListener descricaoFocusListener = new FocusListener() {
		@Override
		public void focusLost(FocusEvent e) {
			if (!textDescricao.getText().isEmpty()) {
				descricao = textDescricao.getText();
			} else {
				descricao = null;
			}
			comboTransacao.setVisible(false);
			sorter = new ArrayList<String>(TransacaoController.listar(data,
					categoria, descricao, parcelas, nomeCartao).keySet());
			sorter.sort(null);
			comboTransacao = new JComboBox(sorter.toArray());
			comboTransacao.setBounds(167, 8, 357, 20);
			contentPane.add(comboTransacao);
			comboTransacao.setVisible(true);
		}

		@Override
		public void focusGained(FocusEvent e) {
		}
	};
	Integer parcelas = null;
	private FocusListener parcelasFocusListener = new FocusListener() {
		@Override
		public void focusLost(FocusEvent e) {
			if (!formattedTextQuantasVezes.getText().isEmpty()) {
				parcelas = Integer
						.parseInt(formattedTextQuantasVezes.getText());
				if (parcelas == 0) {
					parcelas = 1;
				}
				comboTransacao.setVisible(false);
				sorter = new ArrayList<String>(TransacaoController.listar(data,
						categoria, descricao, parcelas, nomeCartao).keySet());
				sorter.sort(null);
				comboTransacao = new JComboBox(sorter.toArray());
				comboTransacao.setBounds(167, 8, 357, 20);
				contentPane.add(comboTransacao);
				comboTransacao.setVisible(true);
			} else {
				parcelas = null;
			}
		}

		@Override
		public void focusGained(FocusEvent e) {
		}
	};

	/**
	 * Launch the application.
	 */
	// public static void main(String[] args) {
	// EventQueue.invokeLater(new Runnable() {
	// public void run() {
	// try {
	// SelecionarTransacao frame = new SelecionarTransacao(
	// "Alterar");
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
	public SelecionarTransacao(String operacao) {
		setTitle(operacao + " transação");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 550, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblEscolhaUmaTransacao = new JLabel("Escolha uma Transação:");
		lblEscolhaUmaTransacao.setBounds(10, 11, 147, 14);
		contentPane.add(lblEscolhaUmaTransacao);

		JLabel lblFiltros = new JLabel("Filtros:");
		lblFiltros.setBounds(10, 36, 46, 14);
		contentPane.add(lblFiltros);

		comboData = new JComboBox<String>(DataController.listar(null));
		comboData.setBounds(66, 33, 115, 20);
		contentPane.add(comboData);
		comboData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				data = comboData.getSelectedItem().toString();
				comboTransacao.setVisible(false);
				sorter = new ArrayList<String>(TransacaoController.listar(data,
						categoria, descricao, parcelas, nomeCartao).keySet());
				sorter.sort(null);
				comboTransacao = new JComboBox(sorter.toArray());
				comboTransacao.setBounds(167, 8, 357, 20);
				contentPane.add(comboTransacao);
				comboTransacao.setVisible(true);
			}
		});
		if (comboData.getSelectedItem() != null) {
			data = comboData.getSelectedItem().toString();
		}

		sorter = new ArrayList<String>(TransacaoController.listar(data,
				categoria, descricao, parcelas, nomeCartao).keySet());
		sorter.sort(null);
		comboTransacao = new JComboBox(sorter.toArray());
		comboTransacao.setBounds(167, 8, 357, 20);
		contentPane.add(comboTransacao);

		JLabel lblParcelado = new JLabel("Parcelado?");
		lblParcelado.setBounds(10, 61, 75, 14);
		contentPane.add(lblParcelado);

		JRadioButton rdbtnParceladoSim = new JRadioButton("Sim");
		rdbtnParceladoSim.setBounds(91, 57, 52, 23);
		contentPane.add(rdbtnParceladoSim);

		JRadioButton rdbtnParceladoNao = new JRadioButton("Não");
		rdbtnParceladoNao.setBounds(145, 57, 52, 23);
		contentPane.add(rdbtnParceladoNao);

		ButtonGroup groupParceladoSN = new ButtonGroup();
		groupParceladoSN.add(rdbtnParceladoSim);
		groupParceladoSN.add(rdbtnParceladoNao);

		JLabel lblQuantasVezes = new JLabel("Quantas Vezes?");
		lblQuantasVezes.setBounds(213, 61, 95, 14);
		contentPane.add(lblQuantasVezes);
		lblQuantasVezes.setVisible(false);

		MaskFormatter fmtQtdVezes = null;
		try {
			fmtQtdVezes = new MaskFormatter("###");
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
		fmtQtdVezes.setPlaceholderCharacter('0');
		formattedTextQuantasVezes = new JFormattedTextField(fmtQtdVezes);
		formattedTextQuantasVezes.setBounds(335, 58, 70, 20);
		contentPane.add(formattedTextQuantasVezes);
		formattedTextQuantasVezes.setVisible(false);
		formattedTextQuantasVezes.addFocusListener(parcelasFocusListener);

		rdbtnParceladoSim.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				int state = event.getStateChange();
				if (state == ItemEvent.SELECTED) {
					lblQuantasVezes.setVisible(true);
					formattedTextQuantasVezes.setVisible(true);
				} else if (state == ItemEvent.DESELECTED) {
					lblQuantasVezes.setVisible(false);
					formattedTextQuantasVezes.setVisible(false);
				}
			}
		});

		JLabel lblTransacaoDeCartao = new JLabel("Transação de Cartão?");
		lblTransacaoDeCartao.setBounds(10, 86, 133, 14);
		contentPane.add(lblTransacaoDeCartao);

		JRadioButton rdbtnCartaoSim = new JRadioButton("Sim");
		rdbtnCartaoSim.setBounds(145, 82, 50, 23);
		contentPane.add(rdbtnCartaoSim);

		JRadioButton rdbtnCartaoNao = new JRadioButton("Não");
		rdbtnCartaoNao.setBounds(197, 82, 52, 23);
		contentPane.add(rdbtnCartaoNao);

		ButtonGroup groupCartaoSN = new ButtonGroup();
		groupCartaoSN.add(rdbtnCartaoSim);
		groupCartaoSN.add(rdbtnCartaoNao);

		JComboBox<String> comboCartao = new JComboBox<String>(
				CartaoController.listar(0, 0));
		comboCartao.setBounds(255, 83, 150, 20);
		contentPane.add(comboCartao);
		comboCartao.setVisible(false);
		comboCartao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nomeCartao = comboCartao.getSelectedItem().toString();
				comboTransacao.setVisible(false);
				sorter = new ArrayList<String>(TransacaoController.listar(data,
						categoria, descricao, parcelas, nomeCartao).keySet());
				sorter.sort(null);
				comboTransacao = new JComboBox(sorter.toArray());
				comboTransacao.setBounds(167, 8, 357, 20);
				contentPane.add(comboTransacao);
				comboTransacao.setVisible(true);
			}
		});

		rdbtnCartaoSim.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				int state = event.getStateChange();
				if (state == ItemEvent.SELECTED) {
					comboCartao.setVisible(true);
				} else if (state == ItemEvent.DESELECTED) {
					comboCartao.setVisible(false);
				}
			}
		});

		JLabel lblCategoria = new JLabel("Categoria:");
		lblCategoria.setBounds(10, 111, 75, 14);
		contentPane.add(lblCategoria);

		JComboBox<String> comboCategoria = new JComboBox<String>(
				TransacaoController.listarCategorias());
		comboCategoria.setBounds(95, 108, 142, 20);
		contentPane.add(comboCategoria);
		comboCategoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				categoria = comboCategoria.getSelectedItem().toString();
				comboTransacao.setVisible(false);
				sorter = new ArrayList<String>(TransacaoController.listar(data,
						categoria, descricao, parcelas, nomeCartao).keySet());
				sorter.sort(null);
				comboTransacao = new JComboBox(sorter.toArray());
				comboTransacao.setBounds(167, 8, 357, 20);
				contentPane.add(comboTransacao);
				comboTransacao.setVisible(true);
			}
		});

		JLabel lblDescricao = new JLabel("Descrição:");
		lblDescricao.setBounds(10, 136, 75, 14);
		contentPane.add(lblDescricao);

		textDescricao = new JTextField();
		textDescricao.setBounds(95, 133, 310, 20);
		contentPane.add(textDescricao);
		textDescricao.setColumns(10);
		textDescricao.addFocusListener(descricaoFocusListener);

		JButton btnSelecionar = new JButton(operacao);
		btnSelecionar.setBounds(429, 39, 95, 23);
		contentPane.add(btnSelecionar);
		btnSelecionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (operacao.equals("Alterar")) {
					alterarTransacao = new CadastroTransacao(operacao,
							TransacaoController.buscar(TransacaoController
									.listar(data, categoria, descricao,
											parcelas, nomeCartao).get(
											comboTransacao.getSelectedItem()
													.toString())));
					alterarTransacao.setVisible(true);
					textDescricao.setText("");
					comboCategoria.setSelectedItem(null);
					groupCartaoSN.clearSelection();
					comboCartao.setSelectedIndex(0);
					comboCartao.setVisible(false);
					groupParceladoSN.clearSelection();
					lblQuantasVezes.setVisible(false);
					formattedTextQuantasVezes.setText("");
					formattedTextQuantasVezes.setVisible(false);
					dispose();
				} else {
					int selectedOption = JOptionPane.showConfirmDialog(null,
							"Tem certeza de que quer excluir a transação "
									+ comboTransacao.getSelectedItem()
											.toString() + "?",
							"Confirmar exclusão", JOptionPane.YES_NO_OPTION);
					if (selectedOption == JOptionPane.YES_OPTION) {
						TransacaoController.deletar(TransacaoController
								.buscar(TransacaoController.listar(data,
										categoria, descricao, parcelas,
										nomeCartao).get(
										comboTransacao.getSelectedItem()
												.toString())));
						// recarrega a pasta
						TransacaoController.recarregarPasta();
						JOptionPane.showMessageDialog(null,
								"Operação realizada com sucesso!");
						comboTransacao.setVisible(false);
						comboData = new JComboBox<String>(DataController
								.listar(null));
						data = comboData.getSelectedItem().toString();
						sorter = new ArrayList<String>(TransacaoController
								.listar(data, categoria, descricao, parcelas,
										nomeCartao).keySet());
						sorter.sort(null);
						comboTransacao = new JComboBox(sorter.toArray());
						comboTransacao.setBounds(167, 8, 357, 20);
						contentPane.add(comboTransacao);
						comboTransacao.setVisible(true);
					}
				}
			}
		});

		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.setBounds(429, 82, 95, 23);
		contentPane.add(btnLimpar);
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textDescricao.setText("");
				comboCategoria.setSelectedItem(null);
				groupCartaoSN.clearSelection();
				comboCartao.setSelectedIndex(0);
				comboCartao.setVisible(false);
				groupParceladoSN.clearSelection();
				lblQuantasVezes.setVisible(false);
				formattedTextQuantasVezes.setText("");
				formattedTextQuantasVezes.setVisible(false);
			}
		});

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(429, 127, 95, 23);
		contentPane.add(btnCancelar);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
}