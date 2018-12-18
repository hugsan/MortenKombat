package core.actors.exploringactors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import core.framework.BaseActor;

/**
 * This class makes the animated torches on the maps. It have no interactions with gameplay.
 */

public class Torch extends BaseActor
{
    private Animation torchAnimation;

    /**
     * Constructor that initialize torch with the animations and default animation.
     * @param x X coordinates where the actor is created
     * @param y Y coordinates where the actor is created
     * @param s Stage where the actor is created
     */
    public Torch(float x, float y, Stage s)
    {
        super(x,y,s);
        String fileName = "assets/img/torch.png";
        int rows = 1;
        int cols = 9;
        Texture texture = new Texture(Gdx.files.internal(fileName), true);
        int frameWidth = texture.getWidth() / cols;
        int frameHeight = texture.getHeight() / rows;
        float frameDuration = 0.14f;
        TextureRegion[][] temp = TextureRegion.split(texture, frameWidth, frameHeight);
        Array<TextureRegion> textureArray = new Array<TextureRegion>();
        for (int c = 0; c < cols; c++)
            textureArray.add( temp[0][c] );
        torchAnimation = new Animation(frameDuration, textureArray, Animation.PlayMode.LOOP_PINGPONG);

        setAnimation(torchAnimation);

    }
    public void act(float dt)
    {
        super.act(dt);
    }

}