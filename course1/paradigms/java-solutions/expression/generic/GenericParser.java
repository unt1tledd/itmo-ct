package expression.generic;

import java.util.Stack;

public class GenericParser<T> implements IGenericParser<T> {
    private String input;
    private int pos = 0;

    @Override
    public IGenericExpression<T> parse(String expression, InterfaceGenericMode<T> mode) {
        if (expression == null || expression.isBlank()) {
            throw new IllegalArgumentException("Входная строка не может быть пустой");
        }
        this.pos = 0;
        this.input = expression;
        checkParenthesesBalance();

        IGenericExpression<T> result = parseGeom(mode);

        skipWhitespace();

        return result;
    }

    private void skipWhitespace() {
        while (pos < input.length() && Character.isWhitespace(input.charAt(pos))) {
            pos++;
        }

    }

    private IGenericExpression<T> parseGeom(InterfaceGenericMode<T> mode) {
        skipWhitespace();
        IGenericExpression<T> result = parseExpression(mode);

        while (pos < input.length()) {
            skipWhitespace();
            if (pos + 4 <= input.length() && input.startsWith("area", pos)) {

                pos += 4;
                IGenericExpression<T> right = parseExpression(mode);
                return new GenericArea<>(result, right);
            }

            if (pos + 9 <= input.length() && input.startsWith("perimeter", pos)) {

                if ((pos > 0 && Character.isDigit(input.charAt(pos - 1))) || (Character.isDigit(input.charAt(pos + 9)))) {
                    throw new IllegalArgumentException("Неизвестный оператор: " + input.substring(pos - 1, pos + 9));
                }

                pos += 9;
                IGenericExpression<T> right = parseExpression(mode);
                return new GenericPerimeter<>(result, right);
            }

            char ch = input.charAt(pos);

            if (ch != '◣' && ch != '▯') {
                break;
            }

            pos++;
            skipWhitespace();
            if (pos >= input.length()) {
                throw new IllegalArgumentException("Ожидался операнд после оператора " + ch);
            }

            IGenericExpression<T> right = parseTerm(mode);
            switch (ch) {
                case '◣' -> result = new GenericArea<>(result, right);
                case '▯' -> result = new GenericPerimeter<>(result, right);
            }

        }
        return result;
    }

    private IGenericExpression<T> parseExpression(InterfaceGenericMode<T> mode) {
        skipWhitespace();
        IGenericExpression<T> result = parseTerm(mode);

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
            skipWhitespace();
            if (pos >= input.length()) {
                throw new IllegalArgumentException("Ожидался операнд после оператора " + ch);
            }

            IGenericExpression<T> right = parseTerm(mode);
            switch (ch) {
                case '+' -> result = new GenericAdd<>(result, right);
                case '-' -> result = new GenericSubtract<>(result, right);
            }

        }
        return result;
    }

    private IGenericExpression<T> parseTerm(InterfaceGenericMode<T> mode) {
        skipWhitespace();
        IGenericExpression<T> result = parseOperand(mode);

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
            skipWhitespace();
            if (pos >= input.length()) {
                throw new IllegalArgumentException("Ожидался операнд после оператора " + ch);
            }

            IGenericExpression<T> right = parseOperand(mode);
            switch (ch) {
                case '*' -> result = new GenericMultiply<>(result, right);
                case '/' -> result = new GenericDivide<>(result, right);
            }
        }
        return result;
    }

    private IGenericExpression<T> parseOperand(InterfaceGenericMode<T> mode) {
        skipWhitespace();
        if (pos >= input.length()) {
            throw new IllegalArgumentException("Ожидался операнд, но конец строки");
        }

        char ch = input.charAt(pos);

        if (ch == '-') {
            pos++;
            skipWhitespace();
            if (pos >= input.length()) {
                throw new IllegalArgumentException("Ожидался операнд после минуса");
            }

            if (Character.isDigit(input.charAt(pos))) {
                return parseNumber(true, mode);
            }
            return new GenericNegate<>(parseOperand(mode));
        } else if (ch == '(' || ch == '[' || ch == '{') {
            pos++;
            IGenericExpression<T> result = parseGeom(mode);
            skipWhitespace();
            pos++;
            return result;
        } else if (Character.isDigit(ch)) {
            return parseNumber(false, mode);
        } else if (Character.isLetter(ch)) {
            return parseVariable(ch);
        }
        throw new IllegalArgumentException("Неожиданный символ: " + ch);
    }


    private IGenericExpression<T> parseNumber(boolean hasMinus, InterfaceGenericMode<T> mode) {
        StringBuilder num = new StringBuilder();
        if (hasMinus) {
            num.append('-');
        }

        while (pos < input.length() && Character.isDigit(input.charAt(pos))) {
            num.append(input.charAt(pos));
            pos++;
        }
        skipWhitespace();
        return new GenericConst<>(mode.parseType(num.toString()));
    }

    private IGenericExpression<T> parseVariable(char var) {
        pos++;
        return new GenericVariable<>(var);
    }

    private void checkParenthesesBalance() {
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if (ch == '(' || ch == '[' || ch == '{') {
                stack.push(ch);
            } else if (ch == ')' || ch == ']' || ch == '}') {
                if (stack.isEmpty()) {
                    throw new IllegalArgumentException("Лишняя закрывающая скобка " + ch + " на позиции " + i);
                }
                char open = stack.pop();
                if (!((open == '(' && ch == ')') ||
                        (open == '[' && ch == ']') ||
                        (open == '{' && ch == '}'))) {
                    throw new IllegalArgumentException("Несоответствующие скобки " + open + " и " + ch + " на позиции " + i);
                }
            }
        }

        if (!stack.isEmpty()) {
            throw new IllegalArgumentException("Пропущена закрывающая скобка для " + stack.peek());
        }
    }
}