package model.expression;

import model.exception.ToyLanguageException;
import model.state.HeapRefInterface;
import model.state.SymbolTableInterface;
import model.type.IntType;
import model.type.Type;
import model.value.IntegerValue;
import model.value.Value;

public class MulExpression implements Expression {
    private final Expression exp1;
    private final Expression exp2;

    public MulExpression(Expression exp1, Expression exp2) {
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    @Override
    public Value eval(SymbolTableInterface<String, Value> table, HeapRefInterface<Integer, Value> heap) throws ToyLanguageException {
        Value v1 = exp1.eval(table, heap);
        Value v2 = exp2.eval(table, heap);

        if (!(v1 instanceof IntegerValue) || !(v2 instanceof IntegerValue))
            throw new ToyLanguageException("MUL: Both operands must be integers");

        int n1 = ((IntegerValue) v1).getVal();
        int n2 = ((IntegerValue) v2).getVal();
        int result = (n1 * n2) - (n1 + n2);
        return new IntegerValue(result);
    }

    @Override
    public Type typeCheck(SymbolTableInterface<String, Type> typeEnv) throws ToyLanguageException {
        Type t1 = exp1.typeCheck(typeEnv);
        Type t2 = exp2.typeCheck(typeEnv);

        if (!t1.equals(new IntType()) || !t2.equals(new IntType()))
            throw new ToyLanguageException("MUL: Both operands must be integers");
        return new IntType();
    }

    @Override
    public Expression deepCopy() {
        return new MulExpression(exp1.deepCopy(), exp2.deepCopy());
    }

    @Override
    public String toString() {
        return "MUL(" + exp1 + "," + exp2 + ")";
    }
}
