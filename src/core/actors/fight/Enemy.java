package core.actors.fight;

public class Enemy {


    int hp = 75;

    public int attackOne(){
        return 10;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}
