package core.framework;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class SelectionArea extends DropTargetActor {

    private int heroNumber; //fighter = 1, mage = 2, support = 3.

    public SelectionArea(float x, float y, Stage s) {

        super(x, y, s);
        isTargetable();
        loadTexture("assets/img/buttons/back.png");
        setSize(100,100);
        setBoundaryRectangle();

    }

    public int getHeroNumber()                  { return heroNumber; }
    public void setHeroNumber(int heroNumber)   { this.heroNumber = heroNumber; }

}
