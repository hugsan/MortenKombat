package core.actors.fightingactors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import core.framework.BaseGame;


public abstract class SpellCaster extends Champion {

    public Sound missingMana = Gdx.audio.newSound(Gdx.files.internal("assets/audio/sound/outmana.mp3"));
    private Label manaBar;

    public SpellCaster(Stage s){
        super(s);

        manaBar = new Label("Mana: ", BaseGame.labelStyle);
        manaBar.setColor( Color.CYAN );
        manaBar.setFontScale(0.6f);
    }
    private final int maxMana = 100;
    private int mana = 100;

    public void manaRegeneration(){
        mana += 10;
        if (mana >= maxMana)
            mana = maxMana;
    }

    public Label getManaBar() { return manaBar; }

    public void updateManaBar() {
        manaBar.setText(getMana() +"/"+ getMaxMana());
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getMaxMana() {
        return maxMana;
    }
    public void gainMana(int amount) {
        this.setMana(this.getMana() + amount );
        if (this.getMana()> this.getMaxMana())
            this.setMana(this.getMaxMana());
    }
    public void spendMana(int amount){
        this.setMana(this.getMana() - amount);
    }

    public boolean enoughMana(int amount){
        if (this.getMana() >= amount)
            return true;
        else {
            missingMana.play();
            missingMana.play(1f);
            return false;
        }
    }
}
