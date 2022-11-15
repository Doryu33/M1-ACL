package dungeoncrypt.game.tools;

import dungeoncrypt.game.room.Room;
import dungeoncrypt.game.tiles.Tile;
import dungeoncrypt.game.tiles.special.SpecialTile;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import static java.nio.file.StandardOpenOption.CREATE;

public class SaveManager {
    public int PlayerHealth;
    public int PlayerScore;
    public char[][] tilesList;


    public SaveManager() {

    }

    public void saveProgression(String pathToSave, Room room){
        //remplir les champs
        this.tilesList = room.getRoomComposition();
        this.PlayerHealth = room.getPlayerHP();
        this.PlayerScore = room.getPlayerScore();
        //Ouvrir le fichier
        Path chemin = Paths.get(pathToSave+".txt");
        // convertit String en un tableau d'octets
        byte[] data = Integer.toString(PlayerHealth).getBytes();
        byte[] data2 = Integer.toString(PlayerScore).getBytes();



        OutputStream output;
        //Ecrire dans le fichier
        try {
            // Un objet BufferedOutputStream est affecté à la référence OutputStream.
            output = new BufferedOutputStream(Files.newOutputStream(chemin, CREATE));
            // Ecrire dans le fichier
            output.write(data);
            output.flush();
            output.write(data2);
            // vider le tampon
            output.flush();

            // fermer le fichier
            output.close();

        } catch (Exception e) {
            System.out.println("Message " + e);
        }



        //Sauvegarder le fichier

        //Fermer le fichier

        this.toString();
    }

    public void loadProgression(String pathToSave){
        //Ouvrir le fichier

        //remplir les champs

        //Fermer le fichier
        this.toString();
    }

    @Override
    public String toString() {
        String res = "SaveManager{" +
                "PlayerHealth=" + PlayerHealth +
                "\nPlayerScore=" + PlayerScore +
                "\ntilesList=" +
                '}';
        for (char[] c:tilesList) {
            for (char d:c) {
                System.out.print(d);
                res += d;
            }
            res += "\n";
        }
        return res;
    }
}
