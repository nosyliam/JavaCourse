package com.liam;

import java.util.Date;
import java.util.Random;


/* Re-using old code because it's not worth remaking a class with the same functionality */
public class Board {
    private static CELL_STATE[][] Cells = new CELL_STATE[3][3];

    enum CELL_STATE {O, X, NONE}

    Board() {
        ClearBoard();
    }

    /**
     * Clear the game board
     */
    public void ClearBoard() {
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                Cells[x][y] = CELL_STATE.NONE;
            }
        }
    }

    /**
     * Sets a cell specified by row and col to the given state.
     * Expects that row and col have already been checked for validity.
     *
     * @param row   the row of the cell to be set
     * @param col   the column of the cell to be set
     * @param state the state that the cell will be set to
     */
    public void SetCell(int row, int col, CELL_STATE state) {
        assert 1 <= row && row <= 3;
        assert 1 <= col && col <= 3;
        Cells[row][col] = state;
    }

    /**
     * Checks if the given player type has won.
     *
     * @param row    the row of the cell to be checked
     * @param col    the column of the cell to be checked
     * @param player the type of player to check a win for
     * @return whether the player has won or not
     */
    public boolean CheckWin(int row, int col, CELL_STATE player) {
        return ((Cells[row][0] == player && Cells[row][1] == player && Cells[row][2] == player)
                || (Cells[0][col] == player && Cells[1][col] == player && Cells[2][col] == player)
                || (row == col && Cells[0][0] == player && Cells[1][1] == player && Cells[2][2] == player)
                || (row + col == 2 && Cells[0][2] == player && Cells[1][1] == player && Cells[2][0] == player));
    }

    /**
     * Makes a choice as an AI. Assumes the AI player is X.
     *
     * @return: The ID of the cell chosen
     */
    public int ChooseAI() {
        Random Rand = new Random(new Date().getTime());
        while (true) {
            int x = Rand.nextInt(3);
            int y = Rand.nextInt(3);
            if (Cells[x][y] == CELL_STATE.NONE) {
                Cells[x][y] = CELL_STATE.X;
                return (x * 3) + y;
            }
        }
    }
}
