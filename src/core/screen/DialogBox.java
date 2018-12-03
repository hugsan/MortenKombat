package core.screen;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Align;
import core.framework.BaseActor;
import core.framework.BaseGame;

public class DialogBox extends BaseActor
{
    private Label dialogLabel;
    private float padding = 16;

    public DialogBox(float x, float y, Stage s)
    {
        super(x,y,s);
        loadTexture("assets/dialog-translucent.png");

        dialogLabel = new Label(" ", BaseGame.labelStyle);
        dialogLabel.setWrap(true);
        dialogLabel.setAlignment( Align.topLeft );
        dialogLabel.setPosition( padding, padding );
        this.setDialogSize( getWidth(), getHeight() );
        this.addActor(dialogLabel);
    }

    /**
     *Set the dialog size to the desired size
     * @param width Float type variable for setting the width of the dialog
     * @param height Float type variable for setting the height of the dialog
     */
    public void setDialogSize(float width, float height)
    {
        this.setSize(width, height);
        dialogLabel.setWidth( width - 2 * padding );
        dialogLabel.setHeight( height - 2 * padding );
    }

    /**
     *Set the dialog's text.
     * @param text String type for the text
     */
    public void setText(String text)
    {  dialogLabel.setText(text);  }

    /**
     *Set the scale of the font
     * @param scale Float type for the scale
     */
    public void setFontScale(float scale)
    {  dialogLabel.setFontScale(scale);  }

    /**
     *Set the color of the font
     * @param color Color object like Color.WHITE...
     */
    public void setFontColor(Color color)
    {  dialogLabel.setColor(color);  }

    /**
     *Set the background color of the dialogbox.
     * @param color Color object like Color.WHITE...
     */
    public void setBackgroundColor(Color color)
    {  this.setColor(color);  }

    /**
     * Align the dialog label to the topleft position
     */
    public void alignTopLeft()
    {  dialogLabel.setAlignment( Align.topLeft );  }

    /**
     * Align the dialog label to the center position
     */
    public void alignCenter()
    {  dialogLabel.setAlignment( Align.center );  }
}
