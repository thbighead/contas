package view;

//import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DecimalFormat;
import java.text.ParseException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import controller.CartaoController;
import controller.DataController;
import controller.DinheiroController;
import controller.TransacaoController;

import javax.swing.JCheckBox;

import org.apache.poi.xssf.usermodel.XSSFRow;

import model.Cartao;
import model.Transacao;

import javax.swing.JSpinner;

public class CadastroTransacao extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1219072088014815979L;
	private JPanel contentPane;
	/**
	 * Components
	 */
	private JTextField textDescricao;
	private JCheckBox chckbxApenasDiaUtil;
	private JFormattedTextField formattedTextQuantasVezes;
	private JFormattedTextField formattedTextData;
	private JTextField textDataUtil;
	private JFormattedTextField formattedTextValor;
	private JComboBox<String> comboCategoria;
	private JRadioButton rdbtnCartaoSim;
	private JRadioButton rdbtnCartaoNao;
	private ButtonGroup groupCartaoSN;
	private JComboBox<String> comboCartao;
	private JRadioButton rdbtnParceladoSim;
	private JRadioButton rdbtnParceladoNao;
	private ButtonGroup groupParceladoSN;
	private JLabel lblQuantasVezes;
	private JLabel lblParcelado;
	private JSpinner qtdDiasParaPagar;
	private JTextField textDataComDiasParaPagar;
	private JCheckBox chckbxDiasAdicionaisParaPagar;
	private JLabel lblDiasParaPagar;

	/**
	 * Define o que acontece quando o campo de data perde o foco: ao perde-lo,
	 * testa se o campo ainda tem algum caractere em branco (algum "_"), caso
	 * nao tenha, formata a data e testa se a opcao de "Apenas dia util" estah
	 * selecionada para gerar a data para o campo de dataUtil
	 */
	private FocusListener dataFocusListener = new FocusListener() {
		@Override
		public void focusLost(FocusEvent e) {
			if (!formattedTextData.getText().contains("_")) {
				formattedTextData.setText(DataController
						.formataData(formattedTextData.getText()));
				if (chckbxApenasDiaUtil.isSelected()) {
					textDataUtil.setText(DataController
							.primeiroDiaUtilAPartirDe(formattedTextData
									.getText()));
				}
				if (chckbxDiasAdicionaisParaPagar.isSelected()) {
					textDataComDiasParaPagar.setText(DataController
							.primeiroDiaUtilAPartirDe(DataController
									.somaDiasAData(formattedTextData.getText(),
											qtdDiasParaPagar.getValue()
													.toString())));
				}
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
	// CadastroTransacao frame = new CadastroTransacao(
	// "Cadastrar", null);
	// // CadastroTransacao frame = new CadastroTransacao("Alterar",
	// // TransacaoController.planilhaPasta.getRow(22));
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
	public CadastroTransacao(String operacao, XSSFRow linha) {
		/**
		 * Setting the window
		 */
		setTitle(operacao + " transação"); // define a operacao como titulo
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 640, 215);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Descricao da transacao
		JLabel lblDescricao = new JLabel("Descrição:");
		lblDescricao.setBounds(10, 11, 70, 14);
		contentPane.add(lblDescricao);

		textDescricao = new JTextField();
		textDescricao.setBounds(90, 8, 180, 20);
		contentPane.add(textDescricao);
		textDescricao.setColumns(10);

		// Categoria da transacao
		JLabel lblCategoria = new JLabel("Categoria:");
		lblCategoria.setBounds(10, 36, 70, 14);
		contentPane.add(lblCategoria);

		comboCategoria = new JComboBox<String>();
		comboCategoria.setModel(new DefaultComboBoxModel<String>(
				TransacaoController.listarCategorias()));
		comboCategoria.setBounds(90, 33, 180, 20);
		contentPane.add(comboCategoria);

		// Data real da transacao
		JLabel lblData = new JLabel("Data:");
		lblData.setBounds(10, 61, 46, 14);
		contentPane.add(lblData);

		MaskFormatter fmtData = null;
		try {
			fmtData = new MaskFormatter("##/##/##");
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
		fmtData.setPlaceholderCharacter('_');
		formattedTextData = new JFormattedTextField(fmtData);
		formattedTextData.setBounds(90, 58, 180, 20);
		contentPane.add(formattedTextData);
		formattedTextData.addFocusListener(dataFocusListener);

		// Possivel cartao usado na transacao
		JLabel lblCartao = new JLabel("Cartão?");
		lblCartao.setBounds(299, 11, 46, 14);
		contentPane.add(lblCartao);

		rdbtnCartaoSim = new JRadioButton("Sim");
		rdbtnCartaoSim.setBounds(351, 7, 55, 23);
		contentPane.add(rdbtnCartaoSim);

		rdbtnCartaoNao = new JRadioButton("Não");
		rdbtnCartaoNao.setBounds(408, 7, 55, 23);
		contentPane.add(rdbtnCartaoNao);

		groupCartaoSN = new ButtonGroup();
		groupCartaoSN.add(rdbtnCartaoSim);
		groupCartaoSN.add(rdbtnCartaoNao);

		comboCartao = new JComboBox<String>();
		comboCartao.setModel(new DefaultComboBoxModel<String>(CartaoController
				.listar(0, 0)));
		comboCartao.setBounds(469, 8, 145, 20);
		contentPane.add(comboCartao);
		comboCartao.setVisible(false);

		// Possivel geracao de data efetiva somente em dias uteis
		chckbxApenasDiaUtil = new JCheckBox("Apenas Dia Útil");
		chckbxApenasDiaUtil.setBounds(276, 57, 115, 23);
		contentPane.add(chckbxApenasDiaUtil);
		chckbxApenasDiaUtil.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				int state = event.getStateChange();
				if (state == ItemEvent.SELECTED) {
					groupCartaoSN.setSelected(rdbtnCartaoNao.getModel(), true);
					comboCartao.setSelectedItem(null);
					textDataUtil.setVisible(true);
					if (!formattedTextData.getText().contains("_")) {
						textDataUtil.setText(DataController
								.primeiroDiaUtilAPartirDe(formattedTextData
										.getText()));
					}
				} else if (state == ItemEvent.DESELECTED) {
					textDataUtil.setVisible(false);
				}
			}
		});

		textDataUtil = new JTextField();
		textDataUtil.setBounds(397, 58, 180, 20);
		contentPane.add(textDataUtil);
		textDataUtil.setColumns(10);
		textDataUtil.setEnabled(false);
		textDataUtil.setVisible(false);

		// Valor da transacao
		JLabel lblValor = new JLabel("Valor:");
		lblValor.setBounds(10, 86, 46, 14);
		contentPane.add(lblValor);

		DecimalFormat fmtDecimal = new DecimalFormat("0.00");
		NumberFormatter fmtValor = new NumberFormatter(fmtDecimal);
		formattedTextValor = new JFormattedTextField(fmtValor);
		formattedTextValor.setBounds(90, 83, 180, 20);
		contentPane.add(formattedTextValor);

		rdbtnCartaoSim.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				int state = event.getStateChange();
				if (state == ItemEvent.SELECTED) {
					comboCartao.setVisible(true);
					chckbxApenasDiaUtil.setSelected(false);
					textDataUtil.setText("");
				} else if (state == ItemEvent.DESELECTED) {
					comboCartao.setSelectedIndex(0);
					comboCartao.setVisible(false);
				}
			}
		});

		// Possivel geracao automatica para dividir a transacao em parcelas
		lblParcelado = new JLabel("Parcelado?");
		lblParcelado.setBounds(280, 86, 65, 14);
		contentPane.add(lblParcelado);

		rdbtnParceladoSim = new JRadioButton("Sim");
		rdbtnParceladoSim.setBounds(351, 82, 55, 23);
		contentPane.add(rdbtnParceladoSim);

		rdbtnParceladoNao = new JRadioButton("Não");
		rdbtnParceladoNao.setBounds(408, 82, 55, 23);
		contentPane.add(rdbtnParceladoNao);

		groupParceladoSN = new ButtonGroup();
		groupParceladoSN.add(rdbtnParceladoSim);
		groupParceladoSN.add(rdbtnParceladoNao);

		lblQuantasVezes = new JLabel("Quantas vezes?");
		lblQuantasVezes.setBounds(469, 86, 95, 14);
		contentPane.add(lblQuantasVezes);
		lblQuantasVezes.setVisible(false);

		NumberFormatter fmtQtdVezes;
		DecimalFormat fmtInteger = new DecimalFormat("0");
		fmtQtdVezes = new NumberFormatter(fmtInteger);
		fmtQtdVezes.setMinimum(1);
		formattedTextQuantasVezes = new JFormattedTextField(fmtQtdVezes);
		formattedTextQuantasVezes.setBounds(574, 83, 40, 20);
		contentPane.add(formattedTextQuantasVezes);
		formattedTextQuantasVezes.setColumns(10);
		formattedTextQuantasVezes.setVisible(false);

		rdbtnParceladoSim.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				int state = event.getStateChange();
				if (state == ItemEvent.SELECTED) {
					lblQuantasVezes.setVisible(true);
					formattedTextQuantasVezes.setVisible(true);
				} else if (state == ItemEvent.DESELECTED) {
					lblQuantasVezes.setVisible(false);
					formattedTextQuantasVezes.setText("");
					formattedTextQuantasVezes.setVisible(false);
				}
			}
		});

		// Possivel quantidade de dias diferenciada para começar o pagamento
		chckbxDiasAdicionaisParaPagar = new JCheckBox(
				"Dias adicionais para pagar?");
		chckbxDiasAdicionaisParaPagar.setBounds(10, 107, 190, 23);
		contentPane.add(chckbxDiasAdicionaisParaPagar);

		lblDiasParaPagar = new JLabel("Dias para pagar:");
		lblDiasParaPagar.setBounds(206, 110, 95, 14);
		contentPane.add(lblDiasParaPagar);
		lblDiasParaPagar.setVisible(false);

		SpinnerModel SMQtdDiasParaPagar = new SpinnerNumberModel(60, 60, 360,
				30);
		qtdDiasParaPagar = new JSpinner(SMQtdDiasParaPagar);
		qtdDiasParaPagar.setBounds(305, 108, 40, 20);
		contentPane.add(qtdDiasParaPagar);
		qtdDiasParaPagar.setVisible(false);

		textDataComDiasParaPagar = new JTextField();
		textDataComDiasParaPagar.setBounds(397, 108, 180, 20);
		contentPane.add(textDataComDiasParaPagar);
		textDataComDiasParaPagar.setColumns(10);
		textDataComDiasParaPagar.setEnabled(false);
		textDataComDiasParaPagar.setVisible(false);

		chckbxDiasAdicionaisParaPagar.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				int state = event.getStateChange();
				if (state == ItemEvent.SELECTED) {
					lblDiasParaPagar.setVisible(true);
					qtdDiasParaPagar.setVisible(true);
					textDataComDiasParaPagar.setVisible(true);
					chckbxApenasDiaUtil.setSelected(false);
					chckbxApenasDiaUtil.setEnabled(false);
				} else if (state == ItemEvent.DESELECTED) {
					chckbxApenasDiaUtil.setEnabled(true);
					lblDiasParaPagar.setVisible(false);
					qtdDiasParaPagar.setVisible(false);
					textDataComDiasParaPagar.setVisible(false);
					qtdDiasParaPagar.setValue(60);
					textDataComDiasParaPagar.setText("");
				}
			}
		});

		// Alterar?
		alterarLoad(operacao, linha);

		// Botoes
		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.setBounds(10, 143, 89, 23);
		contentPane.add(btnLimpar);
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});

		JButton btnSalvar = new JButton(operacao);
		btnSalvar.setBounds(519, 143, 95, 23);
		contentPane.add(btnSalvar);
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (seAlgumCampoEmBranco()) {
					alertMessage("Preencha os campos em branco");
				} else {
					/**
					 * Monta a nova transacao, seja para cadastrar ou para
					 * alterar. Ateh esse momento as datas real e efetiva sao
					 * iguais (a real)
					 */
					Transacao nova = new Transacao(comboCategoria
							.getSelectedItem().toString(), textDescricao
							.getText().toLowerCase(), formattedTextData
							.getText(), formattedTextData.getText(),
							DinheiroController
									.stringToDinheiro(formattedTextValor
											.getText()));
					Transacao[] novas = null;

					if (operacao.equals("Alterar")) {
						if (ehDeCartao()) {
							Cartao card = CartaoController
									.getCartao(CartaoController
											.buscar(comboCartao
													.getSelectedItem()
													.toString()));
							nova.transaCartao(card);
						}
						// altera
						TransacaoController.alterar(linha, nova);
					} else {
						if (chckbxDiasAdicionaisParaPagar.isSelected()) {
							nova.dataEfetiva = DataController
									.stringToCalendar(textDataComDiasParaPagar
											.getText());
						}
						if (ehParcelado() && ehDeCartao()) {
							/**
							 * Monta o cartao selecionado
							 */
							Cartao card = CartaoController
									.getCartao(comboCartao.getSelectedItem()
											.toString());
							novas = nova.transaCartaoParcelada(card,
									formattedTextQuantasVezes.getText());
						} else if (!ehParcelado() && ehDeCartao()) {
							/**
							 * Monta o cartao selecionado
							 */
							Cartao card = CartaoController
									.getCartao(CartaoController
											.buscar(comboCartao
													.getSelectedItem()
													.toString()));
							nova.transaCartao(card);
						} else if (ehParcelado() && !ehDeCartao()) {
							novas = nova.parcelar(formattedTextQuantasVezes
									.getText());
						} else {
							if (ehApenasDeDiasUteis()) {
								nova.dataEfetiva = DataController
										.stringToCalendar(textDataUtil
												.getText());
							}
						}
						/**
						 * Cadastra a transacao ou as transacoes em caso de
						 * parcelamento
						 */
						if (novas == null) {
							TransacaoController.cadastrar(nova);
						} else {
							TransacaoController.cadastrar(novas);
						}
					}

					TransacaoController.recarregarPasta(); // recarrega a pasta
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
		btnCancelar.setBounds(271, 143, 89, 23);
		contentPane.add(btnCancelar);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}

	/**
	 * Verdadeiro se a transacao setada for parcelada
	 */
	private boolean ehParcelado() {
		return ((groupParceladoSN.isSelected(rdbtnParceladoSim.getModel()) && (Integer
				.parseInt(formattedTextQuantasVezes.getText()) > 1)));
	}

	/**
	 * Verdadeiro se a transacao setada for de cartao
	 */
	private boolean ehDeCartao() {
		return (comboCartao.getSelectedItem() != null)
				&& (groupCartaoSN.isSelected(rdbtnCartaoSim.getModel()));
	}

	/**
	 * Mostra mensagem de alerta com a opcao "OK"
	 */
	private void alertMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	/**
	 * Carrega dados caso seja uma operacao de alteracao
	 */
	private void alterarLoad(String operacao, XSSFRow linha) {
		if (operacao.equals("Alterar")) {
			// Recupera a data real da transacao
			String dataReal;
			if (!(dataReal = linha.getCell(0).getDateCellValue().toString())
					.isEmpty()) {
				formattedTextData.setText(dataReal);
			}
			// Recupera a descricao da transacao
			String descricao;
			if (!(descricao = linha.getCell(5).getStringCellValue()).isEmpty()) {
				String dataEfetiva = linha.getCell(1).getDateCellValue()
						.toString();
				// Recupera o cartao usado na transacao
				String cartao;
				if ((cartao = TransacaoController.seTransaCartao(descricao)) != null) {
					descricao = descricao.substring(descricao.indexOf("-") + 1);
					groupCartaoSN.setSelected(rdbtnCartaoSim.getModel(), true);
					comboCartao.setSelectedItem(cartao);
					comboCartao.setVisible(true);
				} else if (!TransacaoController.seTransParcelada(descricao)
						&& !dataReal.equals(dataEfetiva)) {
					// Recupera a data efetiva da transacao
					chckbxApenasDiaUtil.setSelected(true);
					textDataUtil.setText(dataEfetiva);
					textDataUtil.setVisible(true);
				}
				textDescricao.setText(descricao);
			}
			// Recupera a categoria da transacao
			String categoria;
			if (!(categoria = linha.getCell(6).getStringCellValue()).isEmpty()) {
				comboCategoria.setSelectedItem(categoria);
			}
			// Recupera o valor da transacao
			double valor;
			if ((valor = linha.getCell(4).getNumericCellValue()) != 0) {
				formattedTextValor.setText(DinheiroController
						.dinheiroToString(valor));
			}
			/**
			 * Alteracao nao manipula todas as parcelas ao mesmo tempo, cada
			 * parcela deve ser alterada como uma transacao individual.
			 */
			lblParcelado.setVisible(false);
			rdbtnParceladoSim.setVisible(false);
			rdbtnParceladoNao.setVisible(false);
		}
	}

	/**
	 * Limpa todos os campos da janela
	 */
	private void limparCampos() {
		textDescricao.setText("");
		comboCategoria.setSelectedItem(null);
		formattedTextData.setText("");
		formattedTextValor.setText("");
		groupCartaoSN.clearSelection();
		comboCartao.setSelectedIndex(0);
		comboCartao.setVisible(false);
		groupParceladoSN.clearSelection();
		chckbxApenasDiaUtil.setSelected(false);
		textDataUtil.setText("");
		textDataUtil.setVisible(false);
		lblQuantasVezes.setVisible(false);
		formattedTextQuantasVezes.setText("");
		formattedTextQuantasVezes.setVisible(false);
		chckbxDiasAdicionaisParaPagar.setSelected(false);
		lblDiasParaPagar.setVisible(false);
		qtdDiasParaPagar.setVisible(false);
		textDataComDiasParaPagar.setVisible(false);
		qtdDiasParaPagar.setValue(60);
		textDataComDiasParaPagar.setText("");
	}

	/**
	 * Testa se tem algum campo obrigatorio em branco
	 */
	private boolean seAlgumCampoEmBranco() {
		return (textDescricao.getText().isEmpty())
				|| (comboCategoria.getSelectedItem() == null)
				|| (formattedTextData.getText().isEmpty())
				|| (formattedTextValor.getText().isEmpty())
				|| ((groupParceladoSN.isSelected(rdbtnParceladoSim.getModel())) && (formattedTextQuantasVezes
						.getText().isEmpty()))
				|| ((groupCartaoSN.isSelected(rdbtnCartaoSim.getModel()) && (comboCartao
						.getSelectedItem() == null)));
	}

	/**
	 * Verdadeiro se a transacao setada for apenas de dias uteis
	 */
	private boolean ehApenasDeDiasUteis() {
		return chckbxApenasDiaUtil.isSelected()
				&& !textDataUtil.getText().isEmpty();
	}
}