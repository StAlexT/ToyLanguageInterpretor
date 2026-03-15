# Toy Language Interpretor

![Language](https://img.shields.io/badge/language-Java-007396?style=flat-square)
![University Project](https://img.shields.io/badge/type-University%20Project-blue?style=flat-square)
## Description

Toy Language Interpreter is a Java-based interpreter implementation. It executes programs consisting of statements and expressions, managing program execution through multiple concurrent program states with features like semaphores, locks, and cyclic barriers. This project demonstrates fundamental interpreter design patterns and concurrent programming concepts.

## Simple overview of the base functionalities:

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

## Features

- **Statement Types**: Assignment, print, conditional, loops, and procedure calls
- **Expression Evaluation**: Arithmetic, logical, relational, and heap reading expressions
- **Concurrency Control**: Semaphores (count-based and toy semaphores), locks, and cyclic barriers
- **Memory Management**: Heap with reference types and garbage collection support
- **File I/O**: Read and write file operations with file table management
- **Procedure System**: Procedure definitions, calls, and return statements
- **Advanced Control Flow**: Switch statements, repeat-until loops, sleep statements, and fork execution
- **GUI and CLI Interfaces**: Both text-based menu and graphical user interface for program execution
- **Multi-Program Execution**: Support for running multiple programs with isolated program states

### Supported Statement Types

- `VariableDeclarationStatement` - Declare variables
- `AssignmentStatement` - Assign values
- `PrintStatement` - Output values
- `IfStatement` - Conditional execution
- `WhileStatement` / `ForStatement` / `RepeatUntilStatement` - Loops
- `NewStatement` - Heap allocation
- `WritingHeapStatement` - Heap modification
- `ForkStatement` - Concurrent execution
- `OpenReadFileStatement` / `ReadFileStatement` / `CloseReadFileStatement` - File operations
- Procedure statements - `ProcNameStatement`, `CallProcedureStatement`, `ReturnStatement`
- Synchronization - `CreateSemaphore`, `Acquire`, `Release`, `NewLock`, `LockStatement`, `UnlockStatement`, `NewBarrier`, `Await`

## Prerequisites

- Java Development Kit (JDK) 8 or higher

## Running the Interpreter

The interpreter provides two interfaces for execution:

**GUI Mode** - Interactive graphical interface with predefined examples

**Text Menu Mode** - Command-line interface with predefined examples

## Project Structure

```
model/
├── controller/          # Program execution control
├── exception/
├── expression/          # Expression evaluation (arithmetic, logical, relational)
├── repository/          # Repository
├── state/               # All the elements to create a Program State
│   ├── files/           # File table 
│   └── [Execution stack, symbol table, heap, semaphores, locks, barriers]
|   └── ProgramState     # Program state is buld based on the interfaces of execution stack, symbol table, etc.
├── statement/           # Statement implementations
│   ├── CountSemaphoreStatements/
│   ├── CyclicBarrierStatements/
│   ├── LockStatements/
│   ├── Procedures/
│   └── .../             # Other Statements
├── type/                # Type (Int, Bool, String, Ref)
├── value/               # Value representations
└── view/
    ├── Interpreter.java     # Text-based interface
    ├── InterpretorGUI.java  # Graphical interface
    ├── TextMenu.java        
    └── Command.java         # Command implementation
```
---
