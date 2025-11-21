//package model.test;
//
//import model.controller.Controller;
//import model.exception.ToyLanguageException;
//import model.expression.ArithmeticExpression;
//import model.expression.ValueExpression;
//import model.expression.VariableExpression;
//import model.repository.Repository;
//import model.repository.RepositoryInterface;
//import model.state.ExecutionStack;
//import model.state.Output;
//import model.state.ProgramState;
//import model.state.SymbolTable;
//import model.statement.*;
//import model.type.BoolType;
//import model.type.IntType;
//import model.value.BooleanNumber;
//import model.value.IntegerValue;
//
//import java.io.IOException;
//
//public class TestInterpreter {
//    public static void maind(String[] args) {
//        try {
//            // --- TEST 1: int v; v = 2; print(v)
//            Statement ex1 = new CompoundStatement(
//                    new VariableDeclarationStatement("v", new IntType()),
//                    new CompoundStatement(
//                            new AssignmentStatement("v", new ValueExpression(new IntegerValue(2))),
//                            new PrintStatement(new VariableExpression("v"))
//                    )
//            );
//
//            runTest(ex1, "test1.txt");
//
//            // --- TEST 2: bool b; b = true; print(b)
//            Statement ex2 = new CompoundStatement(
//                    new VariableDeclarationStatement("b", new BoolType()),
//                    new CompoundStatement(
//                            new AssignmentStatement("b", new ValueExpression(new BooleanNumber(true))),
//                            new PrintStatement(new VariableExpression("b"))
//                    )
//            );
//
//            runTest(ex2, "test2.txt");
//
//            // --- TEST 3: int x; bool y; x = 10; y = false; print(x)
//            Statement ex3 = new CompoundStatement(
//                    new VariableDeclarationStatement("x", new IntType()),
//                    new CompoundStatement(
//                            new VariableDeclarationStatement("y", new BoolType()),
//                            new CompoundStatement(
//                                    new AssignmentStatement("x", new ValueExpression(new IntegerValue(10))),
//                                    new CompoundStatement(
//                                            new AssignmentStatement("y", new ValueExpression(new BooleanNumber(false))),
//                                            new PrintStatement(new VariableExpression("x"))
//                                    )
//                            )
//                    )
//            );
//
//            runTest(ex3, "test3.txt");
//
//            // Program:
//// int x;
//// bool y;
//// x = 10;
//// y = true;
//// if (y) then print(x) else print(0);
//
//            Statement ex4 = new CompoundStatement(
//                    new VariableDeclarationStatement("x", new IntType()),
//                    new CompoundStatement(
//                            new VariableDeclarationStatement("y", new BoolType()),
//                            new CompoundStatement(
//                                    new AssignmentStatement("x", new ValueExpression(new IntegerValue(10))),
//                                    new CompoundStatement(
//                                            new AssignmentStatement("y", new ValueExpression(new BooleanNumber(true))),
//                                            new IfStatement(
//                                                    new VariableExpression("y"),
//                                                    new PrintStatement(new VariableExpression("x")),
//                                                    new PrintStatement(new ValueExpression(new IntegerValue(0)))
//                                            )
//                                    )
//                            )
//                    )
//            );
//
//            runTest(ex4, "test4.txt");
//
//            Statement ex5 = new CompoundStatement(
//                    new VariableDeclarationStatement("a", new IntType()),
//                    new CompoundStatement(
//                            new AssignmentStatement("a", new ArithmeticExpression(
//                                    new ValueExpression(new IntegerValue(2)),
//                                    new ArithmeticExpression(
//                                            new ValueExpression(new IntegerValue(3)),
//                                            new ValueExpression(new IntegerValue(5)),
//                                            3 // multiply
//                                    ),
//                                    1 // plus
//                            )),
//                            new CompoundStatement(
//                                    new VariableDeclarationStatement("b", new IntType()),
//                                    new CompoundStatement(
//                                            new AssignmentStatement("b", new ArithmeticExpression(
//                                                    new ArithmeticExpression(
//                                                            new VariableExpression("a"),
//                                                            new ArithmeticExpression(
//                                                                    new ValueExpression(new IntegerValue(4)),
//                                                                    new ValueExpression(new IntegerValue(2)),
//                                                                    4 // divide
//                                                            ),
//                                                            2 // minus
//                                                    ),
//                                                    new ValueExpression(new IntegerValue(7)),
//                                                    1 // plus
//                                            )),
//                                            new PrintStatement(new VariableExpression("b"))
//                                    )
//                            )
//                    )
//            );
//
//            runTest(ex5, "test5.txt");
//
//            // -------- Example 3: bool a; a=false; int v; If a Then v=2 Else v=3; Print(v) --------
//            Statement ex6 = new CompoundStatement(
//                    new VariableDeclarationStatement("a", new BoolType()),
//                    new CompoundStatement(
//                            new AssignmentStatement("a", new ValueExpression(new BooleanNumber(false))),
//                            new CompoundStatement(
//                                    new VariableDeclarationStatement("v", new IntType()),
//                                    new CompoundStatement(
//                                            new IfStatement(
//                                                    new VariableExpression("a"),
//                                                    new AssignmentStatement("v", new ValueExpression(new IntegerValue(2))),
//                                                    new AssignmentStatement("v", new ValueExpression(new IntegerValue(3)))
//                                            ),
//                                            new PrintStatement(new VariableExpression("v"))
//                                    )
//                            )
//                    )
//            );
//            runTest(ex6, "test6.txt");
//
//        } catch (ToyLanguageException e) {
//            System.out.println("Interpreter error: " + e.getMessage());
//        }
//    }
//
//
//
//
//    private static void runTest(Statement program, String logFile) {
//        RepositoryInterface repo = new Repository(logFile);
//        ProgramState state = new ProgramState(
//                new ExecutionStack<>(),
//                new SymbolTable<>(),
//                new Output<>(),
//                program
//        );
//        repo.addProgramState(state);
//        Controller controller = new Controller(repo);
//
//        System.out.println("Running " + logFile + "...");
//        try {
//            controller.allSteps();
//            System.out.println("Final Program State:\n" + state);
//            System.out.println("Execution finished.\n");
//        } catch (ToyLanguageException | IOException e) {
//            System.out.println("Error: " + e.getMessage());
//        }
//    }
//}
