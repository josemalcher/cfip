package open.digytal.desktop;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import open.digytal.CfipDesktopApp;
import open.digytal.model.entity.EntidadeConta;
import open.digytal.service.CadastroService;
import open.digytal.util.Formato;
import open.digytal.util.desktop.Formulario;
import open.digytal.util.desktop.ss.SSBotao;
import open.digytal.util.desktop.ss.SSCampoTexto;
import open.digytal.util.desktop.ss.SSGrade;
import open.digytal.util.desktop.ss.SSMensagem;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FrmContas extends Formulario {
	private static final Logger logger = LogManager.getLogger(FrmContas.class);
	@Autowired
	private CadastroService service;
	
	//JA PODERIA VIR DE FormularioConsulta
	private JPanel filtro = new JPanel();
	private JScrollPane scroll = new JScrollPane();
	private SSGrade tabela = new SSGrade();
	
	private SSCampoTexto txtFiltro = new SSCampoTexto();
	private SSBotao cmdBuscar = new SSBotao();
	
	private SSBotao cmdIncluir = new SSBotao();
	private SSBotao cmdAlterar = new SSBotao();
	private SSBotao cmdExtrato = new SSBotao();
	private SSBotao cmdFechar = new SSBotao();
	public FrmContas() {
		//JA PODERIA VIR DE FormularioConsulta

		setTitulo("Contas");
		setDescricao("Consulta das contas do sistema");

		setConteudoLayout(new BorderLayout());
		setAlinhamentoRodape(FlowLayout.LEFT);
		filtro.setLayout(new GridBagLayout());
		
		txtFiltro.setRotulo("Nome");
		txtFiltro.setColunas(30);
		cmdBuscar.setText("Buscar");
		
		cmdIncluir.setText("Incluir");
		cmdIncluir.setIcone("novo");
		cmdAlterar.setText("Alterar");
		cmdFechar.setText("Fechar");
		cmdExtrato.setText("Extrato");
		cmdExtrato.setIcone("pastabusca");
		tabela.getModeloTabela().addColumn("Sigla");
		tabela.getModeloTabela().addColumn("Nome");
		tabela.getModeloTabela().addColumn("Saldo Inicial");
		tabela.getModeloTabela().addColumn("Saldo Atual");
		tabela.getModeloTabela().addColumn("Aplicação");
		
		tabela.getModeloColuna().getColumn(0).setPreferredWidth(80);
		tabela.getModeloColuna().getColumn(1).setPreferredWidth(150);
		tabela.getModeloColuna().getColumn(2).setPreferredWidth(70);
		tabela.getModeloColuna().getColumn(3).setPreferredWidth(70);
		tabela.getModeloColuna().getColumn(4).setPreferredWidth(70);
		
		tabela.getModeloColuna().setCampo(0, "sigla");
		tabela.getModeloColuna().setCampo(1, "nome");
		tabela.getModeloColuna().setCampo(2, "saldoInicial");
		tabela.getModeloColuna().setCampo(3, "saldoAtual");
		tabela.getModeloColuna().setCampo(4, "aplicacao");
		
		
		tabela.getModeloColuna().setFormato(2, Formato.MOEDA);
		tabela.getModeloColuna().setFormato(3, Formato.MOEDA);
		
		//tabela.getModeloColuna().definirPositivoNegativo(2);
		
		//constraints - grid bag layout
		GridBagConstraints gbcTxtFiltro = new GridBagConstraints();
		gbcTxtFiltro.weightx = 1.0;
		gbcTxtFiltro.anchor = GridBagConstraints.NORTHWEST;
		gbcTxtFiltro.insets = new Insets(5, 5, 5, 5);
		gbcTxtFiltro.fill = GridBagConstraints.HORIZONTAL;
		gbcTxtFiltro.gridx = 0;
		gbcTxtFiltro.gridy = 0;
		
		GridBagConstraints gbcCmdBuscar = new GridBagConstraints();
		gbcCmdBuscar.anchor = GridBagConstraints.SOUTHWEST;
		gbcCmdBuscar.fill = GridBagConstraints.HORIZONTAL;
		gbcCmdBuscar.insets = new Insets(0, 0, 5, 5);
		gbcCmdBuscar.gridx = 1;
		gbcCmdBuscar.gridy = 0;
		
		
		//adicionando componentes aos seus containers
		filtro.add(txtFiltro, gbcTxtFiltro);
		filtro.add(cmdBuscar, gbcCmdBuscar);
		
		scroll.setViewportView(tabela);
		
		getConteudo().add(filtro,BorderLayout.NORTH);
		getConteudo().add(scroll,BorderLayout.CENTER);
		
		getRodape().add(cmdIncluir);
		getRodape().add(cmdAlterar);
		getRodape().add(cmdExtrato);
		getRodape().add(cmdFechar);
		
		//métodos
		cmdFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sair();
			}
		});
		cmdBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listar();
			}
		});
		cmdIncluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				incluir();
			}
		});
		cmdAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterar();
			}
		});
		cmdExtrato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				extrato();
			}
		});
	}
	public JPanel getFiltro() {
		return filtro;
	}
	private void sair() {
		super.fechar();
	}
	private void listar() {
		List<EntidadeConta> lista = new ArrayList<EntidadeConta>();
		try {
			String nome = txtFiltro.getText();
			lista = service.listarContas(sessao.getUsuario().getLogin(),nome);
			if(lista.size()==0)
				SSMensagem.avisa("Nenhum dado encontrado");
			
			tabela.setValue(lista);
		} catch (Exception e) {
			logger.error(e);
			SSMensagem.erro(e.getMessage());
		}
	}
	private void extrato() {
		EntidadeConta entidade= (EntidadeConta) tabela.getLinhaSelecionada();
		if(entidade==null) {
			SSMensagem.avisa("Selecione um item da lista");
			return;
		}
		  FrmExtrato frm = CfipDesktopApp.getBean(FrmExtrato.class);
		  frm.setConta(entidade); 
		  frm.carregar();
		  this.exibirDialogo(frm);
		 
	}
	private void incluir() {
		exibirCadastro(null);
	}
	private void alterar() {
		EntidadeConta entidade= (EntidadeConta) tabela.getLinhaSelecionada();
		if(entidade==null) {
			SSMensagem.avisa("Selecione um item da lista");
			return;
		}
		exibirCadastro(entidade);
	}
	private void exibirCadastro(EntidadeConta entidade) {
		//FrmConta frEntidadeContaew FrmConta();
		Formulario frm = CfipDesktopApp.getBean(FrmConta.class);
		frm.setEntidade(entidade);
		this.exibir(frm);
	}
	
}
