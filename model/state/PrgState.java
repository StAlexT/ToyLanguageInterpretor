//package model.state;
//
//import javafx.util.Pair;
//import model.exception.ToyLanguageException;
//import model.state.files.FileTable;
//import model.state.files.FileTableInterface;
//import model.statement.Statement;
//import model.value.StringValue;
//import model.value.Value;
//
//import java.io.BufferedReader;
//import java.util.List;
//
//public class ProgramState {
//    ExecutionStackInterface<Statement> executionStack;
//    SymbolTableInterface<String, Value> symbolTable;
//    OutputInterface<Value> output;
//    Statement originalProgram;
//    FileTableInterface<StringValue, BufferedReader> fileTable;
//    HeapRefInterface<Integer, Value> refTable;
//    private static int lastId = 0;
//    private final int id;
//    public ProgramState(ExecutionStackInterface<Statement> exeStack, SymbolTableInterface<String, Value> symTable,
//                        OutputInterface<Value> out, FileTableInterface<StringValue, BufferedReader> theFileTable,
//                        HeapRefInterface<Integer, Value> refTable,
//                        Statement originalProg){
//        executionStack = exeStack;
//        symbolTable = symTable;
//        this.output = out;
//        originalProgram = originalProg.deepCopy();
//        this.id = getNewId();
//        exeStack.push(originalProg); //pushes so stack not empty anymore
//        this.fileTable = theFileTable;
//        this.refTable = refTable;
//    }
//
//    private static synchronized int getNewId(){
//        return ++lastId;
//    }
//
//    public FileTableInterface<StringValue, BufferedReader> getFileTable() {
//        return fileTable;
//    }
//
//    public ExecutionStackInterface<Statement> getStack(){
//        return executionStack;
//    };
//
//    public SymbolTableInterface<String, Value> getSymbolTable(){
//        return symbolTable;
//    };
//
//    public OutputInterface<Value> getOutput(){
//        return output;
//    };
//
//    public HeapRefInterface<Integer, Value> getHeap() { return refTable;};
//
//    public final int getId(){return id;};
//
//    public void setExecutionStack(ExecutionStackInterface<Statement> stack){
//        this.executionStack = stack;
//    }
//
//    public void setSymbolTable(SymbolTableInterface<String, Value> symbTable){
//        this.symbolTable = symbTable;
//    }
//
//    public void setOutput(OutputInterface<Value> out){
//        this.output = out;
//    }
//
//    public boolean isNotCompleted(){
//        return !executionStack.isEmpty();
//    }
//
//    public ProgramState oneStep() throws ToyLanguageException {
//        if(executionStack.isEmpty()) throw new ToyLanguageException("Empty Stack");
//        Statement currentStatement = executionStack.pop();
//        return currentStatement.execute(this);
//    }
//
//    @Override
//    public String toString(){
//        return  executionStack.toString() + "\n" +
//                symbolTable.toString() + "\n" +
//                output.toString() + "\n" +
//                refTable.toString() + "\n" +
//                semaphoreTable.toString() +"\n" +
//                "id:#"+ id + "\n";
//    }
//
//}
