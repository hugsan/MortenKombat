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
            if (map.getLevel() == null){
                layout[i] = new LevelScreen();
            }else {
                layout[i] = new LevelScreen(map.getLevel());
            }
            i++;
        }
        // initialize all the maps in the same way until m7

        setActiveScreen( layout[0] );
    }
}