package core.actors.exploringactors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import core.framework.BaseActor;

/**
 * This is the exit point of the maze maps, when the hero hits it the map switches to the next map.
 */

public class Door extends BaseActor
{
    public Door(float x, float y, Stage s)
    {
        super(x,y,s);
        loadTexture("assets/maps/Door64p.png");
        setBoundaryPolygon(8);
    }
}