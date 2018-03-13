package com.liam;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class Simulation extends JPanel {
    public enum CELL_STATE {
        DEAD, ALIVE
    }
    private CELL_STATE[][] Cells = new CELL_STATE[Life.CELLS_X][Life.CELLS_Y];

    private Timer stepTimer;

    Simulation() {
        super();

        // Create the simulation panel
        setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 20));
        setPreferredSize(new Dimension(Life.CELLS_X * Life.CELL_WIDTH, Life.CELLS_Y * Life.CELL_HEIGHT));
        setMaximumSize(new Dimension(getPreferredSize()));

        // Add listener for cell interaction with the mouse
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                int posX = (int) Math.ceil(me.getX() / Life.CELL_WIDTH);
                int posY = (int) Math.ceil(me.getY() / Life.CELL_HEIGHT);
                Cells[posX][posY] = (Cells[posX][posY] == CELL_STATE.ALIVE ? CELL_STATE.DEAD : CELL_STATE.ALIVE);
                repaint();
            }
        });

        // Create step timer
        stepTimer = new Timer(1000, evt -> {
            doStep();
        });
    }

    public void startTimer() {
        stepTimer.start();
    }

    public void stopTimer() {
        stepTimer.stop();
    }

    public void doStep() {
        // Calculate the amount of neighbors each cell has
        int d;
        int[][] neighbors = new int[Life.CELLS_X][Life.CELLS_Y];
        for (int x = 0; x < Life.CELLS_X; x++) {
            for (int y = 0; y < Life.CELLS_Y; y++) {
                if (Cells[x][y] != CELL_STATE.ALIVE) continue;
                d = (y - 1 >= 0) ? neighbors[x][y - 1] += 1 : 0;
                d = (x != 0 && y - 1 >= 0) ? neighbors[x - 1][y - 1] += 1 : 0;
                d = (x - Life.CELLS_X != -1 && y - 1 >= 0) ? neighbors[x + 1][y - 1] += 1 : 0;
                d = (x != 0) ? neighbors[x - 1][y] += 1 : 0;
                d = (x - Life.CELLS_X != -1) ? neighbors[x + 1][y] += 1 : 0;
                d = (y + 1 < Life.CELLS_Y) ? neighbors[x][y + 1] += 1 : 0;
                d = (x != 0 && y - 1 >= 0 && y + 1 < Life.CELLS_Y) ? neighbors[x - 1][y + 1] += 1 : 0;
                d = (x - Life.CELLS_X != -1 && y + 1 < Life.CELLS_Y) ? neighbors[x + 1][y + 1] += 1 : 0;
            }
        }

        // Process every cell accordingly
        for (int x = 0; x < Life.CELLS_X; x++) {
            for (int y = 0; y < Life.CELLS_Y; y++) {
                switch(neighbors[x][y]) {
                    case 0:
                    case 1:
                        Cells[x][y] = CELL_STATE.DEAD;
                        break;
                    case 3:
                        Cells[x][y] = CELL_STATE.ALIVE;
                        break;
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                        Cells[x][y] = CELL_STATE.DEAD;
                        break;
                }
            }
        }
        repaint();

    }

    @Override
    public void paintComponent(Graphics g) {
        for (int x = 0; x < Life.CELLS_X; x++) {
            for (int y = 0; y < Life.CELLS_Y; y++) {
                g.setColor(Cells[x][y] == CELL_STATE.ALIVE ? Color.green : Color.black);
                g.fillRect(x * Life.CELL_WIDTH, y * Life.CELL_HEIGHT, Life.CELL_WIDTH, Life.CELL_HEIGHT);
            }
        }
    }
}
