package core.actors.fightingactors;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class ZombieFighter extends EnemyFighters {

    public ZombieFighter (Stage s) {

        super(s);
        this.setHP(120);
        this.setMaxHP(120);
        this.setFighterName("Zombie");

        attack = AnimationCreator.createAnimation("assets/fightingscreen/Zombie Attack.png", 0.14f,1,8);
        iddle = AnimationCreator.createAnimation("assets/fightingscreen/Zombie Idle.png", 0.14f, 1, 15);
        dead = AnimationCreator.createAnimation("assets/fightingscreen/Zombie Dead.png", 0.14f, 1, 12);

        setAnimation(iddle);
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

