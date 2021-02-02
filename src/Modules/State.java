package Modules;
import Constants.*;
import java.util.ArrayList;
public class State implements Cloneable {

    static int NON_DETERMINED_STATE = -2;
    private CellType[][] cells = new CellType[3][3];
    private ArrayList<State> successors;
    private int value;

    {
        value = NON_DETERMINED_STATE;
    }

    public State() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                cells[row][col] = CellType.N;
            }
        }
    }

    public State(CellType[][] cells) {
        this.cells = cells;
    }

    public void generateSuccessors(CellType bead) {
        successors = new ArrayList<State>();
        State successor;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (cells[row][col] == CellType.N) {
                    successor = clone();
                    successor.placeBead(bead, row, col);
                    //System.out.println(successor);
                    successors.add(successor);
                }
            }
        }
    }

    // Deep copy
    public State clone() {
        CellType[][] clonedCells = new CellType[3][3];
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (cells[row][col] == CellType.X) {
                    clonedCells[row][col] = CellType.X;
                } else if (cells[row][col] == CellType.O) {
                    clonedCells[row][col] = CellType.O;
                } else {
                    clonedCells[row][col] = CellType.N;
                }
            }
        }
        return new State(clonedCells);
    }

    public CellType[][] getCells() {
        return cells;
    }

    public void setCells(CellType[][] cells) {
        this.cells = cells;
    }

    public ArrayList<State> getSuccessors() {
        return successors;
    }

    public void setSuccessors(ArrayList<State> successors) {
        this.successors = successors;
    }

    void placeBead(CellType bead, int row, int col) {
        cells[row][col] = bead;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String toString() {
        String display = "";
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                display += cells[row][col].toString() + " ";
            }
            display += "\n";
        }
        return display;
    }
}
