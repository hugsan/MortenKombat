package core.actors.fightingactors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * Class that is used to create animation from sprites for our actors.
 */

public class AnimationCreator {


    /**
     * Method used to create animation from horizontal sprite.
     * @param path address on assets of the sprite
     * @param frameDuration time between the frames of the animation
     * @param rows how many rows does the sprite file have.
     * @param cols in case that you have more than one row from the animation, select the row to create the animation
     * @return Animation that has been created
     */
    public static Animation createAnimation(String path,float frameDuration, int rows, int cols){
        Animation ourAnimation;
        String fileName = path;
        Texture texture = new Texture(Gdx.files.internal(fileName), true);
        int frameWidth = texture.getWidth() / cols;
        int frameHeight = texture.getHeight() / rows;
        TextureRegion[][] temp = TextureRegion.split(texture, frameWidth, frameHeight);
        Array<TextureRegion> textureArray = new Array<TextureRegion>();
        for (int c = 0; c < cols; c++)
            textureArray.add( temp[rows-1][c] );
        ourAnimation = new Animation(frameDuration, textureArray, Animation.PlayMode.LOOP_PINGPONG);
        textureArray.clear();


        return ourAnimation;
    }
}
