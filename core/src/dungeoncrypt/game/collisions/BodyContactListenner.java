package dungeoncrypt.game.collisions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import dungeoncrypt.game.entities.Entity;
import dungeoncrypt.game.entities.monsters.Monster;
import dungeoncrypt.game.entities.Player;
import dungeoncrypt.game.tiles.special.Exit;
import dungeoncrypt.game.tiles.special.SpecialTile;
import dungeoncrypt.game.views.GameScreen;
import dungeoncrypt.game.weapons.Weapon;

import static dungeoncrypt.game.data.Data.DEBUG_MODE;

public class BodyContactListenner implements ContactListener {
    private GameScreen parent;
    public BodyContactListenner (GameScreen parent){
        this.parent = parent;
    }

    @Override
    /**
     * Fonction lancé au début d'un contact entre 2 fixture.
     * L'ordre de la collision (A rentre dans B ou B rentre dans A n'est pas définie.
     * Il faut donc utiliser 2 IF pour spécifier chaque cas.
     */
    public void beginContact(Contact contact) {
        //
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        //On récupère les objets stockés dans les UserData
        Object objA = fixtureA.getBody().getUserData();
        Object objB = fixtureB.getBody().getUserData();
        //Static bodies only
        if(fixtureA.getBody().getType().equals(BodyDef.BodyType.StaticBody) || fixtureB.getBody().getType().equals(BodyDef.BodyType.StaticBody)){
            //Collision entre Special tile et Joueur
            if(objA instanceof SpecialTile && objB instanceof Player){
                if(DEBUG_MODE){
                    System.out.println(fixtureA.getBody().getUserData()+" has hit1 "+ fixtureB.getBody().getUserData());
                }
                tileEffectPlayer(fixtureB,fixtureA);
            }
            if(objB instanceof Monster && objA instanceof SpecialTile){
                if(DEBUG_MODE){
                    System.out.println(fixtureA.getBody().getUserData()+" has hit2 "+ fixtureB.getBody().getUserData());
                }
                tileEffectMonster(fixtureB,fixtureA);
            }
        //Dynamic bodies only
        } else {
            if(objA instanceof Monster){
                if (objB instanceof Player){
                    monstreDamagePlayer(fixtureB, fixtureA);
                } else if(objB instanceof Weapon) {
                    weaponDamageMonster(fixtureB,fixtureA);
                    System.out.println("Monster hit weapon");

                }
            } else if (objB instanceof Monster){
                if(objA instanceof Player){
                    monstreDamagePlayer(fixtureA,fixtureB);
                } else if (objA instanceof Weapon){
                    weaponDamageMonster(fixtureA, fixtureB);
                    System.out.println("Monster hit weapon");
                }
            } else {
                if(DEBUG_MODE){
                    System.out.println("Contact");
                    System.out.println(fixtureA.getBody().getUserData()+" has hit "+ fixtureB.getBody().getUserData());
                }
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    /**
     * Fonction utilisé pour infliger des degats au joueur lors qu'il est en contact avec les monstres
     * @param fplayer Fixture du joueur
     * @param otherFixture Fixture du monstre
     */
    private void monstreDamagePlayer(Fixture fplayer, Fixture otherFixture){
        ((Player) fplayer.getBody().getUserData()).subtractHealthPoint(5);
        System.out.println("Joueur touché par MONSTRE. PV restant: "+((Player) fplayer.getBody().getUserData()).getHealthPoint());
    }

    /**
     * Fonction utilisée pour appliquer l'effet de la case au joueur
     * @param playerFixture
     * @param tileFixture
     */
    private void tileEffectPlayer (Fixture playerFixture, Fixture tileFixture){
        ((SpecialTile) tileFixture.getBody().getUserData()).applyEffectOn((Entity) playerFixture.getBody().getUserData());
    }

    /**
     * Fonction utilisée pour appliquer l'effet de la case au monstre
     * @param monsterFixture
     * @param tileFixture
     */
    private void tileEffectMonster(Fixture monsterFixture, Fixture tileFixture) {
        final Entity monster = ((Entity) monsterFixture.getBody().getUserData());
        ((SpecialTile) tileFixture.getBody().getUserData()).applyEffectOn(monster);
        killMonster((Monster) monster);
    }

    private void weaponDamageMonster(Fixture weaponFixture, Fixture monsterFixture){
        final Entity monster = ((Entity) monsterFixture.getBody().getUserData());
        Weapon weapon = (Weapon) weaponFixture.getBody().getUserData();
        weapon.setDamageTo(monster);
        killMonster((Monster) monster);
        System.out.println("MONSTRE touché par WEAPON. PV restant: "+((Entity) monsterFixture.getBody().getUserData()).getHealthPoint());
    }

    private void killMonster(final Monster monster){
        if(monster.getHealthPoint() <= 0){
            parent.getRoomManager().getActualRoom().killMonster(monster);
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run () {
                    parent.getworld().destroyBody(monster.getBody());
                }
            });
            parent.getRoomManager().checkRoomIsEmpty();
        }
    }
}
