package expression.exceptions;

import expression.IExpression;

public class CheckedNegate implements IExpression{
    private final IExpression operand;

    public CheckedNegate(IExpression operand) {
        this.operand = operand;
    }

    @Override
    public String toString() {
        return "-(" + operand.toString() + ")";
    }

    @Override
    public int evaluate(int x) {
        return -(operand).evaluate(x, 0, 0);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        int value = operand.evaluate(x, y, z);
        if (value == Integer.MIN_VALUE) {
            throw new Overflow("overflow: " + " (-) ", String.valueOf(value));
        }
        return -value;
    }
}
