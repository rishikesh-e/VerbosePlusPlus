package exceptions;

public class IdentifierWithKeywordNameException extends VerboseException {
    public IdentifierWithKeywordNameException(String identifier, int line, int column) {
        super("Identifier '" + identifier + "' cannot use a reserved keyword", line, column);
    }
}
