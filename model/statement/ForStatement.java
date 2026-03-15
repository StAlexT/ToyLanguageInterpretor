
package model.statement;

import model.exception.ToyLanguageException;
import model.expression.ArithmeticExpression;
import model.expression.Expression;
import model.state.ProgramState;
import model.state.SymbolTableInterface;
import model.type.BoolType;
import model.type.Type;
import model.value.BooleanNumber;
import model.value.Value;

public class ForStatement implements Statement {
    String variableName;
    Expression expression1;
    Expression expression2;
    Expression expression3;
    Statement statement;

    public ForStatement(String variableName, Expression expression1, Expression expression2, Expression expression3, Statement statement) {
        this.variableName = variableName;
        this.expression1 = expression1;
        this.expression2 = expression2;
        this.expression3 = expression3;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        Statement first = new AssignmentStatement(variableName, expression1);
        Statement forStatement = new CompoundStatement(
                first,
                new WhileStatement(expression2,
                        new CompoundStatement(
                                statement,
                                new AssignmentStatement(variableName, expression3)
                        )
                )
        );
        state.getStack().push(forStatement);
        return null;
    }

    @Override
    public SymbolTableInterface<String, Type> typeCheck(SymbolTableInterface<String, Type> typeEnv) throws ToyLanguageException {
        Type typeExp = expression2.typeCheck(typeEnv);
        if (typeExp.equals(new BoolType())) {
            statement.typeCheck(typeEnv.deepCopy());
            return typeEnv;
        } else
            throw new ToyLanguageException("The condition of For is not type bool");
    }

    @Override
    public Statement deepCopy() {
        return new ForStatement(variableName, expression1.deepCopy(), expression2.deepCopy(), expression3.deepCopy(), statement.deepCopy());
    }

    @Override
    public String toString() {
        return "for(" + expression1 + ";" + expression2 + ";" + expression3 + ")" + statement;
    }
}
