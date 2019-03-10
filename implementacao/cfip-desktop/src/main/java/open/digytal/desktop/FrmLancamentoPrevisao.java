package open.digytal.desktop;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.border.EtchedBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import open.digytal.controller.LancamentoController;
import open.digytal.model.EntidadeConta;
import open.digytal.model.EntidadeLancamento;
import open.digytal.model.Natureza;
import open.digytal.model.TipoMovimento;
import open.digytal.repository.ContaRepository;
import open.digytal.repository.NaturezaRepository;
import open.digytal.util.Formato;
import open.digytal.util.desktop.DesktopApp;
import open.digytal.util.desktop.Formulario;
import open.digytal.util.desktop.ss.SSBotao;
import open.digytal.util.desktop.ss.SSCaixaCombinacao;
import open.digytal.util.desktop.ss.SSCampoDataHora;
import open.digytal.util.desktop.ss.SSCampoMascara;
import open.digytal.util.desktop.ss.SSCampoNumero;
import open.digytal.util.desktop.ss.SSCampoTexto;
import open.digytal.util.desktop.ss.SSMensagem;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)	
public class FrmLancamentoPrevisao extends Formulario {
	private SSCampoDataHora txtData = new SSCampoDataHora();
	private SSCampoNumero txtValor = new SSCampoNumero();
	private SSCampoTexto txtDescricao = new SSCampoTexto();
	private SSCampoDataHora txtDataPrevisao = new SSCampoDataHora();
	private SSCampoMascara txtParcelas = new SSCampoMascara();
	
	private SSBotao cmdSalvar = new SSBotao();
	private SSBotao cmdSair = new SSBotao();
	private EntidadeLancamento entidade;
	
