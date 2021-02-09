package polygonull.noskulls.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import polygonull.noskulls.NoSkulls;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 480;
		config.height = 854;
		config.x = 750;
		config.y = 50;
		new LwjglApplication(new NoSkulls(), config);
	}
}