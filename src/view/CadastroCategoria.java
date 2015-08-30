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

import controller.TransacaoController;

public class CadastroCategoria extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 53506387969368257L;
	private JPanel contentPane;
	private JTextField textCategoria;

	/**
	 * Launch the application.
	 */
	// public static void main(String[] args) {
	// EventQueue.invokeLater(new Runnable() {
	// public void run() {
	// try {
	// CadastroCategoria frame = new CadastroCategoria();
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
	public CadastroCategoria() {
		setTitle("Cadastro da Categoria");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 390, 110);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNomeDaCategoria = new JLabel("Nome da Categoria:");
		lblNomeDaCategoria.setBounds(10, 11, 120, 14);
		contentPane.add(lblNomeDaCategoria);

		textCategoria = new JTextField();
		textCategoria.setBounds(140, 8, 224, 20);
		contentPane.add(textCategoria);
		textCategoria.setColumns(10);

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(85, 39, 89, 23);
		contentPane.add(btnSalvar);
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textCategoria.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Preencha os campos em branco");
				} else {
					TransacaoController.cadastrarCategoria(textCategoria
							.getText());
					JOptionPane.showMessageDialog(null,
							"Operação realizada com sucesso!");
					dispose();
				}
			}
		});

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(196, 39, 89, 23);
		contentPane.add(btnCancelar);
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}

}
