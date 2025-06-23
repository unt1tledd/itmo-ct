package expression.exceptions;

import expression.AbstractBinaryOperation;
import expression.IExpression;

public class CheckedSubtract extends AbstractBinaryOperation {
    public CheckedSubtract(IExpression a, IExpression b) {
        super(a, b);
    }

    @Override
    protected String getSign() {
        return " - ";
    }

    @Override
    protected int doOperation(int a, int b) {
        if ((b > 0 && a < Integer.MIN_VALUE + b) || (b < 0 && a > Integer.MAX_VALUE + b)) {
            throw new Overflow("overflow",  a + " - " + b);
        }
        return a - b;
    }

}
