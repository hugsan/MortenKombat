package core.framework;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class CharacterCard extends DragAndDropActor {

    private int row;
    private int col;

    private SelectionArea selectionArea;

    public CharacterCard(float x, float y, Stage s) {
        super(x, y, s);
    }

    public void setRow(int r ){
        row = r;
    }

    public void setCol(int c)    {  col = c;  }
    public int getRow()    {  return row;  }
    public int getCol()    {  return col;  }
    public void setSelectionArea(SelectionArea sa)    {  selectionArea = sa;  }
    public SelectionArea getSelectionArea()    {  return selectionArea;  }
    public void clearSelectionArea()    {  selectionArea = null;  }
    public boolean hasSelectionArea()    {  return selectionArea != null;  }
    public boolean isCorrectlyPlaced()
    {
        return hasSelectionArea()
                && this.getRow() == selectionArea.getRow()
                && this.getCol() == selectionArea.getCol();
    }

}
