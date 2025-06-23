import java.util.Scanner;

public class JusttheLastDigit {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        int[][] m = new int[n][n];
        for (int i = 0; i < n; i++) {
            String line = scanner.nextLine();
            for (int j = 0; j < n; j++) {
                m[i][j] = line.charAt(j) - '0';
            }
        }

        int[][] puti = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (m[i][j] > 0) {
                    puti[i][j] = 1;
                    for (int k = j + 1; k < n; k++) {
                        m[i][k] = (m[i][k] - m[j][k] + 10) % 10;
                    }
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(puti[i][j]);
            }
            System.out.println();
        }
    }
}
