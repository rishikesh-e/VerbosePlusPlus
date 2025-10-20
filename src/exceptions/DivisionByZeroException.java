package exceptions;

public class DivisionByZeroException extends VerboseException {
    public DivisionByZeroException(int line, int column) {
        super("Division by zero", line, column);
    }
}