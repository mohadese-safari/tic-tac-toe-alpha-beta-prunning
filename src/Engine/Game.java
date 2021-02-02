package Engine;
import Constants.CellType;
import Modules.*;
import javax.swing.JOptionPane;

public class Game {

    private static Maximizer maximizer; // PC
    private static Minimizer minimizer; // User
    private static Player turn;
    private static State currentState;
    private static Board board;
    static int level = 1;

    public static void main(String[] args) {
        board = new Board();
        runGame();

    }

    public static void playComputerTurn() {
        currentState = alphaBetaSearch(currentState);
        board.showCurrentState();
        Game.switchTurn();
    }

    static void runGame() {
        maximizer = new Maximizer(CellType.X);
        minimizer = new Minimizer(maximizer);
        turn = maximizer;

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                board.setVisible(true);
            }
        });
        State start = new State();
        currentState = start;
        playComputerTurn();
    }

    public static State alphaBetaSearch(State start) {
        int depth = 0;
        int value = maxValue(start, Integer.MIN_VALUE, Integer.MAX_VALUE, 0);

        for (State successor : start.getSuccessors()) {
            if (successor.getValue() == value) {
                return successor;
            }
        }

        return null;
    }

    public static int maxValue(State s, int alpha, int beta, int depth) {

        int utility, value;
        if ((utility = terminalTest(s, depth)) != -10) {
            s.setValue(utility);
            return utility;
        }

        value = Integer.MIN_VALUE;
        s.generateSuccessors(maximizer.getPlayerBead());
        for (State successor : s.getSuccessors()) {
            value = Math.max(value, minValue(successor, alpha, beta, depth + 1));
            // Beta cut
            if (value >= beta) {
                s.setValue(value);
                //System.out.println(value);
                return value;
            }
            alpha = Math.max(alpha, value);
        }
        s.setValue(value);
        return value;
    }

    public static int minValue(State s, int alpha, int beta, int depth) {

        int utility, value;
        if ((utility = terminalTest(s, depth)) != -10) {
            s.setValue(utility);
            return utility;
        }

        value = Integer.MAX_VALUE;
        s.generateSuccessors(minimizer.getPlayerBead());
        for (State successor : s.getSuccessors()) {
            value = Math.min(value, maxValue(successor, alpha, beta, depth + 1));
            // Alpha cut
            if (value <= alpha) {
                s.setValue(value);
                return value;
            }
            beta = Math.min(beta, value);
        }
        s.setValue(value);
        return value;
    }

    public static int terminalTest(State s, int depth) { // returns -10 if not a terminal
        if (wins(s, maximizer.getPlayerBead())) {
            return 1;
        }
        if (wins(s, minimizer.getPlayerBead())) {
            return -1;
        }

        if (depth == 9) {
            return 0;
        }

        return -10;

    }

    public static boolean wins(State s, CellType bead) {

        if (rowOneMatches(s, bead)) {
            return true;
        }

        if (rowTwoMatches(s, bead)) {
            return true;
        }

        if (rowThreeMatches(s, bead)) {
            return true;
        }

        if (colOneMatches(s, bead)) {
            return true;
        }

        if (colTwoMatches(s, bead)) {
            return true;
        }

        if (colThreeMatches(s, bead)) {
            return true;
        }

        if (mainDiagonMatches(s, bead)) {
            return true;
        }

        if (secondDiagonMatches(s, bead)) {
            return true;
        }

        return false;
    }

    static boolean rowOneMatches(State s, CellType bead) {
        CellType[][] cells = s.getCells();
        return (cells[0][0] == bead && cells[0][1] == bead && cells[0][2] == bead);
    }

    static boolean rowTwoMatches(State s, CellType bead) {
        CellType[][] cells = s.getCells();
        return (cells[1][0] == bead && cells[1][1] == bead && cells[1][2] == bead);
    }

    static boolean rowThreeMatches(State s, CellType bead) {
        CellType[][] cells = s.getCells();
        return (cells[2][0] == bead && cells[2][1] == bead && cells[2][2] == bead);
    }

    static boolean colOneMatches(State s, CellType bead) {
        CellType[][] cells = s.getCells();
        return (cells[0][0] == bead && cells[1][0] == bead && cells[2][0] == bead);
    }

    static boolean colTwoMatches(State s, CellType bead) {
        CellType[][] cells = s.getCells();
        return (cells[0][1] == bead && cells[1][1] == bead && cells[2][1] == bead);
    }

    static boolean colThreeMatches(State s, CellType bead) {
        CellType[][] cells = s.getCells();
        return (cells[0][2] == bead && cells[1][2] == bead && cells[2][2] == bead);
    }

    static boolean mainDiagonMatches(State s, CellType bead) {
        CellType[][] cells = s.getCells();
        return (cells[0][0] == bead && cells[1][1] == bead && cells[2][2] == bead);
    }

    static boolean secondDiagonMatches(State s, CellType bead) {
        CellType[][] cells = s.getCells();
        return (cells[0][2] == bead && cells[1][1] == bead && cells[2][0] == bead);
    }

    public static void switchTurn() {
        if (turn == maximizer) {
            turn = minimizer;
        } else {
            turn = maximizer;
        }
        board.turnLabel.setIcon(new javax.swing.ImageIcon("src\\images\\" + turn.getPlayerBead() + "_TURN.png"));
        Player player = gameOver();
        if (player != null) {
            endGame(player);
        } else if (level == 9) {
            endGame(player);
        }
        level++;
    }

    public static Player gameOver() { // null if game not ended
        if (wins(currentState, maximizer.getPlayerBead())) {
            return maximizer;
        } else if (wins(currentState, minimizer.getPlayerBead())) {
            return minimizer;
        }
        return null;
    }

    public static void endGame(Player winner) {
        board.setEnabled(false);
        String message;
        if (winner == maximizer) {
            message = "You lose ! ";
        } else if(winner == minimizer){
            message = "You won ! ";
        }
        else{
            message = "Tie ! ";
        }
        JOptionPane.showMessageDialog(null, message, "Game is Over", JOptionPane.OK_OPTION);
    }

    public static Maximizer getMaximizer() {
        return maximizer;
    }

    public static void setMaximizer(Maximizer aMaximizer) {
        maximizer = aMaximizer;
    }

    public static Minimizer getMinimizer() {
        return minimizer;
    }

    public static void setMinimizer(Minimizer aMinimizer) {
        minimizer = aMinimizer;
    }

    public static Player getTurn() {
        return turn;
    }

    public static void setTurn(Player aTurn) {
        turn = aTurn;
    }

    public static State getCurrentState() {
        return currentState;
    }

    public static void setCurrentState(State aCurrentState) {
        currentState = aCurrentState;
    }

}
