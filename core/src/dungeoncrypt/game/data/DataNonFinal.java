package dungeoncrypt.game.data;

public class DataNonFinal {

    public final static DataNonFinal dnf = new DataNonFinal();

    //Constantes numériques pour les points de dégats des monstres
    private static int ratioDamagePoint = 0;
    private final static int DAMAGE_POINT_SKELETON = 10;
    private final static int DAMAGE_POINT_ZOMBIE = 5;
    private final static int DAMAGE_POINT_GHOST = 3;
    private final static int DAMAGE_POINT_BOSS = 20;
    private final static int DAMAGE_POINT_PROJECTILE_BOSS = 5;

    //Constantes pour la vitesse des monstres
    private static int ratioMoveSpeed = 0;
    private final static int MOVE_SPEED_ZOMBIE = 40;
    private final static int MOVE_SPEED_GHOST = 50;
    private final static int MOVE_SPEED_SKELETON = 40;
    private final static int MOVE_SPEED_BOSS = 80;

    //Constantes numériques pour les points de vie des monstres
    private static int ratioInitialHP = 0;
    private final static int SKELETON_INITIAL_HP = 20;
    private final static int ZOMBIE_INITIAL_HP = 15;
    private final static int GHOST_INITIAL_HP = 5;
    private final static int BOSS_INITIAL_HP = 50;

    private DataNonFinal(){

    }

    public static void increaseDifficulty(){
        ratioDamagePoint += 1;
        ratioMoveSpeed += 2;
        ratioInitialHP += 1;
    }

    //Damage point
    public static int damagePointSkeleton() {
        return DAMAGE_POINT_SKELETON + ratioDamagePoint;
    }
    public static int damagePointZombie() {
        return DAMAGE_POINT_ZOMBIE + ratioDamagePoint;
    }
    public static int damagePointGhost() {
        return DAMAGE_POINT_GHOST + ratioDamagePoint;
    }
    public static int damagePointBoss() {
        return DAMAGE_POINT_BOSS + ratioDamagePoint;
    }
    public static int damagePointProjectileBoss() {
        return DAMAGE_POINT_PROJECTILE_BOSS + ratioDamagePoint;
    }

    //Vitesse
    public static int moveSpeedZombie() {
        return MOVE_SPEED_ZOMBIE + ratioMoveSpeed;
    }
    public static int moveSpeedGhost() {
        return MOVE_SPEED_GHOST + ratioMoveSpeed;
    }
    public static int moveSpeedSkeleton() {
        return MOVE_SPEED_SKELETON + ratioMoveSpeed;
    }
    public static int moveSpeedBoss() {
        return MOVE_SPEED_BOSS + ratioMoveSpeed;
    }

    //Point de vie de base
    public static int initialHpSkeleton() {
        return SKELETON_INITIAL_HP + ratioInitialHP;
    }
    public static int initialHpZombie() {
        return ZOMBIE_INITIAL_HP + ratioInitialHP;
    }
    public static int initialHpGhost() {
        return GHOST_INITIAL_HP + ratioInitialHP;
    }
    public static int initialHpBoss() {
        return BOSS_INITIAL_HP + ratioInitialHP;
    }
}
