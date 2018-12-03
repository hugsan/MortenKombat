package core.actors.fightingactors;

import com.badlogic.gdx.scenes.scene2d.Stage;

public class MortenFighter extends EnemyFighters {
    public MortenFighter(Stage s){
        super(s);
        this.setHP(160);
        this.setMaxHP(160);
        this.setFighterName("Morten");

        attack = AnimationCreator.createAnimation("assets/Fightingscreen/Boss/spritesheets/mage-1-85x94.png", 0.14f,2,4);
        iddle = AnimationCreator.createAnimation("assets/Fightingscreen/Boss/spritesheets/mage-1-85x94.png", 0.14f, 2, 4);
        dead = AnimationCreator.createAnimation("assets/Fightingscreen/Boss/spritesheets/mage-1-85x94.png", 0.14f, 2, 4);

        setAnimation(iddle);

    }
    /**
     * AttackOne from Morten deal 50 to another figther.
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
