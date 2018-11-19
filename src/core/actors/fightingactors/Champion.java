package core.actors.fightingactors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public abstract class Champion extends Fighter {
    private Button firstButton;
    private Button secondButton;
    private Button thirdButton;

    public Champion(Stage s){
        super(s);
        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
        Texture buttonTex = new Texture( Gdx.files.internal("assets/img/button_attack-one.png") );
        TextureRegion buttonRegion =  new TextureRegion(buttonTex);
        buttonStyle.up = new TextureRegionDrawable( buttonRegion );

        firstButton = new Button( buttonStyle );
        firstButton.setColor( Color.CYAN );

        buttonStyle = new Button.ButtonStyle();
        buttonTex = new Texture( Gdx.files.internal("assets/img/button_attack-two.png") );
        buttonRegion =  new TextureRegion(buttonTex);
        buttonStyle.up = new TextureRegionDrawable( buttonRegion );

        secondButton = new Button( buttonStyle );
        secondButton.setColor( Color.CYAN );

        buttonStyle = new Button.ButtonStyle();
        buttonTex = new Texture( Gdx.files.internal("assets/img/button_attack-three.png") );
        buttonRegion =  new TextureRegion(buttonTex);
        buttonStyle.up = new TextureRegionDrawable( buttonRegion );

        thirdButton = new Button( buttonStyle );
        thirdButton.setColor( Color.CYAN );

    }

    public Button getFirstButton() {
        return firstButton;
    }

    public Button getSecondButton() {
        return secondButton;
    }

    public Button getThirdButton() {
        return thirdButton;
    }

    /**
     * return 1 if the ability is possible
     * return -1 if there is missing resources to make the ability
     * @param fighter target for the attack
     * @return 1: if possible; -1: if not possible.
     */
    public abstract boolean attackThree (Fighter fighter);

}
