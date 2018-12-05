package core.actors.exploringactors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import core.framework.BaseActor;

public class Morten extends BaseActor{
        private Animation idle;
        private float facingAngle;



    public Morten (float x, float y, Stage s)
        {
            super(x,y,s);
            String fileName = "assets/Fightingscreen/Boss/Morten-Idle.png";
            int rows = 1;
            int cols = 26;
            Texture texture = new Texture( Gdx.files.internal(fileName), true);
            int frameWidth = texture.getWidth() / cols;
            int frameHeight = texture.getHeight() / rows;
            float frameDuration = 0.2f;
            TextureRegion[][] temp = TextureRegion.split(texture, frameWidth, frameHeight);
            Array<TextureRegion> textureArray = new Array<TextureRegion>();

            for (int c = 0; c < cols; c++)
                textureArray.add( temp[0][c] );
            idle = new Animation(frameDuration, textureArray, Animation.PlayMode.LOOP_PINGPONG);
            textureArray.clear();

            setAnimation ( idle );
            setScale ( 1);
            setBoundaryPolygon(8);
            boundToWorld();


        }
    public void act(float dt)
    {
        super.act(dt);

        alignCamera();
        boundToWorld();
        applyPhysics(dt);
    }

    }
