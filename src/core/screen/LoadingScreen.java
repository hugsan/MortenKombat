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
    LevelScreen previousMap;

    public void initialize()
    {
        //initialize the map with the black screen or loading picture
        BaseActor fightBackground = new BaseActor(0,0, mainStage);
        fightBackground.loadTexture( "assets/img/loadingscreen.jpg" );
        fightBackground.setSize(800,600);
    }

    public void update(float dt)
    {
        //make something so when 1.5s has past you call to the next map from LevelScreen

        //dispose, delete this screen from memory befor
    }
    //setter of the previous map
}
