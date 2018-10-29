import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;


public class LevelScreen extends BaseScreen
{
    Hero hero;
    public void initialize() 
    {

       TilemapActor tma = new TilemapActor("assets/firstmap.tmx", mainStage);
       //create the Solid object from our tilemap
       for (MapObject obj : tma.getRectangleList("Solid") )
        {
            MapProperties props = obj.getProperties();
            new Solid( (float)props.get("x"), (float)props.get("y"),
                    (float)props.get("width"), (float)props.get("height"),
                    mainStage );
        }

        //create our exit from our map, used to move to another map
        for (MapObject obj : tma.getRectangleList("Exit") )
        {
            MapProperties props = obj.getProperties();
            new Exit( (float)props.get("x"), (float)props.get("y"), mainStage );
        }

        MapObject startPoint = tma.getRectangleList("start").get(0);
        MapProperties startProps = startPoint.getProperties();
        hero = new Hero( (float)startProps.get("x"), (float)startProps.get("y"), mainStage);


        
    }

    public void update(float dt)
    {
        // hero movement controls
        if (Gdx.input.isKeyPressed(Keys.LEFT))
            hero.accelerateAtAngle(180);
        if (Gdx.input.isKeyPressed(Keys.RIGHT))
            hero.accelerateAtAngle(0);
        if (Gdx.input.isKeyPressed(Keys.UP))
            hero.accelerateAtAngle(90);
        if (Gdx.input.isKeyPressed(Keys.DOWN))
            hero.accelerateAtAngle(270);
        for (BaseActor solid : BaseActor.getList(mainStage, "Solid"))
        {
            hero.preventOverlap(solid);
        }
        for (BaseActor exit : BaseActor.getList(mainStage, "Exit")) {
            if (hero.overlaps(exit))
                MortenCombat.setActiveScreen(new SecondLevel());
        }

    }



}