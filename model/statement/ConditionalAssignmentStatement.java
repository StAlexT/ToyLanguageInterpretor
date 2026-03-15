package model.statement;

import model.exception.ToyLanguageException;
import model.expression.Expression;
import model.state.ProgramState;
import model.state.SymbolTableInterface;
import model.type.Type;

public class ConditionalAssignmentStatement implements Statement{
    String nameVariable;
    Expression expression1;
    Expression expression2;
    Expression expression3;

    public ConditionalAssignmentStatement(String nameVariable, Expression expression1, Expression expression2, Expression expression3){
        this.nameVariable = nameVariable;
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.expression3 = expression3;
    }
    @Override
    public ProgramState execute(ProgramState state) {
        Statement statement = new IfStatement(
                    expression1,
                    new AssignmentStatement(nameVariable, expression2),
                    new AssignmentStatement(nameVariable, expression3)
        );
        state.getStack().push(statement);
        return null;
    }

    @Override
    public SymbolTableInterface<String, Type> typeCheck(SymbolTableInterface<String, Type> typeEnv) throws ToyLanguageException {
            return typeEnv;
    }

    @Override
    public Statement deepCopy() {
        return new ConditionalAssignmentStatement( nameVariable,
                expression1.deepCopy(),
                expression2.deepCopy(),
                expression3.deepCopy()
        );
    }

    @Override
    public String toString(){
        return nameVariable + "=("+expression1.toString()+")?"+expression2.toString()+":"+expression3.toString();
    }
}
