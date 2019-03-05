package open.digytal.desktop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import open.digytal.model.Conta;
import open.digytal.model.Lancamento;
import open.digytal.model.Total;
import open.digytal.repository.ContaRepository;
import open.digytal.util.Formato;
import open.digytal.util.cfip.CfipUtil;
import open.digytal.util.desktop.Formulario;
import open.digytal.util.desktop.ss.SSBotao;
import open.digytal.util.desktop.ss.SSCampoDataHora;
import open.digytal.util.desktop.ss.SSCampoNumero;
import open.digytal.util.desktop.ss.SSCampoTexto;
import open.digytal.util.desktop.ss.SSGrade;
import open.digytal.util.desktop.ss.SSMensagem;
import open.digytal.util.desktop.ss.SSPosicaoRotulo;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FrmExtrato extends Formulario {
	// rodape
	private SSBotao cmdAtualizar = new SSBotao();

	private SSBotao cmdFechar = new SSBotao();
	private SSGrade grid = new SSGrade();
	private JScrollPane scroll = new JScrollPane();
	// DAOs - NAO OFICIAL
	@Autowired
	private ContaRepository service;
	
	private Conta conta;
	private SSCampoTexto txtConta = new SSCampoTexto();
	private SSCampoNumero txtSaldoInicial = new SSCampoNumero();
	private JLabel lblDesc = new JLabel();
	//
	private Total total = new Total();
	private SSCampoNumero txtDespesas = new SSCampoNumero();
	private SSCampoNumero txtReceitas = new SSCampoNumero();
	private SSCampoNumero txtSaldoAtual = new SSCampoNumero();
	private SSCampoDataHora txtDataInicial = new SSCampoDataHora();
	//
	public FrmExtrato() {
		init();
	}

	private void init() {
		super.setTitulo("Extrato");
		super.setDescricao("Movimentações da conta");
		setAlinhamentoRodape(FlowLayout.LEFT);
		getRodape().add(cmdAtualizar);
		getRodape().add(cmdFechar);
		// implementando o conteudo do formulario
		getConteudo().setLayout(new BorderLayout());

		// usando o painel de conteudo
		JPanel painelFiltro = new JPanel();
		getConteudo().add(painelFiltro, BorderLayout.NORTH);
		scroll.setViewportView(grid);
		JPanel pnlDesc = new JPanel(new BorderLayout());
		lblDesc.setHorizontalAlignment(SwingConstants.CENTER);
		lblDesc.setFont(new Font("Tahoma", Font.BOLD, 9));
		lblDesc.setForeground(Color.BLUE);
		lblDesc.setText("SELECIONE UMA LINHA PARA MAIORES INFORMAÇÕES");
		pnlDesc.add(lblDesc, BorderLayout.NORTH);
		pnlDesc.add(scroll, BorderLayout.CENTER);
		//
		getConteudo().add(pnlDesc, BorderLayout.CENTER);
		grid.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				exibirDescricao();
			}
		});
		GridBagLayout gbl_painelFiltro = new GridBagLayout();
		painelFiltro.setLayout(gbl_painelFiltro);
		painelFiltro.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		// campos da tabela
		grid.getModeloTabela().addColumn("Data");
		grid.getModeloTabela().addColumn("Natureza");
		grid.getModeloTabela().addColumn("Valor");
		
		grid.getModeloColuna().getColumn(0).setPreferredWidth(50);
		grid.getModeloColuna().getColumn(1).setPreferredWidth(170);
		grid.getModeloColuna().getColumn(2).setPreferredWidth(90);
		
		grid.getModeloColuna().setCampo(0, "data");
		grid.getModeloColuna().setFormato(0, "dd/MM/yy");
		grid.getModeloColuna().setCampo(1, "natureza.nome");
		grid.getModeloColuna().setCampo(2, "valor");
		grid.getModeloColuna().setFormato(2, Formato.MOEDA);
		grid.getModeloColuna().definirPositivoNegativo(2);
		cmdAtualizar.setText("Atualizar");
		cmdFechar.setText("Fechar");
		GridBagConstraints gbc_txtSaldoInicial = new GridBagConstraints();
		gbc_txtSaldoInicial.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtSaldoInicial.insets = new Insets(3, 3, 3, 0);
		gbc_txtSaldoInicial.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSaldoInicial.gridx = 1;
		gbc_txtSaldoInicial.gridy = 0;

		
		GridBagConstraints gbc_txtConta = new GridBagConstraints();
		gbc_txtConta.weightx = 1.0;
		gbc_txtConta.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtConta.insets = new Insets(3, 3, 3, 0);
		gbc_txtConta.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtConta.gridx = 0;
		gbc_txtConta.gridy = 0;
		txtConta.setComponenteNegrito(true);

		txtConta.setEditavel(false);
		txtConta.setColunas(8);
		txtConta.setRotulo("Conta");
		painelFiltro.add(txtConta, gbc_txtConta);

		GridBagConstraints gbc_txtSaldoInicial1 = new GridBagConstraints();
		gbc_txtSaldoInicial1.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtSaldoInicial1.insets = new Insets(3, 3, 3, 0);
		gbc_txtSaldoInicial1.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSaldoInicial1.gridx = 1;
		gbc_txtSaldoInicial1.gridy = 0;
		
		txtSaldoInicial.setComponenteNegrito(true);
		txtSaldoInicial.setEditavel(false);
		txtSaldoInicial.setComponenteCorFonte(Color.BLUE);
		txtSaldoInicial.setColunas(6);
		txtSaldoInicial.setRotulo("Saldo Inicial");
		txtSaldoInicial.setFormato(Formato.MOEDA);

		painelFiltro.add(txtSaldoInicial, gbc_txtSaldoInicial1);

		GridBagConstraints gbc_txtDataInicial = new GridBagConstraints();
		gbc_txtDataInicial.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtDataInicial.insets = new Insets(3, 3, 3, 3);
		gbc_txtDataInicial.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDataInicial.gridx = 2;
		gbc_txtDataInicial.gridy = 0;
		txtDataInicial.setComponenteNegrito(true);
		txtDataInicial.setEditavel(false);
		txtDataInicial.setComponenteCorFonte(Color.BLUE);
		txtDataInicial.setColunas(6);
		txtDataInicial.setRotulo("Data Inicial");
		
		painelFiltro.add(txtDataInicial, gbc_txtDataInicial);
		
		cmdFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sair();
			}
		});
		cmdAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listar();
			}
		});

		txtConta.setComponenteCorFonte(Color.BLUE);
		
		FlowLayout pnlSaldoLayout = new FlowLayout();
		pnlSaldoLayout.setAlignment(FlowLayout.RIGHT);
		JPanel pnlSaldo = new JPanel(pnlSaldoLayout);
		pnlSaldo.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		pnlDesc.add(pnlSaldo, BorderLayout.SOUTH);
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
		txtSaldoAtual.setRotulo("Saldo Atual");
				
		txtDespesas.setFormato(Formato.MOEDA);
		txtSaldoAtual.setFormato(Formato.MOEDA);
		txtReceitas.setFormato(Formato.MOEDA);
		
		pnlSaldo.add(txtReceitas);
		pnlSaldo.add(txtDespesas);
		pnlSaldo.add(txtSaldoAtual);
		txtReceitas.setComponenteCorFonte(Color.BLUE);
		txtDespesas.setComponenteCorFonte(Color.RED);

	}

	
	public void setConta(Conta conta) {
		this.conta = conta;
		listar();
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

	private void sair() {
		super.fechar();
	}
	private void listar() {
		if (conta != null) {
			txtDataInicial.setDataHora(conta.getDataInicial());
			
			txtConta.setText(conta.getNome());
			txtSaldoInicial.setValue(conta.getSaldoInicial());
			txtSaldoInicial.setComponenteCorFonte(conta.getSaldoInicial() < 0.0d ? Color.RED : Color.BLUE);
			txtSaldoAtual.setValue(conta.getSaldoAtual());
			txtSaldoAtual.setComponenteCorFonte(conta.getSaldoAtual() < 0.0d ? Color.RED : Color.BLUE);
			
			List<Lancamento> lista = null;
			try {
				
				lista = service.extrato(conta.getId(), conta.getDataInicial());
				if(lista==null || lista.size()==0)
					SSMensagem.avisa("Nenhum dado encontrado");

				total = CfipUtil.extrato(lista);
				txtDespesas.setValue(total.getDebito());
				txtReceitas.setValue(total.getCredito());
				grid.setValue(lista);
			} catch (Exception e) {
				e.printStackTrace();
				SSMensagem.erro(e.getMessage());
			}
			//conta = service.buscar(Conta.class, conta.getId());
		}
	}

}
