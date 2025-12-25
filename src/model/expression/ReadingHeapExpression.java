package model.expression;

import model.exception.ToyLanguageException;
import model.state.SymbolTableInterface;
import model.state.HeapRefInterface;
import model.type.RefType;
import model.type.Type;
import model.value.RefValue;
import model.value.Value;

public class ReadingHeapExpression implements Expression{

    Expression expression;

    public ReadingHeapExpression(Expression expression){
        this.expression = expression;
    }

    @Override
    public Value eval(SymbolTableInterface<String, Value> table, HeapRefInterface<Integer, Value> heap) throws ToyLanguageException {
        Value val = expression.eval(table, heap);
        if(val.getType() instanceof RefType)
        {
            RefValue value = (RefValue) val;
            if(!heap.contains(value.getAddress()))
                throw new ToyLanguageException("Heap does not contain address: " + value.getAddress());

            return heap.getValue(value.getAddress());

        }
        else
            throw new ToyLanguageException("Value must be RefValue");
    }

    @Override
    public Type typeCheck(SymbolTableInterface<String, Type> typeEnv) throws ToyLanguageException {
        Type type = expression.typeCheck(typeEnv);
        if (type instanceof RefType){
            RefType ref = (RefType) type;
            return ref.getInner();
        }
        else
            throw new ToyLanguageException("the rH argument is not a RefType");
    }

    @Override
    public Expression deepCopy() {
        return new ReadingHeapExpression(expression.deepCopy());
    }
    @Override
    public String toString() {
        return "rH(" + expression.toString() + ")";
    }

}
