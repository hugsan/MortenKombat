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

public abstract class Fighter extends BaseActor {

    private String fighterName;
    private int HP;
    private int maxHP;

    private int aux;

    private Label namePlate;
    private Label health;

    private Animation<TextureRegion> animation;
    private float elapsedTime;
    private boolean animationPaused;

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
     * return 1 if the ability is possible
     * return -1 if there is missing resources to make the ability
     * @param fighter target for the attack
     * @return 1: if possible; -1: if not possible.
     */
    abstract public boolean attackOne (Fighter fighter);
    abstract public boolean attackTwo (Fighter fighter);
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

    public void updateHPBar() {
        health.setText(getHP() +"/" + getMaxHP());
        if (getHP ()<0)
            health.setText ( "0/" + getMaxHP () );
        health.setSize(110,20);
    }
    public void updateNamePlate() {
        namePlate.setText("" + getFighterName());
        namePlate.setColor( Color.WHITE );
    }
    public void updateNameColor() {
        namePlate.setColor( Color.GREEN );
    }

    public int getMaxHP() {
        return maxHP;
    }
    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
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

    public int getAux() {
        return aux;
    }

    public void setAux(int aux) {
        this.aux = aux;
    }

}
