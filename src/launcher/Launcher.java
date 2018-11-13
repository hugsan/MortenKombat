package launcher;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import core.ImportQandA;
import core.MortenCombat;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Launcher
{
    public static void main (String[] args) throws FileNotFoundException
    {
        Game myGame = new MortenCombat();
        LwjglApplication launcher = new LwjglApplication( myGame, "Morten Combat", 800, 600 );


    }
}
