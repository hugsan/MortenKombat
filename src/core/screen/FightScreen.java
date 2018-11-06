package core.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import core.MortenCombat;
import core.actors.fight.*;
import core.framework.BaseActor;
import core.framework.BaseScreen;


public class FightScreen extends BaseScreen {
    LevelScreen previousMap;
    Fighter fighterOne;
    Enemy enemyOne;

    public FightScreen(LevelScreen prev){
        super();
        previousMap = prev;
    }


    public void initialize() {
        BaseActor fightBackground = new BaseActor(0,0, mainStage);
        fightBackground.loadTexture( "assets/img/dungeonbackground.jpg" );
        fightBackground.setSize(800,600);
        fighterOne = new Fighter();
        enemyOne = new Enemy();
        System.out.println("champion skill 1: q \n champion skill 2: a \n enemy skill 1: e");


    }

    public void update(float dt) {
        // hero movement controls
        if (enemyOne.getHp() <= 0){
                MortenCombat.setActiveScreen(previousMap);
        }
        if (Gdx.input.isKeyPressed(Keys.Q)){
            System.out.println("I have attack the enemy");
            enemyOne.setHp(enemyOne.getHp() - fighterOne.attackOne());
        }
        if (Gdx.input.isKeyPressed(Keys.A)){
            fighterOne.attachTwo();

        }
        if (Gdx.input.isKeyPressed(Keys.E)){
            fighterOne.setHp(fighterOne.getHp() - enemyOne.attackOne());
        }





    }
}


