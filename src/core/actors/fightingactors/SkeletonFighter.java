package core.actors.fightingactors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

public class SkeletonFighter extends EnemyFighters {


    public SkeletonFighter (Stage s) {

        super(s);
        this.setHP(100);
        this.setMaxHP(100);
        this.setFighterName("Skeleton");
        attack = AnimationCreator.createAnimation("assets/fightscreen/Skeleton Attack.png", 0.14f,1,1);
        iddle = AnimationCreator.createAnimation("assets/fightscreen/Skeleton Idle.png", 0.14f, 1, 11);
        dead = AnimationCreator.createAnimation("assets/fightscreen/Skeleton Dead.png", 0.14f, 1, 15);

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
