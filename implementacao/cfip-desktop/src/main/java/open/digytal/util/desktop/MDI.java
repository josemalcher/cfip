package open.digytal.util.desktop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import open.digytal.util.Imagem;

public abstract class MDI extends JFrame {
    private JDesktopPane areaTrabalho = new JDesktopPane();
    protected JMenuBar barraMenu = new JMenuBar();
    private JLabel lblUsuario = new JLabel("USUARIO");
    private JLabel lblLogin = new JLabel("LOGIN");
    private JLabel lblAmbiente = new JLabel("DESENVOLVIMENTO");
    private JLabel lblConexao = new JLabel("CONEXÃO");

    private JLabel imagemFundo = new JLabel();
    private ImageIcon imgFundo;
    public MDI() {
        areaTrabalho.setBackground(Color.LIGHT_GRAY);
        areaTrabalho.setVisible(true);
        getContentPane().setLayout(new BorderLayout());


        JLabel lblName = new JLabel("NOME:");
        lblName.setFont(new Font("Tahoma", Font.BOLD, 11));

        JLabel lblUser = new JLabel("USUÁRIO:");
        lblUser.setFont(new Font("Tahoma", Font.BOLD, 11));

        JLabel lblEnv = new JLabel("AMBIENTE:");
        lblEnv.setFont(new Font("Tahoma", Font.BOLD, 11));

        JLabel lblVersao = new JLabel("Versão: 1.0");
        lblVersao.setFont(new Font("Tahoma", Font.BOLD, 11));

        JLabel lblCnn = new JLabel("CONEXÃO");
        lblCnn.setFont(new Font("Tahoma", Font.BOLD, 11));

        lblVersao.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblLogin.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblAmbiente.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblConexao.setFont(new Font("Tahoma", Font.BOLD, 11));

        lblLogin.setForeground(Color.BLUE);
        lblUsuario.setForeground(Color.BLUE);
        lblAmbiente.setForeground(Color.BLUE);
        lblConexao.setForeground(Color.BLUE);

        JPanel barraSessao = new JPanel();
        barraSessao.setLayout(new FlowLayout(FlowLayout.LEFT));
        getContentPane().add(barraSessao,BorderLayout.NORTH);

        barraSessao.add(lblUser);
        barraSessao.add(lblLogin);

        barraSessao.add(lblName);
        barraSessao.add(lblUsuario);

        barraSessao.add(lblEnv);
        barraSessao.add(lblAmbiente);

        barraSessao.add(lblCnn);
        barraSessao.add(lblConexao);

        barraSessao.add(lblVersao);

        getContentPane().add(areaTrabalho, BorderLayout.CENTER);
        setJMenuBar(barraMenu);
        setTitle("CFIP - Controle Financeiro Pessoal");

        this.setIconImage(Imagem.png("app").getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(new Rectangle(650, 650));
        setLocationRelativeTo(null);

        imgFundo = Imagem.jpg("fundo");
        imagemFundo.setIcon(imgFundo);
        areaTrabalho.add(imagemFundo);
        areaTrabalho.setBackground(Color.LIGHT_GRAY);
        areaTrabalho.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                desktopPane_componentResized(e);
            }
        });
    }
    private void desktopPane_componentResized(ComponentEvent e) {
        if (imgFundo == null)
            return;

        int top, left;

        top = (areaTrabalho.getWidth() / 2) - (imgFundo.getIconWidth() / 2);
        left = (areaTrabalho.getHeight() / 2) - (imgFundo.getIconHeight() / 2);

        imagemFundo.setBounds(top, left - 20, imgFundo.getIconWidth(), imgFundo.getIconHeight());
    }
    public void exibirSessao(){
        lblLogin.setText("CFIP");
        lblUsuario.setText("CFIP");
        //lblConexao.setText(Configurador.getConfiguracao().getConexao());
        lblConexao.setText("LOCAL");
    }
    public JDesktopPane getAreaTrabalho() {
        return areaTrabalho;
    }
   
    public JMenuBar getBarraMenu() {
        return barraMenu;
    }
    protected void exibir(Formulario formulario) {
		formulario.setMdi(this);
		formulario.carregar();
		formulario.exibir();
	}
    //public abstract void empresaInicial();
}
