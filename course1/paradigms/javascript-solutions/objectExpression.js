function Operator(symbol, op, ...args) {
    this.symbol = symbol;
    this.op = op;
    this.operands = args;
}
Operator.prototype.evaluate = function(...args) { return this.op(...this.operands.map((a) => a.evaluate(...args))) };
Operator.prototype.toString = function() { return `${this.operands.map((a) => a.toString()).join(" ")} ${this.symbol}`};
Operator.prototype.prefix = function() { return `(${this.symbol} ${this.operands.map((a) => a.prefix()).join(" ")})` };

function create(symbol, op) {
    return function (...operands) {
        return new Operator(symbol, op, ...operands);
    }
}

const Add = create("+", (a, b) => a + b)
const Subtract = create("-", (a, b) => a - b);
const Multiply = create("*", (a, b) => a * b);
const Divide = create("/", (a, b) => {
    if (b === 0) {
        return NaN;
    }
    return a / b;
})

const Negate = create("negate", (a) => -a)

function Const(num) {
    return {
        num: num,
        evaluate: function() { return this.num },
        toString: function() { return `${this.num}` },
        prefix: function() { return `${this.num}` }
    };
}

function Variable(name) {
    return {
        name: name,
        evaluate: function(...args) {
            switch (this.name) {
                case "x": return args[0];
                case "y": return args[1];
                case "z": return args[2];
                default: throw new Error("Неизвестная переменная: " + this.name);
            }
        },
        toString: function() { return this.name },
        prefix: function() { return this.name }
    };
}

const Clamp = create("clamp", (a, b, c) => Math.max(b, Math.min(a, c)));

const SumCb = create("sumCb", (...args) => {
    const values = args.map((a) => Math.pow(a, 3));

    if (values.length === 0) {
        return 0;
    }
    return values.reduce((a, b) => a + b);
});

const operations = {
    "+": (a, b) => new Add(a, b),
    "-": (a, b) => new Subtract(a, b),
    "*": (a, b) => new Multiply(a, b),
    "/": (a, b) => new Divide(a, b),
    "clamp": (a, b, c) => new Clamp(a, b, c),
    "negate": (a) => new Negate(a),
    "sumCb": (...args) => new SumCb(...args),
}

const arity = {
    "+": 2,
    "-": 2,
    "*": 2,
    "/": 2,
    "clamp": 3,
    "negate": 1,
    "sumCb": 0,
};

const vars = ['x', 'y', 'z'];

function parse(expression) {
    const tokens = expression.trim().split(/\s+/);
    const stack = [];

    for (let token of tokens) {
        if (/^-?\d+$/.test(token)) {
            stack.push(new Const(parseInt(token)));
        } else if (vars.includes(token)) {
            stack.push(new Variable(token));
        } else if (operations[token]) {
            const args = stack.splice(-arity[token], arity[token]);

            if (args.length < arity[token]) {
                throw new Error(`Error: not enough arguments for ${token}`);
            }

            stack.push(operations[token](...args));
        }
    }

    if (stack.length !== 1) {
        throw new Error("Invalid expression");
    }

    return stack.pop();
}



function parsePrefix(expression) {
    let pos = 0;
    const n = expression.length;

    if (expression.length === 0) {
        throw new Error("Length expr = 0")
    }

    function skipWhitespace() {
        while (pos < n && /\s/.test(expression[pos])) {
            pos++;
        }
    }

    function expect(ch) {
        if (expression[pos] !== ch) {
            throw new Error(`Expected '${ch}' at ${pos}`);
        }
        pos++;
    }

    function parseToken() {
        skipWhitespace();
        let token = "";
        while (pos < n && !/\s/.test(expression[pos]) && expression[pos] !== '(' && expression[pos] !== ')') {
            token += expression[pos++];
        }
        return token;
    }

    function parseExpression() {
        skipWhitespace();
        if (expression[pos] === '(') {
            pos++;
            const operator = parseToken();
            if (!operator) {
                throw new Error("Expected operator after '('");
            }

            const args = [];
            skipWhitespace();
            while (expression[pos] !== ')') {
                args.push(parseExpression());
                skipWhitespace();
            }

            expect(')');

            if (operations[operator]) {

                if (args.length < arity[operator] || (args.length > arity[operator] && operator !== "sumCb")) {
                    throw new Error(`Error: not enough arguments for ${operator}`);
                }

                return operations[operator](...args);
            } else {
                throw new Error(`Unknown operator: ${operator}`);
            }
        } else {
            const token = parseToken();
            if (/^-?\d+$/.test(token)) {
                return new Const(parseInt(token));
            } else if (vars.includes(token)) {
                return new Variable(token);
            } else {
                throw new Error(`Unexpected token: ${token}`);
            }
        }
    }

    const result = parseExpression();
    skipWhitespace();
    if (pos < n) {
        throw new Error(`Unexpected char at end: '${expression.slice(pos)}'`);
    }
    return result;
}