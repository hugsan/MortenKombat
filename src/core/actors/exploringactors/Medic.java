package core.actors.exploringactors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import core.framework.BaseActor;

/**
 * This class is a healing point on the maps, restoring the heroes outside of battle.
 */

public class Medic extends BaseActor
{
    /**
     * Constructor that initialize Medic with the animations and default animation.
     * @param x X coordinates where the actor is created
     * @param y Y coordinates where the actor is created
     * @param s Stage where the actor is created
     */
    public Medic(float x, float y, Stage s)
    {
        super(x,y,s);
        loadTexture("assets/img/MedicPack32.png");
        setBoundaryPolygon(8);
        boundToWorld();

    }

}
