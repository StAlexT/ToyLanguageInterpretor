package model.view;

import model.controller.Controller;
import model.expression.*;
import model.state.files.FileTable;
import model.repository.Repository;
import model.repository.RepositoryInterface;
import model.state.*;
import model.statement.*;
import model.statement.CountSemaphoreStatements.Acquire;
import model.statement.CountSemaphoreStatements.CreateSemaphore;
import model.statement.CountSemaphoreStatements.Release;
import model.statement.CyclicBarrierStatements.Await;
import model.statement.CyclicBarrierStatements.NewBarrier;
import model.statement.LockStatements.LockStatement;
import model.statement.LockStatements.NewLock;
import model.statement.LockStatements.UnlockStatement;
import model.statement.Procedures.CallProcedureStatement;
import model.statement.Procedures.ProcNameStatement;
import model.statement.ToySemaphoreStatement.AcquireToySemaphore;
import model.statement.ToySemaphoreStatement.NewSemaphore;
import model.statement.ToySemaphoreStatement.ReleaseToySemaphore;
import model.type.IntType;
import model.type.RefType;
import model.type.StringType;
import model.value.IntegerValue;
import model.type.BoolType;
import model.value.BooleanNumber;
import model.value.StringValue;

import java.util.List;

public class Interpreter {
    public static void main(String[] args) {
//        // Example 1: - Logical
//        // int v;
//        // v=2;
//        // bool a;
//        // bool b;
//        // a = true;
//        // b = true;
//        // IF(a and b)
//        //      v = 3;
//        // ELSE
//        //      v = 2;
//        // print(v);
//        Statement ex1 = new CompoundStatement(
//                new VariableDeclarationStatement("v", new IntType()),
//                new CompoundStatement(
//                        new AssignmentStatement("v", new ValueExpression(new IntegerValue(2))),
//                        new CompoundStatement(
//                                new VariableDeclarationStatement("a", new BoolType()),
//                                new CompoundStatement(
//                                        new VariableDeclarationStatement("b", new BoolType()),
//                                        new CompoundStatement(
//                                                new AssignmentStatement("a", new ValueExpression(new BooleanNumber(true))),
//                                                new CompoundStatement(
//                                                        new AssignmentStatement("b", new ValueExpression(new BooleanNumber(true))),
//                                                        new CompoundStatement(
//                                                                new IfStatement(
//                                                                        new LogicalExpression(
//                                                                        new VariableExpression("a"),
//                                                                        new VariableExpression("b"),
//                                                                        1
//                                                                    ),
//                                                                    new AssignmentStatement("v", new ValueExpression(new IntegerValue(3))),
//                                                                    new AssignmentStatement("v", new ValueExpression(new IntegerValue(2)))
//                                                                ),
//                                                                new PrintStatement(new VariableExpression("v"))
//                                                        )
//
//                                                )
//                                        )
//                                )
//                        )
//                )
//        );
//
//        ProgramState prg1 = new ProgramState(
//                new ExecutionStack<>(),
//                new SymbolTable<>(),
//                new Output<>(),
//                new FileTable<>(),
//                new HeapRef<>(),
//                new CountSemaphore<>(),
//                new Lock<>(),
//                ex1
//        );
//        RepositoryInterface repo1 = new Repository("log1.txt");
//        repo1.addProgramState(prg1);
//        Controller ctr1 = new Controller(repo1);
//
//        // Example 2: Arithmetical
//        // int a;
//        // a=(2 + (3 * 5));
//        // int b;
//        // b=(a - ((4 / 2) + 7)));
//        // print(b);
//        Statement ex2 = new CompoundStatement(
//                new VariableDeclarationStatement("a", new IntType()),
//                new CompoundStatement(
//                        new AssignmentStatement("a",
//                                new ArithmeticExpression(
//                                        new ValueExpression(new IntegerValue(2)),
//                                        new ArithmeticExpression(
//                                                new ValueExpression(new IntegerValue(3)),
//                                                new ValueExpression(new IntegerValue(5)),
//                                                3
//                                        ),
//                                        1
//                                )
//                        ),
//                        new CompoundStatement(
//                                new VariableDeclarationStatement("b", new IntType()),
//                                new CompoundStatement(
//                                        new AssignmentStatement("b",
//                                                new ArithmeticExpression(
//                                                        new VariableExpression("a"),
//                                                        new ArithmeticExpression(
//                                                                new ArithmeticExpression(
//                                                                        new ValueExpression(new IntegerValue(4)),
//                                                                        new ValueExpression(new IntegerValue(0)),
//                                                                        4
//                                                                ),
//                                                                new ValueExpression(new IntegerValue(7)),
//                                                                1
//                                                        ),
//                                                        2
//                                                )
//                                        ),
//                                        new PrintStatement(new VariableExpression("b"))
//                                )
//                        )
//                )
//        );
//        ProgramState prg2 = new ProgramState(
//                new ExecutionStack<>(),
//                new SymbolTable<>(),
//                new Output<>(),
//                new FileTable<>(),
//                new HeapRef<>(),
//                new CountSemaphore<>(),
//                new Lock<>(),
//                ex2
//        );
//        RepositoryInterface repo2 = new Repository("log2.txt");
//        repo2.addProgramState(prg2);
//        Controller ctr2 = new Controller(repo2);
//
//        // Example 3:
//        // bool a;
//        // a=false;
//        // int v;
//        // IF(a) THEN
//        //      (v=2);
//        // ELSE
//        //      (v=3);
//        // print(v);
//        Statement ex3 = new CompoundStatement(
//                new VariableDeclarationStatement("a", new BoolType()),
//                new CompoundStatement(
//                        new AssignmentStatement("a", new ValueExpression(new BooleanNumber(false))),
//                        new CompoundStatement(
//                                new VariableDeclarationStatement("v", new IntType()),
//                                new CompoundStatement(
//                                        new IfStatement(
//                                                new VariableExpression("a"),
//                                                new AssignmentStatement("v", new ValueExpression(new IntegerValue(2))),
//                                                new AssignmentStatement("v", new ValueExpression(new IntegerValue(3)))
//                                        ),
//                                        new PrintStatement(new VariableExpression("v"))
//                                )
//                        )
//                )
//        );
//
//        ProgramState prg3 = new ProgramState(
//                new ExecutionStack<>(),
//                new SymbolTable<>(),
//                new Output<>(),
//                new FileTable<>(),
//                new HeapRef<>(),
//                new CountSemaphore<>(),
//                new Lock<>(),
//                ex3
//        );
//        RepositoryInterface repo3 = new Repository("log3.txt");
//        repo3.addProgramState(prg3);
//        Controller ctr3 = new Controller(repo3);
//        //Example 4: (string varf;(varf=test.txt;(openRFile(varf);(int varc;(readFile(varf, varc);(print(varc);(readFile(varf, varc);(print(varc);closeRFile(varf)))))))))
//        Statement ex4 = new CompoundStatement(
//                new VariableDeclarationStatement("varf", new StringType()),
//                new CompoundStatement(
//                        new AssignmentStatement("varf", new ValueExpression(new StringValue("test.txt"))),
//                        new CompoundStatement(
//                                new OpenReadFileStatement(new VariableExpression("varf")),
//                                new CompoundStatement(
//                                        new VariableDeclarationStatement("varc", new IntType()),
//                                        new CompoundStatement(
//                                                new ReadFileStatement(new VariableExpression("varf"), "varc"),
//                                                new CompoundStatement(
//                                                        new PrintStatement(new VariableExpression("varc")),
//                                                        new CompoundStatement(
//                                                                new ReadFileStatement(new VariableExpression("varf"), "varc"),
//                                                                new CompoundStatement(
//                                                                        new PrintStatement(new VariableExpression("varc")),
//                                                                        new CloseReadFileStatement(new VariableExpression("varf"))
//                                                                )
//                                                        )
//                                                )
//                                        )
//                                )
//                        )
//                )
//        );
//
//        ProgramState prg4 = new ProgramState(
//                new ExecutionStack<>(),
//                new SymbolTable<>(),
//                new Output<>(),
//                new FileTable<>(),
//                new HeapRef<>(),
//                new CountSemaphore<>(),
//                new Lock<>(),
//                ex4
//        );
//        RepositoryInterface repo4 = new Repository("log4.txt");
//        repo4.addProgramState(prg4);
//        Controller ctr4 = new Controller(repo4);
//        //Example:
//        // int a;
//        // int b;
//        // a = 5;
//        // b = 10;
//        // IF (a < b) THEN
//        //     Print(0);
//        // ELSE
//        //     Print(1);
//
//        Statement ex5 = new CompoundStatement(
//                new VariableDeclarationStatement("a", new IntType()),
//                new CompoundStatement(
//                        new VariableDeclarationStatement("b", new IntType()),
//                        new CompoundStatement(
//                                new AssignmentStatement("a", new ValueExpression(new IntegerValue(5))),
//                                new CompoundStatement(
//                                        new AssignmentStatement("b", new ValueExpression(new IntegerValue(10))),
//                                        new IfStatement(
//                                                new RelationalExpression(
//                                                        new VariableExpression("a"),
//                                                        new VariableExpression("b"),
//                                                        "<"
//                                                ),
//                                                new PrintStatement(new ValueExpression(new IntegerValue(0))),
//                                                new PrintStatement(new ValueExpression(new IntegerValue(1)))
//                                        )
//                                )
//                        )
//                )
//        );
//
//        ProgramState prg5 = new ProgramState(
//                new ExecutionStack<>(),
//                new SymbolTable<>(),
//                new Output<>(),
//                new FileTable<>(),
//                new HeapRef<>(),
//                new CountSemaphore<>(),
//                new Lock<>(),
//                ex5
//        );
//
//        RepositoryInterface repoIfRelational = new Repository("log5.txt");
//        repoIfRelational.addProgramState(prg5);
//        Controller ctr5 = new Controller(repoIfRelational);
//        //int v; v=4; (while (v>0) print(v);v=v-1);print(v)
//
//        Statement ex6 = new CompoundStatement(
//                new VariableDeclarationStatement("v", new IntType()),
//                new CompoundStatement(
//                        new AssignmentStatement("v", new ValueExpression(new IntegerValue(4))),
//                        new CompoundStatement(
//                                new WhileStatement(
//                                        new RelationalExpression(
//                                                new VariableExpression("v"),
//                                                new ValueExpression(new IntegerValue(0)),
//                                                ">"
//                                        ),
//                                        new CompoundStatement(
//                                                new PrintStatement(new VariableExpression("v")),
//                                                new AssignmentStatement(
//                                                        "v",
//                                                        new ArithmeticExpression(
//                                                                new VariableExpression("v"),
//                                                                new ValueExpression(new IntegerValue(1)),
//                                                                2
//                                                        )
//                                                )
//                                        )
//                                ),
//                                new PrintStatement(new VariableExpression("v"))
//                        )
//                )
//        );
//
//        ProgramState prg6 = new ProgramState(
//                new ExecutionStack<>(),
//                new SymbolTable<>(),
//                new Output<>(),
//                new FileTable<>(),
//                new HeapRef<>(),
//
//                new CountSemaphore<>(),
//                ex6
//        );
//        RepositoryInterface repo6 = new Repository("log6.txt");
//        repo6.addProgramState(prg6);
//        Controller ctr6 = new Controller(repo6);
//        //Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)
//
//        Statement ref1 = new CompoundStatement(
//                new VariableDeclarationStatement("v", new RefType(new IntType())),
//                new CompoundStatement(
//                        new NewStatement(
//                                "v",
//                                new ValueExpression(new IntegerValue(20))
//                        ),
//                        new CompoundStatement(
//                                new VariableDeclarationStatement("a", new RefType(new RefType( new IntType()))),
//                                new CompoundStatement(
//                                        new NewStatement("a", new VariableExpression("v")),
//                                        new CompoundStatement(
//                                                new PrintStatement(new VariableExpression("v")),
//                                                new PrintStatement(new VariableExpression("a"))
//                                        )
//                                )
//                        )
//                )
//        );
//
//        ProgramState refState1 = new ProgramState(
//                new ExecutionStack<>(),
//                new SymbolTable<>(),
//                new Output<>(),
//                new FileTable<>(),
//                new HeapRef<>(),
//                new CountSemaphore<>(),
//                ref1
//        );
//
//        RepositoryInterface repo7 = new Repository("ref1.txt");
//        repo7.addProgramState(refState1);
//        Controller ctr7 = new Controller(repo7);
//
//        //Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
//        Statement ref2 = new CompoundStatement(
//                new VariableDeclarationStatement("v", new RefType(new IntType())),
//                new CompoundStatement(
//                        new NewStatement(
//                                "v",
//                                new ValueExpression(new IntegerValue(20))
//                        ),
//                        new CompoundStatement(
//                                new VariableDeclarationStatement("a", new RefType(new RefType( new IntType()))),
//                                new CompoundStatement(
//                                        new NewStatement("a", new VariableExpression("v")),
//                                        new CompoundStatement(
//                                                new PrintStatement(new ReadingHeapExpression(new VariableExpression("v"))),
//                                                new PrintStatement(
//                                                        new ArithmeticExpression(
//                                                                new ReadingHeapExpression(
//                                                                        new ReadingHeapExpression(
//                                                                            new VariableExpression("a")
//                                                                        )
//                                                                ),
//                                                                new ValueExpression(
//                                                                        new IntegerValue(5)
//                                                                ),
//                                                                1
//                                                        )
//                                                )
//                                        )
//                                )
//                        )
//                )
//        );
//
//        ProgramState refState2 = new ProgramState(
//                new ExecutionStack<>(),
//                new SymbolTable<>(),
//                new Output<>(),
//                new FileTable<>(),
//                new HeapRef<>(),
//
//                new CountSemaphore<>(),
//                ref2
//        );
//
//        RepositoryInterface repo8 = new Repository("ref2.txt");
//        repo8.addProgramState(refState2);
//        Controller ctr8 = new Controller(repo8);
//
//        //Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
//
//        Statement ref3 = new CompoundStatement(
//                new VariableDeclarationStatement("v", new RefType(new IntType())),
//                new CompoundStatement(
//                        new NewStatement(
//                                "v",
//                                new ValueExpression(new IntegerValue(20))
//                        ),
//                        new CompoundStatement(
//                                new PrintStatement(new ReadingHeapExpression(new VariableExpression("v"))),
//                                new CompoundStatement(
//                                        new WritingHeapStatement("v", new ValueExpression(new IntegerValue(30))),
//                                        new PrintStatement(
//                                                new ArithmeticExpression(
//                                                        new ReadingHeapExpression(new VariableExpression("v")),
//                                                        new ValueExpression(new IntegerValue(5)),
//                                                        1
//                                                )
//                                        )
//                                )
//                        )
//                )
//        );
//
//        ProgramState refState3 = new ProgramState(
//                new ExecutionStack<>(),
//                new SymbolTable<>(),
//                new Output<>(),
//                new FileTable<>(),
//                new HeapRef<>(),
//
//                new CountSemaphore<>(),
//                ref3
//        );
//
//        RepositoryInterface repo9 = new Repository("ref3.txt");
//        repo9.addProgramState(refState3);
//        Controller ctr9 = new Controller(repo9);
//
//        //Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
//        Statement ref4 = new CompoundStatement(
//                new VariableDeclarationStatement("v", new RefType(new IntType())),
//                new CompoundStatement(
//                        new NewStatement(
//                                "v",
//                                new ValueExpression(new IntegerValue(20))
//                        ),
//                        new CompoundStatement(
//                                new VariableDeclarationStatement("a", new RefType(new RefType( new IntType()))),
//                                new CompoundStatement(
//                                        new NewStatement("a", new VariableExpression("v")),
//                                        new CompoundStatement(
//                                                new NewStatement("v", new ValueExpression(new IntegerValue(30))),
//                                                new PrintStatement(new ReadingHeapExpression(new ReadingHeapExpression(new VariableExpression("a"))))
//                                        )
//                                )
//                        )
//                )
//        );
//
//        ProgramState refState4 = new ProgramState(
//                new ExecutionStack<>(),
//                new SymbolTable<>(),
//                new Output<>(),
//                new FileTable<>(),
//                new HeapRef<>(),
//                new CountSemaphore<>(),
//                ref4
//        );
//
//        RepositoryInterface repo10 = new Repository("ref4.txt");
//        repo10.addProgramState(refState4);
//        Controller ctr10 = new Controller(repo10);
//        // Menu setup
//// Failing Examples for typeCheck
//        Statement bad1 = new CompoundStatement(
//                new VariableDeclarationStatement("v", new IntType()),
//                new AssignmentStatement("v", new ValueExpression(new BooleanNumber(true)))
//        );
//
//        Statement bad2 = new CompoundStatement(
//                new VariableDeclarationStatement("v", new IntType()),
//                new PrintStatement(new ReadingHeapExpression(new VariableExpression("v")))
//        );
//
//        Statement bad5 = new CompoundStatement(
//                new VariableDeclarationStatement("v", new IntType()),
//                new IfStatement(
//                        new VariableExpression("v"), // int instead of bool
//                        new PrintStatement(new ValueExpression(new IntegerValue(1))),
//                        new PrintStatement(new ValueExpression(new IntegerValue(0)))
//                )
//        );
//
//        Statement bad6 = new CompoundStatement(
//                new VariableDeclarationStatement("r", new RefType(new IntType())),
//                new NewStatement("r", new ValueExpression(new BooleanNumber(false)))
//        );
//
//// Add to Repository and Controller
//        RepositoryInterface repoBad1 = new Repository("bad1.txt");
//        repoBad1.addProgramState(new ProgramState(new ExecutionStack<>(), new SymbolTable<>(), new Output<>(), new FileTable<>(), new HeapRef<>(),
//                new CountSemaphore<>(), bad1));
//        RepositoryInterface repoBad2 = new Repository("bad2.txt");
//        repoBad2.addProgramState(new ProgramState(new ExecutionStack<>(), new SymbolTable<>(), new Output<>(), new FileTable<>(), new HeapRef<>(),
//                new CountSemaphore<>(), bad2));
//         RepositoryInterface repoBad5 = new Repository("bad5.txt");
//         repoBad5.addProgramState(new ProgramState(new ExecutionStack<>(), new SymbolTable<>(), new Output<>(), new FileTable<>(), new HeapRef<>(),
//                 new CountSemaphore<>(), bad5));
//        RepositoryInterface repoBad6 = new Repository("bad6.txt");
//        repoBad6.addProgramState(new ProgramState(new ExecutionStack<>(), new SymbolTable<>(), new Output<>(), new FileTable<>(), new HeapRef<>(),
//                new CountSemaphore<>(), bad6));
//
//        Controller ctrBad1 = new Controller(repoBad1);
//        Controller ctrBad2 = new Controller(repoBad2);
//
//        Controller ctrBad5 = new Controller(repoBad5);
//        Controller ctrBad6 = new Controller(repoBad6);
//        Statement simpleFork =
//                new CompoundStatement(
//                        new VariableDeclarationStatement("v", new IntType()),
//                        new CompoundStatement(
//                                new AssignmentStatement(
//                                        "v",
//                                        new ValueExpression(new IntegerValue(1))
//                                ),
//                                new CompoundStatement(
//                                        new ForkStatement(
//                                                new CompoundStatement(
//                                                        new AssignmentStatement(
//                                                                "v",
//                                                                new ValueExpression(new IntegerValue(2))
//                                                        ),
//                                                        new PrintStatement(
//                                                                new VariableExpression("v")
//                                                        )
//                                                )
//                                        ),
//                                        new PrintStatement(
//                                                new VariableExpression("v")
//                                        )
//                                )
//                        )
//                );
//        ProgramState sFork = new ProgramState(
//                new ExecutionStack<>(),
//                new SymbolTable<>(),
//                new Output<>(),
//                new FileTable<>(),
//                new HeapRef<>(),
//                new CountSemaphore<>(),
//                simpleFork
//        );
//        RepositoryInterface repoSimpleFork = new Repository("forkExample.txt");
//        repoSimpleFork.addProgramState(sFork);
//        Controller ctrSimpleFork = new Controller(repoSimpleFork);
//        Statement refFork = new CompoundStatement(
//                new VariableDeclarationStatement("v", new IntType()),
//                new CompoundStatement(
//                        new VariableDeclarationStatement("a", new RefType(new IntType())),
//                        new CompoundStatement(
//                                new AssignmentStatement(
//                                        "v",
//                                        new ValueExpression(new IntegerValue(10))
//                                ),
//                                new CompoundStatement(
//                                        new NewStatement(
//                                                "a",
//                                                new ValueExpression(new IntegerValue(22))
//                                        ),
//                                        new CompoundStatement(
//                                                new ForkStatement(
//                                                        new CompoundStatement(
//                                                                new WritingHeapStatement(
//                                                                        "a",
//                                                                        new ValueExpression(new IntegerValue(30))
//                                                                ),
//                                                                new CompoundStatement(
//                                                                        new AssignmentStatement(
//                                                                                "v",
//                                                                                new ValueExpression(new IntegerValue(32))
//                                                                        ),
//                                                                        new CompoundStatement(
//                                                                                new PrintStatement(
//                                                                                        new VariableExpression("v")
//                                                                                ),
//                                                                                new PrintStatement(
//                                                                                        new ReadingHeapExpression(
//                                                                                                new VariableExpression("a")
//                                                                                        )
//                                                                                )
//                                                                        )
//                                                                )
//                                                        )
//                                                ),
//                                                new CompoundStatement(
//                                                        new PrintStatement(
//                                                                new VariableExpression("v")
//                                                        ),
//                                                        new PrintStatement(
//                                                                new ReadingHeapExpression(
//                                                                        new VariableExpression("a")
//                                                                )
//                                                        )
//                                                )
//                                        )
//                                )
//                        )
//                )
//        );
//
//        ProgramState refForkState = new ProgramState(
//                new ExecutionStack<>(),
//                new SymbolTable<>(),
//                new Output<>(),
//                new FileTable<>(),
//                new HeapRef<>(),
//                new CountSemaphore<>(),
//                refFork
//        );
//
//        RepositoryInterface repoFork = new Repository("forkExample2.txt");
//        repoFork.addProgramState(refForkState);
//        Controller ctrFork = new Controller(repoFork);


        //int a; int b; int c;
        //a=1;b=2;c=5;
        //(switch(a*10)
        //(case (b*c) : print(a);print(b))
        //(case (10) : print(100);print(200))
        //(default : print(300)));
        //print(300)
        //The final Out should be {1,2,300}
        Statement exSwitch = new CompoundStatement(
                new VariableDeclarationStatement("a", new IntType()),
                new CompoundStatement(
                        new VariableDeclarationStatement("b", new IntType()),
                        new CompoundStatement(
                                new VariableDeclarationStatement("c", new IntType()),
                                new CompoundStatement(
                                        new AssignmentStatement("a", new ValueExpression(new IntegerValue(1))),
                                        new CompoundStatement(
                                                new AssignmentStatement("b", new ValueExpression(new IntegerValue(2))),
                                                new CompoundStatement(
                                                        new AssignmentStatement("c", new ValueExpression(new IntegerValue(5))),
                                                        new CompoundStatement(
                                                                new SwitchStatement(
                                                                        new ArithmeticExpression(
                                                                                new VariableExpression("a"),
                                                                                new ValueExpression(new IntegerValue(10)),
                                                                                3
                                                                        ),
                                                                        new ArithmeticExpression(
                                                                                new VariableExpression("b"),
                                                                                new VariableExpression("c"),
                                                                                3
                                                                        ),
                                                                        new CompoundStatement(
                                                                                new PrintStatement(new VariableExpression("a")),
                                                                                new PrintStatement(new VariableExpression("b"))
                                                                        ),
                                                                        new ValueExpression(new IntegerValue(10)),
                                                                        new CompoundStatement(
                                                                                new PrintStatement(new ValueExpression(new IntegerValue(100))),
                                                                                new PrintStatement(new ValueExpression(new IntegerValue(200)))
                                                                        ),
                                                                        new PrintStatement(new ValueExpression(new IntegerValue(300)))
                                                                ),
                                                                new PrintStatement(new ValueExpression(new IntegerValue(300)))
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        ProgramState prgSwitch =  new ProgramState(
                new ExecutionStack<>(),
                new SymbolTable<>(),
                new Output<>(),
                new FileTable<>(),
                new HeapRef<>(),
                new CountSemaphore<>(),
                new Lock<>(),
                new ProcTable(),
                new CyclicBarrier(),
                new ToySemaphore(),
                exSwitch
        );
        RepositoryInterface repoSw = new Repository("switch.xt");
        repoSw.addProgramState(prgSwitch);
        Controller cntrSw = new Controller(repoSw);

        /*Ref int v1; int cnt;
        new(v1,1);createSemaphore(cnt,rH(v1));
        fork(acquire(cnt);wh(v1,rh(v1)*10);print(rh(v1));release(cnt));
        fork(acquire(cnt);wh(v1,rh(v1)*10);wh(v1,rh(v1)*2);print(rh(v1));release(cnt));
        acquire(cnt);
        print(rh(v1)-1);
        release(cnt)
        The final Out should be {10,200,9} or {10,9,200}.*/
        Statement exSemaphore = new CompoundStatement(
                new VariableDeclarationStatement("v1", new RefType(new IntType())),
                new CompoundStatement(
                        new VariableDeclarationStatement("cnt", new IntType()),
                        new CompoundStatement(
                                new NewStatement("v1", new ValueExpression(new IntegerValue(1))),
                                new CompoundStatement(
                                        new CreateSemaphore("cnt", new ReadingHeapExpression(new VariableExpression("v1"))),
                                        new CompoundStatement(
                                                new ForkStatement(
                                                        new CompoundStatement(
                                                                new Acquire("cnt"),
                                                                new CompoundStatement(
                                                                        new WritingHeapStatement("v1",
                                                                            new ArithmeticExpression(
                                                                            new ReadingHeapExpression(new VariableExpression("v1")),
                                                                            new ValueExpression(new IntegerValue(10)), 3)),
                                                                        new CompoundStatement(
                                                                            new PrintStatement(new ReadingHeapExpression(new VariableExpression("v1"))),
                                                                            new Release("cnt")
                                                                        )
                                                                )
                                                        )
                                                ),
                                                new CompoundStatement(
                                                        new ForkStatement(
                                                                new CompoundStatement(
                                                                        new Acquire("cnt"),
                                                                        new CompoundStatement(
                                                                                new WritingHeapStatement("v1",
                                                                                        new ArithmeticExpression(
                                                                                                new ReadingHeapExpression(new VariableExpression("v1")),
                                                                                                new ValueExpression(new IntegerValue(10)), 3)),
                                                                                new CompoundStatement(
                                                                                        new WritingHeapStatement("v1",
                                                                                                new ArithmeticExpression(
                                                                                                        new ReadingHeapExpression(new VariableExpression("v1")),
                                                                                                        new ValueExpression(new IntegerValue(2)), 3)),
                                                                                        new CompoundStatement(
                                                                                            new PrintStatement(new ReadingHeapExpression(new VariableExpression("v1"))),
                                                                                            new Release("cnt")
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        ),
                                                        new CompoundStatement(
                                                                new Acquire("cnt"),
                                                                new CompoundStatement(
                                                                        new PrintStatement(
                                                                                new ArithmeticExpression(
                                                                                        new ReadingHeapExpression(new VariableExpression("v1")),
                                                                                        new ValueExpression(new IntegerValue(1)),
                                                                                        2
                                                                                )
                                                                        ),
                                                                        new Release("cnt")
                                                                )
                                                        )

                                                )
                                        )
                                )
                        )
                )
        );

        ProgramState semaphoreState = new ProgramState(
                new ExecutionStack<>(),
                new SymbolTable<>(),
                new Output<>(),
                new FileTable<>(),
                new HeapRef<>(),
                new CountSemaphore<>(),
                new Lock<>(),
                new ProcTable(),
                new CyclicBarrier(),
                new ToySemaphore(),
                exSemaphore
        );

        RepositoryInterface repoSem = new Repository("semaphore.txt");
        repoSem.addProgramState(semaphoreState);
        Controller ctrSem = new Controller(repoSem);


        //new(v1,20);new(v2,30);newLock(x);
        //fork(
                //fork(
                        //lock(x);wh(v1,rh(v1)-1);unlock(x)
                //);
                //lock(x);wh(v1,rh(v1)+1);unlock(x)
        //);
        //fork(
        //        fork(wh(v2,rh(v2)+1));
                //wh(v2,rh(v2)+1);unlock(x)

        //);
        //print (rh(v1));
        //print(rh(v2))

        Statement exLock =
                new CompoundStatement(
                        new VariableDeclarationStatement("v1", new RefType(new IntType())),
                        new CompoundStatement(
                                new VariableDeclarationStatement("v2", new RefType(new IntType())),
                                new CompoundStatement(
                                        new VariableDeclarationStatement("x", new IntType()),
                                        new CompoundStatement(
                                                new NewStatement("v1", new ValueExpression(new IntegerValue(20))),
                                                new CompoundStatement(
                                                        new NewStatement("v2", new ValueExpression(new IntegerValue(30))),
                                                        new CompoundStatement(
                                                                new NewLock("x"),
                                                                new CompoundStatement(
                                                                        // First fork: v1 updates (locked)
                                                                        new ForkStatement(
                                                                                new CompoundStatement(
                                                                                        new ForkStatement(
                                                                                                new CompoundStatement(
                                                                                                        new LockStatement("x"),
                                                                                                        new CompoundStatement(
                                                                                                                new WritingHeapStatement(
                                                                                                                        "v1",
                                                                                                                        new ArithmeticExpression(
                                                                                                                                new ReadingHeapExpression(new VariableExpression("v1")),
                                                                                                                                new ValueExpression(new IntegerValue(1)),
                                                                                                                                2 // decrement
                                                                                                                        )
                                                                                                                ),
                                                                                                                new UnlockStatement("x")
                                                                                                        )
                                                                                                )
                                                                                        ),
                                                                                        new CompoundStatement(
                                                                                                new LockStatement("x"),
                                                                                                new CompoundStatement(
                                                                                                        new WritingHeapStatement(
                                                                                                                "v1",
                                                                                                                new ArithmeticExpression(
                                                                                                                        new ReadingHeapExpression(new VariableExpression("v1")),
                                                                                                                        new ValueExpression(new IntegerValue(1)),
                                                                                                                        1 // increment
                                                                                                                )
                                                                                                        ),
                                                                                                        new UnlockStatement("x")
                                                                                                )
                                                                                        )
                                                                                )
                                                                        ),
                                                                        new CompoundStatement(
                                                                                // Second fork: v2 increments (unlocked, split into independent statements)
                                                                                new ForkStatement(
                                                                                        new WritingHeapStatement(
                                                                                                "v2",
                                                                                                new ArithmeticExpression(
                                                                                                        new ReadingHeapExpression(new VariableExpression("v2")),
                                                                                                        new ValueExpression(new IntegerValue(1)),
                                                                                                        1
                                                                                                )
                                                                                        )
                                                                                ),
                                                                                new CompoundStatement(
                                                                                        new ForkStatement(
                                                                                                new WritingHeapStatement(
                                                                                                        "v2",
                                                                                                        new ArithmeticExpression(
                                                                                                                new ReadingHeapExpression(new VariableExpression("v2")),
                                                                                                                new ValueExpression(new IntegerValue(1)),
                                                                                                                1
                                                                                                        )
                                                                                                )
                                                                                        ),
                                                                                        // Print final values
                                                                                        new CompoundStatement(
                                                                                                new PrintStatement(new ReadingHeapExpression(new VariableExpression("v1"))),
                                                                                                new PrintStatement(new ReadingHeapExpression(new VariableExpression("v2")))
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                );



// --- Procedures ---
        Statement sumProc = new ProcNameStatement(
                "sum",
                List.of("a", "b"),
                new CompoundStatement(
                        new VariableDeclarationStatement("v", new IntType()), // declare v
                        new CompoundStatement(
                                new AssignmentStatement("v",
                                        new ArithmeticExpression(
                                                new VariableExpression("a"),
                                                new VariableExpression("b"),
                                                1)), // 1 = +
                                new PrintStatement(new VariableExpression("v"))
                        )
                )
        );

        Statement productProc = new ProcNameStatement(
                "product",
                List.of("a", "b"),
                new CompoundStatement(
                        new VariableDeclarationStatement("v", new IntType()), // declare v
                        new CompoundStatement(
                                new AssignmentStatement("v",
                                        new ArithmeticExpression(
                                                new VariableExpression("a"),
                                                new VariableExpression("b"),
                                                3)), // 3 = *
                                new PrintStatement(new VariableExpression("v"))
                        )
                )
        );

// --- Main Program ---
        Statement mainProgram = new CompoundStatement(
                new VariableDeclarationStatement("v", new IntType()), // declare v in main
                new CompoundStatement(
                        new AssignmentStatement("v", new ValueExpression(new IntegerValue(2))),
                        new CompoundStatement(
                                new VariableDeclarationStatement("w", new IntType()), // declare w
                                new CompoundStatement(
                                        new AssignmentStatement("w", new ValueExpression(new IntegerValue(5))),
                                        new CompoundStatement(
                                                new CallProcedureStatement(
                                                        "sum",
                                                        List.of(
                                                                new ArithmeticExpression(new VariableExpression("v"),
                                                                        new ValueExpression(new IntegerValue(10)),
                                                                        3), // *
                                                                new VariableExpression("w")
                                                        )
                                                ),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("v")),
                                                        new CompoundStatement(
                                                                new ForkStatement(
                                                                        new CallProcedureStatement(
                                                                                "product",
                                                                                List.of(new VariableExpression("v"), new VariableExpression("w"))
                                                                        )
                                                                ),
                                                                new ForkStatement(
                                                                        new CallProcedureStatement(
                                                                                "sum",
                                                                                List.of(new VariableExpression("v"), new VariableExpression("w"))
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        Statement exProc = new CompoundStatement(
                sumProc,
                new CompoundStatement(
                        productProc,
                        mainProgram
                )
        );

        ProgramState procState = new ProgramState(
                new ExecutionStack<>(),
                new SymbolTable<>(),
                new Output<>(),
                new FileTable<>(),
                new HeapRef<>(),
                new CountSemaphore<>(),
                new Lock<>(),
                new ProcTable(),
                new CyclicBarrier(),
                new ToySemaphore(),
                exProc
        );
        RepositoryInterface repoProc = new Repository("proc.txt");
        repoProc.addProgramState(procState);
        Controller crtProc = new Controller(repoProc);

//        Statement ex = new CompoundStatement(
//                new AssignStatement("v1", new ValueExpression(new IntValue(2))),
//                new CompoundStatement(
//                        new AssignStatement("v2", new ValueExpression(new IntValue(3))),
//                        new IfStatement(
//                                new VariableExpression("v1"),
//                                new PrintStatement(new MulExpression(
//                                        new VariableExpression("v1"),
//                                        new VariableExpression("v2")
//                                )),
//                                new PrintStatement(new VariableExpression("v1"))
//                        )
//                )
//        );


        Statement startCB = new CompoundStatement(
                new VariableDeclarationStatement("v1", new RefType(new IntType())),
                new CompoundStatement(
                        new VariableDeclarationStatement("v2", new RefType(new IntType())),
                        new CompoundStatement(
                                new VariableDeclarationStatement("v3", new RefType(new IntType())),
                                new CompoundStatement(
                                        new VariableDeclarationStatement("cnt", new IntType()),
                                        new CompoundStatement(
                                                new NewStatement("v1", new ValueExpression(new IntegerValue(2))),
                                                new CompoundStatement(
                                                        new NewStatement("v2", new ValueExpression(new IntegerValue(3))),
                                                        new CompoundStatement(
                                                                new NewStatement("v3", new ValueExpression(new IntegerValue(4))),
                                                                new NewBarrier("cnt", new ReadingHeapExpression(new VariableExpression("v2")))
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        Statement firstForkCB =new ForkStatement(
                new CompoundStatement(
                        new Await("cnt"),
                        new CompoundStatement(
                                new WritingHeapStatement("v1",
                                        new ArithmeticExpression(new ReadingHeapExpression(new VariableExpression("v1")), new ValueExpression(new IntegerValue(10)), 3)
                                ),
                                new PrintStatement(new ReadingHeapExpression(new VariableExpression("v1")))
                        )
                )
        );

        Statement secondForkCB = new ForkStatement(
                new CompoundStatement(
                        new Await("cnt"),
                        new CompoundStatement(
                                new WritingHeapStatement("v2",
                                        new ArithmeticExpression(new ReadingHeapExpression(new VariableExpression("v2")), new ValueExpression(new IntegerValue(10)), 3)
                                ),
                                new CompoundStatement(
                                        new WritingHeapStatement("v2",
                                                new ArithmeticExpression(new ReadingHeapExpression(new VariableExpression("v2")), new ValueExpression(new IntegerValue(10)), 3)
                                        ),
                                        new PrintStatement(new ReadingHeapExpression(new VariableExpression("v2")))
                                )

                        )
                )

        );

        Statement exCyBarrier = new CompoundStatement(
                startCB,
                new CompoundStatement(
                        firstForkCB,
                        new CompoundStatement(
                                secondForkCB,
                                new CompoundStatement(
                                        new Await("cnt"),
                                        new PrintStatement(new ReadingHeapExpression(new VariableExpression("v3")))
                                )
                        )
                )
        );

        ProgramState cyclState = new ProgramState(
                new ExecutionStack<>(),
                new SymbolTable<>(),
                new Output<>(),
                new FileTable<>(),
                new HeapRef<>(),
                new CountSemaphore<>(),
                new Lock<>(),
                new ProcTable(),
                new CyclicBarrier(),
                new ToySemaphore(),
                exCyBarrier
        );
        RepositoryInterface repoCyB = new Repository("barrier.txt");
        repoCyB.addProgramState(cyclState);
        Controller crtCy = new Controller(repoCyB);

        Statement exToySem =
                new CompoundStatement(
                        new VariableDeclarationStatement("v1", new RefType(new IntType())),
                        new CompoundStatement(
                                new VariableDeclarationStatement("cnt", new IntType()),
                                new CompoundStatement(
                                        new NewStatement("v1", new ValueExpression(new IntegerValue(2))),
                                        new CompoundStatement(
                                                new NewSemaphore(
                                                        "cnt",
                                                        new ReadingHeapExpression(new VariableExpression("v1")),
                                                        new ValueExpression(new IntegerValue(1))
                                                ),
                                                new CompoundStatement(
                                                        new ForkStatement(
                                                                new CompoundStatement(
                                                                        new AcquireToySemaphore("cnt"),
                                                                        new CompoundStatement(
                                                                                new WritingHeapStatement(
                                                                                        "v1",
                                                                                        new ArithmeticExpression(
                                                                                                new ReadingHeapExpression(new VariableExpression("v1")),
                                                                                                new ValueExpression(new IntegerValue(10)),
                                                                                                3
                                                                                        )
                                                                                ),
                                                                                new CompoundStatement(
                                                                                        new PrintStatement(
                                                                                                new ReadingHeapExpression(new VariableExpression("v1"))
                                                                                        ),
                                                                                        new ReleaseToySemaphore("cnt")
                                                                                )
                                                                        )
                                                                )
                                                        ),
                                                        new CompoundStatement(
                                                                new ForkStatement(
                                                                        new CompoundStatement(
                                                                                new AcquireToySemaphore("cnt"),
                                                                                new CompoundStatement(
                                                                                        new WritingHeapStatement(
                                                                                                "v1",
                                                                                                new ArithmeticExpression(
                                                                                                        new ReadingHeapExpression(new VariableExpression("v1")),
                                                                                                        new ValueExpression(new IntegerValue(10)),
                                                                                                        3
                                                                                                )
                                                                                        ),
                                                                                        new CompoundStatement(
                                                                                                new WritingHeapStatement(
                                                                                                        "v1",
                                                                                                        new ArithmeticExpression(
                                                                                                                new ReadingHeapExpression(new VariableExpression("v1")),
                                                                                                                new ValueExpression(new IntegerValue(2)),
                                                                                                                3
                                                                                                        )
                                                                                                ),
                                                                                                new CompoundStatement(
                                                                                                        new PrintStatement(
                                                                                                                new ReadingHeapExpression(new VariableExpression("v1"))
                                                                                                        ),
                                                                                                        new ReleaseToySemaphore("cnt")
                                                                                                )
                                                                                        )
                                                                                )
                                                                        )
                                                                ),
                                                                new CompoundStatement(
                                                                        new AcquireToySemaphore("cnt"),
                                                                        new CompoundStatement(
                                                                                new PrintStatement(
                                                                                        new ArithmeticExpression(
                                                                                                new ReadingHeapExpression(new VariableExpression("v1")),
                                                                                                new ValueExpression(new IntegerValue(1)),
                                                                                                2   // subtraction
                                                                                        )
                                                                                ),
                                                                                new ReleaseToySemaphore("cnt")
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                );

        ProgramState ToySemState = new ProgramState(
                new ExecutionStack<>(),
                new SymbolTable<>(),
                new Output<>(),
                new FileTable<>(),
                new HeapRef<>(),
                new CountSemaphore<>(),
                new Lock<>(),
                new ProcTable(),
                new CyclicBarrier(),
                new ToySemaphore(),
                exToySem
        );
        RepositoryInterface repoTySem = new Repository("toySem.txt");
        repoTySem.addProgramState(ToySemState);
        Controller crtTS = new Controller(repoTySem);

        Statement ex2Lock = new CompoundStatement(
                new VariableDeclarationStatement("v1", new RefType(new IntType())),
                new CompoundStatement(
                        new VariableDeclarationStatement("v2", new RefType(new IntType())),
                        new CompoundStatement(
                                new VariableDeclarationStatement("x", new IntType()),
                                new CompoundStatement(
                                        new VariableDeclarationStatement("q", new IntType()),
                                        new CompoundStatement(
                                                new NewStatement("v1", new ValueExpression(new IntegerValue(20))),
                                                new CompoundStatement(
                                                        new NewStatement("v2", new ValueExpression(new IntegerValue(30))),
                                                        new CompoundStatement(
                                                                new NewLock("x"),
                                                                new CompoundStatement(

                                                                        /* fork( fork(...) ; lock(x)... ) */
                                                                        new ForkStatement(
                                                                                new CompoundStatement(
                                                                                        new ForkStatement(
                                                                                                new CompoundStatement(
                                                                                                        new LockStatement("x"),
                                                                                                        new CompoundStatement(
                                                                                                                new WritingHeapStatement(
                                                                                                                        "v1",
                                                                                                                        new ArithmeticExpression(
                                                                                                                                new ReadingHeapExpression(new VariableExpression("v1")),
                                                                                                                                new ValueExpression(new IntegerValue(1)),
                                                                                                                                2 // -
                                                                                                                        )
                                                                                                                ),
                                                                                                                new UnlockStatement("x")
                                                                                                        )
                                                                                                )
                                                                                        ),
                                                                                        new CompoundStatement(
                                                                                                new LockStatement("x"),
                                                                                                new CompoundStatement(
                                                                                                        new WritingHeapStatement(
                                                                                                                "v1",
                                                                                                                new ArithmeticExpression(
                                                                                                                        new ReadingHeapExpression(new VariableExpression("v1")),
                                                                                                                        new ValueExpression(new IntegerValue(10)),
                                                                                                                        3 // *
                                                                                                                )
                                                                                                        ),
                                                                                                        new UnlockStatement("x")
                                                                                                )
                                                                                        )
                                                                                )
                                                                        ),

                                                                        new CompoundStatement(
                                                                                new NewLock("q"),
                                                                                new CompoundStatement(

                                                                                        /* fork( fork(...) ; lock(q)... ) */
                                                                                        new ForkStatement(
                                                                                                new CompoundStatement(
                                                                                                        new ForkStatement(
                                                                                                                new CompoundStatement(
                                                                                                                        new LockStatement("q"),
                                                                                                                        new CompoundStatement(
                                                                                                                                new WritingHeapStatement(
                                                                                                                                        "v2",
                                                                                                                                        new ArithmeticExpression(
                                                                                                                                                new ReadingHeapExpression(new VariableExpression("v2")),
                                                                                                                                                new ValueExpression(new IntegerValue(5)),
                                                                                                                                                1 // +
                                                                                                                                        )
                                                                                                                                ),
                                                                                                                                new UnlockStatement("q")
                                                                                                                        )
                                                                                                                )
                                                                                                        ),
                                                                                                        new CompoundStatement(
                                                                                                                new LockStatement("q"),
                                                                                                                new CompoundStatement(
                                                                                                                        new WritingHeapStatement(
                                                                                                                                "v2",
                                                                                                                                new ArithmeticExpression(
                                                                                                                                        new ReadingHeapExpression(new VariableExpression("v2")),
                                                                                                                                        new ValueExpression(new IntegerValue(10)),
                                                                                                                                        3 // *
                                                                                                                                )
                                                                                                                        ),
                                                                                                                        new UnlockStatement("q")
                                                                                                                )
                                                                                                        )
                                                                                                )
                                                                                        ),

                                                                                        new CompoundStatement(
                                                                                                new LockStatement("x"),
                                                                                                new CompoundStatement(
                                                                                                        new PrintStatement(
                                                                                                                new ReadingHeapExpression(new VariableExpression("v1"))
                                                                                                        ),
                                                                                                        new CompoundStatement(
                                                                                                                new UnlockStatement("x"),
                                                                                                                new CompoundStatement(
                                                                                                                        new LockStatement("q"),
                                                                                                                        new CompoundStatement(
                                                                                                                                new PrintStatement(
                                                                                                                                        new ReadingHeapExpression(new VariableExpression("v2"))
                                                                                                                                ),
                                                                                                                                new UnlockStatement("q")
                                                                                                                        )
                                                                                                                )
                                                                                                        )
                                                                                                )
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        ProgramState lockState = new ProgramState(
                new ExecutionStack<>(),
                new SymbolTable<>(),
                new Output<>(),
                new FileTable<>(),
                new HeapRef<>(),
                new CountSemaphore<>(),
                new Lock<>(),
                new ProcTable(),
                new CyclicBarrier(),
                new ToySemaphore(),
                ex2Lock
        );

        RepositoryInterface lockRepo = new Repository("lock.txt");
        lockRepo.addProgramState(lockState);
        Controller ctrLock = new Controller(lockRepo);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
//        menu.addCommand(new RunExampleCommand("1", ex1.toString(), ctr1));
//        menu.addCommand(new RunExampleCommand("2", ex2.toString(), ctr2));
//        menu.addCommand(new RunExampleCommand("3", ex3.toString(), ctr3));
//        menu.addCommand(new RunExampleCommand("4", ex4.toString(), ctr4));
//        menu.addCommand(new RunExampleCommand("5", ex5.toString(), ctr5));
//        menu.addCommand(new RunExampleCommand("6", ex6.toString(), ctr6));
//        menu.addCommand(new RunExampleCommand("7", ref1.toString(), ctr7));
//        menu.addCommand(new RunExampleCommand("8", ref2.toString(), ctr8));
//        menu.addCommand(new RunExampleCommand("9", ref3.toString(), ctr9));
//        menu.addCommand(new RunExampleCommand("10", ref4.toString(), ctr10));
//        menu.addCommand(new RunExampleCommand("11", bad1.toString(), ctrBad1));
//        menu.addCommand(new RunExampleCommand("12", bad2.toString(), ctrBad2));
//        menu.addCommand(new RunExampleCommand("13", bad5.toString(), ctrBad5));
//        menu.addCommand(new RunExampleCommand("14", bad6.toString(), ctrBad6));
//        menu.addCommand(new RunExampleCommand("15", refFork.toString(), ctrFork));
//        menu.addCommand(new RunExampleCommand("16", simpleFork.toString(), ctrSimpleFork));

        menu.addCommand(new RunExampleCommand("17", exSwitch.toString(), cntrSw));
        menu.addCommand(new RunExampleCommand("18", exSemaphore.toString(), ctrSem));
        menu.addCommand(new RunExampleCommand("19", exLock.toString(), ctrLock));
        menu.addCommand(new RunExampleCommand("20", exProc.toString(), crtProc));
        menu.addCommand(new RunExampleCommand("21", exCyBarrier.toString(), crtCy));
        menu.addCommand(new RunExampleCommand("22", exToySem.toString(), crtTS));
        menu.show();

    }

}
