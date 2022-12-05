import dungeoncrypt.game.entities.Player;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


class PlayerTest {

    @Test
    private static void playerTakeDamage() {
        Player player = new Player();
        int shieldInitial = player.getShieldPoint();
        player.takeDamage(5);
        assertEquals(shieldInitial-5,player.getShieldPoint());
    }

    @Test
    private static void playerTakeDamageInHealthAndShield() {
        Player player = new Player();
        int shieldInitial = player.getShieldPoint();
        int hpInitial = player.getHealthPoint();
        player.takeDamage(50);
        assertEquals((shieldInitial+hpInitial)-50,player.getShieldPoint()+ player.getHealthPoint());
    }

    @Test
    private static void playerTakeShield() {
        Player player = new Player();
        int shieldInitial = player.getShieldPoint();
        player.takeDamage(10);
        player.addShieldPoint(5);
        assertEquals(shieldInitial-10+5,player.getShieldPoint());
    }

    public static void main(String[] args) {
        playerTakeDamage();
        playerTakeDamageInHealthAndShield();
        playerTakeShield();
    }
}