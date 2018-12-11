package core.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.MathUtils;
import core.utils.MortenKombat;
import core.utils.menu.OptionsMenuScreen;
import core.actors.exploringactors.*;
import core.framework.BaseActor;
import core.framework.BaseScreen;
import core.framework.TilemapActor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * Class that represent the screen where a character can explore a dungeon.
 * The dungeon layout is been read from a tmx file, creating the position for all the other actors at the map.
 * Also tells how all the actors should interact with each other.
 */
public class ExploringScreen extends BaseScreen {
    public static String mapName;
    private static int windTimer = 0;
    public static String mapEffect = "normal";
    private String currentMapEffect;
    private ExploringScreen previousMap;
    private ExploringScreen nextMap = null;
    private ExploringScreen nextMap2 = null;
    private Hero hero;
    public static Music backgroundMusic;
    private Label messageDoor;
    private DialogBox dialogBox;

    // X Y position of the hero when the hero travels to next map
    private float x, y;
    // Z W position of the hero when the hero travels to previous map
    private float z, w;

    private final int FRAMESPRBLOW = 150;

    /**
     * Constructor for ExploringScreen. Initialize the map with previous map
     * and next map to null. Also creates the music for the exploring map.
     */
    public ExploringScreen()  {
        this.previousMap = null;

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/audio/music/backgroundmusic.mp3"));
        backgroundMusic.setLooping(true);
    }

    /**
     * Constructor for ExploringScreen. Initialize the map with previous map object. Nextmaps are
     * set to null. Also changes the value of previous map, so the previous map value of nextmap points to the
     * generated map in this constructor
     * This constructor implements a Map data structure as a tree with next and next1 being their branches
     *
     * @param previousMap ExploringScreen object, where the current map is connected to.
     */
    public ExploringScreen(ExploringScreen previousMap) {
        if (previousMap.getNextMap() != null)
            previousMap.setNextMap2(this);
        else previousMap.setNextMap(this);
        this.previousMap = previousMap;
    }

    /**
     * Method that is been used to initialize the map with all object needed.
     * It will read a tmx file from our assets and create all the object that are contained in the tmx file.
     * Will also create any effect if the map as a effect (Dark, Wind, Ice)
     */
    public void initialize() {

        //been read by enum MapLayout
        TilemapActor tma;
        if ( OptionsMenuScreen.isQuickVersion()  ){
             tma = new TilemapActor("assets/maps/" + mapName + "q.tmx", mainStage);
        }
        else {
             tma = new TilemapActor("assets/maps/" + mapName + ".tmx", mainStage);
        }
        currentMapEffect = mapEffect;

        //Creates all the objects of our Tilemaps from the tmx files.
        createMapObjects(tma, "Solid");
        createMapObjects(tma, "Exit");
        createMapObjects(tma, "Exit2");
        createMapObjects(tma, "GoBack");
        createMapObjects(tma, "Bat");
        createMapObjects(tma, "Torch");
        createMapObjects(tma, "Chest");
        createMapObjects(tma, "Zombie");
        createMapObjects(tma, "Troll");
        createMapObjects(tma, "Medic");
        createMapObjects(tma, "Morten" );
        createMapObjects(tma, "Johan" );
        createMapObjects(tma, "Sokol" );
        createMapObjects(tma, "Lene" );
        createMapObjects(tma, "Door" );
        createMapObjects(tma, "Sign");

        //create the starting point for our hero.
        MapObject startPoint = tma.getRectangleList("Start").get(0);
        MapProperties startProps = startPoint.getProperties();

        //Create our hero, taking the generated starting point before.
        hero = new Hero((float) startProps.get("x"), (float) startProps.get("y"), mainStage);

        if (currentMapEffect.equals("wind")) {
            windBlow();
        }
        if (currentMapEffect.equals("ice")) {
            hero.setAcceleration(200);
            hero.setDeceleration(100);
        }
        if (currentMapEffect.equals("dark")) {
            hero.createLight();
        }

        // running this way because new Hero is probably being run in a threat, and execute before giving values to Z, W
        z = (float) startProps.get("x");
        w = (float) startProps.get("y");
        //setting the starting point, when we go back to the previous map
        if (tma.getRectangleList("PreviousHeroPoint").size() != 0) {
            MapObject previousPoint = tma.getRectangleList("PreviousHeroPoint").get(0);
            MapProperties previousProp = previousPoint.getProperties();
            x = (float) previousProp.get("x");
            y = (float) previousProp.get("y");
        }

        dialogBox = new DialogBox(0,0, uiStage);
        dialogBox.setBackgroundColor( Color.TAN );
        dialogBox.setFontColor( Color.BROWN );
        dialogBox.setDialogSize(600, 300);
        dialogBox.setFontScale(0.80f);
        dialogBox.alignCenter();
        dialogBox.setVisible(false);

        uiTable.add(dialogBox).colspan(3);
    }

