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

        // The text for difficulty

        BaseActor title = new BaseActor(200,475, mainStage);
        title.loadTexture("assets/img/buttons/DifficultyText.png");
        title.setSize(400,100);

        // The text for the volume

        BaseActor title1 = new BaseActor(250,100, mainStage);
        title1.loadTexture("assets/img/buttons/Volume.png");
        title1.setSize(300,70);

        //back button

        Button.ButtonStyle buttonStyle1 = new Button.ButtonStyle();

        Texture buttonTex = new Texture( Gdx.files.internal("assets/img/buttons/Back.png") );
        TextureRegion buttonRegion = new TextureRegion(buttonTex);
        buttonStyle1.up = new TextureRegionDrawable( buttonRegion );
        Button back = new Button( buttonStyle1 );

        back.setPosition(25, 25);
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

                    System.out.println("volumen up");
                    return false;
                }
        );

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

                    System.out.println("volumen down");
                    return false;
                }
        );

        //difficulty button 12-16

        Button.ButtonStyle buttonStyle4 = new Button.ButtonStyle();

        Texture buttonTex4 = new Texture( Gdx.files.internal("assets/img/buttons/AgeYoung.png") );
        TextureRegion buttonRegion4 = new TextureRegion(buttonTex4);
        buttonStyle4.up = new TextureRegionDrawable( buttonRegion4 );
        Button easy = new Button( buttonStyle4 );

        easy.setPosition(250, 350);
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

        // Difficulty button 16+

        Button.ButtonStyle buttonStyle5 = new Button.ButtonStyle();

        Texture buttonTex5 = new Texture( Gdx.files.internal("assets/img/buttons/AgeOlder.png") );
        TextureRegion buttonRegion5 = new TextureRegion(buttonTex5);
        buttonStyle5.up = new TextureRegionDrawable( buttonRegion5 );
        Button hard = new Button( buttonStyle5 );

        hard.setPosition(250, 270);
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
