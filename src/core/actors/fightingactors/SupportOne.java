package core.actors.fightingactors;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class SupportOne extends SpellCaster{
    public SupportOne(Stage s){
        super(s);
        this.setFighterName("Nami");
        this.setHP(125);
        this.setMaxHP(125);

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
        this.gainMana(20);
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
        if (this.enoughMana(30) && fighter instanceof Champion){
            fighter.setHP(fighter.getHP()+20);
            spendMana(30);
            if (fighter.getHP()>= fighter.getMaxHP())
                fighter.setHP(fighter.getMaxHP());
            return true;
        }
        cantclick.play();
        return false;
    }

    /**
     * Heals all our champions for 30 at for exchange of 50 mana.
     * @param fighterOne
     * @param fighterTwo
     * @param fighterThree
     * @return
     */

    @Override
    public boolean attackThree(Fighter fighterOne, Fighter fighterTwo, Fighter fighterThree) {
        if (fighterOne instanceof Champion || fighterTwo instanceof Champion || fighterThree instanceof Champion)
        {
            if (enoughMana(50)){
                fighterOne.setHP( Math.max( (fighterOne.getHP()+30),fighterOne.getMaxHP() ) );
                fighterTwo.setHP( Math.max( (fighterTwo.getHP()+30),fighterTwo.getMaxHP() ) );
                fighterThree.setHP( Math.max( (fighterThree.getHP()+30),fighterThree.getMaxHP() ) );
                this.spendMana(35);
            }else
                return false;
        }
        cantclick.play();
        return false;
    }


}
