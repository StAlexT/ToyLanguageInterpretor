package model.statement;

import model.exception.ToyLanguageException;
import model.expression.Expression;
import model.state.ProgramState;
import model.state.SymbolTableInterface;
import model.state.HeapRefInterface;
import model.type.RefType;
import model.value.RefValue;
import model.value.Value;

public class NewStatement implements Statement{

    String variableName;
    Expression expression;

    public NewStatement(String variableName, Expression expression){
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        HeapRefInterface<Integer, Value> table = state.getHeap();
        SymbolTableInterface<String, Value> symbolTable = state.getSymbolTable();
        HeapRefInterface<Integer, Value> heap = state.getHeap();

        if(!symbolTable.isDefined(variableName))
            throw new ToyLanguageException("Variable not defined: " + variableName);

        Value locExpSymTable = expression.eval(symbolTable,heap);
        Value locVarNameSymTable = symbolTable.getValue(variableName);

        if(!(locVarNameSymTable.getType() instanceof RefType varRef))
            throw new ToyLanguageException("Mismatch Type");

        if (!(locExpSymTable.getType().equals(varRef.getInner())))
            throw new ToyLanguageException("Types mismatch for: " + variableName);

        int newAddr = heap.add(locExpSymTable);
        symbolTable.update(variableName, new RefValue(newAddr, varRef.getInner()));

        return state;
    }

    @Override
    public Statement deepCopy() {
        return new NewStatement(variableName, expression.deepCopy());
    }

    @Override
    public String toString() {
        return variableName + " -> " + expression.toString();
    }

}
