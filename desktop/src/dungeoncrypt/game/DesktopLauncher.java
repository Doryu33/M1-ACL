package dungeoncrypt.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import dungeoncrypt.game.views.MainScreen;
import terminal.room.RoomManager;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	private static boolean terminalMode = false;
	public static void main (String[] arg) {
		if(terminalMode){
			RoomManager rm = new RoomManager();
			System.out.println(rm.displayRoomConsole());
			boolean inGame = true;
			//Boucle de jeu en mode terminal
			while(inGame){
				rm.updatePositionRoom();
				rm.checkNextRoomIsRequested();
				System.out.println(rm.displayRoomConsole());
			}
		} else {
			Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
			config.setForegroundFPS(60);
			config.setTitle("DungeonCrypt");
			new Lwjgl3Application(new MainScreen(), config);
		}
	}
}
