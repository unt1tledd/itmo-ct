package expression;

public class Const implements IExpression {
    private int num;

    public Const(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return Integer.toString(num);
    }

    @Override
    public int evaluate(int x) {
        return evaluate(x, 0, 0);
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return num;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Const && num == ((Const) obj).num;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(num);
    }
}