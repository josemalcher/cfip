package open.digytal.util.desktop;

import open.digytal.util.Campos;
import open.digytal.util.TipoOperacao;
import open.digytal.util.desktop.ss.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FormularioConsulta extends Formulario {
	private SSCaixaCombinacao cboCampos = new SSCaixaCombinacao();
	private SSCampoTexto txtFiltro = new SSCampoTexto();
	private SSBotao cmdBuscar = new SSBotao();
	
	private JPanel filtros = new JPanel();
	private SSGrade tabela = new SSGrade();
	private JScrollPane scroll;
	public FormularioConsulta() {
		super();
		init();
	}
	private void init(){
		cboCampos.setVisible(false);
		txtFiltro.setColunas(27);
		GridBagLayout gbl_filtros = new GridBagLayout();
		this.filtros.setLayout(gbl_filtros);
		this.filtros.setBorder(BorderFactory.createEtchedBorder());
		scroll = new JScrollPane();
		scroll.setViewportView(tabela);
		getConteudo().setLayout(new BorderLayout());
		getConteudo().add(filtros,BorderLayout.NORTH);
		
		GridBagConstraints gbc_caixaCombinacao = new GridBagConstraints();
		gbc_caixaCombinacao.insets = new Insets(3, 3, 3, 0);
		gbc_caixaCombinacao.anchor = GridBagConstraints.NORTHWEST;
		gbc_caixaCombinacao.fill = GridBagConstraints.HORIZONTAL;
		gbc_caixaCombinacao.gridx = 0;
		gbc_caixaCombinacao.gridy = 0;
		cboCampos.setRotulo("Campo");
		filtros.add(cboCampos, gbc_caixaCombinacao);
		
		GridBagConstraints gbc_txt = new GridBagConstraints();
		gbc_txt.weightx = 1.0;
		gbc_txt.insets = new Insets(3, 3, 3, 0);
		gbc_txt.anchor = GridBagConstraints.NORTHWEST;
		gbc_txt.fill = GridBagConstraints.HORIZONTAL;
		gbc_txt.gridx = 1;
		gbc_txt.gridy = 0;
		txtFiltro.setRotulo("Filtro");
		filtros.add(txtFiltro, gbc_txt);
		
		GridBagConstraints gbc_cmd = new GridBagConstraints();
		gbc_cmd.insets = new Insets(0, 3, 3, 3);
		gbc_cmd.anchor = GridBagConstraints.SOUTHWEST;
		gbc_cmd.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmd.gridx = 2;
		gbc_cmd.gridy = 0;
		cmdBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				filtrar();
			}
		});
		cmdBuscar.setText("Buscar");
		filtros.add(cmdBuscar, gbc_cmd);
		
		getConteudo().add(scroll,BorderLayout.CENTER);
		this.tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		super.botoesEsquerda();
	}
	
	public SSGrade getTabela() {
		return tabela;
	}
	public JPanel getFiltros() {
		return filtros;
	}
	protected void ocultarFiltros() {
		this.filtros.setVisible(false);
	}
	protected JScrollPane getScroll() {
		return scroll;
	}
	protected void alterar(Formulario formulario){
		//alterar(formulario,false);
		Object entidade = getTabela().getLinhaSelecionada();
		if(entidade==null){
			SSMensagem.avisa("Selecione um registro");
			return;
		}
		exibir(formulario, TipoOperacao.ALTERACAO, entidade);
	}
	protected void incluir(Formulario formulario){
		exibir(formulario, TipoOperacao.INCLUSAO, null);
	}
	protected void incluir(Formulario formulario, Object entidade){
		exibir(formulario, TipoOperacao.INCLUSAO, entidade);
	}
	protected void exibir(Formulario formulario, TipoOperacao operacao, Object entidade){
		formulario.setOperacao(operacao);
		this.exibir(formulario);
		formulario.setEntidade(entidade);
	}
	protected void setFiltroCampos(Campos ... campos) {
		filtros.setVisible(true);
		cboCampos.setVisible(true);
		txtFiltro.setColunas(46);
		cboCampos.setItens(campos,"label");
	}
	protected String getFiltro() {
		return txtFiltro.getText();
	}
	protected String getAtributo() {
		Campos campo = (Campos) cboCampos.getValue();
		return campo.getAtributo();
	}
	protected void filtrar() {
		
	}
	protected void exibirResultado(List lista) {
		if(lista==null || (lista!=null && lista.isEmpty()))
			SSMensagem.avisa("Nenhum registro encontrado");
		getTabela().setValue(lista);
	}

}
