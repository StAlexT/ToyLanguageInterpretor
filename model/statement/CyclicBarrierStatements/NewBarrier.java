package model.statement.CyclicBarrierStatements;

import javafx.util.Pair;
import model.exception.ToyLanguageException;
import model.expression.Expression;
import model.state.CyclicBarrier;
import model.state.ProgramState;
import model.state.SymbolTableInterface;
import model.statement.Statement;
import model.type.IntType;
import model.type.Type;
import model.value.IntegerValue;
import model.value.Value;

import java.util.ArrayList;
import java.util.List;

public class NewBarrier implements Statement {
    String variable;
    Expression expression;

    public NewBarrier(String variable, Expression expression) {
        this.variable = variable;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        SymbolTableInterface<String, Value> symbolTable = state.getSymbolTable();
        CyclicBarrier cyclicTable = state.getCyclicBarrierTable();
        Value numberVal = expression.eval(symbolTable, state.getHeap());
        IntegerValue number = (IntegerValue) numberVal;
        List<Integer> list = new ArrayList<>();
        Pair<Integer, List<Integer>> pair = new Pair<>(number.getVal(), list);
        cyclicTable.lock();
        try {
            int newLoc = cyclicTable.getFreeLocation();
            cyclicTable.add(newLoc, pair);
            if (symbolTable.isDefined(variable))
                symbolTable.update(variable, new IntegerValue(newLoc));
            else
                symbolTable.add(variable, new IntegerValue(newLoc));
            return state;
        }finally {
            cyclicTable.unlock();
        }

    }

    @Override
    public SymbolTableInterface<String, Type> typeCheck(SymbolTableInterface<String, Type> typeEnv) throws ToyLanguageException {
        Type typeVar = typeEnv.getValue(variable);
        Type typeExp = expression.typeCheck(typeEnv);
        if (typeVar.equals(new IntType()) && typeExp.equals(new IntType()))
            return typeEnv;
        else
            throw new ToyLanguageException("Right and left have diff types than int -  newBarrier");
    }

    @Override
    public Statement deepCopy() {
        return new NewBarrier(variable, expression.deepCopy());
    }

    @Override
    public String toString(){
        return "newBarrier(" + variable + ","+expression.toString() +")";
    }
}
