package search;

import java.util.Arrays;

public class BinarySearch {
    //P: arr.length > 0 && x in arr && forall i -> arr[i] >= arr[i + 1]
    // Q: i => (arr[i] = x && i = min({i : arr[i] <= x})) || (if x not on arr: i = min({i : arr[i] < x}))
    static int iterSearch(String[] arr, int x) {
        int l = 0;
        int r = arr.length;
        // I: 0 <= l <= r <= arr.length && x in arr[l:r]
        // if x in arr: x in [l, r)
        while (l < r) {
            // (r - l)/2 in [l/2, r/2] ∈ [l, r]
            // l + max([l/2, r/2]) = l + r/2 ∈ [l, r] ->
            //-> mid in [l, r]
            int mid = l + (r - l) / 2;

            // I && x in [l, mid] || x in [mid + 1, r)
            if (Integer.parseInt(arr[mid]) <= x) {
                // I && l <= mid <= r <= arr.length -> 0 <= r == mid < arr.length
                r = mid;
            } else {
                // I && l <= mid + 1 <= r <= arr.length -> 0 <= l < arr.length
                l = mid + 1;
            }
        }
        // l = min({i | arr[i] <= x})
        return l;
    }

    //P: arr.length != 0 && x in arr && i...n: -> arr[i] >= arr[i + 1]
    // Q: i => (arr[i] = x && i = min({i : arr[i] == x})) || (if x not in arr: i = min({i : arr[i] < x}))
    static int recurSearch(String[] arr, int x, int l, int r) {
        // I: 0 <= l <= r <= arr.length && x in arr[l:r]
        if (r <= l) {
            // l = min({i | arr[i] <= x})
            return l;
        }
        // l in [0, arr.length) && (r - l) in [l/2, r/2] c [l, r]
        // l + max([l/2, r/2]) = l + r/2 c [l, r]  ->
        //-> mid in [l, r]
        int mid = l + (r - l) / 2;
        if (Integer.parseInt(arr[mid]) <= x) {
            // x <= arr[mid] -> x ∈ [l, mid]
            // x in arr[l:mid] - > arr[l, mid].length != 0
            // I && 0 <= l <= mid <= r <= arr.length && (x in arr)
            return recurSearch(arr, x, l, mid);
        } else {
            // x > arr[mid] -> x ∈ [mid + 1, r]
            // x in arr[mid + 1:r] - > arr[mid + 1:r].length != 0
            // I && 0 <= l <= mid <= r <= arr.length && (x in arr)
            return recurSearch(arr, x, mid + 1, r);
        }
    }

    // P: len(args) > 1 && x = args[0] && (arr = args[1:]) && forall i: -> arr[i] >= arr[i + 1] && x in args[1:]
    // Q: min({i | arr[i] <= x})
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        String[] arr = Arrays.copyOfRange(args, 1, args.length);
        // I: iter = min({i | arr[i] <= x}) == recur = min({i | arr[i] <= x})
        // len(args) > 1(from P) -> args[0] - x -> len(arr) = len(args) - 1 -> len(arr) > 0
        // forall i: -> arr[i] >= arr[i + 1] && x in args[1:] (from P)
        // -> P for binarySearch func
        int result_iter = iterSearch(arr, x);
        int result_recur = recurSearch(arr, x, 0, arr.length);
        if (result_recur == result_iter) {
            // min({i | arr[i] <= x})
            System.out.println(result_iter);
        } else {
            System.out.println("error");
        }
    }
}