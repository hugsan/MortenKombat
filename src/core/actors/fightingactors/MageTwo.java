package core.actors.fightingactors;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class MageTwo extends SpellCaster {

    private String spellOneName = "Mana Zapp";
    private String spellTwoName = "Flame Lance";
    private String spellThreeName = "Fire Ball";
    private String spellOneText = "Zaps the enemy for 2-5 damage and gain 50 mana";
    private String spellTwoText = "Burns the enemy for 60 damage for 75 mana";
    private String spellThreeText = "Explode all enemies for 30 damage for 40 mana";

    public MageTwo (Stage s){
        super(s);
        this.setFighterName("Chrille");
        this.setHP(150);
        this.setMaxHP(150);

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
     * @return
     */

    @Override
    public boolean attackOne (Fighter fighter){
        if (fighter instanceof EnemyFighters){
            fighter.setHP(fighter.getHP()- MathUtils.random(2,5));
            this.gainMana(50);
            return true;}
            cantclick.play();
            return false;
        }

    /**
     * Deals attack 60 and decrease mana 75
     * @param fighter
     * @return
     */

    @Override
    public boolean attackTwo (Fighter fighter){
        if (fighter instanceof EnemyFighters){
        if (this.enoughMana(75)){
            fighter.setHP(fighter.getHP()-60);
            this.spendMana(75);
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
     * @param fighterOne
     * @param fighterTwo
     * @param fighterThree
     * @return
     */


    @Override
    public boolean attackThree (Fighter fighterOne,Fighter fighterTwo, Fighter fighterThree ) {
        if (fighterOne instanceof EnemyFighters || fighterTwo instanceof EnemyFighters ||
                fighterThree instanceof EnemyFighters) {
            if (this.enoughMana(40)) {
                fighterOne.setHP(fighterOne.getHP() - 30);
                fighterTwo.setHP(fighterTwo.getHP() - 30);
                fighterThree.setHP(fighterThree.getHP() - 30);
                this.spendMana(40);
                return true;
            } else {
                return false;
            }
        }
        cantclick.play();
        return false;
    }
}



