package core.framework;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

public abstract class BaseScreen implements Screen, InputProcessor
{
    public Stage mainStage;
    protected Stage uiStage;
    protected Stage qaStage;
    protected Table uiTable;
    protected boolean paused = false;

    public BaseScreen() {
        mainStage = new Stage();
        uiStage = new Stage();
        qaStage = new Stage();


        uiTable = new Table();
        uiTable.setFillParent(true);
        uiStage.addActor(uiTable);


        // Do not call overridable methods in the constructor
        initialize();

    }

    public abstract void initialize();

    public abstract void update(float dt);

    // Gameloop:
    // (1) process input (discrete handled by listener; continuous in update)
    // (2) update game logic
    // (3) render the graphics
    public void render(float dt) 
    {
        // limit amount of time that can pass while window is being dragged
        if (!paused)
        {
            dt = Math.min(dt,1/30f);

            // act methods
            uiStage.act(dt);
            qaStage.act(dt);
            mainStage.act(dt);

            // defined by user

                update(dt);


            // clear the screen
            Gdx.gl.glClearColor(0,0,0,1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            // draw the graphics
            mainStage.draw();
            uiStage.draw();
            qaStage.draw();
        }


    }

    // methods required by Screen interface
    public void resize(int width, int height) {  }

    public void pause()   {
        this.paused = true;
    }

    public void resume()  {
        this.paused = false;
    }

    public void dispose() {  }

    /**
     *  Called when this becomes the active screen in a Game.
     *  Set up InputMultiplexer here, in case screen is reactivated at a later time.
     */
    public void show()    
    {  
        InputMultiplexer im = (InputMultiplexer)Gdx.input.getInputProcessor();
        im.addProcessor(this);
        im.addProcessor(uiStage);
        im.addProcessor(qaStage);
        im.addProcessor(mainStage);
    }

    /**
     *  Called when this is no longer the active screen in a Game.
     *  Screen class and Stages no longer process input.
     *  Other InputProcessors must be removed manually.
     */
    public void hide()    
    {  
        InputMultiplexer im = (InputMultiplexer)Gdx.input.getInputProcessor();
        im.removeProcessor(this);
        im.removeProcessor(uiStage);
        im.removeProcessor(qaStage);
        im.removeProcessor(mainStage);
    }

    /**
     *  Useful for checking for touch-down events.
     */
    public boolean isTouchDownEvent(Event e)
    {
        return (e instanceof InputEvent) && ((InputEvent)e).getType().equals(Type.touchDown);
    }

    // methods required by InputProcessor interface
    public boolean keyDown(int keycode)
    {  return false;  }

    public boolean keyUp(int keycode)
    {  return false;  }

    public boolean keyTyped(char c) 
    {  return false;  }

    public boolean mouseMoved(int screenX, int screenY)
    {  return false;  }

    public boolean scrolled(int amount) 
    {  return false;  }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) 
    {  return false;  }

    public boolean touchDragged(int screenX, int screenY, int pointer) 
    {  return false;  }

    public boolean touchUp(int screenX, int screenY, int pointer, int button) 
    {  return false;  }

    public static class DialogBox extends BaseActor
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
}