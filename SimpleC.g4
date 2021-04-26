grammar SimpleC;

// Parser Rule
program: (declList)? (funcList)?;

declList: (declaration)+;
funcList: (function)+;
declaration: type identList SEMICOL;
identList: identifier (COMMA identifier)*;
function: type ID LPAREN (paramList)? RPAREN compoundStmt;
paramList: type identifier (COMMA type identifier)*;
type: (INT | FLOAT);
compoundStmt: LBRACE (declList)? stmList RBRACE;
stmList: (stmt)*;
stmt:
	assignStmt
	| callStmt
	| retStmt
	| whileStmt
	| forStmt
	| ifStmt
	| compoundStmt;
assignStmt: assign SEMICOL;
assign: ID ASNOP expr;
callStmt: call SEMICOL;
call: ID LPAREN (argList)? RPAREN;
retStmt: RETURN (expr)? SEMICOL;
whileStmt: WHILE LPAREN expr RPAREN stmt;
forStmt:
	FOR LPAREN assign SEMICOL expr SEMICOL assign RPAREN stmt;
ifStmt: IF LPAREN expr RPAREN stmt (ELSE stmt)?;
expr:
	UNYOP expr
	| expr ADDOP expr
	| expr MULOP expr
	| expr EQLOP expr
	| expr RELOP expr
	| call
	| INTNUM
	| FLOATNUM
	| ID
	| LPAREN expr RPAREN;

argList: expr (COMMA expr)*;
identifier: ID | ID ASNOP INTNUM | ID ASNOP FLOATNUM;

// Lexer Rule

INT: 'int';
FLOAT: 'float';
LPAREN: '(';
RPAREN: ')';
LBRACE: '{';
RBRACE: '}';
SEMICOL: ';';
COMMA: ',';
UNYOP: '-';
WHILE: 'while';
FOR: 'for';
IF: 'if';
ELSE: 'else';
RETURN: 'return';
ADDOP: '+' | '-';
MULOP: '*' | '/';
RELOP: '<' | '>' | '<=' | '>=';
EQLOP: '==' | '!=';
ASNOP: '=';
ID: [A-Za-z_][A-Za-z0-9_]*;
INTNUM: [0-9]+;
FLOATNUM: [0-9]* '.' [0-9]+;
WS: [ \t]+ -> skip;
NEWLINE: ( '\r' '\n'? | '\n') -> skip;
BlockComment: '/*' .*? '*/' -> skip;
LineComment: '//' ~[\r\n]* -> skip;