package core.framework;

import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * This class extends the BaseActor to allow easier stacking of BaseActors.
 * It also allows a baseactor to be "filled" when there already is a baseactor in the slot.
 */

public class DropTargetActor extends BaseActor {

    private boolean targetable;

    public DropTargetActor(float x, float y, Stage s) {
        super(x, y, s);
        targetable = true;
    }

    public void setTargetable(boolean t) {
        targetable = t;
    }

    public boolean isTargetable() {
        return targetable;
    }
}
