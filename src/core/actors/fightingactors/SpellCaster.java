package core.actors.fightingactors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class SpellCaster extends Champion {

    public Sound missingMana = Gdx.audio.newSound(Gdx.files.internal("assets/audio/sound/outmana.mp3"));
    public SpellCaster(Stage s){
        super(s);

    }
    private final int maxMana = 100;
    private int mana = 100;


    private void manaRegeneration(){
        mana += 10;
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
            return false;
        }
    }

}
