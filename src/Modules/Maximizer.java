package Modules;
import Constants.CellType;

public class Maximizer extends Player {

    public Maximizer(CellType playerBead) {
        super(playerBead);
        if (playerBead == CellType.X) {
            setOpponent(CellType.O);
        } else {
            setOpponent(CellType.X);
        }
    }


}
