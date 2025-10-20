package parser;

import java.util.List;

public class ForLoop implements AST {
    public AST initialization;
    public AST condition;
    public AST update;
    public List<AST> block;
    public ForLoop(AST initialization, AST condition, AST update, List<AST> block) {
        this.initialization = initialization;
        this.condition = condition;
        this.update = update;
        this.block = block;
    }
}