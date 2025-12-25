package model.statement;

import model.exception.ToyLanguageException;
import model.state.ProgramState;
import model.state.SymbolTableInterface;
import model.type.Type;

public interface Statement {
    ProgramState execute(ProgramState state);
    SymbolTableInterface<String, Type> typeCheck(SymbolTableInterface<String, Type> typeEnv) throws ToyLanguageException;
    Statement deepCopy();
}
