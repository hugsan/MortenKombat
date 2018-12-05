package core.utils.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import core.framework.BaseActor;
import core.framework.BaseScreen;

/**
 * Class that shows the user that something has gone wrong with the questionAnswer file text.
 * The error should be generated when the game can not find the file to import the questions.
 */
public class QAerrorScreen extends BaseScreen {

    public void initialize() {
        // initialize the Background for the GameOverScreen
        BaseActor gameOverScreenBackground = new BaseActor(0, 0, mainStage);
        gameOverScreenBackground.loadTexture( "assets/img/GameOverScreen.png" );
        gameOverScreenBackground.setSize(800,600);


        // Button to exit the game
        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();

        Texture buttonTex = new Texture( Gdx.files.internal("assets/img/buttons/Exit.png") );
        TextureRegion buttonRegion3 = new TextureRegion( buttonTex );
        buttonStyle.up = new TextureRegionDrawable( buttonRegion3 );
        Button exit = new Button( buttonStyle );

        exit.setPosition(100, 40);
        uiStage.addActor(exit);

        exit.addListener(
                (Event e) ->
                {
                    if ( !(e instanceof InputEvent) )
                        return false;

                    if ( !((InputEvent)e).getType().equals(InputEvent.Type.touchDown) )
                        return false;
                    System.exit(0);
                    return false;
                }
        );
    }

    @Override
    public void update(float dt) {

    }
}
