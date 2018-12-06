package core.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import core.utils.MortenCombat;
import core.framework.BaseActor;
import core.framework.BaseScreen;

/**
 * This is the GameOverScreen. The screen displays when all of the fighting actors dies.
 */

public class GameOverScreen extends BaseScreen {

    static Music gameOverMusic;
    public void initialize() {
        // initialize the Background for the GameOverScreen
        BaseActor gameOverScreenBackground = new BaseActor(0, 0, mainStage);
        gameOverScreenBackground.loadTexture( "assets/img/GameOverScreen.png" );
        gameOverScreenBackground.setSize(800,600);

        // The text for the motivational quote
        BaseActor title1 = new BaseActor(20,120, mainStage);
        title1.loadTexture("assets/img/Motivation.png");
        title1.setSize(400,300);

        // Button to exit the game
        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();

        Texture buttonTex = new Texture( Gdx.files.internal("assets/img/buttons/Exit.png") );
        TextureRegion buttonRegion3 = new TextureRegion( buttonTex );
        buttonStyle.up = new TextureRegionDrawable( buttonRegion3 );
        Button exit = new Button( buttonStyle );

        // Music for the screen
        gameOverMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/audio/music/gameOverMusic.mp3"));
        gameOverMusic.setVolume(MortenCombat.volume);
        gameOverMusic.setLooping(true);
        gameOverMusic.play();

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
