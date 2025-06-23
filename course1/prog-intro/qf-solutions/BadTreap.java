import java.util.InputMismatchException;
import java.util.Scanner;

public class BadTreap {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();
            final int start = -710 * 25000;
            final int step = 710;

            for (int i = 0; i < n; i++) {
                System.out.println(start + step * i);
            }
        } catch (InputMismatchException e) {
            System.err.println("Ошибка считывания: " + e.getMessage());
        }
    }
}