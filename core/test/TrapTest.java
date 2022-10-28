

import org.junit.Before;
import org.junit.Test;
import terminal.entities.Monster;
import terminal.entities.Player;
import terminal.tiles.special.Trap;

import static org.junit.Assert.assertEquals;

import static terminal.data.Data.HP_TRAP;


class TrapTest {

    @Test
    private static void playerGetDamage() {
        Trap trap = new Trap();
        Player player = new Player();
        int hpInitial = player.getHealthPoint();
        trap.applyEffectOn(player);
        assertEquals(hpInitial-HP_TRAP,player.getHealthPoint());
    }

    @Test
    private static void monstersGetDamage() {
        Trap trap = new Trap();
        Monster monster = new Monster(1,1);
        int hpInitial = monster.getHealthPoint();
        trap.applyEffectOn(monster);
        assertEquals(hpInitial-HP_TRAP,monster.getHealthPoint());
    }

    @Test
    private static void durabilityTrapDecrease() {
        Trap trap = new Trap();
        Player player = new Player();
        int durabilityInitial = trap.getAppliedEffectCounter();
        trap.applyEffectOn(player);
        assertEquals(durabilityInitial,trap.getAppliedEffectCounter());
    }

    public static void main(String[] args) {
        playerGetDamage();
        monstersGetDamage();
        durabilityTrapDecrease();
    }
}