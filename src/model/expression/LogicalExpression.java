package model.expression;

import model.exception.ToyLanguageException;
import model.state.SymbolTableInterface;
import model.state.HeapRefInterface;
import model.type.BoolType;
import model.type.Type;
import model.value.BooleanNumber;
import model.value.Value;

public class LogicalExpression implements Expression{
    Expression expression1;
    Expression expression2;
    int operator;

    public LogicalExpression(Expression exp1, Expression exp2, int op){
        expression1 = exp1;
        expression2 = exp2;
        operator = op;
    }

    @Override
    public Value eval(SymbolTableInterface<String, Value> table, HeapRefInterface<Integer, Value> heap) throws ToyLanguageException {
        Value val1, val2;
        val1 = expression1.eval(table, heap);
        if(val1.getType().equals(new BoolType())){
            val2 = expression2.eval(table, heap);
            if (val2.getType().equals(new BoolType())){
                BooleanNumber booleanNumber1 = (BooleanNumber) val1;
                BooleanNumber booleanNumber2 = (BooleanNumber) val2;
                boolean nr1,  nr2;
                nr1 = booleanNumber1.getVal();
                nr2 = booleanNumber2.getVal();
                if(operator == 1)
                    return new BooleanNumber(nr1 && nr2);
                if(operator == 2)
                    return new BooleanNumber(nr1 || nr2);
                else
                    throw new ToyLanguageException("Not valid operand");
            }
            else
                throw new ToyLanguageException("second operand is not boolean");
        }
        else
            throw new ToyLanguageException("first operand is not boolean");
    }

    @Override
    public Type typeCheck(SymbolTableInterface<String, Type> typeEnv) throws ToyLanguageException {
        Type type1, type2;
        type1 = expression1.typeCheck(typeEnv);
        type2 = expression2.typeCheck(typeEnv);
        if(type1.equals(new BoolType()))
            if (type2.equals(new BoolType()))
                return new BoolType();
            else
                throw new ToyLanguageException("second operand is not boolean");
        else
            throw new ToyLanguageException("first operand is not boolean");
    }

    public String toString() {
        return "(" + expression1.toString() + " " + getOperatorSymbol() + " " + expression2.toString() + ")";
    }

    private String getOperatorSymbol() {
        switch(operator) {
            case 1: return "and";
            case 2: return "or";
            default: return "?";
        }
    }

    @Override
    public Expression deepCopy() {
        return new LogicalExpression(expression1.deepCopy(), expression2.deepCopy(), operator);
    }
}
