package model.view;

import model.controller.Controller;
import model.exception.ToyLanguageException;
import model.expression.*;
import model.repository.Repository;
import model.repository.RepositoryInterface;
import model.state.ExecutionStack;
import model.state.Output;
import model.state.ProgramState;
import model.state.SymbolTable;
import model.statement.*;
import model.type.BoolType;
import model.type.IntType;
import model.value.BooleanNumber;
import model.value.IntegerValue;

import java.io.IOException;
import java.util.Scanner;

public class StatementBuilder implements StatementBuilderInterface {

    public StatementBuilder(){};

    public void read(){

    }


    @Override
    public String typeStatement(String statement){
        String s = statement.trim().toLowerCase();
        if(statement.contains("print")||statement.contains("Print"))
            return "print";
        else if(statement.contains("if")||statement.contains("If"))
            return "if";
        else if(statement.contains("int") || statement.contains("bool"))
            return "declaration";
        else if(statement.contains("="))
            return "assignment";
        else if(s.startsWith("openrfile"))
            return "openrfile";
        else if(s.startsWith("readfile"))
            return "readfile";
        else if(s.startsWith("closerfile"))
            return "closerfile";
        else return "";
    }
    public String typeExpression(String expression){
        String express = expression.trim();
        if(express.contains("<=") || express.contains(">=") || express.contains("==") || express.contains("!=")
            || express.contains("<") || express.contains(">"))
            return "relational";
        if(expression.contains("+")||expression.contains("-")||expression.contains("/")||expression.contains("*"))
            return "arithmetical";
        else if(expression.contains("and")||expression.contains("or"))
            return "logical";
        else if(expression.contains("false"))
            return "value bool false";
        else if(expression.contains("true"))
            return "value bool true";
        else try {
                Integer.parseInt(expression);
                return "value";
            } catch (NumberFormatException e) {
                return "variable";
            }
    }

    public Expression ImplementTypeExpression(String e) throws ToyLanguageException {
        String type = typeExpression(e);
        switch (type) {
            case "relational": {
                String[] operators = {"<=", ">=", "==", "!=", "<", ">"};
                for(String op : operators) {
                    if(e.contains(op)) {
                        String[] parts = e.split("\\Q" + op + "\\E", 2); // literal split
                        return new RelationalExpression(
                                ImplementTypeExpression(parts[0]),
                                ImplementTypeExpression(parts[1]),
                                op
                        );
                    }
                }
                throw new ToyLanguageException("Invalid relational expression: " + e);
            }
            case "arithmetical": {
                // handle left-right split by the last operator
                if (e.contains("+")) {
                    String[] parts = e.split("\\+", 2);
                    return new ArithmeticExpression(
                            ImplementTypeExpression(parts[0]),
                            ImplementTypeExpression(parts[1]),
                            1
                    );
                } else if (e.contains("-")) {
                    String[] parts = e.split("-", 2);
                    return new ArithmeticExpression(
                            ImplementTypeExpression(parts[0]),
                            ImplementTypeExpression(parts[1]),
                            2
                    );
                } else if (e.contains("*")) {
                    String[] parts = e.split("\\*", 2);
                    return new ArithmeticExpression(
                            ImplementTypeExpression(parts[0]),
                            ImplementTypeExpression(parts[1]),
                            3
                    );
                } else if (e.contains("/")) {
                    String[] parts = e.split("/", 2);
                    return new ArithmeticExpression(
                            ImplementTypeExpression(parts[0]),
                            ImplementTypeExpression(parts[1]),
                            4
                    );
                }
                throw new ToyLanguageException("Invalid arithmetic expression: " + e);

            }
            case "logical": {
                if (e.contains("and")) {
                    String[] parts = e.split("and", 2);
                    return new LogicalExpression(
                            ImplementTypeExpression(parts[0]),
                            ImplementTypeExpression(parts[1]),
                            1
                    );
                } else if (e.contains("or")) {
                    String[] parts = e.split("or", 2);
                    return new LogicalExpression(
                            ImplementTypeExpression(parts[0]),
                            ImplementTypeExpression(parts[1]),
                            2
                    );
                }
                throw new ToyLanguageException("Invalid logical expression: " + e);
            }
            case "variable": {
                return new VariableExpression(e.trim());
            }
            case "value bool false": {
                return new ValueExpression((new BooleanNumber(false)));
            }
            case "value bool true": {
                return new ValueExpression((new BooleanNumber(true)));
            }
            case "value": {
                int variableIntValue = Integer.parseInt(e.trim());
                return new ValueExpression(new IntegerValue(variableIntValue));
            }
            default:
                throw new ToyLanguageException("Unknown expression type: " + e);
        }
    }

