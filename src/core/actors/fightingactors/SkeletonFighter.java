package core.actors.fightingactors;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 *
 */
public class SkeletonFighter extends EnemyFighters {



    public SkeletonFighter (Stage s) {

        super(s);
        this.setHP(100);
        this.setMaxHP(100);
        this.setFighterName("Skeleton");
        attack = AnimationCreator.createAnimation("assets/fightingscreen/Skeleton Attack.png", 0.14f,2,5);
        iddle = AnimationCreator.createAnimation("assets/fightingscreen/Skeleton Idle.png", 0.14f, 2, 5);
        dead = AnimationCreator.createAnimation("assets/fightingscreen/Skeleton Dead.png", 0.14f, 2, 5);

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

    /**
     *
     */
    public void updateManaBar() {}
}
