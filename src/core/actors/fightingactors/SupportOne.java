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
            fighter.setHP(fighter.getHP()+50);
            if (fighter.getHP()>= fighter.getMaxHP())
                fighter.setHP(fighter.getMaxHP());
            return true;
        }
        else return false;


    }

    /**
     * Heals the teammember for 30, spending 50mana. It check if the fightter is a champion class.
     * If not it return false.
     * @param fighterOne champion one of our squad
     * @param fighterTwo champion two of our squad
     * @param fighterThree champion three of our squad
     * @return true if it can be casted; false if it can not be casted
     */

    @Override
    public boolean attackThree(Fighter fighterOne, Fighter fighterTwo, Fighter fighterThree) {
        if (this.enoughMana(50)&& fighterOne instanceof Champion && fighterTwo instanceof Champion
        && fighterThree instanceof Champion){
            fighterOne.setHP(fighterOne.getHP()+30);
            if (fighterOne.getHP()>= fighterOne.getMaxHP())
                fighterOne.setHP(fighterOne.getMaxHP());
            fighterTwo.setHP(fighterTwo.getHP()+30);

            if (fighterTwo.getHP()>= fighterTwo.getMaxHP())
                fighterTwo.setHP(fighterTwo.getMaxHP());
            fighterThree.setHP(fighterThree.getHP()+30);

            if (fighterThree.getHP()>= fighterThree.getMaxHP())
                fighterThree.setHP(fighterThree.getMaxHP());
            this.spendMana(50);
            return true;
        }
        return false;
    }


}
