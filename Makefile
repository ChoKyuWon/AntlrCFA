PROG := SimpleC
CFA_TARGET := CFA
DFA_TARGET := DFA
INPUT_C := example.c
BASEFILE := ${PROG}BaseListener.java ${PROG}Listener.java ${PROG}BaseVisitor.java ${PROG}Visitor.java ${PROG}Lexer.java ${PROG}Parser.java

CFG += CFAVisitor.java

ANTLR := java -jar /usr/local/lib/antlr-4.8-complete.jar
GRUN := java org.antlr.v4.gui.TestRig
CLASSPATH := /usr/local/lib/antlr-4.8-complete.jar:.:/usr/local/lib/antlr-4.8-complete.jar:$(shell pwd)

all:antlr javac
antlr:
	@${ANTLR} ${PROG}.g4 -visitor
	@javac -classpath ${CLASSPATH} $(PROG)*.java

javac: cfg
	@javac -classpath ${CLASSPATH} $(PROG)${CFA_TARGET}.java

cfg:
	@javac -classpath $(CLASSPATH) $(CFG)
grun:
	${GRUN} ${PROG} program -tree ${INPUT_C}

cfa:
	@javac ${PROG}${CFA_TARGET}.java
	@java ${PROG}${CFA_TARGET} ${INPUT_C} > cfg.out

dfa:
	@javac ${PROG}${DFA_TARGET}.java
	@java ${PROG}${DFA_TARGET} ${INPUT_C} > liveness.out

clean:
	rm ${BASEFILE};rm *.class;rm *.tokens;rm *.interp;rm *.out