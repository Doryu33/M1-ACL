package dungeoncrypt.game.tools;

import dungeoncrypt.game.room.Room;
import dungeoncrypt.game.tiles.Tile;
import dungeoncrypt.game.tiles.special.SpecialTile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import static dungeoncrypt.game.data.Data.ROOM_SIZE;
import static java.nio.file.StandardOpenOption.CREATE;

public class SaveManager {
    public int PlayerHealth;
    public int PlayerScore;
    public char[][] tilesList = new char[ROOM_SIZE][ROOM_SIZE];
    public float posX;
    public float posY;


    public SaveManager() {

    }

    /**
     * Sauvegarde la progression du joueur dans un .txt
     * @param pathToSave nom du fichier
     * @param room salle Contenant les infos a sauvegarder
     */
    public void saveProgression(String pathToSave, Room room){
        //remplir les champs
        this.tilesList = room.getRoomComposition();
        this.PlayerHealth = room.getPlayerHP();
        this.PlayerScore = room.getPlayerScore();
        this.posX = room.getPlayerPosX();
        this.posY = room.getPlayerPosY();
        //Ouvrir le fichier
        Path chemin = Paths.get(pathToSave+".txt");
        // convertit String en un tableau d'octets
        byte[] endline = "\n".getBytes(StandardCharsets.UTF_8);
        byte[] PlayerHPData = Integer.toString(PlayerHealth).getBytes();
        byte[] PlayerScoreData = Integer.toString(PlayerScore).getBytes();
        byte[] PlayerPosXData = Float.toString(posX).getBytes(StandardCharsets.UTF_8);
        byte[] PlayerPosYData = Float.toString(posY).getBytes(StandardCharsets.UTF_8);
        byte[] RoomDesignData = listTileToString().getBytes(StandardCharsets.UTF_8);

        OutputStream output;
        //Ecrire dans le fichier
        try {
            // Un objet BufferedOutputStream est affecté à la référence OutputStream.
            output = new BufferedOutputStream(Files.newOutputStream(chemin, CREATE));
            // Ecrire dans le fichier
            output.write(PlayerHPData);
            output.write(endline);
            output.flush();

            output.write(PlayerScoreData);
            output.write(endline);
            output.flush();

            output.write(PlayerPosXData);
            output.write(endline);
            output.flush();

            output.write(PlayerPosYData);
            output.write(endline);
            output.flush();

            output.write(RoomDesignData);
            output.flush();
            // fermer le fichier
            output.close();

        } catch (Exception e) {
            System.out.println("Message " + e);
        }
    }

    /**
     * Instancie les champs de SaveManager via une sauvegarde
     * @param pathToSave Nom du fichier
     */
    public void loadProgression(String pathToSave){
        Path chemin = Paths.get(pathToSave+".txt");
        InputStream input;
        try{
            input = Files.newInputStream(chemin);

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String s = null;
            char c;

            s = reader.readLine();
            this.PlayerHealth = Integer.parseInt(s);

            s = reader.readLine();
            this.PlayerScore = Integer.parseInt(s);

            s = reader.readLine();
            this.posX = Float.parseFloat(s);

            s = reader.readLine();
            this.posY = Float.parseFloat(s);


            for (int i = 0; i < ROOM_SIZE; i++) {
                s = reader.readLine();
                for (int j = 0; j < s.length(); j++) {
                    c = s.charAt(j);
                    this.tilesList[i][j] = c;
                }

            }

        } catch (IOException e){
            System.out.println("Message "+ e);
        }
    }

    /**
     * Fonction auxiliaire permettant de renvoyer un string correspondant a la liste des tuiles
     * @return Un string correspondant a la liste des tuiles
     */
    private String listTileToString(){
        String res="";
        for (char[] c:this.tilesList) {
            for (char d:c){
                res += d;
            }
            res+="\n";
        }
        return res;
    }

    @Override
    public String toString() {
        String res="";
        res+= "HP = "+PlayerHealth+"\n";
        res+= "Score = "+PlayerHealth+"\n";
        res+= "PosX = "+posX+"\n";
        res+= "PosY = "+posY+"\n";
        res+="Tableau: \n";
        for (char[] c:tilesList) {
            for (char d:c) {
                res+=d;
            }
            res+="\n";
        }

        return res;
    }
}
