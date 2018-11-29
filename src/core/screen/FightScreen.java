package core.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import core.MortenCombat;
import core.actors.fightingactors.*;
import core.framework.BaseActor;
import core.framework.BaseScreen;
import java.util.concurrent.CopyOnWriteArrayList;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import core.ImportQandA;
import core.framework.BaseGame;
import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;


public class FightScreen extends BaseScreen {
    private LevelScreen previousMap;
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
    long startTime = System.currentTimeMillis();
    int turn = 0;
    int enemyThinking = MathUtils.random(3500,4500);
    Fighter attacker = null; //variable to know when someone have attacked to make the animation
    long deadAnimationStart;
    static private int championOneHP= 666;
    static private int championThreeHP = 666;
    static private int championTwoHP = 666;
    private boolean killHim = false;
    Fighter killingTarget;


    public Stack<ImportQandA> science;
    public Stack<ImportQandA> geography;
    public Stack<ImportQandA> history;
    public Stack<ImportQandA> art;
    public Stack<ImportQandA> sport;
    public Stack<ImportQandA> entertainment;

    private DialogBox questionBox;

    private TextButton answerButton1;
    private TextButton answerButton2;
    private TextButton answerButton3;
    private TextButton answerButton4;

    private TextButton question;

    boolean isAnswerButton1Pushed=false;
    boolean isAnswerButton2Pushed=false;
    boolean isAnswerButton3Pushed=false;
    boolean isAnswerButton4Pushed=false;

    boolean isCorrectAnswer;

    ArrayList<Stack<ImportQandA>> topics;
    ArrayList<String> answers;

    long startTime2;
    long currentTime2;

    static int randomInt=0;


    public FightScreen(LevelScreen prev) throws FileNotFoundException {
        super();
        previousMap = prev;

    }

    public void initialize() throws FileNotFoundException {

        // Battle music
        battleMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/audio/music/NoSurrender.mp3"));
        battleMusic.setVolume(MortenCombat.volume);
        battleMusic.setLooping(true);
        battleMusic.play();

        //initialize the mapbackground
        BaseActor fightBackground = new BaseActor(0,0, mainStage);
        fightBackground.loadTexture( "assets/img/dungeon.png" );
        fightBackground.setSize(800,600);
        //initialize the actors at the screen, depending on the seletionScreen, read by a static variable in
        //Morten combat
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
                    {
                        if ( !(e instanceof InputEvent) )
                            return false;
                        if ( !((InputEvent)e).getType().equals(InputEvent.Type.touchDown) )
                            return false;
                        //making sure that only the fighter with the turn can listen to clicks.
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
                        //if you dont put this line, right click would also trigger the spells. and we only
                        //want to trigget the spells with left mouse click.
                        if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT))
                            return false;

