package expression.exceptions;

import expression.AbstractBinaryOperation;
import expression.IExpression;

public class CheckedArea extends AbstractBinaryOperation {
    public CheckedArea(IExpression a, IExpression b) {
        super(a, b);
    }

    @Override
    protected String getSign() {
        return " area ";
    }

    @Override
    protected int doOperation(int a, int b) {
        if (a < 0 || b < 0) {
            throw new ArithmeticException("negative values");
        }

        long result = (long) a * b;

        if (result > Integer.MAX_VALUE * 2L) {
            throw new ArithmeticException("overflow in " + a + " * " + b);
        }

        return (int) (result / 2);
    }

}
