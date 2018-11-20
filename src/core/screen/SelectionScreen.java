package core.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import core.MortenCombat;
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

        BaseActor fighterTitle = new BaseActor(162,390,mainStage);
        fighterTitle.loadTexture("assets/img/FighterHeadLine.png");

        BaseActor mageTitle = new BaseActor(362,390,mainStage);
        mageTitle.loadTexture("assets/img/MageHeadLine.png");

        BaseActor supportTitle = new BaseActor(562,390,mainStage);
        supportTitle.loadTexture("assets/img/SupportHeadLine.png");

        for ( int i = 1 ; i < 4 ; i++ ) {
            int pixelX = i * 200 - 50;
            int pixelY = 350;

            SelectionArea selectedHero = new SelectionArea(pixelX,pixelY,mainStage);
            selectedHero.setHeroType(i);
            selectedHero.setTargetable(true);
        }
        // make a label for names
        fighterTitle.toFront();
        mageTitle.toFront();
        supportTitle.toFront();

        // create cards
        for ( int t = 1 ; t < 4 ; t++ )
        {
            for ( int n = 1 ; n < 3 ; n++)
            {
                int x = (n * 100 - 100) + (t * 150);
                int y = 50;

                CharacterCard card = new CharacterCard( x, y, mainStage);
                card.createCard(n,t);
            }
        }

        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();

        Texture buttonTex = new Texture( Gdx.files.internal("assets/img/buttons/Start.png") );
        TextureRegion buttonRegion = new TextureRegion(buttonTex);
        buttonStyle.up = new TextureRegionDrawable( buttonRegion );
        Button selectionMenuStart = new Button( buttonStyle );

        selectionMenuStart.setPosition(650, 0);
        uiStage.addActor(selectionMenuStart);
        selectionMenuStart.setSize(150,50);

        selectionMenuStart.addListener(
                (Event e) ->
                {
                    if ( !(e instanceof InputEvent) )
                        return false;
                    //mouseover
                    if ( !((InputEvent)e).getType().equals(InputEvent.Type.touchDown) )
                        return false;
                    this.dispose();
                    MortenCombat.startGame();
                    return false;
                }
        );
    }
    @Override
    public void update(float dt) {
    }
}
