package model.statement;

import model.exception.ToyLanguageException;
import model.expression.Expression;
import model.state.ProgramState;
import model.state.SymbolTableInterface;
import model.type.BoolType;
import model.type.Type;
import model.value.BooleanNumber;
import model.value.Value;

import java.awt.*;

public class WhileStatement implements Statement{

    Expression condition;
    Statement statement;

    public WhileStatement(Expression condition, Statement statement){
        this.condition = condition;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        Value condValue = condition.eval(state.getSymbolTable(), state.getHeap());
        if(!(condValue instanceof BooleanNumber)){
            throw new ToyLanguageException("Condition in While is not boolean");
        }
        BooleanNumber cond = (BooleanNumber) condValue;

        if(cond.getVal()){
            state.getStack().push(this);//this -> the WhileStatement
            state.getStack().push(statement);
        }

        return null;
        //return state;
    }

    @Override
    public SymbolTableInterface<String, Type> typeCheck(SymbolTableInterface<String, Type> typeEnv) throws ToyLanguageException {
        Type typeExp = condition.typeCheck(typeEnv);
        if(typeExp.equals(new BoolType())) {
            statement.typeCheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
            throw new ToyLanguageException("The condition of While is not type bool");
    }

    @Override
    public Statement deepCopy() {
        return new WhileStatement(condition.deepCopy(), statement.deepCopy());
    }

    @Override
    public String toString(){
        return "while(" + condition + ")" + statement;
    }
}
