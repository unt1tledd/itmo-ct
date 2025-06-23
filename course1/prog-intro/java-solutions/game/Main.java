package game;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Введите размер доски и линии для победы: ");
            int n = sc.nextInt();
            int m = sc.nextInt();
            int k = sc.nextInt();
            String format = "Квадрат";
            final Game game = new Game(true, new RandomPlayer(), new HumanPlayer());
            int result;
            do {
                if (n == m) {
                    while (true) {
                        System.out.print("Выберите формат игры (Квадрат, Ромб): ");
                        format = sc.next();
                        if (format.toLowerCase().equals("квадрат")) {
                            result = game.play(new TicTacToeBoard(m, n, k));
                            break;
                        } else if (format.toLowerCase().equals("ромб")) {
                            result = game.play(new Rhomb(n, k));
                            break;
                        } else {
                            System.out.println("Неверно введен формат");
                        }
                    }
                } else {
                    result = game.play(new TicTacToeBoard(m, n, k));
                }
                switch (result) {
                    case 1 -> System.out.println("Win player 1!");
                    case 2 -> System.out.println("Win player 2!");
                    case 0 -> System.out.println("Friendship won!");
                    default -> {}
                }
            } while (result == -1);
        } catch (InputMismatchException e) {
            System.err.println("Ошибка ввода: " + e.getMessage());
        }
    }
}
