package core.screen;

import core.utils.MortenKombat;
import core.framework.BaseActor;
import core.framework.BaseScreen;

/**
 * The LoadingScreen class is a pause between screens, it get the next screen to switch to in the constructor.
 * And when the wait time is over, set the screen to that screen.
 * This screen is a fake loading screen, and is there to give the player a chance to let go of the keys,
 * before switching between the ExploringScreens.
 */

public class LoadingScreen extends BaseScreen {

    private ExploringScreen nextScreen;
    private double startTime;
    private double currentTime;

    public LoadingScreen (ExploringScreen nextScreen){
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
        MortenKombat.setActiveScreen(nextScreen);
    }

}