    public Statement ImplementStatementType(String s) throws ToyLanguageException {
        String type = typeStatement(s);
        switch (type) {
            case "openrfile": {
                String exprStr = s.substring(s.indexOf('(')+1, s.lastIndexOf(')')).trim();
                Expression expr = ImplementTypeExpression(exprStr);
                return new OpenReadFileStatement(expr);
            }
            case "readfile": {
                // syntax: readFile(exp, varName)
                String inside = s.substring(s.indexOf('(')+1, s.lastIndexOf(')')).trim();
                String[] parts = inside.split(",", 2);
                Expression expr = ImplementTypeExpression(parts[0].trim());
                String varName = parts[1].trim();
                return new ReadFileStatement(expr, varName);
            }
            case "closerfile": {
                String exprStr = s.substring(s.indexOf('(')+1, s.lastIndexOf(')')).trim();
                Expression expr = ImplementTypeExpression(exprStr);
                return new CloseReadFileStatement(expr);
            }
            case "declaration":{
                String[] parts = s.trim().split(" ");
                String variableType = parts[0].trim();
                String variableName = parts[1].trim();
                if(variableType.equals("int"))
                    return new VariableDeclarationStatement(variableName, new IntType());
                else if(variableType.equals("bool"))
                    return new VariableDeclarationStatement(variableName, new BoolType());
                throw new ToyLanguageException("Invalid type: " + variableType);
            }
            case "assignment": {
                String[] parts = s.split("=");
                String variableName = parts[0].trim();
                String variableValue = parts[1].trim().toLowerCase();
                return new AssignmentStatement(variableName, ImplementTypeExpression(variableValue));
            }
            case "if":{
                s = s.trim();
                int thenIndex = s.toLowerCase().indexOf("then");
                int elseIndex = s.toLowerCase().indexOf("else");

                String expressionString = s.substring(2, thenIndex).trim();
                String statementThenString = s.substring(thenIndex + 4, elseIndex).trim();
                String statementElseString = s.substring(elseIndex + 4).trim();

                Statement statementThen = ImplementStatementType(statementThenString);
                Statement statementElse = ImplementStatementType(statementElseString);
                Expression expression = ImplementTypeExpression(expressionString);

                return new IfStatement(expression, statementThen, statementElse);
            }
            case "print":
            {
                s = s.trim().toLowerCase();
                String expressionString = s.substring(s.indexOf('(') + 1, s.lastIndexOf(')')).trim();
                Expression expression = ImplementTypeExpression(expressionString);
                return new PrintStatement(expression);
            }
            case "":{
                return new NoOperationStatement();
            }
            default:
                throw new ToyLanguageException("Unknown type of statement: " + s);
        }
    }
    @Override
    public Statement createStatement(String command) throws ToyLanguageException {
        String[] statements = command.split(";");
        Statement tempStatement;
        Statement resultStatement = null;
        for(String s: statements){
            tempStatement = ImplementStatementType(s);
            if(resultStatement == null)
                resultStatement = tempStatement;
            else
                resultStatement = new CompoundStatement(resultStatement, tempStatement);
        }
        return resultStatement;
    }

//    @Override
//    public void start() throws ToyLanguageException {
//
//        Scanner scanner = new Scanner(System.in);
//        System.out.print("Enter log file name: ");
//        String logFile = scanner.nextLine();
//
//        RepositoryInterface repo = new Repository(logFile);
//        ProgramState programState = new ProgramState(
//                new ExecutionStack<>(),
//                new SymbolTable<>(),
//                new Output<>(),
//                new NoOperationStatement()
//        );
//
//        repo.addProgramState(programState);
//        Controller controller = new Controller(repo);
//        Statement firstStmt = createStatement("int v; v=2; Print(v)");
//        ProgramState programSt = new ProgramState(
//                new ExecutionStack<>(),
//                new SymbolTable<>(),
//                new Output<>(),
//                firstStmt
//        );
//
//        execute("int a; a=2+3*5; int b; b=a-4/2+7; Print(b)", controller, programSt);
//        execute("bool a; a=false; int v; If a Then v=2 Else v=3; Print(v)", controller, programSt);
//
////        Scanner scanner = new Scanner(System.in);
////        System.out.print("Enter log file name: ");
////        String logFile = scanner.nextLine();
////        createStatement("int v; v=2; Print(v)", logFile);
////        Scanner scanner2 = new Scanner(System.in);
////        System.out.print("Enter log file name: ");
////        String logFile2 = scanner2.nextLine();
////        createStatement("int a; a=2+3*5; int b; b=a-4/2+7; Print(b)", logFile2);
////        Scanner scanner3 = new Scanner(System.in);
////        System.out.print("Enter log file name: ");
////        String logFile3 = scanner3.nextLine();
////        createStatement("bool a; a=false; int v; If a Then v=2 Else v=3; Print(v)", logFile3);
//    }
//@Override
//public void start() throws ToyLanguageException {
//    Scanner scanner = new Scanner(System.in);
//
//    // Array of program commands
//    String[] programs = {
//            "int v; v=2; Print(v)",
//            "int a; a=2+3*5; int b; b=a-4/2+7; Print(b)",
//            "bool a; a=false; int v; If a Then v=2 Else v=3; Print(v)"
//    };
//
//
//    for (int i = 0; i < programs.length; i++) {
//        System.out.printf("Enter log file name for program %d: ", i + 1);
//        String logFile = scanner.nextLine();
//
//        Statement stmt = createStatement(programs[i]);
//
//        RepositoryInterface repo = new Repository(logFile);
//        ProgramState state = new ProgramState(
//                new ExecutionStack<>(),
//                new SymbolTable<>(),
//                new Output<>(),
//                stmt
//        );
//        repo.addProgramState(state);
//        Controller controller = new Controller(repo);
//
//        try {
//            controller.allSteps();
//            System.out.println("Final Program State:\n" + state);
//            System.out.println("Finished program " + (i + 1) + "\n----------------------------------------------------");
//        } catch (IOException | ToyLanguageException e) {
//            System.out.println("Error in program " + (i + 1) + ": " + e.getMessage());
//        }
//    }
//}

    public void execute(String statement, Controller controller, ProgramState state){
        Statement stmt = createStatement(statement); // build a single compound statement
        state.getStack().push(stmt); // push into the same program state
        try {
            controller.allSteps(); // will log to the same file
        } catch (IOException | ToyLanguageException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
