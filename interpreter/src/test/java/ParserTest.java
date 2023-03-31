import lexer.Lexer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import parser.Parser;
import parser.AST;

public class ParserTest {



    @Test
    public void testSimpleDeclarationAST() throws Exception {
        Parser parser = new Parser(Lexer.tokenize("let x:number;"));
        AST ast = parser.parse();
        Assertions.assertEquals(1, ast.getAst().size());

    }
}
