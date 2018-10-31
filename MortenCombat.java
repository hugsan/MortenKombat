public class MortenCombat extends BaseGame
{

    public void create() {
        super.create();
        System.out.println("MortenCombat - create()");

        LevelScreen.mapName = "firstmap";
        LevelScreen m1 = new LevelScreen();

        LevelScreen.mapName = "secondmap";
        LevelScreen m2 = new LevelScreen(m1);

        // initialize all the maps in the same way until m7

        setActiveScreen( m2 );
    }
}