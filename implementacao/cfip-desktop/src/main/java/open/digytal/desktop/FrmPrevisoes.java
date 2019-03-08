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

import open.digytal.controller.LancamentoController;
import open.digytal.model.EntidadeConta;
import open.digytal.model.EntidadeLancamento;
import open.digytal.model.EntidadeNatureza;
import open.digytal.model.Total;
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
public class FrmPrevisoes extends Formulario {
	// rodape
	private SSBotao cmdFechar = new SSBotao();
	private SSBotao cmdBuscar = new SSBotao();
	private SSGrade grid = new SSGrade();
	private JScrollPane scroll = new JScrollPane();
	// DAOs - NAO OFICIAL
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
	//
	private Total total = new Total();
	private SSCampoNumero txtDespesas = new SSCampoNumero();
	private SSCampoNumero txtReceitas = new SSCampoNumero();
	private SSCampoNumero txtSaldoAtual = new SSCampoNumero();
	//

	public FrmPrevisoes() {
		init();
	}

	private void init() {
		cboConta.setPreferredWidth(180);
		cboNatureza.setPreferredWidth(150);
		super.setTitulo("Consulta de Previsões");
		super.setDescricao("Registro dos valores previstos para compensação");
		setAlinhamentoRodape(FlowLayout.LEFT);
		getRodape().add(cmdFechar);
		// implementando o conteudo do formulario
		JPanel conteudo = super.getConteudo();
		conteudo.setLayout(new BorderLayout());

		// usando o painel de conteudo
		JPanel painelFiltro = new JPanel();
		conteudo.add(painelFiltro, BorderLayout.NORTH);
		grid.setTipoSelecao(SSTipoSelecao.SELECAO_MULTIPLA);
		scroll.setViewportView(grid);
		JPanel pnlDesc = new JPanel(new BorderLayout());
		lblDesc.setHorizontalAlignment(SwingConstants.CENTER);
		lblDesc.setFont(new Font("Tahoma", Font.BOLD, 9));
		lblDesc.setForeground(Color.BLUE);
		lblDesc.setText("SEGURE A TECLA Ctrl PARA SELECIONAR MAIS DE UMA LINHA");
		pnlDesc.add(lblDesc, BorderLayout.NORTH);
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
		gbcBuscar.gridx = 4;
		gbcBuscar.gridy = 0;
		painelFiltro.add(cmdBuscar, gbcBuscar);

		// campos da tabela
		grid.getModeloTabela().addColumn("Data");
		grid.getModeloTabela().addColumn("P. Inicial");
		grid.getModeloTabela().addColumn("P. Final");
		grid.getModeloTabela().addColumn("Conta");
		grid.getModeloTabela().addColumn("Natureza");
		grid.getModeloTabela().addColumn("Valor");
		grid.getModeloTabela().addColumn("Restante");

		grid.getModeloColuna().getColumn(0).setPreferredWidth(50);
		grid.getModeloColuna().getColumn(1).setPreferredWidth(50);
		grid.getModeloColuna().getColumn(2).setPreferredWidth(50);
		grid.getModeloColuna().getColumn(3).setPreferredWidth(160);
		grid.getModeloColuna().getColumn(4).setPreferredWidth(115);
		grid.getModeloColuna().getColumn(5).setPreferredWidth(70);
		grid.getModeloColuna().getColumn(6).setPreferredWidth(100);

		grid.getModeloColuna().setCampo(0, "data");
		grid.getModeloColuna().setFormato(0, "dd/MM/yy");
		grid.getModeloColuna().setCampo(1, "parcelamento.primeiraParcela");
		grid.getModeloColuna().setCampo(2, "parcelamento.ultimaParcela");
		grid.getModeloColuna().setCampo(3, "conta.nome");
		grid.getModeloColuna().setCampo(4, "natureza.nome");
		grid.getModeloColuna().setCampo(5, "valor");
		grid.getModeloColuna().setFormato(5, Formato.MOEDA);
		grid.getModeloColuna().definirPositivoNegativo(5);
		grid.getModeloColuna().setCampo(6, "parcelamento.restante");
		grid.getModeloColuna().setFormato(6, Formato.MOEDA);
		grid.getModeloColuna().definirPositivoNegativo(6);

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
		//
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
		txtSaldoAtual.setRotulo("Saldo");

		txtDespesas.setFormato(Formato.MOEDA);
		txtSaldoAtual.setFormato(Formato.MOEDA);
		txtReceitas.setFormato(Formato.MOEDA);

		pnlSaldo.add(txtReceitas);
		pnlSaldo.add(txtDespesas);
		pnlSaldo.add(txtSaldoAtual);
		txtReceitas.setComponenteCorFonte(Color.BLUE);
		txtDespesas.setComponenteCorFonte(Color.RED);

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
		EntidadeLancamento l = (EntidadeLancamento) grid.getLinhaSelecionada();
		if (l != null) {
			lblDesc.setText(l.getDescricao());
		}
	}

	private void sair() {
		super.fechar();
	}

	private void listar() {
		List<EntidadeLancamento> lista = new ArrayList<EntidadeLancamento>();
		try {
			EntidadeConta conta = (EntidadeConta) cboConta.getValue();
			EntidadeNatureza nat = (EntidadeNatureza) cboNatureza.getValue();
			Integer cId=conta==null?null:conta.getId();
			Integer nId=nat==null?null:nat.getId();
			lista = service.listarPrevisoes(DesktopApp.getLogin(), txtDataDe.getDataHora(),txtDataAte.getDataHora(),cId,nId);
			if (lista.size() == 0)
				SSMensagem.avisa("Nenhum dado encontrado");

			grid.setValue(lista);
			total = CfipUtil.previsoes(lista);
			txtSaldoAtual.setValue(total.getSaldo());
			txtSaldoAtual.setComponenteCorFonte(total.getSaldo() < 0.0d ? Color.RED : Color.BLUE);
			txtDespesas.setValue(total.getDebito());
			txtReceitas.setValue(total.getCredito());
		} catch (Exception e) {
			e.printStackTrace();
			// Mensagem.erro(e.getMessage());
		}

	}
}
