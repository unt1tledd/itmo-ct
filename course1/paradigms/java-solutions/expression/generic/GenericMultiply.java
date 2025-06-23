package expression.generic;

public class GenericMultiply<T> extends AGenericBinaryOper<T> {

    GenericMultiply(IGenericExpression<T> a, IGenericExpression<T> b) {
        super(a, b);
    }

    @Override
    protected T doOperation(T a, T b, InterfaceGenericMode<T> mode) {
        return mode.multiply(a, b);
    }
}