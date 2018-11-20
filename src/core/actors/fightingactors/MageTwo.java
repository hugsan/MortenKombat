package core.actors.fightingactors;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class MageTwo extends SpellCaster {

    public MageTwo (Stage s){
        super(s);
        this.setFighterName("Chrille");
        this.setHP(150);
        this.setMaxHP(150);

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
        if (this.enoughMana(80)){
            fighter.setHP(fighter.getHP()-70);
            this.spendMana(80);
            return true;
        }
        else {
            return false;
        }}
        cantclick.play();
        return false;

}

    @Override
    public boolean attackThree (Fighter fighterOne,Fighter fighterTwo, Fighter fighterThree ) {
        if (fighterOne instanceof EnemyFighters || fighterTwo instanceof EnemyFighters ||
                fighterThree instanceof EnemyFighters) {
            if (this.enoughMana(30)) {
                fighterOne.setHP(fighterOne.getHP() - 20);
                fighterTwo.setHP(fighterTwo.getHP() - 20);
                fighterThree.setHP(fighterThree.getHP() - 20);
                this.spendMana(30);
                return true;
            } else {
                return false;
            }
        }
        cantclick.play();
        return false;
    }
}



