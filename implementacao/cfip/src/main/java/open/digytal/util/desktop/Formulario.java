package open.digytal.util.desktop;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.LayoutManager;
import java.beans.PropertyVetoException;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EtchedBorder;

import open.digytal.util.Imagem;
import open.digytal.util.TipoOperacao;
import open.digytal.util.desktop.ss.SSCabecalho;
import open.digytal.util.desktop.ss.SSMensagem;
import open.digytal.util.desktop.ss.SSRodape;

//WindowBuilder
//http://download.eclipse.org/windowbuilder/WB/integration/4.7/
public abstract class Formulario extends JPanel {
	private Object respostaDialogo;
	private static Object selecionado;
	private Formulario dono;
	private SSCabecalho cabecalho = new SSCabecalho();
	private JPanel conteudo = new JPanel();
	private SSRodape rodape = new SSRodape();
	private MDI mdi;
	protected TipoOperacao operacao;
	public void setOperacao(TipoOperacao operacao) {
		this.operacao = operacao;
	}
	public Formulario() {
		init(new GridBagLayout());
	}
	public Formulario(LayoutManager laytout) {
		init(laytout);
	}

	private String login;
	private void init(LayoutManager laytout) {
		this.conteudo.setLayout(laytout);
		this.setLayout(new BorderLayout());
		this.conteudo.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		this.setTitulo("Informe um título");
		this.setDescricao("Informe uma descrição");
		this.add(cabecalho, BorderLayout.NORTH);
		this.add(conteudo, BorderLayout.CENTER);
		this.add(rodape, BorderLayout.SOUTH);
	}

	public void setTitulo(String titulo) {
		this.cabecalho.setTitulo(titulo);
	}
	public String getTitulo() {
		return cabecalho.getTitulo();
	}
	public void setDescricao(String descricao) {
		this.cabecalho.setDescricao(descricao);
	}
	protected void botoesEsquerda() {
		this.rodape.setLayout(new FlowLayout(FlowLayout.LEFT));
	}
	public void setMdi(MDI mdi) {
		this.mdi = mdi;
	}

	public MDI getMdi() {
		return mdi;
	}

	public JPanel getConteudo() {
		return conteudo;
	}

	public SSRodape getRodape() {
		return rodape;
	}

	public void setConteudoLayout(LayoutManager layout) {
		conteudo.setLayout(layout);
	}

	public void setAlinhamentoRodape(int alinhamento) {
		rodape.setAlinhamento(alinhamento);
	}
	public void ocultarRodape() {
		rodape.setVisible(false);
	}
	public void ocultarCabecalho() {
		cabecalho.setVisible(false);
	}
	public void exibir() {
		this.exibir(this);
	}

	public void exibir(Formulario frm) {
		if (frm != this) {
			frm.setMdi(this.getMdi());
		}
		frm.setLogin(this.getLogin());
		JInternalFrame internal = new JInternalFrame(getTitulo());
		internal.setFrameIcon(Imagem.png("app"));
		internal.getRootPane().setWindowDecorationStyle(JRootPane.NONE);

		internal.setVisible(true);
		internal.setResizable(true);
		internal.setContentPane(frm);
		internal.setSize(frm.getWidth(), frm.getHeight());
		try {
			internal.setSelected(true);
			internal.pack();
			centralizar(internal);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		frm.carregar();
		mdi.getAreaTrabalho().add(internal);
		mdi.getAreaTrabalho().getDesktopManager().activateFrame(internal);
		   
		
	}

	public void fechar(Object resposta) {
		if (resposta != null) {
			if(dono!=null)
				dono.respostaDialogo = resposta;
			selecionado=resposta;
			fechar();
		} else {
			SSMensagem.avisa("Selecione um item da item");
		}
	}

	public void cancelar() {
		boolean resposta = SSMensagem.pergunta("Deseja cancelar esta operação");
		if (resposta) {
			fechar();
		}
	}

	public void fechar() {
		if(isInternal(this))
			removerFormulario();
		else if(isJanela(this))
			fecharJanela();
		else if (isDialogo(this))
			fecharDialogo();
		else
			removerFormulario();
	}
	public boolean isInternal(Formulario form) {
		return SwingUtilities.getAncestorOfClass(JInternalFrame.class, form) != null;
	}
	public boolean isJanela(Formulario form) {
		return SwingUtilities.getAncestorOfClass(JFrame.class, form) != null;
	}
	public boolean isDialogo(Formulario form) {
		return SwingUtilities.getAncestorOfClass(JDialog.class, form) != null;
	}
	private void removerFormulario() {
		JInternalFrame iframe = (JInternalFrame) SwingUtilities.getAncestorOfClass(JInternalFrame.class, this);
		if (mdi != null) {
			mdi.getAreaTrabalho().remove(iframe);
			mdi.getAreaTrabalho().repaint();
		}
	}
	private void fecharDialogo() {
		JDialog dialog = (JDialog) SwingUtilities.getAncestorOfClass(JDialog.class, this);
		dialog.dispose();
		dialog.setVisible(false);
	}
	private void fecharJanela() {
		JFrame frame = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, this);
		frame.setVisible(false);
		frame.dispose();
	}
	private void centralizar(JInternalFrame componente) {
		Dimension dim = mdi.getSize();
		int x = dim.width / 2 - componente.getSize().width / 2;
		int y = dim.height / 2 - componente.getSize().height / 2;
		y = y - 50; // opcional
		componente.setLocation(x, y);
		componente.setVisible(true);
	}

	public Object dialogo(Formulario form) {
		this.respostaDialogo = null;
		form.carregar();
		criarDialogo(form);
		return respostaDialogo;
	}
	public static Object exibirDialogo(JPanel form) {
		selecionado=null;
		criarDialogo(form);
		return selecionado;
	}
	public static void exibirJanela(JPanel form) {
		JFrame frame = new JFrame();
		frame.setSize(form.getWidth(),form.getHeight());
		frame.setUndecorated(true);
		frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		frame.setLayout(new BorderLayout());
		frame.getContentPane().add(form, BorderLayout.CENTER);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}
	private static void criarDialogo(JPanel form) {
		JDialog dialog = new JDialog();
		dialog.setResizable(false);
		dialog.setUndecorated(true);
		dialog.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		dialog.setModal(true);
		dialog.setContentPane(form);
		dialog.pack();
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
		dialog.dispose();
	}
	public void setEntidade(Object entidade) {

	}
	public void novoCadastro() {
		operacao=TipoOperacao.INCLUSAO;
		setEntidade(null);
	}
	public void carregar() {

	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getLogin() {
		return login;
	}
}
