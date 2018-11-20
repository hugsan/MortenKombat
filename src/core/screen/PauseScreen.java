package core.screen;

import core.framework.BaseActor;
import core.framework.BaseScreen;

public class PauseScreen extends BaseScreen {


    public void initialize() {
        // initialize the Background for the pause screen
        BaseActor pauseBackground = new BaseActor(0, 0, mainStage);
        pauseBackground.loadTexture( "assets/img/PauseScreen.png" );
        pauseBackground.setSize(400,500);
        pauseBackground.toBack();
    }

    @Override
    public void update(float dt) {

    }
}
