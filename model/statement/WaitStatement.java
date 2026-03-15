package model.statement;

import model.exception.ToyLanguageException;
import model.expression.Expression;
import model.expression.ValueExpression;
import model.state.ProgramState;
import model.state.SymbolTableInterface;
import model.type.IntType;
import model.type.Type;
import model.value.IntegerValue;
import model.value.Value;

public class WaitStatement implements Statement{
    Expression expression;

    public WaitStatement(Expression expression){
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        Value value = expression.eval(state.getSymbolTable(), state.getHeap());
        if(!value.getType().equals(new IntType()))
            throw new ToyLanguageException("No int  - sleep");
        IntegerValue nr = (IntegerValue) value;
        int number = nr.getVal() - 1;
        Statement waitStatement = new CompoundStatement(
                new PrintStatement(new ValueExpression(new IntegerValue(nr.getVal()))),
                new WaitStatement(new ValueExpression(new IntegerValue(number)))
        );
        if (nr.getVal() <= 0)
            return null;
        state.getStack().push(waitStatement);
        return state;
    }

    @Override
    public SymbolTableInterface<String, Type> typeCheck(SymbolTableInterface<String, Type> typeEnv) throws ToyLanguageException {
        expression.typeCheck(typeEnv);
        return typeEnv;
    }

    @Override
    public Statement deepCopy() {
        return new WaitStatement(expression.deepCopy());
    }

    @Override
    public String toString(){
        return "wait(" + expression.toString() + ")";
    }
}
