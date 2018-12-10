package core.actors.fightingactors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import core.utils.FighterBalanceVariables;

public class JohanFighter extends EnemyFighters {
    //initialize variables for HP and MaxHP
    //variables used to code the abilities of the fighter
    int plusDamage;

    public JohanFighter(Stage s){
        super(s);
        this.setHP( FighterBalanceVariables.JOHANHP);
        this.setMaxHP(FighterBalanceVariables.JOHANMAXHP);
        this.setFighterName(FighterBalanceVariables.JOHANNAME);

        attack = AnimationCreator.createAnimation("assets/Fightingscreen/Boss/Johan-Attack.png", 0.14f,1,6);
        iddle = AnimationCreator.createAnimation("assets/Fightingscreen/Boss/Johan-Idle.png", 0.14f, 1, 8);
        dead = AnimationCreator.createAnimation("assets/Fightingscreen/Boss/Johan-Dead.png", 0.14f, 1, 9);

        setAnimation(iddle);
        plusDamage = 10;

    }
    /**
     * Attack one makes 30 damage, and increased the next attack one by 10.
     * @param fighter target fighter we will attack.
     */
    @Override
    public boolean attackOne(Fighter fighter){
        int damage = 30 +  plusDamage ;
        fighter.setHP(fighter.getHP()-damage);
        plusDamage += 10;
        return true;
    }

    /**
     * heals himself for 40 HP.
     * @param fighter target fighter we will attack. (not been used)
     */
    @Override
    public boolean attackTwo(Fighter fighter){
        this.setHP( Math.min(this.getHP()+40,this.getMaxHP()));
        return true;
    }
    public void updateManaBar() { }

}
