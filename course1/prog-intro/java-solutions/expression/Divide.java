package expression;

public class Divide extends AbstractBinaryOperation{
    public Divide(IExpression a, IExpression b) {
        super(a, b);
    }

    @Override
    protected String getSign() {
        return " / ";
    }

    @Override
    protected int doOperation(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Нельзя делить на ноль");
        }
        return a / b;
    }

    @Override
    protected boolean isAssociative() {
        return false;
    }

    @Override
    protected int getPriority() {
        return 4;
    }
}
