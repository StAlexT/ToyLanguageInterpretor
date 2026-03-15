package model.statement.CyclicBarrierStatements;

import javafx.util.Pair;
import model.exception.ToyLanguageException;
import model.state.CyclicBarrier;
import model.state.ProgramState;
import model.state.SymbolTableInterface;
import model.statement.Statement;
import model.type.IntType;
import model.type.Type;
import model.value.IntegerValue;
import model.value.Value;

import java.util.List;

public class Await implements Statement {
    String variable;

    public Await(String variable) {
        this.variable = variable;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        SymbolTableInterface<String, Value> symbolTable = state.getSymbolTable();
        CyclicBarrier cyclicTable = state.getCyclicBarrierTable();
        if (!symbolTable.isDefined(variable))
            throw new ToyLanguageException("Not in the table - Await");
        Value foundIndexVal = symbolTable.getValue(variable);
        IntegerValue founfIndexIntVal = (IntegerValue) foundIndexVal;
        Integer foundIndex = founfIndexIntVal.getVal();
        cyclicTable.lock();
        try {
            if(!cyclicTable.isDefined(foundIndex))
                throw new ToyLanguageException("Not in the cycle table - Await");
            Pair<Integer, List<Integer>> pair = cyclicTable.getValue(foundIndex);
            List<Integer> list = pair.getValue();
            int NL = list.size();
            int N1 = pair.getKey();
            if (NL < N1){
                if (list.contains(state.getId())){
                    state.getStack().push(this);
                }else
                {
                    list.add(state.getId());
                    state.getStack().push(this);
                }
            } else if(NL == N1)
            {
                list.add(state.getId());
                state.getStack().push(this);
                return state;
            }
            return state;
        } finally {
            cyclicTable.unlock();
        }

    }

    @Override
    public SymbolTableInterface<String, Type> typeCheck(SymbolTableInterface<String, Type> typeEnv) throws ToyLanguageException {
        Type typeVar = typeEnv.getValue(variable);
        if(typeVar.equals(new IntType()))
            return typeEnv;
        else
            throw new ToyLanguageException("Not int typeCheck-  Await");
    }

    @Override
    public Statement deepCopy() {
        return new Await(variable);
    }
    @Override
    public String toString(){
        return "await(" + variable +")";
    }
}
