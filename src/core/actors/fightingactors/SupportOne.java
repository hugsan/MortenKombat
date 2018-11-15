package core.actors.fightingactors;

import com.badlogic.gdx.math.MathUtils;

public class SupportOne extends SpellCaster{
    public SupportOne(){
        super();
        this.setFighterName("Nami");
        this.setHP(125);
        this.setMaxHP(125);
    }

    /**
     * 3-11 damage and restore 20 mana
     * @param figther
     * @return
     */
    @Override
    public boolean attackOne(Figther figther) {
        figther.setHP(figther.getHP() - MathUtils.random(3,11));
        this.gainMana(20);
        return true;
    }

    /**
     * Heals a figther for 50, spending 30 mana. It checks if the figther is a champion class. if not it return false
     * @param figther target that we want to heal
     * @return 1 if you can cast the spell; -1 if you cannot cast the spell
     */
    @Override
    public boolean attackTwo(Figther figther) {
        if (this.enoughMana(30) && figther instanceof Champion){
            figther.setHP(figther.getHP()+20);
            if (figther.getHP()>= figther.getMaxHP())
                figther.setHP(figther.getMaxHP());
            return true;
        }
        else return false;


    }

    /**
     * temporal 
     * @param figther
     * @return
     */
    @Override
    public boolean attackThree(Figther figther) {
        figther.setHP(figther.getHP() - MathUtils.random(2,7));
        this.gainMana(15);
        return true;
    }


}
