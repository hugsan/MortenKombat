package core.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import core.framework.BaseActor;
import core.framework.BaseScreen;
import core.utils.MortenKombat;
import core.utils.menu.MainMenuScreen;

public class TutorialScreen extends BaseScreen {

    private BaseActor[] screenSlide = new BaseActor[6];
    private int j = 1;
    BaseActor p1, p2, p3, p4, p5, p6, title1;

    public void initialize() {
        title1 = new BaseActor(150,0, mainStage);
        title1.loadTexture("assets/img/PressToContinue.png");
        title1.addAction( Actions.forever ( Actions.sequence(Actions.fadeOut (0.60f),Actions.fadeIn ( 0.60f ))));

        p1 = new BaseActor(0,0,mainStage);
        p1.loadTexture("assets/img/TutorialP1.png");
        p1.setSize(800,600);

        p2 = new BaseActor(0,0,mainStage);
        p2.loadTexture("assets/img/TutorialP2.png");
        p2.setSize(800,600);

        p3 = new BaseActor(0,0,mainStage);
        p3.loadTexture("assets/img/TutorialP3.png");
        p3.setSize(800,600);

        p4 = new BaseActor(0,0,mainStage);
        p4.loadTexture("assets/img/TutorialP4.png");
        p4.setSize(800,600);

        p5 = new BaseActor(0,0,mainStage);
        p5.loadTexture("assets/img/TutorialP5.png");
        p5.setSize(800,600);

        p6 = new BaseActor(0,0,mainStage);
        p6.loadTexture("assets/img/TutorialP6.png");
        p6.setSize(800,600);

        p1.toFront();
        title1.toFront();

    }
    @Override
    public void update(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
        {
            if (j == 6){
                MortenKombat.startGame();
                MainMenuScreen.menuMusicStop();
            }
            if (j == 1){
                p2.toFront();
                title1.toFront();
            }
            if (j == 2){
                p3.toFront();
                title1.toFront();
            }
            if (j == 3) {
                p4.toFront();
                title1.toFront();
            }
            if (j == 4) {
                p5.toFront();
                title1.toFront();
            }
            if (j == 5) {
                p6.toFront();
                title1.toFront();
            }
            j++;
        }
    }
}
