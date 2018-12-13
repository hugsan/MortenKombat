package launcher;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import core.utils.MortenKombat;

public class Launcher
{
    public static void main (String[] args)
    {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.resizable = false;
        config.height = 600;
        config.width = 800;
        config.title = "Morten Kombat";
        config.addIcon("assets/img/Logo.png", Files.FileType.Internal);

        LwjglApplication launcher = new LwjglApplication( new MortenKombat(), config);

    }
}
