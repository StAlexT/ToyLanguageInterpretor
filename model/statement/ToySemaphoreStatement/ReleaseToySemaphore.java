package model.statement.ToySemaphoreStatement;

import javafx.util.Pair;
import model.exception.ToyLanguageException;
import model.state.ProgramState;
import model.state.SymbolTableInterface;
import model.state.ToySemaphore;
import model.statement.Statement;
import model.type.Type;
import model.value.IntegerValue;
import model.value.Value;

import java.util.List;

public class ReleaseToySemaphore implements Statement {
    String variable;

    public ReleaseToySemaphore(String variable) {
        this.variable = variable;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        SymbolTableInterface<String, Value> symbolTable = state.getSymbolTable();
        ToySemaphore toySemaphore = state.getToySemaphoreTable();

        if (!symbolTable.isDefined(variable))
            throw new ToyLanguageException("Not in the table - ReleaseToySem");

        Value foundIndexVal = symbolTable.getValue(variable);
        IntegerValue founfIndexIntVal = (IntegerValue) foundIndexVal;
        Integer foundIndex = founfIndexIntVal.getVal();

        toySemaphore.lock();
        try {
            if(!toySemaphore.isDefined(foundIndex))
                throw new ToyLanguageException("Not in the semaphore table - ReleaseToySem");
            Pair<Integer, Pair<List<Integer>, Integer>> pair = toySemaphore.getValue(foundIndex);
            Pair<List<Integer>,Integer> list_integer_pair = pair.getValue();
            List<Integer> list  = list_integer_pair.getKey();

            list.remove((Integer) state.getId());
            toySemaphore.update(foundIndex, pair);
            return state;
        } finally {
            toySemaphore.unlock();
        }
    }
    @Override
    public SymbolTableInterface<String, Type> typeCheck(SymbolTableInterface<String, Type> typeEnv) throws ToyLanguageException {
        return typeEnv;
    }

    @Override
    public Statement deepCopy() {
        return new ReleaseToySemaphore(variable);
    }

    @Override
    public String toString(){
        return "release(" + variable +")";
    }
}
