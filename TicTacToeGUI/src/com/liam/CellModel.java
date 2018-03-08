package com.liam;

import javax.swing.*;
import com.liam.Board.*;
import com.liam.TicTacToeGUI.*;

public class CellModel extends DefaultButtonModel {
    private TicTacToeGUI Game;
    private int ID;
    private int row;
    private int col;

    public static CellModel convert(Object input) {
        return input == null ? null : (CellModel) ((JButton) input).getModel();
    }

    /**
     * Sets the ID of the cell
     * @param id
     */
    public void setID(int id) {
        ID = id;
        row = (int) Math.floor(ID / 3);
        col = ID % 3;
    }

    /**
     * Sets the game instance
     * @param game
     */
    public void setGame(TicTacToeGUI game) {
        Game = game;
    }

    public boolean setEnabled(CELL_STATE state) {
        if (state == CELL_STATE.NONE) {
            super.setEnabled(true);
            return false;
        }
        super.setEnabled(false);

        Game.GameBoard.SetCell(row, col, state);
        Game.State = (Game.State == GAME_STATE.O_PLAYING) ? GAME_STATE.X_PLAYING : GAME_STATE.O_PLAYING;
        return Game.GameBoard.CheckWin(row, col, state);
    }

    /**
     * Resets the cell
     */
    public void reset() {
        super.setEnabled(true);
        Game.GameBoard.SetCell(row, col, CELL_STATE.NONE);
    }

}
