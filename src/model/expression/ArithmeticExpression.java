package model.expression;

import model.exception.ToyLanguageException;
import model.state.SymbolTableInterface;
import model.state.HeapRefInterface;
import model.type.IntType;
import model.type.Type;
import model.value.IntegerValue;
import model.value.Value;

public class ArithmeticExpression implements Expression{
    Expression exp1;
    Expression exp2;
    int operator;

    public ArithmeticExpression(Expression expr1, Expression expr2, int op){
        exp1 = expr1;
        exp2 = expr2;
        operator = op;
    }

    @Override
    public Value eval(SymbolTableInterface<String, Value> table, HeapRefInterface<Integer, Value> heap) throws ToyLanguageException {
        Value val1, val2;
        val1 = exp1.eval(table, heap);
        if (val1.getType().equals(new IntType())) {
            val2 = exp2.eval(table, heap);
            if (val2.getType().equals(new IntType())) {
                IntegerValue integerValue1 = (IntegerValue) val1;
                IntegerValue integerValue2 = (IntegerValue) val2;
                int nr1, nr2;
                nr1 = integerValue1.getVal();
                nr2 = integerValue2.getVal();
                if (operator == 1)
                    return new IntegerValue(nr1 + nr2);
                if (operator == 2)
                    return new IntegerValue(nr1 - nr2);
                if (operator == 3)
                    return new IntegerValue(nr1 * nr2);
                if (operator == 4)
                    if (nr2 == 0)
                        throw new ToyLanguageException("division by 0");
                    else
                        return new IntegerValue(nr1 / nr2);
                else
                    throw new ToyLanguageException("Not valid operand");
            } else
                throw new ToyLanguageException("second operand is not an integer");
        }
        else
            throw new ToyLanguageException("first operand is not an integer");
    }

    @Override
    public Type typeCheck(SymbolTableInterface<String, Type> typeEnv) throws ToyLanguageException {
        Type type1, type2;
        type1 = exp1.typeCheck(typeEnv);
        type2 = exp2.typeCheck(typeEnv);
        if(type1.equals(new IntType()))
            if (type2.equals(new IntType()))
                return new IntType();
            else {
                throw new ToyLanguageException("second operand is not an integer");
            }
        else
            throw new ToyLanguageException("first operand is not an integer");
    }

    @Override
    public Expression deepCopy() {
        return new ArithmeticExpression(exp1.deepCopy(), exp2.deepCopy(), operator);
    }

    @Override
    public String toString() {
        return "(" + exp1.toString() + " " + getOperatorSymbol() + " " + exp2.toString() + ")";
    }

    private String getOperatorSymbol() {
        switch(operator) {
            case 1: return "+";
            case 2: return "-";
            case 3: return "*";
            case 4: return "/";
            default: return "?";
        }
    }
}
