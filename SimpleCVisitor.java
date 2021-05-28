// Generated from SimpleC.g4 by ANTLR 4.8
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link SimpleCParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface SimpleCVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link SimpleCParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(SimpleCParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleCParser#declList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclList(SimpleCParser.DeclListContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleCParser#funcList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncList(SimpleCParser.FuncListContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleCParser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(SimpleCParser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleCParser#identList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentList(SimpleCParser.IdentListContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleCParser#function}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction(SimpleCParser.FunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleCParser#paramList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParamList(SimpleCParser.ParamListContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleCParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(SimpleCParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleCParser#compoundStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompoundStmt(SimpleCParser.CompoundStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleCParser#stmList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmList(SimpleCParser.StmListContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleCParser#stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmt(SimpleCParser.StmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleCParser#assignStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignStmt(SimpleCParser.AssignStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleCParser#assign}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssign(SimpleCParser.AssignContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleCParser#callStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallStmt(SimpleCParser.CallStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleCParser#call}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCall(SimpleCParser.CallContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleCParser#retStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRetStmt(SimpleCParser.RetStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleCParser#whileStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStmt(SimpleCParser.WhileStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleCParser#forStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStmt(SimpleCParser.ForStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleCParser#ifStmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStmt(SimpleCParser.IfStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleCParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(SimpleCParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleCParser#argList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgList(SimpleCParser.ArgListContext ctx);
	/**
	 * Visit a parse tree produced by {@link SimpleCParser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifier(SimpleCParser.IdentifierContext ctx);
}