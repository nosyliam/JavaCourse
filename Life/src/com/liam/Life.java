package com.liam;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import com.liam.Simulation;

public class Life extends JFrame implements ActionListener {
    private Simulation Simulation = new Simulation();

    public static int CELLS_X = 50;
    public static int CELLS_Y = 50;
    public static int CELL_WIDTH = 10;
    public static int CELL_HEIGHT = 10;

    Life() {
        super("Game of Life");

        JButton stepButton = new JButton("Step");
        stepButton.setActionCommand("Step");
        stepButton.addActionListener(this);
        stepButton.setMaximumSize(new Dimension(130, 30));

        JButton runButton = new JButton("Run");
        runButton.setActionCommand("Run");
        runButton.addActionListener(this);
        runButton.setMaximumSize(new Dimension(130, 30));


        JButton stopButton = new JButton("Stop");
        stopButton.setActionCommand("Stop");
        stopButton.addActionListener(this);
        stopButton.setMaximumSize(new Dimension(130, 30));


        JPanel controlPane = new JPanel();
        controlPane.setPreferredSize(new Dimension(CELLS_X * CELL_WIDTH, 30));
        controlPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 10));
        controlPane.setLayout(new BoxLayout(controlPane, BoxLayout.X_AXIS));
        controlPane.add(stepButton);
        controlPane.add(Box.createHorizontalStrut(5));
        controlPane.add(runButton);
        controlPane.add(Box.createHorizontalStrut(5));
        controlPane.add(stopButton);

        Box box = new Box(BoxLayout.Y_AXIS);
        box.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        box.add(Box.createVerticalGlue());
        box.add(controlPane);
        box.add(Box.createVerticalGlue());
        box.add(Simulation);
        box.add(Box.createVerticalGlue());

        add(box);
        pack();
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Step":
                Simulation.doStep();
                break;
            case "Run":
                Simulation.startTimer();
                break;
            case "Stop":
                Simulation.stopTimer();
                break;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Life Game = new Life();
            Game.setVisible(true);
        });
    }
}
