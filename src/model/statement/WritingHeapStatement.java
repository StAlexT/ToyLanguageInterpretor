package model.statement;

import model.exception.ToyLanguageException;
import model.state.HeapRefInterface;
import model.state.ProgramState;
import model.state.SymbolTableInterface;
import model.type.RefType;
import model.type.Type;
import model.value.RefValue;
import model.value.Value;
import model.expression.Expression;

public class WritingHeapStatement implements Statement {
    String variableName;
    Expression expression;

    public WritingHeapStatement(String variableName, Expression expression){
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        SymbolTableInterface<String, Value> table = state.getSymbolTable();
        HeapRefInterface<Integer, Value> heap = state.getHeap();
        if(!table.isDefined(variableName))
            throw new ToyLanguageException("Not defined in SymbolTable");
        Value value = table.getValue(variableName);
        if(!(value.getType() instanceof RefType))
            throw new ToyLanguageException("Value not type RefType");
        RefValue refValue = (RefValue) value;
        if(!heap.contains(refValue.getAddress()))
            throw new ToyLanguageException("Not in heap");
        Value valueExp = expression.eval(table, heap);

        RefType refType = (RefType) refValue.getType();
        Type innerType = refType.getInner();

        if(!valueExp.getType().equals(innerType))
            throw new ToyLanguageException("Type mismatch ");
        heap.update(refValue.getAddress(), valueExp);
        return state;
    }

    @Override
    public SymbolTableInterface<String, Type> typeCheck(SymbolTableInterface<String, Type> typeEnv) throws ToyLanguageException {
        Type typeVar = typeEnv.getValue(variableName);
        Type typeExp = expression.typeCheck(typeEnv);
        if(typeVar.equals(new RefType(typeExp)))
            return typeEnv;
        else
            throw new ToyLanguageException("wH stmt: Type mismatched");

    }

    @Override
    public Statement deepCopy() {
        return new WritingHeapStatement(variableName, expression.deepCopy());
    }

    @Override
    public String toString(){
        return "wH("+ variableName.toString() + "," + expression.toString() + ")";
    }
}