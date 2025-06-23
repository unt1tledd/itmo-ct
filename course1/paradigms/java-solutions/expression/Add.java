package expression;

public class Add extends AbstractBinaryOperation{
    public Add(IExpression a, IExpression b) {
        super(a, b);
    }

    @Override
    protected String getSign() {
        return " + ";
    }

    @Override
    protected int doOperation(int a, int b) {
        return a + b;
    }
}
