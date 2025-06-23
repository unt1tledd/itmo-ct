package game;

import java.util.Random;

public class RandomPlayer implements Player {
    private final Random random;

    public RandomPlayer(final Random random) {
        this.random = random;
    }

    public RandomPlayer() {
        this(new Random());
    }

    @Override
    public Move move(final Position position, final Cell cell) {
        int n = position.getBoardHeight();
        int m = position.getBoardWidth();
        int r = random.nextInt(n);
        int c = random.nextInt(m);
        final Move move = new Move(r, c, cell);
        return move;
    }
}
