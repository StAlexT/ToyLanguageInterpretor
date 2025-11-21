package model.expression;

import model.exception.ToyLanguageException;
import model.state.SymbolTableInterface;
import model.value.Value;

public interface Expression {
    Value eval(SymbolTableInterface<String, Value> table) throws ToyLanguageException;
    Expression deepCopy();
}
