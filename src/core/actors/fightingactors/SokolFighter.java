package core.actors.fightingactors;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class SokolFighter extends EnemyFighters {
    public SokolFighter(Stage s){
        super(s);
        this.setHP(160);
        this.setMaxHP(160);
        this.setFighterName("Sokol");

        attack = AnimationCreator.createAnimation("assets/Fightingscreen/Boss/Sokol-Attack.png", 0.14f,1,8);
        iddle = AnimationCreator.createAnimation("assets/Fightingscreen/Boss/Sokol-Idle.png", 0.14f, 1, 6);
        dead = AnimationCreator.createAnimation("assets/Fightingscreen/Boss/Sokol-Dead.png", 0.14f, 1, 9);

        setAnimation(iddle);

    }
    /**
     * AttackOne from Sokol deal 50 to another figther.
     * @param fighter target fighter we will attack.
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
     */
    @Override
    public boolean attackTwo(Fighter fighter){
        fighter.setHP((fighter.getHP()*(45)/100));
        return true;
    }
    public void updateManaBar() { }

}
