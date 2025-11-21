package model.statement;
import model.exception.ToyLanguageException;
import model.expression.Expression;
import model.state.ExecutionStackInterface;
import model.state.ProgramState;
import model.state.SymbolTableInterface;
import model.type.Type;
import model.value.Value;

public class AssignmentStatement implements Statement{
    String expName;
    Expression expression;

    public AssignmentStatement(String name, Expression exp){
        expName = name;
        expression = exp;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        ExecutionStackInterface<Statement> stack = state.getStack();
        SymbolTableInterface<String, Value> symbolTable = state.getSymbolTable();

        if (symbolTable.isDefined(expName)) {
            Value val = expression.eval(symbolTable);
            Type typeId = symbolTable.getValue(expName).getType();
            if (val.getType().equals(typeId))
                symbolTable.update(expName, val);
            else
                throw new ToyLanguageException("Type mismatch for " + expName);
        } else {
            throw new ToyLanguageException("Variable " + expName + " not declared before");
        }

        return state;
    }

    @Override
    public Statement deepCopy() {
        return new AssignmentStatement(expName, expression.deepCopy());
    }
    @Override
    public String toString() {
        return expName + "=" + expression.toString();
    }

}
