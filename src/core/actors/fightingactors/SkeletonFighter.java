package core.actors.fightingactors;


import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import core.utils.FighterBalanceVariables;

/**
 * EnemyFighter: SkeletonFighter.
 * Used in the fighting screen and represent an enemy. The class has a constructor that initializes the actor
 * with idle, attack and dead animation.
 */
public class SkeletonFighter extends EnemyFighters {



    public SkeletonFighter (Stage s) {

        super(s);
        this.setHP( FighterBalanceVariables.SKELETONHP);
        this.setMaxHP(FighterBalanceVariables.SKELETONMAXHP);
        this.setFighterName("Skeleton");
        attack = AnimationCreator.createAnimation("assets/fightingscreen/Skeleton Attack.png", 0.14f,2,5);
        iddle = AnimationCreator.createAnimation("assets/fightingscreen/Skeleton Idle.png", 0.14f, 2, 5);
        dead = AnimationCreator.createAnimation("assets/fightingscreen/Skeleton Dead.png", 0.14f, 2, 5);

        setAnimation(iddle);




    }
    /**
     * AttackOne from bat deal 8 to 13 damage to another figther.
     * @param fighter target fighter we will attack.
     * @return always return true
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
     * @return always return true
     */
    @Override
    public boolean attackTwo(Fighter fighter) {
        fighter.setHP(fighter.getHP()*65/100);
        return true;

    }

    public void updateManaBar() {}
}
