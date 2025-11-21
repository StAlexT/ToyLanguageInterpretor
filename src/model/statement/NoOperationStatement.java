package model.statement;

import model.state.ProgramState;

public class NoOperationStatement implements Statement{

    @Override
    public String toString(){
        return "no operation statement";
    }

    @Override
    public ProgramState execute(ProgramState state) {
        return state;
    }

    @Override//idk if it needs this or toString
    public Statement deepCopy() {//??
        return new NoOperationStatement();
    }
}
