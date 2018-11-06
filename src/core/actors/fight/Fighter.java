package core.actors.fight;

public class Fighter {


    int hp = 100;


    public int attackOne(){
        return 20;
    }
    public void attachTwo(){
        hp = hp + 10;
    }
    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

}
