package open.digytal.desktop;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import open.digytal.controller.UsuarioController;
import open.digytal.model.EntidadeUsuario;
import open.digytal.repository.ContaRepository;
import open.digytal.util.Imagem;
import open.digytal.util.desktop.ss.SSBotao;
import open.digytal.util.desktop.ss.SSCabecalho;
import open.digytal.util.desktop.ss.SSCampoSenha;
import open.digytal.util.desktop.ss.SSCampoTexto;
import open.digytal.util.desktop.ss.SSMensagem;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FrmUsuario extends JFrame { //extends FrmPerfil {
    private SSBotao cmdConfirmar = new SSBotao();
    private SSBotao cmdSair = new SSBotao();
    private SSCampoTexto txtEmail = new SSCampoTexto();
    private SSCampoTexto txtLogin = new SSCampoTexto();
    private SSCampoSenha txtSenha = new SSCampoSenha();
    private final SSCampoTexto txtNome = new SSCampoTexto();
    private final SSCampoSenha txtRepeteSenha = new SSCampoSenha();
    private EntidadeUsuario usuario;
    @Autowired
    private UsuarioController service;
    @Autowired
	private PasswordEncoder encoder;
    public FrmUsuario() {
        init();
    }

    private void init() {
        this.setIconImage(Imagem.png("app").getImage());
        //setTitle(Configurador.getProduto());
        setTitle("CFIP");
        
        setSize(new Dimension(263, 350));
        setLocationRelativeTo(null);
        txtSenha.setTudoMaiusculo(false);
        txtLogin.setTudoMaiusculo(false);
        txtLogin.setText("login");
        txtLogin.setRotulo("Login");
        JPanel login = new JPanel();
        login.setBorder(new EmptyBorder(5, 5, 5, 5));
        login.setLayout(new BorderLayout(0, 0));
        setContentPane(login);

        SSCabecalho cabecalho = new SSCabecalho();
        cabecalho.setDescricao("Registro de usuÃ¡rios no sistema");
        cabecalho.setTitulo("USUÃ�RIO");
        login.add(cabecalho, BorderLayout.NORTH);

        JPanel conteudo = new JPanel();
        conteudo.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        login.add(conteudo, BorderLayout.CENTER);
        GridBagLayout gbl_conteudo = new GridBagLayout();

        conteudo.setLayout(gbl_conteudo);
        txtEmail.setColunas(10);

        GridBagConstraints gbc_txtLogin = new GridBagConstraints();
        gbc_txtLogin.gridwidth = 2;
        gbc_txtLogin.anchor = GridBagConstraints.NORTHWEST;
        gbc_txtLogin.insets = new Insets(3, 3, 0, 3);
        gbc_txtLogin.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtLogin.gridx = 0;
        gbc_txtLogin.gridy = 2;
        conteudo.add(txtLogin, gbc_txtLogin);

        txtEmail.setRotulo("E-mail");
        txtEmail.setTudoMaiusculo(false);
        GridBagConstraints gbc_txtUsuario = new GridBagConstraints();
        gbc_txtUsuario.gridwidth = 2;
        gbc_txtUsuario.anchor = GridBagConstraints.NORTHWEST;
        gbc_txtUsuario.insets = new Insets(3, 3, 0, 3);
        gbc_txtUsuario.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtUsuario.gridx = 0;
        gbc_txtUsuario.gridy = 1;
        conteudo.add(txtEmail, gbc_txtUsuario);

        txtSenha.setRotulo("Senha");

        GridBagConstraints gbc_txtSenha = new GridBagConstraints();
        gbc_txtSenha.gridwidth = 2;
        gbc_txtSenha.anchor = GridBagConstraints.NORTHEAST;
        gbc_txtSenha.insets = new Insets(3, 3, 0, 3);
        gbc_txtSenha.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtSenha.gridx = 0;
        gbc_txtSenha.gridy = 3;
        conteudo.add(txtSenha, gbc_txtSenha);

        GridBagConstraints gbc_txtNome = new GridBagConstraints();
        gbc_txtNome.gridwidth = 2;
        gbc_txtNome.anchor = GridBagConstraints.NORTHWEST;
        gbc_txtNome.insets = new Insets(3, 3, 0, 3);
        gbc_txtNome.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtNome.gridx = 0;
        gbc_txtNome.gridy = 0;
        txtNome.setRotulo("Nome");
        conteudo.add(txtNome, gbc_txtNome);

        GridBagConstraints gbc_txtRepeteSenha = new GridBagConstraints();
        gbc_txtRepeteSenha.weighty = 1.0;
        gbc_txtRepeteSenha.gridwidth = 2;
        gbc_txtRepeteSenha.weightx = 1.0;
        gbc_txtRepeteSenha.anchor = GridBagConstraints.NORTHWEST;
        gbc_txtRepeteSenha.insets = new Insets(3, 3, 3, 3);
        gbc_txtRepeteSenha.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtRepeteSenha.gridx = 0;
        gbc_txtRepeteSenha.gridy = 4;
        txtRepeteSenha.setRotulo("Repete Senha");
        conteudo.add(txtRepeteSenha, gbc_txtRepeteSenha);

        JPanel botoes = new JPanel();
        botoes.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        FlowLayout fl_botoes = (FlowLayout) botoes.getLayout();
        fl_botoes.setAlignment(FlowLayout.RIGHT);
        login.add(botoes, BorderLayout.SOUTH);

        cmdConfirmar.setText("Confirmar");
        cmdConfirmar.setIcone("ok");

        botoes.add(cmdConfirmar);
        cmdSair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fechar();

            }
        });

        cmdConfirmar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               confirmar();

            }
        });

        cmdSair.setText("Sair");
        cmdSair.setIcone("fechar");
        botoes.add(cmdSair);
        txtEmail.setTudoMaiusculo(false);
        txtSenha.setTudoMaiusculo(false);
        txtRepeteSenha.setTudoMaiusculo(false);

    }


    public void confirmar()  {
        if (txtLogin.getText() == null || txtLogin.getText().trim().isEmpty()) {
           SSMensagem.avisa ("Informe o Login");
        }
        if (txtSenha.getText() == null || txtSenha.getText().trim().isEmpty()) {
            SSMensagem.avisa ("Informe a senha");
        }
        if (!txtSenha.getSenha().equals(txtRepeteSenha.getSenha())) {
            SSMensagem.avisa ("Senhas nÃ£o conferem");
        }
        try {
            usuario = new EntidadeUsuario();
            usuario.setEmail(txtEmail.getText());
            usuario.setNome(txtNome.getText());
            usuario.setSenha(encoder.encode(txtSenha.getText()));
            usuario.setLogin(txtLogin.getText());
            service.incluir(usuario);
            SSMensagem.informa("Usuario registrado com sucesso\nAcesse o sistema");
            fechar();
        }catch (Exception ex){
            ex.printStackTrace();
            SSMensagem.erro(ex.getMessage());
        }
    }

    private void fechar() {
        this.dispose();
    }
    public void prosseguir() {
        SSMensagem.informa("Usuario registrado com sucesso\nAcesse o sistema");
        fechar();
    }

    
}