	@Autowired
	private LancamentoController service;
	@Autowired
	private ContaRepository contaService;
	@Autowired
	private NaturezaRepository naturezaService;
	
	
	private SSCaixaCombinacao cboConta = new SSCaixaCombinacao();
	private SSCaixaCombinacao cboNatureza = new SSCaixaCombinacao();
	private SSCaixaCombinacao cboDestino = new SSCaixaCombinacao();
	private SSCaixaCombinacao cboContato = new SSCaixaCombinacao();
	private JCheckBox chkNovo = new JCheckBox("Novo?");
	private JCheckBox chkRateio = new JCheckBox("Rateio Parcela?");
	public FrmLancamentoPrevisao() {
		init();
	}
	private void init() {
		// HERANÇA
		super.setTitulo("Previsões");
		super.setDescricao("Registra os lançamentos futuros");
		chkRateio.setSelected(true);
		getRodape().add(chkNovo);
		getRodape().add(cmdSalvar);
		getRodape().add(cmdSair);
		// IMPORTANTE
		getConteudo().setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GridBagLayout gbl_panelCampos = new GridBagLayout();
		getConteudo().setLayout(gbl_panelCampos);

		GridBagConstraints gbc_txtData = new GridBagConstraints();
		gbc_txtData.gridwidth = 2;
		gbc_txtData.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtData.anchor = GridBagConstraints.WEST;
		gbc_txtData.insets = new Insets(3, 3, 0, 0);
		gbc_txtData.gridx = 0;
		gbc_txtData.gridy = 0;
		txtData.setColunas(8);
		getConteudo().add(txtData, gbc_txtData);
		txtData.setColunas(8);
		
		GridBagConstraints gbc_cboConta = new GridBagConstraints();
		gbc_cboConta.gridwidth = 2;
		gbc_cboConta.insets = new Insets(3, 3, 0, 3);
		gbc_cboConta.fill = GridBagConstraints.BOTH;
		gbc_cboConta.gridx = 0;
		gbc_cboConta.gridy = 1;
		cboConta.setRotulo("Conta");
		getConteudo().add(cboConta, gbc_cboConta);
		
		GridBagConstraints gbc_cboNatureza = new GridBagConstraints();
		gbc_cboNatureza.gridwidth = 2;
		gbc_cboNatureza.insets = new Insets(3, 3, 0, 3);
		gbc_cboNatureza.fill = GridBagConstraints.BOTH;
		gbc_cboNatureza.gridx = 0;
		gbc_cboNatureza.gridy = 2;
		cboNatureza.setRotulo("Natureza");
		getConteudo().add(cboNatureza, gbc_cboNatureza);
		
		GridBagConstraints gbc_cboDestino = new GridBagConstraints();
		gbc_cboDestino.gridwidth = 2;
		gbc_cboDestino.insets = new Insets(3, 3, 0, 3);
		gbc_cboDestino.fill = GridBagConstraints.BOTH;
		gbc_cboDestino.gridx = 0;
		gbc_cboDestino.gridy = 3;
		cboDestino.setRotulo("Destino");
		getConteudo().add(cboDestino, gbc_cboDestino);
		
		GridBagConstraints gbc_chkRateio = new GridBagConstraints();
		gbc_chkRateio.anchor = GridBagConstraints.SOUTHWEST;
		gbc_chkRateio.weightx = 2.0;
		gbc_chkRateio.insets = new Insets(0, 3, 0, 3);
		gbc_chkRateio.fill = GridBagConstraints.HORIZONTAL;
		gbc_chkRateio.gridx = 1;
		gbc_chkRateio.gridy = 4;
		getConteudo().add(chkRateio, gbc_chkRateio);

		GridBagConstraints gbc_txtValor = new GridBagConstraints();
		gbc_txtValor.weightx = 2.0;
		gbc_txtValor.insets = new Insets(3, 3, 0, 0);
		gbc_txtValor.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtValor.gridx = 0;
		gbc_txtValor.gridy = 4;
		txtValor.setComponenteNegrito(true);
		getConteudo().add(txtValor, gbc_txtValor);

		txtData.setRotulo("Data Registro");
		txtValor.setColunas(10);
		txtValor.setRotulo("Valor");
		txtValor.setFormato(Formato.MOEDA);
		
		GridBagConstraints gbc_txtDescricao = new GridBagConstraints();
		gbc_txtDescricao.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtDescricao.gridwidth = 2;
		gbc_txtDescricao.insets = new Insets(3, 3, 0, 3);
		gbc_txtDescricao.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDescricao.gridx = 0;
		gbc_txtDescricao.gridy = 5;
		txtDescricao.setColunas(20);
		txtDescricao.setRotulo("Descrição");
		getConteudo().add(txtDescricao, gbc_txtDescricao);
		
		GridBagConstraints gbc_cboContato = new GridBagConstraints();
		gbc_cboContato.weightx = 1.0;
		gbc_cboContato.gridwidth = 2;
		gbc_cboContato.anchor = GridBagConstraints.NORTHWEST;
		gbc_cboContato.insets = new Insets(3, 3, 0, 3);
		gbc_cboContato.fill = GridBagConstraints.HORIZONTAL;
		gbc_cboContato.gridx = 0;
		gbc_cboContato.gridy = 6;
		cboContato.setRotulo("Contato");
		getConteudo().add(cboContato, gbc_cboContato);
		
		
		GridBagConstraints gbc_txtDataPrevisao = new GridBagConstraints();
		gbc_txtDataPrevisao.weightx = 1.0;
		gbc_txtDataPrevisao.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtDataPrevisao.insets = new Insets(3, 3, 3, 0);
		gbc_txtDataPrevisao.fill = GridBagConstraints.BOTH;
		gbc_txtDataPrevisao.gridx = 0;
		gbc_txtDataPrevisao.gridy = 7;
		txtDataPrevisao.setColunas(8);
		txtDataPrevisao.setForeground(Color.BLUE);
		txtDataPrevisao.setRotulo("1° Vencimento");
		getConteudo().add(txtDataPrevisao, gbc_txtDataPrevisao);
		
		GridBagConstraints gbc_txtParcelas = new GridBagConstraints();
		gbc_txtParcelas.weightx = 1.0;
		gbc_txtParcelas.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtParcelas.insets = new Insets(3, 3, 3, 3);
		gbc_txtParcelas.fill = GridBagConstraints.BOTH;
		gbc_txtParcelas.gridx = 1;
		gbc_txtParcelas.gridy = 7;
		try {
			txtParcelas.setSelecionarAoEntrar(true);
			txtParcelas.setMascara("##-###");
			txtParcelas.setText("0101");
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		txtParcelas.setColunas(4);
		getConteudo().add(txtParcelas, gbc_txtParcelas);
		
		
		cmdSair.setText("Cancelar");
		
		cmdSalvar.setText("Confirmar");
		// Listners = Comandos = Eventos
		cmdSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				salvar();
			}
		});
		cmdSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sair();
			}
		});
		cboDestino.setEnabled(false);
		cboNatureza.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        habilitarDestino();
		    }
		});
		cboConta.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		       dataVencimento();
		    }
		});
		txtParcelas.setRotulo("Parcelas");
		inicializa();
		
	}
	private void dataVencimento() {
		EntidadeConta conta = (EntidadeConta) cboConta.getValue();
		if(conta!=null) {
			txtDataPrevisao.setDataHora(conta.getDataPagamento());
		}
	}
	private void habilitarDestino() {
		Natureza natureza = (Natureza) cboNatureza.getValue();
		if(natureza!=null) {
			cboDestino.setValue(null);
			cboDestino.setEnabled(natureza!=null && natureza.getTipoMovimento().isTranferencia());
			if(natureza.getTipoMovimento()== TipoMovimento.C)
				txtValor.setComponenteCorFonte(Color.BLACK);
			else
				txtValor.setComponenteCorFonte(Color.RED);
		}
	}
	private void salvar() {
		try {
			entidade = new EntidadeLancamento();
			entidade.setValor(txtValor.getDouble());
			EntidadeConta conta = (EntidadeConta) cboConta.getValue();
			EntidadeConta destino = (EntidadeConta) cboDestino.getValue();
			Natureza natureza = (Natureza) cboNatureza.getValue();
			entidade.setConta(conta);
			if(destino!=null)
				entidade.setDestino(destino);
			
			
			if(conta.isCartaoCredito() && (natureza.getTipoMovimento()!=TipoMovimento.D)) {
				SSMensagem.avisa("Cartão de Crédito só permite débito");
				return;
			}
			/*
			 * Contato contato = (Contato) cboContato.getValue(); if(contato!=null)
			 * entidade.setContato(contato);
			 */
			
			/*Fatura fat = (Fatura) cboFatura.getValue();
			if(fat!=null)
				entidade.setFatura(fat.getId());
			*/
			entidade.setDescricao(txtDescricao.getText() + " PARC.: " + txtParcelas.getText());
			entidade.setData(txtData.getDataHora());
			entidade.setPrevisao(true);
			entidade.getParcelamento().setPrimeiroVencimento(txtDataPrevisao.getDataHora());
			entidade.setNatureza(natureza);
			entidade.setTipoMovimento(natureza.getTipoMovimento());
			entidade.getParcelamento().setRateio(chkRateio.isSelected());
			if(entidade.getConta()==null || entidade.getNatureza() == null 
			|| entidade.getData() == null || entidade.getValor() == null || 
			entidade.getDescricao()==null || entidade.getParcelamento().getPrimeiroVencimento() == null ||  entidade.getDescricao().isEmpty()) {
				SSMensagem.avisa("Dados incompletos");
				return;
			}
			if(entidade.getTipoMovimento()==TipoMovimento.T && destino==null) {
				SSMensagem.avisa("Dados incompletos");
				return;
			}
			if(txtParcelas.getText()== null) {
				SSMensagem.avisa("Dados incompletos");
				return;
			}
			if(txtParcelas.getText()!= null) {
				Integer primeira = Integer.valueOf(txtParcelas.getText().split("-")[0].trim());
				Integer ultima = Integer.valueOf(txtParcelas.getText().split("-")[1].trim());
				entidade.getParcelamento().setPrimeiraParcela(primeira);
				entidade.getParcelamento().setUltimaParcela(ultima);
			}else {
				SSMensagem.avisa("Dados incompletos");
				return;
			}
			if(entidade.getConta() == entidade.getDestino()) {
				SSMensagem.avisa("Origem e Destino são iguais");
				return;
			}
			if(chkRateio.isSelected()) {
				if(!SSMensagem.confirma("Ao marcar a opção Rateio Parcela o valor total será dividido pelo número de parcelas.\nConfirma esta operação?")) {
					return;
				}
			}else {
				if(!SSMensagem.confirma("Ao desmarcar a opção Rateio Parcela o valor total será multiplicado pelo número de parcelas.\nConfirma esta operação?")) {
					return;
				}
			}
			service.incluir(entidade);
			SSMensagem.informa("Lançamento registrado com sucesso!!");
			novo();
		} catch (Exception e) {
			SSMensagem.erro("Dados incompletos");
		}
	}

	private void novo() {
		if(chkNovo.isSelected()) {
			inicializa();
		}else
			super.fechar();
	}
	
	private void inicializa() {
		entidade = new EntidadeLancamento();
		txtData.requestFocus();
		txtData.setValue(new Date());
		txtValor.setValue(0.0d);
		txtData.setDataHora(new Date());
		txtDescricao.setText("");
	}
	private void sair() {
		super.fechar();
	}
	public void carregar() {
		/*
		 * List<EntidadeConta> contas = contaService.listarTodas(DesktopApp.getLogin());
		 * cboConta.setItens( contas,"nome"); cboDestino.setItens( contas,"nome");
		 */
		cboNatureza.setItens( naturezaService.listarTodas(DesktopApp.getLogin()),"nomeSigla");
	}
	public static void main(String[] args) {
		int m = (int) (100.0%3);
		System.out.println(m);
	}
}
