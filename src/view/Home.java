// versao 1.7
package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

public class Home extends JFrame {
	/**
	 * Todas as janelas que podem ser construidas
	 */
	private CadastroTransacao cadastrarNovaTransacao = null;
	private SelecionarTransacao selectTransacaoAlterar = null,
			selectTransacaoExcluir = null;
	private CadastroCartao cadastrarNovoCartao = null;
	private SelecionarCartao selectCartaoAlterar = null,
			selectCartaoExcluir = null;
	private CadastroCategoria cadastrarNovaCategoria = null;
	private ExcluirCategoria selectCategoriaExcluir = null;

	/**
	 * VERSAO
	 */
	private static final long serialVersionUID = -7286910487129241658L;
	private static final String version = "1.7";
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					@SuppressWarnings("unused")
					Home frame = new Home();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Home() {
		setTitle("Contas " + version);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 570, 155);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Transacao
		JButton btnNovaTransacao = new JButton("Nova Transação");
		btnNovaTransacao.setBounds(10, 11, 140, 23);
		contentPane.add(btnNovaTransacao);
		btnNovaTransacao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadastrarNovaTransacao = new CadastroTransacao("Cadastrar",
						null);
				cadastrarNovaTransacao.setVisible(true);
			}
		});

		JButton btnAlterarTransacao = new JButton("Alterar Transação");
		btnAlterarTransacao.setBounds(10, 45, 140, 23);
		contentPane.add(btnAlterarTransacao);
		btnAlterarTransacao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectTransacaoAlterar = new SelecionarTransacao("Alterar");
				selectTransacaoAlterar.setVisible(true);
			}
		});

		JButton btnExcluirTransacao = new JButton("Excluir Transação");
		btnExcluirTransacao.setBounds(10, 79, 140, 23);
		contentPane.add(btnExcluirTransacao);
		btnExcluirTransacao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectTransacaoExcluir = new SelecionarTransacao("Excluir");
				selectTransacaoExcluir.setVisible(true);
			}
		});

		// Cartao
		JButton btnCadastrarNovoCartao = new JButton("Cadastrar Novo Cartão");
		btnCadastrarNovoCartao.setBounds(170, 11, 170, 23);
		contentPane.add(btnCadastrarNovoCartao);
		btnCadastrarNovoCartao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadastrarNovoCartao = new CadastroCartao("Cadastrar", null);
				cadastrarNovoCartao.setVisible(true);
			}
		});

		JButton btnAlterarCartao = new JButton("Alterar Cartão");
		btnAlterarCartao.setBounds(170, 45, 170, 23);
		contentPane.add(btnAlterarCartao);
		btnAlterarCartao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectCartaoAlterar = new SelecionarCartao("Alterar");
				selectCartaoAlterar.setVisible(true);
			}
		});

		JButton btnExcluirCartao = new JButton("Excluir Cartão");
		btnExcluirCartao.setBounds(170, 79, 170, 23);
		contentPane.add(btnExcluirCartao);
		btnExcluirCartao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectCartaoExcluir = new SelecionarCartao("Excluir");
				selectCartaoExcluir.setVisible(true);
			}
		});

		// Categoria
		JButton btnCadastrarNovaCategoria = new JButton(
				"Cadastrar Nova Categoria");
		btnCadastrarNovaCategoria.setBounds(359, 11, 185, 23);
		contentPane.add(btnCadastrarNovaCategoria);
		btnCadastrarNovaCategoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cadastrarNovaCategoria = new CadastroCategoria();
				cadastrarNovaCategoria.setVisible(true);
			}
		});

		JButton btnExcluirCategoria = new JButton("Excluir Categoria");
		btnExcluirCategoria.setBounds(359, 45, 185, 23);
		contentPane.add(btnExcluirCategoria);
		btnExcluirCategoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectCategoriaExcluir = new ExcluirCategoria();
				selectCategoriaExcluir.setVisible(true);
			}
		});

		setVisible(true);
	}
}