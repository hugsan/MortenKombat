import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.sun.corba.se.spi.servicecontext.ServiceContextData;

import java.util.logging.Level;


public class LevelScreen extends BaseScreen {
    public static String mapName ;
    private LevelScreen previousMap;
    private LevelScreen nextMap = null;
    private LevelScreen nextMap2 = null;


    private float x,y;
    private float z;


    private float w;

    public LevelScreen() {
        this.previousMap = null;
    }

    public LevelScreen( LevelScreen previousMap) {
        if (previousMap.getNextMap() != null)
            previousMap.setNextMap2(this);
        else previousMap.setNextMap(this);
        this.previousMap = previousMap;
    }

    Hero hero;


    public void initialize() {
        System.out.println(mapName);
        TilemapActor tma = new TilemapActor("assets/" + mapName + ".tmx", mainStage);
        //create the Solid object from our tilemap
        for (MapObject obj : tma.getRectangleList("Solid")) {
            MapProperties props = obj.getProperties();
            new Solid((float) props.get("x"), (float) props.get("y"),
                    (float) props.get("width"), (float) props.get("height"),
                    mainStage);
        }

        //create our exit from our map, used to move to another map
        for (MapObject obj : tma.getRectangleList("Exit")) {
            MapProperties props = obj.getProperties();
            new Exit((float) props.get("x"), (float) props.get("y"), mainStage);
        }
        //create our exit2 from our map, used to move to another map
        for (MapObject obj : tma.getRectangleList("Exit2")) {
            MapProperties props = obj.getProperties();
            new ExitTwo((float) props.get("x"), (float) props.get("y"), mainStage);
        }

        for (MapObject obj : tma.getRectangleList("GoBack")) {
            MapProperties props = obj.getProperties();
            new GoBack((float) props.get("x"), (float) props.get("y"), mainStage);
        }

        //create the starting point for our hero.
        MapObject startPoint = tma.getRectangleList("Start").get(0);
        MapProperties startProps = startPoint.getProperties();



        hero = new Hero( (float) startProps.get("x"), (float) startProps.get("y"), mainStage);

        //create acceleration for ice map
        if (mapName.equals("map4"))
        {
            hero.setAcceleration(200);
            hero.setDeceleration(100);
        }
        // running this way coz new Hero is probably being run in a threat, and execute before giving values to Z, W
        z = (float) startProps.get("x");
        w = (float) startProps.get("y");
        //setting the starting point, when we go back to the previous map
        MapObject previousPoint = tma.getRectangleList("PreviousHeroPoint").get(0);
        MapProperties previousProp = previousPoint.getProperties();
        x = (float) previousProp.get("x");
        y = (float) previousProp.get("y");


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
        //prevent the hero to colide with any solid
        for (BaseActor solid : BaseActor.getList(mainStage, "Solid")) {
            hero.preventOverlap(solid);
        }
        //check when the hero hit the gate "Exit" and changes the map
        if (nextMap != null){
        for (BaseActor a : BaseActor.getList(mainStage, "Exit")) {
            if (hero.overlaps(a))
            {
                hero.setPosition( getX(),getY() );
                hero.setSpeed(0);
                MortenCombat.setActiveScreen(nextMap);
            }
        }
        }
        if (nextMap2 != null) {
            for (BaseActor a : BaseActor.getList(mainStage, "ExitTwo")) {
                if (hero.overlaps(a)){
                    hero.setPosition( getX(),getY() );
                    hero.setSpeed(0);
                    MortenCombat.setActiveScreen(nextMap2);
                }
            }
        }
        if (previousMap != null){
            for (BaseActor a : BaseActor.getList(mainStage, "GoBack")) {
                if (hero.overlaps(a)) {
                    hero.setPosition( getZ(), getW());
                    hero.setSpeed(0);
                    MortenCombat.setActiveScreen(previousMap);
                }
            }

        }

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



}