package expression.generic;

public class GenericMode {
    public static InterfaceGenericMode<?> getMode(String mode) {
        InterfaceGenericMode<?> result;
        switch (mode) {
            case "i" -> result = new IntCount();
            case "d" -> result = new DoubleCount();
            case "bi" -> result = new BigIntCount();
            default -> throw new IllegalArgumentException("Unknown mode: " + mode);
        }
        return result;
    }
}
