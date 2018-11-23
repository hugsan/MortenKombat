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
            sa.setHeroNumber(0);
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
                sa.setHeroNumber(fighterNumber);
            } else {
                moveToStart();
            }
        }
    }
    public void createCard(int fighterNumber, int fighterType) {

        this.fighterNumber = fighterNumber;
        this.fighterType = fighterType;


        if (fighterType == 1 && fighterNumber == 1) {

            loadTexture("assets/img/FighterCardOne.png");
        }
        if (fighterType == 1 && fighterNumber == 2) {
            loadTexture("assets/img/FighterCardTwo.png");
        }
        if (fighterType == 2 && fighterNumber == 1) {
            loadTexture("assets/img/MageCardTwo.png");
        }
        if (fighterType == 2 && fighterNumber == 2) {
            loadTexture("assets/img/MageCardOne.png");

        }
        if (fighterType == 3 && fighterNumber == 1) {
            loadTexture("assets/img/SupportCardTwo.png");

        }
        if (fighterType == 3 && fighterNumber == 2) {
            loadTexture("assets/img/SupportCardOne.png");


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
