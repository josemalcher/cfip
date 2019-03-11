package open.digytal.desktop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import open.digytal.controller.LancamentoController;
import open.digytal.model.entity.EntidadeConta;
import open.digytal.model.entity.EntidadeLancamento;
import open.digytal.model.entity.EntidadeNatureza;
import open.digytal.model.entity.Total;
import open.digytal.repository.ContaRepository;
import open.digytal.repository.NaturezaRepository;
import open.digytal.util.Calendario;
import open.digytal.util.Formato;
import open.digytal.util.cfip.CfipUtil;
import open.digytal.util.desktop.DesktopApp;
import open.digytal.util.desktop.Formulario;
import open.digytal.util.desktop.ss.SSBotao;
import open.digytal.util.desktop.ss.SSCaixaCombinacao;
import open.digytal.util.desktop.ss.SSCampoDataHora;
import open.digytal.util.desktop.ss.SSCampoNumero;
import open.digytal.util.desktop.ss.SSGrade;
import open.digytal.util.desktop.ss.SSMensagem;
import open.digytal.util.desktop.ss.SSPosicaoRotulo;
import open.digytal.util.desktop.ss.tabela.SSTipoSelecao;
import open.digytal.util.desktop.ss.util.SSDataHora;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FrmProjecoes extends Formulario {
	private SSBotao cmdFechar = new SSBotao();
	private SSBotao cmdBuscar = new SSBotao();
	private SSGrade gridContas = new SSGrade();
	private SSGrade gridLancamentos = new SSGrade();
	
	@Autowired
	private ContaRepository contaService;
	@Autowired
	private NaturezaRepository naturezaService;

	@Autowired
	private LancamentoController service;

	private SSCampoDataHora txtDataDe = new SSCampoDataHora();
	private SSCampoDataHora txtDataAte = new SSCampoDataHora();
	private SSCaixaCombinacao cboConta = new SSCaixaCombinacao();
	private SSCaixaCombinacao cboNatureza = new SSCaixaCombinacao();
	private JLabel lblDesc = new JLabel();
	
	private SSCampoNumero txtSaldoContas = new SSCampoNumero();
	//
	private Total totalLancamentos=new Total();
	private SSCampoNumero txtDespesas = new SSCampoNumero();
	private SSCampoNumero txtReceitas = new SSCampoNumero();
	private SSCampoNumero txtSaldoAtual = new SSCampoNumero();
	//

	public FrmProjecoes() {
		init();
	}

	private void init() {
		cboConta.setPreferredWidth(180);
		cboNatureza.setPreferredWidth(150);
		super.setTitulo("Resumo \\ Projeções");
		super.setDescricao("Análise financeira futura");
		getRodape().add(cmdFechar);
		// implementando o conteudo do formulario
		JPanel conteudo = super.getConteudo();
		conteudo.setLayout(new BorderLayout());

		// usando o painel de conteudo
		JPanel painelFiltro = new JPanel();
		conteudo.add(painelFiltro, BorderLayout.NORTH);
		gridLancamentos.setTipoSelecao(SSTipoSelecao.SELECAO_MULTIPLA);
		JScrollPane scrollLancamentos = new JScrollPane();
		scrollLancamentos.setViewportView(gridLancamentos);
		JScrollPane scrollSaldos = new JScrollPane();
		scrollSaldos.setPreferredSize(new Dimension(0, 100));
		scrollLancamentos.setPreferredSize(new Dimension(0, 170));
		scrollSaldos.setViewportView(gridContas);
		JPanel pnlConteudo= new JPanel(new BorderLayout());
		
		pnlConteudo.add(scrollSaldos,BorderLayout.NORTH);
		pnlConteudo.add(scrollLancamentos,BorderLayout.CENTER);
		conteudo.add(pnlConteudo, BorderLayout.CENTER);
		gridLancamentos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
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
		gbcBuscar.gridx = 4;
		gbcBuscar.gridy = 0;
		painelFiltro.add(cmdBuscar, gbcBuscar);
		
		gridContas.getModeloTabela().addColumn("Sigla");
		gridContas.getModeloTabela().addColumn("Nome");
		gridContas.getModeloTabela().addColumn("Saldo Inicial");
		gridContas.getModeloTabela().addColumn("Saldo Atual");
		gridContas.getModeloTabela().addColumn("Aplicação");
		gridContas.getModeloColuna().getColumn(0).setPreferredWidth(80);
		gridContas.getModeloColuna().getColumn(1).setPreferredWidth(180);
		gridContas.getModeloColuna().getColumn(2).setPreferredWidth(70);
		gridContas.getModeloColuna().getColumn(3).setPreferredWidth(70);
		gridContas.getModeloColuna().getColumn(4).setPreferredWidth(70);
		gridContas.getModeloColuna().setCampo(0, "sigla");
		gridContas.getModeloColuna().setCampo(1, "nome");
		gridContas.getModeloColuna().setCampo(2, "saldoInicial");
		gridContas.getModeloColuna().setCampo(3, "saldoAtual");
		gridContas.getModeloColuna().setCampo(4, "aplicacao");
		gridContas.getModeloColuna().setFormato(2, Formato.MOEDA);
		gridContas.getModeloColuna().setFormato(3, Formato.MOEDA);
		
		// campos da tabela
		gridLancamentos.getModeloTabela().addColumn("Data");
		gridLancamentos.getModeloTabela().addColumn("Conta");
		gridLancamentos.getModeloTabela().addColumn("Natureza");
		gridLancamentos.getModeloTabela().addColumn("Valor");
		gridLancamentos.getModeloTabela().addColumn("Restante");

		gridLancamentos.getModeloColuna().getColumn(0).setPreferredWidth(50);
		gridLancamentos.getModeloColuna().getColumn(1).setPreferredWidth(180);
		gridLancamentos.getModeloColuna().getColumn(2).setPreferredWidth(180);
		gridLancamentos.getModeloColuna().getColumn(3).setPreferredWidth(80);
		gridLancamentos.getModeloColuna().getColumn(4).setPreferredWidth(80);

		gridLancamentos.getModeloColuna().setCampo(0, "data");
		gridLancamentos.getModeloColuna().setFormato(0, "dd/MM/yy");
		gridLancamentos.getModeloColuna().setCampo(1, "conta.nome");
		gridLancamentos.getModeloColuna().setCampo(2, "natureza.nome");
		gridLancamentos.getModeloColuna().setCampo(3, "valor");
		gridLancamentos.getModeloColuna().setFormato(3, Formato.MOEDA);
		gridLancamentos.getModeloColuna().definirPositivoNegativo(3);
		gridLancamentos.getModeloColuna().setCampo(4, "parcelamento.restante");
		gridLancamentos.getModeloColuna().setFormato(4, Formato.MOEDA);
		gridLancamentos.getModeloColuna().definirPositivoNegativo(4);
		
		cmdFechar.setText("Fechar");
		cmdBuscar.setText("Buscar");
		
		GridBagConstraints gbc_txtDataDe = new GridBagConstraints();
		gbc_txtDataDe.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtDataDe.insets = new Insets(5, 5, 5, 0);
		gbc_txtDataDe.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDataDe.gridx = 0;
		gbc_txtDataDe.gridy = 0;
		txtDataDe.setColunas(8);
		txtDataDe.setRotulo("De");
		painelFiltro.add(txtDataDe, gbc_txtDataDe);

		GridBagConstraints gbc_txtDataAte = new GridBagConstraints();
		gbc_txtDataAte.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtDataAte.insets = new Insets(5, 5, 5, 0);
		gbc_txtDataAte.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDataAte.gridx = 1;
		gbc_txtDataAte.gridy = 0;
		txtDataAte.setColunas(8);
		txtDataAte.setRotulo("Até");
		painelFiltro.add(txtDataAte, gbc_txtDataAte);

		GridBagConstraints gbc_cboConta = new GridBagConstraints();
		gbc_cboConta.weightx = 1.0;
		gbc_cboConta.anchor = GridBagConstraints.NORTHWEST;
		gbc_cboConta.insets = new Insets(5, 5, 5, 0);
		gbc_cboConta.fill = GridBagConstraints.HORIZONTAL;
		gbc_cboConta.gridx = 2;
		gbc_cboConta.gridy = 0;
		cboConta.setRotulo("Conta");
		painelFiltro.add(cboConta, gbc_cboConta);

		GridBagConstraints gbc_cboNatureza = new GridBagConstraints();
		gbc_cboNatureza.anchor = GridBagConstraints.NORTHWEST;
		gbc_cboNatureza.weightx = 1.0;
		gbc_cboNatureza.insets = new Insets(5, 5, 5, 0);
		gbc_cboNatureza.fill = GridBagConstraints.HORIZONTAL;
		gbc_cboNatureza.gridx = 3;
		gbc_cboNatureza.gridy = 0;
		cboNatureza.setRotulo("Natureza");
		painelFiltro.add(cboNatureza, gbc_cboNatureza);
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
		
		FlowLayout pnlSaldoLayout = new FlowLayout();
		pnlSaldoLayout.setAlignment(FlowLayout.RIGHT);
		JPanel pnlSaldo= new JPanel(pnlSaldoLayout);
		pnlSaldo.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		pnlConteudo.add(pnlSaldo,BorderLayout.SOUTH);
		
		txtDespesas.setComponenteCorFonte(Color.RED);
		txtDespesas.setComponenteNegrito(true);
		txtDespesas.setEditavel(false);
		txtDespesas.setColunas(6);
		txtDespesas.setRotuloPosicao(SSPosicaoRotulo.ESQUERDA);
		txtDespesas.setRotulo("Despesa:");
		
		txtReceitas.setComponenteNegrito(true);
		txtReceitas.setComponenteCorFonte(Color.BLUE);
		txtReceitas.setEditavel(false);
		txtReceitas.setColunas(6);
		txtReceitas.setRotuloPosicao(SSPosicaoRotulo.ESQUERDA);
		txtReceitas.setRotulo("Receita:");
		
		txtSaldoAtual.setComponenteNegrito(true);
		txtSaldoAtual.setComponenteCorFonte(Color.BLUE);
		txtSaldoAtual.setEditavel(false);
		txtSaldoAtual.setColunas(6);
		txtSaldoAtual.setRotuloPosicao(SSPosicaoRotulo.ESQUERDA);
		txtSaldoAtual.setRotulo("Saldo:");
		
		txtSaldoContas.setEditavel(false);
		txtSaldoContas.setColunas(6);
		txtSaldoContas.setRotuloPosicao(SSPosicaoRotulo.ESQUERDA);
		txtSaldoContas.setRotulo("Contas:");
		txtSaldoContas.setComponenteNegrito(true);
		
		
		txtDespesas.setFormato(Formato.MOEDA);
		txtSaldoAtual.setFormato(Formato.MOEDA);
		txtReceitas.setFormato(Formato.MOEDA);
		txtSaldoContas.setFormato(Formato.MOEDA);
			
		pnlSaldo.add(txtSaldoContas);
		pnlSaldo.add(txtReceitas);
		pnlSaldo.add(txtDespesas);
		pnlSaldo.add(txtSaldoAtual);
		txtReceitas.setComponenteCorFonte(Color.BLUE);
		txtDespesas.setComponenteCorFonte(Color.RED);
		
		//
		
	}

	@Override
	public void carregar() {
		cboConta.setPrimeiroElementoVazio(true);
		cboNatureza.setPrimeiroElementoVazio(true);
		cboConta.setItens(contaService.listarContas(DesktopApp.getLogin()), "nome");
		cboNatureza.setItens(naturezaService.listarTodas(DesktopApp.getLogin()), "nome");
		int ano = SSDataHora.pegaAno(new Date());
		txtDataDe.setDataHora(Calendario.data(1, 1, ano));
		txtDataAte.setDataHora(Calendario.data(31, 12, ano));

	}
	private void exibirDescricao() {
		try {
			EntidadeLancamento l = (EntidadeLancamento) gridLancamentos.getLinhaSelecionada();
			if (l != null) {
				lblDesc.setText(l.getDescricao());
			}
		} catch (java.lang.IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}
	private void sair() {
		super.fechar();
	}
	

	private void listar() {
		List<EntidadeLancamento> lista = new ArrayList<EntidadeLancamento>();
		List<EntidadeConta> contas = new ArrayList<EntidadeConta>();
		try {
			EntidadeConta conta = (EntidadeConta) cboConta.getValue();
			EntidadeNatureza nat = (EntidadeNatureza) cboNatureza.getValue();
			Integer cId=conta==null?null:conta.getId();
			Integer nId=nat==null?null:nat.getId();
			
			lista = service.listarPrevisoes(DesktopApp.getLogin(), txtDataDe.getDataHora(),txtDataAte.getDataHora(),cId,nId);
			if(cId==null)
				contas = contaService.listarContas(DesktopApp.getLogin());
			else
				contas = contaService.listar(cId);
			gridContas.setValue(contas);
			gridLancamentos.setValue(lista);
			totalLancamentos = CfipUtil.previsoes(lista);
			if(contas.size()==0 &&  lista.size()==0)
				SSMensagem.avisa("Nenhum dado encontrado");

			Double saldo = CfipUtil.totalContas(contas);
			txtSaldoContas.setValue(saldo);
			saldo=saldo + totalLancamentos.getSaldo();
			txtSaldoAtual.setValue(saldo);
			txtSaldoAtual.setComponenteCorFonte(saldo < 0.0d ? Color.RED: Color.BLUE);
			txtDespesas.setValue(totalLancamentos.getDebito());
			txtReceitas.setValue(totalLancamentos.getCredito());
		} catch (Exception e) {
			e.printStackTrace();
			//Mensagem.erro(e.getMessage());
		}

	}
}
