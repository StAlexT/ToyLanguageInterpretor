package model.statement;

import model.exception.ToyLanguageException;
import model.expression.Expression;
import model.state.ProgramState;
import model.type.BoolType;
import model.value.BooleanNumber;
import model.value.Value;

public class IfStatement implements Statement{
    Expression expression;
    Statement thenStatement;
    Statement elseStatement;

    public IfStatement(Expression expr, Statement thenState, Statement elseState){
        expression = expr;
        thenStatement = thenState;
        elseStatement = elseState;
    }
    @Override
    public String toString(){
        return "(IF("+expression.toString()+") THEN("+thenStatement.toString()+")ELSE("+elseStatement.toString()+"))";
    }

    @Override
    public ProgramState execute(ProgramState state) {
        Value cond = expression.eval(state.getSymbolTable(), state.getHeap());
        if (!(cond.getType().equals(new BoolType())))
            throw new ToyLanguageException("Condition not boolean");
        boolean val = ((BooleanNumber) cond).getVal();
        if (val)
            state.getStack().push(thenStatement);
        else
            state.getStack().push(elseStatement);
        return state;

    }
    public Statement deepCopy(){
        return new IfStatement(expression.deepCopy(), thenStatement.deepCopy(), elseStatement.deepCopy());

    }
}
