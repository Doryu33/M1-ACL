package dungeoncrypt.game.entities;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    @Test
    public void takeDamage() {
        Player player = new Player();
        int shieldInitial = player.getShieldPoint();
        player.takeDamage(5);
        assertEquals(shieldInitial-5,player.getShieldPoint());
    }

    @Test
    public void playerTakeDamageInHealthAndShield() {
        Player player = new Player();
        int shieldInitial = player.getShieldPoint();
        int hpInitial = player.getHealthPoint();
        player.takeDamage(50);
        assertEquals((shieldInitial+hpInitial)-50,player.getShieldPoint()+ player.getHealthPoint());
    }

    @Test
    public void playerTakeShield() {
        Player player = new Player();
        int shieldInitial = player.getShieldPoint();
        player.takeDamage(10);
        player.addShieldPoint(5);
        assertEquals(shieldInitial-10+5,player.getShieldPoint());
    }
}