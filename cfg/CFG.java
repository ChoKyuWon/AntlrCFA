package cfg;

import cfg.BasicBlock;
import cfg.FunctionEntry;
import java.io.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.Double;
import java.lang.Integer;
import java.util.*;

public class CFG {
  public ArrayList<FunctionEntry> entry;
  public FunctionEntry current_function;
  public BasicBlock current_block;
  public Integer blockNum;
  public CFG() {
    entry = new ArrayList<FunctionEntry>();
    // current_block = new BasicBlock;
    // current_function = new FunctionEntry;
    blockNum = 0;
  }
  public ArrayList<FunctionEntry> getEntry() { return entry; }
  public void printResult() {
    for (FunctionEntry fn : entry()) {
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
}