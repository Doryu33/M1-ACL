package terminal.room;

import terminal.entities.Monster;
import terminal.tiles.Exit;
import terminal.tiles.Floor;
import terminal.tiles.Tile;
import terminal.tiles.Wall;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import static terminal.data.Data.*;

/**
 * Génère des salles soit par génération fixe soit aléatoirement
 */
public final class RoomGenerator {

    //Liste des monstres de la dernière salle générée
    private final ArrayList<Monster> monsters;
    //Objet pour générer des chiffres aléatoires
    private final Random randomNumber;
    //Salle aléatoire générée
    private Tile[][] tilesRandomRoom;
    //Pattern utilisé pour la génération de la salle. Est amené à changer régulierement.
    private char[][] actualPattern;
    //Liste de tous les patterns
    private Pattern[] patterns;
    //Nombre de pattern au total
    private int numberOfPattern;

    public RoomGenerator(){
        monsters = new ArrayList<>();
        randomNumber = new Random();
        getAllPatterns();
    }

    /**
     * Génère une salle simple utilisée pour la première salle de chaque étage.
     * Une salle vide entourée de mur, le joueur est positionné au centre, la sortie est en haut au centre.
     * @return le tableau des éléments de la salle
     */
    public Tile[][] generateSimpleRoom(){
        Tile[][] tiles = new Tile[ROOM_SIZE][ROOM_SIZE];

        for (int y = 0; y < ROOM_SIZE; y++) {
            for (int x = 0; x < ROOM_SIZE; x++) {
                //Lorsque l'on est en haut au centre
                if(x == EXIT_POS_X && y == EXIT_POS_Y){
                    tiles[y][x] = new Exit();
                }
                //Lorsque l'on est sur les bords
                else if(y == 0 || y == ROOM_SIZE-1 || x == 0 || x == ROOM_SIZE-1){
                    tiles[y][x] = new Wall();
                }else{
                    tiles[y][x] = new Floor();
                }
            }
        }
        return tiles;
    }

    /**
     * Génère une salle avec des patterns et une génération aléatoire.
     * @return l'environnement de la salle
     */
    public Tile[][] generateRandomRoom(){
        monsters.clear();

        tilesRandomRoom = generateSimpleRoom();

        int numberOfGenerations = 2;
        int generationChoice = randomNumber.nextInt(numberOfGenerations);

        int patternChoice = randomNumber.nextInt(numberOfPattern);
        actualPattern = patterns[patternChoice].getPattern();

        if (generationChoice == 0) {
            plusGeneration();
        } else if (generationChoice == 1) {
            crossGeneration();
        }

        return tilesRandomRoom;
    }

    /*
     * Représentation de la délimitation d'une salle
     * |-----------------|
     * |  1  |  2  |  3  |
     * |-----|-----|-----|
     * |  4  |  5  |  6  |
     * |-----|-----|-----|
     * |  7  |  8  |  9  |
     * |-----------------|
     */

    /**
     * Applique le pattern pour le coin haut gauche. Chiffre 1 du schéma
     */
    private void patternLeftUpCorner(){
        applyPatternTo(1,1,ROOM_SIZE/3,ROOM_SIZE/3);
    }

    /**
     * Applique le pattern pour la case haut centré. Chiffre 2 du schéma
     */
    private void patternMiddleUp(){
        applyPatternTo(ROOM_SIZE/3+1,1,(ROOM_SIZE/3)*2,ROOM_SIZE/3);
    }

    /**
     * Applique le pattern pour le coin haut droit. Chiffre 3 du schéma
     */
    private void patternRightUpCorner(){
        applyPatternTo((ROOM_SIZE/3*2)+1,1,(ROOM_SIZE/3)*3,ROOM_SIZE/3);
    }

    /**
     * Applique le pattern pour la case gauche au milieu. Chiffre 4 du schéma
     */
    private void patternLeftCenter(){
        applyPatternTo(1,(ROOM_SIZE/3)+1,ROOM_SIZE/3,(ROOM_SIZE/3)*2);
    }

    /**
     * Applique le pattern pour la case au centre. Chiffre 5 du schéma
     */
    private void patternMiddleCenter() { applyPatternTo(ROOM_SIZE/3+1,(ROOM_SIZE/3)+1,(ROOM_SIZE/3)*2,(ROOM_SIZE/3)*2);}

    /**
     * Applique le pattern pour la case droite au milieu. Chiffre 6 du schéma
     */
    private void patternRightCenter() { applyPatternTo((ROOM_SIZE/3*2)+1,(ROOM_SIZE/3)+1,(ROOM_SIZE/3)*3,(ROOM_SIZE/3)*2);}

