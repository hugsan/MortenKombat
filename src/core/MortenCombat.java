package core;
/**
 *
 */

import core.framework.BaseGame;
import core.screen.LevelScreen;
import core.screen.MapLayout;

public class MortenCombat extends BaseGame {

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
        // initialize all the maps in the same way until m7
        setActiveScreen( layout[0] );
    }
}