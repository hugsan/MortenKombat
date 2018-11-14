package core.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import core.MortenCombat;
import core.framework.BaseActor;
import core.framework.BaseScreen;

public class OptionsMenuScreen extends BaseScreen {

    @Override
    public void initialize() {

        BaseActor mainMenuBackground = new BaseActor(0,0, mainStage);
        mainMenuBackground.loadTexture("assets/img/menubackground.png");
        mainMenuBackground.setSize(800,600);

        BaseActor title = new BaseActor(100,400, mainStage);
        title.loadTexture("assets/img/MortanKombatLogo.png");
        title.setSize(600,150);

        //back button
        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();

        Texture buttonTex = new Texture( Gdx.files.internal("assets/img/buttons/Start.png") );
        TextureRegion buttonRegion = new TextureRegion(buttonTex);
        buttonStyle.up = new TextureRegionDrawable( buttonRegion );
        Button back = new Button( buttonStyle );

        back.setPosition(335, 100);
        uiStage.addActor(back);

        back.addListener(
                (Event e) ->
                {
                    if ( !(e instanceof InputEvent) )
                        return false;

                    if ( !((InputEvent)e).getType().equals(InputEvent.Type.touchDown) )
                        return false;

                    MainMenuScreen menu = new MainMenuScreen();
                    this.dispose();
                    MortenCombat.setActiveScreen(menu);
                    return false;
                }
        );

        //volumen button

        Texture buttonTex2 = new Texture( Gdx.files.internal("assets/img/buttons/Start.png") );
        TextureRegion buttonRegion2 = new TextureRegion(buttonTex2);
        buttonStyle.up = new TextureRegionDrawable( buttonRegion2 );
        Button volumenUp = new Button( buttonStyle );

        volumenUp.setPosition(235, 300);
        uiStage.addActor(volumenUp);

        volumenUp.addListener(
                (Event e) ->
                {
                    if ( !(e instanceof InputEvent) )
                        return false;

                    if ( !((InputEvent)e).getType().equals(InputEvent.Type.touchDown) )
                        return false;

                    System.out.println("volumen up");
                    return false;
                }
        );

        Texture buttonTex3 = new Texture( Gdx.files.internal("assets/img/buttons/Start.png") );
        TextureRegion buttonRegion3 = new TextureRegion(buttonTex3);
        buttonStyle.up = new TextureRegionDrawable( buttonRegion3 );
        Button volumenDown = new Button( buttonStyle );

        volumenDown.setPosition(435, 300);
        uiStage.addActor(volumenDown);

        volumenDown.addListener(
                (Event e) ->
                {
                    if ( !(e instanceof InputEvent) )
                        return false;

                    if ( !((InputEvent)e).getType().equals(InputEvent.Type.touchDown) )
                        return false;

                    System.out.println("volumen down");
                    return false;
                }
        );
        //difficulty button
        Texture buttonTex4 = new Texture( Gdx.files.internal("assets/img/newGame.png") );
        TextureRegion buttonRegion4 = new TextureRegion(buttonTex4);
        buttonStyle.up = new TextureRegionDrawable( buttonRegion4 );
        Button easy = new Button( buttonStyle );

        easy.setPosition(435, 250);
        uiStage.addActor(easy);

        easy.addListener(
                (Event e) ->
                {
                    if ( !(e instanceof InputEvent) )
                        return false;

                    if ( !((InputEvent)e).getType().equals(InputEvent.Type.touchDown) )
                        return false;

                    System.out.println("Easy");
                    return false;
                }
        );
        Texture buttonTex5 = new Texture( Gdx.files.internal("assets/img/buttons/Start.png") );
        TextureRegion buttonRegion5 = new TextureRegion(buttonTex5);
        buttonStyle.up = new TextureRegionDrawable( buttonRegion5 );
        Button hard = new Button( buttonStyle );

        hard.setPosition(235, 250);
        uiStage.addActor(hard);

        hard.addListener(
                (Event e) ->
                {
                    if ( !(e instanceof InputEvent) )
                        return false;

                    if ( !((InputEvent)e).getType().equals(InputEvent.Type.touchDown) )
                        return false;

                    System.out.println("Hard");
                    return false;
                }
        );

    }

    @Override
    public void update(float dt) {

    }
}
