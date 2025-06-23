package expression.generic;

public class GenericDivide<T> extends AGenericBinaryOper<T> {

    GenericDivide(IGenericExpression<T> a, IGenericExpression<T> b) {
        super(a, b);
    }

    @Override
    protected T doOperation(T a, T b, InterfaceGenericMode<T> mode) {
        return mode.divide(a, b);
    }
}