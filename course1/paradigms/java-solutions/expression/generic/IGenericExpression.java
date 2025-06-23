package expression.generic;

@FunctionalInterface
public interface IGenericExpression<T> {
    T evaluate(T x, T y, T z, InterfaceGenericMode<T> mode);
}
