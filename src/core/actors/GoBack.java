package core.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import core.framework.BaseActor;

public class GoBack extends BaseActor
{
    public GoBack(float x, float y, Stage s)
    {
        super(x,y,s);
        loadTexture("assets/cavernopening.png");
        setBoundaryPolygon(8);
    }
}