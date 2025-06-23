package expression;

import java.util.Objects;

public abstract class AbstractBinaryOperation implements IExpression {
    protected IExpression a, b;

    public AbstractBinaryOperation(IExpression a, IExpression b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public String toString() {
       return "(" + a.toString() + getSign() + b.toString() + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !getClass().equals(obj.getClass())) {
            return false;
        }

        AbstractBinaryOperation that = (AbstractBinaryOperation) obj;
        return Objects.equals(a, that.a) && Objects.equals(b, that.b);
    }

    @Override
    public int hashCode() {
        return Objects.hash(toString());
    }

    @Override
    public int evaluate(int x) {
        return evaluate(x, 0, 0);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return doOperation(a.evaluate(x, y, z), b.evaluate(x, y, z));
    }
    protected abstract String getSign();
    protected abstract int doOperation(int a, int b);
}
