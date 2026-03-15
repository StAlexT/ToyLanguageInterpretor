package model.expression;

import model.exception.ToyLanguageException;
import model.state.HeapRefInterface;
import model.state.SymbolTableInterface;
import model.type.BoolType;
import model.type.Type;
import model.value.BooleanNumber;
import model.value.Value;

public class NotExpression implements Expression {
    private final Expression exp;

    public NotExpression(Expression exp) {
        this.exp = exp;
    }

    @Override
    public Value eval(SymbolTableInterface<String, Value> table, HeapRefInterface<Integer, Value> heap) throws ToyLanguageException {
        BooleanNumber v = (BooleanNumber) exp.eval(table, heap);
        return new BooleanNumber(!v.getVal());
    }

    @Override
    public Type typeCheck(SymbolTableInterface<String, Type> env) throws ToyLanguageException {
        if (!exp.typeCheck(env).equals(new BoolType()))
            throw new ToyLanguageException("NOT operand is not boolean");
        return new BoolType();
    }

    @Override
    public Expression deepCopy() {
        return new NotExpression(exp.deepCopy());
    }
}
