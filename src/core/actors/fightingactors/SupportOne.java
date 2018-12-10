package core.actors.fightingactors;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import core.utils.FighterBalanceVariables;

public class SupportOne extends SpellCaster{

    private String spellOneName = "Light Hit";
    private String spellTwoName = "Lights Grace";
    private String spellThreeName = "Holy Light";
    private String spellOneText = "Hits the enemy for 3-11 damage and restore "+ FighterBalanceVariables.SUPPORTONEATTACKONEGAIN+" mana";
    private String spellTwoText = "Heals a hero for 50 health, cost "+FighterBalanceVariables.SUPPORTONEATTACKTWOCOST+" mana";
    private String spellThreeText = "Heals all heroes for 30 health, cost "+FighterBalanceVariables.SUPPORTONEATTACKTHREECOST+" mana";

    public SupportOne(Stage s){
        super(s);
        this.setFighterName("Valyn");
        this.setHP(FighterBalanceVariables.SUPPPORTHP);
        this.setMaxHP(FighterBalanceVariables.SUPPORTMAXHP);

        setFirstButtonName(spellOneName);
        setSecondButtonName(spellTwoName);
        setThridButtonName(spellThreeName);

        setSpellOneText(spellOneText);
        setSpellTwoText(spellTwoText);
        setSpellThreeText(spellThreeText);

        attack = AnimationCreator.createAnimation("assets/fightingscreen/Heroes/Robot Attack-min.png", 0.14f,1,8);
        iddle = AnimationCreator.createAnimation("assets/fightingscreen/Heroes/Robot Iddle-min.png", 0.14f, 1, 10);
        dead = AnimationCreator.createAnimation("assets/fightingscreen/Heroes/Robot Dead-min.png", 0.14f, 1, 10);

        setAnimation(iddle);

    }

    /**
     * 3-11 damage and restore 20 mana
     * @param fighter
     * @return
     */
    @Override
    public boolean attackOne(Fighter fighter) {
        if(fighter instanceof EnemyFighters){
        fighter.setHP(fighter.getHP() - MathUtils.random(3,11));
        this.gainMana(FighterBalanceVariables.SUPPORTONEATTACKONEGAIN);
        return true;}
        cantclick.play();
        return false;
    }

    /**
     * Heals a fighter for 50, spending 30 mana. It checks if the fighter is a champion class. if not it return false
     * @param fighter target that we want to heal
     * @return 1 if you can cast the spell; -1 if you cannot cast the spell
     */
    @Override
    public boolean attackTwo(Fighter fighter) {
        if (this.enoughMana(FighterBalanceVariables.SUPPORTONEATTACKTWOCOST) && fighter instanceof Champion){
            fighter.setHP(Math.min ( (fighter.getHP()+50), fighter.getMaxHP ()));
            spendMana(FighterBalanceVariables.SUPPORTONEATTACKTWOCOST);

            return true;
        }
        cantclick.play();
        return false;
    }

    /**
     *Heals all our champions for 30 at for exchange of 50 mana.
     * @param fighterOne
     * @param fighterTwo
     * @param fighterThree
     * @return
     */
    @Override
    public boolean attackThree(Fighter fighterOne, Fighter fighterTwo, Fighter fighterThree) {
        if (fighterOne instanceof Champion || fighterTwo instanceof Champion || fighterThree instanceof Champion)
        {
            //checking if the fighters are alive before fighting them.
            if (enoughMana(FighterBalanceVariables.SUPPORTONEATTACKTHREECOST)){
                if (fighterOne.getHP() != 0)
                    fighterOne.setHP( Math.min( (fighterOne.getHP()+30),fighterOne.getMaxHP() ) );
                if (fighterTwo.getHP() != 0)
                    fighterTwo.setHP( Math.min ( (fighterTwo.getHP()+30),fighterTwo.getMaxHP() ) );
                if (fighterThree.getHP() != 0)
                    fighterThree.setHP( Math.min ( (fighterThree.getHP()+30),fighterThree.getMaxHP() ) );
                this.spendMana(FighterBalanceVariables.SUPPORTONEATTACKTHREECOST);
                return  true;
            }else {
               // missingMana.play ();
                return false;}
        }
        cantclick.play();
        return false;
    }


}
