package model.statement.CountSemaphoreStatements;
import model.exception.ToyLanguageException;
import model.expression.Expression;
import model.state.*;
import model.statement.Statement;
import model.type.IntType;
import model.type.Type;
import model.value.IntegerValue;
import model.value.Value;

public class CreateSemaphore implements Statement {
    String variable;
    Expression expression;

    public CreateSemaphore(String variable, Expression expression){
        this.expression = expression;
        this.variable = variable;
    }
    @Override
    public ProgramState execute(ProgramState state) {
        SymbolTableInterface<String, Value> symbolTable = state.getSymbolTable();
        CountSemaphoreInterface<Integer, Integer, Integer> semaphoreTable = state.getSemaphoreTable();

        semaphoreTable.lock();
        try {
            Value exp1 = expression.eval(state.getSymbolTable(), state.getHeap());
            if (!(exp1.getType() instanceof IntType))
                throw new ToyLanguageException("Not int -  createSemaphore");
            Value var = symbolTable.getValue(variable);
            IntegerValue variab = (IntegerValue) var;
            IntegerValue expres1 = (IntegerValue) exp1;
//            int loc = semaphoreTable.getLocation(expres1.getVal());
            int newLoc = semaphoreTable.getFreeLocation();
            if (symbolTable.isDefined(variable) && var.getType() instanceof IntType) {
                semaphoreTable.add(newLoc, expres1.getVal(), -10000);
                symbolTable.update(variable, new IntegerValue(newLoc));
                return state;
            } else
                throw new ToyLanguageException("Not int var  - createSemaphore");
        } finally {
            semaphoreTable.unlock();
        }
    }

    @Override
    public SymbolTableInterface<String, Type> typeCheck(SymbolTableInterface<String, Type> typeEnv) throws ToyLanguageException {
            Type typeVar = typeEnv.getValue(variable);
            Type typeExp = expression.typeCheck(typeEnv);
            if (typeVar.equals(new IntType()) && typeExp.equals(new IntType()))
                return typeEnv;
            else
                throw new ToyLanguageException("Right and left have diff types than int -  createSemaphore");
    }

    @Override
    public Statement deepCopy() {
        return new CreateSemaphore(variable, expression);
    }


    @Override
    public String toString() {
        return "createStatement("+ variable.toString() + ", "+ expression.toString()+")";
    }
}
