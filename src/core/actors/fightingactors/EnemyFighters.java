package core.actors.fightingactors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class EnemyFighters extends Fighter {
    Animation attack;
    Animation iddle;
    Animation dead;

    public EnemyFighters(Stage s){
        super(s);
    }
    public Animation getAnimationAttack(){
        return attack;

    }
    public Animation getAnimationIddle(){
        return iddle;
    }
    public Animation getAnimationDead(){
        return dead;
    }

}
