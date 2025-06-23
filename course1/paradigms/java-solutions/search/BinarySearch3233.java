package search;

import java.util.Arrays;

public class BinarySearch3233 {
    //P: arr.length > 0 && forall i -> arr[i] >= arr[i + 1]
    // Q: i => (arr[i] = x && i = min({i : arr[i] <= x})) || (if x not in arr: i = min({i : arr[i] < x}))
    static int iterSearch(String[] arr, int x) {
        int l = 0;
        int r = arr.length;
        // I: 0 <= l <= r <= arr.length && x in arr[l:r]
        // if x in arr: x in [l, r)
        // d_0 = r - l = arr.length
        while (l < r) {
            // (r - l) >= 0 -> mid >= l
            // (r - l) / 2 <= r - l
            // l + (r - l) = r -> l + (r - l)/2 <= r
            // mid in [l, r]
            int mid = l + (r - l) / 2;

            // I && x in [l, mid] || x in [mid + 1, r)
            if (Integer.parseInt(arr[mid]) <= x) {
                // I && l <= mid <= r <= arr.length -> 0 <= r == mid < arr.length
                r = mid;
                // d' = mid - l = l + (r - l)/2 - l = (r - l) / 2
            } else {
                // I && l <= mid + 1 <= r <= arr.length -> 0 <= l < arr.length
                l = mid + 1;
                // d' = r - (mid + 1) = r - l - (r - l)/2 - 1 = (r - l) / 2 - 1
            }
            // d' <= d_0/2
            // d_k <= arr.lenght/2^k < 1
        }
        if (l < arr.length && Integer.parseInt(arr[l]) == x) {
            // l = min({i | arr[i] <= x})
            return l;
        } else {
            return -(l + 1);
        }
    }

    //P: arr.length != 0 && i...n: -> arr[i] >= arr[i + 1]
    // Q: i => (arr[i] = x && i = min({i : arr[i] == x})) || (if x not in arr: i = min({i : arr[i] < x}))
    static int recurSearch(String[] arr, int x, int l, int r) {
        // d_0 = r - l = arr.lenght

        // I: 0 <= l <= r <= arr.length && x in arr[l:r]
        if (r <= l) {
            // d' <= d_0/2
            // d_k <= arr.lenght/2^k < 1
            if (l < arr.length && Integer.parseInt(arr[l]) == x) {
                // l = min({i | arr[i] <= x})
                return l;
            } else {
                return -(l + 1);
            }
        }
        // (r - l) >= 0 -> mid >= l
        // (r - l) / 2 <= r - l
        // l + (r - l) = r -> l + (r - l)/2 <= r
        // mid in [l, r]
        int mid = l + (r - l) / 2;
        if (Integer.parseInt(arr[mid]) <= x) {
            // x <= arr[mid] -> x ∈ [l, mid]
            // x in arr[l:mid] - > arr[l, mid].length != 0
            // I && 0 <= l <= mid <= r <= arr.length && (x in arr)
            return recurSearch(arr, x, l, mid);
            // d' = mid - l = l + (r - l)/2 - l = (r - l) / 2
        } else {
            // x > arr[mid] -> x ∈ [mid + 1, r]
            // x in arr[mid + 1:r] - > arr[mid + 1:r].length != 0
            // I && 0 <= l <= mid <= r <= arr.length && (x in arr)
            return recurSearch(arr, x, mid + 1, r);
            // d' = r - (mid + 1) = r - l - (r - l)/2 - 1 = (r - l) / 2 - 1
        }
    }

    // P: len(args) > 1 && x = args[0] && (arr = args[1:]) && forall i: -> arr[i] >= arr[i + 1]
    // Q: if x in arr:  min({i | arr[i] <= x}) else: (
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