package core.screen;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import core.MortenCombat;
import core.actors.fightingactors.*;
import core.framework.BaseActor;
import core.framework.BaseScreen;


public class FightScreen extends BaseScreen {
    LevelScreen previousMap;
    testingFigther fighterOne;
    testingEnemy testingEnemyOne;
    boolean turn = true; //variable to set the turn of the player, if true is players turn, otherwise enemy turn

    public FightScreen(LevelScreen prev){
        super();
        previousMap = prev;
    }


    public void initialize() {
        //initialize the mapbackground
        BaseActor fightBackground = new BaseActor(0,0, mainStage);
        fightBackground.loadTexture( "assets/img/dungeon.png" );
        fightBackground.setSize(800,600);
        //initialize the actors at the screen
        fighterOne = MortenCombat.getFigther();
        testingEnemyOne = new testingEnemy();

        //create the buttons
        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();

        Texture buttonTex = new Texture( Gdx.files.internal("assets/img/button_attack-one.png") );
        TextureRegion buttonRegion =  new TextureRegion(buttonTex);
        buttonStyle.up = new TextureRegionDrawable( buttonRegion );

        Button attackOneButton = new Button( buttonStyle );
        attackOneButton.setColor( Color.CYAN );
        //attackOneButton.setPosition(120 ,80);
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
                    System.out.println("I have attack the enemy, enemy current HP: "+ testingEnemyOne.getHp());
                    testingEnemyOne.setHp(testingEnemyOne.getHp() - fighterOne.attackOne());
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
        //attackTwoButton.setPosition(120 ,45);
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

        //put the buttons in the table.
        uiTable.pad(10); // add 10 pixel corner to the screen.
        uiTable.add().height(440).width(25);
        uiTable.add().height(440).width(116).top(); //hero 3 position
        uiTable.add().height(440).width(116); //hero 2 position
        uiTable.add().height(440).width(116); //hero 1 possition
        uiTable.add().height(440).width(56); //space between hero and enemy
        uiTable.add().height(440).width(116); //enemy 1 position
        uiTable.add().height(440).width(116); //enemy 2 position
        uiTable.add().height(440).width(116); //enemy 3 position
        uiTable.add().height(440).width(25);
        //next column for abilities and items.
        uiTable.row();
        uiTable.add().width(25);
        uiTable.add().width(116); // ability 1 section for hero 3
        uiTable.add().width(116); // ability 1 section for hero 2
        uiTable.add(attackOneButton).width(116);// ability 1 section for hero 1
        uiTable.row();
        uiTable.add().width(25);
        uiTable.add().width(116); // ability 2 section for hero 3
        uiTable.add().width(116); // ability 2 section for hero 2
        uiTable.add(attackTwoButton).width(116);// ability 2 section for hero 1
        uiTable.row();
        uiTable.add().width(25);
        uiTable.add().width(116); // ability 3 section for hero 3
        uiTable.add().width(116); // ability 3 section for hero 2
        uiTable.add().width(116);// ability 3 section for hero 1






    }

    public void update(float dt) {
        // hero movement controls
        int enemyThinking = MathUtils.random(1200,2200);
        if (!(turn)){
            try
            {
                Thread.sleep(enemyThinking);
            }
            catch (InterruptedException e)
            {
            }
            fighterOne.setHp(fighterOne.getHp() - testingEnemyOne.attackOne());
            turn = !(turn);
            System.out.println("we got attacked by enemy");
            System.out.println("Our hero health is: "+ fighterOne.getHp());

        }

        if (testingEnemyOne.getHp() <= 0){
            this.dispose();
            MortenCombat.setActiveScreen(previousMap);
        }
        // make if our figther hp <= 0 go to Game over -Screen

    }
    /**
    private Button  buttonGenerator(String imagePath){


        return ourButton;
    }*/

}


