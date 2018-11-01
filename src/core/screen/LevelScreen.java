package core.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
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

    Hero hero;


    public void initialize() {
        TilemapActor tma = new TilemapActor("assets/maps/" + mapName + ".tmx", mainStage);

        //Creates all the objects of our Tilemaps
        createMapObjects(tma,"Solid");
        createMapObjects(tma,"Exit");
        createMapObjects(tma,"Exit2");
        createMapObjects(tma,"GoBack");

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

        actorObjectInteraction("core.actors.Solid");
        actorObjectInteraction("core.actors.Exit");
        actorObjectInteraction("core.actors.ExitTwo");
        actorObjectInteraction("core.actors.GoBack");
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
                default:
                    System.out.println("Something went really wrong, contact ITCOM5");
            }
        }
    }
    public void actorObjectInteraction(String className){
        for (BaseActor a : BaseActor.getList(mainStage, className)) {
            if (hero.overlaps(a))
            {
                switch(className){
                    case "core.actors.Solid":
                        hero.preventOverlap(a);
                        break;
                    case "core.actors.Exit":
                        hero.setPosition( getX(),getY() );
                        hero.setSpeed(0);
                        MortenCombat.setActiveScreen(nextMap);
                        break;
                    case "core.actors.ExitTwo":
                        hero.setPosition( getX(),getY() );
                        hero.setSpeed(0);
                        MortenCombat.setActiveScreen(nextMap2);
                        break;
                    case "core.actors.GoBack":
                        hero.setPosition( getZ(),getW() );
                        hero.setSpeed(0);
                        MortenCombat.setActiveScreen(previousMap);
                        break;
                    default:
                        System.out.println("Contact ITCOM5");
                }
            }
        }
    }
}


