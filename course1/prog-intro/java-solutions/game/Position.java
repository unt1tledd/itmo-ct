package game;

public interface Position {
    boolean isValid(Move move);
    int getBoardHeight();
    int getBoardWidth();
}
