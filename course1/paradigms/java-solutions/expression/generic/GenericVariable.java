package expression.generic;

public class GenericVariable<T> implements IGenericExpression<T> {
    private char name;

    public GenericVariable(char name) {
        this.name = name;
    }

    @Override
    public T evaluate(T x, T y, T z, InterfaceGenericMode<T> mode) {
        return switch (name) {
            case 'x' -> x;
            case 'y' -> y;
            case 'z' -> z;
            default -> throw new IllegalArgumentException("Неизвестная переменная: " + name);
        };
    }
}
