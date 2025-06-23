package expression;

//x^2−2x+1
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) {
        try (Scanner scanner = new Scanner(System.in)) {
            int x = scanner.nextInt();
            int valid_result = x*x - 2*x + 1;
            int result = new Add(new Subtract(new Multiply(new Variable("x"), new Variable("x")), new Multiply(new Const(2), new Variable("x"))), new Const(1)).evaluate(x);
            System.out.println(valid_result == result);
        } catch (InputMismatchException e) {
            System.err.println("Ошибка ввода: " + e.getMessage());
        }
    }
}
