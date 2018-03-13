package com.liam;

import java.util.*;

public class InsertionSort {
    static String doSort(ArrayList<Integer> numbers) {
        for (int n = 1; n < numbers.size(); n++) {
            int cursor = n;
            while (cursor > 0 && numbers.get(cursor - 1) > numbers.get(cursor)) {
                Collections.swap(numbers, cursor, cursor - 1);
                cursor--;
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
