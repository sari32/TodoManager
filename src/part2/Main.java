package part2;

import java.util.ArrayList;
import java.util.List;

public class Main {
    /**
     * Returns all maximal strictly increasing sequences in the array.
     */
    public static List<List<Integer>> getIncreasingSequences(int[] arr) {
        List<List<Integer>> result = new ArrayList<>();
        int n = arr.length;

        int i = 0;
        while (i < n) {
            List<Integer> seq = new ArrayList<>();
            seq.add(arr[i]);

            int j = i + 1;
            while (j < n && arr[j] > arr[j - 1]) {
                seq.add(arr[j]);
                j++;
            }

            if (seq.size() >= 2) { // keep only sequences of length >= 2
                result.add(seq);
            }

            i = j; // skip to the end of the sequence
        }

        return result;
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 1, 2};
        List<List<Integer>> sequences = getIncreasingSequences(arr);

        for (List<Integer> seq : sequences) {
            System.out.println(seq);
        }
    }

}
