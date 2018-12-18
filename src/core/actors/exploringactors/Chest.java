package core.actors.exploringactors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import core.framework.BaseActor;

/**
 * Class used to create chest. The exploring Hero can pick them up. Right now does nothing, need to implement
 * items and/or potion.
 */
public class Chest extends BaseActor
{
    public Chest(float x, float y, Stage s)
    {
        super(x,y,s);
        loadTexture("assets/img/Chest.png");
        setBoundaryPolygon(8);

    }

    public void chestOpen() {
        loadTexture("assets.img.ChestOpen.png");
    }

}
