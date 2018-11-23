package core.actors.fightingactors;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class WarriorTwo extends Champion{

    private Label manaBar;

    public WarriorTwo (Stage s){
        super(s);
        setFighterName ( "Jens" );
        setHP(250);
        setMaxHP(250);


        attack = AnimationCreator.createAnimation("assets/fightingscreen/Heroes/Knight 2 Attack.png", 0.14f,1,8);
        iddle = AnimationCreator.createAnimation("assets/fightingscreen/Heroes/Knight 2 Idle.png", 0.14f, 1, 7);
        dead = AnimationCreator.createAnimation("assets/fightingscreen/Heroes/Knight 2 Dead.png", 0.14f, 1, 7);

        setAnimation(iddle);
    }


    /**
     * Deals 11 to 20 damage
     * @param fighter target for the attack
     * @return
     */
    @Override
    public boolean attackOne(Fighter fighter) {
        if (fighter instanceof EnemyFighters){
            fighter.setHP(fighter.getHP()- MathUtils.random(11,20));
        return true;}
        cantclick.play();
        return false;

    }

    /**
     * If Warrior HP is lower than 100 double damage of attackOne
     * @param fighter
     * @return
     */

    @Override
    public boolean attackTwo(Fighter fighter) {
        if (fighter instanceof EnemyFighters){
            if (this.getHP() <= 100){
            }
        fighter.setHP(fighter.getHP() - MathUtils.random(22,40));
        return true;}
        cantclick.play();
        return false;
    }

    /**
     * Warrior takes 10% of maxHP in self damage but attacks with 20% of his own currentHP
     * @return
     */
    @Override
    public boolean attackThree(Fighter fighterOne, Fighter fighterTwo, Fighter fighterThree) {
        if (fighterOne instanceof EnemyFighters){

            fighterOne.setHP(fighterOne.getHP() - (int)(this.getHP()*(0.20f)));

            this.setHP(this.getHP() - (int)(this.getMaxHP()*(0.10f)) );
        return true;}
        cantclick.play();
        return false;
    }

    public Label getManaBar() { return manaBar;}
    public void updateManaBar() {

    }
}
