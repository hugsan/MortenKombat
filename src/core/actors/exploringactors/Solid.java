package core.actors.exploringactors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import core.framework.BaseActor;

/**
 * This is the class that handles all our walls and other objects.
 * It prevents the hero or any other actor from overlapping.
 */

public class Solid extends BaseActor
{
    public Solid(float x, float y, float width, float height, Stage s){

        super(x,y,s);
        setSize(width, height);
        setBoundaryRectangle();

    }
}
