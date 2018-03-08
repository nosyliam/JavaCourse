package com.liam;

import java.awt.*;
import javax.swing.BorderFactory;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class TicTacToeGUI extends JFrame {
    public final Board GameBoard = new Board();
    public final JPanel ButtonPanel = new JPanel();

    enum GAME_STATE {
        O_PLAYING, X_PLAYING, GAME_FINISHED
    }

    public GAME_STATE State = GAME_STATE.O_PLAYING;

    TicTacToeGUI() {
        super("Tic-Tac-Toe");
        InitUI();
    }

    private void InitUI() {
        getContentPane().setLayout(new BorderLayout());

        ButtonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        ButtonPanel.setLayout(new GridLayout(3, 3, 3, 3));

        for (int n = 0; n < 9; n++) {
            JButton Button = new JButton();
            Button.setPreferredSize(new Dimension(100, 100));
            Button.setModel(new CellModel());
            Button.addActionListener(new CellAction());
            ((CellModel) Button.getModel()).setID(n);
            ((CellModel) Button.getModel()).setGame(this);
            ButtonPanel.add(Button);
        }
        getContentPane().add(ButtonPanel);

        pack();
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private class CellAction extends AbstractAction {
        public CellAction() {
            super("Cell");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton Source = (JButton) e.getSource();
            CellModel Model = CellModel.convert(Source);
            if (Model.isEnabled()) {
                String CurrentPlayer = (State == GAME_STATE.O_PLAYING) ? "O" : "X";
                Source.setText(CurrentPlayer);
                if (Model.setEnabled((State == GAME_STATE.O_PLAYING) ? Board.CELL_STATE.O : Board.CELL_STATE.X)) {
                    JOptionPane.showMessageDialog(null, String.format("%s won!", CurrentPlayer));
                    for (Component Button : ButtonPanel.getComponents()) {
                        if (Button instanceof JButton) {
                            ((CellModel) ((JButton) Button).getModel()).reset();
                            ((JButton) Button).setText("");
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TicTacToeGUI GUI = new TicTacToeGUI();
            GUI.setVisible(true);
        });
    }
}
