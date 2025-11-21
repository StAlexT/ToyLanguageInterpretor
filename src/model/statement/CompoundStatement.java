package model.statement;
import model.state.ExecutionStackInterface;
import model.state.ProgramState;

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

    public Statement deepCopy(){
        return new CompoundStatement(first.deepCopy(), second.deepCopy());
    }
}
