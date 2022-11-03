package dungeoncrypt.game.collisions;

import com.badlogic.gdx.physics.box2d.*;
import dungeoncrypt.game.entities.Entity;
import dungeoncrypt.game.entities.Monster;
import dungeoncrypt.game.entities.Player;
import dungeoncrypt.game.tiles.special.SpecialTile;
import dungeoncrypt.game.views.GameScreen;

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
        Boolean debugMod = true;
        //
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        //On récupère les objets stockés dans les UserData
        Object obj1 = fa.getBody().getUserData();
        Object obj2 = fb.getBody().getUserData();
        //Static bodies only
        if(fa.getBody().getType().equals(BodyDef.BodyType.StaticBody) || fb.getBody().getType().equals(BodyDef.BodyType.StaticBody)){
            //Collision entre Special tile et Joueur
            if(obj1 instanceof SpecialTile && obj2 instanceof Player){
                if(debugMod){
                    System.out.println(fa.getBody().getUserData()+" has hit1 "+ fb.getBody().getUserData());
                }
                tileEffectPlayer(fb,fa);
            }
            if(obj2 instanceof Player && obj1 instanceof SpecialTile){
                if(debugMod){
                    System.out.println(fa.getBody().getUserData()+" has hit2 "+ fb.getBody().getUserData());
                }
                tileEffectPlayer(fb,fa);
            }
        //Dynamic bodies only
        } else {
            if(obj1 instanceof Player && obj2 instanceof Monster){
                monstreDamagePlayer(fa,fb);
            } else if(obj1 instanceof Monster && obj2 instanceof Player){
                monstreDamagePlayer(fb,fa);
            } else {
                if(debugMod){
                    //System.out.println("Contact");
                    //System.out.println(fa.getBody().getUserData()+" has hit "+ fb.getBody().getUserData());
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
     * @param fplayer
     * @param ftile
     */
    private void tileEffectPlayer (Fixture fplayer, Fixture ftile){
        ((SpecialTile) ftile.getBody().getUserData()).applyEffectOn((Entity) fplayer.getBody().getUserData());
    }
}
