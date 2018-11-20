package core.screen;

/**
 * Enum that saves the the layout of our map. Each map contains as the first argument what is their previousMap
 * and the second argument is the name of the file that has to read in assets/maps
 * To create more complex map layout, just keep adding more lines into the ENUM, and connect them by previousmap.
 * The same map (tmx) can be used in different maps.
 */
public enum MapLayout {
    // Previous map starting at 0 to n-1
    // If previousMap 0, then there is no previous map
    MAP1(0,"normal","map1"),
    MAP2(1,"normal","map2"),
    MAP3(2,"wind","map3"),
    MAP4(3,"ice","map4"),
    MAP5(2,"dark","map5"),
    MAP6(5,"dark","map6"),
    MAP7(5,"dark","map7");

    private final int previousMap;
    private final String mapEffect;
    private final String tmx;


    MapLayout (int previousMap, String mapEffect, String tmx){
        this.previousMap = previousMap;
        this.mapEffect = mapEffect;
        this.tmx = tmx;
    }

    public int getLevel(){
        return previousMap;
    }

    public String getTmx(){ return tmx; }

    public String getMapEffect() { return mapEffect; }

}
