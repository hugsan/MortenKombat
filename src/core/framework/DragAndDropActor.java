package core.framework;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 * This class allows BaseActors to be draggable. It has three stages, touchDown, touchDragged and touchUp.
 * First it sees if i can be moved, then it follows the mouse movement, and finally on release
 * it sees where it gets dropped, and if there are any available targets it snaps to it.
 * If the target is filled it get moved back to before the drag motion started.
 */

public class DragAndDropActor extends BaseActor {

    private DragAndDropActor self;

    private float grabOffsetX;
    private float grabOffsetY;

    private float startPositionX;
    private float startPositionY;

    private DropTargetActor dropTarget;

    private boolean draggable;

    public DragAndDropActor(float x, float y, Stage s)
    {
        super(x, y, s);

        draggable = true;
        self = this;

        addListener(
                new InputListener()
            {

                public boolean touchDown(InputEvent event, float offsetX, float offsetY, int pointer, int button)
                {

                    if ( !self.isDraggable() ) return false;

                    self.grabOffsetX = offsetX;
                    self.grabOffsetY = offsetY;

                    self.startPositionX = self.getX();
                    self.startPositionY = self.getY();

                    self.toFront();

                    self.addAction( Actions.scaleTo(1.1f, 1.1f, 0.25f) );
                    self.onDragStart();

                    return true;
                }

                public void touchDragged(InputEvent event, float offsetX, float offsetY, int pointer)
                {
                    float deltaX = offsetX - self.grabOffsetX;
                    float deltaY = offsetY - self.grabOffsetY;

                    self.moveBy(deltaX, deltaY);
                }

                public void touchUp(InputEvent event, float offsetX, float offsetY, int pointer, int button)
                {
                    self.setDropTarget(null);

                    float closetsDistance = Float.MAX_VALUE;

                    for ( BaseActor actor : BaseActor.getList(self.getStage(), "core.framework.DropTargetActor") )
                    {
                        DropTargetActor target = (DropTargetActor)actor;

                        if ( target.isTargetable() && self.overlaps(target) )
                        {
                            float currentDistance = Vector2.dst(self.getX(),self.getY(), target.getX(),target.getY());

                            if (currentDistance < closetsDistance)
                            {
                                self.setDropTarget(target);
                                closetsDistance = currentDistance;
                            }
                        }
                    }
                    self.addAction( Actions.scaleTo(1.00f, 1.00f, 0.25f) );
                    self.onDrop();
                }
            }
        );
    }
    public void moveToActor(BaseActor other)
    {
        float x = other.getX() + (other.getWidth()  - this.getWidth())  / 2;
        float y = other.getY() + (other.getHeight() - this.getHeight()) / 2;
        addAction( Actions.moveTo(x,y, 0.50f, Interpolation.pow3) );
    }
    public void moveToStart()
    {
        addAction( Actions.moveTo(startPositionX, startPositionY, 0.50f, Interpolation.pow3) );
    }

    public void act(float dt) { super.act(dt); boundToWorld(); }
    public void onDrop() {    }
    public void onDragStart() {    }
    public boolean hasDropTarget() {  return (dropTarget != null);  }
    private void setDropTarget(DropTargetActor dt) {  dropTarget = dt;  }
    public DropTargetActor getDropTarget() {  return dropTarget;  }
    private boolean isDraggable() {  return draggable;  }

}
