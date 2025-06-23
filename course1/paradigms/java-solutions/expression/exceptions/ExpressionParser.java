package expression.exceptions;

import expression.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class ExpressionParser implements TripleParser {
    private String input;
    private int pos = 0;

    @Override
    public IExpression parse(String expression) {
        if (expression == null || expression.isBlank()) {
            throw new IllegalArgumentException("Входная строка не может быть пустой");
        }
        this.pos = 0;
        this.input = expression;
        checkParenthesisBalance();

        IExpression result = parseGeom();

        skipWhitespace();
        if (pos < input.length()) {
            throw new IllegalArgumentException("Неожиданный символ: " + input.charAt(pos) + " на позиции " + pos);
        }

        return result;
    }

    private void skipWhitespace() {
        while (pos < input.length() && Character.isWhitespace(input.charAt(pos))) {
            pos++;
        }
    }

    Map<String, Integer> operators = new HashMap<>(Map.ofEntries(
            Map.entry("area", 4),
            Map.entry("perimeter", 9)
    ));

    private IExpression parseGeom() {
        skipWhitespace();
        IExpression result = parseExpression();

        while (pos < input.length()) {
            skipWhitespace();
            boolean foundOperator = false;
            for (Map.Entry<String, Integer> entry : operators.entrySet()) {
                String op = entry.getKey();
                int length = entry.getValue();
                if (pos + length <= input.length() && input.startsWith(op, pos)) {

                    if ((pos > 0 && Character.isDigit(input.charAt(pos - 1))) || (Character.isDigit(input.charAt(pos + length)))) {
                        throw new IllegalArgumentException("Неизвестный оператор: " + input.substring(pos - 1, pos + length + 1) + " начинается на  позиции: " + pos);
                    }

                    pos += length;
                    IExpression right = parseExpression();
                    switch (op) {
                        case "area" -> result = new CheckedArea(result, right);
                        case "perimeter" -> result = new CheckedPerimeter(result, right);
                    }
                    foundOperator = true;
                }
            }
            if (foundOperator) {
                continue;
            }

            char ch = input.charAt(pos);
            if (ch != '◣' && ch != '▯') {
                break;
            }

            pos++;
            skipWhitespace();
            if (pos >= input.length()) {
                throw new IllegalArgumentException("Ожидался операнд после оператора " + ch + " на позиции: " + pos);
            }

            IExpression right = parseExpression();
            switch (ch) {
                case '◣' -> result = new CheckedArea(result, right);
                case '▯' -> result = new CheckedPerimeter(result, right);
            }
        }
        return result;
    }

    private IExpression parseExpression() {
        skipWhitespace();
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
            skipWhitespace();
            if (pos >= input.length()) {
                throw new IllegalArgumentException("Ожидался операнд после оператора " + ch + " на позиции: " + pos);
            }

            IExpression right = parseTerm();
            switch (ch) {
                case '+' ->  result = new CheckedAdd(result, right);
                case '-' ->  result = new CheckedSubtract(result, right);
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
            skipWhitespace();
            if (pos >= input.length()) {
                throw new IllegalArgumentException("Ожидался операнд после оператора " + ch + " на позиции: " + pos);
            }

            IExpression right = parseOperand();
            switch (ch) {
                case '*' ->  result = new CheckedMultiply(result, right);
                case '/' ->  result = new CheckedDivide(result, right);
            }
        }
        return result;
    }

    private IExpression parseOperand() {
        skipWhitespace();
        if (pos >= input.length()) {
            throw new IllegalArgumentException("Ожидался операнд, но конец строки" + " на позиции: " + pos);
        }

        char ch = input.charAt(pos);
        if (ch == '-') {
            pos++;
            skipWhitespace();
            if (pos >= input.length()) {
                throw new IllegalArgumentException("Ожидался операнд после минуса" + " на позиции: " + pos);
            }

            if (Character.isDigit(input.charAt(pos))) {
                return parseNumber(true);
            }
            return new CheckedNegate(parseOperand());
        } else if (ch == '(' || ch == '[' || ch == '{') {
            pos++;
            IExpression result = parseGeom();
            skipWhitespace();
            pos++;
            return result;
        } else if (Character.isDigit(ch)) {
            return parseNumber(false);
        } else if (Character.isLetter(ch)) {
            return parseVariable();
        }
        throw new IllegalArgumentException("Неожиданный символ: " + ch + " на позиции: " + pos);
    }

    private IExpression parseNumber(boolean hasMinus) {
        StringBuilder num = new StringBuilder();
        if (hasMinus) {
            num.append('-');
        }

        while (pos < input.length() && Character.isDigit(input.charAt(pos))) {
            num.append(input.charAt(pos));
            pos++;
        }
        skipWhitespace();

        try {
            return new Const(Integer.parseInt(num.toString()));
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Число выходит за пределы 32-битного диапазона: " + num + " на позиции: " + pos);
        }
    }

    private IExpression parseVariable() {
        StringBuilder var = new StringBuilder();
        while (pos < input.length() && Character.isLetter(input.charAt(pos))) {
            var.append(input.charAt(pos));
            pos++;
        }
        String varName = var.toString();
        if (!varName.equals("x") && !varName.equals("y") && !varName.equals("z")) {
            throw new IllegalArgumentException("Недопустимое имя переменной: " + varName + " на позиции: " + pos);
        }
        return new Variable(varName);
    }

    private void checkParenthesisBalance() {
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
