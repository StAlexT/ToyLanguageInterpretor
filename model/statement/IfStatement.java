package model.statement;

import model.exception.ToyLanguageException;
import model.expression.Expression;
import model.state.ProgramState;
import model.state.SymbolTable;
import model.state.SymbolTableInterface;
import model.type.BoolType;
import model.type.Type;
import model.value.BooleanNumber;
import model.value.Value;

import java.util.Hashtable;
import java.util.Map;

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
        return null;
        //return state;
    }

    private SymbolTableInterface<String, Type> clone(SymbolTableInterface<String, Type> original) {
        return original.deepCopy();
    }

    @Override
    public SymbolTableInterface<String, Type> typeCheck(SymbolTableInterface<String, Type> typeEnv) throws ToyLanguageException {
        Type typeExp = expression.typeCheck(typeEnv);
        if(typeExp.equals(new BoolType())){
            elseStatement.typeCheck(clone(typeEnv));
            thenStatement.typeCheck(clone(typeEnv));
            return typeEnv;
        }
        else
            throw new ToyLanguageException("The cond of IF is not type bool");
    }

    public Statement deepCopy(){
        return new IfStatement(expression.deepCopy(), thenStatement.deepCopy(), elseStatement.deepCopy());
    }
}
