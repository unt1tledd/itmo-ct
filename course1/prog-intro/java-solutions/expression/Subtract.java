package expression;

public class Subtract extends AbstractBinaryOperation{
    public Subtract(IExpression a, IExpression b) {
        super(a, b);
    }

    @Override
    protected String getSign() {
        return " - ";
    }

    @Override
    protected int doOperation(int a, int b) {
        return a - b;
    }

    @Override
    protected boolean isAssociative() {
        return false;
    }

    @Override
    protected int getPriority() {
        return 1;
    }
}
