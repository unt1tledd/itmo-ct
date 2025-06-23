package expression.exceptions;

import expression.AbstractBinaryOperation;
import expression.IExpression;

public class CheckedPerimeter extends AbstractBinaryOperation {
    public CheckedPerimeter(IExpression a, IExpression b) {
        super(a, b);
    }

    @Override
    protected String getSign() {
        return " perimeter ";
    }

    @Override
    protected int doOperation(int a, int b) {
        if (a < 0 || b < 0) {
            throw new ArithmeticException("negative values");
        }

        if (a > 0 && b > Integer.MAX_VALUE - a) {
            throw new ArithmeticException("overflow in " +  a + " + " + b);
        }

        long result = (long) (a + b) * 2;

        if (result > Integer.MAX_VALUE ) {
            throw new ArithmeticException("overflow in " + a + " + " + b);
        }

        return (a + b) * 2;
    }
}
