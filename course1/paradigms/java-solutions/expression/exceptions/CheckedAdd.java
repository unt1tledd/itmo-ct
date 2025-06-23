package expression.exceptions;

import expression.AbstractBinaryOperation;
import expression.IExpression;

public class CheckedAdd extends AbstractBinaryOperation {
    public CheckedAdd(IExpression a, IExpression b) {
        super(a, b);
    }

    @Override
    protected String getSign() {
        return " + ";
    }

    @Override
    protected int doOperation(int a, int b) {
        if ((a > 0 && b > Integer.MAX_VALUE - a) || (a < 0 && b < Integer.MIN_VALUE - a)) {
            throw new Overflow("overflow", a + " + " + b);
        }
        return a + b;
    }
}
