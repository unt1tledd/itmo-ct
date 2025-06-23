package expression.generic;

public class IntCount implements InterfaceGenericMode<Integer> {
    @Override
    public Integer add(Integer a, Integer b) {
        if ((a > 0 && b > Integer.MAX_VALUE - a) || (a < 0 && b < Integer.MIN_VALUE - a)) {
            throw new ArithmeticException("overflow in " +  a + " + " + b);
        }
        return a + b;
    }

    @Override
    public Integer subtract(Integer a, Integer b) {
        if ((b > 0 && a < Integer.MIN_VALUE + b) || (b < 0 && a > Integer.MAX_VALUE + b)) {
            throw new ArithmeticException("overflow in " + a + " - " + b);
        }
        return a - b;
    }

    @Override
    public Integer multiply(Integer a, Integer b) {
        if (a != 0 && b != 0 && (a > 0 && b > 0 && a > Integer.MAX_VALUE / b || a < 0 && b < 0 && a <
                Integer.MAX_VALUE / b || a > 0 && b < 0 &&
                b < Integer.MIN_VALUE / a || a < 0 && b > 0 && a < Integer.MIN_VALUE / b)) {
            throw new ArithmeticException("overflow in " + a + " * " + b);
        }
        return a * b;
    }

    @Override
    public Integer divide(Integer a, Integer b) {
        checkZeroDivision(b);
        if ((a == Integer.MIN_VALUE && b == -1) || b == 0) {
            throw new ArithmeticException("overflow in " + a + " / " + b);
        }
        return a / b;
    }

    @Override
    public Integer fromInt(int value) {
        return value;
    }

    @Override
    public Integer parseType(String str) {
        return Integer.parseInt(str);
    }

    @Override
    public Integer negate(Integer num) {
        if (num == Integer.MIN_VALUE) {
            throw new ArithmeticException("overflow in " + " (-) " + num);
        }
        return -num;
    }

    @Override
    public Integer area(Integer a, Integer b) {
        if (a < 0 || b < 0) {
            throw new ArithmeticException("negative values");
        }

        long result = (long) a * b;

        if (result > Integer.MAX_VALUE * 2L) {
            throw new ArithmeticException("overflow in " + a + " * " + b);
        }

        return (int) (result / 2);
    }

    @Override
    public Integer perimeter(Integer a, Integer b) {
        if (a < 0 || b < 0) {
            throw new ArithmeticException("negative values");
        }

        if (a > 0 && b > Integer.MAX_VALUE - a) {
            throw new ArithmeticException("overflow in " +  a + " + " + b);
        }

        long result = (long) (a + b) * 2;

        if (result > Integer.MAX_VALUE ) {
            throw new ArithmeticException("overflow in " + a + " + " + b);
        }

        return (a + b) * 2;
    }
}
