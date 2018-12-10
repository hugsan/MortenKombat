package core.actors.fightingactors;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import core.utils.FighterBalanceVariables;

public class MageOne extends SpellCaster {

    private String spellOneName = "Mana Torrent";
    private String spellTwoName = "Death Lance";
    private String spellThreeName = "Flame Wave";
    private String spellOneText = "Hits for 10-18 damage and restore "+ FighterBalanceVariables.MAGEONEATTACKONEGAIN + " mana";
    private String spellTwoText = "Large single attack for 50 damage, cost "+FighterBalanceVariables.MAGEONEATTACKTWOCOST+" mana";
    private String spellThreeText = "Attack all 3 enemies for 20 damage, cost "+ FighterBalanceVariables.MAGEONEATTACKTHREECOST +" mana";


    public MageOne(Stage s){
        super(s);
        this.setFighterName("Drafaris");
        this.setHP(FighterBalanceVariables.MAGEHP);
        this.setMaxHP(FighterBalanceVariables.MAGEMAXHP);

        setFirstButtonName(spellOneName);
        setSecondButtonName(spellTwoName);
        setThridButtonName(spellThreeName);

        setSpellOneText(spellOneText);
        setSpellTwoText(spellTwoText);
        setSpellThreeText(spellThreeText);

        attack = AnimationCreator.createAnimation("assets/fightingscreen/Heroes/Mage 1 Attack.png", 0.14f,1,5);
        iddle = AnimationCreator.createAnimation("assets/fightingscreen/Heroes/Mage 1 Idle.png", 0.14f, 1, 5);
        dead = AnimationCreator.createAnimation("assets/fightingscreen/Heroes/Mage 1 Dead.png", 0.14f, 1, 5);

        setAnimation(iddle);

    }

    /**
     * Attack for 10-18 damage, and restore 15 mana
     * @param fighter
     */
    @Override
    public boolean attackOne(Fighter fighter)
    {
        if (fighter instanceof EnemyFighters){
            fighter.setHP(fighter.getHP()- MathUtils.random(10,18));
            this.gainMana(FighterBalanceVariables.MAGEONEATTACKONEGAIN);
            return true;}
        cantclick.play();
        return false;

    }

    /**
     * Attack 50 and decrease mana 55
     * @param fighter
     */
    @Override
    public boolean attackTwo(Fighter fighter)
    {
        if (fighter instanceof EnemyFighters){
            if (this.enoughMana(FighterBalanceVariables.MAGEONEATTACKTWOCOST)){
                fighter.setHP(fighter.getHP()-50);
                this.spendMana(FighterBalanceVariables.MAGEONEATTACKTWOCOST);
                return true;
            }
            else{
                return false;
            }
        }
        cantclick.play();
        return false;
    }

    /**
     * Deas 20 damage to all enemies for 35 mana
     * @param fighterOne deals damage to fighterOne
     * @param fighterTwo deals damage to fighterTwo
     * @param fighterThree deals damage to fighterThree
     * @return true if ability can be used, false if can not.
     */
    @Override
    public boolean attackThree(Fighter fighterOne,Fighter fighterTwo, Fighter fighterThree )
    {
        if (fighterOne instanceof EnemyFighters || fighterTwo instanceof EnemyFighters ||
                fighterThree instanceof EnemyFighters){
            if (this.enoughMana(FighterBalanceVariables.MAGEONEATTACKTHREECOST)){
                fighterOne.setHP(fighterOne.getHP()-20);
                if (fighterTwo != null)
                    fighterTwo.setHP(fighterTwo.getHP()-20);
                if (fighterThree != null)
                    fighterThree.setHP(fighterThree.getHP()-20);
                this.spendMana(FighterBalanceVariables.MAGEONEATTACKTHREECOST);
                return true;
            }else{
                return false;
            }
        }
        cantclick.play();
        return false;
    }





}
