package game;

import java.util.Arrays;
import java.util.Map;

public class Rhomb implements Board, Position{

    private static final Map<Cell, Character> SYMBOLS = Map.of(
            Cell.X, 'X',
            Cell.O, 'O',
            Cell.E, '.'
    );

    private final Cell[][] cells;
    private final int n, k;
    private int emptyCnt;
    private Cell turn;

    public  Rhomb(int n, int k) {
        this.n = n;
        this.k = k;
        this.cells = new Cell[2*n - 1][2*n - 1];
        for (Cell[] row : cells) {
            Arrays.fill(row, Cell.E);
        }
        emptyCnt = n * n;
        turn = Cell.X;
    }
    @Override
    public Position getPosition() {
        return this;
    }

    @Override
    public Cell getCell() {
        return turn;
    }

    private int checkLine(final Move move, int x, int y) {
        int row = move.getRow();
        int col = move.getCol();
        int cntTurn = 0;

        for (int i = 0; i < k; i++){
            if (row >= n || col >= n || row < 0 || col < 0 || cells[row][col] != turn) {
                break;
            }
            cntTurn++;
            row += x;
            col += y;
        }
        return cntTurn;
    }

    private boolean checkResult(Move move, int dx, int dy) {
        int cnt1 = checkLine(move, dx, dy);
        int cnt2 = checkLine(move, -dx, -dy);
        return cnt1 + cnt2 - 1 >= k;
    }

    @Override
    public Result makeMove(final Move move) {
        if (!isValid(move)) {
            return Result.LOSE;
        }

        emptyCnt--;
        cells[move.getRow()][move.getCol()] = move.getValue();

        boolean turnRow = checkResult(move, 1, 0);
        boolean turnCol = checkResult(move, 0, 1);
        boolean turnMainDiag = checkResult(move, -1, 1);
        boolean turnDiag = checkResult(move, 1, 1);

        if (turnRow || turnCol || turnMainDiag || turnDiag) {
            return Result.WIN;
        }

        if (emptyCnt == 0) {
            return Result.DRAW;
        }

        turn = (turn == Cell.X) ? Cell.O : Cell.X;
        return Result.UNKNOWN;
    }


    @Override
    public boolean isValid(final Move move) {
        int row = move.getRow();
        int col = move.getCol();

        if (row >= 2 * n - 1 || col >= 2 * n - 1 || row + col < n - 1 || col - row > n - 1) {
            return false;
        }

        if (cells[row][col] != Cell.E) {
            return false;
        }

        if (move.getValue() != turn) {
            return false;
        }

        return true;
    }

    @Override
    public int getBoardHeight() {
        return n;
    }

    @Override
    public int getBoardWidth() {
        return n;
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder("Rhomb Board:\n");
        int n = getBoardHeight();

        for (int r = 0; r < n; r++) {
            sb.append(" ".repeat((n - r)*2));
            for (int c = 0; c < 2 * r + 1; c++) {
                sb.append(SYMBOLS.get(cells[r][c]));
                sb.append(" ");
            }
            sb.append("\n");
        }

        for (int r = n; r < 2 * n; r++) {
            sb.append(" ".repeat((r - n + 1) * 2 + 2));

            int startCol = n - (2 * n - 1 - r) + 1;
            int endCol = n + (2 * n - 1 - r);

            for (int c = startCol; c < endCol; c++) {
                sb.append(SYMBOLS.get(cells[r][c]));
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}