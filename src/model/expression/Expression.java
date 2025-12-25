package model.expression;

import model.exception.ToyLanguageException;
import model.state.SymbolTableInterface;
import model.state.HeapRefInterface;
import model.type.Type;
import model.value.Value;

public interface Expression {
    Value eval(SymbolTableInterface<String, Value> table, HeapRefInterface<Integer, Value> heap) throws ToyLanguageException;
    Type typeCheck(SymbolTableInterface<String, Type> typeEnv) throws ToyLanguageException;
    Expression deepCopy();
}
