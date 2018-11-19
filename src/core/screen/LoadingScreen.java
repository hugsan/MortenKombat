package core.screen;
import core.MortenCombat;
import core.framework.BaseActor;
import core.framework.BaseScreen;

public class LoadingScreen extends BaseScreen {

    // Variable of the next LevelScreen
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
