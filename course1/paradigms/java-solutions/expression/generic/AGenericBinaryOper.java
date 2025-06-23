package expression.generic;

public abstract class AGenericBinaryOper<T> implements IGenericExpression<T> {
    protected IGenericExpression<T> a;
    protected IGenericExpression<T> b;

    public AGenericBinaryOper(IGenericExpression<T> a, IGenericExpression<T> b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public T evaluate(T x, T y, T z, InterfaceGenericMode<T> mode) {
        return doOperation(a.evaluate(x, y, z, mode), b.evaluate(x, y, z, mode), mode);
    }

    protected abstract T doOperation(T a, T b, InterfaceGenericMode<T> mode);
}
