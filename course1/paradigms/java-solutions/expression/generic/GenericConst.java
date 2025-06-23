package expression.generic;

public class GenericConst<T> implements IGenericExpression<T> {
    private final T num;

    public GenericConst(T num) {
        this.num = num;
    }

    @Override
    public T evaluate(T x, T y, T z, InterfaceGenericMode<T> mode) {
        return num;
    }
}
