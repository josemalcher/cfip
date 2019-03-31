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
}
