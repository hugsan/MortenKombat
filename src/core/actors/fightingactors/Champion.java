package core.actors.fightingactors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import core.framework.BaseGame;

public abstract class Champion extends Fighter {
    private TextButton firstButton;
    private TextButton secondButton;
    private TextButton thirdButton;

    private String spellOneText;
    private String spellTwoText;
    private String spellThreeText;

    public Sound cantclick = Gdx.audio.newSound(Gdx.files.internal("assets/audio/sound/cantclick.mp3"));

    public Champion(Stage s){
        super(s);

        firstButton = new TextButton( "" , BaseGame.textButtonStyle );
        //firstButton.setColor( Color.CYAN );
        firstButton.getLabel().setFontScale(0.4f);
        firstButton.getLabel().setWrap(true);

        secondButton = new TextButton( "",BaseGame.textButtonStyle );
        //secondButton.setColor( Color.CYAN );
        secondButton.getLabel().setFontScale(0.4f);
        firstButton.getLabel().setWrap(true);

        thirdButton = new TextButton( "", BaseGame.textButtonStyle );
        //thirdButton.setColor( Color.CYAN );
        thirdButton.getLabel().setFontScale(0.4f);
        firstButton.getLabel().setWrap(true);

    }

    public String getSpellOneText() {
        return spellOneText;
    }
    public void setSpellOneText(String spellOneText) {
        this.spellOneText = spellOneText;
    }
    public String getSpellTwoText() {
        return spellTwoText;
    }
    public void setSpellTwoText(String spellTwoText) {
        this.spellTwoText = spellTwoText;
    }
    public String getSpellThreeText() {
        return spellThreeText;
    }
    public void setSpellThreeText(String spellThreeText) {
        this.spellThreeText = spellThreeText;
    }

    public TextButton getFirstButton() {
        return firstButton;
    }
    public void setFirstButtonName(String name) {
        firstButton.setText(name);
    }
    public TextButton getSecondButton() {
        return secondButton;
    }
    public void setSecondButtonName(String name) {
        secondButton.setText(name);
    }
    public TextButton getThirdButton() {
        return thirdButton;
    }
    public void setThridButtonName(String name) {
        thirdButton.setText(name);
    }

    public abstract Label getManaBar();

    public abstract void updateManaBar();

    /**
     * return 1 if the ability is possible
     * return -1 if there is missing resources to make the ability
     * @param Fighter target for the attack
     * @return 1: if possible; -1: if not possible.
     */
    public abstract boolean attackThree (Fighter fighterOne, Fighter fighterTwo, Fighter fighterThree );

}
