package core.actors.fightingactors;

import com.badlogic.gdx.math.MathUtils;

public class SkeletonFighter extends Figther {

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
    public boolean attackOne(Figther fighter) {
        int damage = MathUtils.random(8,13);
        fighter.setHP(fighter.getHP()-damage);
        return true;
    }
    /**
     * Attack two, makes percentage damage of the target 35% of current
     * @param figther target figther we will attack.
     */
    @Override
    public boolean attackTwo(Figther figther) {
        figther.setHP(figther.getHP()*65/100);
        return true;

    }
}
