package core.actors.fightingactors;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class SokolFighter extends EnemyFighters {
    public SokolFighter(Stage s){
        super(s);
        this.setHP(140);
        this.setMaxHP(140);
        this.setFighterName("Sokol");

        attack = AnimationCreator.createAnimation("assets/fightingscreen/Boss/Sokol-Attack.png", 0.14f,1,6);
        iddle = AnimationCreator.createAnimation("assets/fightingscreen/Boss/Sokol-Idle.png", 0.14f, 1, 8);
        dead = AnimationCreator.createAnimation("assets/fightingscreen/Boss/Sokol-Dead.png", 0.14f, 1, 5);

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
