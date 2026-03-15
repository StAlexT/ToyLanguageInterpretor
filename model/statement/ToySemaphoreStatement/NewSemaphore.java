package model.statement.ToySemaphoreStatement;

import javafx.util.Pair;
import model.exception.ToyLanguageException;
import model.expression.Expression;
import model.state.CyclicBarrier;
import model.state.ProgramState;
import model.state.SymbolTableInterface;
import model.state.ToySemaphore;
import model.statement.Statement;
import model.type.IntType;
import model.type.Type;
import model.value.IntegerValue;
import model.value.Value;

import java.util.ArrayList;
import java.util.List;

public class NewSemaphore implements Statement {
    String variable;
    Expression expression1;
    Expression expression2;

    public NewSemaphore(String variable, Expression expression, Expression expression1) {
        this.variable = variable;
        this. expression1 =expression;
        expression2 =expression1;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        SymbolTableInterface<String, Value> symbolTable = state.getSymbolTable();
        ToySemaphore toySemaphore = state.getToySemaphoreTable();

        Value numberVal1 = expression1.eval(symbolTable, state.getHeap());
        IntegerValue number1 = (IntegerValue) numberVal1;

        Value numberVal2 = expression2.eval(symbolTable, state.getHeap());
        IntegerValue number2 = (IntegerValue) numberVal2;

        List<Integer> list = new ArrayList<>();

        Pair<List<Integer>, Integer> pair = new Pair<>( list, number2.getVal());
        Pair<Integer, Pair<List<Integer>, Integer>> finalPair = new Pair<>(number1.getVal(), pair);

        toySemaphore.lock();
        try {
            int newLoc = toySemaphore.getFreeLocation();
            toySemaphore.add(newLoc, finalPair);
            if (symbolTable.isDefined(variable)&&symbolTable.getValue(variable).getType().equals(new IntType()))
                symbolTable.update(variable, new IntegerValue(newLoc));
            else
                throw new ToyLanguageException("Not in table or not int - newToySemaphore");
            return state;
        }finally {
            toySemaphore.unlock();
        }
    }

    @Override
    public SymbolTableInterface<String, Type> typeCheck(SymbolTableInterface<String, Type> typeEnv) throws ToyLanguageException {
        return typeEnv;
    }

    @Override
    public Statement deepCopy() {
        return new NewSemaphore(variable, expression1.deepCopy(), expression2.deepCopy());
    }

    @Override
    public String toString(){
        return "newSemaphore(" + variable + ","+ expression1.toString() + "," + expression2.toString() +")";
    }
}
