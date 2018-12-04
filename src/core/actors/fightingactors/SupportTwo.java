package core.actors.fightingactors;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class SupportTwo extends SpellCaster {

    public String spellOneName = "Swing";
    public String spellTwoName = "Heal";
    public String spellThreeName = "Radiant Heal";

    public SupportTwo(Stage s) {
        super(s);
        this.setFighterName("Onagi");
        this.setHP(125);
        this.setMaxHP(125);

        setFirstButtonName(spellOneName);
        setSecondButtonName(spellTwoName);
        setThridButtonName(spellThreeName);

        attack = AnimationCreator.createAnimation("assets/fightingscreen/Heroes/Knight Sup Attack-min.png", 0.14f,1,10);
        iddle = AnimationCreator.createAnimation("assets/fightingscreen/Heroes/Knight Sup Iddle-min.png", 0.14f, 1, 10);
        dead = AnimationCreator.createAnimation("assets/fightingscreen/Heroes/Knight Sup Dead-min.png", 0.14f, 1, 10);

        setAnimation(iddle);
    }

    /**
     * 6-22 damage and restore 10 mana
     *
     * @param fighter target for the attack
     * @return
     */

    @Override
    public boolean attackOne(Fighter fighter) {
        if (fighter instanceof EnemyFighters) {
            fighter.setHP(fighter.getHP() - MathUtils.random(6, 22));
            this.gainMana(10);
            return true;
        }
        cantclick.play();
        return false;

    }

    /**
     * Heals a fighter for 30, spending 15 mana. It checks if the fighter is a champion class. if not it return false
     *
     * @param fighter
     * @return true if the fighter is instance of champion and there is enough mana for the act
     */
    @Override
    public boolean attackTwo(Fighter fighter) {
        if (this.enoughMana(15) && fighter instanceof Champion) {
            fighter.setHP(fighter.getHP() + 30);
            spendMana(15);
            if (fighter.getHP() >= fighter.getMaxHP())
                fighter.setHP(fighter.getMaxHP());
            return true;
        }

        cantclick.play();
        return false;
    }


    /**
     * Heals all our champions for 40 at for exchange of 60 mana.
     * @param fighterOne
     * @param fighterTwo
     * @param fighterThree
     * @return true if at least one fighter is instance of champion and false if not
     */

    @Override
    public boolean attackThree(Fighter fighterOne, Fighter fighterTwo, Fighter fighterThree) {
        if (fighterOne instanceof Champion || fighterTwo instanceof Champion || fighterThree instanceof Champion) {
            //checking if the fighters are alive before healing them.
            if (enoughMana(60)) {
                if (fighterOne.getHP() != 0)
                    fighterOne.setHP(Math.max((fighterOne.getHP() + 40), fighterOne.getMaxHP()));
                if (fighterTwo.getHP() != 0)
                    fighterTwo.setHP(Math.max((fighterTwo.getHP() + 40), fighterTwo.getMaxHP()));
                if (fighterThree.getHP() != 0)
                    fighterThree.setHP(Math.max((fighterThree.getHP() + 40), fighterThree.getMaxHP()));
                this.spendMana(60);
                return true;
            } else{
                missingMana.play ();
                return true;}

        }
        cantclick.play();
        return false;
    }
}
