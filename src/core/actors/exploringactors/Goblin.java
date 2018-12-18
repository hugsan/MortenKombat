package core.actors.exploringactors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import core.framework.BaseActor;

/**
 * Class for the goblin actors in the maze.
 * It should walk in a random direction until it hits a wall, then chance to a new random direction.
 * When the Hero hit a skeleton it starts the FightScreen.
 */

public class Goblin extends BaseActor
{
    private Animation north;
    private Animation south;
    private Animation east;
    private Animation west;
    private float facingAngle;

    /**
     * Constructor that initialize Goblins with the animations and default animation.
     * @param x X coordinates where the actor is created
     * @param y Y coordinates where the actor is created
     * @param s Stage where the actor is created
     */
    public Goblin(float x, float y, Stage s)
    {
        super(x,y,s);
        String fileName = "assets/img/Goblin.png";
        int rows = 5;
        int cols = 11;
        Texture texture = new Texture(Gdx.files.internal(fileName), true);
        int frameWidth = texture.getWidth() /cols;
        int frameHeight = texture.getHeight() / rows;
        float frameDuration = 0.18f;
        TextureRegion[][] temp = TextureRegion.split(texture, frameWidth, frameHeight);
        Array<TextureRegion> textureArray = new Array<TextureRegion>();

        for (int c = 0; c < cols; c++)
            textureArray.add( temp[0][c] );
        south = new com.badlogic.gdx.graphics.g2d.Animation(frameDuration, textureArray, com.badlogic.gdx.graphics.g2d.Animation.PlayMode.LOOP_PINGPONG);
        textureArray.clear();
        for (int c = 0; c < cols; c++)
            textureArray.add( temp[1][c] );
        east = new com.badlogic.gdx.graphics.g2d.Animation(frameDuration, textureArray, com.badlogic.gdx.graphics.g2d.Animation.PlayMode.LOOP_PINGPONG);
        textureArray.clear();
        for (int c = 0; c < cols; c++)
            textureArray.add( temp[2][c] );
        north = new com.badlogic.gdx.graphics.g2d.Animation(frameDuration, textureArray, com.badlogic.gdx.graphics.g2d.Animation.PlayMode.LOOP_PINGPONG);
        textureArray.clear();
        for (int c = 0; c < cols; c++)
            textureArray.add( temp[3][c] );
        west = new com.badlogic.gdx.graphics.g2d.Animation(frameDuration, textureArray, com.badlogic.gdx.graphics.g2d.Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(south);
        facingAngle = 270;
        setBoundaryPolygon(8);
        boundToWorld();

        setScale(1.3f);
        setSpeed(MathUtils.random(80,110));
        setMotionAngle( MathUtils.random(0,360));
    }

    /**
     * Method that checks for all the changes at our actor.
     * @param dt elapsed time (second) since last frame (supplied by Stage act method)
     */
    public void act(float dt)
    {
        super.act(dt);

        // pause animation when character not moving
        if ( getSpeed() == 0 )
            setAnimationPaused(true);
        else
        {
            setAnimationPaused(false);

            // set direction animation
            float angle = getMotionAngle();
            if (angle >= 45 && angle <= 135)
            {
                facingAngle = 90;
                setAnimation(north);
            }
            else if (angle > 135 && angle < 225)
            {
                facingAngle = 180;
                setAnimation(west);
            }
            else if (angle >= 225 && angle <= 315)
            {
                facingAngle = 270;
                setAnimation(south);
            }
            else
            {
                facingAngle = 0;
                setAnimation(east);
            }
        }
        boundToWorld();
        applyPhysics(dt);
    }
}
