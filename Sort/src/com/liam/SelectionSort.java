package com.liam;

import java.util.ArrayList;
import java.util.Collections;

public class SelectionSort {
    static String doSort(ArrayList<Integer> numbers) {
        for (int n = 0; n < numbers.size() - 1; n++) {
            int min = n;
            for (int i = n + 1; i < numbers.size(); i++) {
                if (numbers.get(i) < numbers.get(min)) {
                    min = i;
                }
            }
            if (min != n) {
                Collections.swap(numbers, n, min);
            }
        }

        StringBuilder output = new StringBuilder();
        for (Integer number : numbers) {
            output.append(number);
            output.append(",");
        }
        return output.toString();
    }
}
