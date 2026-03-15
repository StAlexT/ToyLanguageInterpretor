package model.statement.Procedures;
import model.exception.ToyLanguageException;
import model.state.ProgramState;
import model.state.SymbolTableInterface;
import model.statement.Statement;

public class ReturnStatement implements Statement {

    @Override
    public ProgramState execute(ProgramState state) throws ToyLanguageException {
        // Pop the top SymTable to restore caller's scope
        state.popSymTable();
        return state;
    }

    @Override
    public Statement deepCopy() {
        return new ReturnStatement();
    }

    @Override
    public String toString() {
        return "return";
    }

    @Override
    public SymbolTableInterface<String, model.type.Type> typeCheck(SymbolTableInterface<String, model.type.Type> typeEnv) {
        return typeEnv;
    }
}
