import java.util.Scanner;
import java.util.Arrays;

public class ReverseOdd {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] lines = new String[100];
        int count = 0;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (!line.isEmpty()) {
                StringBuilder reverse_line = new StringBuilder();
                Scanner scanner_for_num = new Scanner(line);
                while (scanner_for_num.hasNextInt()) {
                    int num = scanner_for_num.nextInt();
                    if (num % 2 != 0){
                        reverse_line.insert(0, num + " ");
                    }
                }
                scanner_for_num.close();

                if (reverse_line.length() > 0) {
                    reverse_line.setLength(reverse_line.length() - 1);
                }

                lines[count] = reverse_line.toString();
                count++;
            } else {
                lines[count] = "";
                count++;
            }

            if (count >= lines.length) {
                lines = Arrays.copyOf(lines, lines.length * 2);
            }
        }

        scanner.close();

        for (int i = count - 1; i >= 0; i--) {
            System.out.println(lines[i]);
        }
    }
}