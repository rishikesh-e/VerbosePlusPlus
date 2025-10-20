package exceptions;

public class SyntaxErrorException extends VerboseException {
    public SyntaxErrorException(String got, int line, int column) {
        super(got , line, column);
    }
}
