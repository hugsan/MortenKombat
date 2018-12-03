package core;

import core.framework.BaseGame;
import core.screen.LevelScreen;
import core.screen.LoadingScreen;
import core.screen.MainMenuScreen;
import core.screen.MapLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;



public class MortenCombat extends BaseGame {
    public static float volume = 0.05f;
    public static int fighterN = 0;
    public static int mageN = 0;
    public static int supportN = 0;



    public static Stack<ImportQandA> questionAnswer;
    public static Stack<ImportQandA> questionBackUp;

    public static ArrayList<Stack<ImportQandA>> topics;

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


        File data = new File("assets\\QnA\\questionsanswers.txt");
        questionAnswer = new Stack<ImportQandA>();
        ImportQandA.filler(questionAnswer,data);
        Collections.shuffle(questionAnswer);

        questionBackUp = new Stack<ImportQandA>();
        questionBackUp.addAll(questionAnswer);

        topics= new ArrayList<Stack<ImportQandA>>();

        Collections.shuffle(topics);

        MainMenuScreen menu = null;
        try {
            menu = new MainMenuScreen();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        setActiveScreen( menu );
    }

    /**
     * method used to start the levelScreen from another screen (for example MenuScreen)
     * @throws FileNotFoundException
     */
    public static void startGame() {
        setActiveScreen( new LoadingScreen(layout[0]) );
        LevelScreen.musicPlay();
    }

}