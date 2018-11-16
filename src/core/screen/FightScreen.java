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
    Champion fighterOne;
    testingEnemy testingEnemyOne;
    boolean turn = true; //variable to set the turn of the player, if true is players turn, otherwise enemy turn
    Fighter enemyOne;
    Fighter enemyTwo;
    Fighter enemyThree;

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
        fighterOne = MortenCombat.getFigtherOne();
        enemyOne = new BatFighter();
        enemyTwo = new SkeletonFighter();
        enemyThree = new ZombieFighter();

        //create the buttons
        uiStage.addActor(fighterOne.getFirstButton());

        fighterOne.getFirstButton().addListener(
                (Event e) ->
                {
                    if ( !(e instanceof InputEvent) )
                        return false;

                    if ( !((InputEvent)e).getType().equals(InputEvent.Type.touchDown) )
                        return false;
                    if (!(turn))
                        return false;
                    fighterOne.attackOne(enemyOne);
                    System.out.println("I have attack the enemy, enemy current HP: "+ enemyOne.getHP());

                    //testingEnemyOne.setHp(testingEnemyOne.getHp() - fighterOne.attackOne());
                    turn = !(turn);
                    return true;
                }
        );


        //attackTwoButton.setPosition(120 ,45);
        uiStage.addActor(fighterOne.getSecondButton());

        fighterOne.getSecondButton().addListener(
                (Event e) ->
                {
                    if ( !(e instanceof InputEvent) )
                        return false;

                    if ( !((InputEvent)e).getType().equals(InputEvent.Type.touchDown) )
                        return false;
                    if (!(turn))
                        return false;
                    fighterOne.attackTwo(enemyOne);
                    System.out.println("I have healed my hero, Current HP: "+fighterOne.getHP());
                    turn = !(turn);
                    return true;
                }
        );

        uiStage.addActor(fighterOne.getThirdButton());

        fighterOne.getThirdButton().addListener(
                (Event e) ->
                {
                    if ( !(e instanceof InputEvent) )
                        return false;

                    if ( !((InputEvent)e).getType().equals(InputEvent.Type.touchDown) )
                        return false;
                    if (!(turn))
                        return false;
                    fighterOne.attackTwo(enemyOne);
                    System.out.println("I have healed my hero, Current HP: "+fighterOne.getHP());
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
        uiTable.add(fighterOne.getFirstButton()).width(116);// ability 1 section for hero 1
        uiTable.row();
        uiTable.add().width(25);
        uiTable.add().width(116); // ability 2 section for hero 3
        uiTable.add().width(116); // ability 2 section for hero 2
        uiTable.add(fighterOne.getSecondButton()).width(116);// ability 2 section for hero 1
        uiTable.row();
        uiTable.add().width(25);
        uiTable.add().width(116); // ability 3 section for hero 3
        uiTable.add().width(116); // ability 3 section for hero 2
        uiTable.add(fighterOne.getThirdButton()).width(116);// ability 3 section for hero 1






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
            enemyOne.attackOne(fighterOne);
            turn = !(turn);
            System.out.println("we got attacked by enemy");
            System.out.println("Our hero health is: "+ fighterOne.getHP());

        }

        if (enemyOne.getHP() <= 0){
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


