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

public class LoadingScreen extends BaseScreen {

    //variable of the next LevelScreen
    LevelScreen nextScreen;
    double startTime;
    double currentTime;

    public LoadingScreen (LevelScreen nextScreen){
        super();
        this.nextScreen = nextScreen;
        startTime = System.currentTimeMillis();
    }

    public void initialize()
    {
        BaseActor loadingScreen = new BaseActor(0,0, mainStage);
        loadingScreen.loadTexture( "assets/img/loadingscreen.jpg" );
        loadingScreen.setSize(800,600);
    }

    public void update(float dt)
    {
        currentTime = System.currentTimeMillis();
        if ((currentTime - startTime) > 1500)
        MortenCombat.setActiveScreen(nextScreen);
    }

}
