package open.digytal.desktop;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import open.digytal.model.Conta;
import open.digytal.repository.ContaRepository;
import open.digytal.util.Formato;
import open.digytal.util.desktop.Formulario;
import open.digytal.util.desktop.ss.SSBotao;
import open.digytal.util.desktop.ss.SSCampoNumero;
import open.digytal.util.desktop.ss.SSCampoTexto;
import open.digytal.util.desktop.ss.SSMensagem;

@Component
public class FrmConta extends Formulario {
	@Autowired
	private ContaRepository service;
	private SSCampoTexto txtNome = new SSCampoTexto();
	private SSCampoTexto txtSigla = new SSCampoTexto();
	private SSCampoNumero txtSaldoAtual = new SSCampoNumero();
	private SSCampoNumero txtSaldoInicial = new SSCampoNumero();
	
	private JCheckBox chkAplicacao = new JCheckBox("Aplicação ?");
	private JCheckBox chkPropria = new JCheckBox("Propria ?");
	// bototes
	private SSBotao cmdFechar = new SSBotao();
	private SSBotao cmdSalvar = new SSBotao();
	
	private Conta entidade;
	
	public FrmConta() {
		init();
	}
	
	private void init() {
		// CABECALHO
		setTitulo("Conta");
		setDescricao("Cadastro das contas do sistema");

		txtNome.setRotulo("Nome");
		txtSigla.setRotulo("Sigla");
		txtSaldoAtual.setRotulo("Saldo Atual");
		txtSaldoAtual.setEnabled(false);
		cmdSalvar.setText("Salvar");
		cmdFechar.setText("Fechar");
		txtSaldoAtual.setFormato(Formato.MOEDA);
		txtSaldoInicial.setFormato(Formato.MOEDA);

		//
		GridBagConstraints gbc_txtNome = new GridBagConstraints();
		gbc_txtNome.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtNome.gridwidth = 2;
		gbc_txtNome.insets = new Insets(3, 3, 0, 3);
		gbc_txtNome.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtNome.gridx = 0;
		gbc_txtNome.gridy = 0;
		getConteudo().add(txtNome, gbc_txtNome);

		//
		GridBagConstraints gbc_txtSigla = new GridBagConstraints();
		gbc_txtSigla.gridwidth = 2;
		gbc_txtSigla.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtSigla.insets = new Insets(3, 3, 0, 3);
		gbc_txtSigla.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSigla.gridx = 0;
		gbc_txtSigla.gridy = 1;
		getConteudo().add(txtSigla, gbc_txtSigla);

		//
		GridBagConstraints gbc_txtPrevisto = new GridBagConstraints();
		gbc_txtPrevisto.weighty = 1.0;
		gbc_txtPrevisto.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtPrevisto.weightx = 1.0;
		gbc_txtPrevisto.insets = new Insets(3, 3, 3, 0);
		gbc_txtPrevisto.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPrevisto.gridx = 0;
		gbc_txtPrevisto.gridy = 3;
		getConteudo().add(txtSaldoAtual, gbc_txtPrevisto);
		
		GridBagConstraints gbc_chkAplicacao = new GridBagConstraints();
		gbc_chkAplicacao.weighty = 1.0;
		gbc_chkAplicacao.anchor = GridBagConstraints.SOUTHWEST;
		gbc_chkAplicacao.fill = GridBagConstraints.HORIZONTAL;
		gbc_chkAplicacao.gridx = 1;
		gbc_chkAplicacao.gridy = 3;
		getConteudo().add(chkAplicacao, gbc_chkAplicacao);
		
		GridBagConstraints gbc_chkPropria = new GridBagConstraints();
		gbc_chkPropria.weighty = 1.0;
		gbc_chkPropria.anchor = GridBagConstraints.SOUTHWEST;
		gbc_chkPropria.fill = GridBagConstraints.HORIZONTAL;
		gbc_chkPropria.gridx = 1;
		gbc_chkPropria.gridy = 2;
		getConteudo().add(chkPropria, gbc_chkPropria);

		GridBagConstraints gbc_txtSaldoInicial = new GridBagConstraints();
		gbc_txtSaldoInicial.insets = new Insets(3, 3, 0, 0);
		gbc_txtSaldoInicial.weighty = 1.0;
		gbc_txtSaldoInicial.anchor = GridBagConstraints.SOUTHWEST;
		gbc_txtSaldoInicial.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSaldoInicial.gridx = 0;
		gbc_txtSaldoInicial.gridy = 2;
		txtSaldoInicial.setRotulo("Saldo Inicial");
		getConteudo().add(txtSaldoInicial, gbc_txtSaldoInicial);
		
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
		this.entidade=(Conta) conta;
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
			txtSaldoAtual.setValue(entidade.getSaldoInicial());
			chkAplicacao.setSelected(entidade.isAplicacao());
			chkPropria.setSelected(entidade.isPropria());
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
			entidade.setPropria(chkPropria.isSelected());
			entidade.setSaldoInicial(txtSaldoInicial.getDouble());
			if (entidade.getNome() == null || entidade.getNome().isEmpty() || entidade.getSigla() == null
					|| entidade.getSigla().isEmpty()) {
				SSMensagem.avisa("Dados incompletos");
				return;
			}
			if(entidade.getId()==null) {
				entidade.setSaldoAtual(entidade.getSaldoInicial());
			}
			service.save(entidade);
			
			SSMensagem.informa("Conta registrada com sucesso!!");
			novo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
