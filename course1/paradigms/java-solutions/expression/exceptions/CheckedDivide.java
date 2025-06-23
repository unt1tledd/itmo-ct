package expression.exceptions;
import expression.AbstractBinaryOperation;
import expression.IExpression;

public class CheckedDivide extends AbstractBinaryOperation {
    public CheckedDivide(IExpression a, IExpression b) {
        super(a, b);
    }

    @Override
    protected String getSign() {
        return " / ";
    }

    @Override
    protected int doOperation(int a, int b) {
        if (b == 0) {
            throw new Overflow( "division by zero", a + " / " + b);
        }
        if (a == Integer.MIN_VALUE && b == -1) {
            throw new Overflow("overflow",  a + " / " + b);
        }
        return a / b;
    }
}
