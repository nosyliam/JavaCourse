package com.liam;

import java.util.Collections;
import java.util.Scanner;
import javafx.util.Pair;

public class TicTacToe {
    private static final Board GameBoard = new Board();
    private static final Scanner Input = new Scanner(System.in);

    private static final int CELL_SPACING = 5;

    private static GAME_STATE State = GAME_STATE.O_PLAYING;

    enum GAME_STATE {
        O_PLAYING, X_PLAYING, GAME_FINISHED
    }

    @SuppressWarnings("Duplicates")
    public static void main(String[] args) {
        System.out.println("Would you like to play against an AI? (y/n): ");
        boolean PlayingAI = true;
        do {
            String Result = Input.nextLine();
            if (Result.startsWith("y")) {
                PlayingAI = true;
                break;
            } else if (Result.startsWith("n")) {
                PlayingAI = false;
                break;
            }
        } while (true);

        do {
            Pair<Integer, Integer> Choice;
            switch (State) {
                case O_PLAYING:
                    Choice = GetInput("O");
                    GameBoard.SetCell(Choice.getKey().intValue(), Choice.getValue().intValue(), Board.CELL_STATE.O);
                    PrintBoard();
                    if (GameBoard.CheckWin(Choice.getKey().intValue() - 1, Choice.getValue().intValue() - 1, Board.CELL_STATE.O)) {
                        System.out.println("You won! Play again?");
                        State = GAME_STATE.GAME_FINISHED;
                    }
                    break;
                case X_PLAYING:
                    if (PlayingAI) {
                        if (GameBoard.ChooseAI()) {
                            System.out.println("The AI beat you. Play again? (y/n)");
                            State = GAME_STATE.GAME_FINISHED;
                        };
                    } else {
                        Choice = GetInput("X");
                        GameBoard.SetCell(Choice.getKey().intValue(), Choice.getValue().intValue(), Board.CELL_STATE.X);
                        if (GameBoard.CheckWin(Choice.getKey().intValue() - 1, Choice.getValue().intValue() - 1, Board.CELL_STATE.X)) {
                            System.out.println("X won! Play again? (y/n)");
                            State = GAME_STATE.GAME_FINISHED;
                        }
                    }
                    PrintBoard();
                    break;
                case GAME_FINISHED:
                    boolean PlayAgain;
                    do {
                        String Result = Input.nextLine();
                        if (Result.startsWith("y")) {
                            PlayAgain = true;
                            break;
                        } else if (Result.startsWith("n")) {
                            PlayAgain = false;
                            break;
                        }
                    } while (true);
                    if (PlayAgain) {
                        GameBoard.ClearBoard();
                        State = GAME_STATE.X_PLAYING;
                    } else {
                        System.exit(0);
                    }
                    break;
            }
            if (State == GAME_STATE.GAME_FINISHED) continue;
            State = (State == GAME_STATE.O_PLAYING) ? GAME_STATE.X_PLAYING : GAME_STATE.O_PLAYING;
        } while (true);
    }

    @SuppressWarnings("Duplicates")
    private static Pair<Integer, Integer> GetInput(String PlayerPrefix) {
        System.out.println(String.format("%s's turn! What row would you like to fill? [1-3] ", PlayerPrefix));
        int Row = 0, Column = 0;
        while (Row == 0) {
            int Choice;
            try {
                Choice = Integer.parseInt(Input.nextLine());
            } catch (NumberFormatException e) {
                continue;
            }
            if ((1 <= Choice && Choice <= 3) || GameBoard.IsRowFilled(Choice)) {
                Row = Choice;
            } else {
                System.out.println("That number won't work, try again: ");
            }
        }
        System.out.println("What column? ");
        while (Column == 0) {
            int Choice;
            try {
                Choice = Integer.parseInt(Input.nextLine());
            } catch (NumberFormatException e) {
                continue;
            }
            if (1 <= Choice && Choice <= 3 && !GameBoard.IsCellTaken(Row, Choice)) {
                Column = Choice;
            } else {
                System.out.println("That number won't work, try again: ");
            }
        }
        return new Pair<Integer, Integer>(Row, Column);
    }

    private static void PrintBoard() {
        String Space = String.join("", Collections.nCopies(CELL_SPACING, " "));

        StringBuilder Output = new StringBuilder();
        Output.append(String.format(" |%s|%s|%s| \n", Space, Space, Space));
        Output.append(String.join("", Collections.nCopies((CELL_SPACING * 3) + 6, "-")) + "\n");
        for (int x = 1; x <= 3; x++) {
            Output.append(String.format(" |  %s  |  %s  |  %s  | \n", GameBoard.GetCell(x, 1), GameBoard.GetCell(x, 2), GameBoard.GetCell(x, 3)));
            Output.append(String.join("", Collections.nCopies((CELL_SPACING * 3) + 6, "-")) + "\n");
        }
        Output.append(String.format(" |%s|%s|%s| \n\n", Space, Space, Space));
        System.out.print(Output.toString());
    }
}
