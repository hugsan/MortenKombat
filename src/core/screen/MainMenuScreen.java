package core.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
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

/**
 * This is the main menu class. It is the first screen in the game,
 * it consist of 3 buttons lead to other menus or exiting the game.
 * It also contains the menu music that will play during all the menus, and the volume for all music in the game.
 */

public class MainMenuScreen extends BaseScreen{
    private static Music menuMusic;

    public MainMenuScreen() throws FileNotFoundException {
    }

    public void initialize() {

        // This is the background picture
        BaseActor mainMenuBackground = new BaseActor(0,0, mainStage);
        mainMenuBackground.loadTexture("assets/img/menubackground.png");
        mainMenuBackground.setSize(800,600);

        // This is the headline in the menu screen
        BaseActor title = new BaseActor(100,400, mainStage);
        title.loadTexture("assets/img/MortenKombatLogo.png");
        title.setSize(600,150);

        // Starting music
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/audio/music/TheWitcherSpikeroog.mp3"));
        menuMusic.setVolume(MortenCombat.volume);
        menuMusic.setLooping(true);
        menuMusic.play();

        // Button to start the game
        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();

        Texture buttonTex = new Texture( Gdx.files.internal("assets/img/buttons/Start.png") );
        TextureRegion buttonRegion = new TextureRegion(buttonTex);
        buttonStyle.up = new TextureRegionDrawable( buttonRegion );
        Button newGame = new Button( buttonStyle );

        newGame.setPosition(450, 180);
        uiStage.addActor(newGame);

        //listener starting the game, going to characterSelector screen
        newGame.addListener(
                (Event e) ->
                {
                    if ( !(e instanceof InputEvent) )
                        return false;
                    //mouseover
                    if ( !((InputEvent)e).getType().equals(InputEvent.Type.touchDown) )
                        return false;
                    SelectionScreen charSelect = null;

                    charSelect = new SelectionScreen(this);

                    MortenCombat.setActiveScreen(charSelect);
                    return true;
                }
        );

        // Button for the settings
        Button.ButtonStyle buttonStyle2 = new Button.ButtonStyle();

        Texture buttonTex2 = new Texture( Gdx.files.internal("assets/img/buttons/Settings.png") );
        TextureRegion buttonRegion2 = new TextureRegion(buttonTex2);
        buttonStyle2.up = new TextureRegionDrawable( buttonRegion2 );
        Button options = new Button( buttonStyle2 );

        options.setPosition(450, 100);
        uiStage.addActor(options);

        options.addListener(
                (Event e) ->
                {
                    if ( !(e instanceof InputEvent) )
                        return false;

                    if ( !((InputEvent)e).getType().equals(InputEvent.Type.touchDown) )
                        return false;
                    OptionsMenuScreen optionsScreen = null;

                        optionsScreen = new OptionsMenuScreen(this);

                    this.dispose();
                    MortenCombat.setActiveScreen(optionsScreen);


                    return true;
                }
        );

        // Button to exit the game
        Button.ButtonStyle buttonStyle3 = new Button.ButtonStyle();

        Texture buttonTex3 = new Texture( Gdx.files.internal("assets/img/buttons/Exit.png") );
        TextureRegion buttonRegion3 = new TextureRegion( buttonTex3 );
        buttonStyle3.up = new TextureRegionDrawable( buttonRegion3 );
        Button exit = new Button( buttonStyle3 );

        exit.setPosition(450, 20);
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

    //abstract method from BaseScreen need to be declared in our class.
    public void update(float dt) { }

    /**
     * Method that increases 5% the music of our game
      */
    protected static void musicVolumeUp(){
        MortenCombat.volume = MortenCombat.volume + 0.05f;
        if ( MortenCombat.volume > 0.5)
            MortenCombat.volume = 0.5f;
        menuMusic.setVolume(MortenCombat.volume);
    }

    /**
     * Method that decreases 5% the music of our game
     */
    protected static void musicVolumeDown() {
        MortenCombat.volume = MortenCombat.volume - 0.05f;
        if (MortenCombat.volume < 0 )
            MortenCombat.volume = 0;
        menuMusic.setVolume(MortenCombat.volume);
    }
    public static void menuMusicStop() {
        menuMusic.stop();
    }

}
