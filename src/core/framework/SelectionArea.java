package core.framework;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class SelectionArea extends DropTargetActor {

    private int row;
    private int col;

    public SelectionArea(float x, float y, Stage s) {

        super(x, y, s);
        //loadTexture("a");
    }

    public void setRow(int r)    {  row = r;  }
    public void setCol(int c)    {  col = c;  }
    public int getRow()    {  return row;  }
    public int getCol()    {  return col;  }

}
