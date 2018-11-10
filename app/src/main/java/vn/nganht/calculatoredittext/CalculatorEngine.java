package vn.nganht.calculatoredittext;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * 1. While there are still tokens to be read in,
 *    1.1 Get the next token.
 *    1.2 If the token is:
 *        1.2.1 A number: push it onto the value stack.
 *        1.2.2 A variable: get its value, and push onto the value stack.
 *        1.2.3 A left parenthesis: push it onto the operator stack.
 *        1.2.4 A right parenthesis:
 *          1 While the thing on top of the operator stack is not a
 *            left parenthesis,
 *              1 Pop the operator from the operator stack.
 *              2 Pop the value stack twice, getting two operands.
 *              3 Apply the operator to the operands, in the correct order.
 *              4 Push the result onto the value stack.
 *          2 Pop the left parenthesis from the operator stack, and discard it.
 *        1.2.5 An operator (call it thisOp):
 *          1 While the operator stack is not empty, and the top thing on the
 *            operator stack has the same or greater precedence as thisOp,
 *            1 Pop the operator from the operator stack.
 *            2 Pop the value stack twice, getting two operands.
 *            3 Apply the operator to the operands, in the correct order.
 *            4 Push the result onto the value stack.
 *          2 Push thisOp onto the operator stack.
 * 2. While the operator stack is not empty,
 *     1 Pop the operator from the operator stack.
 *     2 Pop the value stack twice, getting two operands.
 *     3 Apply the operator to the operands, in the correct order.
 *     4 Push the result onto the value stack.
 * 3. At this point the operator stack should be empty, and the value
 *    stack should have only one value in it, which is the final result.
 */
public class CalculatorEngine {
    public static float evaluate(String expression) throws CalculatorException {
        char[] tokens = expression
                .replaceAll(Constant.DOT, "")
                .replaceAll(Constant.COMMA, "")
                .toCharArray();

        // Stack for numbers: 'values'
        Stack<Float> values = new Stack<>();

        // Stack for Operators: 'ops'
        Stack<Character> ops = new Stack<>();
        
        try {

            for (int i = 0; i < tokens.length; i++) {
                // Current token is a whitespace, skip it
                if (tokens[i] == ' ') {
                    continue;
                }

                // Current token is a number, push it to stack for numbers
                if (tokens[i] >= '0' && tokens[i] <= '9') {
                    StringBuilder sb = new StringBuilder();
                    // There may be more than one digits in number
                    while (i < tokens.length && tokens[i] >= '0' && tokens[i] <= '9') {
                        sb.append(tokens[i++]);
                    }
                    values.push(Float.parseFloat(sb.toString()));
                }

                // Current token is an opening brace, push it to 'ops'
                else if (tokens[i] == Constant.PARENTHESIS_OPEN) {
                    ops.push(tokens[i]);
                }

                // Closing brace encountered, solve entire brace
                else if (tokens[i] == Constant.PARENTHESIS_CLOSE) {
                    while (ops.peek() != Constant.PARENTHESIS_OPEN)
                        values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                    ops.pop();
                }

                // Current token is an operator.
                else if (tokens[i] == Constant.OPERATOR_ADD || tokens[i] == Constant.OPERATOR_SUBTRACT ||
                        tokens[i] == Constant.OPERATOR_MULTIPLY || tokens[i] == Constant.OPERATOR_DIV) {
                    // While top of 'ops' has same or greater precedence to current
                    // token, which is an operator. Apply operator on top of 'ops'
                    // to top two elements in values stack
                    while (!ops.empty() && hasPrecedence(tokens[i], ops.peek())) {
                        values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                    }

                    // Push current token to 'ops'.
                    ops.push(tokens[i]);
                }
            }

            // Entire expression has been parsed at this point, apply remaining
            // ops to remaining values
            while (!ops.empty()) {
                values.push(applyOp(ops.pop(), values.pop(), values.pop()));
            }

            // Top of 'values' contains result, return it
            return values.pop();
        } catch (EmptyStackException e) {
            throw new CalculatorException("Could not evaluate for this input");
        }
    }

    // Returns true if 'op2' has higher or same precedence as 'op1',
    // otherwise returns false.
    public static boolean hasPrecedence(char op1, char op2) {
        if (op2 == Constant.PARENTHESIS_OPEN || op2 == Constant.PARENTHESIS_CLOSE) {
            return false;
        }
        return (op1 != Constant.OPERATOR_MULTIPLY && op1 != Constant.OPERATOR_DIV) || (op2 != Constant.OPERATOR_ADD && op2 != Constant.OPERATOR_SUBTRACT);
    }

    // A utility method to apply an operator 'op' on operands 'a'
    // and 'b'. Return the result.
    public static float applyOp(char op, float b, float a) throws CalculatorException {
        if (Constant.OPERATOR_DIV == op && b == 0) {
            throw new CalculatorException("Cannot divide by zero");
        }
        if (Constant.OPERATOR_MULTIPLY == op && Integer.MAX_VALUE / b < a) {
            throw new CalculatorException("Could not multiply for too large number");
        }
        switch (op) {
            case Constant.OPERATOR_ADD:
                return a + b;
            case Constant.OPERATOR_SUBTRACT:
                return a - b;
            case Constant.OPERATOR_MULTIPLY:
                return a * b;
            case Constant.OPERATOR_DIV:
                return a / b;
            default:
                return 0;
        }
    }
}
