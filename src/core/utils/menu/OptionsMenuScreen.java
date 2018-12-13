package core.utils.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import core.utils.MortenKombat;
import core.framework.BaseActor;
import core.framework.BaseScreen;

/**
 * This is the options screen class and it consist of five buttons, it is in here the volume for music can be changed.
 */

public class OptionsMenuScreen extends BaseScreen {

    private BaseScreen menu;

    public static boolean isQuickVersion() {
        return quickVersion;
    }

    private static boolean quickVersion = false;

    public OptionsMenuScreen(BaseScreen menu) {
        super();
        this.menu = menu;
    }
    @Override
    public void initialize() {

        // Background picture for the settings screen
        BaseActor mainMenuBackground = new BaseActor(0,0, mainStage);
        mainMenuBackground.loadTexture("assets/img/SettingsBackground.png");
        mainMenuBackground.setSize(800,600);

        // The text for difficulty
        BaseActor title = new BaseActor(200,475, mainStage);
        title.loadTexture("assets/img/buttons/DifficultyText.png");
        title.setSize(400,100);

        // The text for the volume
        BaseActor title1 = new BaseActor(250,100, mainStage);
        title1.loadTexture("assets/img/buttons/Volume.png");
        title1.setSize(300,70);

        // Back button
        Button.ButtonStyle buttonStyle1 = new Button.ButtonStyle();

        Texture buttonTex = new Texture( Gdx.files.internal("assets/img/buttons/Back.png") );
        TextureRegion buttonRegion = new TextureRegion(buttonTex);
        buttonStyle1.up = new TextureRegionDrawable( buttonRegion );
        Button back = new Button( buttonStyle1 );

        back.setPosition(25, 25);
        uiStage.addActor(back);
        back.setSize(60,60);

        back.addListener(
                (Event e) ->
                {
                    if ( !(e instanceof InputEvent) )
                        return false;

                    if ( !((InputEvent)e).getType().equals(InputEvent.Type.touchDown) )
                        return false;

                    System.out.println("Exit Settings");
                    //deletes the screen that we are using, before going back to utils screen.
                    this.dispose();
                    MortenKombat.setActiveScreen(menu);
                    return false;
                }
        );

        // Volume button for turning the volume up
        Button.ButtonStyle buttonStyle2 = new Button.ButtonStyle();

        Texture buttonTex2 = new Texture( Gdx.files.internal("assets/img/buttons/VolumeUp.png") );
        TextureRegion buttonRegion2 = new TextureRegion(buttonTex2);
        buttonStyle2.up = new TextureRegionDrawable( buttonRegion2 );
        Button volumenUp = new Button( buttonStyle2 );

        volumenUp.setPosition(525, 115);
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
        Button.ButtonStyle buttonStyle3 = new Button.ButtonStyle();

        Texture buttonTex3 = new Texture( Gdx.files.internal("assets/img/buttons/VolumeDown.png") );
        TextureRegion buttonRegion3 = new TextureRegion(buttonTex3);
        buttonStyle3.up = new TextureRegionDrawable( buttonRegion3 );
        Button volumenDown = new Button( buttonStyle3 );

        volumenDown.setPosition(525, 55);
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

        // Difficulty button age 12-16
        Button.ButtonStyle buttonStyle4 = new Button.ButtonStyle();

        Texture buttonTex4 = new Texture( Gdx.files.internal("assets/img/buttons/Quick.png") );
        TextureRegion buttonRegion4 = new TextureRegion(buttonTex4);
        buttonStyle4.up = new TextureRegionDrawable( buttonRegion4 );
        Button easy = new Button( buttonStyle4 );

        easy.setPosition(250, 270);
        uiStage.addActor(easy);

        easy.addListener(
                (Event e) ->
                {
                    if ( !(e instanceof InputEvent) )
                        return false;

                    if ( !((InputEvent)e).getType().equals(InputEvent.Type.touchDown) )
                        return false;

                    quickVersion = true;
                    this.dispose();
                    MortenKombat.setActiveScreen(menu);
                    return false;
                }
        );

        // Difficulty button age 16+
        Button.ButtonStyle buttonStyle5 = new Button.ButtonStyle();

        Texture buttonTex5 = new Texture( Gdx.files.internal("assets/img/buttons/Normal.png") );
        TextureRegion buttonRegion5 = new TextureRegion(buttonTex5);
        buttonStyle5.up = new TextureRegionDrawable( buttonRegion5 );
        Button hard = new Button( buttonStyle5 );

        hard.setPosition(250, 350);
        uiStage.addActor(hard);

        hard.addListener(
                (Event e) ->
                {
                    if ( !(e instanceof InputEvent) )
                        return false;

                    if ( !((InputEvent)e).getType().equals(InputEvent.Type.touchDown) )
                        return false;

                    quickVersion = false;
                    this.dispose();
                    MortenKombat.setActiveScreen(menu);
                    return false;
                }
        );

    }

    @Override
    public void update(float dt) {   }
}
