import java.io.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.Double;
import java.lang.Integer;
import java.util.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import cfg.CFG;
import cfg.BasicBlock;
import cfg.FunctionEntry;

class CFAVisitor extends SimpleCBaseVisitor<Integer> {
  CFG g;

  public ArrayList<FunctionEntry> getEntry() { return g.entry; }
  public CFG getCFG() { return g; }

  @Override
  public Integer visitProgram(SimpleCParser.ProgramContext ctx) {
    g = new CFG();
    return visitChildren(ctx);
  }
  @Override
  public Integer visitFunction(SimpleCParser.FunctionContext ctx) {
    String args = "";
    SimpleCParser.ParamListContext _args = ctx.paramList();
    args += _args.type(0).getText();
    args += " ";
    args += _args.identifier(0).getText();
    for (int i = 1;; i++) {
      if (_args.identifier(i) == null) {
        break;
      }
      args += ",";
      args += _args.type(i).getText();
      args += " ";
      args += _args.identifier(i).getText();
    }
    g.current_function = new FunctionEntry(ctx.ID().getText(),
                                         ctx.type().getText(), args);
    g.entry.add(g.current_function);
    g.current_block = new BasicBlock(0, "B0");
    BasicBlock entryBlock = new BasicBlock(-2, "fn_entry");
    g.blockNum = 0;
    g.current_block.predecessors.add(entryBlock);
    g.current_function.blocks.add(g.current_block);
    return visitChildren(ctx);
  }
  @Override
  public Integer visitIfStmt(SimpleCParser.IfStmtContext ctx) {
    BasicBlock prevBlock = g.current_block;
    BasicBlock elseblock = null;
    g.current_block.addStmt("if(" + ctx.expr().getText() + ")");
    g.blockNum += 1;
    BasicBlock ifblock =
        new BasicBlock(g.blockNum, "B" + String.valueOf(g.blockNum));
    g.current_block.successors.add(ifblock);
    ifblock.predecessors.add(g.current_block);
    g.current_function.blocks.add(ifblock);
    g.current_block = ifblock;
    if (ctx.ELSE() == null) {
      visitStmt(ctx.stmt(0));
    } else {
      visitStmt(ctx.stmt(0));
      g.blockNum += 1;
      elseblock = new BasicBlock(g.blockNum, "B" + String.valueOf(g.blockNum));
      prevBlock.successors.add(elseblock);
      elseblock.predecessors.add(prevBlock);
      g.current_function.blocks.add(elseblock);
      g.current_block = elseblock;
      visitStmt(ctx.stmt(1));
    }
    g.blockNum += 1;
    BasicBlock newblock =
        new BasicBlock(g.blockNum, "B" + String.valueOf(g.blockNum));
    ifblock.successors.add(newblock);
    newblock.predecessors.add(ifblock);
    if (elseblock != null) {
      elseblock.successors.add(newblock);
      newblock.predecessors.add(elseblock);
    }
    g.current_function.addBlock(newblock);
    g.current_block = newblock;
    return 0;
  }

  @Override
  public Integer visitWhileStmt(SimpleCParser.WhileStmtContext ctx) {
    BasicBlock prevBlock = g.current_block;
    g.current_block.addStmt("while(" + ctx.expr().getText() + ")");
    g.blockNum += 1;
    BasicBlock loopblock =
        new BasicBlock(g.blockNum, "B" + String.valueOf(g.blockNum));
    g.current_block.successors.add(loopblock);
    loopblock.predecessors.add(g.current_block);
    loopblock.successors.add(g.current_block);
    g.current_function.blocks.add(loopblock);
    g.blockNum += 1;
    BasicBlock newblock =
        new BasicBlock(g.blockNum, "B" + String.valueOf(g.blockNum));
    newblock.predecessors.add(g.current_block);
    g.current_block.successors.add(newblock);
    g.current_function.addBlock(newblock);
    g.current_block = loopblock;
    visitStmt(ctx.stmt());
    g.current_block = newblock;
    return 0;
  }
  @Override
  public Integer visitForStmt(SimpleCParser.ForStmtContext ctx) {
    BasicBlock prevBlock = g.current_block;
    g.current_block.addStmt("for(" + ctx.assign(0).getText() + ";" +
                          ctx.expr().getText() + ";" + ctx.assign(1).getText() +
                          ")");
    g.blockNum += 1;
    BasicBlock loopblock =
        new BasicBlock(g.blockNum, "B" + String.valueOf(g.blockNum));
    g.current_block.successors.add(loopblock);
    loopblock.predecessors.add(g.current_block);
    loopblock.successors.add(g.current_block);
    g.current_function.blocks.add(loopblock);
    g.blockNum += 1;
    BasicBlock newblock =
        new BasicBlock(g.blockNum, "B" + String.valueOf(g.blockNum));
    newblock.predecessors.add(g.current_block);
    g.current_block.successors.add(newblock);
    g.current_function.addBlock(newblock);
    g.current_block = loopblock;
    visitStmt(ctx.stmt());
    g.current_block = newblock;
    return 0;
  }
  @Override
  public Integer visitDeclaration(SimpleCParser.DeclarationContext ctx) {
    String args = "";
    args += ctx.type().getText();
    args += " ";
    args += ctx.identList().identifier(0).getText();
    for (int i = 1;; i++) {
      if (ctx.identList().identifier(i) == null) {
        break;
      }
      args += ", ";
      args += ctx.identList().identifier(i).getText();
    }
    g.current_block.addStmt(args);
    return visitChildren(ctx);
  }

  @Override
  public Integer visitStmt(SimpleCParser.StmtContext ctx) {
    if (ctx.assignStmt() != null) {
      g.current_block.addStmt(ctx.assignStmt().getText());
    } else if (ctx.callStmt() != null) {
      g.current_block.addStmt(ctx.callStmt().getText());
    } else if (ctx.retStmt() != null) {
      g.current_block.addStmt(ctx.retStmt().getText());
      BasicBlock exitBlock = g.current_function.getExit();
      if (exitBlock == null) {
        exitBlock = new BasicBlock(-1, "fn_exit");
        exitBlock.predecessors.add(g.current_block);
        g.current_block.successors.add(exitBlock);
        g.current_function.addBlock(exitBlock);
      } else {
        exitBlock.predecessors.add(g.current_block);
        g.current_block.successors.add(exitBlock);
      }
    }
    return visitChildren(ctx);
  }
}

public class SimpleCCFA {
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

    for (FunctionEntry fn : g.getEntry()) {
      System.out.println(
          "fn_entry{"
          + "\n    fn_name:" + fn.fn_name + "\n    ret_type:" + fn.ret_type +
          "\n    args:" + fn.args + "\n}\nPredecessors: -\nSuccessors: B0");
      for (BasicBlock b : fn.getBlocks()) {
        System.out.println("\n" + b.blockName + "\n{");
        for (String stmt : b.stmtList) {
          System.out.println("    " + stmt);
        }
        System.out.println("}");
        if (b.predecessors.size() == 0) {
          System.out.println("Predecessors: -");
        } else {
          System.out.print("Predecessors:");
          for (BasicBlock pre : b.predecessors) {
            System.out.print(pre.blockName + " ");
          }
          System.out.println("");
        }
        if (b.successors.size() == 0) {
          System.out.println("Successors: -");
        } else {
          System.out.print("Successors:");
          for (BasicBlock pre : b.successors) {
            System.out.print(pre.blockName + " ");
          }
          System.out.println("");
        }
      }
    }
    // walk from the root of parse tree
    // walker.walk(listener, parser.prog());
  }
}