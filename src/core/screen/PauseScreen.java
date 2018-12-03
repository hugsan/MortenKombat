package core.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import core.MortenCombat;
import core.framework.BaseActor;
import core.framework.BaseScreen;

import java.io.FileNotFoundException;

public class PauseScreen extends BaseScreen {

    private BaseScreen screen;

    public PauseScreen(BaseScreen s) {
        super();
        screen = s;


    }

    public void initialize() {
        // initialize the Background for the pause screen
        BaseActor pauseBackground = new BaseActor(0, 0, mainStage);
        pauseBackground.loadTexture( "assets/img/PauseScreen.png" );
        pauseBackground.setSize(400,500);
        pauseBackground.toBack();
        pauseBackground.setPosition(200, 50);

        // Button to exit the game
        Button.ButtonStyle buttonStyle3 = new Button.ButtonStyle();

        Texture buttonTex3 = new Texture( Gdx.files.internal("assets/img/buttons/Exit.png") );
        TextureRegion buttonRegion3 = new TextureRegion( buttonTex3 );
        buttonStyle3.up = new TextureRegionDrawable( buttonRegion3 );
        Button exit = new Button( buttonStyle3 );

        exit.setPosition(250, 100);
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

        // The text for the volume
        BaseActor title1 = new BaseActor(250,250, mainStage);
        title1.loadTexture("assets/img/buttons/Volume.png");
        title1.setSize(300,70);

        // Volume button for turning the volume up
        Button.ButtonStyle buttonStyle2 = new Button.ButtonStyle();

        Texture buttonTex2 = new Texture( Gdx.files.internal("assets/img/buttons/VolumeUp.png") );
        TextureRegion buttonRegion2 = new TextureRegion(buttonTex2);
        buttonStyle2.up = new TextureRegionDrawable( buttonRegion2 );
        Button volumenUp = new Button( buttonStyle2 );

        volumenUp.setPosition(320, 170);
        uiStage.addActor(volumenUp);

        volumenUp.addListener(
                (Event e) ->
                {
                    if ( !(e instanceof InputEvent) )
                        return false;

                    if ( !((InputEvent)e).getType().equals(InputEvent.Type.touchDown) )
                        return false;

                    MainMenuScreen.musicVolumeUp();
                    return false;

                }
        );

        // The volume button for turning the volume down
        Button.ButtonStyle buttonStyle4 = new Button.ButtonStyle();

        Texture buttonTex4 = new Texture( Gdx.files.internal("assets/img/buttons/VolumeDown.png") );
        TextureRegion buttonRegion4 = new TextureRegion(buttonTex4);
        buttonStyle4.up = new TextureRegionDrawable( buttonRegion4 );
        Button volumenDown = new Button( buttonStyle4 );

        volumenDown.setPosition(380, 170);
        uiStage.addActor(volumenDown);

        volumenDown.addListener(
                (Event e) ->
                {
                    if ( !(e instanceof InputEvent) )
                        return false;

                    if ( !((InputEvent)e).getType().equals(InputEvent.Type.touchDown) )
                        return false;

                    MainMenuScreen.musicVolumeDown();
                    return false;
                }
        );

        // Continue button
        Button.ButtonStyle buttonStyle1 = new Button.ButtonStyle();

        Texture buttonTex = new Texture( Gdx.files.internal("assets/img/buttons/Continue.png") );
        TextureRegion buttonRegion = new TextureRegion(buttonTex);
        buttonStyle1.up = new TextureRegionDrawable( buttonRegion );
        Button back = new Button( buttonStyle1 );

        back.setPosition(250, 350);
        uiStage.addActor(back);

        back.addListener(
                (Event e) ->
                {
                    if ( !(e instanceof InputEvent) )
                        return false;

                    if ( !((InputEvent)e).getType().equals(InputEvent.Type.touchDown) )
                        return false;
                    //deletes the screen that we are using, before going back to menu screen.
                    this.dispose();
                    MortenCombat.setActiveScreen(screen);
                    return false;
                }
        );

    }

    @Override
    public void update(float dt) {
        if (screen instanceof LevelScreen)
          LevelScreen.backgroundMusic.setVolume(MortenCombat.volume);
        if (screen instanceof  FightScreen){
            FightScreen.battleMusic.setVolume(MortenCombat.volume);
        }
    }
}
