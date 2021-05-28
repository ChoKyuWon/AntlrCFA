import java.io.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.Double;
import java.lang.Integer;
import java.util.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;



public class SimpleCDFA {
  public static void main(String[] args) throws IOException {
    String input = new String();
    File fo;
    try {
      File f = new File(args[0]);
      FileReader reader = new FileReader(f);
      BufferedReader buf = new BufferedReader(reader);
      Scanner scan = new Scanner(f);
      while (scan.hasNextLine()) {
        input += scan.nextLine();
        input += '\n';
      }
    } catch (IOException e) {
      System.out.println(e);
    }
    try {
      fo = new File(args[1]);
    } catch (Exception e) {
      fo = new File("g.out");
    }
    // Get lexer
    SimpleCLexer lexer = new SimpleCLexer(new ANTLRInputStream(input));
    // Pass Tokens to parser
    SimpleCParser parser = new SimpleCParser(new CommonTokenStream(lexer));
    // visit root of tree
    ParseTree tree = parser.program();
    CFAVisitor v = new CFAVisitor();
    v.visit(tree);

    CFG g = v.getCFG();
    // ArrayList<FunctionEntry> array = v.getEntry();

    g.liveness();
    // walk from the root of parse tree
    // walker.walk(listener, parser.prog());
  }
}