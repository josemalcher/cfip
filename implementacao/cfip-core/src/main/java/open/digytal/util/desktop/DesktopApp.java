package open.digytal.util.desktop;

public class DesktopApp {
	private static Splash splash;

	public static void exibirSplash() {
		splash = new Splash();
		splash.setVisible(true);
	}

	public static void fecharSplash() {
		if (splash != null)
			splash.dispose();
	}
	/*
	 * public static Sessao getSessao() { return sessao; }
	 */

	/*
	 * public static void configurarSessao(Credencial credencial) { sessao = new
	 * Sessao(); sessao.setLogin(credencial.getUsuario().getLogin());
	 * sessao.setUsuario(credencial.getUsuario().getNome()); }
	 */

}
