package core.actors.fightingactors;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class SkeletonFighter extends EnemyFighters {



    public SkeletonFighter (Stage s) {

        super(s);
        this.setHP(1);
        this.setMaxHP(100);
        this.setFighterName("Skeleton");
        attack = AnimationCreator.createAnimation("assets/fightscreen/Troll Attack-min.png", 0.14f,1,7);
        iddle = AnimationCreator.createAnimation("assets/fightscreen/Troll Iddle-min.png", 0.14f, 1, 7);
        dead = AnimationCreator.createAnimation("assets/fightscreen/Troll Dead-min.png", 0.14f, 1, 7);

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
