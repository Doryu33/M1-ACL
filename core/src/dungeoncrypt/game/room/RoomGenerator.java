package dungeoncrypt.game.room;

import dungeoncrypt.game.entities.monsters.Ghost;
import dungeoncrypt.game.entities.monsters.Monster;
import dungeoncrypt.game.entities.monsters.Skeleton;
import dungeoncrypt.game.entities.monsters.Zombie;
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

        int numberOfGenerations = 7;
        int generationChoice = randomNumber.nextInt(numberOfGenerations);

        switch (generationChoice){
            case 0:
                labyrinthGeneration();
                break;
            case 1:
                crossGeneration();
                break;
            case 2:
                fullPathGeneration();
                break;
            case 3:
                hGeneration();
                break;
            case 4:
                yGeneration();
                break;
            case 5:
                i9generation();
                break;
            case 6:
                tGeneration();
                break;
        }
        return tilesRandomRoom;
    }

    /*
     * Représentation de la délimitation d'une salle
     * |-----------------|
     * |  7  |  8  |  9  |
     * |-----|-----|-----|
     * |  4  |  5  |  6  |
     * |-----|-----|-----|
     * |  1  |  2  |  3  |
     * |-----------------|
     */

    /**
     * Applique le pattern pour le coin haut gauche. Chiffre 7 du schéma
     * @param pattern à appliquer
     */
    private void patternLeftUpCorner7(char[][] pattern){
        applyPatternTo(1,1,ROOM_SIZE/3,ROOM_SIZE/3,pattern);
    }

    /**
     * Applique le pattern pour la case haut centré. Chiffre 8 du schéma
     * @param pattern à appliquer
     */
    private void patternMiddleUp8(char[][] pattern){
        applyPatternTo(ROOM_SIZE/3+1,1,(ROOM_SIZE/3)*2,ROOM_SIZE/3,pattern);
    }

    /**
     * Applique le pattern pour le coin haut droit. Chiffre 9 du schéma
     * @param pattern à appliquer
     */
    private void patternRightUpCorner9(char[][] pattern){
        applyPatternTo((ROOM_SIZE/3*2)+1,1,(ROOM_SIZE/3)*3,ROOM_SIZE/3,pattern);
    }

    /**
     * Applique le pattern pour la case gauche au milieu. Chiffre 4 du schéma
     * @param pattern à appliquer
     */
    private void patternLeftCenter4(char[][] pattern){
        applyPatternTo(1,(ROOM_SIZE/3)+1,ROOM_SIZE/3,(ROOM_SIZE/3)*2,pattern);
    }

    /**
     * Applique le pattern pour la case au centre. Chiffre 5 du schéma
     * @param pattern à appliquer
     */
    private void patternMiddleCenter5(char[][] pattern) { applyPatternTo(ROOM_SIZE/3+1,(ROOM_SIZE/3)+1,(ROOM_SIZE/3)*2,(ROOM_SIZE/3)*2,pattern);}

    /**
     * Applique le pattern pour la case droite au milieu. Chiffre 6 du schéma
     * @param pattern à appliquer
     */
    private void patternRightCenter6(char[][] pattern) { applyPatternTo((ROOM_SIZE/3*2)+1,(ROOM_SIZE/3)+1,(ROOM_SIZE/3)*3,(ROOM_SIZE/3)*2,pattern);}

    /**
     * Applique le pattern pour le coin bas gauche. Chiffre 1 du schéma
     * @param pattern à appliquer
     */
    private void patternLeftDownCorner1(char[][] pattern){
        applyPatternTo(1,(ROOM_SIZE/3)*2+1,ROOM_SIZE/3,(ROOM_SIZE/3)*3,pattern);
    }

    /**
     * Applique le pattern pour le coin bas centré. Chiffre 2 du schéma
     * @param pattern à appliquer
     */
    private void patternMiddleDown2(char[][] pattern){
        applyPatternTo(ROOM_SIZE/3+1,(ROOM_SIZE/3)*2+1,(ROOM_SIZE/3)*2,(ROOM_SIZE/3)*3,pattern);
    }

    /**
     * Applique le pattern pour le coin bas droit. Chiffre 3 du schéma
     * @param pattern à appliquer
     */
    private void patternRightDownCorner3(char[][] pattern){
        applyPatternTo((ROOM_SIZE/3*2)+1,(ROOM_SIZE/3)*2+1,(ROOM_SIZE/3)*3,(ROOM_SIZE/3)*3,pattern);
    }

    /**
     * Génération en forme de X
     */
    private void crossGeneration(){
        int variant = randomNumber.nextInt(2);
        if(variant == 0){
            patternRightCenter6(getRandomPatternWithAccess(1,true,true,false,true).getPattern());
        }
        patternLeftUpCorner7(getRandomPatternWithAccess(0,false,true,true,false).getPattern());
        patternRightUpCorner9(getRandomPatternWithAccess(0,false,true,false,true).getPattern());
        patternMiddleCenter5(getRandomPatternWithAccess(0,true,true,true,true).getPattern());
        patternLeftDownCorner1(getRandomPatternWithAccess(0,true,false,true,false).getPattern());
        patternRightDownCorner3(getRandomPatternWithAccess(0,true,false,false,true).getPattern());
    }

    /**
     * Génération sous forme d'un T penché sur le côté
     */
    private void tGeneration(){
        int variant = randomNumber.nextInt(2);
        if(variant == 0){
            patternRightDownCorner3(getRandomPatternWithAccess(0,true,false,false,true).getPattern());
            patternRightCenter6(getRandomPatternWithAccess(0,true,true,false,true).getPattern());
            patternRightUpCorner9(getRandomPatternWithAccess(0,false,true,false,true).getPattern());
            patternLeftCenter4(getRandomPatternWithAccess(2,false,false,true,false).getPattern());
        }else{
            patternLeftDownCorner1(getRandomPatternWithAccess(0,true,false,true,false).getPattern());
            patternLeftCenter4(getRandomPatternWithAccess(0,true,true,true,false).getPattern());
            patternLeftUpCorner7(getRandomPatternWithAccess(0,false,true,true,false).getPattern());
            patternRightCenter6(getRandomPatternWithAccess(2,false,false,false,true).getPattern());
        }
        patternMiddleCenter5(getRandomPatternWithAccess(0,false,false,true,true).getPattern());
    }

    /**
     * Génération sous forme de I9
     */
    private void i9generation(){
        int variant = randomNumber.nextInt(2);
        if (variant == 0){
            patternMiddleCenter5(getRandomPatternWithAccess(0,true,true,true,false).getPattern());
            patternRightDownCorner3(getRandomPatternWithAccess(0,true,false,false,false).getPattern());
        }else{
            patternMiddleCenter5(getRandomPatternWithAccess(0,true,false,true,false).getPattern());
            patternRightDownCorner3(getRandomPatternWithAccess(0,true,false,false,true).getPattern());
        }
        patternMiddleUp8(getRandomPatternWithAccess(0,true,true,true,true).getPattern());
        patternRightUpCorner9(getRandomPatternWithAccess(0,false,true,false,true).getPattern());
        patternRightCenter6(getRandomPatternWithAccess(0,true,true,false,true).getPattern());
        patternLeftUpCorner7(getRandomPatternWithAccess(0,false,true,true,false).getPattern());
        patternLeftCenter4(getRandomPatternWithAccess(0,true,true,false,false).getPattern());
        patternLeftDownCorner1(getRandomPatternWithAccess(variant+1,true,false,false,false).getPattern());
    }

    /**
     * Génération sous forme d'un Y inversé
     */
    private void yGeneration(){
        int variant = randomNumber.nextInt(3);
        if(variant == 0){
            patternRightCenter6(getRandomPatternWithAccess(0,false,true,false,true).getPattern());
            patternLeftCenter4(getRandomPatternWithAccess(0,false,true,true,false).getPattern());
        }else if(variant == 1){
            patternRightCenter6(getRandomPatternWithAccess(0,true,true,false,true).getPattern());
            patternRightUpCorner9(getRandomPatternWithAccess(1,false,true,false,false).getPattern());
            patternLeftCenter4(getRandomPatternWithAccess(0,false,true,true,false).getPattern());
        }else{
            Pattern randomPatternWithAccess = getRandomPatternWithAccess(0,false,true,true,false);
            patternLeftCenter4(randomPatternWithAccess.getPattern());
            randomPatternWithAccess = invertPatternVertically(randomPatternWithAccess);
            patternRightCenter6(randomPatternWithAccess.getPattern());
        }
        patternMiddleCenter5(getRandomPatternWithAccess(0,true,true,true,true).getPattern());
        patternMiddleUp8(getRandomPatternWithAccess(0,true,true,false,false).getPattern());
        patternRightDownCorner3(getRandomPatternWithAccess(0,true,false,false,false).getPattern());
        patternLeftDownCorner1(getRandomPatternWithAccess(0,true,false,false,false).getPattern());
    }

    /**
     * Génération avec tous les chemins connecté entre les patterns
     */
    private void fullPathGeneration(){
        int variant = randomNumber.nextInt(3);
        if(variant == 0){
            //Entrée par 5
            patternLeftDownCorner1(getRandomPatternWithAccess(0,true,false,false,false).getPattern());
            patternRightDownCorner3(getRandomPatternWithAccess(2,true,false,false,false).getPattern());
            patternMiddleCenter5(getRandomPatternWithAccess(0,true,true,true,true).getPattern());
        }else if(variant == 1){
            //Entrée par 3
            patternLeftDownCorner1(getRandomPatternWithAccess(1,true,false,false,false).getPattern());
            patternRightDownCorner3(getRandomPatternWithAccess(0,true,false,false,true).getPattern());
            patternMiddleCenter5(getRandomPatternWithAccess(0,true,false,true,true).getPattern());
        }else{
            //Entrée par 1
            patternLeftDownCorner1(getRandomPatternWithAccess(0,true,false,true,false).getPattern());
            patternRightDownCorner3(getRandomPatternWithAccess(1,true,false,false,false).getPattern());
            patternMiddleCenter5(getRandomPatternWithAccess(0,true,false,true,true).getPattern());
        }
        patternLeftCenter4(getRandomPatternWithAccess(0,true,true,true,false).getPattern());
        patternRightCenter6(getRandomPatternWithAccess(0,true,true,false,true).getPattern());
        patternLeftUpCorner7(getRandomPatternWithAccess(0,false,true,true,false).getPattern());
        patternMiddleUp8(getRandomPatternWithAccess(0,true,true,true,true).getPattern());
        patternRightUpCorner9(getRandomPatternWithAccess(0,false,true,false,true).getPattern());

    }

    /**
     * Génération sous forme d'un petit labyrinth
     */
    private void labyrinthGeneration(){
        int variant = randomNumber.nextInt(2);
        if(variant == 0){
            patternLeftDownCorner1(getRandomPatternWithAccess(0,true,false,true,false).getPattern());
            patternRightDownCorner3(getRandomPatternWithAccess(1,true,false,false,false).getPattern());
            patternLeftUpCorner7(getRandomPatternWithAccess(0,false,true,false,false).getPattern());
            patternRightUpCorner9(getRandomPatternWithAccess(0,false,true,false,true).getPattern());
            patternMiddleUp8(getRandomPatternWithAccess(0,true,false,true,false).getPattern());
        }else{
            patternLeftDownCorner1(getRandomPatternWithAccess(1,true,false,false,false).getPattern());
            patternRightDownCorner3(getRandomPatternWithAccess(0,true,false,false,true).getPattern());
            patternLeftUpCorner7(getRandomPatternWithAccess(0,false,true,true,false).getPattern());
            patternRightUpCorner9(getRandomPatternWithAccess(0,false,true,false,false).getPattern());
            patternMiddleUp8(getRandomPatternWithAccess(0,true,false,false,true).getPattern());
        }
        patternLeftCenter4(getRandomPatternWithAccess(0,true,true,true,false).getPattern());
        patternMiddleCenter5(getRandomPatternWithAccess(0,false,false,true,true).getPattern());
        patternRightCenter6(getRandomPatternWithAccess(0,true,true,false,true).getPattern());
    }

    /**
     * Génération sous forme d'un H
     */
    private void hGeneration(){
        int variant = randomNumber.nextInt(2);
        if (variant == 0){
            Pattern randomPatternWithAccess = getRandomPatternWithAccess(0, true, true, true, false);
            patternLeftCenter4(randomPatternWithAccess.getPattern());
            randomPatternWithAccess = invertPatternVertically(randomPatternWithAccess);
            patternRightCenter6(randomPatternWithAccess.getPattern());
        }else{
            patternLeftCenter4(getRandomPatternWithAccess(0,true,true,true,false).getPattern());
            patternRightCenter6(getRandomPatternWithAccess(0,true,true,false,true).getPattern());
        }
        patternMiddleCenter5(getRandomPatternWithAccess(0,true,true,true,true).getPattern());
        patternLeftDownCorner1(getRandomPatternWithAccess(0,true,false,false,false).getPattern());
        patternLeftUpCorner7(getRandomPatternWithAccess(2,false,true,false,false).getPattern());
        patternRightDownCorner3(getRandomPatternWithAccess(2,true,false,false,false).getPattern());
        patternRightUpCorner9(getRandomPatternWithAccess(0,false,true,false,false).getPattern());
    }

    /**
     * Fonction qui permet de récupérer un pattern aléatoire qui correspond aux accès demandés
     * @param withHeal 0 pour un pattern sans tuile de soin, 1 pour un pattern avec une tuile de soin, et 2 pour une tuile complétement aléatoire
     * @param northAccess souhaité
     * @param southAccess souhaité
     * @param eastAccess souhaité
     * @param westAccess souhaité
     * @return le pattern correspondant
     */
    private Pattern getRandomPatternWithAccess(int withHeal, boolean northAccess, boolean southAccess, boolean eastAccess, boolean westAccess){
        Pattern pattern;
        if(withHeal == 0){
            pattern = getRandomPatternWithoutHeal();
        }else if(withHeal == 1){
            pattern = getRandomHealPattern();
        }else{
            pattern = getRandomPattern();
        }

        Pattern possiblePattern;
        while(pattern.hasNorthAccess() != northAccess || pattern.hasSouthAccess() != southAccess
                || pattern.hasEastAccess() != eastAccess || pattern.hasWestAccess() != westAccess){

            possiblePattern = canThisPatternBe(pattern,northAccess,southAccess, eastAccess, westAccess);
            if(possiblePattern != null){
                return possiblePattern;
            }

            if(withHeal == 0){
                pattern = getRandomPatternWithoutHeal();
            }else if(withHeal == 1){
                pattern = getRandomHealPattern();
            }else{
                pattern = getRandomPattern();
            }
        }
        return pattern;
    }

    /**
     * Savoir si le pattern passé en paramètre peut correspondre aux demandes. Càd, savoir si les accès du pattern peuvent correspondre avec ceux passés en paramètre
     * tout en inversant horizontallement et/ou verticalement le pattern.
     * @param pattern à analyser
     * @param northAccess souhaité
     * @param southAccess souhaité
     * @param eastAccess souhaité
     * @param westAccess souhaité
     * @return une copie inversée du pattern passé en paramètre si c'est possible. null sinon.
     */
    private Pattern canThisPatternBe(Pattern pattern, boolean northAccess, boolean southAccess, boolean eastAccess, boolean westAccess){
        pattern = invertPatternHorizontally(pattern);
        if(isPatternMatchesTo(pattern,northAccess,southAccess,eastAccess,westAccess)){
            return pattern;
        }

        pattern = invertPatternVertically(pattern);
        if(isPatternMatchesTo(pattern,northAccess,southAccess,eastAccess,westAccess)){
            return pattern;
        }

        pattern = invertPatternHorizontally(pattern);
        if(isPatternMatchesTo(pattern,northAccess,southAccess,eastAccess,westAccess)){
            return pattern;
        }

        for (int i = 0; i < 3; i++) {
            pattern = rotatePatternClockwise(pattern);
            if(isPatternMatchesTo(pattern,northAccess,southAccess,eastAccess,westAccess)){
                return pattern;
            }
        }
        return null;
    }
    //Fonction simple pour savoir si les champs du pattern correspondent avec ceux en paramètres
    private boolean isPatternMatchesTo(Pattern pattern, boolean northAccess, boolean southAccess, boolean eastAccess, boolean westAccess){
        return pattern.hasNorthAccess() == northAccess && pattern.hasSouthAccess() == southAccess
                && pattern.hasEastAccess() == eastAccess && pattern.hasWestAccess() == westAccess;
    }

    /**
     * Séléctionner un pattern qui possède une/plusieurs tuiles de soin
     * @return un pattern qui possède une tuile de soin
     */
    private Pattern getRandomHealPattern(){
        int patternChoice = randomNumber.nextInt(numberOfPattern);
        while(!patterns[patternChoice].hasHeal){
            patternChoice = randomNumber.nextInt(numberOfPattern);
        }
        return getPattern(patternChoice);
    }

    /**
     * Séléctionner un pattern aléatoire mais qui ne possède de tuiles de soin
     * @return un pattern aléatoire sans tuile de soin
     */
    private Pattern getRandomPatternWithoutHeal(){
        int patternChoice = randomNumber.nextInt(numberOfPattern);
        while(patterns[patternChoice].hasHeal){
            patternChoice = randomNumber.nextInt(numberOfPattern);
        }
        return getPattern(patternChoice);
    }

    /**
     * Séléctionner un pattern aléatoire
     * @return un pattern aléatoire
     */
    private Pattern getRandomPattern(){
        int patternChoice = randomNumber.nextInt(numberOfPattern);
        return getPattern(patternChoice);
    }

    /**
     * Récupérer une copie du pattern
     * @param index de la liste de tous les patterns
     * @return une copie du pattern
     */
    private Pattern getPattern(int index){
        return new Pattern(patterns[index]);
    }

    /**
     * Inverse horizontallement le pattern en parametre en créant une copie
     * @param pattern à inverser
     * @return une copie du pattern avec la transformation
     */
    private Pattern invertPatternHorizontally(Pattern pattern){
        char[][] newArrayPattern = new char[PATTERN_SIZE][PATTERN_SIZE];
        char[][] arrayPattern = pattern.getPattern();
        int yNewPattern = PATTERN_SIZE-1;
        for (int y = 0; y < PATTERN_SIZE; y++) {
            System.arraycopy(arrayPattern[y], 0, newArrayPattern[yNewPattern], 0, PATTERN_SIZE);
            yNewPattern--;
        }
        Pattern p = new Pattern(pattern,newArrayPattern);
        p.flipHorizontally();
        return p;
    }

    /**
     * Inverse verticalement le pattern en parametre en créant une copie
     * @param pattern à inverser
     * @return une copie du pattern avec la transformation
     */
    private Pattern invertPatternVertically(Pattern pattern){
        char[][] newArrayPattern = new char[PATTERN_SIZE][PATTERN_SIZE];
        char[][] arrayPattern = pattern.getPattern();
        int xNewPattern;
        for (int y = 0; y < PATTERN_SIZE; y++) {
            xNewPattern = PATTERN_SIZE-1;
            for (int x = 0; x < PATTERN_SIZE; x++) {
                newArrayPattern[y][xNewPattern] = arrayPattern[y][x];
                xNewPattern--;
            }
        }
        Pattern p = new Pattern(pattern,newArrayPattern);
        p.flipVertically();
        return p;
    }

    /**
     * Inverse dans le sens horaire le pattern en parametre en créant une copie
     * @param pattern qui doit subir une rotation
     * @return une copie du pattern avec la transformation
     */
    private Pattern rotatePatternClockwise(Pattern pattern){
        char[][] p = pattern.getPattern();
        char[][] newArrayPattern = new char[PATTERN_SIZE][PATTERN_SIZE];
        for (int r = 0; r < PATTERN_SIZE; r++) {
            for (int c = 0; c < PATTERN_SIZE; c++) {
                newArrayPattern[c][PATTERN_SIZE-1-r] = p[r][c];
            }
        }
        Pattern pat = new Pattern(pattern,newArrayPattern);
        pat.rotateClockwise();
        return pat;
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
                        int choice = randomNumber.nextInt(3);
                        switch (choice){
                            case 0:
                                monsters.add(new Skeleton(x,y));
                                break;
                            case 1:
                                monsters.add(new Zombie(x,y));
                                break;
                            case 2:
                                monsters.add(new Ghost(x,y));
                                break;
                        }
                        break;
                    case 'S':
                        monsters.add(new Skeleton(x,y));
                        break;
                    case 'Z':
                        monsters.add(new Zombie(x,y));
                        break;
                    case 'G':
                        monsters.add(new Ghost(x,y));
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
        Scanner fileReader = new Scanner(patternsFile);
        //Lire la première ligne qui contient le nombre de pattern dans le fichier
        line = fileReader.nextLine();
        int index = 0;
        StringBuilder numberP = new StringBuilder();
        while(line.charAt(index) != ' '){
            numberP.append(line.charAt(index));
            index++;
        }
        numberOfPattern = Integer.parseInt(numberP.toString());
        //Initialisé le tableau de patterns
        patterns = new RoomGenerator.Pattern[numberOfPattern];

        int level = 0;
        boolean hasHeal = false;
        boolean northAccess,southAccess,westAccess,eastAccess;
        eastAccess = false;
        westAccess = false;
        northAccess = false;
        southAccess = false;

        //Tant que nous ne sommes pas à la fin du fichier
        while (fileReader.hasNextLine()) {
            line = fileReader.nextLine();
            //Si la ligne commence par 's' alors nous sommes au début d'un nouveau pattern. On initialise alors le tableau
            if(line.charAt(0) == 's'){
                pattern = new char[ROOM_SIZE/3][ROOM_SIZE/3];
                line = fileReader.nextLine();
                level = Integer.parseInt(String.valueOf(line.charAt(0)));
                hasHeal = line.charAt(2) == 't';

                eastAccess = false;
                westAccess = false;
                northAccess = false;
                southAccess = false;
                line = fileReader.nextLine();
                for (int i = 0; i < line.length(); i++) {
                    switch (line.charAt(i)){
                        case 'N':
                            northAccess = true;
                            break;
                        case 'S':
                            southAccess = true;
                            break;
                        case 'E':
                            eastAccess = true;
                            break;
                        case 'W':
                            westAccess = true;
                            break;
                    }
                }
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
                patterns[indexPatternsArray] = new RoomGenerator.Pattern(pattern,level,hasHeal,northAccess,southAccess,eastAccess,westAccess);
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
        private char[][] pattern;
        private final int level;
        private final boolean hasHeal;
        private boolean northAccess;
        private boolean southAccess;
        private boolean eastAccess;
        private boolean westAccess;

        private Pattern(char[][] p, int level, boolean hasHeal, boolean northAccess, boolean southAccess, boolean eastAccess, boolean westAccess){
            this.pattern = p;
            this.level = level;
            this.hasHeal = hasHeal;
            this.northAccess = northAccess;
            this.southAccess = southAccess;
            this.eastAccess = eastAccess;
            this.westAccess = westAccess;
        }

        private Pattern(Pattern pattern){
            this.pattern = pattern.copyPattern();
            this.level = pattern.getLevel();
            this.hasHeal = pattern.hasHeal;
            this.northAccess = pattern.hasNorthAccess();
            this.southAccess = pattern.hasSouthAccess();
            this.eastAccess = pattern.hasEastAccess();
            this.westAccess = pattern.hasWestAccess();
        }

        private Pattern(Pattern pattern,char[][] p){
            this.pattern = p;
            this.level = pattern.getLevel();
            this.hasHeal = pattern.hasHeal;
            this.northAccess = pattern.hasNorthAccess();
            this.southAccess = pattern.hasSouthAccess();
            this.eastAccess = pattern.hasEastAccess();
            this.westAccess = pattern.hasWestAccess();
        }

        public char[][] getPattern() {
            return pattern;
        }
        public char[][] copyPattern() {
            char[][] p = new char[PATTERN_SIZE][PATTERN_SIZE];
            for (int y = 0; y < PATTERN_SIZE; y++) {
                System.arraycopy(pattern[y], 0, p[y], 0, PATTERN_SIZE);
            }
            return p;
        }
        public int getLevel() {
            return level;
        }
        public boolean hasHeal() {
            return hasHeal;
        }
        public String toString(){
            return "Level : "+level+" and hasHeal : "+hasHeal+
                    " northAccess : "+northAccess+
                    " southAccess : "+southAccess+
                    " eastAccess : "+eastAccess+
                    " westAccess : "+westAccess;
        }
        public boolean hasNorthAccess() {
            return northAccess;
        }
        public boolean hasSouthAccess() {
            return southAccess;
        }
        public boolean hasEastAccess() {
            return eastAccess;
        }
        public boolean hasWestAccess() {
            return westAccess;
        }
        public void flipVertically(){
            boolean tmp = eastAccess;
            eastAccess = westAccess;
            westAccess = tmp;
        }
        public void flipHorizontally(){
            boolean tmp = northAccess;
            northAccess = southAccess;
            southAccess = tmp;
        }
        public void rotateClockwise() {
            boolean tmpEast = eastAccess;
            boolean tmpWest = westAccess;
            boolean tmpNorth = northAccess;
            boolean tmpSouth = southAccess;
            eastAccess = tmpNorth;
            southAccess = tmpEast;
            westAccess = tmpSouth;
            northAccess = tmpWest;
        }
    }
}



