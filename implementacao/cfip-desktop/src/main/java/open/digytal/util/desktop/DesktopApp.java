package open.digytal.util.desktop;

public class DesktopApp {
	private static Splash splash;
	@Deprecated
	public static String getLogin() {
		return "gso";
	}
	public static void exibirSplash() {
		splash = new Splash();
		splash.setVisible(true);
	}

	public static void fecharSplash() {
		if (splash != null)
			splash.dispose();
	}
}
