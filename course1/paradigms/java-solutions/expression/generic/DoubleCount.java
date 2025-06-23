package expression.generic;

public class DoubleCount implements InterfaceGenericMode<Double> {
    @Override
    public Double add(Double a, Double b) {
        return a + b;
    }

    @Override
    public Double subtract(Double a, Double b) {
        return a - b;
    }

    @Override
    public Double multiply(Double a, Double b) {
        return a * b;
    }

    @Override
    public Double divide(Double a, Double b) {
        return a / b;
    }

    @Override
    public Double fromInt(int value) {
        return (double) value;
    }

    @Override
    public Double parseType(String str) {
        return Double.valueOf(str);
    }

    @Override
    public Double area(Double a, Double b) {
        return (0.5 * a * b);
    }

    @Override
    public Double perimeter(Double a, Double b) {
        return (a + b) * 2;
    }

    @Override
    public Double negate(Double num) {
        return -num;
    }
}

