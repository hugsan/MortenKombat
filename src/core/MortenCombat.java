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

    /*
    public void importing() throws FileNotFoundException {

        File data = new File("assets\\QnA\\QnAData.txt");
        File data2 = new File("assets\\QnA\\Science.txt");
        File data3 = new File("assets\\QnA\\Geography.txt");
        File data4 = new File("assets\\QnA\\History.txt");
        File data5 = new File("assets\\QnA\\Art.txt");
        File data6 = new File("assets\\QnA\\Sport.txt");
        File data7 = new File("assets\\QnA\\Entertainment.txt");

        ArrayList<ImportQandA> listQScience = new ArrayList<ImportQandA>();
        ArrayList<ImportQandA> listQGeography = new ArrayList<ImportQandA>();
        ArrayList<ImportQandA> listQHistory = new ArrayList<ImportQandA>();
        ArrayList<ImportQandA> listQArt = new ArrayList<ImportQandA>();
        ArrayList<ImportQandA> listQSport = new ArrayList<ImportQandA>();
        ArrayList<ImportQandA> listQEntertainment = new ArrayList<ImportQandA>();

        ImportQandA.filler(5,listQScience,data2);

        ImportQandA a = listQScience.get(2);
//        System.out.println(a.question);
    }*/

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

    //method used to start the levelScreen from another screen (for example MenuScreen)
    public static void startGame(){
        setActiveScreen( new LoadingScreen(layout[0]) );
        LevelScreen.musicPlay();
    }

}