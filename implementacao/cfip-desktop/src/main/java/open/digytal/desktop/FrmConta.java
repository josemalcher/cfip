package open.digytal.desktop;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import open.digytal.model.Conta;
import open.digytal.model.EntidadeConta;
import open.digytal.service.ContaService;
import open.digytal.util.Formato;
import open.digytal.util.desktop.DesktopApp;
import open.digytal.util.desktop.Formulario;
import open.digytal.util.desktop.ss.SSBotao;
import open.digytal.util.desktop.ss.SSCampoNumero;
import open.digytal.util.desktop.ss.SSCampoTexto;
import open.digytal.util.desktop.ss.SSMensagem;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)	
public class FrmConta extends Formulario {
	@Autowired
	private ContaService service;
	private SSCampoTexto txtNome = new SSCampoTexto();
	private SSCampoTexto txtSigla = new SSCampoTexto();
	private SSCampoNumero txtSaldoAtual = new SSCampoNumero();
	private SSCampoNumero txtSaldoInicial = new SSCampoNumero();
	
	private JPanel pnlCartaoCredito = new JPanel();
	private JCheckBox chkAplicacao = new JCheckBox("Aplicação ?");
	private JCheckBox chkCartaoCredito = new JCheckBox("Sim");
	// bototes
	private SSBotao cmdFechar = new SSBotao();
	private SSBotao cmdSalvar = new SSBotao();
	
	private Conta entidade;
	private final SSCampoNumero txtDiaPagamento = new SSCampoNumero();
	private final SSCampoNumero txtDiaFechamento = new SSCampoNumero();
	
	public FrmConta() {
		init();
	}
	
