import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AccurateMovement {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            int n = scanner.nextInt();
            int res = 2 * ((int) Math.ceil((double) (n - b) / (b - a))) + 1;
            System.out.println(res);
        } catch (InputMismatchException e) {
            System.err.println("Ошибка считывания: " + e.getMessage());
        }
    }
}
