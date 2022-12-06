package dungeoncrypt.game.tiles.special;

import dungeoncrypt.game.entities.Player;
import dungeoncrypt.game.entities.monsters.Zombie;
import org.junit.Test;

import static dungeoncrypt.game.data.Data.HP_TRAP;
import static org.junit.Assert.*;

public class TrapTest {

    @Test
    public void playerGetDamage() {
        Trap trap = new Trap();
        Player player = new Player();
        int hpInitial = player.getHealthPoint();
        trap.applyEffectOn(player);
        assertEquals(hpInitial-HP_TRAP,player.getHealthPoint());
    }

    @Test
    public void monstersGetDamage() {
        Trap trap = new Trap();
        Zombie monster = new Zombie(1,1);
        int hpInitial = monster.getHealthPoint();
        trap.applyEffectOn(monster);
        assertEquals(hpInitial-HP_TRAP,monster.getHealthPoint());
    }

    @Test
    public void durabilityTrapDecrease() {
        Trap trap = new Trap();
        Player player = new Player();
        int durabilityInitial = trap.getAppliedEffectCounter();
        trap.applyEffectOn(player);
        assertEquals(durabilityInitial,trap.getAppliedEffectCounter());
    }

}