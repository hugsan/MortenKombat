package core.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import core.MortenCombat;
import core.actors.fight.*;
import core.framework.BaseActor;
import core.framework.BaseGame;
import core.framework.BaseScreen;
import java.lang.Thread;
import java.util.concurrent.TimeUnit;


public class FightScreen extends BaseScreen {
    LevelScreen previousMap;
    Fighter fighterOne;
    Enemy enemyOne;
    boolean turn = true; //variable to set the turn of the player, if true is players turn, otherwise enemy turn

    public FightScreen(LevelScreen prev){
        super();
        previousMap = prev;
initialize();
    }


    public void initialize() {
        BaseActor fightBackground = new BaseActor(0,0, mainStage);
        fightBackground.loadTexture( "assets/img/dungeonbackground.jpg" );
        fightBackground.setSize(800,600);
        enemyOne = new Enemy();

        fighterOne = MortenCombat.getFigther();


        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();

        Texture buttonTex = new Texture( Gdx.files.internal("assets/img/button_attack-one.png") );
        TextureRegion buttonRegion =  new TextureRegion(buttonTex);
        buttonStyle.up = new TextureRegionDrawable( buttonRegion );

        Button attackOneButton = new Button( buttonStyle );
        attackOneButton.setColor( Color.CYAN );
        attackOneButton.setPosition(120 ,80);
        uiStage.addActor(attackOneButton);

        attackOneButton.addListener(
                (Event e) ->
                {
                    if ( !(e instanceof InputEvent) )
                        return false;

                    if ( !((InputEvent)e).getType().equals(InputEvent.Type.touchDown) )
                        return false;
                    if (!(turn))
                        return false;
                    System.out.println("I have attack the enemy, enemy current HP: "+enemyOne.getHp());
                    enemyOne.setHp(enemyOne.getHp() - fighterOne.attackOne());
                    //method of launching questions;
                    turn = !(turn);
                    return true;
                }
        );


        Button.ButtonStyle buttonStyle2 = new Button.ButtonStyle();


        Texture buttonTex2 = new Texture( Gdx.files.internal("assets/img/button_attack-two.png") );
        TextureRegion buttonRegion2 =  new TextureRegion(buttonTex2);
        buttonStyle2.up = new TextureRegionDrawable( buttonRegion2 );

        Button attackTwoButton = new Button( buttonStyle2 );
        attackTwoButton.setColor( Color.CYAN );
        attackTwoButton.setPosition(120 ,45);
        uiStage.addActor(attackTwoButton);

        attackTwoButton.addListener(
                (Event e) ->
                {
                    if ( !(e instanceof InputEvent) )
                        return false;

                    if ( !((InputEvent)e).getType().equals(InputEvent.Type.touchDown) )
                        return false;
                    if (!(turn))
                        return false;

                    fighterOne.attachTwo();
                    System.out.println("I have healed my hero, Current HP: "+fighterOne.getHp());
                    turn = !(turn);
                    return true;
                }
        );


    }

    public void update(float dt) {
        // hero movement controls
        // makes the enemy wait 1.3s-2.2 randomly before making a decision.
        if (!(turn)){
            long startThinking = MathUtils.random(1300,2200);

            try
            {
                Thread.sleep(startThinking);
            }
            catch(InterruptedException ex)
            {
                Thread.currentThread().interrupt();
            }

            fighterOne.setHp(fighterOne.getHp() - enemyOne.attackOne());
            turn = !(turn);
            System.out.println("we got attacked by enemy");
            System.out.println("Our hero health is: "+ fighterOne.getHp());

        }

        if (enemyOne.getHp() <= 0){
            this.dispose();
            MortenCombat.setActiveScreen(previousMap);
        }
        // make if our figther hp <= 0 go to Game over -Screen


    }

}


