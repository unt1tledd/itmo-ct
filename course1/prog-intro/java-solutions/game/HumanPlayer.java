package game;

import java.io.PrintStream;
import java.util.Scanner;
import static java.lang.Character.isDigit;

public class HumanPlayer implements Player {
    private final PrintStream out;
    private final Scanner in;

    public HumanPlayer(final PrintStream out, final Scanner in) {
        this.out = out;
        this.in = in;
    }

    public HumanPlayer() {
        this(System.out, new Scanner(System.in));
    }

    private boolean isValidFormat(String input) {
        if (input.isEmpty()) {
            return false;
        }

        if (input.charAt(0) == '+' || input.charAt(0) == '-') {
            input = input.substring(1);
        }

        for (char ch : input.toCharArray()) {
            if (!isDigit(ch)) {
                return false;
            }
        }
        return true;
    }

    private Integer readValidInput() {
        while (true) {
            String input = in.next();
            if (isValidFormat(input)) {
                return Integer.parseInt(input);
            } else {
                out.println("Неверный формат данных. Пожалуйста, введите число.");
            }
        }
    }

    @Override
    public Move move(final Position position, final Cell cell) {
        while (true) {
            out.println(position);
            out.println(cell + "'s move");
            out.println("Enter row and column");
            int x = readValidInput();
            int y = readValidInput();
            final Move move = new Move(x, y, cell);
            if (position.isValid(move)) {
                return move;
            }
            out.println("Move " + move + " is invalid");
            out.println("Выход за допустимые значения доски или ход на занятую клетку");
        }
    }
}
