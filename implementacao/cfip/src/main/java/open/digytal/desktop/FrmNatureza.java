package open.digytal.desktop;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import open.digytal.SpringBootApp;
import open.digytal.model.Categoria;
import open.digytal.model.Natureza;
import open.digytal.model.TipoMovimento;
import open.digytal.repository.NaturezaRepository;
import open.digytal.util.desktop.Formulario;
import open.digytal.util.desktop.ss.SSBotao;
import open.digytal.util.desktop.ss.SSCaixaCombinacao;
import open.digytal.util.desktop.ss.SSCampoTexto;
import open.digytal.util.desktop.ss.SSMensagem;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FrmNatureza extends Formulario {
	@Autowired
	private NaturezaRepository service;
	private Natureza entidade;
	
	private SSCampoTexto txtNome = new SSCampoTexto();
	private SSBotao cmdSalvar = new SSBotao();
	private SSBotao cmdSair = new SSBotao();
	private SSCampoTexto txtDescricao = new SSCampoTexto();
	private SSCaixaCombinacao cboTipoMovto = new SSCaixaCombinacao();
	private SSCaixaCombinacao cboCategoria = new SSCaixaCombinacao();

	private JCheckBox chkNovo = new JCheckBox("Novo?");
	public FrmNatureza() {
		init();
	}
	private void init() {
		// HERANÇA
		super.setTitulo("Cadastro de Naturezas");
		super.setDescricao("Descrição das Entradas e Saidas");
		super.getRodape().add(chkNovo);
		super.getRodape().add(cmdSalvar);
		super.getRodape().add(cmdSair);
		cboTipoMovto.setItens(TipoMovimento.values(),"nome");
		cboCategoria.setItens(Categoria.values(),"nome");
		// IMPORTANTE
		JPanel panelCampos = super.getConteudo();
		panelCampos.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GridBagLayout gbl_panelCampos = new GridBagLayout();
		panelCampos.setLayout(gbl_panelCampos);


		GridBagConstraints gbcTxtNome = new GridBagConstraints();
		gbcTxtNome.weightx = 2.0;
		gbcTxtNome.insets = new Insets(5, 5, 5, 5);
		gbcTxtNome.fill = GridBagConstraints.HORIZONTAL;
		gbcTxtNome.gridx = 0;
		gbcTxtNome.gridy = 0;
		panelCampos.add(txtNome, gbcTxtNome);

		txtNome.setColunas(30);
		txtNome.setRotulo("Nome");

		GridBagConstraints gbc_txtDescricao = new GridBagConstraints();
		gbc_txtDescricao.insets = new Insets(5, 5, 0, 5);
		gbc_txtDescricao.fill = GridBagConstraints.BOTH;
		gbc_txtDescricao.gridx = 0;
		gbc_txtDescricao.gridy = 1;
		txtDescricao.setRotulo("Descrição");
		panelCampos.add(txtDescricao, gbc_txtDescricao);

		GridBagConstraints gbc_cboTipoMovto = new GridBagConstraints();
		gbc_cboTipoMovto.weighty = 1.0;
		gbc_cboTipoMovto.insets = new Insets(5, 5, 0, 5);
		gbc_cboTipoMovto.fill = GridBagConstraints.BOTH;
		gbc_cboTipoMovto.gridx = 0;
		gbc_cboTipoMovto.gridy = 2;
		cboTipoMovto.setRotulo("Tipo Movimento");
		panelCampos.add(cboTipoMovto, gbc_cboTipoMovto);
		
		GridBagConstraints gbc_cboCategoria = new GridBagConstraints();
		gbc_cboCategoria.weighty = 1.0;
		gbc_cboCategoria.insets = new Insets(5, 5, 5, 5);
		gbc_cboCategoria.fill = GridBagConstraints.BOTH;
		gbc_cboCategoria.gridx = 0;
		gbc_cboCategoria.gridy = 3;
		cboCategoria.setRotulo("Categoria");
		panelCampos.add(cboCategoria, gbc_cboCategoria);
		
		cmdSair.setText("Fechar");
		cmdSalvar.setText("Salvar");

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
	}
	//public void setEntidade(NaturezaService entidade) {
	public void setEntidade(Object entidade) {
		this.entidade = (Natureza) entidade;
		if (entidade != null)
			atribuir();
		else
			criar();
	}
	private void atribuir() {
		try {
			txtNome.setValue(entidade.getNome());
			txtDescricao.setText(entidade.getNome());
			cboTipoMovto.setValue(entidade.getTipoMovimento());
			cboCategoria.setValue(entidade.getCategoria());
			txtNome.requestFocus();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	private void criar() {
		entidade = new Natureza();
		atribuir();
	}
	private void salvar() {
		try {
			entidade.setNome(txtNome.getText());
			entidade.setNome(txtDescricao.getText());
			entidade.setTipoMovimento((TipoMovimento) cboTipoMovto.getValue());
			entidade.setCategoria((Categoria) cboCategoria.getValue());

			if (entidade.getNome() == null || entidade.getNome().isEmpty() || entidade.getNome() == null
					|| entidade.getNome().isEmpty() || entidade.getTipoMovimento() == null || entidade.getCategoria() ==null) {
				SSMensagem.avisa("Dados incompletos");
				return;
			}
			if(entidade.getTipoMovimento().isTranferencia()) {
				SSMensagem.avisa("Não é possível alterar a natureza TRANSFERENCIA");
				return;
			}
			
			//dao.gravar(operacao, entidade);
			service.save(entidade);
			
			SSMensagem.informa("Natureza registrada com sucesso!!");
			novo();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	private void novo() {
		if(chkNovo.isSelected()) {
			criar();
		}else
			super.fechar();
	}
	private void sair() {
		super.fechar();
	}

}
