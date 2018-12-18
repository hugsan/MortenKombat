package core.actors.fightingactors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.compression.lzma.Base;
import core.framework.BaseActor;
import core.framework.BaseGame;

/**
 * Abstract class that represent the fighters in the FightScreen. Fighters has HP, maxHP, fighterName, labels and
 * animation in common.
 * Also 2 abstract method are implemented to represent the attack that they can do.
 */
public abstract class Fighter extends BaseActor {

    private String fighterName;
    private int HP;
    private int maxHP;

    private int aux;

    private Label namePlate;
    private Label health;

    //Animation iddle, attack, dead
    public Animation attack;
    public Animation iddle;
    public Animation dead;

    public Fighter( Stage s) {
        super(s);

        health = new Label("Health: ", BaseGame.labelStyle);
        health.setColor( Color.RED );
        health.setFontScale(0.6f);

        namePlate = new Label(""+fighterName, BaseGame.labelStyle);
        //namePlate.setText(""+fighterName);
        namePlate.setColor( Color.WHITE );
        namePlate.setFontScale(0.5f);

    }

    /**
     * Abstract method that should be implemented in the classes that extends Fighter. Those attack will
     * be used in the FightScreen.
     * @param fighter target for our attack
     * @return True if you can deal with the ability, false if you cannot deal the ability.
     */
    abstract public boolean attackOne (Fighter fighter);
    /**
     * Abstract method that should be implemented in the classes that extends Fighter. Those attack will
     * be used in the FightScreen.
     * @param fighter target for our attack
     * @return True if you can deal with the ability, false if you cannot deal the ability.
     */
    abstract public boolean attackTwo (Fighter fighter);

    /**
     * Abstract method that will implement the update on the manaBar.
     */
    abstract public void updateManaBar();

    public Label getFighterNamePlate() {
        return namePlate;
    }
    public String getFighterName() { return fighterName; }
    public void setFighterName(String s){
        this.fighterName=s;
    }
    public int getHP() {
        return HP;
    }
    public void setHP(int HP) {
        this.HP = HP;
    }
    public Label getHPBar() { return health;  }

    /**
     * Method that updates the HP label with the HP values from the fighters.
     */
    public void updateHPBar() {
        health.setText(getHP() +"/" + getMaxHP());
        if (getHP ()<0)
            health.setText ( "0/" + getMaxHP () );
        health.setSize(110,20);
    }

    /**
     * Method that updates the NamePlate of the fighters and set the color to White
     */
    public void updateNamePlate() {
        namePlate.setText("" + getFighterName());
        namePlate.setColor( Color.WHITE );
    }

    /**
     * Method that changes the NAmePlate color to Green.
     */
    public void updateNameColor() {
        namePlate.setColor( Color.GREEN );
    }

    public int getMaxHP() {
        return maxHP;
    }
    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public int getAux() {
        return aux;
    }
    public void setAux(int aux) {
        this.aux = aux;
    }
}
