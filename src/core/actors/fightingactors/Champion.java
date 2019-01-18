package core.actors.fightingactors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import core.framework.BaseGame;
import core.screen.FightScreen;

/**
 * Abstract class that extends Fighter to implement ally fighter.
 * We create buttons and a extra attackThree abstract method compared to Fighter.
 */
public abstract class Champion extends Fighter implements Poisonable{
    private TextButton firstButton;
    private TextButton secondButton;
    private TextButton thirdButton;

    private String spellOneText;
    private String spellTwoText;
    private String spellThreeText;

    private int exp = 0;
    private int level = 1;

    public boolean stun = false;

    public Sound cantclick = Gdx.audio.newSound(Gdx.files.internal("assets/audio/sound/cantclick.mp3"));

    public Champion(Stage s){
        super(s);

        firstButton = new TextButton( "" , BaseGame.textButtonStyle );
        firstButton.getLabel().setFontScale(0.37f);
        firstButton.getLabel().setColor(Color.GRAY);
        firstButton.getLabel().setSize(135,40);
        firstButton.getLabel().setWrap(true);

        secondButton = new TextButton( "",BaseGame.textButtonStyle );
        secondButton.getLabel().setFontScale(0.38f);
        secondButton.getLabel().setColor(Color.GRAY);
        secondButton.setSize(135,40);
        secondButton.getLabel().setWrap(true);

        thirdButton = new TextButton( "", BaseGame.textButtonStyle );
        thirdButton.getLabel().setFontScale(0.37f);
        thirdButton.getLabel().setColor(Color.GRAY);
        thirdButton.getLabel().setSize(135,40);
        thirdButton.getLabel().setWrap(true);

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
    public void gainExp(int expGained) {
        exp = exp + expGained;
        if (exp > 500) {
            exp = exp - 500;
            level++;
        }
        System.out.println(getFighterName() + " Level:" + level + " Exp:" + exp);
    }

    public abstract Label getManaBar();

    public abstract void updateManaBar();

    /**
     * abstract method used for champions. In case that you want to use a single target here only use in the
     * implementation fightOne
     * @param fighterOne target for the attack
     * @param fighterTwo target for the attack
     * @param fighterThree target for the attack
     * @return true if the attack can be done, false if the attack can not be done
     */
    public abstract boolean attackThree (Fighter fighterOne, Fighter fighterTwo, Fighter fighterThree );

}
