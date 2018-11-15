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
    Champion fighterWarrior;
    Champion fighterMage;
    Champion fighterSupport;
    testingEnemy testingEnemyOne;
    boolean turn = true;//variable to set the turn of the player, if true is players turn, otherwise enemy turn
    ButtonCreator freshCreatedButton;
    public FightScreen(LevelScreen prev){
        super();
        previousMap = prev;
    }


    public void initialize() {
        //initialize the mapbackground
        BaseActor fightBackground = new BaseActor(0,0, mainStage);
        fightBackground.loadTexture( "assets/img/dungeon.png" );
        fightBackground.setSize(800,600);

        //intialize the actors at the screen
        fighterWarrior = MortenCombat.getFigtherWarrior();
        fighterMage = MortenCombat.getFigtherWarrior();
        fighterSupport = MortenCombat.getFigtherWarrior();

        testingEnemyOne = new testingEnemy();


        //create the buttons
        freshCreatedButton = new ButtonCreator(uiStage, "assets/img/button_attack-one.png");
        Button attackOneButton = freshCreatedButton.getCreatedButton();

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
                    turn = !(turn);
                    return true;
                }
        );

        freshCreatedButton = new ButtonCreator(uiStage, "assets/img/button_attack-one.png");
        Button attackTwoButton = freshCreatedButton.getCreatedButton();

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

                    System.out.println("I have healed my hero, Current HP: "+ fighterWarrior.getHP());
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
        //int enemyThinking = MathUtils.random(1200,2200);
        if (!(turn)){
            fighterWarrior.setHP(fighterWarrior.getHP() - testingEnemyOne.attackOne());
            turn = !(turn);
            System.out.println("we got attacked by enemy");
            System.out.println("Our hero health is: "+ fighterWarrior.getHP());

        }

        if (testingEnemyOne.getHp() <= 0){
            this.dispose();
            MortenCombat.setActiveScreen(previousMap);
        }
        // make if our figther hp <= 0 go to Game over -Screen

    }
}


