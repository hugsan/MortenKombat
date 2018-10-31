public class MortenCombat extends BaseGame
{

    public void create() {
        super.create();
        LevelScreen[] layout = new LevelScreen[MapLayout.values().length];
        int i = 0;

        /**LevelScreen.mapName = "firstmap";
        LevelScreen m1 = new LevelScreen();

        LevelScreen.mapName = "secondmap";
        LevelScreen m2 = new LevelScreen(m1);*/
        for (MapLayout map : MapLayout.values()){
            LevelScreen.mapName = map.getTmx();
            if (map.getLevel() == 0){
                layout[i] = new LevelScreen();
            }else {
                System.out.println("we got it!");
                layout[i] = new LevelScreen(layout[map.getLevel() -1]);
            }
            i++;
        }
        // initialize all the maps in the same way until m7

        setActiveScreen( layout[0] );
    }
}