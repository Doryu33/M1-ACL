package dungeoncrypt.game.data;

public class Data {

    //Taille en longueur et en largeur d'un pattern, celui-ci doit être impaire pour pouvoir centrer des éléments.
    public static final int PATTERN_SIZE = 7;

    //Taille en longueur et en largeur d'une salle, celle-ci doit être impaire pour centré la sortie et la position initiale du joueur.
    //La salle est répartit en 9 sous-zones qui doivent avoir comme taille un nombre impair.
    //Ici 3 fois la taille d'un pattern plus 2 (les murs qui entourent la salle)
    public static final int ROOM_SIZE = (PATTERN_SIZE*3)+2;

    //Position en X et en Y de la sortie.
    public static final int EXIT_POS_X = ROOM_SIZE/2;
    public static final int EXIT_POS_Y = 0;

    //Position initial en X et en Y du joueur en fonction de la taille de la salle
    public static final int INITIAL_PLAYER_POS_X = ROOM_SIZE/2;
    public static final int INITIAL_PLAYER_POS_Y = ROOM_SIZE-3;

    //String pour définir le type d'une tuile : sol / mur / sortie
    public static final String FLOOR_TYPE = "Floor";
    public static final String WALL_TYPE = "Wall";
    public static final String EXIT_TYPE = "Exit";

    //String pour définir le type d'une entitée : joueur / monstre
    public static final String PLAYER_TYPE = "Player";
    public static final String MONSTER_TYPE = "Monster";

    //Caractère pour définir l'affichage de chaque élément
    public static final char FLOOR_TILE = ' ';
    public static final char WALL_TILE = 'X';
    public static final char EXIT_TILE = 'S';
    public static final char PLAYER_TILE = 'P';
    public static final char MONSTER_TILE = 'M';

    //String pour définir la direction du déplacement du joueur
    public static final String PLAYER_TOP_KEY = "Z";
    public static final String PLAYER_TOP_KEY_MIN = "z";
    public static final String PLAYER_BOTTOM_KEY = "S";
    public static final String PLAYER_BOTTOM_KEY_MIN = "s";
    public static final String PLAYER_LEFT_KEY = "Q";
    public static final String PLAYER_LEFT_KEY_MIN = "q";
    public static final String PLAYER_RIGHT_KEY = "D";
    public static final String PLAYER_RIGHT_KEY_MIN = "d";

    //Echelle
    public static final int RENDER_SCALE = 32;
}

