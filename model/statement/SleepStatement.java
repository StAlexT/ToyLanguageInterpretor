package model.statement;

import model.exception.ToyLanguageException;
import model.expression.Expression;
import model.expression.ValueExpression;
import model.state.ProgramState;
import model.state.SymbolTableInterface;
import model.type.IntType;
import model.type.Type;
import model.value.IntegerValue;
import model.value.Value;

public class SleepStatement implements Statement{
    Expression expression;

    public SleepStatement(Expression expression){
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        Value value = expression.eval(state.getSymbolTable(), state.getHeap());
        if(!value.getType().equals(new IntType()))
            throw new ToyLanguageException("No int  - sleep");
        IntegerValue nr = (IntegerValue) value;
        int number = nr.getVal() - 1;
        Statement sleepStatemetn = new SleepStatement(new ValueExpression(new IntegerValue(number)));
        if (nr.getVal() <= 0)
            return null;
        state.getStack().push(sleepStatemetn);
        return state;
    }

    @Override
    public SymbolTableInterface<String, Type> typeCheck(SymbolTableInterface<String, Type> typeEnv) throws ToyLanguageException {
        expression.typeCheck(typeEnv);
        return typeEnv;
    }

    @Override
    public Statement deepCopy() {
        return new SleepStatement(expression.deepCopy());
    }

    @Override
    public String toString(){
        return "sleep(" + expression.toString() + ")";
    }
}
