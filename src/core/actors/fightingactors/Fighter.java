package core.actors.fightingactors;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import core.framework.BaseActor;


public abstract class Fighter extends BaseActor {

    private String fighterName;
    private int HP;
    private int maxHP;

    private Animation<TextureRegion> animation;
    private float elapsedTime;
    private boolean animationPaused;

    //healthbar

    public Fighter( Stage s)
    {
            super(s);

    }

    public void setAnimation(Animation<TextureRegion> anim)
    {
        animation = anim;
        TextureRegion tr = animation.getKeyFrame(0);
        float w = tr.getRegionWidth();
        float h = tr.getRegionHeight();
        setSize( w, h );
        setOrigin( w/2, h/2 );
    }

    /**
     * Creates an animation from images stored in separate files.
     * @param fileNames array of names of files containing animation images
     * @param frameDuration how long each frame should be displayed
     * @param loop should the animation loop
     * @return animation created (useful for storing multiple animations)
     */
    public Animation<TextureRegion> loadAnimationFromFiles(String[] fileNames, float frameDuration, boolean loop)
    {
        int fileCount = fileNames.length;
        Array<TextureRegion> textureArray = new Array<TextureRegion>();

        for (int n = 0; n < fileCount; n++)
        {
            String fileName = fileNames[n];
            Texture texture = new Texture( Gdx.files.internal(fileName) );
            texture.setFilter( TextureFilter.Linear, TextureFilter.Linear );
            textureArray.add( new TextureRegion( texture ) );
        }

        Animation<TextureRegion> anim = new Animation<TextureRegion>(frameDuration, textureArray);

        if (loop)
            anim.setPlayMode(Animation.PlayMode.LOOP);
        else
            anim.setPlayMode(Animation.PlayMode.NORMAL);

        if (animation == null)
            setAnimation(anim);

        return anim;
    }
    public void setAnimationPaused(boolean pause)
    {
        animationPaused = pause;
    }
    public boolean isAnimationFinished()
    {
        return animation.isAnimationFinished(elapsedTime);
    }
    @Override
    public void act(float dt)
    {
        super.act( dt );

        if (!animationPaused)
            elapsedTime += dt;
    }

    /**
     *  Draws current frame of animation; automatically called by draw method in Stage class. <br>
     *  If color has been set, image will be tinted by that color. <br>
     *  If no animation has been set or object is invisible, nothing will be drawn.
     *  @param batch (supplied by Stage draw method)
     *  @param parentAlpha (supplied by Stage draw method)
     *  @see #setColor
     *  @see #setVisible
     *
     */
    @Override
    public void draw(Batch batch, float parentAlpha)
    {

        // apply color tint effect
        Color c = getColor();
        batch.setColor(c.r, c.g, c.b, c.a);

        if ( animation != null && isVisible() )
            batch.draw( animation.getKeyFrame(elapsedTime),
                    getX(), getY(), getOriginX(), getOriginY(),
                    getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation() );

        super.draw( batch, parentAlpha );
    }

    /**
     * return 1 if the ability is possible
     * return -1 if there is missing resources to make the ability
     * @param fighter target for the attack
     * @return 1: if possible; -1: if not possible.
     */
    abstract public boolean attackOne (Fighter fighter);
    abstract public boolean attackTwo (Fighter fighter);

    public String getFighterName() {
        return fighterName;
    }
    public void setFighterName(String s){
        this.fighterName=s;
    }


    public int getHP() {
        return HP;
    }
    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getMaxHP() {
        return maxHP;
    }
    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

}
