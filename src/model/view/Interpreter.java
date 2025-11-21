package model.view;

import model.controller.Controller;
import model.expression.*;
import model.state.files.FileTable;
import model.repository.Repository;
import model.repository.RepositoryInterface;
import model.state.*;
import model.statement.*;
import model.type.IntType;
import model.type.StringType;
import model.value.IntegerValue;
import model.type.BoolType;
import model.value.BooleanNumber;
import model.value.StringValue;

public class Interpreter {
    public static void main(String[] args) {
        // Example 1: - Logical
        // int v;
        // v=2;
        // bool a;
        // bool b;
        // a = true;
        // b = true;
        // IF(a and b)
        //      v = 3;
        // ELSE
        //      v = 2;
        // print(v);
        Statement ex1 = new CompoundStatement(
                new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(
                        new AssignmentStatement("v", new ValueExpression(new IntegerValue(2))),
                        new CompoundStatement(
                                new VariableDeclarationStatement("a", new BoolType()),
                                new CompoundStatement(
                                        new VariableDeclarationStatement("b", new BoolType()),
                                        new CompoundStatement(
                                                new AssignmentStatement("a", new ValueExpression(new BooleanNumber(true))),
                                                new CompoundStatement(
                                                        new AssignmentStatement("b", new ValueExpression(new BooleanNumber(true))),
                                                        new CompoundStatement(
                                                                new IfStatement(
                                                                        new LogicalExpression(
                                                                        new VariableExpression("a"),
                                                                        new VariableExpression("b"),
                                                                        1
                                                                    ),
                                                                    new AssignmentStatement("v", new ValueExpression(new IntegerValue(3))),
                                                                    new AssignmentStatement("v", new ValueExpression(new IntegerValue(2)))
                                                                ),
                                                                new PrintStatement(new VariableExpression("v"))
                                                        )

                                                )
                                        )
                                )
                        )
                )
        );

        ProgramState prg1 = new ProgramState(
                new ExecutionStack<>(),
                new SymbolTable<>(),
                new Output<>(),
                new FileTable<>(),
                ex1
        );
        RepositoryInterface repo1 = new Repository("log1.txt");
        repo1.addProgramState(prg1);
        Controller ctr1 = new Controller(repo1);

        // Example 2: Arithmetical
        // int a;
        // a=(2 + (3 * 5));
        // int b;
        // b=(a - ((4 / 2) + 7)));
        // print(b);
        Statement ex2 = new CompoundStatement(
                new VariableDeclarationStatement("a", new IntType()),
                new CompoundStatement(
                        new AssignmentStatement("a",
                                new ArithmeticExpression(
                                        new ValueExpression(new IntegerValue(2)),
                                        new ArithmeticExpression(
                                                new ValueExpression(new IntegerValue(3)),
                                                new ValueExpression(new IntegerValue(5)),
                                                3
                                        ),
                                        1
                                )
                        ),
                        new CompoundStatement(
                                new VariableDeclarationStatement("b", new IntType()),
                                new CompoundStatement(
                                        new AssignmentStatement("b",
                                                new ArithmeticExpression(
                                                        new VariableExpression("a"),
                                                        new ArithmeticExpression(
                                                                new ArithmeticExpression(
                                                                        new ValueExpression(new IntegerValue(4)),
                                                                        new ValueExpression(new IntegerValue(0)),
                                                                        4
                                                                ),
                                                                new ValueExpression(new IntegerValue(7)),
                                                                1
                                                        ),
                                                        2
                                                )
                                        ),
                                        new PrintStatement(new VariableExpression("b"))
                                )
                        )
                )
        );
        ProgramState prg2 = new ProgramState(
                new ExecutionStack<>(),
                new SymbolTable<>(),
                new Output<>(),
                new FileTable<>(),
                ex2
        );
        RepositoryInterface repo2 = new Repository("log2.txt");
        repo2.addProgramState(prg2);
        Controller ctr2 = new Controller(repo2);

        // Example 3:
        // bool a;
        // a=false;
        // int v;
        // IF(a) THEN
        //      (v=2);
        // ELSE
        //      (v=3);
        // print(v);
        Statement ex3 = new CompoundStatement(
                new VariableDeclarationStatement("a", new BoolType()),
                new CompoundStatement(
                        new AssignmentStatement("a", new ValueExpression(new BooleanNumber(false))),
                        new CompoundStatement(
                                new VariableDeclarationStatement("v", new IntType()),
                                new CompoundStatement(
                                        new IfStatement(
                                                new VariableExpression("a"),
                                                new AssignmentStatement("v", new ValueExpression(new IntegerValue(2))),
                                                new AssignmentStatement("v", new ValueExpression(new IntegerValue(3)))
                                        ),
                                        new PrintStatement(new VariableExpression("v"))
                                )
                        )
                )
        );

        ProgramState prg3 = new ProgramState(
                new ExecutionStack<>(),
                new SymbolTable<>(),
                new Output<>(),
                new FileTable<>(),
                ex3
        );
        RepositoryInterface repo3 = new Repository("log3.txt");
        repo3.addProgramState(prg3);
        Controller ctr3 = new Controller(repo3);
        //Example 4: (string varf;(varf=test.txt;(openRFile(varf);(int varc;(readFile(varf, varc);(print(varc);(readFile(varf, varc);(print(varc);closeRFile(varf)))))))))
        Statement ex4 = new CompoundStatement(
                new VariableDeclarationStatement("varf", new StringType()),
                new CompoundStatement(
                        new AssignmentStatement("varf", new ValueExpression(new StringValue("test.txt"))),
                        new CompoundStatement(
                                new OpenReadFileStatement(new VariableExpression("varf")),
                                new CompoundStatement(
                                        new VariableDeclarationStatement("varc", new IntType()),
                                        new CompoundStatement(
                                                new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("varc")),
                                                        new CompoundStatement(
                                                                new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                                new CompoundStatement(
                                                                        new PrintStatement(new VariableExpression("varc")),
                                                                        new CloseReadFileStatement(new VariableExpression("varf"))
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        ProgramState prg4 = new ProgramState(
                new ExecutionStack<>(),
                new SymbolTable<>(),
                new Output<>(),
                new FileTable<>(),
                ex4
        );
        RepositoryInterface repo4 = new Repository("log4.txt");
        repo4.addProgramState(prg4);
        Controller ctr4 = new Controller(repo4);
        //Example:
        // int a;
        // int b;
        // a = 5;
        // b = 10;
        // IF (a < b) THEN
        //     Print(0);
        // ELSE
        //     Print(1);

        Statement ex5 = new CompoundStatement(
                new VariableDeclarationStatement("a", new IntType()),
                new CompoundStatement(
                        new VariableDeclarationStatement("b", new IntType()),
                        new CompoundStatement(
                                new AssignmentStatement("a", new ValueExpression(new IntegerValue(5))),
                                new CompoundStatement(
                                        new AssignmentStatement("b", new ValueExpression(new IntegerValue(10))),
                                        new IfStatement(
                                                new RelationalExpression(
                                                        new VariableExpression("a"),
                                                        new VariableExpression("b"),
                                                        "<"
                                                ),
                                                new PrintStatement(new ValueExpression(new IntegerValue(0))),
                                                new PrintStatement(new ValueExpression(new IntegerValue(1)))
                                        )
                                )
                        )
                )
        );

        ProgramState prg5 = new ProgramState(
                new ExecutionStack<>(),
                new SymbolTable<>(),
                new Output<>(),
                new FileTable<>(),
                ex5
        );

        RepositoryInterface repoIfRelational = new Repository("log5.txt");
        repoIfRelational.addProgramState(prg5);
        Controller ctr5 = new Controller(repoIfRelational);
        // Menu setup
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExampleCommand("1", ex1.toString(), ctr1));
        menu.addCommand(new RunExampleCommand("2", ex2.toString(), ctr2));
        menu.addCommand(new RunExampleCommand("3", ex3.toString(), ctr3));
        menu.addCommand(new RunExampleCommand("4", ex4.toString(), ctr4));
        menu.addCommand(new RunExampleCommand("5", ex5.toString(), ctr5));
        menu.show();

    }

}
