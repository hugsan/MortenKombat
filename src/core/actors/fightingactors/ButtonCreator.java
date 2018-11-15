package core.actors.fightingactors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


public class ButtonCreator{

    Button createdButton;

    public ButtonCreator(Stage uiStage, String buttonAsset){
        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();

        Texture buttonTex = new Texture( Gdx.files.internal(buttonAsset) );
        TextureRegion buttonRegion =  new TextureRegion(buttonTex);
        buttonStyle.up = new TextureRegionDrawable( buttonRegion );

        this.createdButton = new Button( buttonStyle );
        createdButton.setColor( Color.CYAN );
        uiStage.addActor(createdButton);
    }

    public Button getCreatedButton(){
        return createdButton;
    }


}
