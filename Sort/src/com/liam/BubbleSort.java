package com.liam;

import java.util.*;

public class BubbleSort {
    static String doSort(ArrayList<Integer> numbers) {
        do {
            int numSwaps = 0;
            for (int n = 0; n < numbers.size() - 1; n++) {
                if (numbers.get(n) > numbers.get(n + 1)) {
                    Collections.swap(numbers, n, n + 1);
                    numSwaps++;
                }
            }
            if (numSwaps == 0)
                break;
        } while (true);

        StringBuilder output = new StringBuilder();
        for (Integer number : numbers) {
            output.append(number);
            output.append(",");
        }
        return output.toString();
    }
}
