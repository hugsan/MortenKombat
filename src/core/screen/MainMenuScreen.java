package core.screen;

import com.badlogic.gdx.Gdx;
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

public class MainMenuScreen extends BaseScreen {
    private LevelScreen playableMap;

    public void initialize() {
        BaseActor mainMenuBackground = new BaseActor(0,0, mainStage);
        mainMenuBackground.loadTexture("assets/img/menubackground.png");
        mainMenuBackground.setSize(800,600);



        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();

        Texture buttonTex = new Texture( Gdx.files.internal("assets/img/buttons/Start.png") );
        TextureRegion buttonRegion = new TextureRegion(buttonTex);
        buttonStyle.up = new TextureRegionDrawable( buttonRegion );
        Button newGame = new Button( buttonStyle );

        newGame.setPosition(335, 300);
        uiStage.addActor(newGame);

        newGame.addListener(
                (Event e) ->
                {
                    if ( !(e instanceof InputEvent) )
                        return false;
                    //mouseover
                    if ( !((InputEvent)e).getType().equals(InputEvent.Type.touchDown) )
                        return false;
                    MortenCombat.startGame();
                    return false;
                }
        );

        Texture buttonTex2 = new Texture( Gdx.files.internal("assets/img/options.png") );
        TextureRegion buttonRegion2 = new TextureRegion(buttonTex2);
        buttonStyle.up = new TextureRegionDrawable( buttonRegion2 );
        Button options = new Button( buttonStyle );

        options.setPosition(335, 250);
        uiStage.addActor(options);

        options.addListener(
                (Event e) ->
                {
                    if ( !(e instanceof InputEvent) )
                        return false;

                    if ( !((InputEvent)e).getType().equals(InputEvent.Type.touchDown) )
                        return false;
                    System.out.println("options pressed");
                    OptionsMenuScreen optionsScreen = new OptionsMenuScreen();
                    this.dispose();
                    MortenCombat.setActiveScreen(optionsScreen);


                    return false;
                }
        );

        Texture buttonTex3 = new Texture( Gdx.files.internal("assets/img/newGame.png") );
        TextureRegion buttonRegion3 = new TextureRegion( buttonTex3 );
        buttonStyle.up = new TextureRegionDrawable( buttonRegion3 );
        Button exit = new Button( buttonStyle );

        exit.setPosition(335, 180);
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

    }
}
