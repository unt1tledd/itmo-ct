package expression;

public class Cbrt implements IExpression {
    IExpression num;
    public Cbrt(IExpression num) {
        this.num = num;
    }

    @Override
    public int evaluate(int x) {
        return (int) Math.cbrt(num.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return (int) Math.cbrt(num.evaluate(x, y, z));
    }

    @Override
    public String toString() {
        return "∛(" + num.toString() + ")";
    }

    @Override
    public String toMiniString() {
        return "∛(" + num.toMiniString() + ")";
    }
}
