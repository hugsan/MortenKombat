import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;


public class LevelScreen extends BaseScreen
{
    Hero sara;
    public void initialize() 
    {

       TilemapActor tma = new TilemapActor("assets/map.tmx", mainStage);
       for (MapObject obj : tma.getRectangleList("Solid") )
        {
            MapProperties props = obj.getProperties();
            new Solid( (float)props.get("x"), (float)props.get("y"),
                    (float)props.get("width"), (float)props.get("height"),
                    mainStage );
        }
        MapObject startPoint = tma.getRectangleList("start").get(0);
        MapProperties startProps = startPoint.getProperties();
        sara = new Hero( (float)startProps.get("x"), (float)startProps.get("y"), mainStage);
        
    }

    public void update(float dt)
    {
        // hero movement controls
        if (Gdx.input.isKeyPressed(Keys.LEFT))
            sara.accelerateAtAngle(180);
        if (Gdx.input.isKeyPressed(Keys.RIGHT))
            sara.accelerateAtAngle(0);
        if (Gdx.input.isKeyPressed(Keys.UP))
            sara.accelerateAtAngle(90);
        if (Gdx.input.isKeyPressed(Keys.DOWN))
            sara.accelerateAtAngle(270);
        for (BaseActor solid : BaseActor.getList(mainStage, "Solid"))
        {
            sara.preventOverlap(solid);
        }
       
    }
}