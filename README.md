# Toy Language Interppretor - An university project in Java

## Toy Language - Simple Overview of the base functionalities:

A program (Prg) in this language consists of one single statement (Stmt).

A statement can be either:
- compound statement (CompStmt):
  - used to wrap up two statements
  - by doing new CompStmt(Stmt1, new CompStmt(...)) we can wrap up as many statemnts as our program needs
- assignment statement (AssignStmt)
- print statement (PrintStmt)
- conditional statement (IfStmt)
- etc.

Statements can contain:
- other statements
- expressions
- statemnts + expressions

The interpreter uses three main structures:
- Execution Stack (ExeStack):
  - a stack of statements to execute the currrent program
  - at the beginning: the original CompStmt program
  - after the evaluation started: the remaining part of the program that was elvaluated
- Table of Symbols (SymTable):
  - a table which keeps the variables values
  - at the beginning: empty
  - after the evaluation started: contains the variable with their assigned values
- Output (Out):
  - that keeps all the mesages printed by the toy program
  - at the beginning: empty
  - after the evaluation started: contains the values printed so far

These three main structures form the program state (PrgState).

The interpreter can execute multiple programs but for each of them use a different PrgState structures (that 
means different ExeStack, SymTable and Out structures).
