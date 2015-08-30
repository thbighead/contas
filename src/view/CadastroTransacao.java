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
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JRadioButton;
import javax.swing.JButton;

import controller.CartaoController;
import controller.DataController;
import controller.DinheiroController;
import controller.TransacaoController;

import javax.swing.JCheckBox;

import org.apache.poi.xssf.usermodel.XSSFRow;

import model.Cartao;
import model.Transacao;

public class CadastroTransacao extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1219072088014815979L;
	private JPanel contentPane;
	private JTextField textDescricao;
	JCheckBox chckbxApenasDiaUtil;
	private JFormattedTextField formattedTextQuantasVezes;
	private JFormattedTextField formattedTextData;
	private JTextField textDataUtil;
	private FocusListener dataFocusListener = new FocusListener() {
		@Override
		public void focusLost(FocusEvent e) {
			if (!formattedTextData.getText().contains("_")) {
				formattedTextData.setText(DataController
						.formataData(formattedTextData.getText()));
				if (chckbxApenasDiaUtil.isSelected()) {
					textDataUtil.setText(DataController
							.calendarToString(DataController
									.primeiroDiaUtilDepoisDe(DataController
											.stringToCalendar(formattedTextData
													.getText()))));
				}
			}
		}

		@Override
		public void focusGained(FocusEvent e) {
		}
	};
	private JFormattedTextField formattedTextValor;
	Calendar c = Calendar.getInstance();

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
		setTitle(operacao + " transação");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 640, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblDescricao = new JLabel("Descrição:");
		lblDescricao.setBounds(10, 11, 70, 14);
		contentPane.add(lblDescricao);

		textDescricao = new JTextField();
		textDescricao.setBounds(90, 8, 180, 20);
		contentPane.add(textDescricao);
		textDescricao.setColumns(10);
		if ((operacao == "Alterar")
				&& (!linha.getCell(5).getStringCellValue().isEmpty())) {
			String getDescricao = linha.getCell(5).getStringCellValue();
			if (TransacaoController.seTransaCartao(getDescricao) != null) {
				getDescricao = getDescricao
						.substring(getDescricao.indexOf("-") + 1);
			}
			textDescricao.setText(getDescricao);
		}

		JLabel lblCategoria = new JLabel("Categoria:");
		lblCategoria.setBounds(10, 36, 70, 14);
		contentPane.add(lblCategoria);

		JComboBox<String> comboCategoria = new JComboBox<String>(
				TransacaoController.listarCategorias());
		comboCategoria.setBounds(90, 33, 180, 20);
		contentPane.add(comboCategoria);
		if ((operacao == "Alterar")
				&& (!linha.getCell(6).getStringCellValue().isEmpty())) {
			comboCategoria.setSelectedItem(linha.getCell(6)
					.getStringCellValue());
		}

		JLabel lblData = new JLabel("Data:");
		lblData.setBounds(10, 61, 46, 14);
		contentPane.add(lblData);

		MaskFormatter fmtData = null;
		try {
			fmtData = new MaskFormatter("##/##/####");
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		fmtData.setPlaceholderCharacter('_');
		formattedTextData = new JFormattedTextField(fmtData);
		formattedTextData.setBounds(90, 58, 180, 20);
		contentPane.add(formattedTextData);
		formattedTextData.addFocusListener(dataFocusListener);
		if ((operacao == "Alterar")
				&& (!linha.getCell(0).getDateCellValue().toString().isEmpty())) {
			c.setTime(linha.getCell(0).getDateCellValue());
			formattedTextData.setText(DataController.calendarToString(c));
		}

		JLabel lblCartao = new JLabel("Cartão?");
		lblCartao.setBounds(299, 11, 46, 14);
		contentPane.add(lblCartao);

		JRadioButton rdbtnCartaoSim = new JRadioButton("Sim");
		rdbtnCartaoSim.setBounds(351, 7, 55, 23);
		contentPane.add(rdbtnCartaoSim);

		JRadioButton rdbtnCartaoNao = new JRadioButton("Não");
		rdbtnCartaoNao.setBounds(408, 7, 55, 23);
		contentPane.add(rdbtnCartaoNao);

		ButtonGroup groupCartaoSN = new ButtonGroup();
		groupCartaoSN.add(rdbtnCartaoSim);
		groupCartaoSN.add(rdbtnCartaoNao);

		JComboBox<String> comboCartao = new JComboBox<String>(
				CartaoController.listar(0, 0));
		comboCartao.setBounds(469, 8, 145, 20);
		contentPane.add(comboCartao);
		comboCartao.setVisible(false);
		if ((operacao == "Alterar")
				&& (!linha.getCell(5).getStringCellValue().isEmpty())
				&& (TransacaoController.seTransaCartao(linha.getCell(5)
						.getStringCellValue()) != null)) {
			groupCartaoSN.setSelected(rdbtnCartaoSim.getModel(), true);
			comboCartao.setVisible(true);
			comboCartao.setSelectedItem(TransacaoController
					.seTransaCartao(linha.getCell(5).getStringCellValue()));
		}

		chckbxApenasDiaUtil = new JCheckBox("Apenas Dia Útil");
		chckbxApenasDiaUtil.setBounds(276, 57, 115, 23);
		contentPane.add(chckbxApenasDiaUtil);
		if ((operacao == "Alterar")
				&& (!linha.getCell(1).getDateCellValue().toString().isEmpty())
				&& (TransacaoController.seTransaCartao(linha.getCell(5)
						.getStringCellValue()) == null)) {
			chckbxApenasDiaUtil.setSelected(true);
		}
		chckbxApenasDiaUtil.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				int state = event.getStateChange();
				if (state == ItemEvent.SELECTED) {
					groupCartaoSN.setSelected(rdbtnCartaoNao.getModel(), true);
					comboCartao.setSelectedItem(null);
					textDataUtil.setVisible(true);
					if (!formattedTextData.getText().contains("_")) {
						textDataUtil.setText(DataController.calendarToString(DataController
								.primeiroDiaUtilDepoisDe(DataController
										.stringToCalendar(formattedTextData
												.getText()))));
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
		if ((operacao == "Alterar")
				&& (!linha.getCell(1).getDateCellValue().toString().isEmpty())
				&& (TransacaoController.seTransaCartao(linha.getCell(5)
						.getStringCellValue()) == null)) {
			c.setTime(linha.getCell(1).getDateCellValue());
			textDataUtil.setText(DataController.calendarToString(c));
			textDataUtil.setVisible(true);
		}

		JLabel lblValor = new JLabel("Valor:");
		lblValor.setBounds(10, 86, 46, 14);
		contentPane.add(lblValor);

		NumberFormatter fmtValor = new NumberFormatter(
				new DecimalFormat("#.##"));
		formattedTextValor = new JFormattedTextField(fmtValor);
		formattedTextValor.setBounds(90, 83, 180, 20);
		contentPane.add(formattedTextValor);
		if ((operacao == "Alterar")
				&& (linha.getCell(4).getNumericCellValue() != 0)) {
			formattedTextValor.setText(DinheiroController
					.dinheiroToString(linha.getCell(4).getNumericCellValue()));
		}

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

		JLabel lblParcelado = new JLabel("Parcelado?");
		lblParcelado.setBounds(280, 86, 65, 14);
		contentPane.add(lblParcelado);
		if (operacao.equals("Alterar")) {
			lblParcelado.setVisible(false);
		}

		JRadioButton rdbtnParceladoSim = new JRadioButton("Sim");
		rdbtnParceladoSim.setBounds(351, 82, 55, 23);
		contentPane.add(rdbtnParceladoSim);
		if (operacao.equals("Alterar")) {
			rdbtnParceladoSim.setVisible(false);
		}

		JRadioButton rdbtnParceladoNao = new JRadioButton("Não");
		rdbtnParceladoNao.setBounds(408, 82, 55, 23);
		contentPane.add(rdbtnParceladoNao);
		if (operacao.equals("Alterar")) {
			rdbtnParceladoNao.setVisible(false);
		}

		ButtonGroup groupParceladoSN = new ButtonGroup();
		groupParceladoSN.add(rdbtnParceladoSim);
		groupParceladoSN.add(rdbtnParceladoNao);

		JLabel lblQuantasVezes = new JLabel("Quantas vezes?");
		lblQuantasVezes.setBounds(469, 86, 95, 14);
		contentPane.add(lblQuantasVezes);
		lblQuantasVezes.setVisible(false);

		MaskFormatter fmtQtdVezes = null;
		try {
			fmtQtdVezes = new MaskFormatter("###");
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		fmtQtdVezes.setPlaceholderCharacter('0');
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

		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.setBounds(10, 130, 89, 23);
		contentPane.add(btnLimpar);
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
			}
		});

		JButton btnSalvar = new JButton(operacao);
		btnSalvar.setBounds(519, 130, 95, 23);
		contentPane.add(btnSalvar);
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String dataCalculada = formattedTextData.getText();
				if ((textDescricao.getText().isEmpty())
						|| (comboCategoria.getSelectedItem() == null)
						|| (formattedTextData.getText().isEmpty())
						|| (formattedTextValor.getText().isEmpty())
						|| ((groupParceladoSN.isSelected(rdbtnParceladoSim
								.getModel())) && (formattedTextQuantasVezes
								.getText().isEmpty()))
						|| ((groupCartaoSN.isSelected(rdbtnCartaoSim.getModel()) && (comboCartao
								.getSelectedItem() == null)))) {
					JOptionPane.showMessageDialog(null,
							"Preencha os campos em branco");
				} else {
					Transacao nova = new Transacao(comboCategoria
							.getSelectedItem().toString(), textDescricao
							.getText(), formattedTextData.getText(),
							DinheiroController
									.stringToDinheiro(formattedTextValor
											.getText()));

					if (operacao.equals("Alterar")) {
						if ((comboCartao.getSelectedItem() != null)
								&& (groupCartaoSN.isSelected(rdbtnCartaoSim
										.getModel()))) {
							Cartao card = CartaoController
									.getCartao(CartaoController
											.buscar(comboCartao
													.getSelectedItem()
													.toString()));
							nova.transaCartao(card);
							c = nova.data;
							nova.data = DataController
									.stringToCalendar(dataCalculada);
							dataCalculada = DataController.calendarToString(c);
						}
						TransacaoController.alterar(linha, nova, dataCalculada);
					} else {
						if ((comboCartao.getSelectedItem() != null)
								&& (groupCartaoSN.isSelected(rdbtnCartaoSim
										.getModel()))) {
							Cartao card = CartaoController
									.getCartao(CartaoController
											.buscar(comboCartao
													.getSelectedItem()
													.toString()));
							nova.transaCartao(card);
							c = nova.data;
							nova.data = DataController
									.stringToCalendar(dataCalculada);
							dataCalculada = DataController.calendarToString(c);
						}

						if ((Integer.parseInt(formattedTextQuantasVezes
								.getText()) > 1)
								&& (!formattedTextQuantasVezes.getText()
										.isEmpty())) {
							nova.data = DataController
									.stringToCalendar(dataCalculada);
							if (chckbxApenasDiaUtil.isSelected()
									&& !textDataUtil.getText().isEmpty()) {
								nova.data = DataController
										.stringToCalendar(textDataUtil
												.getText());
							}
							Transacao[] transacoes = nova.parcelar(Integer
									.parseInt(formattedTextQuantasVezes
											.getText()));

							for (Transacao transacao : transacoes) {
								if (chckbxApenasDiaUtil.isSelected()
										&& !textDataUtil.getText().isEmpty()) {
									transacao.data = DataController
											.primeiroDiaUtilDepoisDe(transacao.data);
								}
								TransacaoController.cadastrar(
										transacao,
										DataController
												.calendarToString(transacao.data));
							}
						} else {
							if (chckbxApenasDiaUtil.isSelected()
									&& !textDataUtil.getText().isEmpty()) {
								TransacaoController.cadastrar(nova,
										textDataUtil.getText());
							} else {
								TransacaoController.cadastrar(nova,
										dataCalculada);
							}
						}
					}
					JOptionPane.showMessageDialog(null,
							"Operação realizada com sucesso!");
				}
			}
		});

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(271, 130, 89, 23);
		contentPane.add(btnCancelar);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
}
