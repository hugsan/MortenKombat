package core.actors.fightingactors;

import com.badlogic.gdx.math.MathUtils;

public class MageOne extends SpellCaster implements AreaDamageAbility{

    public MageOne(){
        super();
        this.setFighterName("Dimitrova");
        this.setHP(150);
        this.setMaxHP(150);
    }

    /**
     * Attack for 3-8 damage, and restore 15 mana
     * @param figther
     */
    @Override
    public boolean attackOne(Figther figther){
        figther.setHP(figther.getHP()- MathUtils.random(3,8));
        this.gainMana(15);
        return true;

    }

    /**
     * Attack 50 and decrease mana 55
     * @param figther
     */
    @Override
    public boolean attackTwo(Figther figther){
        if (this.enoughMana(55))
            return false;
        else{
            figther.setHP(figther.getHP()-50);
            this.spendMana(55);
            return true;
        }
    }

    /**
     * Attacks that deal 20 dmg to all enemies for 35 mana.
     * @param figther
     * @return
     */
    @Override
    public boolean attackThree(Figther figther){
        if (this.getMana()<35)
            return false;
        else{
            figther.setHP(figther.getHP()-20);
            this.setMana(this.getMana() - 35);
            return true;
        }
    }
    @Override
    public boolean attackThree(Figther figther, Figther figther2) {
        if (this.getMana()<35)
            return false;
        else{
            figther.setHP(figther.getHP()-20);
            figther2.setHP(figther2.getHP()-20);
            this.setMana(this.getMana() - 35);
            return true;
        }
    }
    @Override
    public boolean attackThree(Figther figther, Figther figther2, Figther figther3) {
        if (this.getMana()<35)
            return false;
        else{
            figther.setHP(figther.getHP()-20);
            figther2.setHP(figther2.getHP()-20);
            figther3.setHP(figther3.getHP()-20);
            this.setMana(this.getMana() - 35);
            return true;
        }

    }


    }
