package core.actors.fightingactors;

import com.badlogic.gdx.math.MathUtils;

public class WarriorOne extends Champion {
    public WarriorOne(){
        setFighterName("Paul");
        setHP(200);
        setMaxHP(200);
    }

    /**
     * does 8-23 damage to enemy
     * @param figther enemy target
     */
    @Override
    public boolean attackOne(Figther figther){
        figther.setHP(figther.getHP()-MathUtils.random(8,23));
        return true;
    }

    /**
     * Makes 40% of missing target life
     * @param figther enemy target
     */
    @Override
    public boolean attackTwo (Figther figther){
        figther.setHP((int)0.40*(figther.getMaxHP()-figther.getHP()));
        return true;
    }

    /**
     * Deals 3-15 Damage. If the champion kills the target, he heals for 20% of his life.
     * @param figther
     */
    @Override
    public boolean attackThree (Figther figther){
        figther.setHP(figther.getHP()-MathUtils.random(3,15));
        if (figther.getHP()<= 0 ){
            this.setHP((int)(this.getHP()+ 0.20*this.getMaxHP()));
            if (this.getHP() >= this.getMaxHP())
                this.setHP(this.getHP());
        }
        return true;
    }
}
