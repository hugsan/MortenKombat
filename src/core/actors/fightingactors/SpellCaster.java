package core.actors.fightingactors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import core.framework.BaseGame;
import core.utils.FighterBalanceVariables;


public abstract class SpellCaster extends Champion {
    //configuration variables for SpellCasters
    private final int MAXMANA = FighterBalanceVariables.MAXMANA;
    private int mana = FighterBalanceVariables.MANA;


    public Sound missingMana = Gdx.audio.newSound(Gdx.files.internal("assets/audio/sound/outmana.mp3"));
    private Label manaBar;

    public SpellCaster(Stage s){
        super(s);

        manaBar = new Label("Mana: ", BaseGame.labelStyle);
        manaBar.setColor( Color.CYAN );
        manaBar.setFontScale(0.6f);
    }


    /**
     * Regenerates mana by 10 each time it is called, until the mana value reach MAXMANA
     */
    public void manaRegeneration(){
        mana += FighterBalanceVariables.MANAREGENERATION;
        if (mana >= MAXMANA)
            mana = MAXMANA;
    }

    public Label getManaBar() { return manaBar; }

    /**
     * Update the value of mana.
     */
    public void updateManaBar() {
        manaBar.setText(getMana() +"/"+ getMAXMANA ());
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getMAXMANA() {
        return MAXMANA;
    }

    /**
     * Give a certain amount of mana to the hero
     * @param amount represents managain
     */
    public void gainMana(int amount) {
        this.setMana(this.getMana() + amount );
        if (this.getMana()> this.getMAXMANA ())
            this.setMana(this.getMAXMANA ());
    }

    /**
     * Subtract a certain amount of mana from the hero`s mana.
     * @param amount the value deducted
     */
    public void spendMana(int amount){
        this.setMana(this.getMana() - amount);
    }

    /**
     * Check if there is enough mana of the hero, if not sound is played to notify the user
     * @param amount checked mana value
     * @return true if there is and false if there is not enough mana
     */
    public boolean enoughMana(int amount){
        if (this.getMana() >= amount)
            return true;
        else {
            missingMana.play(1f);
            return false;
        }
    }
}
