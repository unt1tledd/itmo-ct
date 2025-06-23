package expression.generic;

public class GenericSubtract<T> extends AGenericBinaryOper<T> {

    GenericSubtract(IGenericExpression<T> a, IGenericExpression<T> b) {
        super(a, b);
    }

    @Override
    protected T doOperation(T a, T b, InterfaceGenericMode<T> mode) {
        return mode.subtract(a, b);
    }
}