package expression.generic;

public class GenericPerimeter<T> extends AGenericBinaryOper<T>{

    GenericPerimeter(IGenericExpression<T> a, IGenericExpression<T> b) {
        super(a, b);
    }

    @Override
    protected T doOperation(T a, T b, InterfaceGenericMode<T> mode) {
        return mode.perimeter(a, b);
    }
}
