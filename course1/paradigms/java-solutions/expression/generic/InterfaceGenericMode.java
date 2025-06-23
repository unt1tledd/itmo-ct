package expression.generic;

public interface InterfaceGenericMode<T> {
    T add(T a, T b);
    T subtract(T a, T b);
    T multiply(T a, T b);
    T divide(T a, T b);
    T fromInt(int value);
    T negate(T a);
    T parseType(String num);
    T area(T a, T b);
    T perimeter(T a, T b);
    default void checkZeroDivision(T b) {
        if (b.equals(fromInt(0))) {
            throw new ArithmeticException("division by zero");
        }
    }
}
