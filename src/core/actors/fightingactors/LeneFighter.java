package core.actors.fightingactors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import core.utils.FighterBalanceVariables;
/**
 * EnemyFighter: LeneFighter.
 * Used in the fighting screen and represent an enemy. The class has a constructor that initializes the actor
 * with idle, attack and dead animation.
 */
public class LeneFighter extends EnemyFighters {
    public LeneFighter(Stage s){
        super(s);
        this.setHP( FighterBalanceVariables.LENEHP);
        this.setMaxHP(FighterBalanceVariables.LENEMAXHP);
        this.setFighterName(FighterBalanceVariables.LENENAME);

        attack = AnimationCreator.createAnimation("assets/Fightingscreen/Boss/Lene-Attack.png", 0.14f,1,30);
        iddle = AnimationCreator.createAnimation("assets/Fightingscreen/Boss/Lene-Idle.png", 0.14f, 1, 16);
        dead = AnimationCreator.createAnimation("assets/Fightingscreen/Boss/Lene-Dead.png", 0.14f, 1, 30);

        setAnimation(iddle);

    }
    /**
     * AttackOne from Johan deal 50 to another figther.
     * @param fighter target fighter we will attack.
     * @return always return true
     */
    @Override
    public boolean attackOne(Fighter fighter){
        int damage = 50;
        fighter.setHP(fighter.getHP()-damage);
        return true;
    }

    /**
     * Attack two makes 55% damage of target current HP
     * @param fighter target fighter we will attack.
     * @return always return true
     */
    @Override
    public boolean attackTwo(Fighter fighter){
        fighter.setHP((fighter.getHP()*(45)/100));
        return true;
    }
    public void updateManaBar() { }
}
