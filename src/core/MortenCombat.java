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

    public Stack<ImportQandA> science;
    public Stack<ImportQandA> geography;
    public Stack<ImportQandA> history;
    public Stack<ImportQandA> art;
    public Stack<ImportQandA> sport;
    public Stack<ImportQandA> entertainment;
    public static ArrayList<Stack<ImportQandA>> topics;

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

        File data1= new File("assets\\QnA\\Science.txt");
        File data2= new File("assets\\QnA\\Geography.txt");
        File data3= new File("assets\\QnA\\History.txt");
        File data4= new File("assets\\QnA\\Art.txt");
        File data5= new File("assets\\QnA\\Sport.txt");
        File data6= new File("assets\\QnA\\Entertainment.txt");

        Stack<ImportQandA> science = new Stack<>();
        Stack<ImportQandA> geography = new Stack<>();
        Stack<ImportQandA> history = new Stack<>();
        Stack<ImportQandA> art = new Stack<>();
        Stack<ImportQandA> sport = new Stack<>();
        Stack<ImportQandA> entertainment = new Stack<>();

        ImportQandA.filler(science,data1);
        ImportQandA.filler(geography,data2);
        ImportQandA.filler(history,data3);
        ImportQandA.filler(art,data4);
        ImportQandA.filler(sport,data5);
        ImportQandA.filler(entertainment,data6);

        topics= new ArrayList<Stack<ImportQandA>>();
        topics.add(science);
        topics.add(geography);
        topics.add(history);
        topics.add(art);
        topics.add(sport);
        topics.add(entertainment);

        for (Stack<ImportQandA> QA: topics){
            Collections.shuffle(QA);
        }
        Collections.shuffle(topics);

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