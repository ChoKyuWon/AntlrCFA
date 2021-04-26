PROG := SimpleC
TARGET := CFA
INPUT_C := example.c
BASEFILE := ${PROG}BaseListener.java ${PROG}Listener.java ${PROG}BaseVisitor.java ${PROG}Visitor.java ${PROG}Lexer.java ${PROG}Parser.java

ANTLR := java -jar /usr/local/lib/antlr-4.8-complete.jar
GRUN := java org.antlr.v4.gui.TestRig
CLASSPATH := /usr/local/lib/antlr-4.8-complete.jar:.:/usr/local/lib/antlr-4.8-complete.jar

all:antlr javac
antlr:
	@${ANTLR} ${PROG}.g4 -visitor
javac:
	@javac -classpath ${CLASSPATH} ${PROG}${TARGET}.java

grun:
	${GRUN} ${PROG} program -tree ${INPUT_C}

cfa:
	@javac ${PROG}${TARGET}.java
	@java ${PROG}${TARGET} ${INPUT_C} > cfg.out

clean:
	rm ${BASEFILE};rm *.class;rm *.tokens;rm *.interp