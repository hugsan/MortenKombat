package core.actors.fightingactors;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class WarriorOne extends Champion {

    private Label manaBar;

    public WarriorOne(Stage s){
        super(s);
        setFighterName("Dominiqo Vespucci             ");
        setHP(200);
        setMaxHP(200);

        attack = AnimationCreator.createAnimation("assets/fightingscreen/Heroes/Knight 1 Attack-min.png", 0.14f,1,7);
        iddle = AnimationCreator.createAnimation("assets/fightingscreen/Heroes/Knight 1 Iddle-min.png", 0.14f, 1, 7);
        dead = AnimationCreator.createAnimation("assets/fightingscreen/Heroes/Knight 1 Dead-min.png", 0.14f, 1, 7);

        setAnimation(iddle);
    }


    /**
     * does 8-23 damage to enemy
     * @param fighter enemy target
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
     * Makes 30% of target maximun life.
     * @param fighter enemy target
     */
    @Override
    public boolean attackTwo (Fighter fighter){
        if (fighter instanceof EnemyFighters){
        fighter.setHP(fighter.getHP()-((int)(0.40*(fighter.getMaxHP()- fighter.getHP()))));
        return true;}
        cantclick.play();
        return false;
    }

    /**
     * Deals 3-15 Damage. If the champion kills the target, he heals for 20% of his life.
     * @param fighterOne
     */
    @Override
    public boolean attackThree (Fighter fighterOne, Fighter fighterTwo, Fighter fighterThree){
        if (fighterOne instanceof EnemyFighters){
            fighterOne.setHP(fighterOne.getHP()-MathUtils.random(3,15));
            if (fighterOne.getHP()<= 0 ){
                this.setHP((int)(this.getHP()+ 0.20*this.getMaxHP()));
                if (this.getHP() >= this.getMaxHP())
                this.setHP(this.getHP());
            }
            return true;
        }
        cantclick.play();
        return false;
    }
    public void updateManaBar() { }

    public Label getManaBar() { return manaBar; }

}
