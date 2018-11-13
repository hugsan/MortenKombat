package core.actors.fightingactors;

import com.badlogic.gdx.math.MathUtils;

public class BatFighter extends Figther {

    public BatFighter(){
        super();
        this.setHP(60);
        this.setMaxHP(60);
        this.setFighterName("Bat");
    }


    /**
     * AttackOne from bat deal 8 to 13 damage to another figther.
     * @param fighter target fighter we will attack.
     */
    @Override
    public void attackOne(Figther fighter){
        int damage = MathUtils.random(8,13);
        fighter.setHP(fighter.getHP()-damage);
    }

    /**
     * Attack two, makes percentage damage of the target 35% of current
     * @param figther target figther we will attack.
     */
    @Override
    public void attackTwo(Figther figther){
        figther.setHP(figther.getHP()*(65/100));
    }
}
