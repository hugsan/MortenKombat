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
     * @param fighter
     * @return
     */
    @Override
    public boolean attackOne(Fighter fighter) {
        fighter.setHP(fighter.getHP() - MathUtils.random(3,11));
        this.gainMana(20);
        return true;
    }

    /**
     * Heals a fighter for 50, spending 30 mana. It checks if the fighter is a champion class. if not it return false
     * @param fighter target that we want to heal
     * @return 1 if you can cast the spell; -1 if you cannot cast the spell
     */
    @Override
    public boolean attackTwo(Fighter fighter) {
        if (this.enoughMana(30) && fighter instanceof Champion){
            fighter.setHP(fighter.getHP()+20);
            if (fighter.getHP()>= fighter.getMaxHP())
                fighter.setHP(fighter.getMaxHP());
            return true;
        }
        else return false;


    }

    /**
     * temporal
     * @param fighter
     * @return
     */
    @Override
    public boolean attackThree(Fighter fighter) {
        fighter.setHP(fighter.getHP() - MathUtils.random(2,7));
        this.gainMana(15);
        return true;
    }


}
