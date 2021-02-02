package Modules;

public class Minimizer extends Player {

    public Minimizer(Maximizer maximizer) {
        super(maximizer.getOpponent());
        setOpponent(maximizer.getPlayerBead());
    }

}
