package exceptions;

public class SemicolonExpectedException extends VerboseException {
    public SemicolonExpectedException(String got, int line, int column) {
        super(got , line, column);
    }
}