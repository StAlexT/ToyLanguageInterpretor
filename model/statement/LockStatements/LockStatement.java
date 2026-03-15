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

public class LockStatement implements Statement {

    String variable;

    public LockStatement(String variable) {
        this.variable = variable;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        SymbolTableInterface<String, Value> symbolTable = state.getSymbolTable();
        if (!state.getSymbolTable().isDefined(variable))
            throw new ToyLanguageException("Not in symbolTable - LockStatement");
        Value foundIndex = symbolTable.getValue(variable);
        IntegerValue indexFound = (IntegerValue) foundIndex;
        LockInterface<Integer, Integer> lockTable = state.getLockTable();
        lockTable.lock();
        try {
            if (!lockTable.isDefined(indexFound.getVal()))
                throw new ToyLanguageException("Not in LockTable - LockStatement");
            else if (lockTable.getValue(indexFound.getVal()).equals(-1)) {
                lockTable.update(indexFound.getVal(), state.getId());
            } else {
                state.getStack().push(this);
            }
            return state;
        }
        finally {
            lockTable.unlock();
        }

    }

    @Override
    public SymbolTableInterface<String, Type> typeCheck(SymbolTableInterface<String, Type> typeEnv) throws ToyLanguageException {
        Type typeVar = typeEnv.getValue(variable);
        if(typeVar.equals(new IntType()))
            return typeEnv;
        else
            throw new ToyLanguageException("Not int typeCheck-  Lock Statement");
    }
    @Override

    public Statement deepCopy() {
        return new LockStatement(variable);
    }
    @Override
    public String toString(){
        return "lock(" + variable +")";
    }
}
