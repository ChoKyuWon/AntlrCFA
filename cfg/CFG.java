package cfg;

import java.io.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.Double;
import java.lang.Integer;
import java.util.*;

import cfg.BasicBlock;
import cfg.FunctionEntry;

public class CFG{
    public ArrayList<FunctionEntry> entry;
    public FunctionEntry current_function;
    public BasicBlock current_block;
    public Integer blockNum;
    public CFG(){
        entry = new ArrayList<FunctionEntry>();
        // current_block = new BasicBlock;
        // current_function = new FunctionEntry;
        blockNum = 0;
    }
    public ArrayList<FunctionEntry> getEntry(){
        return entry;
    }
}