                        //if the first condition is false, the second condition is never check and never executed
                        if (firstAttack && abilityUser.attackOne((Fighter)o.getTarget ())) {
                            abilitySuccess (abilityUser);
                        }else if (  secondAttack && abilityUser.attackTwo ( (Fighter)o.getTarget () )){
                            abilitySuccess (abilityUser);
                        }else if (thirdAttack ){
                            if (o.getTarget() instanceof Champion){
                                if(abilityUser.attackThree(championOne, championTwo, championThree)) {
                                    abilitySuccess (abilityUser);
                                }
                                //when clicking make sure that the target is the first target.
                            }else if (o.getTarget () instanceof EnemyFighters){
                                if (o.getTarget () == enemyOne )
                                    if (abilityUser.attackThree(enemyOne,enemyTwo,enemyThree)){
                                        abilitySuccess (abilityUser);
                                    }
                                if (o.getTarget () == enemyTwo )
                                    if (abilityUser.attackThree(enemyTwo,enemyOne,enemyThree)){
                                        abilitySuccess (abilityUser);
                                    }
                                if (o.getTarget () == enemyThree )
                                    if (abilityUser.attackThree(enemyThree,enemyOne,enemyTwo)){
                                        abilitySuccess (abilityUser);
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
        uiTable.add ( enemyOne.getFighterNamePlate() ).height( 20 ).width( 110 ); //enemy 1 nameplate
        uiTable.add ( enemyTwo.getFighterNamePlate() ).height( 20 ).width( 110 ); //enemy 2 nameplate
        uiTable.add ( enemyThree.getFighterNamePlate() ).height( 20 ).width( 110 ); // enemy 3 nameplate
        uiTable.add ( ).height( 20 ).width( 25 );
        uiTable.row ();
        //HPBar row
        uiTable.add ( ).height( 20 ).width( 25 );
        uiTable.add ( championThree.getHPBar() ).height( 20 ).width( 110 ); //hero 3 hpbar
        uiTable.add ( championTwo.getHPBar() ).height( 20 ).width( 110 ); //hero 2 hpbar
        uiTable.add ( championOne.getHPBar() ).height( 20 ).width( 110 ); //hero 1 hpbar
        uiTable.add ( ).height( 20 ).width( 56 );
        uiTable.add ( enemyOne.getHPBar() ).height( 20 ).width( 110 ); //enemy 1 hpbar
        uiTable.add ( enemyTwo.getHPBar() ).height( 20 ).width( 110 ); //enemy 2 hpbar
        uiTable.add ( enemyThree.getHPBar() ).height( 20 ).width( 110 ); // enemy 3 hpbar
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


        for (Fighter f : fightingTurn){
            f.sizeBy(60);

            f.setAux ( f.getHP () );
        }


        /*
        data1= new File("assets\\QnA\\Science.txt");
        data2= new File("assets\\QnA\\Geography.txt");
        data3= new File("assets\\QnA\\History.txt");
        data4= new File("assets\\QnA\\Art.txt");
        data5= new File("assets\\QnA\\Sport.txt");
        data6= new File("assets\\QnA\\Entertainment.txt");

        science = new Stack<>();
        geography = new Stack<>();
        history = new Stack<>();
        art = new Stack<>();
        sport = new Stack<>();
        entertainment = new Stack<>();

        ImportQandA.filler(10,science,data1);
        ImportQandA.filler(10,geography,data2);
        ImportQandA.filler(10,history,data3);
        ImportQandA.filler(10,art,data4);
        ImportQandA.filler(10,sport,data5);
        ImportQandA.filler(10,entertainment,data6);

        Collections.shuffle(science);
        Collections.shuffle(geography);
        Collections.shuffle(history);
        Collections.shuffle(art);
        Collections.shuffle(sport);
        Collections.shuffle(entertainment);

        topics= new ArrayList<Stack<ImportQandA>>();
        topics.add(science);
        topics.add(geography);
        topics.add(history);
        topics.add(art);
        topics.add(sport);
        topics.add(entertainment);

        Collections.shuffle(topics);*/
        topics = MortenCombat.topics;

        questionBox= new DialogBox(100,400,uiStage);
        questionBox.setBackgroundColor(Color.BLACK);
        questionBox.setFontColor(Color.GOLDENROD);
        questionBox.setDialogSize(600,100);
        questionBox.setFontScale(0.80f);
        questionBox.alignCenter();
        questionBox.setText(topics.get(randomInt).peek().question);
        questionBox.setVisible(false);

        answerButton1= new TextButton("",BaseGame.textButtonStyle);
        answerButton2= new TextButton("",BaseGame.textButtonStyle);
        answerButton3= new TextButton("",BaseGame.textButtonStyle);
        answerButton4= new TextButton("",BaseGame.textButtonStyle);

        question= new TextButton("",BaseGame.textButtonStyle);

        answers= new ArrayList<String>();

        answers.add(topics.get(randomInt).peek().correctAnswer);
        answers.add(topics.get(randomInt).peek().wrongAnswer1);
        answers.add(topics.get(randomInt).peek().wrongAnswer2);
        answers.add(topics.get(randomInt).peek().wrongAnswer3);

        Collections.shuffle(answers);

        setAnswerButton(answerButton1,answers.get(0),100,275,300);
        setAnswerButton(answerButton2,answers.get(1),100,175,300);
        setAnswerButton(answerButton3,answers.get(2),400,275,300);
        setAnswerButton(answerButton4,answers.get(3),400,175,300);

        setAnswerButton(question,"???",500,500,100);
        question.getLabel().setColor(Color.YELLOW);
        question.setVisible(true);

        question.addListener(
                (Event e) ->
                {
                    if ( !(e instanceof InputEvent) )
                        return false;
                    if ( !((InputEvent)e).getType().equals(InputEvent.Type.touchDown) )
                        return false;

                    showTrivia();

                    return true;
                }
        );

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
        qaStage.addActor(question);

    }

    public void update(float dt) {
        //if the turn is over (means there is no more object in the stack), we create a new turn by feeding the stack
        //with alivefighter Arraylist
        if (fightingTurn.isEmpty ()){
            fightingTurn.addAll(aliveFighters);
            Collections.shuffle(fightingTurn);
            turn ++; // not been used, maybe we can put a Turn number on screen.
        }
        for (Fighter f : aliveFighters){
            f.updateHPBar();
            f.updateNamePlate();
            f.updateManaBar();
            //updating to green when the actor has the turn
            fightingTurn.peek().updateNameColor();
        }

        //makes the attack animation and reset to idle after
        if (attacker != null){
            attacker.setAnimation(attacker.attack);
            attacker.sizeBy(110);
            if ((System.currentTimeMillis() - startTime)/1000 > attacker.attack.getAnimationDuration()){
                attacker.setAnimation(attacker.iddle);
                attacker.sizeBy(90);
                attacker = null;
            }
        }

        if (fightingTurn.peek() instanceof EnemyFighters){
            // = false;
            currentTime = System.currentTimeMillis();

            /*while ((currentTime - startTime) < enemyThinking){
                currentTime = System.currentTimeMillis();
            }*/
            if ((currentTime - startTime) > enemyThinking){
                int championTarget = MathUtils.random(0,100);
                //60% chance to hit first target 25% second chance 15% last target
                //if target is dead, goes for the next one
                if (championTarget > 40 && aliveFighters.contains ( championOne )){
                    theEnemyAttacks ( (EnemyFighters)fightingTurn.peek(), championOne );
                    startTime = System.currentTimeMillis();
                    attacker = fightingTurn.pop();
                }
                else if (championTarget > 15 && aliveFighters.contains (championTwo)){
                    theEnemyAttacks ( (EnemyFighters)fightingTurn.peek(), championTwo );
                    startTime = System.currentTimeMillis();
                    attacker = fightingTurn.pop();
                }
                else{
                    theEnemyAttacks ( (EnemyFighters)fightingTurn.peek(),championThree );
                    startTime = System.currentTimeMillis();
                    attacker = fightingTurn.pop();

                }
                enemyThinking = MathUtils.random(3500,4500); //thinking for the next enemy
            }
        }

        //going out of the spell buffer if we right click.
        if (Gdx.input.isButtonPressed(Input.Buttons.RIGHT)){
            activateDefaultMouse ();
            firstAttack = secondAttack = thirdAttack = false;
        }
        //I assume that all enemies are dead
        boolean isAllEnemyDead = true;
        //I check if any enemy is alive
        for (Fighter f : aliveFighters){
            if (f instanceof EnemyFighters)
                isAllEnemyDead = false;
        }
        if (isAllEnemyDead){//if all enemys are dead go back to exploring map
            //implement HP and MANA exporting of our characters before leaving the screen
            exportHP();
            battleMusic.stop();
            //remove the screen
            this.dispose();
            //exit to exploring map
            MortenCombat.setActiveScreen(previousMap);
        }
        //checking if we have killed any fighters
        for (Fighter f : aliveFighters){
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


                f.setAux ( f.getHP () );
            }



        }
        if (killHim && (System.currentTimeMillis() - deadAnimationStart)/1000 > killingTarget.dead.getAnimationDuration() ){
            killingTarget.dead.setPlayMode(Animation.PlayMode.NORMAL);
            killHim = false;
        }

        caseOfAnswerButton1(isAnswerButton1Pushed);
        caseOfAnswerButton2(isAnswerButton2Pushed);
        caseOfAnswerButton3(isAnswerButton3Pushed);
        caseOfAnswerButton4(isAnswerButton4Pushed);
        stateOfAnswer(isAnswerButton1Pushed,isAnswerButton2Pushed,isAnswerButton3Pushed,isAnswerButton4Pushed);

    }

    private void activateDefaultMouse(){
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(defaultMouse, 0, 0));
    }

