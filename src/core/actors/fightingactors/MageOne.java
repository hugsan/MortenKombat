package core.actors.fightingactors;

import com.badlogic.gdx.math.MathUtils;

public class MageOne extends SpellCaster{

    public MageOne(){
        super();
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


    @Override
    public boolean attackThree(Fighter fighterOne, Fighter fighterTwo, Fighter fighterThree){
        if (this.getMana()<35)
            return false;
        else{
            fighterOne.setHP(fighterOne.getHP()-20);
            fighterTwo.setHP(fighterTwo.getHP()-20);
            fighterThree.setHP(fighterThree.getHP()-20);
            this.setMana(this.getMana() - 35);
            return true;
        }
    }
    /**
    @Override
    public boolean attackThree(Fighter fighter, Fighter fighter2) {
        if (this.getMana()<35)
            return false;
        else{
            fighter.setHP(fighter.getHP()-20);
            fighter2.setHP(fighter2.getHP()-20);
            this.setMana(this.getMana() - 35);
            return true;
        }
    }
    @Override
    public boolean attackThree(Fighter fighter, Fighter fighter2, Fighter fighter3) {
        if (this.getMana()<35)
            return false;
        else{
            fighter.setHP(fighter.getHP()-20);
            fighter2.setHP(fighter2.getHP()-20);
            fighter3.setHP(fighter3.getHP()-20);
            this.setMana(this.getMana() - 35);
            return true;
        }

    }*/


    }
