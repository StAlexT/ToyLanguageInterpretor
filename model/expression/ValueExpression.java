package model.expression;

import model.exception.ToyLanguageException;
import model.state.SymbolTableInterface;
import model.state.HeapRefInterface;
import model.type.Type;
import model.value.Value;

public class ValueExpression implements Expression{
    Value value;

    public ValueExpression(Value val){
        value = val;
    }

    @Override
    public Value eval(SymbolTableInterface<String, Value> table, HeapRefInterface<Integer, Value> heap) throws ToyLanguageException {
        return value;
    }

    @Override
    public Type typeCheck(SymbolTableInterface<String, Type> typeEnv) throws ToyLanguageException {
        return value.getType();
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
