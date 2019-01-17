package core.actors.fightingactors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import core.utils.FighterBalanceVariables;
/**
 * EnemyFighter: SokolFighter.
 * Used in the fighting screen and represent an enemy. The class has a constructor that initializes the actor
 * with idle, attack and dead animation.
 */
public class SokolFighter extends EnemyFighters {
    public SokolFighter(Stage s){
        super(s);
        this.setHP( FighterBalanceVariables.SOKOLHP);
        this.setMaxHP(FighterBalanceVariables.SOKOLMAXHP);
        this.setFighterName(FighterBalanceVariables.SOKOLNAME);

        attack = AnimationCreator.createAnimation("assets/fightingscreen/Boss/Sokol-Attack.png", 0.14f,1,6);
        iddle = AnimationCreator.createAnimation("assets/fightingscreen/Boss/Sokol-Idle.png", 0.14f, 1, 8);
        dead = AnimationCreator.createAnimation("assets/fightingscreen/Boss/Sokol-Dead.png", 0.14f, 1, 5);

        setAnimation(iddle);

    }
    /**
     * AttackOne from Sokol deal 50 to another figther.
     * @param fighter target fighter we will attack.
     * @return always return true
     */
    @Override
    public boolean attackOne(Fighter fighter){

        fighter.setHP(fighter.getHP()- FighterBalanceVariables.SOKOLFIRSTATTACKDAMAGE);
        return true;
    }

    /**
     * Attack two makes 55% damage of target current HP + stunning the target for a turn
     * @param fighter target fighter we will attack.
     * @return always return true.
     */
    @Override
    public boolean attackTwo(Fighter fighter){
        fighter.setHP((fighter.getHP()*(45)/100));
        if(fighter instanceof Champion){
            ((Champion) fighter).stun = true;
            fighter.setAnimationPaused ( true );
        }
        return true;
    }
    public void updateManaBar() { }

}
