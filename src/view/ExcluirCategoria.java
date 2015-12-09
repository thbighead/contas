package view;

//import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import controller.TransacaoController;

public class ExcluirCategoria extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7190644287867828998L;
	private JPanel contentPane;
	private JComboBox<String> comboCategoria;

	/**
	 * Launch the application.
	 */
	// public static void main(String[] args) {
	// EventQueue.invokeLater(new Runnable() {
	// public void run() {
	// try {
	// ExcluirCategoria frame = new ExcluirCategoria();
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
	public ExcluirCategoria() {
		setTitle("Excluir categoria");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 375, 110);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblCategoria = new JLabel("Categoria:");
		lblCategoria.setBounds(10, 11, 60, 14);
		contentPane.add(lblCategoria);

		comboCategoria = new JComboBox<String>();
		comboCategoria.setModel(new DefaultComboBoxModel<String>(
				TransacaoController.listarCategorias()));
		comboCategoria.setBounds(100, 8, 249, 20);
		contentPane.add(comboCategoria);

		JButton btnSelecionar = new JButton("Excluir");
		btnSelecionar.setBounds(62, 41, 89, 23);
		contentPane.add(btnSelecionar);
		btnSelecionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedOption = JOptionPane.showConfirmDialog(null,
						"Tem certeza de que quer excluir a categoria "
								+ comboCategoria.getSelectedItem().toString()
								+ "?", "Confirmar exclusão",
						JOptionPane.YES_NO_OPTION);
				if (selectedOption == JOptionPane.YES_OPTION) {
					TransacaoController.deletarCategoria(TransacaoController
							.buscarCategoria(comboCategoria.getSelectedItem()
									.toString()));
					TransacaoController.recarregarBase();
					JOptionPane.showMessageDialog(null,
							"Operação realizada com sucesso!");
					comboCategoria.setVisible(false);
					comboCategoria = new JComboBox<String>(TransacaoController
							.listarCategorias());
					comboCategoria.setBounds(100, 8, 249, 20);
					contentPane.add(comboCategoria);
					comboCategoria.setVisible(true);
				}
			}
		});

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(205, 41, 89, 23);
		contentPane.add(btnCancelar);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}

}
