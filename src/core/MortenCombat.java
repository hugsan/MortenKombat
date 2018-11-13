package core;
/**
 *
 */
import core.actors.fightingactors.testingFigther;
import core.framework.BaseGame;
import core.screen.LevelScreen;
import core.screen.MainMenuScreen;
import core.screen.MapLayout;

public class MortenCombat extends BaseGame {
    static private testingFigther fighter = new testingFigther();
    static LevelScreen[] layout = new LevelScreen[MapLayout.values().length];

    public void create() {
        super.create();

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
        setActiveScreen( layout[0] );
    }

    public static testingFigther getFigther(){
        return fighter;
    }
}