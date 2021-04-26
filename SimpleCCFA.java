import java.io.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.Double;
import java.lang.Integer;
import java.util.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

class BasicBlock {
  ArrayList<String> stmtList;
  String blockName;
  Integer blockNum;
  ArrayList<BasicBlock> predecessors;
  ArrayList<BasicBlock> successors;
  public void init() {
    predecessors = new ArrayList<BasicBlock>();
    successors = new ArrayList<BasicBlock>();
    stmtList = new ArrayList<String>();
  }
  public BasicBlock(Integer num, String name) {
    blockName = name;
    blockNum = num;
    init();
  }
  public void addStmt(String stmt) { stmtList.add(stmt); }
}

class FunctionEntry {
  ArrayList<BasicBlock> blocks;
  String fn_name;
  String ret_type;
  String args;
  public FunctionEntry(String name, String type,
                       SimpleCParser.ParamListContext _args) {
    fn_name = name;
    ret_type = type;
    args = "";
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
    blocks = new ArrayList<BasicBlock>();
  }
  public void addBlock(BasicBlock b) { blocks.add(b); }
  public ArrayList<BasicBlock> getBlocks() { return blocks; }
  public BasicBlock getExit() {
    for (BasicBlock b : blocks) {
      if (b.blockNum == -1) {
        return b;
      }
    }
    return null;
  }
}

class CFAVisitor extends SimpleCBaseVisitor<Integer> {
  ArrayList<FunctionEntry> entry;
  FunctionEntry current_function;
  BasicBlock current_block;
  Integer blockNum;

  public ArrayList<FunctionEntry> getEntry() { return entry; }

  @Override
  public Integer visitProgram(SimpleCParser.ProgramContext ctx) {
    return visitChildren(ctx);
  }
  @Override
  public Integer visitFunction(SimpleCParser.FunctionContext ctx) {
    if (entry == null) {
      entry = new ArrayList<FunctionEntry>();
    }
    current_function = new FunctionEntry(ctx.ID().getText(),
                                         ctx.type().getText(), ctx.paramList());
    entry.add(current_function);
    current_block = new BasicBlock(0, "B0");
    BasicBlock entryBlock = new BasicBlock(-2, "fn_entry");
    blockNum = 0;
    current_block.predecessors.add(entryBlock);
    current_function.blocks.add(current_block);
    return visitChildren(ctx);
  }
  @Override
  public Integer visitIfStmt(SimpleCParser.IfStmtContext ctx) {
    BasicBlock prevBlock = current_block;
    BasicBlock elseblock = null;
    current_block.addStmt("if(" + ctx.expr().getText() + ")");
    blockNum += 1;
    BasicBlock ifblock =
        new BasicBlock(blockNum, "B" + String.valueOf(blockNum));
    current_block.successors.add(ifblock);
    ifblock.predecessors.add(current_block);
    current_function.blocks.add(ifblock);
    current_block = ifblock;
    if (ctx.ELSE() == null) {
      visitStmt(ctx.stmt(0));
    } else {
      visitStmt(ctx.stmt(0));
      blockNum += 1;
      elseblock = new BasicBlock(blockNum, "B" + String.valueOf(blockNum));
      prevBlock.successors.add(elseblock);
      elseblock.predecessors.add(prevBlock);
      current_function.blocks.add(elseblock);
      current_block = elseblock;
      visitStmt(ctx.stmt(1));
    }
    blockNum += 1;
    BasicBlock newblock =
        new BasicBlock(blockNum, "B" + String.valueOf(blockNum));
    ifblock.successors.add(newblock);
    newblock.predecessors.add(ifblock);
    if (elseblock != null) {
      elseblock.successors.add(newblock);
      newblock.predecessors.add(elseblock);
    }
    current_function.addBlock(newblock);
    current_block = newblock;
    return 0;
  }

  @Override
  public Integer visitWhileStmt(SimpleCParser.WhileStmtContext ctx) {
    BasicBlock prevBlock = current_block;
    current_block.addStmt("while(" + ctx.expr().getText() + ")");
    blockNum += 1;
    BasicBlock loopblock =
        new BasicBlock(blockNum, "B" + String.valueOf(blockNum));
    current_block.successors.add(loopblock);
    loopblock.predecessors.add(current_block);
    loopblock.successors.add(current_block);
    current_function.blocks.add(loopblock);
    blockNum += 1;
    BasicBlock newblock =
        new BasicBlock(blockNum, "B" + String.valueOf(blockNum));
    newblock.predecessors.add(current_block);
    current_block.successors.add(newblock);
    current_function.addBlock(newblock);
    current_block = loopblock;
    visitStmt(ctx.stmt());
    current_block = newblock;
    return 0;
  }
  @Override
  public Integer visitForStmt(SimpleCParser.ForStmtContext ctx) {
    BasicBlock prevBlock = current_block;
    current_block.addStmt("for(" + ctx.assign(0).getText() + ";" +
                          ctx.expr().getText() + ";" + ctx.assign(1).getText() +
                          ")");
    blockNum += 1;
    BasicBlock loopblock =
        new BasicBlock(blockNum, "B" + String.valueOf(blockNum));
    current_block.successors.add(loopblock);
    loopblock.predecessors.add(current_block);
    loopblock.successors.add(current_block);
    current_function.blocks.add(loopblock);
    blockNum += 1;
    BasicBlock newblock =
        new BasicBlock(blockNum, "B" + String.valueOf(blockNum));
    newblock.predecessors.add(current_block);
    current_block.successors.add(newblock);
    current_function.addBlock(newblock);
    current_block = loopblock;
    visitStmt(ctx.stmt());
    current_block = newblock;
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
    current_block.addStmt(args);
    return visitChildren(ctx);
  }

  @Override
  public Integer visitStmt(SimpleCParser.StmtContext ctx) {
    if (ctx.assignStmt() != null) {
      current_block.addStmt(ctx.assignStmt().getText());
    } else if (ctx.callStmt() != null) {
      current_block.addStmt(ctx.callStmt().getText());
    } else if (ctx.retStmt() != null) {
      current_block.addStmt(ctx.retStmt().getText());
      BasicBlock exitBlock = current_function.getExit();
      if (exitBlock == null) {
        exitBlock = new BasicBlock(-1, "fn_exit");
        exitBlock.predecessors.add(current_block);
        current_block.successors.add(exitBlock);
        current_function.addBlock(exitBlock);
      } else {
        exitBlock.predecessors.add(current_block);
        current_block.successors.add(exitBlock);
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
      fo = new File("cfg.out");
    }
    // Get lexer
    SimpleCLexer lexer = new SimpleCLexer(new ANTLRInputStream(input));
    // Pass Tokens to parser
    SimpleCParser parser = new SimpleCParser(new CommonTokenStream(lexer));
    // visit root of tree
    ParseTree tree = parser.program();
    CFAVisitor v = new CFAVisitor();
    v.visit(tree);
    ArrayList<FunctionEntry> array = v.getEntry();

    for (FunctionEntry fn : array) {
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