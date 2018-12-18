package core.actors.fightingactors;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import core.utils.FighterBalanceVariables;
/**
 * SpellCaster: MageTwo.
 * Used in the fighting screen and represent an ally. The class has a constructor that initializes the actor
 * with idle, attack and dead animation.
 */
public class MageTwo extends SpellCaster {

    private String spellOneName = "Mana Zapp";
    private String spellTwoName = "Flame Lance";
    private String spellThreeName = "Fire Ball";
    private String spellOneText = "Zaps the enemy for 2-5 damage and gain "+ FighterBalanceVariables.MAGETWOATTACKONEGAIN+" mana";
    private String spellTwoText = "Burns the enemy for 60 damage for " + FighterBalanceVariables.MAGETWOATTACKTWOCOST+ " mana";
    private String spellThreeText = "Explode all enemies for 30 damage for "+ FighterBalanceVariables.MAGETWOATTACKTHREECOST+" mana";

    public MageTwo (Stage s){
        super(s);
        this.setFighterName("Alenor");
        this.setHP(FighterBalanceVariables.MAGEHP);
        this.setMaxHP(FighterBalanceVariables.MAGEMAXHP);

        setFirstButtonName(spellOneName);
        setSecondButtonName(spellTwoName);
        setThridButtonName(spellThreeName);

        setSpellOneText(spellOneText);
        setSpellTwoText(spellTwoText);
        setSpellThreeText(spellThreeText);

        attack = AnimationCreator.createAnimation("assets/fightingscreen/Heroes/Mage 2 Attack.png", 0.14f,1,5);
        iddle = AnimationCreator.createAnimation("assets/fightingscreen/Heroes/Mage 2 Idle.png", 0.14f, 1, 5);
        dead = AnimationCreator.createAnimation("assets/fightingscreen/Heroes/Mage 2 Dead.png", 0.14f, 1, 5);

        setAnimation(iddle);
    }

    /**
     * Attack for 2-5 damage, and restore 50 mana
     * @param fighter target for the attack
     * @return true if the attack can be done, false if the attack can not be done.
     */

    @Override
    public boolean attackOne (Fighter fighter){
        if (fighter instanceof EnemyFighters){
            fighter.setHP(fighter.getHP()- MathUtils.random(2,5));
            this.gainMana(FighterBalanceVariables.MAGETWOATTACKONEGAIN);
            return true;}
            cantclick.play();
            return false;
        }

    /**
     * Deals attack 60 and decrease mana 75
     * @param fighter target of our attack
     * @return true if the attack can be done, false if the attack can not be done.
     */

    @Override
    public boolean attackTwo (Fighter fighter){
        if (fighter instanceof EnemyFighters){
        if (this.enoughMana(FighterBalanceVariables.MAGETWOATTACKTWOCOST)){
            fighter.setHP(fighter.getHP()-60);
            this.spendMana(FighterBalanceVariables.MAGETWOATTACKTWOCOST);
            return true;
        }
        else {
            return false;
        }}
        cantclick.play();
        return false;

}

    /**
     * Deals 30 damage to all enemies for 40 mana
     * @param fighterOne target of our attack, if null the method wont use the argument.
     * @param fighterTwo target of our attack, if null the method wont use the argument.
     * @param fighterThree target of our attack, if null the method wont use the argument.
     * @return true if the attack can be done, false if it can not be done.
     */


    @Override
    public boolean attackThree (Fighter fighterOne,Fighter fighterTwo, Fighter fighterThree ) {
        if (fighterOne instanceof EnemyFighters || fighterTwo instanceof EnemyFighters ||
                fighterThree instanceof EnemyFighters) {
            if (this.enoughMana(FighterBalanceVariables.MAGETWOATTACKTHREECOST)) {
                fighterOne.setHP(fighterOne.getHP() - 30);
                if (fighterTwo != null)
                    fighterTwo.setHP(fighterTwo.getHP() - 30);
                if (fighterThree != null)
                    fighterThree.setHP(fighterThree.getHP() - 30);
                this.spendMana(FighterBalanceVariables.MAGETWOATTACKTHREECOST);
                return true;
            } else {
                return false;
            }
        }
        cantclick.play();
        return false;
    }
}



