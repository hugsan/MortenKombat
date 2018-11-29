package core.actors.exploringactors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import core.framework.BaseActor;

public class Medic extends BaseActor
{
    public Medic(float x, float y, Stage s)
    {
        super(x,y,s);
        loadTexture("assets/img/MedicPack32.png");
        setBoundaryPolygon(8);
        boundToWorld();

    }

}
