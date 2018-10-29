import com.badlogic.gdx.scenes.scene2d.Stage;

public class Exit extends BaseActor
{
    public Exit(float x, float y, Stage s)
    {
        super(x,y,s);
        loadTexture("assets/cavernopening.png");
        setBoundaryPolygon(8);
    }
}