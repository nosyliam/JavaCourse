package com.liam;

import java.util.ArrayList;
import java.util.Collections;

public class QuickSort {
    static int partition(ArrayList<Integer> numbers, int lo, int hi) {
        int i = lo - 1;
        int pivot = numbers.get(hi);

        for (int j = lo; j <= hi - 1; j++) {
            if (numbers.get(j) <= pivot) {
                i++;
                Collections.swap(numbers, i, j);
            }
        }
        Collections.swap(numbers, i + 1, hi);
        return i + 1;
    }

    static void sort(ArrayList<Integer> numbers, int lo, int hi) {
        if (lo < hi) {
            int p = partition(numbers, lo, hi);
            sort(numbers, lo, p - 1);
            sort(numbers, p + 1, hi);
        }
    }

    static String doSort(ArrayList<Integer> numbers) {
        sort(numbers, 0, numbers.size() - 1);

        StringBuilder output = new StringBuilder();
        for (Integer number : numbers) {
            output.append(number);
            output.append(",");
        }
        return output.toString();
    }
}
