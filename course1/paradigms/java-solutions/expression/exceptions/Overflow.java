package expression.exceptions;

public class Overflow extends ArithmeticException{
    public Overflow(String message, String args) {
        super(message + " in " + args);
    }
}
