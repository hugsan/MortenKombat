package core.actors.exploringactors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import core.framework.BaseActor;

/**
 * Exploring actor used in the exploring map.
 * The class has a constructor to initialize the class with animation and sets up the default animation to idle.
 */
public class Sign extends BaseActor
{
    // the text to be displayed
    private String text;
    // used to determine if sign text is currently being displayed
    private boolean viewing;

    /**
     * Constructor that initialize Sign with the animations and default animation.
     * @param x X coordinates where the actor is created
     * @param y Y coordinates where the actor is created
     * @param s Stage where the actor is created
     */
    public Sign(float x, float y, Stage s)
    {
       super(x,y,s);
       loadTexture("assets/sign.png");
       text = " ";
       viewing = false;
    }
    
    public void setText(String t)
    {  text = t;  }
    
    public String getText()
    {  return text;  }
    
    public void setViewing(boolean v)
    {  viewing = v;  }
    
    public boolean isViewing()
    {  return viewing;  }  
}