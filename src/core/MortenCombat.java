package core;
/**
 *
 */

import core.framework.BaseGame;
import core.screen.LevelScreen;
import core.screen.MainMenuScreen;
import core.screen.MapLayout;
import core.screen.OptionsMenuScreen;

public class MortenCombat extends BaseGame {

    static LevelScreen[] layout = new LevelScreen[MapLayout.values().length];
//    static OptionsMenuScreen optionsMenu = new OptionsMenuScreen();


    public void create() {
        super.create();
        LevelScreen[] layout = new LevelScreen[MapLayout.values().length];
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

        // initialize all the maps in the same way until m7
        setActiveScreen( menu );

    }

    public static void startGame(){
        setActiveScreen( layout[0] );
    }
}