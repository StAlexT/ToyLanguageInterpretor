package model.statement;
import model.exception.ToyLanguageException;
import model.state.ExecutionStackInterface;
import model.state.ProgramState;
import model.state.SymbolTableInterface;
import model.type.Type;

public class CompoundStatement implements Statement{

    Statement first;
    Statement second;

    public CompoundStatement(Statement f, Statement s){
        this.first = f;
        this.second = s;
    }
    @Override
    public String toString(){
        return "("+first.toString()+";"+second.toString()+")";
    }

    @Override
    public ProgramState execute(ProgramState state) {
        ExecutionStackInterface<Statement> stack = state.getStack();
        stack.push(second);
        stack.push(first);
        return state;
    }
    private SymbolTableInterface<String, Type> clone(SymbolTableInterface<String, Type> original) {
        return original.deepCopy();
    }

    @Override
    public SymbolTableInterface<String, Type> typeCheck(SymbolTableInterface<String, Type> typeEnv) throws ToyLanguageException {
        SymbolTableInterface<String, Type> updatedEnv = first.typeCheck(typeEnv);
        return second.typeCheck(updatedEnv);
    }


    public Statement deepCopy(){
        return new CompoundStatement(first.deepCopy(), second.deepCopy());
    }
}
