import java.util.*;

public class ManagingDifficulties {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        for (int k = 0; k < t; k++) {
            int n = scanner.nextInt();
            long result = 0;
            Map<Long, Long> C = new HashMap<>();
            int[] a = new int[n];
            for (int j = 0; j < n; j++) {
                a[j] = scanner.nextInt();
            }
            for (int j = n - 1; j >= 1; j--) {
                for (int i = 0; i < j; i++) {
                    long a_k = 2 * (long) a[j] - a[i];
                    result += C.getOrDefault(a_k, 0L);
                }
                C.put((long) a[j], C.getOrDefault((long) a[j], 0L) + 1);
            }
            System.out.println(result);
        }
        scanner.close();
    }
}
