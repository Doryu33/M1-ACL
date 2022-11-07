package dungeoncrypt.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import dungeoncrypt.game.views.ScreenManager;
import terminal.room.RoomManager;
import static dungeoncrypt.game.data.Data.RENDER_SCALE;
import static dungeoncrypt.game.data.Data.ROOM_SIZE;

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
			config.setWindowedMode(RENDER_SCALE*ROOM_SIZE,RENDER_SCALE*ROOM_SIZE);
			config.setForegroundFPS(60);
			config.setTitle("DungeonCrypt");
			new Lwjgl3Application(new ScreenManager(), config);
		}
	}
}
