package staticCodeAnalyser;

import com.fasterxml.jackson.databind.ObjectMapper;
import lexer.Lexer;
import parser.AST;
import parser.Parser;
import parser.expr.*;
import parser.node.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StaticCodeAnalyser implements NodeVisitor, ExpressionVisitor {

    private final String namingConvention;
    private final boolean printlnCondition;
    private final HashMap<String, Object> map = new HashMap<>();


    public StaticCodeAnalyser(String configFile) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Config config = objectMapper.readValue(new File(configFile), Config.class);

        this.namingConvention = config.getNamingConvention();
        this.printlnCondition = config.getPrintlnCondition();
    }

    private void analyze(String source){
        Parser parser = new Parser(Lexer.tokenize(source));
        AST ast = parser.parse();

        List<Node> nodes = ast.getAst();
        List<String> messages = new ArrayList<>();

        for (Node node: nodes){
            try {
                node.accept(this);
            } catch (Exception e){
                String message = e.getMessage();
                messages.add(message);
            }
        }

        for (String message :
                messages) {
            System.out.println(message);
        }
    }

    @Override
    public void visitNode(DeclarationNode node) {
        String variable = node.getVariableName();

        if (namingConvention.equals("snake case")){
            if (!variable.matches("[a-z]+(_[a-z]+)*")){
                throw new RuntimeException("Variable not written in snake case in line: ");
            }
        }else{
            if (!variable.matches("[a-z]+([A-Z][a-z]+)*")){
                throw new RuntimeException("Variable not written in camel case in line: ");
            }
        }
    }

    @Override
    public void visitNode(AssignationNode node) {

    }

    @Override
    public void visitNode(PrintNode node) {
        if (printlnCondition){
            Object printExpression = node.getExpression().accept(this);

            if (printExpression.equals(ExpressionType.BINARY) || printExpression.equals(ExpressionType.UNARY)){
                throw new RuntimeException("´println argumento not valid at line:");
            }
        }
    }


    @Override
    public Object visitExpr(BinaryExpression binaryExpression) {
        return ExpressionType.BINARY;
    }

    @Override
    public Object visitExpr(LiteralExpression literalExpression) {
        return ExpressionType.LITERAL;
    }

    @Override
    public Object visitExpr(UnaryExpression unaryExpression) {
        return ExpressionType.UNARY;
    }

    @Override
    public Object visitExpr(VariableExpression variableExpression) {
        return ExpressionType.VARIABLE;
    }
}