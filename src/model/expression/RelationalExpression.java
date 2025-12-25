package model.expression;

import model.exception.ToyLanguageException;
import model.state.SymbolTableInterface;
import model.state.HeapRefInterface;
import model.type.BoolType;
import model.type.IntType;
import model.type.Type;
import model.value.BooleanNumber;
import model.value.IntegerValue;
import model.value.Value;

public class RelationalExpression implements Expression {
    private final Expression e1, e2;
    private final String operator;

    public RelationalExpression(Expression e1, Expression e2, String operator) {
        this.e1 = e1;
        this.e2 = e2;
        this.operator = operator;
    }

    @Override
    public Value eval(SymbolTableInterface<String, Value> table, HeapRefInterface<Integer, Value> heap) throws ToyLanguageException {
        Value v1 = e1.eval(table, heap);
        Value v2 = e2.eval(table, heap);

        if (!(v1.getType() instanceof IntType) || !(v2.getType() instanceof IntType))
            throw new ToyLanguageException("Both operands must be integers");

        int n1 = ((IntegerValue) v1).getVal();
        int n2 = ((IntegerValue) v2).getVal();

        return switch (operator) {
            case "<" -> new BooleanNumber(n1 < n2);
            case "<=" -> new BooleanNumber(n1 <= n2);
            case "==" -> new BooleanNumber(n1 == n2);
            case "!=" -> new BooleanNumber(n1 != n2);
            case ">" -> new BooleanNumber(n1 > n2);
            case ">=" -> new BooleanNumber(n1 >= n2);
            default -> throw new ToyLanguageException("Invalid relational operator: " + operator);
        };
    }

    @Override
    public Type typeCheck(SymbolTableInterface<String, Type> typeEnv) throws ToyLanguageException {
        Type type1, type2;
        type1 = e1.typeCheck(typeEnv);
        type2 = e2.typeCheck(typeEnv);
        if(type1.equals(new IntType()))
            if (type2.equals(new IntType()))
                return new IntType();
            else
                throw new ToyLanguageException("second operand not integer");
        else
            throw new ToyLanguageException("first operand not integer");
    }

    @Override
    public Expression deepCopy() {
        return new RelationalExpression(e1.deepCopy(), e2.deepCopy(), operator);
    }

    public String toString() {
        return e1.toString() + " " + operator + " " + e2.toString();
    }
}
