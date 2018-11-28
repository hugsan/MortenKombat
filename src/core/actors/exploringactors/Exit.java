package core.actors.exploringactors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import core.framework.BaseActor;

/**
 * This is the exit point of the maze maps, when the hero hits it the map switches to the next map.
 */

public class Exit extends BaseActor
{
    public Exit(float x, float y, Stage s)
    {
        super(x,y,s);
        loadTexture("assets/img/cavernopening.png");
        setBoundaryPolygon(8);
    }
}