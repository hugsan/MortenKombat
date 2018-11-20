package core.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import core.MortenCombat;
import core.actors.fightingactors.*;
import core.framework.BaseActor;
import core.framework.BaseScreen;

import java.util.ArrayList;


public class FightScreen extends BaseScreen {
    LevelScreen previousMap;
    Champion championOne;
    Champion championTwo;
    Champion championThree;
    ArrayList<Champion> champions;
    ArrayList<EnemyFighters> enemies;
    Champion abilityUser;
    boolean turn = true; //variable to set the turn of the player, if true is players turn, otherwise enemy turn
    EnemyFighters enemyOne;
    EnemyFighters enemyTwo;
    EnemyFighters enemyThree;
    boolean firstAttack = false;
    boolean secondAttack = false;
    boolean thirdAttack = false;
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
        championOne = new WarriorOne(mainStage);
        championTwo = new MageOne(mainStage);
        championThree = new SupportOne(mainStage);

        champions = new ArrayList<Champion>();

        champions.add(championOne);
        champions.add(championTwo);
        champions.add(championThree);

        enemyOne = new SkeletonFighter(mainStage);
        enemyTwo = new BatFighter ( mainStage );
        enemyThree = new ZombieFighter ( mainStage );

        enemies = new ArrayList<EnemyFighters>();
        enemies.add(enemyOne);
        enemies.add(enemyTwo);
        enemies.add(enemyThree);

        //initialize the mouses
        defaultMouse = new Pixmap (Gdx.files.internal("assets/img/NormalMouse.png"));
        spellMouse = new Pixmap (Gdx.files.internal("assets/img/SpellMouse.png"));
        activateDefaultMouse();
        //create the buttons
        uiStage.addActor( championOne.getFirstButton());

