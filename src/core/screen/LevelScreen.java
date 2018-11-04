package core.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.math.MathUtils;
import core.MortenCombat;
import core.actors.*;
import core.actors.Solid;
import core.framework.BaseActor;
import core.framework.BaseScreen;
import core.framework.TilemapActor;


public class LevelScreen extends BaseScreen {
    public static String mapName ;
    private LevelScreen previousMap;
    private LevelScreen nextMap = null;
    private LevelScreen nextMap2 = null;
    private Hero hero;
    private Music backgroundMusic;

    // X Y position of the hero when the hero travels to next map
    private float x,y;
    // Z W position of the hero when the hero travels to previous map
    private float z,w;

    /**
     * Constructor for LevelScreen. Initialize the map withprevious map
     * and next map to null
     */
    public LevelScreen() {
        this.previousMap = null;
    }

    /**
     * Constructor for LevelScreen. Initialize the map with previous map object. Nextmaps are
     * set to null. Also changes the value of previous map, so the previous map value of nextmap points to the
     * generated map in this constructor
     * This constructor implements a Map data structure as a tree with next and next1 being their branches
     * @param previousMap LevelScreen object, where the current map is connected to.
     */
    public LevelScreen( LevelScreen previousMap) {
        if (previousMap.getNextMap() != null)
            previousMap.setNextMap2(this);
        else previousMap.setNextMap(this);
        this.previousMap = previousMap;
    }




    public void initialize() {
        TilemapActor tma = new TilemapActor("assets/maps/" + mapName + ".tmx", mainStage);

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/audio/music/backgroundmusic.mp3"));
        backgroundMusic.setVolume(0.5f);
        backgroundMusic.setLooping(true);
        backgroundMusic.play();

        //Creates all the objects of our Tilemaps
        createMapObjects(tma,"Solid");
        createMapObjects(tma,"Exit");
        createMapObjects(tma,"Exit2");
        createMapObjects(tma,"GoBack");
        createMapObjects(tma, "Bat");

        //create the starting point for our hero.
        MapObject startPoint = tma.getRectangleList("Start").get(0);
        MapProperties startProps = startPoint.getProperties();

        //Create our hero, taking the generated starting point before.
        hero = new Hero( (float) startProps.get("x"), (float) startProps.get("y"), mainStage);

        //create acceleration for ice map
        if (mapName.equals("map4"))
        {
            hero.setAcceleration(200);
            hero.setDeceleration(100);
        }
        // running this way because new Hero is probably being run in a threat, and execute before giving values to Z, W
        z = (float) startProps.get("x");
        w = (float) startProps.get("y");
        //setting the starting point, when we go back to the previous map
        if (tma.getRectangleList("PreviousHeroPoint").size() !=0){
            MapObject previousPoint = tma.getRectangleList("PreviousHeroPoint").get(0);
            MapProperties previousProp = previousPoint.getProperties();
            x = (float) previousProp.get("x");
            y = (float) previousProp.get("y");
        }
    }

    public void update(float dt) {
        // hero movement controls

        if (Gdx.input.isKeyPressed(Keys.LEFT))
            hero.accelerateAtAngle(180);
        if (Gdx.input.isKeyPressed(Keys.RIGHT))
            hero.accelerateAtAngle(0);
        if (Gdx.input.isKeyPressed(Keys.UP))
            hero.accelerateAtAngle(90);
        if (Gdx.input.isKeyPressed(Keys.DOWN))
            hero.accelerateAtAngle(270);

        //Checks if our object interact with each other. If they interact their functionality is executed.
        actorObjectInteraction("core.actors.Solid");
        actorObjectInteraction("core.actors.Exit");
        actorObjectInteraction("core.actors.ExitTwo");
        actorObjectInteraction("core.actors.GoBack");
        actorObjectInteraction("core.actors.Bat");


    }

    public void setNextMap(LevelScreen nextMap) {
        this.nextMap = nextMap;
    }

    public LevelScreen getNextMap() {
        return nextMap;
    }

    public LevelScreen getNextMap2() {
        return nextMap2;
    }

    public void setNextMap2(LevelScreen nextMap2) {
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

    /**
     * Method used to create our object from our tile maps.
     *
     * The method reads all the object created in the tile maps. If their object name matc hwith the argument
     * String then they create that specific object.
     * @param tma TileMapActor where you want to create the object.
     * @param mapattributes Name attribute of our object in the TileMap
     */
    public void createMapObjects(TilemapActor tma, String mapattributes) {
        for (MapObject obj : tma.getRectangleList(mapattributes)) {
            MapProperties props = obj.getProperties();
            switch (mapattributes) {
                case "Exit":
                    new Exit((float) props.get("x"), (float) props.get("y"), mainStage);
                    break;
                case "Exit2":
                    new ExitTwo((float) props.get("x"), (float) props.get("y"), mainStage);
                    break;
                case "GoBack":
                    new GoBack((float) props.get("x"), (float) props.get("y"), mainStage);
                    break;
                case "Solid":
                    new Solid((float) props.get("x"), (float) props.get("y"),
                            (float) props.get("width"), (float) props.get("height"),
                            mainStage);
                    break;
                case "Bat":
                    new Bat( (float) props.get("x"), (float) props.get("y"), mainStage);
                    break;
                default:
                    System.out.println("Something went really wrong, contact ITCOM5");
            }
        }
    }

    /**
     * Method used to execute the interactions between the object in our TileMapActor.
     * @param className Name of the class that we want to check of any interaction.
     */
    public void actorObjectInteraction(String className){
        for (BaseActor a : BaseActor.getList(mainStage, className)) {

                switch(className){
                    case "core.actors.Solid":
                        if (hero.overlaps(a)){
                        hero.preventOverlap(a);}
                        break;
                    case "core.actors.Bat":
                        for (BaseActor s: BaseActor.getList(mainStage, "core.actors.Solid")){
                            if (a.overlaps(s)){
                                a.preventOverlap(s);
                                a.setMotionAngle( MathUtils.random(0,360));
                            }

                        }
                        break;
                    case "core.actors.Exit":
                        if (hero.overlaps(a)){
                        hero.setPosition( getX(),getY() );
                        hero.setSpeed(0);
                        MortenCombat.setActiveScreen(nextMap);}
                        break;
                    case "core.actors.ExitTwo":
                        if (hero.overlaps(a)){
                        hero.setPosition( getX(),getY() );
                        hero.setSpeed(0);
                        MortenCombat.setActiveScreen(nextMap2);}
                        break;
                    case "core.actors.GoBack":
                        if (hero.overlaps(a)){
                        hero.setPosition( getZ(),getW() );
                        hero.setSpeed(0);
                        MortenCombat.setActiveScreen(previousMap);}
                        break;
                    default:
                        System.out.println("Contact with ITCOM5 group, something went wrong.");

                }


        }
    }
}


