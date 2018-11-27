package core;
/**
 *
 */
import core.actors.fightingactors.*;
import core.framework.BaseGame;
import core.screen.LevelScreen;
import core.screen.LoadingScreen;
import core.screen.MainMenuScreen;
import core.screen.MapLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;


public class MortenCombat extends BaseGame {
    public static float volume = 0.05f;
    public static int fighterN = 0;
    public static int mageN = 0;
    public static int supportN = 0;

    static LevelScreen[] layout = new LevelScreen[MapLayout.values().length];

    public void create() {

        super.create();

        int i = 0;

        for (MapLayout map : MapLayout.values()){
            LevelScreen.mapName = map.getTmx();
            LevelScreen.mapEffect = map.getMapEffect();
            if (map.getLevel() == 0){
                try {
                    layout[i] = new LevelScreen();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }else {
                try {
                    layout[i] = new LevelScreen(layout[map.getLevel() -1]);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            i++;
        }

        MainMenuScreen menu = null;
        try {
            menu = new MainMenuScreen();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        setActiveScreen( menu );
    }

    //method used to start the levelScreen from another screen (for example MenuScreen)
    public static void startGame() throws FileNotFoundException {
        setActiveScreen( new LoadingScreen(layout[0]) );
        LevelScreen.musicPlay();
    }

}