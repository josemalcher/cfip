package open.digytal.util.desktop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import open.digytal.util.Imagem;
import open.digytal.util.ambiente.Configurador;

public class Splash extends JFrame {
    private JLabel logotipo;
    public Splash() {

        logotipo = new JLabel(Imagem.jpg( "splash"));
        getContentPane().add(logotipo, BorderLayout.CENTER);
        logotipo.setBorder(BorderFactory.createEtchedBorder());
        JLabel lblVersao = new JLabel(Configurador.getFabricante() + " - " + Configurador.getProduto() + " - Versão: 1.0");
        lblVersao.setBorder(BorderFactory.createEtchedBorder());
        lblVersao.setFont(new Font("Tahoma", Font.BOLD, 11));
        lblVersao.setForeground(Color.BLUE);
        lblVersao.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(lblVersao, BorderLayout.SOUTH);
        setSize(400,300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setVisible(true);
        setLocationRelativeTo(null);
    }
}
