package expression.generic;

public class GenericAdd<T> extends AGenericBinaryOper<T> {

    GenericAdd(IGenericExpression<T> a, IGenericExpression<T> b) {
        super(a, b);
    }

    @Override
    protected T doOperation(T a, T b, InterfaceGenericMode<T> mode) {
        return mode.add(a, b);
    }
}