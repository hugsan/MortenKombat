package core.screen;

import core.framework.BaseActor;
import core.framework.BaseScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.graphics.g2d.Animation;
import core.framework.CharacterCard;
import core.framework.SelectionArea;

public class SelectionScreen extends BaseScreen {


    @Override
    public void initialize() {

        BaseActor background = new BaseActor(0,0, mainStage);
        background.loadTexture("assets/img/SelectionBackground.png");
        background.setSize(800,600);
      //  BaseActor.setWorldBounds(background);

        for ( int i = 1 ; i < 4 ; i++ ) {
            int pixelX = i * 200 - 50;
            int pixelY = 450;

            SelectionArea selectedHero = new SelectionArea(pixelX,pixelY,mainStage);
            selectedHero.setHeroNumber(i);
            selectedHero.setTargetable(true);
        }
        // make a label for names
        // create cards
        for ( int t = 1 ; t < 4 ; t++ )
        {
            for ( int n = 1 ; n < 3 ; n++)
            {
                int x = (n * 100 - 100) + (t * 150);
                int y = 50;

                CharacterCard card = new CharacterCard( x, y, mainStage);
                card.setFighterType(t);
                card.setFighterNumber(n);

            }
        }
    }

    @Override
    public void update(float dt) {

    }
}
