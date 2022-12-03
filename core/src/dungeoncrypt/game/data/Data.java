package dungeoncrypt.game.data;

public class Data {

    //Constantes pour afficher le mode debug ou non
    public final static boolean DEBUG_MODE = false;

    //Taille en longueur et en largeur d'un pattern, celui-ci doit être impaire pour pouvoir centrer des éléments.
    public static final int PATTERN_SIZE = 7;

    //Taille en longueur et en largeur d'une salle, celle-ci doit être impaire pour centré la sortie et la position initiale du joueur.
    //La salle est répartit en 9 sous-zones qui doivent avoir comme taille un nombre impair.
    //Ici 3 fois la taille d'un pattern plus 2 (les murs qui entourent la salle)
    public static final int ROOM_SIZE = (PATTERN_SIZE*3)+2;

    //Echelle
    public static final int RENDER_SCALE = 32;
    public static final int RENDER_SCALE_SPRITE = RENDER_SCALE/2;
    public static final int RENDER_SCALE_ENTITY = RENDER_SCALE*3/4;
    public static final int RENDER_SCALE_BOSS = RENDER_SCALE*10;

    //Taille total de la salle
    public static final int PIXEL_ROOM_SIZE = RENDER_SCALE*ROOM_SIZE;

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
    public static final String SWORD_TYPE = "Sword";

    //String pour définir le type spécial d'une tuile. Ce type permet de savoir sur quoi intéragit la tuile (Santé, Armure, Niveau, etc)
    //Par exemple les tuiles HealingTile et Trap intéragissent avec la santé.
    public static final String HEALTH_SPECIAL_TYPE = "Health";
    //Savoir si la sortie va rediriger le joueur vers une nouvelle salle ou un nouveau niveau
    public static final String EXIT_SPECIAL_TYPE_NEW_ROOM = "NewRoom";
    public static final String EXIT_SPECIAL_TYPE_NEW_LEVEL = "NewLevel";

    //String pour définir le type d'une entitée : joueur / monstre
    public static final String PLAYER_TYPE = "Player";
    public static final String MONSTER_TYPE = "Monster";

    //String pour définir le type spécial d'un joueur. Identique à PLAYER_TYPE pour le moment mais peut-être utile dans le futur
    public static final String PLAYER_SPECIAL_TYPE = "Player";

    //String pour définir le type spécial d'un monstre
    public static final String SKELETON_TYPE = "Skeleton";
    public static final String ZOMBIE_TYPE = "Zombie";
    public static final String GHOST_TYPE = "Ghost";
    public static final String BOSS_TYPE = "Boss";

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
    public static final int ZOMBIE_INITIAL_HP = 15;
    public static final int GHOST_INITIAL_HP = 5;
    public static final int BOSS_INITIAL_HP = 50;

    //Constantes numériques pour les points des monstres
    public static final int SKELETON_SCORE = 10;
    public static final int ZOMBIE_SCORE = 20;
    public static final int GHOST_SCORE = 5;
    public static final int BOSS_SCORE = 100;

    //Constantes numériques pour les points de dégats des monstres
    public static final int DAMAGE_POINT_SKELETON = 10;
    public static final int DAMAGE_POINT_ZOMBIE = 5;
    public static final int DAMAGE_POINT_GHOST = 3;
    public static final int DAMAGE_POINT_BOSS = 20;

    //Constantes numériques pour les tuiles spéciales
    //Nombre de point de vie que donne la tuile HealingTile
    public static final int HP_HEALING_TILE = 10;
    //Nombre de point de vie que fait perdre la tuile Trap
    public static final int HP_TRAP = 5;

    //Constantes numériques pour les Armes
    //Nombre de point de vie que donne l'arme Sword
    public static final int HP_SWORD = 5;

    //Constantes numériques du nombre maximum d'utilisation de l'effet d'une tuile
    //Définir à -1 pour une utilisation illimité
    public static final int MAX_USE_HEALING_TILE = 1;
    public static final int MAX_USE_TRAP = -1;
    public static final int MAX_USE_EXIT = -1;

    //Constantes pour les changements screens
    public final static int MENU = 0;
    public final static int PREFERENCES = 1;
    public final static int APPLICATION = 2;
    public final static int ENDGAME = 3;
    public final static int LOAD_APPLICATION = 4;
    public final static int CONTINUE_LOAD_APPLICATION = 5;
    //Constantes pour la taille des boutons du menu
    public final static int PADDING_TOP = 10;
    public final static int PADDING_LEFT = 200;
    public final static int PADDING_RIGHT = 200;
    public final static int PADDING_DOWN = 10;
    public final static int BUTTON_MINWIDTH = 150;
    public final static int BUTTON_MAXWIDTH = 350;

    //Constantes pour la vitesse du joueur
    public final static int MOVE_SPEED_PLAYER = 100;

    //Constantes pour la vitesse des monstres
    public final static int MOVE_SPEED_ZOMBIE = 40;
    public final static int MOVE_SPEED_GHOST = 50;
    public final static int MOVE_SPEED_SKELETON = 40;
    public final static int MOVE_SPEED_BOSS = 100;

    //Coordonnées de la barre de vie
    public final static int HEALTH_BAR_X = 20;
    public final static int EMPTY_HEALTH_BAR_Y = PIXEL_ROOM_SIZE-25;
    public final static int HEALTH_BAR_Y = EMPTY_HEALTH_BAR_Y+4;
    public final static int MAX_SIZE_HEALTH_BAR = 250;
    public final static int EMPTY_HEALTH_BAR_HEIGHT = 20;
    public final static int HEALTH_BAR_HEIGHT = EMPTY_HEALTH_BAR_HEIGHT-4;
    public final static int LOGO_HEALTH_BAR_WIDTH = 16;

    //Coordonnées de l'affichage du score
    public final static int SCORE_X = 550;
    public final static int SCORE_Y = 735;
    public final static int FONT_SIZE = 20;

    //Nom des sauvegardes
    public final static String AUTO_SAVE_NAME ="DungeonCrypt-AutoSave";
    public final static String SAVE_NAME ="DungeonCrypt-Save";
    public final static String DEBUG_SAVE_NAME ="DungeonCrypt-DebugSave";

    //Durée de l'effet de knockback
    public final static int MAX_TIMER_KNOCKBACK = 20;

}

