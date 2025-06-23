package expression.parser;

import expression.*;

public class ExpressionParser implements TripleParser {
    private String input;
    private int pos = 0;

    @Override
    public IExpression parse(String expression) {
        if (expression == null) {
            throw new IllegalArgumentException("Входная строка не может быть пустая");
        }
        this.pos = 0;
        this.input = expression;
        return parseExpression();
    }

    private void skipWhitespace() {
        while (pos < input.length() && Character.isWhitespace(input.charAt(pos))) {
            pos++;
            if (pos >= input.length()) {
                break;
            }
        }
    }

    private void isValidPos(int pos) {
        if (pos >= input.length()) {
            throw new IndexOutOfBoundsException("Выход за пределы строки: pos = " + pos + ", длина = " + input.length() + " предыдущий символ: " + input.charAt(pos - 1));
        }
    }

    private IExpression parseExpression() {
        skipWhitespace();
        isValidPos(pos);
        IExpression result = parseTerm();
        while (pos < input.length()) {
            skipWhitespace();
            if (pos >= input.length()) {
                break;
            }
            char ch = input.charAt(pos);
            if (ch != '+' && ch != '-') {
                break;
            }
            pos++;
            IExpression right = parseTerm();
            switch (ch) {
                case '+' -> result = new Add(result, right);
                case '-' -> result = new Subtract(result, right);
            }
        }
        return result;
    }

    private IExpression parseTerm() {
        skipWhitespace();
        IExpression result = parseOperand();
        while (pos < input.length()) {
            skipWhitespace();
            if (pos >= input.length()) {
                break;
            }
            char ch = input.charAt(pos);
            if (ch != '*' && ch != '/') {
                break;
            }
            pos++;
            IExpression right = parseOperand();
            switch (ch) {
                case '*' -> result = new Multiply(result, right);
                case '/' -> result = new Divide(result, right);
            }
        }
        return result;
    }

    private IExpression parseOperand() {
        skipWhitespace();
        char ch = input.charAt(pos);
        if (ch == '-') {
            pos++;
            if (pos >= input.length()) {
                throw new IllegalArgumentException("Ожидался операнд после минуса, но конец строки.");
            }

            if (Character.isDigit(input.charAt(pos))) {
                return parseNumber(true);
            }

            return new Minus(parseOperand());
        } else if (ch == '(') {
            pos++;
            IExpression result = parseExpression();
            skipWhitespace();
            if (pos >= input.length()) {
                return result;
            }
            if (input.charAt(pos) != ')') {
                throw new IllegalArgumentException("Пропущена закрывающая скобка " + input.charAt(pos));
            }
            pos++;
            return result;
        } else if (ch == '∛') {
            pos++;
            IExpression result = parseOperand();
            skipWhitespace();
            return new Cbrt(result);
        } else {
            if (Character.isDigit(ch)) {
                return parseNumber(false);
            } else if (Character.isLetter(ch)) {
                return parseVariable();
            }
            throw new IllegalArgumentException("Неожиданный символ: " + ch);
        }
    }

    private IExpression parseNumber(boolean hasMinus) {
        StringBuilder num = new StringBuilder();

        if (hasMinus) {
            num.append('-');
        }

        while (pos < input.length() && Character.isDigit(input.charAt(pos))) {
            if (pos >= input.length()) {
                break;
            }
            num.append(input.charAt(pos));
            pos++;
        }
        try {
            return new Const(Integer.parseInt(num.toString()));
        } catch (NumberFormatException e) {
            System.err.println("Число не 32-битного знакового целочисленного типа: " + num);
            return null;
        }
    }

    private IExpression parseVariable() {
        StringBuilder var = new StringBuilder();
        while (pos < input.length() && Character.isLetter(input.charAt(pos))) {
            var.append(input.charAt(pos));
            pos++;
        }
        if (input.charAt(pos - 1) == 'x' || input.charAt(pos - 1) == 'y' || input.charAt(pos - 1) == 'z') {
            return new Variable(var.toString());
        } else {
            throw new IllegalArgumentException("Некорректный идентификатор: " + var + ". Допустимые: x, y, z");
        }
    }
}