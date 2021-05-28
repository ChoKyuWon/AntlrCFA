import java.io.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.Double;
import java.lang.Integer;
import java.util.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

class BasicBlock {
  public ArrayList<String> input;
  public ArrayList<String> output;
  public ArrayList<String> stmtList;
  public String blockName;
  public Integer blockNum;
  public ArrayList<BasicBlock> predecessors;
  public ArrayList<BasicBlock> successors;

  public BasicBlock(Integer num, String name) {
    blockName = name;
    blockNum = num;
    predecessors = new ArrayList<BasicBlock>();
    successors = new ArrayList<BasicBlock>();
    stmtList = new ArrayList<String>();
    input = new ArrayList<String>();
    output = new ArrayList<String>(input);
  }
  public void addStmt(String stmt) {
    stmtList.add(stmt);
  }
  public void addSuccessors(BasicBlock b){
    this.successors.add(b);
    b.input.addAll(this.output);
    b.output.addAll(b.input);
  }

  public void addPredecessors(BasicBlock b){
    this.predecessors.add(b);
  }
}

class CFG {
  public ArrayList<FunctionEntry> entry;
  public FunctionEntry current_function;
  public BasicBlock current_block;
  public Integer blockNum;
  public CFG() {
    entry = new ArrayList<FunctionEntry>();
    blockNum = 0;
  }
  public ArrayList<FunctionEntry> getEntry() { return entry; }
  public void printResult() {
    for (FunctionEntry fn : entry) {
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
  }
  public void liveness(){
    for(FunctionEntry e : entry){
      for(BasicBlock b : e.blocks){
        if(b.blockNum > -1){
          if(b.input != null)
            System.out.println(b.blockName + "-INPUT:" +b.input +";");
          else
            System.out.println(b.blockName + "-INPUT: ;");
          System.out.println(b.blockName + "-OUTPUT:" + b.output + ";");
        }
      }
    }
  }
}

class FunctionEntry {
  public ArrayList<BasicBlock> blocks;
  public String fn_name;
  public String ret_type;
  public String args;
  public FunctionEntry(String name, String type,
                       String _args) {
    fn_name = name;
    ret_type = type;
    args = _args;
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

public class CFAVisitor extends SimpleCBaseVisitor<Integer> {
  CFG g;

  private BasicBlock blockEpilogue(BasicBlock prevBlock){
    g.blockNum += 1;
    BasicBlock newblock = new BasicBlock(g.blockNum, "B" + String.valueOf(g.blockNum));
    newblock.addPredecessors(prevBlock);
    prevBlock.addSuccessors(newblock);
    g.current_function.blocks.add(newblock);
    g.current_block = newblock;
    return newblock;
  }

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
    BasicBlock ifblock = null;
    BasicBlock elseblock = null;
    g.current_block.addStmt("if(" + ctx.expr().getText() + ")");
    ifblock = blockEpilogue(prevBlock);
    if (ctx.ELSE() == null) {
      visitStmt(ctx.stmt(0));
    } else {
      visitStmt(ctx.stmt(0));
      elseblock = blockEpilogue(prevBlock);
      visitStmt(ctx.stmt(1));
    }
    BasicBlock newblock = blockEpilogue(ifblock);
    if (elseblock != null) {
      elseblock.successors.add(newblock);
      newblock.predecessors.add(elseblock);
    }
    return 0;
  }

  @Override
  public Integer visitWhileStmt(SimpleCParser.WhileStmtContext ctx) {
    BasicBlock prevBlock = g.current_block;
    g.current_block.addStmt("while(" + ctx.expr().getText() + ")");
    BasicBlock loopblock = blockEpilogue(prevBlock);
    prevBlock.predecessors.add(loopblock);
    loopblock.successors.add(prevBlock);
    visitStmt(ctx.stmt());
    blockEpilogue(prevBlock);
    return 0;
  }

  @Override
  public Integer visitForStmt(SimpleCParser.ForStmtContext ctx) {
    BasicBlock prevBlock = g.current_block;
    g.current_block.addStmt("for(" + ctx.assign(0).getText() + ";" +
                          ctx.expr().getText() + ";" + ctx.assign(1).getText() +
                          ")");
    BasicBlock loopblock = blockEpilogue(prevBlock);
    prevBlock.predecessors.add(loopblock);
    loopblock.successors.add(prevBlock);
    visitStmt(ctx.stmt());
    blockEpilogue(prevBlock);
    return 0;
  }
  @Override
  public Integer visitDeclaration(SimpleCParser.DeclarationContext ctx) {
    String args = "";
    args += ctx.type().getText();
    args += " ";
    args += ctx.identList().identifier(0).getText();
    g.current_block.output.add(ctx.identList().identifier(0).ID().getText());
    for (int i = 1;; i++) {
      if (ctx.identList().identifier(i) == null) {
        break;
      }
      args += ", ";
      args += ctx.identList().identifier(i).getText();;
      g.current_block.output.add(ctx.identList().identifier(i).ID().getText());
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