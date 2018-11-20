package core.actors.fightingactors;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class BatFighter extends EnemyFighters {

    public BatFighter(Stage s){
        super(s);
        this.setHP(60);
        this.setMaxHP(60);
        this.setFighterName("Bat");

        attack = AnimationCreator.createAnimation("assets/fightscreen/Bat_Sprite_Sheet1.png", 0.14f,1,5);
        iddle = AnimationCreator.createAnimation("assets/fightscreen/Bat_Sprite_Sheet2.png", 0.14f, 1, 5);
        dead = AnimationCreator.createAnimation("assets/fightscreen/Bat_Sprite_Sheet3.png", 0.14f, 1, 5);

        setAnimation(iddle);


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