    /**
     * Applique le pattern pour le coin bas gauche. Chiffre 7 du schéma
     */
    private void patternLeftDownCorner(){
        applyPatternTo(1,(ROOM_SIZE/3)*2+1,ROOM_SIZE/3,(ROOM_SIZE/3)*3);
    }

    /**
     * Applique le pattern pour le coin bas centré. Chiffre 8 du schéma
     */
    private void patternMiddleDown(){
        applyPatternTo(ROOM_SIZE/3+1,(ROOM_SIZE/3)*2+1,(ROOM_SIZE/3)*2,(ROOM_SIZE/3)*3);
    }

    /**
     * Applique le pattern pour le coin bas droit. Chiffre 9 du schéma
     */
    private void patternRightDownCorner(){
        applyPatternTo((ROOM_SIZE/3*2)+1,(ROOM_SIZE/3)*2+1,(ROOM_SIZE/3)*3,(ROOM_SIZE/3)*3);
    }

    /**
     * Applique les patterns de sorte à former une croix dans la salle. X
     */
    private void crossGeneration(){
        patternLeftUpCorner();
        patternRightUpCorner();
        patternMiddleCenter();
        patternLeftDownCorner();
        patternRightDownCorner();
    }

    /**
     * Applique les patterns de sorte à former un plus dans la salle. +
     * La case en bas au centre reste vide pour laisser la place au joueur d'apparaitre.
     */
    private void plusGeneration(){
        patternMiddleUp();
        patternLeftCenter();
        patternMiddleCenter();
        patternRightCenter();
        //patternMiddleDown();
    }

    /**
     * Applique le pattern tiré aléatoirement dans la salle aux positions données
     * @param xS point X du coin haut gauche
     * @param yS point Y du coin haut gauche
     * @param xE point X du coin bas droite
     * @param yE point Y du coin bas droite
     */
    private void applyPatternTo(int xS, int yS, int xE, int yE){
        int xPattern;
        int yPattern = 0;
        for (int y = yS; y <= yE; y++) {
            xPattern = 0;
            for (int x = xS; x <= xE; x++) {
                switch (actualPattern[yPattern][xPattern]){
                    case 'X':
                        tilesRandomRoom[y][x] = new Wall();
                        break;
                    case 'M':
                        monsters.add(new Monster(x,y));
                        break;
                }
                xPattern++;
            }
            yPattern++;
        }
    }

    /**
     * Getter de la liste des monstres
     * @return liste des monstres
     */
    public ArrayList<Monster> getGeneratedMonsters(){
        return monsters;
    }

    /**
     * Récupère tous les patterns depuis le fichier patterns.txt.
     * Un seul appel au début du programme est nécessaire.
     */
    private void getAllPatterns(){
        char[][] pattern = new char[ROOM_SIZE/3][ROOM_SIZE/3];
        int indexPatternsArray = 0;
        int y = 0;
        int x;
        String line;
        //Lire le fichier patterns.txt
        InputStream patternsFile = getClass().getClassLoader().getResourceAsStream("patterns.txt");
        Scanner fileReader = new Scanner(patternsFile);
        //Lire la première ligne qui contient le nombre de pattern dans le fichier
        line = fileReader.nextLine();
        numberOfPattern = Integer.parseInt(String.valueOf(line.charAt(0)));
        //Initialisé le tableau de patterns
        patterns = new Pattern[numberOfPattern];

        //Tant que nous ne sommes pas à la fin du fichier
        while (fileReader.hasNextLine()) {
            line = fileReader.nextLine();
            //Si la ligne commence par 's' alors nous sommes au début d'un nouveau pattern. On initialise alors le tableau
            if(line.charAt(0) == 's'){
                pattern = new char[ROOM_SIZE/3][ROOM_SIZE/3];
            }

            //Initialise chaque case
            if(line.charAt(0) == '.' || line.charAt(0) == 'X' || line.charAt(0) == 'M'){
                x = 0;
                for (int i = 0; i < line.length(); i++) {
                    pattern[y][x] = line.charAt(i);
                    x++;
                }
                y++;
            }

            //Si la ligne commence par 'e' alors nous sommes à la fin d'un pattern. On initialise alors l'objet Pattern
            if(line.charAt(0) == 'e'){
                patterns[indexPatternsArray] = new Pattern(pattern);
                indexPatternsArray++;
                y = 0;
            }
        }
        fileReader.close();
    }

    /**
     * Class pour garder en mémoire les différents patterns
     */
    private static final class Pattern{
        private final char[][] pattern;
        private Pattern(char[][] p){
            this.pattern = p;
        }
        public char[][] getPattern() {
            return pattern;
        }
    }
}


