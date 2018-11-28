package core.actors.exploringactors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import core.framework.BaseActor;

/**
 * This is a black blur that follows the hero in the underground maps.
 * It should limit the line of sight and make the maze more difficult to navigate.
 */

public class DarkEffect extends BaseActor
{
    public DarkEffect(float x, float y, Stage s)
    {
        super(x,y,s);
        loadTexture("assets/darkEffect.png");

    }
}
