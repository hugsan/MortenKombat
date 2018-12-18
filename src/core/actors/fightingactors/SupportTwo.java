package core.actors.fightingactors;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import core.utils.FighterBalanceVariables;
/**
 * SpellCaster: SupportTwo.
 * Used in the fighting screen and represent an allie. The class has a constructor that initializes the actor
 * with idle, attack and dead animation.
 */
public class SupportTwo extends SpellCaster {

    private String spellOneName = "Swing";
    private String spellTwoName = "Heal";
    private String spellThreeName = "Radiant Heal";
    private String spellOneText = "Slashes the enemy for 6-22 damage and restore "+ FighterBalanceVariables.SUPPORTTWOATTACKONEGAIN+" mana";
    private String spellTwoText = "Heals a hero for 30 health, cost "+FighterBalanceVariables.SUPPORTTWOATTACKTWOCOST+" mana";
    private String spellThreeText = "Heals all heroes for 40 health, cost "+FighterBalanceVariables.SUPPORTTWOATTACKTHREECOST+" mana";

    public SupportTwo(Stage s) {
        super(s);
        this.setFighterName("Koryssh");
        this.setHP(FighterBalanceVariables.SUPPPORTHP);
        this.setMaxHP(FighterBalanceVariables.SUPPORTMAXHP);

        setFirstButtonName(spellOneName);
        setSecondButtonName(spellTwoName);
        setThridButtonName(spellThreeName);

        setSpellOneText(spellOneText);
        setSpellTwoText(spellTwoText);
        setSpellThreeText(spellThreeText);

        attack = AnimationCreator.createAnimation("assets/fightingscreen/Heroes/Knight Sup Attack-min.png", 0.14f,1,10);
        iddle = AnimationCreator.createAnimation("assets/fightingscreen/Heroes/Knight Sup Iddle-min.png", 0.14f, 1, 10);
        dead = AnimationCreator.createAnimation("assets/fightingscreen/Heroes/Knight Sup Dead-min.png", 0.14f, 1, 10);

        setAnimation(iddle);
    }

    /**
     * 6-22 damage and restore 10 mana
     *
     * @param fighter target for the attack
     * @return true if the attack can be done, false if the attack can not be done
     */

    @Override
    public boolean attackOne(Fighter fighter) {
        if (fighter instanceof EnemyFighters) {
            fighter.setHP(fighter.getHP() - MathUtils.random(6, 22));
            this.gainMana(FighterBalanceVariables.SUPPORTTWOATTACKONEGAIN);
            return true;
        }
        cantclick.play();
        return false;

    }

    /**
     * Heals a fighter for 30, spending 15 mana. It checks if the fighter is a champion class. if not it return false
     *
     * @param fighter target of our attack
     * @return true if the fighter is instance of champion and there is enough mana for the act
     */
    @Override
    public boolean attackTwo(Fighter fighter) {
        if (this.enoughMana(FighterBalanceVariables.SUPPORTTWOATTACKTWOCOST) && fighter instanceof Champion) {
            fighter.setHP(fighter.getHP() + 30);
            spendMana(FighterBalanceVariables.SUPPORTTWOATTACKTWOCOST);
            if (fighter.getHP() >= fighter.getMaxHP())
                fighter.setHP(fighter.getMaxHP());
            return true;
        }

        cantclick.play();
        return false;
    }


    /**
     * Heals all our champions for 40 at for exchange of 60 mana.
     * @param fighterOne Target for the heal, it parameter null, does nothing
     * @param fighterTwo Target for the heal, it parameter null, does nothing
     * @param fighterThree Target for the heal, it parameter null, does nothing
     * @return true if at least one fighter is instance of champion and false if not
     */

    @Override
    public boolean attackThree(Fighter fighterOne, Fighter fighterTwo, Fighter fighterThree) {
        if (fighterOne instanceof Champion || fighterTwo instanceof Champion || fighterThree instanceof Champion) {
            //checking if the fighters are alive before healing them.
            if (enoughMana(FighterBalanceVariables.SUPPORTTWOATTACKTHREECOST)) {
                if (fighterOne.getHP() != 0)
                    fighterOne.setHP(Math.min((fighterOne.getHP() + 40), fighterOne.getMaxHP()));
                if (fighterTwo.getHP() != 0)
                    fighterTwo.setHP(Math.min((fighterTwo.getHP() + 40), fighterTwo.getMaxHP()));
                if (fighterThree.getHP() != 0)
                    fighterThree.setHP(Math.min((fighterThree.getHP() + 40), fighterThree.getMaxHP()));
                this.spendMana(FighterBalanceVariables.SUPPORTTWOATTACKTHREECOST);
                return true;
            } else{
                missingMana.play ();
                return true;}

        }
        cantclick.play();
        return false;
    }
}
