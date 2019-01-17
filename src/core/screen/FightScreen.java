package core.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import core.utils.MortenKombat;
import core.actors.fightingactors.*;
import core.framework.BaseActor;
import core.framework.BaseScreen;

import java.util.ArrayDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import core.utils.ImportQandA;
import core.framework.BaseGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

/**
 * Screen that is been used to make a fight between the champions and the enemies.
 * Is a turn based fight, were each turn has a chance to activate a trivia question.
 */
public class FightScreen extends BaseScreen {
    private final int TRIVIATOBECHECK = -1;
    private final int TRIVIAWAITINGFORANSWER = 0;
    private final int TRIVIAHASBEENANSWER = 1;
    //variable that determine what is the chance to pop a trivia question on each turn.
    //For example if the value is 3 that means that we have a 30% chance to pop the trivia.
    private final int TRIVIACHANCE = 3;
    //Turn variables
    private ArrayList<EnemyFighters> enemies;
    private ArrayList<Champion> champions;
    private int enemyThinking = MathUtils.random(3500,4500);
    //CopyOnWriteArrayList is a threat safe ArrayList.
    private CopyOnWriteArrayList<Fighter> aliveFighters;
    //ArrayDeque is a threat safe Stack.
    ArrayDeque<Fighter> fightingTurn;
    private int turn = 0;
    //Trivia variables
    private boolean criticalAttack = false;
    private int triviaMustBeShown = TRIVIATOBECHECK;
    private DialogBox questionBox;
    private TextButton answerButton1, answerButton2, answerButton3, answerButton4;
    private DialogBox triviaInformation;

    private boolean isAnswerButton1Pushed=false;
    private boolean isAnswerButton2Pushed=false;
    private boolean isAnswerButton3Pushed=false;
    private boolean isAnswerButton4Pushed=false;

    private boolean isCorrectAnswer = false;

    private Stack<ImportQandA> qA;
    private ArrayList<String> answers;

    private long startTime2;
    private long currentTime2;
    static int randomInt=0;

    private int triviaHasCheck = -1;
    private boolean isTriviaAttack = false;
    //Labels and buttons variables
    private Pixmap defaultMouse, spellMouse;
    private Label tooltipText;
    private Label turnLabel;

    //Fighter variable
    static int amountOfEnemies;
    private Champion championOne, championTwo, championThree;
    private EnemyFighters enemyOne, enemyTwo, enemyThree;

    static private int championOneHP= 666;
    static private int championThreeHP = 666;
    static private int championTwoHP = 666;

    //animation and action related variables
    private long currentTime;
    private long startTime = System.currentTimeMillis();
    private Champion abilityUser;
    private Fighter attacker = null; //variable to know when someone have attacked to make the animation
    private boolean firstAttack = false;
    private boolean secondAttack = false;
    private boolean thirdAttack = false;
    private Fighter killingTarget;
    private boolean killHim = false;
    private long deadAnimationStart;

    //ScreenVariables
    private ExploringScreen previousMap;
    private Sound cantclick = Gdx.audio.newSound(Gdx.files.internal("assets/audio/sound/cantclick.mp3"));
    public static Music battleMusic;
    private static int countKeys = 0;

    /**
     * Constructor for FightScreen.
     * @param prev reference where we want to return when the fight is over in our fightSCreen.
     */
    public FightScreen(ExploringScreen prev)  {
        super();
        previousMap = prev;
    }

