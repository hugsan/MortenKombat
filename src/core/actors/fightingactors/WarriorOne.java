package core.actors.fightingactors;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import core.utils.FighterBalanceVariables;
/**
 * ChampionFighter: WarriorOne.
 * Used in the fighting screen and represent an ally. The class has a constructor that initializes the actor
 * with idle, attack and dead animation.
 */

public class WarriorOne extends Champion {

    static final double PERCENTAGE_GENERATE_QUESTIONS = 0.3; // this is the size in cm

    private String spellOneName = "Light Strike";
    private String spellTwoName = "Equalize";
    private String spellThreeName = "Execute";
    private String spellOneText = "Strikes the enemy for 8-23 damage";
    private String spellTwoText = " Executes the enemy!!! Deals 30% of the target missing life.";
    private String spellThreeText = "Tries to execute the enemy for 3-15 damage, " +
                                    "if it kills the enemy restore 20% health";

    private Label manaBar;

    public WarriorOne(Stage s){
        super(s);
        setFighterName("William");
        setHP( FighterBalanceVariables.WARRIORONEHP);
        setMaxHP(FighterBalanceVariables.WARRIORONEMAXHP);

        setFirstButtonName(spellOneName);
        setSecondButtonName(spellTwoName);
        setThridButtonName(spellThreeName);

        setSpellOneText(spellOneText);
        setSpellTwoText(spellTwoText);
        setSpellThreeText(spellThreeText);

        attack = AnimationCreator.createAnimation("assets/fightingscreen/Heroes/Knight 1 Attack-min.png", 0.14f,1,7);
        iddle = AnimationCreator.createAnimation("assets/fightingscreen/Heroes/Knight 1 Iddle-min.png", 0.14f, 1, 7);
        dead = AnimationCreator.createAnimation("assets/fightingscreen/Heroes/Knight 1 Dead-min.png", 0.14f, 1, 7);

        setAnimation(iddle);
    }


    /**
     * does 8-23 damage to enemy
     * @param fighter enemy target
     * @return True if the attack can be done, false if the attack can not be done.
     */
    @Override
    public boolean attackOne(Fighter fighter){
        if (fighter instanceof EnemyFighters){
        fighter.setHP(fighter.getHP()-MathUtils.random(8,23));
        return true;}
        cantclick.play();
        return false;
    }

    /**
     * Makes 30% of target missing life.
     * @param fighter enemy target
     * @return True if the attack can be done, false if the attack can not be done.
     */
    @Override
    public boolean attackTwo (Fighter fighter){
        if (fighter instanceof EnemyFighters){
            int damage = (int)(( fighter.getMaxHP() - fighter.getHP() ) * 0.3);
            fighter.setHP(fighter.getHP()- damage);
            return true;}
        cantclick.play();
        return false;
    }

    /**
     * Deals 3-15 Damage. If the champion kills the target, he heals for 20% of his life.
     * @param fighterOne target if our attack.
     * @param fighterTwo fighter object can be null, wont do anything to this argument.
     * @param fighterThree  fighter object can be null, wont do anything to this artument.
     * @return True if the attack can be done, false if the attack can not be done.
     */
    @Override
    public boolean attackThree (Fighter fighterOne, Fighter fighterTwo, Fighter fighterThree){
        if (fighterOne instanceof EnemyFighters){
            fighterOne.setHP(fighterOne.getHP()-MathUtils.random(3,15));
            if (fighterOne.getHP()<= 0 ){
                this.setHP((int)(this.getHP()+ 0.20*this.getMaxHP()));
                if (this.getHP() >= this.getMaxHP())
                    this.setHP(this.getMaxHP());
            }
            return true;
        }
        cantclick.play();
        return false;
    }
    public void updateManaBar() { }

    public Label getManaBar() { return manaBar; }

}
