package dungeoncrypt.game.room;

import dungeoncrypt.game.entities.Monster;
import dungeoncrypt.game.tiles.classic.Floor;
import dungeoncrypt.game.tiles.Tile;
import dungeoncrypt.game.tiles.classic.Wall;
import dungeoncrypt.game.tiles.special.Exit;
import dungeoncrypt.game.tiles.special.HealingTile;
import dungeoncrypt.game.tiles.special.SpecialTile;
import dungeoncrypt.game.tiles.special.Trap;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import static dungeoncrypt.game.data.Data.*;

/**
 * Génère des salles soit par génération fixe soit aléatoirement
 */
public final class RoomGenerator {

    //Liste des monstres de la dernière salle générée
    private final ArrayList<Monster> monsters;
    //Objet pour générer des chiffres aléatoires
    private final Random randomNumber;
    //Liste de toutes les tuile spéciales de la salle
    private final ArrayList<SpecialTile> specialTileList;
    //Salle aléatoire générée
    private Tile[][] tilesRandomRoom;
    //Pattern utilisé pour la génération de la salle. Est amené à changer régulierement.
    private char[][] actualPattern;
    //Liste de tous les patterns
    private RoomGenerator.Pattern[] patterns;
    //Nombre de pattern au total
    private int numberOfPattern;

