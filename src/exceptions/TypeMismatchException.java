package exceptions;

public class TypeMismatchException extends VerboseException {
    public TypeMismatchException(int line, int column) {
        super("Type mismatch", line, column);
    }
}