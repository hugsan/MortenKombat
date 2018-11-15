package core.actors.fightingactors;

import com.badlogic.gdx.math.MathUtils;

public class BatFighter extends Fighter {

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
    public boolean attackOne(Fighter fighter){
        int damage = MathUtils.random(8,13);
        fighter.setHP(fighter.getHP()-damage);
        return true;
    }

    /**
     * Attack two makes 35% damage of target current HP
     * @param fighter target fighter we will attack.
     */
    @Override
    public boolean attackTwo(Fighter fighter){
        fighter.setHP(fighter.getHP()*(65/100));
        return true;
    }
}
