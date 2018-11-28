package core.actors.fightingactors;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class BatFighter extends EnemyFighters {

    public BatFighter(Stage s){
        super(s);
        this.setHP(60);
        this.setMaxHP(60);
        this.setFighterName("Patrick");

        attack = AnimationCreator.createAnimation("assets/fightingscreen/Troll Attack.png", 0.14f,1,7);
        iddle = AnimationCreator.createAnimation("assets/fightingscreen/Troll Idle.png", 0.14f, 1, 7);
        dead = AnimationCreator.createAnimation("assets/fightingscreen/Troll Dead.png", 0.14f, 1, 7);

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
        fighter.setHP((fighter.getHP()*(65)/100));
        return true;
    }
    public void updateManaBar() { }
}
