package core.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import core.framework.BaseActor;
import core.framework.BaseScreen;

public class VictoryScreen extends BaseScreen {



    public void initialize() {

        // initialize the Background for the VictoryScreen
        BaseActor background = new BaseActor(0,0, mainStage);
        background.loadTexture("assets/img/VictoryBackground.png");
        background.setSize(800,600);


        // Button to exit the game
        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();

        Texture buttonTex = new Texture( Gdx.files.internal("assets/img/buttons/Exit.png") );
        TextureRegion buttonRegion = new TextureRegion( buttonTex );
        buttonStyle.up = new TextureRegionDrawable( buttonRegion );
        Button exit = new Button( buttonStyle );

        exit.setPosition(300, 200);
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