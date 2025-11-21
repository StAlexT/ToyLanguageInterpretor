package model.expression;

import model.exception.ToyLanguageException;
import model.state.SymbolTableInterface;
import model.value.Value;

public class ValueExpression implements Expression{
    Value value;

    public ValueExpression(Value val){
        value = val;
    }

    @Override
    public Value eval(SymbolTableInterface<String, Value> table) throws ToyLanguageException {
        return value;
    }

    @Override
    public String toString(){
        return value.toString();
    }

    @Override
    public Expression deepCopy() {
        return new ValueExpression(value.deepCopy());
    }
}
