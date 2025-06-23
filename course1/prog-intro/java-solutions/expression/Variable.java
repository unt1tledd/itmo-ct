package expression;

import java.util.Objects;

public class Variable implements IExpression {
    private String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public String toMiniString() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public int evaluate(int var) {
        return evaluate(var, 0, 0);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        char var = name.charAt(name.length() - 1);
        return switch (var) {
            case 'x' -> x;
            case 'y' -> y;
            case 'z' -> z;
            default -> throw new IllegalArgumentException("Неизвестная переменная: " + name);
        };
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Variable && name == ((Variable) obj).name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
