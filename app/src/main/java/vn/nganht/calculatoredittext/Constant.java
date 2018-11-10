package vn.nganht.calculatoredittext;

public class Constant {
    public static final int MAX_LENGTH_NUMBER = 9;
    public static final String PATTERN_SPLIT_NUMBER_OPERATOR = "(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)";
    public static final String PATTERN_NUMBER = ".*\\d+.*";
    public static final String WHITE_SPACE = " ";
    public static final String DOT = "\\.";
    public static final String COMMA = "\\,";
    public static final char PARENTHESIS_OPEN = '(';
    public static final char PARENTHESIS_CLOSE = ')';
    public static final char OPERATOR_ADD = '+';
    public static final char OPERATOR_SUBTRACT = '-';
    public static final char OPERATOR_MULTIPLY = '*';
    public static final char OPERATOR_DIV = '/';
}
