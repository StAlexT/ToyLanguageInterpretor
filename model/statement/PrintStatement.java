package model.statement;

import model.exception.ToyLanguageException;
import model.expression.Expression;
import model.state.ProgramState;
import model.state.SymbolTableInterface;
import model.type.Type;
import model.value.Value;

public class PrintStatement implements Statement{
    Expression expression;

    public PrintStatement(Expression exp){
        expression = exp;
    }

    public String toString(){
        return "print("+expression.toString()+")";
    }

    @Override
    public ProgramState execute(ProgramState state) {
        Value value = expression.eval(state.getSymbolTable(), state.getHeap());
        state.getOutput().add(value);
        return null;
        //return state;
    }

    @Override
    public SymbolTableInterface<String, Type> typeCheck(SymbolTableInterface<String, Type> typeEnv) throws ToyLanguageException {
        expression.typeCheck(typeEnv);
        return typeEnv;
    }

    @Override
    public Statement deepCopy() {
        return new PrintStatement(expression.deepCopy());
    }
}
