package core.actors.fightingactors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.awt.*;

public class WarriorOne extends Champion {

    public WarriorOne(Stage s){
        super(s);
        setFighterName("Paul");
        setHP(200);
        setMaxHP(200);


    }


    /**
     * does 8-23 damage to enemy
     * @param fighter enemy target
     */
    @Override
    public boolean attackOne(Fighter fighter){
        fighter.setHP(fighter.getHP()-MathUtils.random(8,23));
        return true;
    }

    /**
     * Makes 30% of target maximun life.
     * @param fighter enemy target
     */
    @Override
    public boolean attackTwo (Fighter fighter){
        fighter.setHP(fighter.getHP()-((int)(0.40*(fighter.getMaxHP()- fighter.getHP()))));
        return true;
    }

    /**
     * Deals 3-15 Damage. If the champion kills the target, he heals for 20% of his life.
     * @param fighter
     */
    @Override
    public boolean attackThree (Fighter fighter){
        fighter.setHP(fighter.getHP()-MathUtils.random(3,15));
        if (fighter.getHP()<= 0 ){
            this.setHP((int)(this.getHP()+ 0.20*this.getMaxHP()));
            if (this.getHP() >= this.getMaxHP())
                this.setHP(this.getHP());
        }
        return true;
    }

}
