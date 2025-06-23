package expression.generic;

import java.math.BigInteger;

public class BigIntCount implements InterfaceGenericMode<BigInteger> {

    @Override
    public BigInteger add(BigInteger a, BigInteger b) {
        return a.add(b);
    }

    @Override
    public BigInteger subtract(BigInteger a, BigInteger b) {
        return a.subtract(b);
    }

    @Override
    public BigInteger multiply(BigInteger a, BigInteger b) {
        return a.multiply(b);
    }

    @Override
    public BigInteger divide(BigInteger a, BigInteger b) {
        checkZeroDivision(b);
        return a.divide(b);
    }

    @Override
    public BigInteger fromInt(int value) {
        return BigInteger.valueOf(value);
    }

    @Override
    public BigInteger parseType(String num) {
        return new BigInteger(num);
    }

    @Override
    public BigInteger negate(BigInteger num) {
        return num.negate();
    }

    @Override
    public BigInteger area(BigInteger a, BigInteger b) {
        return a.multiply(b).divide(BigInteger.TWO);
    }

    @Override
    public BigInteger perimeter(BigInteger a, BigInteger b) {
        return a.add(b).multiply(BigInteger.TWO);
    }
}