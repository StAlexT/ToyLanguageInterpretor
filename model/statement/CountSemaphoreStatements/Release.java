package model.statement.CountSemaphoreStatements;

import javafx.util.Pair;
import model.exception.ToyLanguageException;
import model.state.CountSemaphoreInterface;
import model.state.ExecutionStackInterface;
import model.state.ProgramState;
import model.state.SymbolTableInterface;
import model.statement.Statement;
import model.type.IntType;
import model.type.Type;
import model.value.IntegerValue;
import model.value.Value;

import java.util.List;

public class Release implements Statement {
    String variableName;

    public Release(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        SymbolTableInterface<String, Value> symbolTable = state.getSymbolTable();
        ExecutionStackInterface<Statement> executionStack = state.getStack();
        CountSemaphoreInterface<Integer, Integer, Integer> semaphoreTable = state.getSemaphoreTable();
        if(!symbolTable.isDefined(variableName))
            throw new ToyLanguageException("Does not exist in table - Release");
        Value value = symbolTable.getValue(variableName);
        if (!value.getType().equals(new IntType()))
            throw new ToyLanguageException("Not int  -  Release");
        IntegerValue val = (IntegerValue) value;
        semaphoreTable.lock();
        try {
            if (!semaphoreTable.isDefined(val.getVal()))
                throw new ToyLanguageException("Not in semaphore table - Release");
            Pair<Integer, List<Integer>> pair = semaphoreTable.getValue(val.getVal());
            List<Integer> L1 = pair.getValue();
            Integer N1 = pair.getKey();

            L1.remove((Integer) state.getId());
            semaphoreTable.update(val.getVal(), N1, L1);
            return state;
        } finally {
            semaphoreTable.unlock();
        }


    }

    @Override
    public SymbolTableInterface<String, Type> typeCheck(SymbolTableInterface<String, Type> typeEnv) throws ToyLanguageException {
        Type typeVar = typeEnv.getValue(variableName);
        if(typeVar.equals(new IntType()))
            return typeEnv;
        else
            throw new ToyLanguageException("Not int typeCheck-  Release");
    }

    @Override
    public Statement deepCopy() {
        return new Release(variableName);
    }
    public String toString(){
        return "release(" + variableName.toString() +")";
    }
}
