package expression;

public class Minus implements IExpression {
    private final IExpression operand;

    public Minus(IExpression operand) {
        this.operand = operand;
    }

    @Override
    public String toString() {
        return "-(" + operand.toString() + ")";
    }

    @Override
    public int evaluate(int x) {
        return -(operand).evaluate(x);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return -operand.evaluate(x, y, z);
    }

}
