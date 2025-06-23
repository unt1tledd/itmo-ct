package expression.generic;


public class GenericNegate<T> implements IGenericExpression<T>{
    private final IGenericExpression<T> operand;

    public GenericNegate(IGenericExpression<T> operand) {
        this.operand = operand;
    }

    @Override
    public T evaluate(T x, T y, T z, InterfaceGenericMode<T> mode) {
        return mode.negate(operand.evaluate(x, y, z, mode));
    }
}
