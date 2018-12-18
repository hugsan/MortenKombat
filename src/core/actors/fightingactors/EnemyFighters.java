package core.actors.fightingactors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Abstrac class that extends Fighter. Main use of this class is to distinct it from Champions.
 * Also should be easier to implement functionaries to all the enemies when another abstract class is extended from Fighter.
 * There is different declaration or implementation from the class Fighter
 */

public abstract class EnemyFighters extends Fighter {

    public EnemyFighters(Stage s){
        super(s);
    }


}
