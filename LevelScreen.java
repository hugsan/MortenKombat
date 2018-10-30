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

    public LevelScreen() {
        this.previousMap = null;
    }

    public LevelScreen(String mapName, LevelScreen previous) {
        this.previousMap = previous;
        if (previousMap.getNextMap() != null)
            previousMap.setNextMap2(this);
        else previousMap.setNextMap(this);
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

        //create the starting point for our hero.
        MapObject startPoint = tma.getRectangleList("start").get(0);
        MapProperties startProps = startPoint.getProperties();
        hero = new Hero((float) startProps.get("x"), (float) startProps.get("y"), mainStage);


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
                MortenCombat.setActiveScreen(nextMap);
        }
        }

        for (BaseActor a : BaseActor.getList(mainStage, "ExitTwo")) {
            if (hero.overlaps(a))
                MortenCombat.setActiveScreen(nextMap2);
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
}