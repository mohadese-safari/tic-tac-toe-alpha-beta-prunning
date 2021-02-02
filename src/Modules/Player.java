package Modules;
import Constants.CellType;

public abstract class Player {

    private CellType playerBead;
    private CellType opponent;

    public Player(CellType playerBead) {
        this.playerBead = playerBead;
    }

    public CellType getOpponent() {
        return opponent;
    }

    public void setOpponent(CellType opponent) {
        this.opponent = opponent;
    }

    public CellType getPlayerBead() {
        return playerBead;
    }

    public void setPlayerBead(CellType playerBead) {
        this.playerBead = playerBead;
    }

}
