package core.actors.fightingactors;

import com.badlogic.gdx.math.MathUtils;

public class SkeletonFighter extends Fighter {

    public SkeletonFighter () {

        super();
        this.setHP(100);
        this.setMaxHP(100);
        this.setFighterName("Skeleton");
    }
    /**
     * AttackOne from bat deal 8 to 13 damage to another figther.
     * @param fighter target fighter we will attack.
     */
    @Override
    public boolean attackOne(Fighter fighter) {
        int damage = MathUtils.random(8,13);
        fighter.setHP(fighter.getHP()-damage);
        return true;
    }
    /**
     * Attack two, makes percentage damage of the target 35% of current
     * @param fighter target fighter we will attack.
     */
    @Override
    public boolean attackTwo(Fighter fighter) {
        fighter.setHP(fighter.getHP()*65/100);
        return true;

    }
}
