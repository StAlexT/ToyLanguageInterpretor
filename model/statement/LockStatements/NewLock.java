package model.statement.LockStatements;

import model.exception.ToyLanguageException;
import model.expression.Expression;
import model.state.LockInterface;
import model.state.ProgramState;
import model.state.SymbolTableInterface;
import model.statement.Statement;
import model.type.IntType;
import model.type.Type;
import model.value.IntegerValue;
import model.value.Value;

public class NewLock implements Statement {

    String variable;

    public NewLock(String variable) {
        this.variable=variable;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        LockInterface<Integer, Integer> lockTable = state.getLockTable();
        lockTable.lock();
        try {
            int newLoc = lockTable.getNextFreeLocation();
            lockTable.add(newLoc, -1);
            SymbolTableInterface<String, Value> symbolTable = state.getSymbolTable();
            if (state.getSymbolTable().isDefined(variable))
                symbolTable.update(variable, new IntegerValue(newLoc));
            else
                symbolTable.add(variable, new IntegerValue(newLoc));
            return state;
        }finally {
            lockTable.unlock();
        }

    }

    @Override
    public SymbolTableInterface<String, Type> typeCheck(SymbolTableInterface<String, Type> typeEnv) throws ToyLanguageException {
        Type typeVar = typeEnv.getValue(variable);
        if(typeVar.equals(new IntType()))
            return typeEnv;
        else
            throw new ToyLanguageException("Not int typeCheck-  NewLock Statement");
    }

    @Override
    public Statement deepCopy() {
        return new NewLock(variable);
    }

    @Override
    public String toString(){
        return "newLock(" + variable +")";
    }
}
