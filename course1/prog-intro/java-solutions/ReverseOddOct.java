import java.io.IOException;
import java.util.Arrays;
import Sc.MyScanner;

public class ReverseOddOct {
    static final int start_buffer = 100;

    public static void main(String[] args) {
        try {
            MyScanner scanner = new MyScanner(System.in);
            long[][] numbers = new long[start_buffer][];
            int lineCount = 0;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                MyScanner lineScanner = new MyScanner(line);

                long[] lineNumbers = new long[start_buffer];
                int count = 0;

                while (lineScanner.hasNext()) {
                    String token = lineScanner.next();
                    try {
                        long num = Long.parseLong(token, 8);

                        if (num % 2 != 0) {
                            if (count >= lineNumbers.length) {
                                lineNumbers = Arrays.copyOf(lineNumbers, lineNumbers.length * 2);
                            }
                            lineNumbers[count++] = num;
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Неверный формат числа: " + token);
                    }
                }
                lineScanner.close();

                if (lineCount >= numbers.length) {
                    numbers = Arrays.copyOf(numbers, numbers.length * 2);
                }

                numbers[lineCount++] = Arrays.copyOf(lineNumbers, count);
            }
            scanner.close();

            for (int i = lineCount - 1; i >= 0; i--) {
                for (int j = numbers[i].length - 1; j >= 0; j--) {
                    System.out.print(Long.toOctalString(numbers[i][j]) + " ");
                }
                System.out.println();
            }
        } catch (IOException e) {
            System.err.println("Ошибка считывания: " + e.getMessage());
        }
    }
}
