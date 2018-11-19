package core.actors.fightingactors;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class MageOne extends SpellCaster {

    public MageOne(Stage s){
        super(s);
        this.setFighterName("Dimitrova");
        this.setHP(150);
        this.setMaxHP(150);
    }

    /**
     * Attack for 3-8 damage, and restore 15 mana
     * @param fighter
     */
    @Override
    public boolean attackOne(Fighter fighter){
        fighter.setHP(fighter.getHP()- MathUtils.random(3,8));
        this.gainMana(15);
        return true;

    }

    /**
     * Attack 50 and decrease mana 55
     * @param fighter
     */
    @Override
    public boolean attackTwo(Fighter fighter){
        if (this.enoughMana(55))
            return false;
        else{
            fighter.setHP(fighter.getHP()-50);
            this.spendMana(55);
            return true;
        }
    }

    /**
     * Attacks that deal 20 dmg to all enemies for 35 mana.
     * @param fighter
     * @return
     */
    @Override
    public boolean attackThree(Fighter fighter){
        if (this.getMana()<35)
            return false;
        else{
            fighter.setHP(fighter.getHP()-20);
            this.setMana(this.getMana() - 35);
            return true;
        }
    }





}
