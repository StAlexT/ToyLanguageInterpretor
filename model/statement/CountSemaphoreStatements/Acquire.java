package model.statement.CountSemaphoreStatements;

import javafx.util.Pair;
import model.exception.ToyLanguageException;
import model.state.*;
import model.statement.Statement;
import model.type.IntType;
import model.type.Type;
import model.value.IntegerValue;
import model.value.Value;

import java.util.List;

public class Acquire implements Statement {
    String variableName;

    public Acquire(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        SymbolTableInterface<String, Value> symbolTable = state.getSymbolTable();
        ExecutionStackInterface<Statement> executionStack = state.getStack();
        CountSemaphoreInterface<Integer, Integer, Integer> semaphoreTable = state.getSemaphoreTable();
        if (!symbolTable.isDefined(variableName))
            throw new ToyLanguageException("Does not exist in table - Acquire");
        Value value = symbolTable.getValue(variableName);
        if (!value.getType().equals(new IntType()))
            throw new ToyLanguageException("Not int  -  Acquire");
        IntegerValue val = (IntegerValue) value;
        semaphoreTable.lock();
        try {
            if (!semaphoreTable.isDefined(val.getVal()))
                throw new ToyLanguageException("Not in semaphore table - Acquire");
            Pair<Integer, List<Integer>> pair = semaphoreTable.getValue(val.getVal());
            List<Integer> L1 = pair.getValue();
            Integer N1 = pair.getKey();
            Integer NL = L1.size();
            if (!L1.contains(state.getId()) && L1.size() < N1) {
                L1.add(state.getId());          // acquire
                semaphoreTable.update(val.getVal(), N1, L1);
                return state;
            } else {
                // Cannot acquire now -> stay on stack but do NOT return null
                executionStack.push(this);
                return state;
            }

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
            throw new ToyLanguageException("Not int typeCheck-  Acquire");
    }

    @Override
    public Statement deepCopy() {
        return new Acquire(variableName);
    }

    @Override
    public String toString(){
        return "acquire(" + variableName.toString() +")";
    }
}

