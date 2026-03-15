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

public class UnlockStatement implements Statement {
    String variable;

    public UnlockStatement(String variable) {
        this.variable = variable;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        SymbolTableInterface<String, Value> symbolTable = state.getSymbolTable();
        LockInterface<Integer, Integer> lockTable = state.getLockTable();

        if (!symbolTable.isDefined(variable))
            throw new ToyLanguageException("Not in SymTable - Unlock Statement");

        Value foundIndex = symbolTable.getValue(variable);
        IntegerValue indexFound = (IntegerValue) foundIndex;

        lockTable.lock();
        try {
            // If the lock exists and owned by thread, unlock
            if (lockTable.isDefined(indexFound.getVal()) &&
                    lockTable.getValue(indexFound.getVal()).equals(state.getId())) {
                lockTable.update(indexFound.getVal(), -1);
            }
            // Always continue execution
            return state;
        } finally {
            lockTable.unlock();
        }
    }

    @Override
    public SymbolTableInterface<String, Type> typeCheck(SymbolTableInterface<String, Type> typeEnv) throws ToyLanguageException {
        Type typeVar = typeEnv.getValue(variable);
        if(typeVar.equals(new IntType()))
            return typeEnv;
        else
            throw new ToyLanguageException("Not int typeCheck-  Unlock Statement");
    }

    @Override
    public Statement deepCopy() {
        return new UnlockStatement(variable);
    }

    @Override
    public String toString(){
        return "unlock(" + variable +")";
    }
}
