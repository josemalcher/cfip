package open.digytal.desktop.cfip;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import org.springframework.stereotype.Component;

import open.digytal.SpringBootApp;
import open.digytal.util.Imagem;
import open.digytal.util.desktop.Formulario;
import open.digytal.util.desktop.MDI;

@Component
public class MDICfip extends MDI {
	//private AnnotationConfigApplicationContext context;
	public MDICfip() {
		setTitle("CFIP - Controle Financeiro Pessoal");
		JMenu mnCadastros = new JMenu("Cadastros");
		mnCadastros.setIcon(Imagem.png( "0cadastros"));
		JMenuItem mnConta = new JMenuItem("Conta");
		mnConta.setIcon(Imagem.png("conta"));
		mnConta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				exibirConta();
				//exibirBean("frmContas");
			}
		});
		mnCadastros.add(mnConta);

		JMenuItem mnNatureza = new JMenuItem("Natureza");
		mnNatureza.setIcon(Imagem.png("natureza"));
		mnNatureza.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				exibirNatureza();
			}
		});

		mnCadastros.add(mnNatureza);

		JMenuItem mnDespesasRapidas = new JMenuItem("Despesa Rápida");
		mnDespesasRapidas.setIcon(Imagem.png("despesarapida"));
		mnDespesasRapidas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				exibirDespesasRapidas();
			}
		});
		mnCadastros.add(mnDespesasRapidas);
		
		JMenuItem mntmContato = new JMenuItem("Contato");
		mntmContato.setIcon(Imagem.png("contato"));
		mntmContato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				exibirContatos();
			}
		});
		
		JMenuItem mntmFatura = new JMenuItem("Fatura");
		mntmFatura.setIcon(Imagem.png("fatura"));
		mntmFatura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				//exibirFatura();
			}
		});
		//mnCadastros.add(mntmFatura);
		mnCadastros.add(mntmContato);
		

		JMenu mnLancamentos = new JMenu("Lançamentos");
		mnLancamentos.setIcon(Imagem.png("1lancamentos"));
		

		JMenuItem mnDespesaRapida = new JMenuItem("Despesa Rápida");
		mnDespesaRapida.setIcon(Imagem.png("despesarapida"));
		mnDespesaRapida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				exibirLactoDespesaRapida();
			}
		});
		mnLancamentos.add(mnDespesaRapida);

		JMenuItem mnReceitas = new JMenuItem("Receitas");
		mnReceitas.setIcon(Imagem.png("receita"));
		mnLancamentos.add(mnReceitas);
		mnReceitas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				exibirLactoReceita();
			}
		});

		JMenuItem mnPrevisoes = new JMenuItem("Previsões");
		mnPrevisoes.setIcon(Imagem.png("previsao"));
		mnPrevisoes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				exibirLanctoPrevisao();
			}
		});

		JMenuItem mnDespesas = new JMenuItem("Despesas");
		mnDespesas.setIcon(Imagem.png("despesa"));
		mnLancamentos.add(mnDespesas);
		mnDespesas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				exibirLanctoDespesa();
			}
		});
		JMenuItem mnTranferencias = new JMenuItem("Transferências");
		mnTranferencias.setIcon(Imagem.png("transferencia"));
		mnTranferencias.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				exibirTransferencia();
			}
		});
		mnLancamentos.add(mnTranferencias);
		mnLancamentos.add(mnPrevisoes);

		JMenuItem mnFaturas = new JMenuItem("Faturas");
		mnFaturas.setIcon(Imagem.png("fatura"));
		mnFaturas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				//exibirFaturas();
			}
		});
		mnLancamentos.add(mnFaturas);

		JMenu mnConsultas = new JMenu("Consultas");
		mnConsultas.setIcon(Imagem.png("2consultas"));
		

		JMenuItem mnConsultaLancamentos = new JMenuItem("Lançamentos");
		mnConsultaLancamentos.setIcon(Imagem.png("dinheiro"));
		mnConsultaLancamentos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				exibirConsultaLancamentos();
			}
		});
		mnConsultas.add(mnConsultaLancamentos);

		JMenuItem mnConsultaPrevisoes = new JMenuItem("Previsões");
		mnConsultaPrevisoes.setIcon(Imagem.png("calendario10"));
		mnConsultaPrevisoes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				exibirConsultaPrevisoes();
			}
		});

		mnConsultas.add(mnConsultaPrevisoes);
		
		JMenuItem mnProjecoes = new JMenuItem("Projeções");
		mnProjecoes.setIcon(Imagem.png("projecao"));
		mnProjecoes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				exibirProjecoes();
			}
		});
		mnConsultas.add(mnProjecoes);
		
		JMenuItem mnMovimentacoes = new JMenuItem("Movimentações");
		mnMovimentacoes.setIcon(Imagem.png("resumo"));
		mnMovimentacoes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				exibirMovimentacoes();
			}
		});
		mnConsultas.add(mnMovimentacoes);

		JMenu mnRelatorios = new JMenu("Relatórios");
		mnRelatorios.setIcon(Imagem.png( "3relatorios"));
		
		JMenu mnAjuda = new JMenu("Ajuda");
		mnAjuda.setIcon(Imagem.png( "5ajuda"));
		
		
		JMenu mnFerramentas = new JMenu("Ajustes");
		mnFerramentas.setIcon(Imagem.png("4ajustes"));
		
		
		JMenuItem mntmManual = new JMenuItem("Manual 1.0");
		mntmManual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//exibirManual();
			}

		});
		mntmManual.setIcon(Imagem.png("informacao"));
		mnAjuda.add(mntmManual);
		
		JMenuItem mntmUsurio = new JMenuItem("Usuário");
		mntmUsurio.setIcon(Imagem.png("cardeit"));
		mntmUsurio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				//exibirUsuario();
			}
		});
		mnFerramentas.add(mntmUsurio);
		
		JSeparator separator_2 = new JSeparator();
		mnFerramentas.add(separator_2);
		
		JMenu mnConexoes = new JMenu("Conexões");
		mnFerramentas.add(mnConexoes);
		
		JMenuItem mntmConexo = new JMenuItem("Banco Dados");
		mnConexoes.add(mntmConexo);
		mnConexoes.setIcon(Imagem.png("conexao"));
		mntmConexo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				exibirConfiguracao();
			}
		});
		mntmConexo.setIcon(Imagem.png("dbconexao"));
		
		JSeparator separator = new JSeparator();
		mnFerramentas.add(separator);
		
		JMenu mnBancoDados = new JMenu("Banco Dados");
		mnBancoDados.setIcon(Imagem.png("dbajuste"));
		mnFerramentas.add(mnBancoDados);
		
		JMenuItem mnBackup = new JMenuItem("Backup");
		mnBancoDados.add(mnBackup);
		mnBackup.setIcon(Imagem.png("backup"));
		
		JMenuItem mnRestaurar = new JMenuItem("Restore");
		mnRestaurar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				exibirRestore();
			}
		});
		mnBancoDados.add(mnRestaurar);
		mnRestaurar.setIcon(Imagem.png("restaurar"));
		
		JSeparator separator_1 = new JSeparator();
		mnBancoDados.add(separator_1);
		
		JMenuItem mnSql = new JMenuItem("SQL");
		mnSql.setIcon(Imagem.png("executar"));
		mnSql.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				//sql();
			}
		});
		
		mnBancoDados.add(mnSql);
		mnBackup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				exibirBackup();
			}
		});
		getBarraMenu().add(mnCadastros);
		getBarraMenu().add(mnLancamentos);
		getBarraMenu().add(mnConsultas);
		getBarraMenu().add(mnRelatorios);
		getBarraMenu().add(mnFerramentas);
		getBarraMenu().add(mnAjuda);
	}
	private void exibirConfiguracao() {
		//FrmConfiguracao.instance();
	}
	private void exibirDespesasRapidas() {
		//exibir((Formulario)SpringBootApp.getBean(FrmDespesasRapidas.class));
	}
	private void exibirConta() {
		exibir((Formulario)SpringBootApp.getBean(FrmContas.class));
	}
	private void exibirNatureza() {
		exibir((Formulario)SpringBootApp.getBean(FrmNaturezas.class));
	}
	private void exibirContatos() {
		//exibir((Formulario)SpringBootApp.getBean(FrmContatos.class));
	}
	private void exibirTransferencia() {
		//exibir((Formulario)SpringBootApp.getBean(FrmLancamentoTransferencia.class));
	}
	private void exibirProjecoes() {
		//exibir((Formulario)SpringBootApp.getBean(FrmProjecoes.class));
	}
	private void exibirMovimentacoes() {
		//exibir((Formulario)SpringBootApp.getBean(FrmMovimentacoes.class));
	}
	private void exibirLactoDespesaRapida() {
		//exibir((Formulario)SpringBootApp.getBean(FrmLancamentoDespesaRapida.class));
	}
	private void exibirLactoReceita() {
		//exibir((Formulario)SpringBootApp.getBean(FrmLancamentoCredito.class));
	}
	private void exibirLanctoDespesa() {
		//exibir((Formulario)SpringBootApp.getBean(FrmLancamentoDebito.class));
	}
	private void exibirLanctoPrevisao() {
		//exibir((Formulario)SpringBootApp.getBean(FrmLancamentoPrevisao.class));
	}
	private void exibirConsultaLancamentos() {
		//exibir((Formulario) SpringBootApp.getBean(FrmLancamentos.class));
	}
	private void exibirConsultaPrevisoes() {
		//exibir((Formulario)SpringBootApp.getBean(FrmPrevisoes.class));
	}
	private void exibirBackup() {
		//exibir((Formulario)SpringBootApp.getBean(FrmBackup.class));
	}
	private void exibirRestore() {
		//exibir((Formulario)SpringBootApp.getBean(FrmRestore.class));
	}
	/*private void exibirUsuario() {
		Usuario perfil = (Usuario) MDI.getPerfil();
		FrmUsuario frm = SpringDesktopApp.getBean(FrmUsuario.class);
		FrmCfipLogin login = SpringDesktopApp.getBean(FrmCfipLogin.class);
		login.abrirCadastroPerfil(frm, perfil);
	}
	private void exibirConfiguracao() {
		DesktopApp.exibirConfiguracao();
	}*/
	/*private void sql() {
		//http://www.avajava.com/tutorials/lessons/how-do-i-run-another-application-from-java.html
		try {
		if(SSMensagem.pergunta("Esta operação encerra a aplicação\nDeseja prosseguir?")) {
			//SpringDesktopApp.closeContext();
			openLocalDb();
			this.dispose();
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	/*private static void openLocalDb() {
		DatabaseManagerSwing.main(new String[] { "--url", "jdbc:hsqldb:file:/cfip/database/cfipdb", "--user", "sa", "--password", "sa"});
		
	}*/
	
	
}
