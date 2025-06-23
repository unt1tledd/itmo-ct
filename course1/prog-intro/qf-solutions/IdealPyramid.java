import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class IdealPyramid {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int n = sc.nextInt();
            sc.nextLine();
            if (n == 1) {
                String str = sc.nextLine();
                System.out.println(str);
            } else {
                int xLeft = Integer.MAX_VALUE;
                int xRight = Integer.MIN_VALUE;
                int yBottom = Integer.MAX_VALUE;
                int yTop = Integer.MIN_VALUE;

                for (int i = 0; i < n; i++) {
                    int x = sc.nextInt();
                    int y = sc.nextInt();
                    int h = sc.nextInt();

                    xLeft = Math.min(xLeft, x - h);
                    xRight = Math.max(xRight, x + h);
                    yBottom = Math.min(yBottom, y - h);
                    yTop = Math.max(yTop, y + h);
                }

                int h = (Math.max(xRight - xLeft, yTop - yBottom) + 1) / 2;
                int centerX = (xLeft + xRight) / 2;
                int centerY = (yBottom + yTop) / 2;

                System.out.print(centerX + " ");
                System.out.print(centerY + " ");
                System.out.print(h);
            }
        } catch (InputMismatchException e) {
            System.err.println("Ошибка считывания: " + e.getMessage());
        }
    }
}
