package expression;

public class Multiply extends AbstractBinaryOperation{
    public Multiply(IExpression a, IExpression b) {
        super(a, b);
    }

    @Override
    protected String getSign() {
        return " * ";
    }

    @Override
    protected int doOperation(int a, int b) {
        return a * b;
    }

    @Override
    protected boolean isAssociative() {
        return getClass().equals(b.getClass());
    }

    @Override
    protected int getPriority() {
        return 4;
    }
}
