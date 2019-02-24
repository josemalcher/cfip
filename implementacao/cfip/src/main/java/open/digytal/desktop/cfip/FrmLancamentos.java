package open.digytal.desktop.cfip;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import open.digytal.SpringBootApp;
import open.digytal.controller.LancamentoController;
import open.digytal.model.Conta;
import open.digytal.model.Lancamento;
import open.digytal.model.Natureza;
import open.digytal.model.Total;
import open.digytal.repository.ContaRepository;
import open.digytal.repository.NaturezaRepository;
import open.digytal.util.Formato;
import open.digytal.util.cfip.CfipUtil;
import open.digytal.util.desktop.Formulario;
import open.digytal.util.desktop.ss.SSBotao;
import open.digytal.util.desktop.ss.SSCaixaCombinacao;
import open.digytal.util.desktop.ss.SSCampoDataHora;
import open.digytal.util.desktop.ss.SSCampoNumero;
import open.digytal.util.desktop.ss.SSGrade;
import open.digytal.util.desktop.ss.SSMensagem;
import open.digytal.util.desktop.ss.SSPosicaoRotulo;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FrmLancamentos extends Formulario {
	// rodape
	private SSBotao cmdIncluir = new SSBotao();
	private SSBotao cmdFechar = new SSBotao();
	private SSBotao cmdBuscar = new SSBotao();
	private SSGrade grid = new SSGrade();
	private JScrollPane scroll = new JScrollPane();
	@Autowired
	private LancamentoController service;
	@Autowired
	private ContaRepository contaService;
	@Autowired
	private NaturezaRepository naturezaService;

	private SSCampoDataHora txtDataDe = new SSCampoDataHora();
	private SSCampoDataHora txtDataAte = new SSCampoDataHora();
	private SSCaixaCombinacao cboConta = new SSCaixaCombinacao();
	private SSCaixaCombinacao cboNatureza = new SSCaixaCombinacao();
	private SSCaixaCombinacao cboSaldo = new SSCaixaCombinacao();
	private JLabel lblDesc = new JLabel();
	
	//
	private Total total=new Total();
	private SSCampoNumero txtDespesas = new SSCampoNumero();
	private SSCampoNumero txtReceitas = new SSCampoNumero();
	private SSCampoNumero txtSaldoAtual = new SSCampoNumero();
	//
	public FrmLancamentos() {
		init();
	}

	private void init() {
		cboConta.setPreferredWidth(180);
		cboNatureza.setPreferredWidth(150);
		super.setTitulo("Consulta de Lançamentos");
		super.setDescricao("Descrição das entradas e saidas no sistema");
		getRodape().add(cmdIncluir);
		getRodape().add(cmdFechar);
		// implementando o conteudo do formulario
		JPanel conteudo = super.getConteudo();
		conteudo.setLayout(new BorderLayout());
		// usando o painel de conteudo
		JPanel painelFiltro = new JPanel();
		conteudo.add(painelFiltro, BorderLayout.NORTH);
		JPanel pnlDesc = new JPanel(new BorderLayout());
		lblDesc.setHorizontalAlignment(SwingConstants.CENTER);
		lblDesc.setFont(new Font("Tahoma", Font.BOLD, 9));
		lblDesc.setForeground(Color.BLUE);
		lblDesc.setText("SELECIONE UMA LINHA PARA MAIORES INFORMAÇÕES");
		pnlDesc.add(lblDesc, BorderLayout.NORTH);
		scroll.setViewportView(grid);
		pnlDesc.add(scroll, BorderLayout.CENTER);

		conteudo.add(pnlDesc, BorderLayout.CENTER);
		grid.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				exibirDescricao();
			}
		});

		GridBagLayout gbl_painelFiltro = new GridBagLayout();
		painelFiltro.setLayout(gbl_painelFiltro);
		painelFiltro.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		GridBagConstraints gbcBuscar = new GridBagConstraints();
		gbcBuscar.anchor = GridBagConstraints.NORTHWEST;
		gbcBuscar.fill = GridBagConstraints.HORIZONTAL;
		gbcBuscar.insets = new Insets(15, 5, 5, 5);
		gbcBuscar.gridx = 5;
		gbcBuscar.gridy = 0;
		painelFiltro.add(cmdBuscar, gbcBuscar);

		// campos da tabela
		grid.getModeloTabela().addColumn("Data");
		grid.getModeloTabela().addColumn("Conta");
		grid.getModeloTabela().addColumn("Natureza");
		grid.getModeloTabela().addColumn("Valor");
		
		grid.getModeloColuna().getColumn(0).setPreferredWidth(50);
		grid.getModeloColuna().getColumn(1).setPreferredWidth(220);
		grid.getModeloColuna().getColumn(2).setPreferredWidth(220);
		grid.getModeloColuna().getColumn(3).setPreferredWidth(80);
		
		grid.getModeloColuna().setCampo(0, "data");
		grid.getModeloColuna().setFormato(0, "dd/MM/yy");
		grid.getModeloColuna().setCampo(1, "conta.nome");
		grid.getModeloColuna().setCampo(2, "natureza.nome");
		grid.getModeloColuna().setCampo(3, "valor");
		grid.getModeloColuna().setFormato(3, Formato.MOEDA);
		cmdIncluir.setText("Novo");
		cmdFechar.setText("Fechar");
		cmdBuscar.setText("Buscar");

		GridBagConstraints gbc_txtDataDe = new GridBagConstraints();
		gbc_txtDataDe.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtDataDe.insets = new Insets(5, 5, 5, 0);
		gbc_txtDataDe.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDataDe.gridx = 1;
		gbc_txtDataDe.gridy = 0;
		txtDataDe.setColunas(8);
		txtDataDe.setRotulo("De");
		painelFiltro.add(txtDataDe, gbc_txtDataDe);

		GridBagConstraints gbc_txtDataAte = new GridBagConstraints();
		gbc_txtDataAte.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtDataAte.insets = new Insets(5, 5, 5, 0);
		gbc_txtDataAte.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDataAte.gridx = 2;
		gbc_txtDataAte.gridy = 0;
		txtDataAte.setColunas(8);
		txtDataAte.setRotulo("Até");
		painelFiltro.add(txtDataAte, gbc_txtDataAte);

		GridBagConstraints gbc_cboConta = new GridBagConstraints();
		gbc_cboConta.weightx = 1.0;
		gbc_cboConta.anchor = GridBagConstraints.NORTHWEST;
		gbc_cboConta.insets = new Insets(5, 5, 5, 0);
		gbc_cboConta.fill = GridBagConstraints.HORIZONTAL;
		gbc_cboConta.gridx = 3;
		gbc_cboConta.gridy = 0;
		cboConta.setRotulo("Conta");
		painelFiltro.add(cboConta, gbc_cboConta);

		GridBagConstraints gbc_cboNatureza = new GridBagConstraints();
		gbc_cboNatureza.anchor = GridBagConstraints.NORTHWEST;
		gbc_cboNatureza.weightx = 1.0;
		gbc_cboNatureza.insets = new Insets(5, 5, 5, 0);
		gbc_cboNatureza.fill = GridBagConstraints.HORIZONTAL;
		gbc_cboNatureza.gridx = 4;
		gbc_cboNatureza.gridy = 0;
		cboNatureza.setRotulo("NaturezaService");
		painelFiltro.add(cboNatureza, gbc_cboNatureza);

		GridBagConstraints gbc_cboSaldo = new GridBagConstraints();
		gbc_cboSaldo.anchor = GridBagConstraints.NORTHWEST;
		gbc_cboSaldo.insets = new Insets(5, 5, 5, 0);
		gbc_cboSaldo.fill = GridBagConstraints.HORIZONTAL;
		gbc_cboSaldo.gridx = 0;
		gbc_cboSaldo.gridy = 0;
		cboSaldo.setRotulo("Saldo");
		painelFiltro.add(cboSaldo, gbc_cboSaldo);
		cmdBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listar();
			}
		});

		cmdFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sair();
			}
		});
		cmdIncluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				incluir();
			}
		});
		cboSaldo.setVisible(false);
		cmdIncluir.setVisible(false);
		
		//
		FlowLayout pnlSaldoLayout = new FlowLayout();
		pnlSaldoLayout.setAlignment(FlowLayout.RIGHT);
		JPanel pnlSaldo= new JPanel(pnlSaldoLayout);
		pnlSaldo.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		pnlDesc.add(pnlSaldo,BorderLayout.SOUTH);
		txtDespesas.setComponenteCorFonte(Color.RED);
		txtDespesas.setComponenteNegrito(true);
		txtDespesas.setEditavel(false);
		txtDespesas.setColunas(6);
		txtDespesas.setRotuloPosicao(SSPosicaoRotulo.ESQUERDA);
		txtDespesas.setRotulo("Despesa");
		txtReceitas.setComponenteNegrito(true);
		txtReceitas.setComponenteCorFonte(Color.BLUE);
		
		txtReceitas.setEditavel(false);
		txtReceitas.setColunas(6);
		txtReceitas.setRotuloPosicao(SSPosicaoRotulo.ESQUERDA);
		txtReceitas.setRotulo("Receita");
		txtSaldoAtual.setComponenteNegrito(true);
		txtSaldoAtual.setComponenteCorFonte(Color.BLUE);
		
		txtSaldoAtual.setEditavel(false);
		txtSaldoAtual.setColunas(6);
		txtSaldoAtual.setRotuloPosicao(SSPosicaoRotulo.ESQUERDA);
		txtSaldoAtual.setRotulo("Saldo");
		
		
		txtDespesas.setFormato(Formato.MOEDA);
		txtSaldoAtual.setFormato(Formato.MOEDA);
		txtReceitas.setFormato(Formato.MOEDA);
			
		pnlSaldo.add(txtReceitas);
		pnlSaldo.add(txtDespesas);
		pnlSaldo.add(txtSaldoAtual);
		txtReceitas.setComponenteCorFonte(Color.BLUE);
		txtDespesas.setComponenteCorFonte(Color.RED);
		//
	}

	private void exibirDescricao() {
		try {
			Lancamento l = (Lancamento) grid.getLinhaSelecionada();
			if (l != null) {
				lblDesc.setText(l.getDescricao());
			}
		} catch (java.lang.IndexOutOfBoundsException e) {
			// TODO: handle exception
		}
	}

	@Override
	public void carregar() {
		cboConta.setPrimeiroElementoVazio(true);
		cboNatureza.setPrimeiroElementoVazio(true); 
		cboConta.setItens(contaService.listar(), "nome");
		cboNatureza.setItens(naturezaService.listar(), "nome");

		txtDataDe.setDataHora(new Date());
		txtDataAte.setDataHora(new Date());

	}

	private void sair() {
		super.fechar();
	}

	private void incluir() {
		abrirCadastro(null);
	}

	private void abrirCadastro(Lancamento entidade) {
		FrmLancamentoPrevisao frm = SpringBootApp.getBean(FrmLancamentoPrevisao.class);
		frm.setEntidade(entidade);
		this.exibir(frm);
	}

	private void listar() {
		List<Lancamento> lista = new ArrayList<Lancamento>();
		try {
			Conta conta = (Conta) cboConta.getValue();
			Natureza nat = (Natureza) cboNatureza.getValue();
			Integer cId=conta==null?null:conta.getId();
			Integer nId=nat==null?null:nat.getId();
			lista = service.listarLancamentos(txtDataDe.getDataHora(),txtDataAte.getDataHora(),cId,nId );
			if(lista.size()==0)
				SSMensagem.avisa("Nenhum dado encontrado");
			
			grid.setValue(lista);
			total = CfipUtil.totais(lista,conta!=null);
			txtSaldoAtual.setValue(total.getSaldo());
			txtSaldoAtual.setComponenteCorFonte(total.getSaldo() < 0.0d ? Color.RED: Color.BLUE);
			txtDespesas.setValue(total.getDebito());
			txtReceitas.setValue(total.getCredito());
		} catch (Exception e) {
			e.printStackTrace();
			SSMensagem.erro(e.getMessage());
		}

	}

}
