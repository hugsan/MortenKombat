package launcher;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import core.utils.MortenKombat;

public class Launcher
{
    public static void main (String[] args)
    {
        Game myGame = new MortenKombat();
        LwjglApplication launcher = new LwjglApplication( myGame, "Morten Kombat", 800, 600 );
    }
}
