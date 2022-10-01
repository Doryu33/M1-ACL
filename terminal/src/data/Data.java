package data;

public class Data {

    //Taille en longueur et en largeur d'une salle, celle-ci doit être impaire pour centré la sortie et la position initiale du joueur.
    //La salle est répartit en 9 sous-zones qui doivent avoir comme taille un nombre impair.
    //Ici 23 - 2 (les murs qui entourent la salle) = 21. 21 / 3 = 7 (dimension d'une sous-zone)
    public static final int ROOM_SIZE = 23;

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
}
