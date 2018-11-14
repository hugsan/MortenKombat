package core.framework;

import com.badlogic.gdx.scenes.scene2d.Stage;
import core.framework.BaseActor;

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
