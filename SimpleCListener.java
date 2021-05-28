// Generated from SimpleC.g4 by ANTLR 4.8
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SimpleCParser}.
 */
public interface SimpleCListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link SimpleCParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(SimpleCParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleCParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(SimpleCParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleCParser#declList}.
	 * @param ctx the parse tree
	 */
	void enterDeclList(SimpleCParser.DeclListContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleCParser#declList}.
	 * @param ctx the parse tree
	 */
	void exitDeclList(SimpleCParser.DeclListContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleCParser#funcList}.
	 * @param ctx the parse tree
	 */
	void enterFuncList(SimpleCParser.FuncListContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleCParser#funcList}.
	 * @param ctx the parse tree
	 */
	void exitFuncList(SimpleCParser.FuncListContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleCParser#declaration}.
	 * @param ctx the parse tree
	 */
	void enterDeclaration(SimpleCParser.DeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleCParser#declaration}.
	 * @param ctx the parse tree
	 */
	void exitDeclaration(SimpleCParser.DeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleCParser#identList}.
	 * @param ctx the parse tree
	 */
	void enterIdentList(SimpleCParser.IdentListContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleCParser#identList}.
	 * @param ctx the parse tree
	 */
	void exitIdentList(SimpleCParser.IdentListContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleCParser#function}.
	 * @param ctx the parse tree
	 */
	void enterFunction(SimpleCParser.FunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleCParser#function}.
	 * @param ctx the parse tree
	 */
	void exitFunction(SimpleCParser.FunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleCParser#paramList}.
	 * @param ctx the parse tree
	 */
	void enterParamList(SimpleCParser.ParamListContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleCParser#paramList}.
	 * @param ctx the parse tree
	 */
	void exitParamList(SimpleCParser.ParamListContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleCParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(SimpleCParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleCParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(SimpleCParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleCParser#compoundStmt}.
	 * @param ctx the parse tree
	 */
	void enterCompoundStmt(SimpleCParser.CompoundStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleCParser#compoundStmt}.
	 * @param ctx the parse tree
	 */
	void exitCompoundStmt(SimpleCParser.CompoundStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleCParser#stmList}.
	 * @param ctx the parse tree
	 */
	void enterStmList(SimpleCParser.StmListContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleCParser#stmList}.
	 * @param ctx the parse tree
	 */
	void exitStmList(SimpleCParser.StmListContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleCParser#stmt}.
	 * @param ctx the parse tree
	 */
	void enterStmt(SimpleCParser.StmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleCParser#stmt}.
	 * @param ctx the parse tree
	 */
	void exitStmt(SimpleCParser.StmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleCParser#assignStmt}.
	 * @param ctx the parse tree
	 */
	void enterAssignStmt(SimpleCParser.AssignStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleCParser#assignStmt}.
	 * @param ctx the parse tree
	 */
	void exitAssignStmt(SimpleCParser.AssignStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleCParser#assign}.
	 * @param ctx the parse tree
	 */
	void enterAssign(SimpleCParser.AssignContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleCParser#assign}.
	 * @param ctx the parse tree
	 */
	void exitAssign(SimpleCParser.AssignContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleCParser#callStmt}.
	 * @param ctx the parse tree
	 */
	void enterCallStmt(SimpleCParser.CallStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleCParser#callStmt}.
	 * @param ctx the parse tree
	 */
	void exitCallStmt(SimpleCParser.CallStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleCParser#call}.
	 * @param ctx the parse tree
	 */
	void enterCall(SimpleCParser.CallContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleCParser#call}.
	 * @param ctx the parse tree
	 */
	void exitCall(SimpleCParser.CallContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleCParser#retStmt}.
	 * @param ctx the parse tree
	 */
	void enterRetStmt(SimpleCParser.RetStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleCParser#retStmt}.
	 * @param ctx the parse tree
	 */
	void exitRetStmt(SimpleCParser.RetStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleCParser#whileStmt}.
	 * @param ctx the parse tree
	 */
	void enterWhileStmt(SimpleCParser.WhileStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleCParser#whileStmt}.
	 * @param ctx the parse tree
	 */
	void exitWhileStmt(SimpleCParser.WhileStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleCParser#forStmt}.
	 * @param ctx the parse tree
	 */
	void enterForStmt(SimpleCParser.ForStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleCParser#forStmt}.
	 * @param ctx the parse tree
	 */
	void exitForStmt(SimpleCParser.ForStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleCParser#ifStmt}.
	 * @param ctx the parse tree
	 */
	void enterIfStmt(SimpleCParser.IfStmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleCParser#ifStmt}.
	 * @param ctx the parse tree
	 */
	void exitIfStmt(SimpleCParser.IfStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleCParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(SimpleCParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleCParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(SimpleCParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleCParser#argList}.
	 * @param ctx the parse tree
	 */
	void enterArgList(SimpleCParser.ArgListContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleCParser#argList}.
	 * @param ctx the parse tree
	 */
	void exitArgList(SimpleCParser.ArgListContext ctx);
	/**
	 * Enter a parse tree produced by {@link SimpleCParser#identifier}.
	 * @param ctx the parse tree
	 */
	void enterIdentifier(SimpleCParser.IdentifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link SimpleCParser#identifier}.
	 * @param ctx the parse tree
	 */
	void exitIdentifier(SimpleCParser.IdentifierContext ctx);
}