    /**
     * Update methods tells what we should change in our game during runtime.
     * We will check if the user moves the hero reading the input of the keyboard
     * Also check all the interaction between the Actors that are in screen.
     * @param dt delta time (time that has elapsed between frames in the game)
     */
    public void update(float dt)  {


        // hero movement controls
        if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A))
            hero.accelerateAtAngle(180);
        if (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D))
            hero.accelerateAtAngle(0);
        if (Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.W))
            hero.accelerateAtAngle(90);
        if (Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.S))
            hero.accelerateAtAngle(270);

        //escape that should implement a pauseScreen
        if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)){

            MortenKombat.setActiveScreen(new PauseScreen(this));
        }

        //update the volume for the game
        backgroundMusic.setVolume(MortenKombat.volume);
        //Checks if our object interact with each other. If they interact their functionality is executed.
        actorObjectInteraction("core.actors.exploringactors.Solid");
        actorObjectInteraction("core.actors.exploringactors.Exit");
        actorObjectInteraction("core.actors.exploringactors.ExitTwo");
        actorObjectInteraction("core.actors.exploringactors.GoBack");
        actorObjectInteraction("core.actors.exploringactors.Bat");
        actorObjectInteraction("core.actors.exploringactors.Chest");
        actorObjectInteraction("core.actors.exploringactors.Zombie");
        actorObjectInteraction("core.actors.exploringactors.Troll");
        actorObjectInteraction("core.actors.exploringactors.Medic");
        actorObjectInteraction ( "core.actors.exploringactors.Morten" );
        actorObjectInteraction ( "core.actors.exploringactors.Johan" );
        actorObjectInteraction ( "core.actors.exploringactors.Lene" );
        actorObjectInteraction ( "core.actors.exploringactors.Sokol" );
        actorObjectInteraction ( "core.actors.exploringactors.Door");
        actorObjectInteraction("core.actors.exploringactors.Sign");

        //Checks if the current map is windy. if it is blows the hero every 1 sec.
        if (currentMapEffect.equals("wind")) {
            windTimer++;
            //every second
            if (windTimer % FRAMESPRBLOW == 0) {
                windBlow();
            }
        } else {
            windTimer = 0;
        }

    } // update end

    /**
     * Method that is been used to simulate a wind push to our hero.
     * Will push the champion to random direction.
     */
    private void windBlow() {
        hero.setMotionAngle(MathUtils.random(0, 360));
        hero.setSpeed(MathUtils.random(10000, 20000));
    }

    /**
     * Method used to create our object from our tile maps.
     * The method reads all the object created in the tile maps. If their object name matc hwith the argument
     * String then they create that specific object.
     *
     * @param tma           TileMapActor where you want to create the object.
     * @param mapattributes Name attribute of our object in the TileMap
     */
    private void createMapObjects(TilemapActor tma, String mapattributes) {
        for (MapObject obj : tma.getRectangleList(mapattributes)) {
            MapProperties props = obj.getProperties();
            switch (mapattributes) {
                case "Exit":
                    new Exit((float) props.get("x"), (float) props.get("y"), mainStage);
                    break;
                case "Sign":
                    Sign s = new Sign( (float)props.get("x"), (float)props.get("y"), mainStage );
                    s.setText( (String)props.get("message") );
                    break;
                case "Exit2":
                    new ExitTwo((float) props.get("x"), (float) props.get("y"), mainStage);
                    break;
                case "GoBack":
                    new GoBack((float) props.get("x"), (float) props.get("y"), mainStage);
                    break;
                case "Solid":
                    new Solid((float) props.get("x"), (float) props.get("y"),
                            (float) props.get("width"), (float) props.get("height"), mainStage);
                    break;
                case "Bat":
                    new Bat((float) props.get("x"), (float) props.get("y"), mainStage);
                    break;
                case "Torch":
                    new Torch((float) props.get("x"), (float) props.get("y"), mainStage);
                    break;
                case "Door":
                    new Door((float) props.get("x"), (float) props.get("y"), mainStage);
                    break;
                case "Chest":
                    new Chest((float) props.get("x"), (float) props.get("y"), mainStage);
                    break;
                case "Zombie":
                    new Zombie((float) props.get("x"), (float) props.get("y"), mainStage);
                    break;
                case "Troll":
                    new Troll((float) props.get("x"), (float) props.get("y"), mainStage);
                    break;
                case "Medic" :
                    new Medic((float) props.get("x"), (float) props.get("y"), mainStage);
                    break;
                case "Morten" :
                    new Morten ((float) props.get("x"), (float) props.get("y"), mainStage  );
                    break;
                case "Lene" :
                    new Lene ((float) props.get("x"), (float) props.get("y"), mainStage  );
                    break;
                case "Sokol" :
                    new Sokol ((float) props.get("x"), (float) props.get("y"), mainStage  );
                    break;
                case "Johan" :
                    new Johan ((float) props.get("x"), (float) props.get("y"), mainStage  );
                    break;
                default:
                    System.out.println("Something went really wrong, contact ITCOM5");
            }
        }
    }

    /**
     * Method used to execute the interactions between the object in our TileMapActor.
     *
     * @param className Name of the class that we want to check of any interaction.
     */
    private void actorObjectInteraction(String className){
        for (BaseActor a : BaseActor.getList(mainStage, className)) {

            switch (className) {
                case "core.actors.exploringactors.Solid":
                    if (hero.overlaps(a)) {
                        hero.preventOverlap(a);
                    }
                    break;
                case "core.actors.exploringactors.Bat":
                    for (BaseActor s : BaseActor.getList(mainStage, "core.actors.exploringactors.Solid")) {
                        if (a.overlaps(s)) {
                            a.preventOverlap(s);
                            a.setMotionAngle(MathUtils.random(0, 360));
                        }
                    }
                    if (a.overlaps(hero)) {

                        musicStop();
                        FightScreen.amountOfEnemies = 1;
                        MortenKombat.setActiveScreen(new FightScreen(this));
                        a.remove();
                    }
                    break;
                case "core.actors.exploringactors.Troll":
                    for (BaseActor s : BaseActor.getList(mainStage, "core.actors.exploringactors.Solid")) {
                        if (a.overlaps(s)) {
                            a.preventOverlap(s);
                            a.setMotionAngle(MathUtils.random(0, 360));
                        }
                    }
                    if (a.overlaps(hero)) {

                        musicStop();
                        FightScreen.amountOfEnemies = 2;
                        MortenKombat.setActiveScreen(new FightScreen(this));
                        a.remove();
                    }
                    break;
                case "core.actors.exploringactors.Zombie":
                    for (BaseActor s : BaseActor.getList(mainStage, "core.actors.exploringactors.Solid")) {
                        if (a.overlaps(s)) {
                            a.preventOverlap(s);
                            a.setMotionAngle(MathUtils.random(0, 360));
                        }
                    }
                    if (a.overlaps(hero)) {

                        musicStop();
                        FightScreen.amountOfEnemies = 3;
                        MortenKombat.setActiveScreen(new FightScreen(this));
                        a.remove();
                    }
                    break;
                case "core.actors.exploringactors.Exit":
                    if (hero.overlaps(a)) {
                        hero.setPosition(getX(), getY());
                        hero.setSpeed(0);
                        MortenKombat.setActiveScreen(new LoadingScreen(nextMap));
                    }
                    break;
                case "core.actors.exploringactors.ExitTwo":
                    if (hero.overlaps(a)) {
                        hero.setPosition(getX(), getY());
                        hero.setSpeed(0);
                        MortenKombat.setActiveScreen(new LoadingScreen(nextMap2));
                    }
                    break;
                case "core.actors.exploringactors.GoBack":
                    if (hero.overlaps(a)) {
                        hero.setPosition(getZ(), getW());
                        hero.setSpeed(0);
                        MortenKombat.setActiveScreen(new LoadingScreen(previousMap));
                    }
                    break;

                case "core.actors.exploringactors.Chest":
                    if (hero.overlaps(a)) {
                        a.remove();
                        System.out.println("Chest opened");
                    }
                    break;
                case "core.actors.exploringactors.Door":
                    if ( FightScreen.getCountKeys () == 3 ) {
                        a.remove ();
                    }
                     break;
                case "core.actors.exploringactors.Sign":
                    Sign sign = (Sign)a;

                    hero.preventOverlap(a);
                    boolean nearby = hero.isWithinDistance(4, sign);

                    if ( nearby && !sign.isViewing() )
                    {
                        dialogBox.setText( sign.getText() );
                        dialogBox.setVisible( true );
                        sign.setViewing( true );
                    }

                    if (sign.isViewing() && !nearby)
                    {
                        dialogBox.setText( " " );
                        dialogBox.setVisible( false );
                        sign.setViewing( false );
                    }
                    break;

                case "core.actors.exploringactors.Medic":
                    if (hero.overlaps(a)) {
                        FightScreen.medicHeal();
                        a.remove();
                        System.out.println("party healed");
                    }
                    break;
                case "core.actors.exploringactors.Morten":
                    if (hero.overlaps(a)) {
                        FightScreen.amountOfEnemies = -4;
                        musicStop();
                        MortenKombat.setActiveScreen(new FightScreen(this));
                        a.remove();
                        System.out.println ("fight Morten" );
                    }
                    break;
                case "core.actors.exploringactors.Johan":
                    if (hero.overlaps(a)) {
                        FightScreen.amountOfEnemies = -2;
                        musicStop();
                        MortenKombat.setActiveScreen(new FightScreen(this));
                        a.remove();
                    }
                    break;
                case "core.actors.exploringactors.Sokol":
                    if (hero.overlaps(a)) {
                        FightScreen.amountOfEnemies = -3;
                        musicStop();
                        MortenKombat.setActiveScreen(new FightScreen(this));
                        a.remove();
                    }
                    break;
                case "core.actors.exploringactors.Lene":
                    if (hero.overlaps(a)) {
                        FightScreen.amountOfEnemies = -1;
                        musicStop();
                        MortenKombat.setActiveScreen(new FightScreen(this));
                        a.remove();

                    }
                    break;

                default:
                    System.out.println("Contact with ITCOM5 group, something went wrong.");
            }
        }
    }

    public static void musicPlay() { backgroundMusic.play(); }

    private static void musicStop() { backgroundMusic.stop(); }

    public void setNextMap(ExploringScreen nextMap) {
        this.nextMap = nextMap;
    }

    public ExploringScreen getNextMap() {
        return nextMap;
    }

    public ExploringScreen getNextMap2() {
        return nextMap2;
    }

    public void setNextMap2(ExploringScreen nextMap2) {
        this.nextMap2 = nextMap2;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public float getW() {
        return w;
    }
}