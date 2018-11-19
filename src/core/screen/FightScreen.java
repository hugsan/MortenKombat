package core.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import core.MortenCombat;
import core.actors.fightingactors.*;
import core.framework.BaseActor;
import core.framework.BaseScreen;


public class FightScreen extends BaseScreen {
    LevelScreen previousMap;
    Champion fighterOne;
    boolean turn = true; //variable to set the turn of the player, if true is players turn, otherwise enemy turn
    EnemyFighters enemyOne;
    EnemyFighters enemyTwo;
    EnemyFighters enemyThree;
    boolean firstAttack = false;
    private Pixmap defaultMouse;
    private Pixmap spellMouse;


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
        fighterOne = new WarriorOne(mainStage);
        enemyOne = new BatFighter(mainStage);
        enemyTwo = new SkeletonFighter(mainStage);
        enemyThree = new ZombieFighter(mainStage);

        //initialize the mouses
        defaultMouse = new Pixmap (Gdx.files.internal("assets/img/NormalMouse.png"));
        spellMouse = new Pixmap (Gdx.files.internal("assets/img/SpellMouse.png"));
        activateDefaultMouse ();
        //activateDefaultMouse ();
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
                    firstAttack = true;
                    activateSpellMouse ();
                    System.out.println("we need a target please!");

                    return true;
                }
        );
        enemyTwo.addListener(
                (Event o) ->
                {
                    if ( !(o instanceof InputEvent) )
                        return false;

                    if ( !((InputEvent)o).getType().equals(InputEvent.Type.touchDown) ){
                        return false;
                    }
                    if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT))
                        return false;
                    if (firstAttack ){
                        fighterOne.attackOne(enemyTwo);
                        firstAttack = false;
                        System.out.println("we delivered the attack! viva");
                        turn = !turn;
                        activateDefaultMouse ();
                    }else
                        return false;

                    return true;
                }
        );


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
        uiTable.pad ( 10 ); // add 10 pixel corner to the screen.
        uiTable.add ( ).height ( 440 ).width ( 25 );
        uiTable.add ( ).height ( 440 ).width ( 116 ).top ( ); //hero 3 position
        uiTable.add ( ).height ( 440 ).width ( 116 ); //hero 2 position
        uiTable.add ( ).height ( 440 ).width ( 116 ); //hero 1 possition
        uiTable.add ( ).height ( 440 ).width ( 56 ); //space between hero and enemy
        uiTable.add ( ).height ( 440 ).width ( 116 ); //enemy 1 position
        uiTable.add ( enemyTwo ).height ( 440 ).width ( 116 ); //enemy 2 position
        uiTable.add ( ).height ( 440 ).width ( 116 ); //enemy 3 position
        uiTable.add ( ).height ( 440 ).width ( 25 );
        //next column for abilities and items.
        uiTable.row ( );
        uiTable.add ( ).width ( 25 );
        uiTable.add ( ).width ( 116 ); // ability 1 section for hero 3
        uiTable.add ( ).width ( 116 ); // ability 1 section for hero 2
        uiTable.add ( fighterOne.getFirstButton ( ) ).width ( 116 );// ability 1 section for hero 1
        uiTable.row ( );
        uiTable.add ( ).width ( 25 );
        uiTable.add ( ).width ( 116 ); // ability 2 section for hero 3
        uiTable.add ( ).width ( 116 ); // ability 2 section for hero 2
        uiTable.add ( fighterOne.getSecondButton ( ) ).width ( 116 );// ability 2 section for hero 1
        uiTable.row ( );
        uiTable.add ( ).width ( 25 );
        uiTable.add ( ).width ( 116 ); // ability 3 section for hero 3
        uiTable.add ( ).width ( 116 ); // ability 3 section for hero 2
        uiTable.add ( fighterOne.getThirdButton ( ) ).width ( 116 );// ability 3 section for hero 1


    }

    public void update(float dt) {
        //creating the multiplexer for handling events.
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
        if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)){
            System.out.println("setting attack to false");
            activateDefaultMouse ();
            firstAttack = false;
        }

        if (enemyOne.getHP() <= 0){
            this.dispose();
            MortenCombat.setActiveScreen(previousMap);
        }
        // make if our figther hp <= 0 go to Game over -Screen

    }


private void activateDefaultMouse(){
    Gdx.graphics.setCursor(Gdx.graphics.newCursor(defaultMouse, 0, 0));
}

private void activateSpellMouse(){
    Gdx.graphics.setCursor(Gdx.graphics.newCursor(spellMouse, 0, 0));
}

}


