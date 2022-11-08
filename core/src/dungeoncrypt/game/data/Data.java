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
    public static final String HEALING_TILE_TYPE = "HealingTile";
    public static final String TRAP_TYPE = "Trap";

    //String pour définir le type spécial d'une tuile. Ce type permet de savoir sur quoi intéragit la tuile (Santé, Armure, Niveau, etc)
    //Par exemple les tuiles HealingTile et Trap intéragissent avec la santé.
    public static final String HEALTH_SPECIAL_TYPE = "Health";
    //Savoir si la sortie va rediriger le joueur vers une nouvelle salle ou un nouveau niveau
    public static final String EXIT_SPECIAL_TYPE_NEW_ROOM = "NewRoom";
    public static final String EXIT_SPECIAL_TYPE_NEW_LEVEL = "NewLevel";

    //String pour définir le type d'une entitée : joueur / monstre
    public static final String PLAYER_TYPE = "Player";
    public static final String MONSTER_TYPE = "Monster";

    //String pour définir le type spécial d'un monstre
    public static final String SKELETON_TYPE = "Skeleton";
    public static final String ZOMBIE_TYPE = "Zombie";
    public static final String GHOST_TYPE = "Ghost";

    //Caractère pour définir l'affichage de chaque élément
    public static final char FLOOR_TILE = ' ';
    public static final char WALL_TILE = 'X';
    public static final char EXIT_TILE = 'E';
    public static final char PLAYER_TILE = 'P';
    public static final char MONSTER_TILE = 'M'; //monstre quelconque
    public static final char ZOMBIE_TILE = 'Z';
    public static final char SKELETON_TILE = 'S';
    public static final char GHOST_TILE = 'G';
    public static final char HEALING_TILE = 'H';
    public static final char TRAP_TILE = 'T';

    //Constantes numériques pour les statistiques du joueur
    public static final int PLAYER_INITIAL_HP = 50;

    //Constantes numériques pour les statistiques des monstres
    public static final int SKELETON_INITIAL_HP = 20;
    public static final int ZOMBIE_INITIAL_HP = 20;
    public static final int GHOST_INITIAL_HP = 20;

    //Constantes numériques pour les tuiles spéciales
    //Nombre de point de vie que donne la tuile HealingTile
    public static final int HP_HEALING_TILE = 10;
    //Nombre de point de vie que fait perdre la tuile Trap
    public static final int HP_TRAP = 5;

    //Constantes numériques du nombre maximum d'utilisation de l'effet d'une tuile
    //Définir à -1 pour une utilisation illimité
    public static final int MAX_USE_HEALING_TILE = 1;
    public static final int MAX_USE_TRAP = -1;
    public static final int MAX_USE_EXIT = 1;

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

    //Constantes pour les changements screens
    public final static int MENU = 0;
    public final static int PREFERENCES = 1;
    public final static int APPLICATION = 2;
    public final static int ENDGAME = 3;
    //Constantes pour la taille des boutons du menu
    public final static int PADDING_TOP = 10;
    public final static int PADDING_LEFT = 200;
    public final static int PADDING_RIGHT = 200;
    public final static int PADDING_DOWN = 10;
    public final static int BUTTON_MINWIDTH = 150;
    public final static int BUTTON_MAXWIDTH = 350;

    //Constantes pour afficher le mode debug ou non
    public final static boolean DEBUG_MODE = false;

    //Constantes pour la vitesse du joueur
    public final static int MOVE_SPEED = 100;

    //Coordonnées de la barre de vie
    public final static int HEALTH_BAR_X = 0;
    public final static int HEALTH_BAR_Y = 435;

    //Coordonnées de l'affichage du score
    public final static int SCORE_X = 550;
    public final static int SCORE_Y = 735;
    public final static int FONT_SIZE = 20;

}