    /**
     * Initialize the class fightingScreen when create it.
     * Will initialize the next object in our screen class:
     *    - Put background music, and background picture.
     *    - Create our champions reading witch ones should be created from MortenKombat class.
     *    - Create random enemies. The amount of enemies depend on amountOfEnemies.
     *    - Initializes the data structure to be able to make the Turns in our game.
     *    - Create the listeners for our button abilities and listeners for fighters.
     *    - Create a table and position all the objects that we need in our interface.
     *    - Initialize the dialog and textButtons for our trivia questions.
     */
    public void initialize() {

        // Battle music
        battleMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/audio/music/NoSurrender.mp3"));
        battleMusic.setVolume(MortenKombat.volume);
        battleMusic.setLooping(true);
        battleMusic.play();

        //initialize the mapbackground
        BaseActor fightBackground = new BaseActor(0,0, mainStage);
        fightBackground.loadTexture( "assets/img/dungeon.png" );
        fightBackground.setSize(800,600);

        //initialize the tooltip label.
        tooltipText = new Label("", BaseGame.labelStyle);
        tooltipText.setFontScale(0.5f);
        tooltipText.setColor(Color.LIGHT_GRAY);
        tooltipText.setPosition(430,80);
        tooltipText.setSize(300,50);
        tooltipText.setWrap(true);
        uiStage.addActor(tooltipText);

        //initialize the turn label
        turnLabel = new Label( "Turn: "+turn, BaseGame.labelStyle);
        turnLabel.setColor( Color.GRAY);
        turnLabel.setPosition(320,520);
        uiStage.addActor(turnLabel);

        //Code that create the buttons, and dialogs for our trivia questions.
        qA = MortenKombat.questionAnswer;

        questionBox= new DialogBox(100,460,uiStage);
        questionBox.setBackgroundColor(Color.BLACK);
        questionBox.setFontColor(Color.GOLDENROD);
        questionBox.setDialogSize(600,100);
        questionBox.setFontScale(0.80f);
        questionBox.alignCenter();
        questionBox.setText(qA.peek().question);
        questionBox.setVisible(false);

        triviaInformation = new DialogBox (100, 10, uiStage);
        triviaInformation.setBackgroundColor(Color.DARK_GRAY);
        triviaInformation.setFontColor(Color.LIGHT_GRAY);
        triviaInformation.setDialogSize(600,150);
        triviaInformation.setFontScale(0.50f);
        triviaInformation.alignCenter();
        triviaInformation.setVisible(false);

        BaseGame.textButtonStyle.fontColor = Color.WHITE;

        answerButton1= new TextButton("",BaseGame.textButtonStyle);
        answerButton2= new TextButton("",BaseGame.textButtonStyle);
        answerButton3= new TextButton("",BaseGame.textButtonStyle);
        answerButton4= new TextButton("",BaseGame.textButtonStyle);

        answerButton1.addAction(Actions.alpha(0.8f,0.8f));
        answerButton2.addAction(Actions.alpha(0.8f,0.8f));
        answerButton3.addAction(Actions.alpha(0.8f,0.8f));
        answerButton4.addAction(Actions.alpha(0.8f,0.8f));

        answers= new ArrayList<String>();

        answers.add(qA.peek().correctAnswer);
        answers.add(qA.peek().wrongAnswer1);
        answers.add(qA.peek().wrongAnswer2);
        answers.add(qA.peek().wrongAnswer3);

        Collections.shuffle(answers);

        setAnswerButton(answerButton1,answers.get(0),100,275,300);
        setAnswerButton(answerButton2,answers.get(1),100,175,300);
        setAnswerButton(answerButton3,answers.get(2),400,275,300);
        setAnswerButton(answerButton4,answers.get(3),400,175,300);

        //initialize the actors at the screen, depending on the selectionScreen, read by a static variable in
        //MortenKombat
        if (MortenKombat.fighterN == 1) {
            championOne = new WarriorOne(mainStage);
        }
        else {
            championOne = new WarriorTwo(mainStage);
        }
        if (MortenKombat.mageN == 1)
            championTwo = new MageOne(mainStage);
        else
            championTwo = new MageTwo(mainStage);
        if (MortenKombat.supportN == 1)
            championThree = new SupportOne(mainStage);
        else
            championThree = new SupportTwo(mainStage);
        importHP();

        champions = new ArrayList<Champion>();
        champions.add(championOne);
        champions.add(championTwo);
        champions.add(championThree);

        enemies = new ArrayList<EnemyFighters>();
        if (amountOfEnemies >= 1){
            enemyOne = createRandomEnemy();
            enemies.add(enemyOne);
        }
        if (amountOfEnemies >= 2){
            enemyTwo = createRandomEnemy();
            enemies.add(enemyTwo);
        }
        if (amountOfEnemies >= 3){
            enemyThree = createRandomEnemy();
            enemies.add(enemyThree);
        }
        if(amountOfEnemies == -1){
            enemyOne =  new LeneFighter ( mainStage );
            enemies.add(enemyOne);
        }
        if (amountOfEnemies == -2){
            enemyOne =  new JohanFighter ( mainStage);
            enemies.add(enemyOne);
        }
        if (amountOfEnemies == -3){
            enemyOne =  new SokolFighter ( mainStage);
            enemies.add(enemyOne);
        }
        if (amountOfEnemies == -4){
            enemyOne =  new MortenFighter ( mainStage);
            enemies.add(enemyOne);
        }

        fightingTurn = new ArrayDeque<> ( );
        fightingTurn.addAll ( champions );
        fightingTurn.addAll ( enemies );
        //fightingTurn is a ArrayList of fighters, randomize every turn.
        shuffleDeque(fightingTurn);
        //aliveFighters is a arrayList of all the remaining Fighters in our screen.
        //A thread-safe variant of ArrayList in which all mutative operations (add, set, and so on) are implemented
        // by making a fresh copy of the underlying array. (copied from Oracle)
        aliveFighters = new CopyOnWriteArrayList<> (  );
        aliveFighters.addAll ( fightingTurn );

        //initialize the mouses
        defaultMouse = new Pixmap (Gdx.files.internal("assets/img/NormalMouse.png"));
        spellMouse = new Pixmap (Gdx.files.internal("assets/img/SpellMouse.png"));
        activateDefaultMouse();

        //creating listeners for buttons, activate the spell selector.
        for (Champion c : champions){
            c.getFirstButton().addListener(
                    (Event e) ->
                    {//making sure that only the fighter with the turn can listen to clicks.
                        if (c != safeFighterStackPeek()) {
                            return false;
                        }
                        tooltipText.setText(c.getSpellOneText());
                        if ( !(e instanceof InputEvent) ){
                            return false;
                        }
                        if ( !((InputEvent)e).getType().equals(InputEvent.Type.touchDown) ) {
                            return false;
                        }
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
                        if (c != safeFighterStackPeek())
                            return false;
                        tooltipText.setText(c.getSpellTwoText());
                        if ( !(e instanceof InputEvent) )
                            return false;
                        if ( !((InputEvent)e).getType().equals(InputEvent.Type.touchDown) )
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
                        if (c != safeFighterStackPeek())
                            return false;
                        tooltipText.setText(c.getSpellThreeText());
                        if ( !(e instanceof InputEvent) )
                            return false;
                        if ( !((InputEvent)e).getType().equals(InputEvent.Type.touchDown) )
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
                        //if you dont put this line, right click would also trigger the spells. and we only
                        //want to trigger the spells with left mouse click.
                        if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT))
                            return false;
                        if (!aliveFighters.contains(o.getTarget())){
                            //check if the target is dead before accepting the event
                            cantclick.play();
                            return false;
                        }
                        if (firstAttack){
                            //if the first condition is false, the second condition is never check and never executed
                            if (!isTriviaAttack && abilityUser.attackOne((Fighter)o.getTarget ()))
                                abilitySuccess(abilityUser);
                            if (isTriviaAttack  && abilityUser.attackOne((Fighter)o.getTarget ())){
                                abilityUser.attackOne((Fighter)o.getTarget ());
                                abilitySuccess(abilityUser);
                            }
                        }else if (  secondAttack ){
                            if (!isTriviaAttack && abilityUser.attackTwo ( (Fighter)o.getTarget () ))
                                abilitySuccess(abilityUser);
                            if (isTriviaAttack   && abilityUser.attackTwo((Fighter)o.getTarget ())){
                                abilityUser.attackTwo((Fighter)o.getTarget ());
                                abilitySuccess(abilityUser);
                            }
                        }else if (thirdAttack ){
                            if (o.getTarget() instanceof Champion){
                                if(!isTriviaAttack && abilityUser.attackThree(championOne, championTwo, championThree)) {
                                    abilitySuccess (abilityUser);
                                }
                                if (isTriviaAttack  && abilityUser.attackThree(championOne, championTwo, championThree)){
                                    abilityUser.attackThree(championOne, championTwo, championThree);
                                    abilitySuccess(abilityUser);
                                }
                            //when clicking on a enemy with the third attack we make sure that the target is been passed
                                //as tge first argument for the attackThree method.
                            }else if (o.getTarget () instanceof EnemyFighters){
                                if (o.getTarget () == enemyOne )
                                    if (!isTriviaAttack && abilityUser.attackThree(enemyOne,enemyTwo,enemyThree)){
                                        abilitySuccess (abilityUser);
                                    }
                                    if (isTriviaAttack   && abilityUser.attackThree(enemyOne,enemyTwo,enemyThree)){
                                        abilityUser.attackThree(enemyOne,enemyTwo,enemyThree);
                                        abilitySuccess(abilityUser);
                                    }
                                if (o.getTarget () == enemyTwo )
                                    if (!isTriviaAttack && abilityUser.attackThree(enemyTwo,enemyOne,enemyThree)){
                                        abilitySuccess (abilityUser);
                                    }
                                if (isTriviaAttack   && abilityUser.attackThree(enemyTwo,enemyOne,enemyThree)){
                                    abilityUser.attackThree(enemyTwo,enemyOne,enemyThree);
                                    abilitySuccess(abilityUser);
                                }
                                if (o.getTarget () == enemyThree )
                                    if (!isTriviaAttack && abilityUser.attackThree(enemyThree,enemyTwo,enemyOne)){
                                        abilitySuccess (abilityUser);
                                    }
                                if (isTriviaAttack   && abilityUser.attackThree(enemyThree,enemyTwo,enemyOne)){
                                    abilityUser.attackThree(enemyThree,enemyTwo,enemyOne);
                                    abilitySuccess(abilityUser);
                                }
                            }
                        }
                        else return false;
                        return true;
                    }
            );
        }

        //Create the listeners for the trivia question answer buttons.
        answerButton1.addListener(
                (Event e) ->
                {
                    if ( !(e instanceof InputEvent) )
                        return false;
                    if ( !((InputEvent)e).getType().equals(InputEvent.Type.touchDown) )
                        return false;

                    isAnswerButton1Pushed=true;
                    startTime2=System.currentTimeMillis();

                    return true;
                }
        );
        answerButton2.addListener(
                (Event e) ->
                {
                    if ( !(e instanceof InputEvent) )
                        return false;
                    if ( !((InputEvent)e).getType().equals(InputEvent.Type.touchDown) )
                        return false;

                    isAnswerButton2Pushed=true;
                    startTime2=System.currentTimeMillis();

                    return true;
                }
        );
        answerButton3.addListener(
                (Event e) ->
                {
                    if ( !(e instanceof InputEvent) )
                        return false;
                    if ( !((InputEvent)e).getType().equals(InputEvent.Type.touchDown) )
                        return false;
                    isAnswerButton3Pushed=true;
                    startTime2=System.currentTimeMillis();

                    return true;
                }
        );
        answerButton4.addListener(
                (Event e) ->
                {
                    if ( !(e instanceof InputEvent) )
                        return false;
                    if ( !((InputEvent)e).getType().equals(InputEvent.Type.touchDown) )
                        return false;

                    isAnswerButton4Pushed=true;
                    startTime2=System.currentTimeMillis();

                    return true;
                }
        );

        qaStage.addActor(questionBox);
        qaStage.addActor(answerButton1);
        qaStage.addActor(answerButton2);
        qaStage.addActor(answerButton3);
        qaStage.addActor(answerButton4);

        //table where we structure the position of our heroes, buttons, labels ...
        uiTable.add ( ).height ( 140 ).width ( 25 );
        uiTable.add ( ).height ( 140 ).width ( 116 );
        uiTable.add ( ).height ( 140 ).width ( 116 );
        uiTable.add ( ).height ( 140 ).width ( 116 );
        uiTable.add ( ).height ( 140 ).width ( 56 );
        uiTable.add ( ).height ( 140 ).width ( 116 );
        uiTable.add ( ).height ( 140 ).width ( 116 );
        uiTable.add ( ).height ( 140 ).width ( 116 );
        uiTable.add ( ).height ( 140 ).width ( 25 );
        uiTable.row ();
        //nameplate of the hero
        uiTable.add ( ).height( 20 ).width( 25 );
        uiTable.add ( championThree.getFighterNamePlate() ).height( 20 ).width( 110 ); //hero 3 nameplate
        uiTable.add ( championTwo.getFighterNamePlate() ).height( 20 ).width( 110 ); //hero 2 nameplate
        championOne.getFighterNamePlate().setWrap(true);
        uiTable.add ( championOne.getFighterNamePlate() ).height( 20 ).width( 110 ); //hero 1 nameplate
        uiTable.add ( ).height( 20 ).width( 56 );
        if (enemyOne != null)
            uiTable.add (  enemyOne.getFighterNamePlate() ).height( 20 ).width( 110 ); //enemy 1 nameplate
        else
            uiTable.add (   ).height( 20 ).width( 110 ); //enemy 1 nameplate
        if (enemyTwo != null)
            uiTable.add ( enemyTwo.getFighterNamePlate() ).height( 20 ).width( 110 ); //enemy 2 nameplate
        else
            uiTable.add (  ).height( 20 ).width( 110 ); //enemy 2 nameplate
        if (enemyThree != null)
            uiTable.add ( enemyThree.getFighterNamePlate() ).height( 20 ).width( 110 ); // enemy 3 nameplate
        else
            uiTable.add (  ).height( 20 ).width( 110 ); // enemy 3 nameplate
        uiTable.add ( ).height( 20 ).width( 25 );
        uiTable.row ();
        //HPBar row
        uiTable.add ( ).height( 20 ).width( 25 );
        uiTable.add ( championThree.getHPBar() ).height( 20 ).width( 110 ); //hero 3 hpbar
        uiTable.add ( championTwo.getHPBar() ).height( 20 ).width( 110 ); //hero 2 hpbar
        uiTable.add ( championOne.getHPBar() ).height( 20 ).width( 110 ); //hero 1 hpbar
        uiTable.add ( ).height( 20 ).width( 56 );
        if (enemyOne != null)
            uiTable.add (  enemyOne.getHPBar() ).height( 20 ).width( 110 ); //enemy 1 HP bar
        else
            uiTable.add (   ).height( 20 ).width( 110 ); //enemy 1 HP bar
        if (enemyTwo != null)
            uiTable.add ( enemyTwo.getHPBar() ).height( 20 ).width( 110 ); //enemy 2 HP bar
        else
            uiTable.add (  ).height( 20 ).width( 110 ); //enemy 2 HP bar
        if (enemyThree != null)
            uiTable.add ( enemyThree.getHPBar() ).height( 20 ).width( 110 ); // enemy 3 HP bar
        else
            uiTable.add (  ).height( 20 ).width( 110 ); // enemy 3 HP bar
        uiTable.add ( ).height( 20 ).width( 25 );
        uiTable.row ();
        //ManaBar row
        uiTable.add ( ).height( 20 ).width( 25 );
        uiTable.add ( championThree.getManaBar() ).height( 20 ).width( 110 ); //hero 3 manabar
        uiTable.add ( championTwo.getManaBar() ).height( 20 ).width( 110 ); //hero 2 manabar
        uiTable.add ( ).height( 20 ).width( 110 );
        uiTable.add ( ).height( 20 ).width( 56 );
        uiTable.add ( ).height( 20 ).width( 110 );
        uiTable.add ( ).height( 20 ).width( 110 );
        uiTable.add ( ).height( 20 ).width( 110 );
        uiTable.add ( ).height( 20 ).width( 25 );
        uiTable.row ();
        //Heroes row
        uiTable.add().height ( 200 ).width ( 25 );
        uiTable.add ( championThree ).height ( 200 ).width ( 116 ); //hero 3 position
        uiTable.add ( championTwo ).height ( 200 ).width ( 116 ); //hero 2 position
        uiTable.add ( championOne ).height ( 200 ).width ( 116 ); //hero 1 position
        uiTable.add ( ).height ( 200 ).width ( 50 ); //space between hero and enemy
        uiTable.add ( enemyOne ).height ( 200 ).width ( 116 ); //enemy 1 position
        uiTable.add ( enemyTwo ).height ( 200 ).width ( 116 ); //enemy 2 position
        uiTable.add ( enemyThree ).height ( 200 ).width ( 116 ); //enemy 3 position
        uiTable.add ( ).height ( 200 ).width ( 25 );
        //next column for abilities and items.
        uiTable.row ( );
        uiTable.add ( ).width ( 25 );

        uiTable.add ( championThree.getFirstButton ()).height( 50 ).width ( 115 ); // ability 1 section for hero 3
        uiTable.add ( championTwo.getFirstButton ()).height( 50 ).width ( 115 ); // ability 1 section for hero 2
        uiTable.add ( championOne.getFirstButton ()).height( 50 ).width ( 115 );// ability 1 section for hero 1
        uiTable.row ( );
        uiTable.add ( ).width( 25 );
        uiTable.add ( championThree.getSecondButton ()).height( 50 ).width ( 115 ); // ability 2 section for hero 3
        uiTable.add ( championTwo.getSecondButton ()).height( 50 ).width ( 115 ); // ability 2 section for hero 2
        uiTable.add ( championOne.getSecondButton ()).height( 50 ).width ( 115 );// ability 2 section for hero 1
        uiTable.row ( );
        uiTable.add ( ).width ( 25 );
        uiTable.add ( championThree.getThirdButton ()).height( 50 ).width ( 115 ); // ability 3 section for hero 3
        uiTable.add ( championTwo.getThirdButton ()).height( 50 ).width ( 115 ); // ability 3 section for hero 2
        uiTable.add ( championOne.getThirdButton ()).height( 50 ).width ( 115 );// ability 3 section for hero 1
        uiTable.row ( );


        for (Fighter f : fightingTurn){
            f.sizeBy(60);

            f.setAux ( f.getHP () );
        }
        hideAllButtons(champions);
    }

    /**
     * Method that will periodically check if there is any changes in our game. Update is a threat method, and need
     * to be aware of it when using data structure making them threat safe. Our Update method runs once per frame, if
     * we do not have any frame drop we should run 60 update "checks" every second. In our update we check for the next:
     *    - Check if we should trigger the trivia for the champion turns.
     *    - Updates manaBars, healthBars, turn labels.
     *    - Check for conditions if all enemies or all champions are dead and changing to another screen if "true".
     *    - Check if it is enemy turn, and randomize a attack to a champion (also check if this need to be a trivia attack).
     *    - Changes animations of fighters when they attack or die.
     * @param dt deltaTime for the update. Delta time means the time that has passes between frames in our game.
     */
    public void update(float dt) {
        boolean isAllEnemyDead = true;
        boolean isAllHeroesDead = true;
        //escape that should implement a pauseScreen
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            MortenKombat.setActiveScreen(new PauseScreen(this));
        }

        if (!fightingTurn.isEmpty () && fightingTurn.peek () instanceof Champion ){
            Champion temporalChampion = (Champion)fightingTurn.peek ();

            if (temporalChampion.stun){
                temporalChampion.setAnimationPaused ( false );
                temporalChampion.stun = false;
                safeFighterStackPop ();
            }
            isFightTurnOver ();

        }




        //if the turn is over (means there is no more object in the stack), we create a new turn by feeding the stack
        //with alivefighter Arraylist
        caseOfAnswerButton1(isAnswerButton1Pushed);
        caseOfAnswerButton2(isAnswerButton2Pushed);
        caseOfAnswerButton3(isAnswerButton3Pushed);
        caseOfAnswerButton4(isAnswerButton4Pushed);

        if (triviaHasCheck <= TRIVIAWAITINGFORANSWER && safeFighterStackPeek() instanceof Champion ){
            if (MathUtils.random(0,9)< TRIVIACHANCE && triviaHasCheck == TRIVIATOBECHECK){//if true its a trivia question
                triviaHasCheck = TRIVIAWAITINGFORANSWER ;
                showTrivia();
                isTriviaAttack = true;
            }else if (triviaHasCheck == TRIVIATOBECHECK){//it is not a trivia question
                triviaHasCheck = TRIVIAHASBEENANSWER;
                isTriviaAttack = false;
            }
            if (triviaHasCheck == TRIVIAWAITINGFORANSWER && (isAnswerButton1Pushed || isAnswerButton2Pushed ||
                    isAnswerButton3Pushed || isAnswerButton4Pushed) ){
                stateOfAnswer(isAnswerButton1Pushed,isAnswerButton2Pushed,isAnswerButton3Pushed,isAnswerButton4Pushed);
                triviaHasCheck = TRIVIAHASBEENANSWER;
                if (!isCorrectAnswer)
                    abilityNotSuccess();
            }
        }
        for (Fighter f : aliveFighters){
            f.updateHPBar();
            f.updateNamePlate();
            f.updateManaBar();
            if (f.getHP ()<= 0){
                //checking that we only make the dead animation once
                if (aliveFighters.contains(f)){
                    f.setAnimation(f.dead);
                    f.sizeBy(70);
                    deadAnimationStart = System.currentTimeMillis();
                    killHim = true;
                    killingTarget = f;
                }
                aliveFighters.remove ( f );
                fightingTurn.remove ( f );
            }
            if (f.getAux () != f.getHP ()){
                f.getHPBar().addAction( Actions.repeat ( 3, Actions.sequence (Actions.fadeOut (0.20f),Actions.fadeIn ( 0.20f )) ) );
                f.setAux ( f.getHP () ); }
            if (f instanceof EnemyFighters)
                isAllEnemyDead = false;
            else
                isAllHeroesDead = false;
            if (!fightingTurn.isEmpty())
                fightingTurn.peek().updateNameColor();
        }

        //makes the attack animation and reset to idle after action
        if (attacker != null){
            attacker.setAnimation(attacker.attack);
            attacker.sizeBy(125);
            if ((System.currentTimeMillis() - startTime)/1000 > attacker.attack.getAnimationDuration()){
                attacker.setAnimation(attacker.iddle);
                attacker.sizeBy(100);
                attacker = null;
            }

        }
        if (!fightingTurn.isEmpty()){
            if (safeFighterStackPeek() instanceof EnemyFighters){
                currentTime = System.currentTimeMillis();
                if ((currentTime - startTime) > enemyThinking){
                    boolean isNonQuestionAttack = false;
                    //30% chance to activate the question for enemies.
                    if (triviaMustBeShown == TRIVIATOBECHECK && MathUtils.random(0,9)< TRIVIACHANCE ){
                        triviaMustBeShown = TRIVIAHASBEENANSWER;
                        showTrivia();
                    }else if (triviaMustBeShown == TRIVIATOBECHECK) {
                        triviaMustBeShown = TRIVIAWAITINGFORANSWER;
                        isNonQuestionAttack = true;
                    }
                    stateOfAnswer(isAnswerButton1Pushed,isAnswerButton2Pushed,isAnswerButton3Pushed,isAnswerButton4Pushed);
                    boolean hasBeenPressed = (isAnswerButton1Pushed || isAnswerButton2Pushed ||
                            isAnswerButton3Pushed || isAnswerButton4Pushed);
                    int championTarget = MathUtils.random(0,100);
                    //60% chance to hit first target 25% second chance 15% last target
                    //if target is dead, goes for the next one
                    if (championTarget > 40 && aliveFighters.contains ( championOne ) &&
                            (hasBeenPressed || isNonQuestionAttack) ){ //when a enemy choose to attack first target
                        startTime = System.currentTimeMillis() ;
                        if (isNonQuestionAttack){
                            theEnemyAttacks ( (EnemyFighters)safeFighterStackPeek(), championOne );
                            attacker = safeFighterStackPeek();
                        }
                        else if (!isCorrectAnswer){
                            theEnemyAttacks ( (EnemyFighters)safeFighterStackPeek(), championOne );
                            theEnemyAttacks ( (EnemyFighters)safeFighterStackPeek(), championOne );
                            attacker = safeFighterStackPeek();
                            startTime += 4000; //increasing animation time there is a 4s delay with the answer on screen
                        }
                        safeFighterStackPop();
                        isFightTurnOver();
                        triviaMustBeShown  = TRIVIATOBECHECK;
                    }
                    else if (championTarget > 15 && aliveFighters.contains (championTwo) &&
                            (hasBeenPressed || isNonQuestionAttack)){
                        startTime = System.currentTimeMillis() ;
                        if (isNonQuestionAttack){
                            theEnemyAttacks ( (EnemyFighters)safeFighterStackPeek(), championTwo );
                            attacker = safeFighterStackPeek();
                        }else if (!isCorrectAnswer){
                            theEnemyAttacks ( (EnemyFighters)safeFighterStackPeek(), championTwo );
                            theEnemyAttacks ( (EnemyFighters)safeFighterStackPeek(), championTwo );
                            attacker = safeFighterStackPeek();
                            startTime += 4000;
                        }
                        safeFighterStackPop();
                        isFightTurnOver();
                        triviaMustBeShown = TRIVIATOBECHECK;
                    }
                    else if ((hasBeenPressed || isNonQuestionAttack)){
                        startTime = System.currentTimeMillis() ;
                        if (isNonQuestionAttack){
                            theEnemyAttacks ( (EnemyFighters)safeFighterStackPeek(),championThree );
                            attacker = safeFighterStackPeek();
                        }else if (!isCorrectAnswer){
                            theEnemyAttacks ( (EnemyFighters)safeFighterStackPeek(),championThree );
                            theEnemyAttacks ( (EnemyFighters)safeFighterStackPeek(),championThree );
                            attacker = safeFighterStackPeek();
                            startTime += 4000;
                        }
                        safeFighterStackPop();
                        isFightTurnOver();
                        triviaMustBeShown = TRIVIATOBECHECK;
                    }
                    enemyThinking = MathUtils.random(3500,4500); //thinking for the next enemy
                }
            }
        }

        //going out of the spell buffer if we right click.
        if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)){
            activateDefaultMouse ();
            firstAttack = secondAttack = thirdAttack = false;
        }

        if (isAllEnemyDead){//if all enemys are dead go back to exploring map
            //implement HP and MANA exporting of our characters before leaving the screen
            exportHP();
            battleMusic.stop();
            this.dispose();
            if (amountOfEnemies<0){
                countKeys++;
            }
            if (amountOfEnemies == -4){
                // Exit to the GameOverScreen
                MortenKombat.setActiveScreen(new VictoryScreen());
            }
            else{
                ExploringScreen.musicPlay();
                MortenKombat.setActiveScreen(previousMap);
            }
        } else if (isAllHeroesDead) { // If all the fighting actors are dead, go to GameOverScreen
                battleMusic.stop();
                // Exit to the GameOverScreen
                this.dispose();
                MortenKombat.setActiveScreen(new GameOverScreen());
            }

        //checking if we have killed any fighters
        if (killHim && (System.currentTimeMillis() - deadAnimationStart)/1000 > killingTarget.dead.getAnimationDuration() ){
            killingTarget.dead.setPlayMode(Animation.PlayMode.NORMAL);
            killHim = false;
        }
        hideAllButtons(champions);
        if (safeFighterStackPeek() instanceof Champion){
            showButtons((Champion)safeFighterStackPeek());
        }
        isFightTurnOver();
    } // Update end

    /**
     * chnages the came mouse icon to the default mode.
     */
    private void activateDefaultMouse(){
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(defaultMouse, 0, 0));
    }

    /**
     * Changes the game mouse icon to a mouse with lighting effect. Used when an ability is in the buffer.
     */
    private void activateSpellMouse(){
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(spellMouse, 0, 0));
    }

    /**
     * Method that we call when one of our champions has done an ability. Will initialize the "attack animation"
     * and reset all the variables to the state that we dont have a ability in buffer, and reset the state of the trivia.
      * @param user Champion that has used an ability
     */
    private void abilitySuccess(Champion user){
        attacker = user;
        abilityNotSuccess();
    }

    /**
     * Method call when you have done a ability without any success. Will restart some values in our class to the state
     * where we dont have any ability in a buffer. Also the state of the trivia will be reset.
     */
    private void abilityNotSuccess(){
        startTime = System.currentTimeMillis();
        activateDefaultMouse();
        tooltipText.setVisible(false);
        tooltipText.setText("");
        safeFighterStackPop();
        if (safeFighterStackPeek() instanceof Champion)
            tooltipText.setVisible(true);
        isFightTurnOver();
        firstAttack = secondAttack = thirdAttack = false;
        triviaHasCheck = -1;
    }

    /**
     * Method that randomize between attackOne and attackTwo for enemyFighters.
     * @param enemy EnemyFighter that uses the ability.
     * @param fighter Fighter that will be target of our ability.
     */
    private void theEnemyAttacks(EnemyFighters enemy,Fighter fighter){
        int chanceAbility = MathUtils.random(0,100);
        if (chanceAbility >=40)
            enemy.attackOne(fighter);//60% chance to attack with attack1
        else
            enemy.attackTwo(fighter);//40% chance to attack with attack2
    }

    /**
     * Exports the current HP of the champions in Static variable.
     * This variables can be get with exportHP method declared in this class.
     *
     */
    private void exportHP(){
        championOneHP = championOne.getHP();
        championTwoHP = championTwo.getHP();
        championThreeHP = championThree.getHP();

    }

    /**
     * Import champions HP from previous states of the fight. If the import is equal to 666 that means is the first
     * time we import the HP and will import the currentHP from the class.
     * If the variable that saves the HP of our heroes is not 666 then check on the individual variables and set the
     * current HP to the saved HP.
     */
    private void importHP(){
        if (championOneHP < 666 && championTwoHP < 666 && championThreeHP < 666){
            championOne.setHP(championOneHP);
            championTwo.setHP(championTwoHP);
            championThree.setHP(championThreeHP);
        } else {
            championOne.setHP(championOne.getMaxHP());
            championTwo.setHP(championTwo.getMaxHP());
            championThree.setHP(championThree.getMaxHP());
        }
    }

    /**
     * Method that is call to heal and revive our dead or alive champions to full life.
     */
    public static void medicHeal() {
        championOneHP = 777;
        championTwoHP = 777;
        championThreeHP = 777;
    }

    /**
     * Creates the answerbuttons.
     * @param answerButton Textbutton object
     * @param answer the text of the button
     * @param x coordinate of the button
     * @param y coordinate of the button
     * @param width of the button
     */
    private void setAnswerButton(TextButton answerButton,String answer,float x,float y,float width){

        answerButton.getLabel().setText(answer);
        answerButton.setPosition(x,y);
        answerButton.setWidth(width);
        answerButton.getLabel().setFontScale(0.5f,0.5f);
        answerButton.getLabel().setWrap(true);
        answerButton.getLabel().setColor(Color.WHITE);
        answerButton.setColor(Color.BLACK);
        answerButton.setVisible(false);
    }
    /**
     * If the given answerButton1 is pushed, it checks if that button is the correct answer or not. If yes it appears as green and the rest become invisible.
     * If this answerButton1 is wrong, then it appears as red and the good answer as green and the rest disappear.
     * @param isAnswerButton1Pushed gives if answerButton1 pushed or not
     */
    private void caseOfAnswerButton1(boolean isAnswerButton1Pushed){
        if(isAnswerButton1Pushed){
            triviaInformation.setVisible(false);
            if(answerButton1.getText().toString().equals(qA.peek().correctAnswer)){

                answerButton1.getLabel().setColor(Color.GREEN);
                answerButton2.getLabel().setColor(Color.CLEAR);
                answerButton3.getLabel().setColor(Color.CLEAR);
                answerButton4.getLabel().setColor(Color.CLEAR);

            }else{

                answerButton1.getLabel().setColor(Color.RED);
                if(answerButton2.getText().toString().equals(qA.peek().correctAnswer)){

                    answerButton2.getLabel().setColor(Color.GREEN);
                    answerButton3.getLabel().setColor(Color.CLEAR);
                    answerButton4.getLabel().setColor(Color.CLEAR);
                }
                if(answerButton3.getText().toString().equals(qA.peek().correctAnswer)){

                    answerButton3.getLabel().setColor(Color.GREEN);
                    answerButton2.getLabel().setColor(Color.CLEAR);
                    answerButton4.getLabel().setColor(Color.CLEAR);
                }
                if(answerButton4.getText().toString().equals(qA.peek().correctAnswer)){

                    answerButton4.getLabel().setColor(Color.GREEN);
                    answerButton2.getLabel().setColor(Color.CLEAR);
                    answerButton3.getLabel().setColor(Color.CLEAR);
                }

            }
            deleteTrivia(startTime2);
        }
    }
    /**
     If the given answerButton2 is pushed, it checks if that button is the correct answer or not. If yes it appears as green and the rest become invisible.
     * If this answerButton2 is wrong, then it appears as red and the good answer as green and the rest disappear.
     * @param isAnswerButton2Pushed gives if answerButton1 pushed or not
     */
    private void caseOfAnswerButton2(boolean isAnswerButton2Pushed){
        if(isAnswerButton2Pushed){
            triviaInformation.setVisible(false);

            if(answerButton2.getText().toString().equals(qA.peek().correctAnswer)){

                answerButton2.getLabel().setColor(Color.GREEN);
                answerButton1.getLabel().setColor(Color.CLEAR);
                answerButton3.getLabel().setColor(Color.CLEAR);
                answerButton4.getLabel().setColor(Color.CLEAR);

            }else{

                answerButton2.getLabel().setColor(Color.RED);
                if(answerButton1.getText().toString().equals(qA.peek().correctAnswer)){

                    answerButton1.getLabel().setColor(Color.GREEN);
                    answerButton3.getLabel().setColor(Color.CLEAR);
                    answerButton4.getLabel().setColor(Color.CLEAR);
                }
                if(answerButton3.getText().toString().equals(qA.peek().correctAnswer)){

                    answerButton3.getLabel().setColor(Color.GREEN);
                    answerButton1.getLabel().setColor(Color.CLEAR);
                    answerButton4.getLabel().setColor(Color.CLEAR);
                }
                if(answerButton4.getText().toString().equals(qA.peek().correctAnswer)){

                    answerButton4.getLabel().setColor(Color.GREEN);
                    answerButton1.getLabel().setColor(Color.CLEAR);
                    answerButton3.getLabel().setColor(Color.CLEAR);
                }

            }
            deleteTrivia(startTime2);
        }
    }
    /**
     If the given answerButton3 is pushed, it checks if that button is the correct answer or not. If yes it appears as green and the rest become invisible.
     * If this answerButton3 is wrong, then it appears as red and the good answer as green and the rest disappear.
     * @param isAnswerButton3Pushed gives if answerButton1 pushed or not
     */
    private void caseOfAnswerButton3(boolean isAnswerButton3Pushed){
        if(isAnswerButton3Pushed){
            triviaInformation.setVisible(false);

            if(answerButton3.getText().toString().equals(qA.peek().correctAnswer)){

                answerButton3.getLabel().setColor(Color.GREEN);
                answerButton1.getLabel().setColor(Color.CLEAR);
                answerButton2.getLabel().setColor(Color.CLEAR);
                answerButton4.getLabel().setColor(Color.CLEAR);

            }else{

                answerButton3.getLabel().setColor(Color.RED);
                if(answerButton1.getText().toString().equals(qA.peek().correctAnswer)){

                    answerButton1.getLabel().setColor(Color.GREEN);
                    answerButton2.getLabel().setColor(Color.CLEAR);
                    answerButton4.getLabel().setColor(Color.CLEAR);
                }
                if(answerButton2.getText().toString().equals(qA.peek().correctAnswer)){

                    answerButton2.getLabel().setColor(Color.GREEN);
                    answerButton1.getLabel().setColor(Color.CLEAR);
                    answerButton4.getLabel().setColor(Color.CLEAR);
                }
                if(answerButton4.getText().toString().equals(qA.peek().correctAnswer)){

                    answerButton4.getLabel().setColor(Color.GREEN);
                    answerButton1.getLabel().setColor(Color.CLEAR);
                    answerButton2.getLabel().setColor(Color.CLEAR);
                }

            }
            deleteTrivia(startTime2);
        }
    }
    /**
     If the given answerButton4 is pushed, it checks if that button is the correct answer or not. If yes it appears as green and the rest become invisible.
     * If this answerButton4 is wrong, then it appears as red and the good answer as green and the rest disappear.
     * @param isAnswerButton4Pushed gives if answerButton1 pushed or not
     */
    private void caseOfAnswerButton4(boolean isAnswerButton4Pushed){
        if(isAnswerButton4Pushed){
            triviaInformation.setVisible(false);

            if(answerButton4.getText().toString().equals(qA.peek().correctAnswer)){

                answerButton4.getLabel().setColor(Color.GREEN);
                answerButton1.getLabel().setColor(Color.CLEAR);
                answerButton2.getLabel().setColor(Color.CLEAR);
                answerButton3.getLabel().setColor(Color.CLEAR);

            }else{

                answerButton4.getLabel().setColor(Color.RED);
                if(answerButton1.getText().toString().equals(qA.peek().correctAnswer)){

                    answerButton1.getLabel().setColor(Color.GREEN);
                    answerButton2.getLabel().setColor(Color.CLEAR);
                    answerButton3.getLabel().setColor(Color.CLEAR);
                }
                if(answerButton2.getText().toString().equals(qA.peek().correctAnswer)){

                    answerButton2.getLabel().setColor(Color.GREEN);
                    answerButton1.getLabel().setColor(Color.CLEAR);
                    answerButton3.getLabel().setColor(Color.CLEAR);
                }
                if(answerButton3.getText().toString().equals(qA.peek().correctAnswer)){

                    answerButton3.getLabel().setColor(Color.GREEN);
                    answerButton1.getLabel().setColor(Color.CLEAR);
                    answerButton2.getLabel().setColor(Color.CLEAR);
                }

            }
            deleteTrivia(startTime2);
        }
    }
    /**
     * It deletes the Trivia when it is called. It takes the exact time when an answerButton was pushed and compare it to the current time,
     * if it exceeds it makes invisible the Trivia, reset the isAnswerButtonPushed boolean variables` state to false and reset the colors of the buttons` text to white.
     * It pops one element from the stack, so the same question does not appear again.
     * Then it points to the next element in the stack.
     * Clear the previous element`s answers from the "answer" arraylist.
     * It mixes the answer in the arraylist to be randomized on the screen.
     * In the end, it makes uiTable visible again.
     * @param startTime2 time that is compared inside deleteTrivia, if the startTime2 is 3.4s difference with current
     *                   machine time it will execute some code.
     */
    private void deleteTrivia(long startTime2){

        currentTime2=System.currentTimeMillis();

        if((currentTime2-startTime2)>3400){
            isAnswerButton1Pushed=false;
            isAnswerButton2Pushed=false;
            isAnswerButton3Pushed=false;
            isAnswerButton4Pushed=false;

            answerButton1.setVisible(false);
            answerButton2.setVisible(false);
            answerButton3.setVisible(false);
            answerButton4.setVisible(false);
            questionBox.setVisible(false);

            answerButton1.getLabel().setColor(Color.WHITE);
            answerButton2.getLabel().setColor(Color.WHITE);
            answerButton3.getLabel().setColor(Color.WHITE);
            answerButton4.getLabel().setColor(Color.WHITE);

            qA.pop();
            if (qA.isEmpty()){
                qA.addAll(MortenKombat.questionBackUp);
                Collections.shuffle(qA);
            }

            randomInt=MathUtils.random(0,5);

//            if(!qA.get(randomInt).empty()){

                questionBox.setText(qA.peek().question);

                answers.clear();

                answers.add(qA.peek().correctAnswer);
                answers.add(qA.peek().wrongAnswer1);
                answers.add(qA.peek().wrongAnswer2);
                answers.add(qA.peek().wrongAnswer3);

//            }

            Collections.shuffle(answers);

            answerButton1.getLabel().setText(answers.get(0));
            answerButton2.getLabel().setText(answers.get(1));
            answerButton3.getLabel().setText(answers.get(2));
            answerButton4.getLabel().setText(answers.get(3));
            for (Champion c : champions){
                c.getFirstButton().setVisible(true);
                c.getSecondButton().setVisible(true);
                c.getThirdButton().setVisible(true);
            }
            turnLabel.setVisible(true);
            tooltipText.setVisible(true);
        }
    }
    /**
     * Makes answerButtons, questionBox visible and the uiTable invisible.
     */
    private void showTrivia(){

        answerButton1.setVisible(true);
        answerButton2.setVisible(true);
        answerButton3.setVisible(true);
        answerButton4.setVisible(true);
        questionBox.setVisible(true);
        if (safeFighterStackPeek() instanceof EnemyFighters)
            triviaInformation.setText(safeFighterStackPeek().getFighterName()+ " has the trivia attack. Answer" +
                    " correctly to avoid been attacked by the enemy. If you fail you will be attacked twice by" +
                    " the enemy");
        if (safeFighterStackPeek() instanceof Champion)
            triviaInformation.setText(safeFighterStackPeek().getFighterName()+ " has the trivia attack. Answer" +
                    " correctly and your champion will deal twice the ability in the same turn (check if your spell " +
                    "casters have enough mana!). If you give a wrong answer your champion will lose his turn.");
        triviaInformation.setVisible(true);
        turnLabel.setVisible(false);
        tooltipText.setVisible(false);
        for (Champion c: champions){
            c.getFirstButton().setVisible(false);
            c.getSecondButton().setVisible(false);
            c.getThirdButton().setVisible(false);
        }
    }
    /**
     * Check if the answer is correct or not.
     * @param isAnswerButton1Pushed boolean variable to check if the particular button is pushed or not
     * @param isAnswerButton2Pushed boolean variable to check if the particular button is pushed or not
     * @param isAnswerButton3Pushed boolean variable to check if the particular button is pushed or not
     * @param isAnswerButton4Pushed boolean variable to check if the particular button is pushed or not
     */
    private void stateOfAnswer(boolean isAnswerButton1Pushed,boolean isAnswerButton2Pushed, boolean isAnswerButton3Pushed,boolean isAnswerButton4Pushed){

        if((isAnswerButton1Pushed && answerButton1.getLabel().getText().toString().equals(qA.peek().correctAnswer))
                || (isAnswerButton2Pushed && answerButton2.getLabel().getText().toString().equals(qA.peek().correctAnswer))
                || (isAnswerButton3Pushed && answerButton3.getLabel().getText().toString().equals(qA.peek().correctAnswer))
                || (isAnswerButton4Pushed && answerButton4.getLabel().getText().toString().equals(qA.peek().correctAnswer))){
                    isCorrectAnswer=true;

        }else if((isAnswerButton1Pushed && !(answerButton1.getLabel().getText().toString().equals(qA.peek().correctAnswer)))
                || (isAnswerButton2Pushed && !(answerButton2.getLabel().getText().toString().equals(qA.peek().correctAnswer)))
                || (isAnswerButton3Pushed && !(answerButton3.getLabel().getText().toString().equals(qA.peek().correctAnswer)))
                || (isAnswerButton4Pushed && !(answerButton4.getLabel().getText().toString().equals(qA.peek().correctAnswer)))){
                    isCorrectAnswer=false;
        }

    }

    /**
     * Returns a randomize enemyFighter from SkeletonFighter, ZombieFighter and TrollFighter.
     * @return enemyFighter that is random generated. It can be selected between SkeletonFighter, ZombieFighter or TrollFghter
     *
     */
    private EnemyFighters createRandomEnemy(){
        int random = MathUtils.random(1,3);
        if (random == 1)
            return new SkeletonFighter(mainStage);
        else if (random == 2)
            return new ZombieFighter(mainStage);
        else
            return new TrollFighter(mainStage);
    }

    /**
     * Method that check if the fighting turn is over.
     * In case that the fightingTurn is empty we do the next steps to create a new turn:
     *  - We refill the fightingTurn with new fighters from aliveFighters.
     *  - We shuffle the fightingTurn to randomize the position for our fighters inside the turn.
     *  - We call the method manaRegenation for all our spellcasters that are in fightingTurn.
     *  - We update the turn label.
     */
    private void isFightTurnOver(){
        if (fightingTurn.isEmpty ()){
            fightingTurn.addAll(aliveFighters);
            for (Fighter sC : fightingTurn){ //regenerates mana at the end of each turn.
                if (sC instanceof SpellCaster)
                    ((SpellCaster) sC).manaRegeneration();
            }
            shuffleDeque(fightingTurn);
            turn ++;
            turnLabel.setText("Turn: "+turn);
        }
    }

    /**
     * Method used to get the amount of keys our champions has collected.
     * @return integer that represent the amount of keys we have achieved by killing bosses.
     */
    public static int getCountKeys() {
        return countKeys;
    }

    /**
     * Method that check if the stack is empty before making a pop. if not empty return pop of the stack.
     * @return return the pop of fighting turn if it is not empty. If it is empty return null.
     */
    private  Fighter safeFighterStackPop(){
        if (!fightingTurn.isEmpty()){
           return fightingTurn.pop ();

        }
        return null;
    }

    /**
     * Method that check if the stack is empty before peeking. If not empty return the peek of the stack.
     * @return Peek of the stack fighting turn if it is not empty, return null if the stack is empty.
     */
    private Fighter safeFighterStackPeek(){
        if (!fightingTurn.isEmpty())
            return fightingTurn.peek();
        return null;
    }

    /**
     * Method that suffle an ArrayDeque.
     * @param fightingTurn target ArrayDeque that need to suffle.
     */
    private void shuffleDeque(ArrayDeque fightingTurn){
        ArrayList shuffler = new ArrayList<Fighter>();
        shuffler.addAll(fightingTurn);
        Collections.shuffle(shuffler);
        fightingTurn.clear();
        fightingTurn.addAll(shuffler);
    }
    private void hideAllButtons(ArrayList<Champion> champions){
        for (Champion c : champions){
            c.getThirdButton().setVisible(false);
            c.getSecondButton().setVisible(false);
            c.getFirstButton().setVisible(false);
        }
    }
    private void showButtons(Champion champion){
        champion.getFirstButton().setVisible(true);
        champion.getSecondButton().setVisible(true);
        champion.getThirdButton().setVisible(true);

    }
}