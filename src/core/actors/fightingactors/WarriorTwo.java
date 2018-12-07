package core.actors.fightingactors;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class WarriorTwo extends Champion{

    private String spellOneName = "Heavy Hit";
    private String spellTwoName = "Bloodied Strike";
    private String spellThreeName = "Double Edge";
    private String spellOneText = "Hits the enemy for 11 to 20 damage";
    private String spellTwoText = "If the warrior is below 100 health, deals double damage of the first attack";
    private String spellThreeText = "Strikes the enemy for 20% of warriors current health, cost 10% of current health";


    private Label manaBar;

    public WarriorTwo (Stage s){
        super(s);
        setFighterName ( "Arthur" );
        setHP(250);
        setMaxHP(250);

        setFirstButtonName(spellOneName);
        setSecondButtonName(spellTwoName);
        setThridButtonName(spellThreeName);

        setSpellOneText(spellOneText);
        setSpellTwoText(spellTwoText);
        setSpellThreeText(spellThreeText);

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
            int damage = MathUtils.random(11,20);
            if (this.getHP() <= 100){
                fighter.setHP(fighter.getHP() - damage);
            }
        fighter.setHP(fighter.getHP() - damage);
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
            return true;
        }else{
            cantclick.play();
            return false;
        }
    }

    public Label getManaBar() { return manaBar;}
    public void updateManaBar() {

    }
}
