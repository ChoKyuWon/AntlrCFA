package cfg;

import java.io.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.Double;
import java.lang.Integer;
import java.util.*;

public class BasicBlock {
  public ArrayList<String> stmtList;
  public String blockName;
  public Integer blockNum;
  public ArrayList<BasicBlock> predecessors;
  public ArrayList<BasicBlock> successors;
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