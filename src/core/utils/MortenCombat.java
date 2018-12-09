package core.utils;

import core.framework.BaseGame;
import core.screen.*;
import core.utils.menu.MainMenuScreen;
import core.utils.menu.QAerrorScreen;

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

    static ExploringScreen[] layout = new ExploringScreen[MapLayout.values().length];

    public void create() {

        super.create();





        File data = new File("assets\\QnA\\questionsanswers.txt");
        questionAnswer = new Stack<ImportQandA>();
        ImportQandA.filler(questionAnswer,data);
        Collections.shuffle(questionAnswer);

        questionBackUp = new Stack<ImportQandA>();
        questionBackUp.addAll(questionAnswer);

        topics= new ArrayList<Stack<ImportQandA>>();

        Collections.shuffle(topics);

        if (ImportQandA.fileError)
            setActiveScreen(new QAerrorScreen());
        else{
            MainMenuScreen menu = new MainMenuScreen();
            setActiveScreen( menu );
        }
    }

    /**
     * method used to start the levelScreen from another screen (for example MenuScreen)
     *
     */
    public static void startGame() {
            setActiveScreen( new LoadingScreen(layout[5] ));
            ExploringScreen.musicPlay();
    }
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