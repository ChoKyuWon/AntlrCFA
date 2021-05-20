package cfg;

import java.io.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.Double;
import java.lang.Integer;
import java.util.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class FunctionEntry {
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

/*
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
    */