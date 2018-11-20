package core.actors.fightingactors;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class MageOne extends SpellCaster {

    public MageOne(Stage s){
        super(s);
        this.setFighterName("Dimitrova");
        this.setHP(150);
        this.setMaxHP(150);

        attack = AnimationCreator.createAnimation("assets/fightscreen/Mage 1 Attack.png", 0.14f,1,5);
        iddle = AnimationCreator.createAnimation("assets/fightscreen/Mage 1 Iddle.png", 0.14f, 1, 5);
        dead = AnimationCreator.createAnimation("assets/fightscreen/Mage 1 Dead.png", 0.14f, 1, 5);

        setAnimation(iddle);

    }

    /**
     * Attack for 3-8 damage, and restore 15 mana
     * @param fighter
     */
    @Override
    public boolean attackOne(Fighter fighter){
        if (fighter instanceof EnemyFighters){
        fighter.setHP(fighter.getHP()- MathUtils.random(3,8));
        this.gainMana(15);
        return true;}
        cantclick.play();
        return false;

    }

    /**
     * Attack 50 and decrease mana 55
     * @param fighter
     */
    @Override
    public boolean attackTwo(Fighter fighter){
        if (fighter instanceof EnemyFighters){
        if (this.enoughMana(55)){
            fighter.setHP(fighter.getHP()-50);
            this.spendMana(55);
            return true;
        }
        else{
            return false;
        }}
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
    public boolean attackThree(Fighter fighterOne,Fighter fighterTwo, Fighter fighterThree ){
        if (fighterOne instanceof EnemyFighters || fighterTwo instanceof EnemyFighters ||
                fighterThree instanceof EnemyFighters){
            if (this.enoughMana(35)){
                fighterOne.setHP(fighterOne.getHP()-20);
                fighterTwo.setHP(fighterTwo.getHP()-20);
                fighterThree.setHP(fighterThree.getHP()-20);
                this.spendMana(35);
                return true;
            }else{
                return false;
        }
        }
        cantclick.play();
        return false;
    }





}
