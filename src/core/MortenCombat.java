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

        int i = 0;

        for (MapLayout map : MapLayout.values()){
            LevelScreen.mapName = map.getTmx();
            LevelScreen.mapEffect = map.getMapEffect();
            if (map.getLevel() == 0){
                layout[i] = new LevelScreen();
            }else {
                layout[i] = new LevelScreen(layout[map.getLevel() -1]);
            }
            i++;
        }

        MainMenuScreen menu = new MainMenuScreen();
        setActiveScreen( menu );
    }

    public static void startGame(){
        setActiveScreen( new LoadingScreen(layout[0]) );
        System.out.println(fighterN +" "+ mageN +" "+ supportN);
        LevelScreen.musicPlay();
    }

}
