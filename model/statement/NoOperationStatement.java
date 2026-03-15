package model.statement;

import model.exception.ToyLanguageException;
import model.state.ProgramState;
import model.state.SymbolTableInterface;
import model.type.Type;

public class NoOperationStatement implements Statement{

    @Override
    public String toString(){
        return "no operation statement";
    }

    @Override
    public ProgramState execute(ProgramState state) {
        return null;
        //return state;
    }

    @Override
    public SymbolTableInterface<String, Type> typeCheck(SymbolTableInterface<String, Type> typeEnv) throws ToyLanguageException {
        return typeEnv;
    }

    @Override//idk if it needs this or toString
    public Statement deepCopy() {//??
        return new NoOperationStatement();
    }
}
