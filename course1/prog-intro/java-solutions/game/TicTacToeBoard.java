package game;

import java.util.Arrays;
import java.util.Map;


public class TicTacToeBoard implements Board, Position {
    private static final Map<Cell, Character> SYMBOLS = Map.of(
            Cell.X, 'X',
            Cell.O, 'O',
            Cell.E, '.'
    );

    private final Cell[][] cells;
    private final int m, n, k;
    private int emptyCnt;
    private Cell turn;

    public TicTacToeBoard(int n, int m, int k) {
        this.m = m;
        this.n = n;
        this.k = k;
        this.cells = new Cell[n][m];
        for (Cell[] row : cells) {
            Arrays.fill(row, Cell.E);
        }
        emptyCnt = n * m;
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
            if (row >= n || col >= m || row < 0 || col < 0 || cells[row][col] != turn) {
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
            System.out.println("Ход на занятую клетку");
            return Result.LOSE;
        }

        emptyCnt--;
        cells[move.getRow()][move.getCol()] = move.getValue();
        boolean turnRow = checkResult(move , 1, 0);
        boolean turnCol = checkResult(move, 0, 1);
        boolean turnMainDiag = checkResult(move, -1, 1);
        boolean turnDiag = checkResult(move, 1, 1);

        if (turnRow || turnCol || turnDiag || turnMainDiag) {
            return Result.WIN;
        }
        if (emptyCnt == 0) {
            return Result.DRAW;
        }
        turn = (turn == Cell.X) ? Cell.O : Cell.X;
        return Result.UNKNOWN;
    }

    @Override
    public int getBoardHeight() {
        return n;
    }

    @Override
    public int getBoardWidth() {
        return m;
    }

    @Override
    public boolean isValid(final Move move) {
        return 0 <= move.getRow() && move.getRow() < n
                && 0 <= move.getCol() && move.getCol() < m
                && cells[move.getRow()][move.getCol()] == Cell.E
                && turn == getCell();
    }

    protected Cell getCellAt(int row, int col) {
        return cells[row][col];
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Board:\n");
        sb.append("+" + "-".repeat(m));

        for (int r = 0; r < n; r++) {
            sb.append("\n");
            sb.append('|');

            for (int c = 0; c < m; c++) {
                sb.append(SYMBOLS.get(cells[r][c]));
            }
            sb.append('|');
        }

        sb.append("\n");
        return sb.toString();
    }
}
