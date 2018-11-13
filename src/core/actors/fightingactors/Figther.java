package core.actors.fightingactors;

public abstract class Figther {

    private String fighterName;
    private int HP;
    private int maxHP;

    abstract void attackOne (Figther fighter);
    abstract void attackTwo (Figther figther);

    public String getFighterName() {
        return fighterName;
    }
    public void setFighterName(String s){
        this.fighterName=s;
    }


    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

}
