package open.digytal.desktop;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import open.digytal.model.Lancamento;
import open.digytal.model.entity.EntidadeConta;
import open.digytal.model.entity.EntidadeNatureza;
import open.digytal.model.enums.TipoMovimento;
import open.digytal.service.CadastroService;
import open.digytal.service.LancamentoService;
import open.digytal.util.Formato;
import open.digytal.util.desktop.DesktopApp;
import open.digytal.util.desktop.Formulario;
import open.digytal.util.desktop.ss.SSBotao;
import open.digytal.util.desktop.ss.SSCaixaCombinacao;
import open.digytal.util.desktop.ss.SSCampoDataHora;
import open.digytal.util.desktop.ss.SSCampoNumero;
import open.digytal.util.desktop.ss.SSCampoTexto;
import open.digytal.util.desktop.ss.SSMensagem;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FrmLancamentoCredito extends Formulario {
	private SSCampoDataHora txtData = new SSCampoDataHora();
	private SSCampoNumero txtValor = new SSCampoNumero();
	private SSCampoNumero txtTxCambio = new SSCampoNumero();
	private SSCampoTexto txtDescricao = new SSCampoTexto();

	private SSBotao cmdSalvar = new SSBotao();
	private SSBotao cmdSair = new SSBotao();
	private Lancamento entidade;
	@Autowired
	private LancamentoService service;
	@Autowired
	private CadastroService contaService;
	private SSCaixaCombinacao cboConta = new SSCaixaCombinacao();
	private SSCaixaCombinacao cboNatureza = new SSCaixaCombinacao();
	private JCheckBox chkNovo = new JCheckBox("Novo?");

	public FrmLancamentoCredito() {
		init();
	}

	private void init() {
		// HERANÇA
		super.setTitulo("Receitas");
		super.setDescricao("Lançamento de créditos e receitas");
		getRodape().add(chkNovo);
		getRodape().add(cmdSalvar);
		getRodape().add(cmdSair);

		// IMPORTANTE
		JPanel panelCampos = super.getConteudo();
		panelCampos.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GridBagLayout gbl_panelCampos = new GridBagLayout();
		panelCampos.setLayout(gbl_panelCampos);

		GridBagConstraints gbc_txtData = new GridBagConstraints();
		gbc_txtData.gridwidth = 2;
		gbc_txtData.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtData.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtData.insets = new Insets(3, 3, 3, 3);
		gbc_txtData.gridx = 0;
		gbc_txtData.gridy = 0;
		txtData.setColunas(15);
		panelCampos.add(txtData, gbc_txtData);

		GridBagConstraints gbc_cboConta = new GridBagConstraints();
		gbc_cboConta.anchor = GridBagConstraints.NORTHWEST;
		gbc_cboConta.gridwidth = 2;
		gbc_cboConta.insets = new Insets(3, 3, 3, 3);
		gbc_cboConta.fill = GridBagConstraints.HORIZONTAL;
		gbc_cboConta.gridx = 0;
		gbc_cboConta.gridy = 1;
		cboConta.setRotulo("Conta");
		panelCampos.add(cboConta, gbc_cboConta);

		GridBagConstraints gbc_cboNatureza = new GridBagConstraints();
		gbc_cboNatureza.anchor = GridBagConstraints.NORTHWEST;
		gbc_cboNatureza.gridwidth = 2;
		gbc_cboNatureza.insets = new Insets(3, 3, 3, 3);
		gbc_cboNatureza.fill = GridBagConstraints.HORIZONTAL;
		gbc_cboNatureza.gridx = 0;
		gbc_cboNatureza.gridy = 2;
		cboNatureza.setRotulo("Natureza");
		panelCampos.add(cboNatureza, gbc_cboNatureza);

		GridBagConstraints gbc_txtValor = new GridBagConstraints();
		gbc_txtValor.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtValor.weightx = 2.0;
		gbc_txtValor.insets = new Insets(3, 3, 3, 0);
		gbc_txtValor.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtValor.gridx = 0;
		gbc_txtValor.gridy = 3;
		txtValor.setComponenteNegrito(true);
		panelCampos.add(txtValor, gbc_txtValor);
		
		GridBagConstraints gbc_txtTx = new GridBagConstraints();
		gbc_txtTx.insets = new Insets(3, 3, 3, 3);
		gbc_txtTx.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtTx.gridx = 1;
		gbc_txtTx.gridy = 3;
		txtValor.setComponenteNegrito(true);
		txtTxCambio.setColunas(6);
		panelCampos.add(txtTxCambio, gbc_txtTx);

		txtData.setRotulo("Data Registro");
		txtValor.setColunas(15);
		txtValor.setRotulo("Valor");
		txtValor.setFormato(Formato.MOEDA);
		txtTxCambio.setRotulo("R$ Tx");
		txtTxCambio.setFormato(Formato.MOEDA);
		txtTxCambio.setComponenteNegrito(true);

		GridBagConstraints gbc_txtDescricao = new GridBagConstraints();
		gbc_txtDescricao.gridwidth = 2;
		gbc_txtDescricao.anchor = GridBagConstraints.NORTHWEST;
		gbc_txtDescricao.weighty = 1.0;
		gbc_txtDescricao.insets = new Insets(3, 3, 3, 3);
		gbc_txtDescricao.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtDescricao.gridx = 0;
		gbc_txtDescricao.gridy = 4;
		txtDescricao.setColunas(15);
		txtDescricao.setRotulo("Descrição");
		panelCampos.add(txtDescricao, gbc_txtDescricao);

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

		inicializa();

	}

	private void salvar() {
		try {
			entidade = new Lancamento();
			entidade.setValor(txtValor.getDouble());
			entidade.setDescricao(txtDescricao.getText());
			EntidadeConta conta = (EntidadeConta) cboConta.getValue();
			EntidadeNatureza natureza = (EntidadeNatureza) cboNatureza.getValue();
			entidade.setConta(conta.getId());

			entidade.setData(txtData.getDataHora());
			entidade.setNatureza(natureza.getId());
			
			if (entidade.getConta() == null || entidade.getNatureza() == null || entidade.getData() == null
					|| entidade.getValor() == null || entidade.getDescricao() == null
					|| entidade.getDescricao().isEmpty()) {
				SSMensagem.avisa("Dados incompletos");
				return;
			}
			service.incluir(entidade);
			SSMensagem.informa("Receita registrada com sucesso!!");
			novo();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void novo() {
		if (chkNovo.isSelected()) {
			inicializa();
		} else
			super.fechar();
	}

	private void inicializa() {
		entidade = new Lancamento();	
		txtData.requestFocus();
		txtData.setValue(new Date());
		txtValor.setValue(0.0d);
		txtTxCambio.setValue(0.0d);
		txtData.setDataHora(new Date());
		txtDescricao.setText("");
	}

	private void sair() {
		super.fechar();
	}

	public void carregar() {
		List<EntidadeConta> contas = contaService.listarCorrentesPoupanca(DesktopApp.getLogin());
		cboConta.setItens(contas, "nome");
		cboNatureza.setItens(contaService.listarNaturezas(DesktopApp.getLogin(),TipoMovimento.C), "nomeSigla");
	}
}
