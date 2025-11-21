package model.state;
import model.state.files.FileTable;
import model.state.files.FileTableInterface;
import model.statement.Statement;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;

public class ProgramState {
    ExecutionStackInterface<Statement> executionStack;
    SymbolTableInterface<String, Value> symbolTable;
    OutputInterface<Value> output;
    Statement originalProgram;
    FileTableInterface<StringValue, BufferedReader> fileTable;

    public ProgramState(ExecutionStackInterface<Statement> exeStack, SymbolTableInterface<String, Value> symTable, OutputInterface<Value> out, FileTable<StringValue, BufferedReader> theFileTable, Statement originalProg){
        executionStack = exeStack;
        symbolTable = symTable;
        this.output = out;
        originalProgram = originalProg.deepCopy();
        exeStack.push(originalProg); //pushes so stack not empty anymore
        this.fileTable = theFileTable;
    }

    public FileTableInterface<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public ExecutionStackInterface<Statement> getStack(){
        return executionStack;
    };

    public SymbolTableInterface<String, Value> getSymbolTable(){
        return symbolTable;
    };

    public OutputInterface<Value> getOutput(){
        return output;
    }

    public void setExecutionStack(ExecutionStackInterface<Statement> stack){
        this.executionStack = stack;
    }

    public void setSymbolTable(SymbolTableInterface<String, Value> symbTable){
        this.symbolTable = symbTable;
    }

    public void setOutput(OutputInterface<Value> out){
        this.output = out;
    }

    @Override
    public String toString(){
        return  executionStack.toString() + "\n" +
                 symbolTable.toString() + "\n" +
                 output.toString() + "\n";
    }

}
