package parser;

import java.util.List;

public class IfStatement implements AST {
    public AST condition;
    public List<AST> ifBranch;
    public List<AST> elseBranch;
    public IfStatement(AST condition, List<AST> ifBranch, List<AST> elseBranch) {
        this.condition = condition;
        this.ifBranch = ifBranch;
        this.elseBranch = elseBranch;
    }
}