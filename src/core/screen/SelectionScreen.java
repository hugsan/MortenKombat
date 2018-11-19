package core.screen;

import core.framework.BaseActor;
import core.framework.BaseScreen;
import core.actors.CharacterCard;
import core.actors.SelectionArea;

public class SelectionScreen extends BaseScreen {


    @Override
    public void initialize() {

        BaseActor background = new BaseActor(0,0, mainStage);
        background.loadTexture("assets/img/WoodBackground.png");
        background.setSize(800,600);


        for ( int i = 1 ; i < 4 ; i++ ) {
            int pixelX = i * 200 - 50;
            int pixelY = 350;

            SelectionArea selectedHero = new SelectionArea(pixelX,pixelY,mainStage);
            selectedHero.setHeroType(i);
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
