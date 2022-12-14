package hunt.potato;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import hunt.potato.utils.Constants;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Potato Hunt");
		config.setWindowedMode(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
		config.useVsync(true);
		new Lwjgl3Application(new PotatoHunt(), config);
	}
}
