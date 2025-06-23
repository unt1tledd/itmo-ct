const variable = function(name) {
    return function(x, y, z, t) {
        switch (name) {
            case "x": return x;
            case "y": return y;
            case "z": return z;
            case "t": return t;
            default: throw new Error("Неизвестная переменная: " + name);
        }
    };
};

const binary = (op) => (a, b) => (x, y, z, t) => op(a(x, y, z, t), b(x, y, z, t));
const unary = (op) => (a) => (x, y, z, t) => op(a(x, y, z, t));

const cnst = (num) => () => num;
const tau = cnst(2 * Math.PI);
const phi = cnst((1 + Math.sqrt(5)) / 2);

const add = binary((a, b) => a + b);
const subtract = binary((a, b) => a - b);
const multiply = binary((a, b) => a * b);
const divide = binary((a, b) => a / b);

const negate = unary((a) => -a);
const sinh = unary((a) => Math.sinh(a));
const cosh = unary((a) => Math.cosh(a));