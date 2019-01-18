package core.actors.fightingactors;

public interface Poisonable {
    default void poison (int isPoisonTurn, Fighter target){
        target.setPoison ( true );
        target.setIsPoisonTurn ( target.getIsPoisonTurn () + isPoisonTurn );
        target.setColor (0f, 255f, 0f, 255f  );
    }
}
