package model.statement;

import model.exception.ToyLanguageException;
import model.state.ExecutionStack;
import model.state.ProgramState;
import model.state.SymbolTableInterface;
import model.type.Type;

public class ForkStatement implements Statement{
    Statement statement;

    public ForkStatement(Statement statement){
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        return new ProgramState(
                new ExecutionStack<>(),
                state.getSymbolTable().deepCopy(),
//                state.getCurrentSymTable().deepCopy(),
                state.getOutput(),
                state.getFileTable(),
                state.getHeap(),
                state.getSemaphoreTable(),
                state.getLockTable(),
                state.getProcTable(),
                state.getCyclicBarrierTable(),
                state.getToySemaphoreTable(),
                statement
        );
    }

    @Override
    public SymbolTableInterface<String, Type> typeCheck(SymbolTableInterface<String, Type> typeEnv) throws ToyLanguageException {
        statement.typeCheck(typeEnv.deepCopy());
        return typeEnv;
    }

    @Override
    public Statement deepCopy() {
        return new ForkStatement(statement.deepCopy());
    }

    @Override
    public String toString() {
        return "fork(" + statement + ")";
    }
}
