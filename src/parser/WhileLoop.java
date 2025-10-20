package parser;

import java.util.List;

public class WhileLoop implements AST {
    public AST condition;
    public List<AST> block;
    public WhileLoop(AST condition, List<AST> block) {
        this.condition = condition;
        this.block = block;
    }
}