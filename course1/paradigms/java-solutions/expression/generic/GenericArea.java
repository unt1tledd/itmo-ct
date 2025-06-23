package expression.generic;

public class GenericArea<T> extends AGenericBinaryOper<T>{
    GenericArea(IGenericExpression<T> a, IGenericExpression<T> b) {
        super(a, b);
    }

    @Override
    protected T doOperation(T a, T b, InterfaceGenericMode<T> mode) {
        return mode.area(a, b);
    }

}
