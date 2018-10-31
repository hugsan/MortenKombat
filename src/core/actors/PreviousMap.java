package core.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import core.framework.BaseActor;

public class PreviousMap extends BaseActor
{
    public PreviousMap(float x, float y, Stage s)
    {
        super(x,y,s);
        loadTexture("assets/creeperEyesGreen.png");
        setBoundaryPolygon(8);
    }
}