package core.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import core.MortenCombat;
import core.actors.fightingactors.*;
import core.framework.BaseActor;
import core.framework.BaseScreen;
import java.util.concurrent.CopyOnWriteArrayList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;


public class FightScreen extends BaseScreen {
    LevelScreen previousMap;
    Champion championOne;
    Champion championTwo;
    Champion championThree;
    ArrayList<Champion> champions;
    ArrayList<EnemyFighters> enemies;
    Champion abilityUser;
    EnemyFighters enemyOne;
    EnemyFighters enemyTwo;
    EnemyFighters enemyThree;
    boolean firstAttack = false;
    boolean secondAttack = false;
    boolean thirdAttack = false;
    private Pixmap defaultMouse;
    private Pixmap spellMouse;
    private static Music battleMusic;
    CopyOnWriteArrayList<Fighter> aliveFighters;
    Stack<Fighter> fightingTurn;
    long currentTime;
    long startTime;
    int turn = 0;

    static private int championOneHP= 666;
    static private int championThreeHP = 666;
    static private int championTwoHP = 666;


    public FightScreen(LevelScreen prev){
        super();
        previousMap = prev;
    }

    public void initialize() {

        // Battle music
        battleMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/audio/music/NoSurrender.mp3"));
        battleMusic.setVolume(MortenCombat.volume);
        battleMusic.setLooping(true);
        battleMusic.play();

        //initialize the mapbackground
        BaseActor fightBackground = new BaseActor(0,0, mainStage);
        fightBackground.loadTexture( "assets/img/dungeon.png" );
        fightBackground.setSize(800,600);
        //initialize the actors at the screen
        if (MortenCombat.fighterN == 1)
            championOne = new WarriorOne(mainStage);
        else
            championOne = new WarriorTwo(mainStage);
        if (MortenCombat.mageN == 1)
            championTwo = new MageOne(mainStage);
        else
            championTwo = new MageTwo(mainStage);
        if (MortenCombat.supportN == 1)
            championThree = new SupportOne(mainStage);
        else
            championThree = new SupportTwo(mainStage);
        importHP();

        champions = new ArrayList<Champion>();

        champions.add(championOne);
        champions.add(championTwo);
        champions.add(championThree);

        enemyOne = new SkeletonFighter(mainStage);
        enemyTwo = new ZombieFighter (mainStage);
        enemyThree = new BatFighter (mainStage);

        enemies = new ArrayList<EnemyFighters>();
        enemies.add(enemyOne);
        enemies.add(enemyTwo);
        enemies.add(enemyThree);

        fightingTurn = new Stack<> ( );
        fightingTurn.addAll ( champions );
        fightingTurn.addAll ( enemies );
        //fightingTurn is a ArrayList of fighters, randomize every turn.
        Collections.shuffle ( fightingTurn );

            System.out.println (fightingTurn.size () );
        //aliveFighters is a arrayList of all the remaining Fighters in our screen.
        aliveFighters = new CopyOnWriteArrayList<> (  );
        aliveFighters.addAll ( fightingTurn );

        //initialize the mouses
        defaultMouse = new Pixmap (Gdx.files.internal("assets/img/NormalMouse.png"));
        spellMouse = new Pixmap (Gdx.files.internal("assets/img/SpellMouse.png"));
        activateDefaultMouse();
        //create the buttons
        uiStage.addActor( championOne.getFirstButton());

        //creating listeners for buttons, activate the spell selector.
        for (Champion c : champions){
            c.getFirstButton().addListener(
                    (Event e) ->
                    {
                        if ( !(e instanceof InputEvent) )
                            return false;
                        if ( !((InputEvent)e).getType().equals(InputEvent.Type.touchDown) )
                            return false;
                        if (c != fightingTurn.peek ())
                            //implement not my turn sound
                            return false;
                        firstAttack = true;
                        secondAttack = false;
                        thirdAttack = false;
                        abilityUser = c;
                        activateSpellMouse ();

                        return true;
                    }
            );
            c.getSecondButton ().addListener(
                    (Event e) ->
                    {
                        if ( !(e instanceof InputEvent) )
                            return false;
                        if ( !((InputEvent)e).getType().equals(InputEvent.Type.touchDown) )
                            return false;
                        if (c != fightingTurn.peek ())
                            return false;
                        secondAttack = true;
                        firstAttack = false;
                        thirdAttack = false;
                        abilityUser = c;
                        activateSpellMouse ();
                        return true;
                    }
            );
            c.getThirdButton ().addListener(
                    (Event e) ->
                    {
                        if ( !(e instanceof InputEvent) )
                            return false;
                        if ( !((InputEvent)e).getType().equals(InputEvent.Type.touchDown) )
                            return false;
                        if (c != fightingTurn.peek ())
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
        //creating the listeners of all the actors. Will be the target of ours spells.
        for (Fighter e : fightingTurn){
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
                            System.out.println ( abilityUser + " we delivered the first attack! target: " + o.getTarget ( ) );
                            abilitySuccess ();
                        }else if (  secondAttack && abilityUser.attackTwo ( (Fighter)o.getTarget () )){
                            System.out.println(abilityUser+" we delivered the second attack! target: "+o.getTarget ());
                            abilitySuccess ();
                        }else if (thirdAttack ){
                            if (o.getTarget() instanceof Champion){
                                if(abilityUser.attackThree(championOne, championTwo, championThree)) {
                                    abilitySuccess ();
                                }
                            }else if (o.getTarget () instanceof EnemyFighters){
                                if (o.getTarget () == enemyOne )
                                    if (abilityUser.attackThree(enemyOne,enemyTwo,enemyThree)){
                                        System.out.println ("hitting third enemy with third ability" );
                                        abilitySuccess ();
                                    }
                                if (o.getTarget () == enemyTwo )
                                    if (abilityUser.attackThree(enemyTwo,enemyOne,enemyThree)){
                                        abilitySuccess ();
                                    }
                                if (o.getTarget () == enemyThree )
                                    if (abilityUser.attackThree(enemyThree,enemyOne,enemyTwo)){
                                        abilitySuccess ();
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
        uiTable.add().height ( 200 ).width ( 25 );
        uiTable.add ( championThree ).height ( 200 ).width ( 116 ); //hero 3 position
        uiTable.add ( championTwo ).height ( 200 ).width ( 116 ); //hero 2 position
        uiTable.add ( championOne ).height ( 200 ).width ( 116 ); //hero 1 possition
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
        if (Gdx.input.isKeyJustPressed (Input.Keys.E))
            System.out.println (fightingTurn.peek() );

        if (fightingTurn.isEmpty ()){
            fightingTurn.addAll(aliveFighters);
            Collections.shuffle(fightingTurn);
            turn ++; // not been used, maybe we can put a Turn number on screen.
        }
        int enemyThinking = MathUtils.random(1200,2200);
        if (fightingTurn.peek() instanceof EnemyFighters){
            // = false;

            while ((currentTime - startTime) < enemyThinking){
                currentTime = System.currentTimeMillis();
            }
            int championTarget = MathUtils.random(0,100);
            //60% chance to hit first target 25% second chance 15% last if the target is dead goes for next
            if (championTarget > 40 && aliveFighters.contains ( championOne )){
                theEnemyAttacks ( (EnemyFighters)fightingTurn.pop(), championOne );
                System.out.println ("we have been attacked to warrior" );
                System.out.println (fightingTurn );
            }
            else if (championTarget > 15 && aliveFighters.contains (championTwo)){
                theEnemyAttacks ( (EnemyFighters)fightingTurn.pop(), championTwo );
                System.out.println ("we have been attacked to mage" );
                System.out.println (fightingTurn );
            }
            else{
                theEnemyAttacks ( (EnemyFighters)fightingTurn.pop(),championThree );
                System.out.println ("we have been attacked to support" );
                System.out.println (fightingTurn );

            }

        }

        if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)){
            activateDefaultMouse ();
            firstAttack = false;
        }
        boolean enemyAlive = true;
        for (Fighter f : aliveFighters){
            if (f instanceof EnemyFighters)
                enemyAlive = false;
        }
        if (enemyAlive){//if all enemys are dead go back to exploring map
            //implement HP and MANA exporting of our characters before leaving the screen
            exportHP();
            this.dispose();
            MortenCombat.setActiveScreen(previousMap);
        }
        for (Fighter f : aliveFighters){
            if (f.getHP ()<= 0){
                aliveFighters.remove ( f );
                fightingTurn.remove ( f );
            }
        }

    }

private void activateDefaultMouse(){
    Gdx.graphics.setCursor(Gdx.graphics.newCursor(defaultMouse, 0, 0));
}

private void activateSpellMouse(){
    Gdx.graphics.setCursor(Gdx.graphics.newCursor(spellMouse, 0, 0));
}
//method that is been used every ability that has been done by our Champions
private void abilitySuccess(){
    activateDefaultMouse ();
    fightingTurn.pop();
    System.out.println (fightingTurn );
    startTime = System.currentTimeMillis();
    firstAttack = secondAttack = thirdAttack = false;
}
private void theEnemyAttacks(EnemyFighters enemy,Fighter fighter){
    int chanceAbility = MathUtils.random(0,100);
    if (chanceAbility >=40)
        enemy.attackOne(fighter);//60% chance to attack with attack1
    else
        enemy.attackTwo(fighter);//40% chance to attack with attack2
}
private void exportHP(){
        championOneHP = championOne.getHP();
        championTwoHP = championTwo.getHP();
        championThreeHP = championThree.getHP();
}
private void importHP(){
        if (championOneHP != 666 && championTwoHP != 666 && championThreeHP != 666){
            championOne.setHP(championOneHP);
            championTwo.setHP(championTwoHP);
            championThree.setHP(championThreeHP);
        }
}
}