        for (Champion c : champions){
            c.getFirstButton().addListener(
                    (Event e) ->
                    {
                        if ( !(e instanceof InputEvent) )
                            return false;

                        if ( !((InputEvent)e).getType().equals(InputEvent.Type.touchDown) )
                            return false;
                        if (!(turn))
                            return false;
                        firstAttack = true;
                        secondAttack = false;
                        thirdAttack = false;
                        abilityUser = c;
                        activateSpellMouse ();

                        return true;
                    }
            );
        }
        for (Champion c : champions){
            c.getSecondButton ().addListener(
                    (Event e) ->
                    {
                        if ( !(e instanceof InputEvent) )
                            return false;
                        if ( !((InputEvent)e).getType().equals(InputEvent.Type.touchDown) )
                            return false;
                        if (!(turn))
                            return false;
                        secondAttack = true;
                        firstAttack = false;
                        thirdAttack = false;
                        abilityUser = c;
                        activateSpellMouse ();
                        return true;
                    }
            );
        }
        for (Champion c : champions){
            c.getThirdButton ().addListener(
                    (Event e) ->
                    {
                        if ( !(e instanceof InputEvent) )
                            return false;
                        if ( !((InputEvent)e).getType().equals(InputEvent.Type.touchDown) )
                            return false;
                        if (!(turn))
                            return false;
                        thirdAttack = true;
                        firstAttack = false;
                        secondAttack = false;
                        abilityUser = c;
                        activateSpellMouse ();
                        return true;
                    }
            );

        }
        for (EnemyFighters e : enemies){
            e.addListener(
                    (Event o) ->
                    {
                        if ( !(o instanceof InputEvent) )
                            return false;
                        if ( !((InputEvent)o).getType().equals(InputEvent.Type.touchDown) ){
                            return false;
                        }
                        if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT))
                            return false;

                        if (firstAttack && abilityUser.attackOne((Fighter)o.getTarget ())) {
                            firstAttack = false;
                            System.out.println ( abilityUser + " we delivered the first attack! target: " + o.getTarget ( ) );
                            turn = !turn;
                            activateDefaultMouse ( );

                        }else if ( abilityUser.attackTwo ( (Fighter)o.getTarget () ) && secondAttack){
                            secondAttack = false;
                            System.out.println(abilityUser+" we delivered the second attack! target: "+o.getTarget ());
                            turn = !turn;
                            activateDefaultMouse ();

                        }else if (thirdAttack ){
                            if (o.getTarget() instanceof Champion){
                                if(abilityUser.attackThree(championOne, championTwo, championThree)) {
                                    thirdAttack = false;
                                    turn =! turn;
                                    activateDefaultMouse ();
                                }
                            }else if (o.getTarget () instanceof EnemyFighters){
                                if (o.getTarget () == enemyOne )
                                    if (abilityUser.attackThree(enemyOne,enemyTwo,enemyThree)){
                                        System.out.println ("hitting third enemy with third ability" );
                                        thirdAttack = false;
                                        turn =! turn;
                                        activateDefaultMouse ();
                                    }
                                if (o.getTarget () == enemyTwo )
                                    if (abilityUser.attackThree(enemyTwo,enemyOne,enemyThree)){
                                        thirdAttack = false;
                                        turn =! turn;
                                        activateDefaultMouse ();
                                    }
                                if (o.getTarget () == enemyThree )
                                    if (abilityUser.attackThree(enemyThree,enemyOne,enemyTwo)){
                                        thirdAttack = false;
                                        turn =! turn;
                                        activateDefaultMouse ();
                                    }
                            }
                        }
                        else return false;
                        return true;
                    }
            );

        }

        //put the buttons in the table.
        //uiTable.pad ( 25 ); // add 10 pixel corner to the screen.
        uiTable.add ( ).height ( 200 ).width ( 25 );
        uiTable.add ( ).height ( 200 ).width ( 116 );
        uiTable.add ( ).height ( 200 ).width ( 116 );
        uiTable.add ( ).height ( 200 ).width ( 116 );
        uiTable.add ( ).height ( 200 ).width ( 56 );
        uiTable.add ( ).height ( 200 ).width ( 116 );
        uiTable.add ( ).height ( 200 ).width ( 116 );
        uiTable.add ( ).height ( 200 ).width ( 116 );
        uiTable.add ( ).height ( 200 ).width ( 25 );
        uiTable.row ();
        uiTable.add (championOne ).height ( 200 ).width ( 116 ).top ( ); //hero 3 position
        uiTable.add (championTwo ).height ( 200 ).width ( 116 ); //hero 2 position
        uiTable.add (championThree ).height ( 200 ).width ( 116 ); //hero 1 possition
        uiTable.add ( ).height ( 200 ).width ( 56 ); //space between hero and enemy
        uiTable.add ( enemyOne ).height ( 200 ).width ( 116 ); //enemy 1 position
        uiTable.add ( enemyTwo ).height ( 200 ).width ( 116 ); //enemy 2 position
        uiTable.add ( enemyThree ).height ( 200 ).width ( 116 ); //enemy 3 position
        uiTable.add ( ).height ( 200 ).width ( 25 );
        //next column for abilities and items.
        uiTable.row ( );
        uiTable.add ( ).width ( 25 );
        uiTable.add ( championThree.getFirstButton ()).width ( 115 ); // ability 1 section for hero 3
        uiTable.add ( championTwo.getFirstButton ()).width ( 115 ); // ability 1 section for hero 2
        uiTable.add ( championOne.getFirstButton ( ) ).width ( 115 );// ability 1 section for hero 1
        uiTable.row ( );
        uiTable.add ( ).width( 25 );
        uiTable.add ( championThree.getSecondButton ( ) ).width ( 115 ); // ability 2 section for hero 3
        uiTable.add ( championTwo.getSecondButton () ).width ( 115 ); // ability 2 section for hero 2
        uiTable.add ( championOne.getSecondButton ( ) ).width ( 115 );// ability 2 section for hero 1
        uiTable.row ( );
        uiTable.add ( ).width ( 25 );
        uiTable.add ( championThree.getThirdButton ()).width ( 115 ); // ability 3 section for hero 3
        uiTable.add ( championTwo.getThirdButton ()).width ( 115 ); // ability 3 section for hero 2
        uiTable.add ( championOne.getThirdButton ( ) ).width ( 115 );// ability 3 section for hero 1


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
            enemyOne.attackOne( championOne );
            turn = !(turn);
            System.out.println ("enemyTwo HP: "+enemyTwo.getHP() );
            System.out.println("we got attacked by enemy");
            System.out.println("Our hero health is: "+ championOne.getHP());

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