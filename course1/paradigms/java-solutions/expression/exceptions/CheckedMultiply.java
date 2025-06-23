package expression.exceptions;

import expression.AbstractBinaryOperation;
import expression.IExpression;

public class CheckedMultiply extends AbstractBinaryOperation {
    public CheckedMultiply(IExpression a, IExpression b) {
        super(a, b);
    }

    @Override
    protected String getSign() {
        return " * ";
    }

    @Override
    protected int doOperation(int a, int b) {
        if (a != 0 && b != 0 && (a > 0 && b > 0 && a > Integer.MAX_VALUE / b || a < 0 && b < 0 && a <
                Integer.MAX_VALUE / b || a > 0 && b < 0 &&
                b < Integer.MIN_VALUE / a || a < 0 && b > 0 && a < Integer.MIN_VALUE / b)) {
                throw new Overflow("overflow", a + " * " + b);
        }
        return a * b;
    }
}
