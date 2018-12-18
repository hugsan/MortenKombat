package core.utils;

import core.framework.BaseGame;
import core.screen.*;
import core.utils.menu.MainMenuScreen;
import core.utils.menu.QAerrorScreen;
import java.io.File;
import java.util.Collections;
import java.util.Stack;

/**
 * This is our game class, in here all the questions are loaded from the questionsanswers.txt file.
 * And all the maps from the MapLayout enum are also created here.
 * There are also some values like the game volume and what characters are selected at the start of the game.
 */

public class MortenKombat extends BaseGame {
    public static float volume = 0.05f;
    public static int fighterN = 0;
    public static int mageN = 0;
    public static int supportN = 0;

    public static Stack<ImportQandA> questionAnswer;
    public static Stack<ImportQandA> questionBackUp;

    static private ExploringScreen[] layout = new ExploringScreen[MapLayout.values().length];

    public void create() {

        super.create();

        File data = new File("assets\\QnA\\questionsanswers.txt");
        questionAnswer = new Stack<ImportQandA>();
        ImportQandA.filler(questionAnswer,data);
        Collections.shuffle(questionAnswer);

        questionBackUp = new Stack<ImportQandA>();
        questionBackUp.addAll(questionAnswer);

        if (ImportQandA.fileError)
            setActiveScreen(new QAerrorScreen());
        else{
            MainMenuScreen menu = new MainMenuScreen();
            setActiveScreen( menu );
        }
    }

    /**
     * Method used to start the ExploringScreen from another screen (for example the menu)
     */
    public static void startGame() {
            setActiveScreen( layout[0] );
            ExploringScreen.musicPlay();
    }

    /**
     * Method used to create all the ExploringMaps and storing them in the layout Array.
     */
    public static void createMaps(){
        int i = 0;

        for (MapLayout map : MapLayout.values()){
            ExploringScreen.mapName = map.getTmx();
            ExploringScreen.mapEffect = map.getMapEffect();
            if (map.getLevel() == 0){
                layout[i] = new ExploringScreen();
            }else {
                layout[i] = new ExploringScreen(layout[map.getLevel() -1]);
            }
            i++;
        }
    }
}