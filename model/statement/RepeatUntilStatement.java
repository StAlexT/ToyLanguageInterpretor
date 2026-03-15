package model.statement;

import model.exception.ToyLanguageException;
import model.expression.*;
import model.state.ProgramState;
import model.state.SymbolTableInterface;
import model.type.BoolType;
import model.type.Type;
import model.value.BooleanNumber;
import model.value.Value;

public class RepeatUntilStatement implements Statement{
    Statement statement;
    Expression expression;

    public RepeatUntilStatement(Statement statement, Expression expression){
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        Value condVal = expression.eval(state.getSymbolTable(), state.getHeap());
        BooleanNumber cond = (BooleanNumber) condVal;
        Boolean condition = cond.getVal();
        Expression negatedCondition = new NotExpression(expression);
        Statement statementRU = new CompoundStatement(
                statement,
                new WhileStatement(negatedCondition, statement)
        );
        state.getStack().push(statementRU);
        return null;
    }

    @Override
    public SymbolTableInterface<String, Type> typeCheck(SymbolTableInterface<String, Type> typeEnv) throws ToyLanguageException {
        Type typeExp = expression.typeCheck(typeEnv);
        if(typeExp.equals(new BoolType())){
            statement.typeCheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else
            throw new ToyLanguageException("The expression is not type bool - repeat until");
    }

    @Override
    public Statement deepCopy() {
        return new RepeatUntilStatement(statement.deepCopy(), expression.deepCopy());
    }

    @Override
    public String toString(){
        return "repeat "+statement.toString() + " until " + expression.toString();
    }
}
