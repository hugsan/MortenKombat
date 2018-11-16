package core.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import core.MortenCombat;
import core.framework.BaseActor;
import core.framework.BaseScreen;
import core.MortenCombat;

public class MainMenuScreen extends BaseScreen {
    private LevelScreen playableMap;
    private static Music menuMusic;


        // This is the background picture
    public void initialize() {
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

        newGame.addListener(
                (Event e) ->
                {
                    if ( !(e instanceof InputEvent) )
                        return false;
                    //mouseover
                    if ( !((InputEvent)e).getType().equals(InputEvent.Type.touchDown) )
                        return false;
                    SelectionScreen charSelect = new SelectionScreen();
                    this.dispose();
                    //MortenCombat.setActiveScreen(charSelect);
                    menuMusic.stop();
                    MortenCombat.startGame();
                    return false;
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
                    System.out.println("options pressed");
                    OptionsMenuScreen optionsScreen = new OptionsMenuScreen(this);
                    this.dispose();
                    MortenCombat.setActiveScreen(optionsScreen);


                    return false;
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

    public void update(float dt) {

        // This changes the volume when you press the volume button
    }
    protected static void volumeUp(){
        MortenCombat.volume = MortenCombat.volume + 0.1f;
        if ( MortenCombat.volume > 1)
            MortenCombat.volume = 1;
        menuMusic.setVolume(MortenCombat.volume);
    }
    protected static void volumeDown() {
        MortenCombat.volume = MortenCombat.volume - 0.1f;
        if (MortenCombat.volume < 0 )
            MortenCombat.volume = 0;
        menuMusic.setVolume(MortenCombat.volume);
    }

}
