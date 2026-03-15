package model.state;

import javafx.util.Pair;
import model.exception.ToyLanguageException;
import model.state.files.FileTable;
import model.state.files.FileTableInterface;
import model.statement.Statement;
import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;
import java.util.Deque;
import java.util.List;
import java.util.Stack;

public class ProgramState {
    ExecutionStackInterface<Statement> executionStack;
    SymbolTableInterface<String, Value> symbolTable;
    OutputInterface<Value> output;
    Statement originalProgram;
    FileTableInterface<StringValue, BufferedReader> fileTable;
    HeapRefInterface<Integer, Value> refTable;
    CountSemaphoreInterface<Integer, Integer, Integer> semaphoreTable;
    LockInterface<Integer, Integer> lockTable;
    Stack<SymbolTableInterface<String, Value>> symbolTableStack;
    ProcTableInterface procTable;
    CyclicBarrier cyclicBarrierTable;
    ToySemaphore toySemaphoreTable;
    private static int lastId = 0;
    private final int id;
    public ProgramState(ExecutionStackInterface<Statement> exeStack, SymbolTableInterface<String, Value> symTable,
                        OutputInterface<Value> out, FileTableInterface<StringValue, BufferedReader> theFileTable,
                        HeapRefInterface<Integer, Value> refTable,
                        CountSemaphoreInterface<Integer, Integer, Integer> semaphoreTable,
                        LockInterface<Integer, Integer> lockTable, ProcTableInterface procTable,
                        CyclicBarrier cyclicBarrierTable,
                        ToySemaphore toySemaphoreTable,
                        Statement originalProg){
        executionStack = exeStack;
        symbolTable = symTable;
        this.output = out;
        originalProgram = originalProg.deepCopy();
        this.id = getNewId();
        exeStack.push(originalProg); //pushes so stack not empty anymore
        this.fileTable = theFileTable;
        this.refTable = refTable;

        this.semaphoreTable = semaphoreTable;
        this.lockTable = lockTable;
        this.symbolTableStack = new Stack<>();
        this.symbolTableStack.push(symTable);
        this.procTable = procTable;
        this.cyclicBarrierTable = cyclicBarrierTable;
        this.toySemaphoreTable = toySemaphoreTable;
    }

    private static synchronized int getNewId(){
        return ++lastId;
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
    };

    public HeapRefInterface<Integer, Value> getHeap() { return refTable;};

    public CountSemaphoreInterface<Integer, Integer, Integer> getSemaphoreTable(){return semaphoreTable;};

    public LockInterface<Integer, Integer> getLockTable() {
        return lockTable;
    }

    public final int getId(){return id;};

    public void setExecutionStack(ExecutionStackInterface<Statement> stack){
        this.executionStack = stack;
    }

    public void setSymbolTable(SymbolTableInterface<String, Value> symbTable){
        this.symbolTable = symbTable;
    }

    public void setOutput(OutputInterface<Value> out){
        this.output = out;
    }

    public boolean isNotCompleted(){
        return !executionStack.isEmpty();
    }

    // Top of stack is the current symbol table
    public SymbolTableInterface<String, Value> getCurrentSymTable() {
        return symbolTableStack.peek();
    }

    // Push a new symbol table (e.g., for new scope)
    public void pushSymTable(SymbolTableInterface<String, Value> symTable) {
        symbolTableStack.push(symTable);
    }

    // Pop the top symbol table
    public SymbolTableInterface<String, Value> popSymTable() {
        return symbolTableStack.pop();
    }

    // Clone the entire stack for fork
    public Stack<SymbolTableInterface<String, Value>> cloneSymTableStack() {
        Stack<SymbolTableInterface<String, Value>> clone = new Stack<>();
        for (SymbolTableInterface<String, Value> symTable : symbolTableStack) {
            clone.add(symTable.deepCopy()); // each SymTable must support deepCopy()
        }
        return clone;
    }


    public ProcTableInterface getProcTable() {
        return procTable;
    }

    public void setProcTable(ProcTableInterface procTable) {
        this.procTable = procTable;
    }

    public CyclicBarrier getCyclicBarrierTable(){
        return cyclicBarrierTable;
    }

    public ToySemaphore getToySemaphoreTable() {
        return toySemaphoreTable;
    }

    public ProgramState oneStep() throws ToyLanguageException {
        if(executionStack.isEmpty()) throw new ToyLanguageException("Empty Stack");
        Statement currentStatement = executionStack.pop();
        return currentStatement.execute(this);
    }

    @Override
    public String toString(){
        return  executionStack.toString() + "\n" +
//                 symbolTable.toString() + "\n" +

                 output.toString() + "\n" +
                refTable.toString() + "\n" +
                semaphoreTable.toString() +"\n" +
                lockTable.toString()+"\n"+
                symbolTableStack.toString() +"\n"+
                "id:#"+ id + "\n";
    }


}
