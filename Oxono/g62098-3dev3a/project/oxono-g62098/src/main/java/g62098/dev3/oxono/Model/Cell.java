package g62098.dev3.oxono.Model;

/**
 * The Cell class represents a cell on the Oxono game board.
 * Each cell can either be occupied by a totem or remain empty.
 */
public class Cell {
    private Occupant occupant; // Can be a totem or another piece

    /**
     * Constructor to initialize the cell without an occupant.
     */
    public Cell() {
        this.occupant = null; // Initially, the cell is empty
    }

    /**
     * Checks if the cell is occupied.
     *
     * @return true if the cell is occupied, false otherwise.
     */
    public boolean isOccupied() {
        return occupant != null; // Returns true if occupied, false otherwise
    }

    /**
     * Occupies the cell with an occupant (totem or another piece).
     *
     * @param occupant The occupant to place in the cell.
     */
    public void occupy(Occupant occupant) {
        this.occupant = occupant; // Sets the occupant of the cell
    }

    /**
     * Frees the cell by removing the occupant.
     */
    public void deleteOccupant() {
        this.occupant = null; // Makes the cell empty
    }

    /**
     * Returns the current occupant of the cell.
     *
     * @return The occupant of the cell, or null if the cell is empty.
     */
    public Occupant getOccupiedBy() {
        return occupant;
    }
}
