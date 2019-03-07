package open.digytal.util.desktop;

public class DesktopApp {
	private static Splash splash;
	private static String login;
	public static void exibirSplash() {
		splash = new Splash();
		splash.setVisible(true);
	}

	public static void fecharSplash() {
		if (splash != null)
			splash.dispose();
	}
	public static void setLogin(String novoLogin) {
		login = novoLogin;
	}
	public static String getLogin() {
		return login;
	}

}
