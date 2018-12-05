package core.utils;

import com.badlogic.gdx.scenes.scene2d.Stage;
import core.framework.DragAndDropActor;

/**
 * This class sets the rules for how you may select your heroes.
 * Using the SelectionArea class it limits the selection to one of each type of hero,
 * and allows to unselect a previously selected hero.
 * It also have the textures for the different heroes.
 */

public class CharacterCard extends DragAndDropActor {

    private int fighterNumber;
    private int fighterType;

    private SelectionArea selectionArea;

    public CharacterCard(float x, float y, Stage s)
    {
        super(x, y, s);
    }

    /**
     * Check if the card is in the selection area, if it is, we clear the selection area for a new card.
     */
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

    /**
     * Check if there is a selection area beneath the card, and if that selection area is valid.
     * If there is a area and it's empty fill it. Else move the card back to where it was picked up.
     */

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

    /**
     * Creates the card for our fighters, with art, number and type.
     */

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

    public void act(float dt)
    {
        super.act(dt);
    }
    private int getFighterType() {        return fighterType;    }
    private void setSelectionArea(SelectionArea sa)    {  selectionArea = sa;  }
    private SelectionArea getSelectionArea()    {  return selectionArea;  }
    private void clearSelectionArea()    {  selectionArea = null;  }
    private boolean hasSelectionArea()    {  return selectionArea != null;  }

}