    private void activateSpellMouse(){
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(spellMouse, 0, 0));
    }
    //method that is been used every ability that has been done by our Champions
    private void abilitySuccess(Champion user){
        activateDefaultMouse ();
        fightingTurn.pop();

        startTime = System.currentTimeMillis();
        attacker = user;
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
    //method to initialize the HP. if the HP of the heroes are 666 that means that is entering the first time in the
    //fighting screen and does not need to import any HP.
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
    public static void medicHeal() {
        championOneHP = 777;
        championTwoHP = 777;
        championThreeHP = 777;
    }

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

    private void caseOfAnswerButton1(boolean isAnswerButton1Pushed){
        if(isAnswerButton1Pushed){

            if(answerButton1.getText().toString().equals(topics.get(randomInt).peek().correctAnswer)){

                answerButton1.getLabel().setColor(Color.GREEN);
                answerButton2.getLabel().setColor(Color.CLEAR);
                answerButton3.getLabel().setColor(Color.CLEAR);
                answerButton4.getLabel().setColor(Color.CLEAR);

            }else{

                answerButton1.getLabel().setColor(Color.RED);
                if(answerButton2.getText().toString().equals(topics.get(randomInt).peek().correctAnswer)){

                    answerButton2.getLabel().setColor(Color.GREEN);
                    answerButton3.getLabel().setColor(Color.CLEAR);
                    answerButton4.getLabel().setColor(Color.CLEAR);
                }
                if(answerButton3.getText().toString().equals(topics.get(randomInt).peek().correctAnswer)){

                    answerButton3.getLabel().setColor(Color.GREEN);
                    answerButton2.getLabel().setColor(Color.CLEAR);
                    answerButton4.getLabel().setColor(Color.CLEAR);
                }
                if(answerButton4.getText().toString().equals(topics.get(randomInt).peek().correctAnswer)){

                    answerButton4.getLabel().setColor(Color.GREEN);
                    answerButton2.getLabel().setColor(Color.CLEAR);
                    answerButton3.getLabel().setColor(Color.CLEAR);
                }

            }

            deleteTrivia(startTime2);
        }
    }

    private void caseOfAnswerButton2(boolean isAnswerButton2Pushed){
        if(isAnswerButton2Pushed){

            if(answerButton2.getText().toString().equals(topics.get(randomInt).peek().correctAnswer)){

                answerButton2.getLabel().setColor(Color.GREEN);
                answerButton1.getLabel().setColor(Color.CLEAR);
                answerButton3.getLabel().setColor(Color.CLEAR);
                answerButton4.getLabel().setColor(Color.CLEAR);

            }else{

                answerButton2.getLabel().setColor(Color.RED);
                if(answerButton1.getText().toString().equals(topics.get(randomInt).peek().correctAnswer)){

                    answerButton1.getLabel().setColor(Color.GREEN);
                    answerButton3.getLabel().setColor(Color.CLEAR);
                    answerButton4.getLabel().setColor(Color.CLEAR);
                }
                if(answerButton3.getText().toString().equals(topics.get(randomInt).peek().correctAnswer)){

                    answerButton3.getLabel().setColor(Color.GREEN);
                    answerButton1.getLabel().setColor(Color.CLEAR);
                    answerButton4.getLabel().setColor(Color.CLEAR);
                }
                if(answerButton4.getText().toString().equals(topics.get(randomInt).peek().correctAnswer)){

                    answerButton4.getLabel().setColor(Color.GREEN);
                    answerButton1.getLabel().setColor(Color.CLEAR);
                    answerButton3.getLabel().setColor(Color.CLEAR);
                }

            }

            deleteTrivia(startTime2);
        }
    }

    private void caseOfAnswerButton3(boolean isAnswerButton3Pushed){
        if(isAnswerButton3Pushed){

            if(answerButton3.getText().toString().equals(topics.get(randomInt).peek().correctAnswer)){

                answerButton3.getLabel().setColor(Color.GREEN);
                answerButton1.getLabel().setColor(Color.CLEAR);
                answerButton2.getLabel().setColor(Color.CLEAR);
                answerButton4.getLabel().setColor(Color.CLEAR);

            }else{

                answerButton3.getLabel().setColor(Color.RED);
                if(answerButton1.getText().toString().equals(topics.get(randomInt).peek().correctAnswer)){

                    answerButton1.getLabel().setColor(Color.GREEN);
                    answerButton2.getLabel().setColor(Color.CLEAR);
                    answerButton4.getLabel().setColor(Color.CLEAR);
                }
                if(answerButton2.getText().toString().equals(topics.get(randomInt).peek().correctAnswer)){

                    answerButton2.getLabel().setColor(Color.GREEN);
                    answerButton1.getLabel().setColor(Color.CLEAR);
                    answerButton4.getLabel().setColor(Color.CLEAR);
                }
                if(answerButton4.getText().toString().equals(topics.get(randomInt).peek().correctAnswer)){

                    answerButton4.getLabel().setColor(Color.GREEN);
                    answerButton1.getLabel().setColor(Color.CLEAR);
                    answerButton2.getLabel().setColor(Color.CLEAR);
                }

            }

            deleteTrivia(startTime2);
        }
    }

    private void caseOfAnswerButton4(boolean isAnswerButton4Pushed){
        if(isAnswerButton4Pushed){

            if(answerButton4.getText().toString().equals(topics.get(randomInt).peek().correctAnswer)){

                answerButton4.getLabel().setColor(Color.GREEN);
                answerButton1.getLabel().setColor(Color.CLEAR);
                answerButton2.getLabel().setColor(Color.CLEAR);
                answerButton3.getLabel().setColor(Color.CLEAR);

            }else{

                answerButton4.getLabel().setColor(Color.RED);
                if(answerButton1.getText().toString().equals(topics.get(randomInt).peek().correctAnswer)){

                    answerButton1.getLabel().setColor(Color.GREEN);
                    answerButton2.getLabel().setColor(Color.CLEAR);
                    answerButton3.getLabel().setColor(Color.CLEAR);
                }
                if(answerButton2.getText().toString().equals(topics.get(randomInt).peek().correctAnswer)){

                    answerButton2.getLabel().setColor(Color.GREEN);
                    answerButton1.getLabel().setColor(Color.CLEAR);
                    answerButton3.getLabel().setColor(Color.CLEAR);
                }
                if(answerButton3.getText().toString().equals(topics.get(randomInt).peek().correctAnswer)){

                    answerButton3.getLabel().setColor(Color.GREEN);
                    answerButton1.getLabel().setColor(Color.CLEAR);
                    answerButton2.getLabel().setColor(Color.CLEAR);
                }

            }

            deleteTrivia(startTime2);
        }
    }

    private void deleteTrivia(long startTime2){

        currentTime2=System.currentTimeMillis();
        if((currentTime2-startTime2)>4000){

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

            topics.get(randomInt).pop();

            randomInt=MathUtils.random(0,5);

//            if(!topics.get(randomInt).empty()){

                questionBox.setText(topics.get(randomInt).peek().question);

                answers.clear();

                answers.add(topics.get(randomInt).peek().correctAnswer);
                answers.add(topics.get(randomInt).peek().wrongAnswer1);
                answers.add(topics.get(randomInt).peek().wrongAnswer2);
                answers.add(topics.get(randomInt).peek().wrongAnswer3);

//            }

            Collections.shuffle(answers);

            answerButton1.getLabel().setText(answers.get(0));
            answerButton2.getLabel().setText(answers.get(1));
            answerButton3.getLabel().setText(answers.get(2));
            answerButton4.getLabel().setText(answers.get(3));

            uiTable.setVisible(true);

        }
    }

    private void showTrivia(){

        answerButton1.setVisible(true);
        answerButton2.setVisible(true);
        answerButton3.setVisible(true);
        answerButton4.setVisible(true);
        questionBox.setVisible(true);

        uiTable.setVisible(false);

    }

    private boolean stateOfAnswer(boolean isAnswerButton1Pushed,boolean isAnswerButton2Pushed, boolean isAnswerButton3Pushed,boolean isAnswerButton4Pushed){

        if((isAnswerButton1Pushed && answerButton1.getLabel().getText().toString().equals(topics.get(randomInt).peek().correctAnswer))
                || (isAnswerButton2Pushed && answerButton2.getLabel().getText().toString().equals(topics.get(randomInt).peek().correctAnswer))
                || (isAnswerButton3Pushed && answerButton3.getLabel().getText().toString().equals(topics.get(randomInt).peek().correctAnswer))
                || (isAnswerButton4Pushed && answerButton4.getLabel().getText().toString().equals(topics.get(randomInt).peek().correctAnswer))){
//            System.out.println("Correct");
                    isCorrectAnswer=true;

        }else if((isAnswerButton1Pushed && !(answerButton1.getLabel().getText().toString().equals(topics.get(randomInt).peek().correctAnswer)))
                || (isAnswerButton2Pushed && !(answerButton2.getLabel().getText().toString().equals(topics.get(randomInt).peek().correctAnswer)))
                || (isAnswerButton3Pushed && !(answerButton3.getLabel().getText().toString().equals(topics.get(randomInt).peek().correctAnswer)))
                || (isAnswerButton4Pushed && !(answerButton4.getLabel().getText().toString().equals(topics.get(randomInt).peek().correctAnswer)))){
//            System.out.println("UnCorrect");
                    isCorrectAnswer=false;

        }

        return isCorrectAnswer;
    }
}