	private void init() {
		// CABECALHO
		setTitulo("Conta");
		setDescricao("Cadastro das contas do sistema");

		txtNome.setRotulo("Nome");
		txtSigla.setRotulo("Sigla");
		cmdSalvar.setText("Salvar");
		cmdFechar.setText("Fechar");
		txtSaldoInicial.setFormato(Formato.MOEDA);

		//
		GridBagConstraints gbc_txtNome = new GridBagConstraints();
		gbc_txtNome.weightx = 1.0;
		gbc_txtNome.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtNome.gridwidth = 2;
		gbc_txtNome.insets = new Insets(3, 3, 0, 3);
		gbc_txtNome.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNome.gridx = 0;
		gbc_txtNome.gridy = 0;
		getConteudo().add(txtNome, gbc_txtNome);

		//
		GridBagConstraints gbc_txtSigla = new GridBagConstraints();
		gbc_txtSigla.weightx = 1.0;
		gbc_txtSigla.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtSigla.insets = new Insets(3, 3, 0, 0);
		gbc_txtSigla.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSigla.gridx = 0;
		gbc_txtSigla.gridy = 1;
		getConteudo().add(txtSigla, gbc_txtSigla);
		
		GridBagConstraints gbc_chkAplicacao = new GridBagConstraints();
		gbc_chkAplicacao.weightx = 1.0;
		gbc_chkAplicacao.insets = new Insets(0, 3, 0, 3);
		gbc_chkAplicacao.weighty = 1.0;
		gbc_chkAplicacao.anchor = GridBagConstraints.SOUTHWEST;
		gbc_chkAplicacao.fill = GridBagConstraints.HORIZONTAL;
		gbc_chkAplicacao.gridx = 1;
		gbc_chkAplicacao.gridy = 1;
		getConteudo().add(chkAplicacao, gbc_chkAplicacao);

		GridBagConstraints gbc_txtSaldoInicial = new GridBagConstraints();
		gbc_txtSaldoInicial.insets = new Insets(3, 3, 0, 0);
		gbc_txtSaldoInicial.weighty = 1.0;
		gbc_txtSaldoInicial.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtSaldoInicial.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSaldoInicial.gridx = 0;
		gbc_txtSaldoInicial.gridy = 2;
		txtSaldoInicial.setRotulo("Saldo Inicial");
		getConteudo().add(txtSaldoInicial, gbc_txtSaldoInicial);
		txtSaldoAtual.setRotulo("Saldo Atual");
		txtSaldoAtual.setEnabled(false);
		txtSaldoAtual.setFormato(Formato.MOEDA);
		
		//
		GridBagConstraints gbc_txtPrevisto = new GridBagConstraints();
		gbc_txtPrevisto.weighty = 1.0;
		gbc_txtPrevisto.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtPrevisto.insets = new Insets(3, 3, 0, 3);
		gbc_txtPrevisto.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPrevisto.gridx = 1;
		gbc_txtPrevisto.gridy = 2;
		getConteudo().add(txtSaldoAtual, gbc_txtPrevisto);
	
		
		GridBagConstraints gbc_chkCartaoCredito = new GridBagConstraints();
		gbc_chkCartaoCredito.weightx = 1.0;
		gbc_chkCartaoCredito.gridwidth = 2;
		gbc_chkCartaoCredito.insets = new Insets(3, 3, 3, 3);
		gbc_chkCartaoCredito.weighty = 1.0;
		gbc_chkCartaoCredito.anchor = GridBagConstraints.SOUTHWEST;
		gbc_chkCartaoCredito.fill = GridBagConstraints.HORIZONTAL;
		gbc_chkCartaoCredito.gridx = 0;
		gbc_chkCartaoCredito.gridy = 3;
		FlowLayout flowLayout = (FlowLayout) pnlCartaoCredito.getLayout();
		flowLayout.setVgap(3);
		flowLayout.setHgap(3);
		flowLayout.setAlignment(FlowLayout.LEFT);
		pnlCartaoCredito.setBorder(new TitledBorder(null, "Cart\u00E3o de Cr\u00E9dito?", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getConteudo().add(pnlCartaoCredito, gbc_chkCartaoCredito);
		
		pnlCartaoCredito.add(chkCartaoCredito);
		txtDiaFechamento.setRotulo("Dia Fechamento");
		pnlCartaoCredito.add(txtDiaFechamento);
		txtDiaPagamento.setRotulo("Dia Pagamento");
		pnlCartaoCredito.add(txtDiaPagamento);
		// rodape
		getRodape().add(cmdSalvar);
		getRodape().add(cmdFechar);
		// métodos
		cmdFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sair();
			}
		});
		cmdSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salvar();
			}
		});
	}
	@Override
	public void setEntidade(Object conta) {
		this.entidade=(EntidadeConta) conta;
		if(entidade==null) 
			novo();
		else
			atribuir();
	}
	private void atribuir() {
		try {
			txtNome.requestFocus();
			txtNome.setValue(entidade.getNome());
			txtSigla.setText(entidade.getSigla());
			txtSaldoAtual.setValue(entidade.getSaldoAtual());
			txtSaldoInicial.setValue(entidade.getSaldoInicial());
			chkAplicacao.setSelected(entidade.isAplicacao());
			chkCartaoCredito.setSelected(entidade.isCartaoCredito());
			txtDiaPagamento.setNumero(entidade.getDiaPagamento());
			txtDiaFechamento.setNumero(entidade.getDiaFechamento());
			txtSaldoInicial.setEnabled(entidade.getId()==null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void novo() {
		entidade = new Conta();
		atribuir();
	}
	private void sair() {
		super.cancelar();
	}
	private void salvar() {
		try {
			if (entidade == null) {
				entidade = new Conta();
			}
			entidade.setNome(txtNome.getText());
			entidade.setSigla(txtSigla.getText());
			entidade.setAplicacao(chkAplicacao.isSelected());
			entidade.setCartaoCredito(chkCartaoCredito.isSelected());
			entidade.setSaldoInicial(txtSaldoInicial.getDouble());
			entidade.setDiaPagamento(txtDiaPagamento.getInteger());
			entidade.setDiaFechamento(txtDiaFechamento.getInteger());
			entidade.setLogin(DesktopApp.getLogin());
			if (entidade.getNome() == null || entidade.getNome().isEmpty() || entidade.getSigla() == null
					|| entidade.getSigla().isEmpty()) {
				SSMensagem.avisa("Dados incompletos");
				return;
			}
			if(entidade.getId()==null) {
				entidade.setSaldoAtual(entidade.getSaldoInicial());
			}
			if(entidade.getId()==null)
				service.incluir(entidade);
			else
				service.alterar(entidade);
			SSMensagem.informa("Conta registrada com sucesso!!");
			novo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