    public RoomGenerator(){
        monsters = new ArrayList<>();
        specialTileList = new ArrayList<>();
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
                    Exit exit = new Exit(EXIT_SPECIAL_TYPE_NEW_ROOM);
                    tiles[y][x] = exit;
                    specialTileList.add(exit);
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
        specialTileList.clear();

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
     * @param pattern à appliquer
     */
    private void patternLeftUpCorner(char[][] pattern){
        applyPatternTo(1,1,ROOM_SIZE/3,ROOM_SIZE/3,pattern);
    }

    /**
     * Applique le pattern pour la case haut centré. Chiffre 2 du schéma
     * @param pattern à appliquer
     */
    private void patternMiddleUp(char[][] pattern){
        applyPatternTo(ROOM_SIZE/3+1,1,(ROOM_SIZE/3)*2,ROOM_SIZE/3,pattern);
    }

    /**
     * Applique le pattern pour le coin haut droit. Chiffre 3 du schéma
     * @param pattern à appliquer
     */
    private void patternRightUpCorner(char[][] pattern){
        applyPatternTo((ROOM_SIZE/3*2)+1,1,(ROOM_SIZE/3)*3,ROOM_SIZE/3,pattern);
    }

    /**
     * Applique le pattern pour la case gauche au milieu. Chiffre 4 du schéma
     * @param pattern à appliquer
     */
    private void patternLeftCenter(char[][] pattern){
        applyPatternTo(1,(ROOM_SIZE/3)+1,ROOM_SIZE/3,(ROOM_SIZE/3)*2,pattern);
    }

    /**
     * Applique le pattern pour la case au centre. Chiffre 5 du schéma
     * @param pattern à appliquer
     */
    private void patternMiddleCenter(char[][] pattern) { applyPatternTo(ROOM_SIZE/3+1,(ROOM_SIZE/3)+1,(ROOM_SIZE/3)*2,(ROOM_SIZE/3)*2,pattern);}

    /**
     * Applique le pattern pour la case droite au milieu. Chiffre 6 du schéma
     * @param pattern à appliquer
     */
    private void patternRightCenter(char[][] pattern) { applyPatternTo((ROOM_SIZE/3*2)+1,(ROOM_SIZE/3)+1,(ROOM_SIZE/3)*3,(ROOM_SIZE/3)*2,pattern);}

    /**
     * Applique le pattern pour le coin bas gauche. Chiffre 7 du schéma
     * @param pattern à appliquer
     */
    private void patternLeftDownCorner(char[][] pattern){
        applyPatternTo(1,(ROOM_SIZE/3)*2+1,ROOM_SIZE/3,(ROOM_SIZE/3)*3,pattern);
    }

    /**
     * Applique le pattern pour le coin bas centré. Chiffre 8 du schéma
     * @param pattern à appliquer
     */
    private void patternMiddleDown(char[][] pattern){
        applyPatternTo(ROOM_SIZE/3+1,(ROOM_SIZE/3)*2+1,(ROOM_SIZE/3)*2,(ROOM_SIZE/3)*3,pattern);
    }

    /**
     * Applique le pattern pour le coin bas droit. Chiffre 9 du schéma
     * @param pattern à appliquer
     */
    private void patternRightDownCorner(char[][] pattern){
        applyPatternTo((ROOM_SIZE/3*2)+1,(ROOM_SIZE/3)*2+1,(ROOM_SIZE/3)*3,(ROOM_SIZE/3)*3,pattern);
    }

    /**
     * Applique les patterns de sorte à former une croix dans la salle. X
     */
    private void crossGeneration(){
        patternLeftUpCorner(getRandomPattern());
        patternRightUpCorner(getRandomPattern());
        patternMiddleCenter(getRandomHealPattern());
        patternLeftDownCorner(getRandomPattern());
        patternRightDownCorner(getRandomPattern());
    }

    /**
     * Applique les patterns de sorte à former un plus dans la salle. +
     * La case en bas au centre reste vide pour laisser la place au joueur d'apparaitre.
     */
    private void plusGeneration(){
        patternMiddleUp(getRandomPattern());
        char[][] randomPattern = getRandomPattern();
        patternLeftCenter(randomPattern);

        patternMiddleCenter(getRandomHealPattern());

        invertPatternVertically(randomPattern);
        invertPatternHorizontally(randomPattern);
        patternRightCenter(randomPattern);
        //patternMiddleDown();
    }

    /**
     * Séléctionner un pattern qui possède une/plusieurs tuiles de soin
     * @return un pattern qui possède une tuile de soin
     */
    private char[][] getRandomHealPattern(){
        int patternChoice = randomNumber.nextInt(numberOfPattern);
        while(!patterns[patternChoice].hasHeal){
            patternChoice = randomNumber.nextInt(numberOfPattern);
        }
        return patterns[patternChoice].getPattern();
    }

    /**
     * Séléctionner un pattern aléatoire mais qui ne possède de tuiles de soin
     * @return un pattern aléatoire sans tuile de soin
     */
    private char[][] getRandomPattern(){
        int patternChoice = randomNumber.nextInt(numberOfPattern);
        while(patterns[patternChoice].hasHeal){
            patternChoice = randomNumber.nextInt(numberOfPattern);
        }
        return patterns[patternChoice].getPattern();
    }

    /**
     * Inverse horizontallement le pattern en parametre
     * @param pattern à inverser
     */
    private void invertPatternHorizontally(char[][] pattern){
        for(int i = 0; i < (pattern.length / 2); i++) {
            char[] temp = pattern[i];
            pattern[i] = pattern[pattern.length - i - 1];
            pattern[pattern.length - i - 1] = temp;
        }
    }

    /**
     * Inverse verticalement le pattern en parametre
     * @param pattern à inverser
     */
    private void invertPatternVertically(char[][] pattern){
        for (int x = 0; x < PATTERN_SIZE; x++) {
            for (int y = 0; y < PATTERN_SIZE/2; y++) {
                char tmp = pattern[x][PATTERN_SIZE - y - 1];
                pattern[x][PATTERN_SIZE - y - 1] = pattern[x][y];
                pattern[x][y] = tmp;
            }
        }
    }

    /**
     * Applique le pattern tiré aléatoirement dans la salle aux positions données
     * @param xS point X du coin haut gauche
     * @param yS point Y du coin haut gauche
     * @param xE point X du coin bas droite
     * @param yE point Y du coin bas droite
     * @param pattern à appliquer
     */
    private void applyPatternTo(int xS, int yS, int xE, int yE, char[][] pattern){
        int xPattern;
        int yPattern = 0;
        for (int y = yS; y <= yE; y++) {
            xPattern = 0;
            for (int x = xS; x <= xE; x++) {
                switch (pattern[yPattern][xPattern]){
                    case 'X':
                        tilesRandomRoom[y][x] = new Wall();
                        break;
                    case 'T':
                        Trap trap = new Trap();
                        tilesRandomRoom[y][x] = trap;
                        specialTileList.add(trap);
                        break;
                    case 'H':
                        HealingTile healingTile = new HealingTile();
                        tilesRandomRoom[y][x] = healingTile;
                        specialTileList.add(healingTile);
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
        InputStream patternsFile = getClass().getClassLoader().getResourceAsStream("patterns/patterns.txt");
        System.out.println(patternsFile);
        Scanner fileReader = new Scanner(patternsFile);
        //Lire la première ligne qui contient le nombre de pattern dans le fichier
        line = fileReader.nextLine();
        numberOfPattern = Integer.parseInt(String.valueOf(line.charAt(0)));
        //Initialisé le tableau de patterns
        patterns = new RoomGenerator.Pattern[numberOfPattern];

        int level = 0;
        boolean hasHeal = false;

        //Tant que nous ne sommes pas à la fin du fichier
        while (fileReader.hasNextLine()) {
            line = fileReader.nextLine();
            //Si la ligne commence par 's' alors nous sommes au début d'un nouveau pattern. On initialise alors le tableau
            if(line.charAt(0) == 's'){
                pattern = new char[ROOM_SIZE/3][ROOM_SIZE/3];
                line = fileReader.nextLine();
                level = Integer.parseInt(String.valueOf(line.charAt(0)));
                hasHeal = line.charAt(2) == 't';
            }

            //Initialise chaque case
            if(line.charAt(0) == '.' || line.charAt(0) == 'X' || line.charAt(0) == 'M' || line.charAt(0) == 'T' || line.charAt(0) == 'H'){
                x = 0;
                for (int i = 0; i < line.length(); i++) {
                    pattern[y][x] = line.charAt(i);
                    x++;
                }
                y++;
            }

            //Si la ligne commence par 'e' alors nous sommes à la fin d'un pattern. On initialise alors l'objet Pattern
            if(line.charAt(0) == 'e'){
                patterns[indexPatternsArray] = new RoomGenerator.Pattern(pattern,level,hasHeal);
                indexPatternsArray++;
                y = 0;
            }
        }
        fileReader.close();
    }

    public ArrayList<SpecialTile> getSpecialTileList() {
        return specialTileList;
    }

    /**
     * Class pour garder en mémoire les différents patterns
     */
    private static final class Pattern{
        private final char[][] pattern;
        private final int level;
        private final boolean hasHeal;
        private Pattern(char[][] p, int level, boolean hasHeal){
            this.pattern = p;
            this.level = level;
            this.hasHeal = hasHeal;
        }
        public char[][] getPattern() {
            return pattern.clone();
        }
        public int getLevel() {
            return level;
        }
        public boolean hasHeal() {
            return hasHeal;
        }
        public String toString(){
            return "Level : "+level+" and hasHeal : "+hasHeal;
        }
    }
}



