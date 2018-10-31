import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class Launcher
{
    public static void main (String[] args)
    {
        Game myGame = new MortenCombat();
        LwjglApplication launcher = new LwjglApplication( myGame, "Morten Combat", 800, 600 );
    }
}