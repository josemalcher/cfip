package open.digytal.desktop;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import open.digytal.CfipDesktopApp;
import open.digytal.model.Sessao;
import open.digytal.service.UsuarioService;
import open.digytal.util.desktop.LoginPanel;
import open.digytal.util.desktop.ss.SSMensagem;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FrmLogin extends LoginPanel {
	private static final Logger logger = LogManager.getLogger(FrmLogin.class);
	@Autowired
	private UsuarioService service;
	public FrmLogin() {
		super.logar(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logarAction();
			}
		});
	}

	private void logarAction() {
		try {
			Sessao sessao = service.login(getLogin(),getSenha());
			if (sessao.getUsuario()==null) {
				SSMensagem.avisa("Credencial Inválida");
				FrmUsuario frm = CfipDesktopApp.getBean(FrmUsuario.class);
				frm.setVisible(true);
			} else {
				MDICfip mdi = CfipDesktopApp.getBean(MDICfip.class);
				mdi.exibirSessao();
				mdi.setVisible(true);
				this.dispose();
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}
}
