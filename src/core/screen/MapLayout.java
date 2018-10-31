package core.screen;

public enum MapLayout {
//previous map starting at 0 to n-1
    //if previousMap 0, then there is no previous map
    MAP1(0, "map1"),
    MAP2(1,"map2"),
    MAP3(2,"map3"),
    MAP4(3,"map4"),
    MAP5(2,"map5"),
    MAP6(5,"map6"),
    MAP7(5,"map7");






    private final int previousMap;
    private final String tmx;
    private MapLayout (int previousMap, String tmx){
        this.previousMap = previousMap;
        this.tmx = tmx;
    }

    public int getLevel(){
        return previousMap;
    }
    public String getTmx(){
        return tmx;
    }

}
