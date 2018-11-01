package core.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import core.framework.BaseActor;

public class Exit extends BaseActor
{
    public Exit(float x, float y, Stage s)
    {
        super(x,y,s);
        loadTexture("assets/img/cavernopening.png");
        setBoundaryPolygon(8);
    }
}