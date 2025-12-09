package model.statement;

import model.expression.Expression;
import model.state.ProgramState;
import model.value.Value;

public class PrintStatement implements Statement{
    Expression expression;

    public PrintStatement(Expression exp){
        expression = exp;
    }

    public String toString(){
        return "print("+expression.toString()+")";
    }

    @Override
    public ProgramState execute(ProgramState state) {
        Value value = expression.eval(state.getSymbolTable(), state.getHeap());
        state.getOutput().add(value);
        return state;
    }

    @Override
    public Statement deepCopy() {
        return new PrintStatement(expression.deepCopy());
    }
}
