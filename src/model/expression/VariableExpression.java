package model.expression;

import model.exception.ToyLanguageException;
import model.state.SymbolTableInterface;
import model.state.HeapRefInterface;
import model.value.Value;

public class VariableExpression implements Expression{
    String id;

    public VariableExpression(String id_exp){
        id = id_exp;
    }

    @Override
    public Value eval(SymbolTableInterface<String, Value> table, HeapRefInterface<Integer, Value> heap) throws ToyLanguageException {
        if(table.isDefined(id))
            return table.getValue(id);
        else
            throw new ToyLanguageException("Not a valid id!\n");
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public Expression deepCopy() {
        return new VariableExpression(id);
    }
}

