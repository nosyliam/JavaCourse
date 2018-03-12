package com.liam;

import java.awt.*;
import javax.swing.BorderFactory;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class TicTacToeGUI extends JFrame {
    public final Board GameBoard = new Board();
    public final JPanel ButtonPanel = new JPanel();
    public final JLabel XWinsText = new JLabel();
    public final JLabel OWinsText = new JLabel();

    public int XWins = 0;
    public int OWins = 0;

    private boolean PlayingAI = false;
    public JButton[] Cells = new JButton[9];

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
        ButtonPanel.setLayout(new GridBagLayout());
        GridBagConstraints Constraints = new GridBagConstraints();
        Constraints.fill = GridBagConstraints.VERTICAL;
        Constraints.insets = new Insets(3, 3, 3, 3);

        Constraints.gridx = 0;
        Constraints.gridy = 0;
        XWinsText.setText("X Wins: 0");
        ButtonPanel.add(XWinsText, Constraints);

        Constraints.gridx = 2;
        Constraints.gridy = 0;
        OWinsText.setText("O Wins: 0");
        ButtonPanel.add(OWinsText, Constraints);

        Constraints.fill = GridBagConstraints.HORIZONTAL;
        for (int x = 0; x < 3; x++) {
            for (int y = 1; y <= 3; y++) {
                JButton Button = new JButton();
                Button.setPreferredSize(new Dimension(100, 100));
                Button.setModel(new CellModel());
                Button.addActionListener(new CellAction());
                ((CellModel) Button.getModel()).setID((x * 3) + (y - 1));
                ((CellModel) Button.getModel()).setGame(this);

                Constraints.gridx = x;
                Constraints.gridy = y;
                ButtonPanel.add(Button, Constraints);
                Cells[(x * 3) + (y - 1)] = Button;
            }
        }

        getContentPane().add(ButtonPanel);


        pack();
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        AskPlayer();
    }

    private void AskPlayer() {
        int Reply = JOptionPane.showConfirmDialog(null, "Play an AI?", "", JOptionPane.YES_NO_OPTION);
        if (Reply == JOptionPane.YES_OPTION) {
            PlayingAI = true;
        }
        else {
            PlayingAI = false;
        }
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
                    if (CurrentPlayer == "O") {
                        OWinsText.setText(String.format("O Wins: %d", ++OWins));
                    } else {
                        XWinsText.setText(String.format("X Wins: %d", ++XWins));
                    }
                    for (Component Button : ButtonPanel.getComponents()) {
                        if (Button instanceof JButton) {
                            ((CellModel) ((JButton) Button).getModel()).reset();
                            ((JButton) Button).setText("");
                        }
                    }
                } else {
                    if (PlayingAI && State == GAME_STATE.X_PLAYING){
                        Cells[GameBoard.ChooseAI()].doClick();
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
