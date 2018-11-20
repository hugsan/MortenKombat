package core.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import core.framework.DragAndDropActor;

public class CharacterCard extends DragAndDropActor {

    public static String[] fighterTypeString = {"Fighter", "Mage", "Support"};

    private int fighterNumber;
    private int fighterType;

    private SelectionArea selectionArea;

    public CharacterCard(float x, float y, Stage s)
    {
        super(x, y, s);
    }

    public void act(float dt)
    {
        super.act(dt);

    }
    public void onDragStart()
    {
        if ( hasSelectionArea() )
        {
            SelectionArea sa = getSelectionArea();
            sa.setTargetable(true);
            clearSelectionArea();
        }
    }
    public void onDrop()
    {
        if ( hasDropTarget() )
        {
            SelectionArea sa = (SelectionArea) getDropTarget();

            if (getFighterType() == sa.getHeroType()) {
                moveToActor(sa);
                setSelectionArea(sa);
                sa.setTargetable(false);
            } else {
                moveToStart();
            }
        }
    }
    public void createCard(int fighterNumber, int fighterType) {

        this.fighterNumber = fighterNumber;
        this.fighterType = fighterType;
        loadTexture("assets/img/FighterOne.png");

        if (fighterType == 1 && fighterNumber == 1) {
            //loadTexture("assets/img/FighterOne.png");
        }
        if (fighterType == 1 && fighterNumber == 2) {

        }
        if (fighterType == 2 && fighterNumber == 1) {

        }
        if (fighterType == 2 && fighterNumber == 2) {

        }
        if (fighterType == 3 && fighterNumber == 3) {

        }
        if (fighterType == 3 && fighterNumber == 3) {

        }

    }

    public static String[] getFighterTypeString() {        return fighterTypeString;    }
    public int getFighterNumber() {        return fighterNumber;    }
    public int getFighterType() {        return fighterType;    }
    public void setSelectionArea(SelectionArea sa)    {  selectionArea = sa;  }
    public SelectionArea getSelectionArea()    {  return selectionArea;  }
    public void clearSelectionArea()    {  selectionArea = null;  }
    public boolean hasSelectionArea()    {  return selectionArea != null;  }


}
