package com.liam;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.FileNotFoundException;

public class Sort {
    public static ArrayList<Integer> parseInput(String input) throws IllegalArgumentException {
        ArrayList<Integer> numbers = new ArrayList<Integer> ();
        for (String number : input.split(",")) {
            try {
                numbers.add(Integer.parseInt(number));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException();
            }
        }
        return numbers;
    }

    public static void askInput() {
        Scanner in = new Scanner(System.in);
        System.out.print("Input file name > ");
        ArrayList<Integer> input;
        do {
            try {
                String raw = new String(Files.readAllBytes(Paths.get(in.nextLine())));
                input = parseInput(raw);
                break;
            } catch (IOException e) {
                System.out.print("Unable to open file. Try again > ");
            } catch (IllegalArgumentException e) {
                System.out.print("Invalid input file. Try again > ");
            }
        } while (true);

        System.out.print("Output file name > ");
        String outfile = in.nextLine();

        System.out.print("Available algorithms: \n" +
                "1 - Bubble sort \n" +
                "2 - Selection sort \n" +
                "3 - Insertion sort \n" +
                "4 - Quicksort \n" +
                "Which algorithm? > ");
        int algorithm = 0;
        do {
            try {
                algorithm = Integer.parseInt(in.nextLine());
                assert 1 <= algorithm && algorithm <= 4;
                break;
            } catch (Exception e) {
                System.out.print("Invalid selection. Try again > ");
            }
        } while (true);

        long startTime = System.currentTimeMillis();
        switch (algorithm) {
            case 1:
                writeOutput(outfile, BubbleSort.doSort(input), startTime);
                break;
            case 2:
                writeOutput(outfile, SelectionSort.doSort(input), startTime);
                break;
            case 3:
                writeOutput(outfile, InsertionSort.doSort(input), startTime);
                break;
            case 4:
                writeOutput(outfile, QuickSort.doSort(input), startTime);
                break;

        }
    }

    public static void writeOutput(String outfile, String data, long startTime) {
        System.out.println(String.format("Sort finished in %ds", (int) Math.floor((System.currentTimeMillis() - startTime) / 1000)));
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(outfile));
            writer.write(data);
            writer.close();
        } catch (IOException e) {
            System.out.println("Unable to write to output file.");
        }
    }

    public static void main(String[] args) {
        askInput();
    }
}
