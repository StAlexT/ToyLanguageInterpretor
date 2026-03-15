package model.statement;

import model.exception.ToyLanguageException;
import model.expression.Expression;
import model.state.ProgramState;
import model.state.SymbolTableInterface;
import model.type.Type;
import model.value.Value;

public class SwitchStatement implements Statement{
    Expression expression;
    Expression expressionCase1;
    Statement statementCase1;
    Expression expressionCase2;
    Statement statementCase2;
    Statement statementDefault;
    public SwitchStatement(Expression expression, Expression expressionCase1, Statement statementCase1, Expression expressionCase2, Statement statementCase2, Statement statementDefault){
        this.expression = expression;
        this.expressionCase1 =expressionCase1;
        this.statementCase1 = statementCase1;
        this.expressionCase2 = expressionCase2;
        this.statementCase2 = statementCase2;
        this.statementDefault = statementDefault;
    }

    @Override
    public ProgramState execute(ProgramState state) {
        Value exp = expression.eval(state.getSymbolTable(), state.getHeap());
        Value exp1 = expressionCase1.eval(state.getSymbolTable(), state.getHeap());
        Value exp2 = expressionCase2.eval(state.getSymbolTable(), state.getHeap());
        if (!exp.getType().equals(exp1.getType()))
            throw new ToyLanguageException("Different types - switch statement expression and expressionCase1");
        if (!exp.getType().equals(exp2.getType()))
            throw new ToyLanguageException("Different types - switch statement expression and expressionCase2");
        if (exp.toString().equals(exp1.toString())){
            state.getStack().push(statementCase1);
        }else if (exp.toString().equals(exp2.toString()))
            state.getStack().push(statementCase2);
        else
            state.getStack().push(statementDefault);
        return state;
    }

    @Override
    public SymbolTableInterface<String, Type> typeCheck(SymbolTableInterface<String, Type> typeEnv) throws ToyLanguageException {
        Type typeExp = expression.typeCheck(typeEnv);
        Type typeExp1 = expressionCase1.typeCheck(typeEnv);
        Type typeExp2 = expressionCase2.typeCheck(typeEnv);
        if(typeExp.equals(typeExp1)&&typeExp.equals(typeExp2)){
            statementCase1.typeCheck(typeEnv);
            statementCase2.typeCheck(typeEnv);
            statementDefault.typeCheck(typeEnv);
            return typeEnv;
        }
        else
            throw new ToyLanguageException("Different types between expressions - Switch Statement");
    }

    @Override
    public String toString(){
        return "Switch(" + expression.toString() +") (case "+expressionCase1.toString() +" : "+ statementCase1.toString() +
                ") (case " + expressionCase2.toString()+" : "+statementCase2.toString()+") (default: "+statementDefault.toString();
    }
    @Override
    public Statement deepCopy() {
        return new SwitchStatement(expression.deepCopy(), expressionCase1.deepCopy(), statementCase1.deepCopy(), expressionCase2.deepCopy(), statementCase2.deepCopy(), statementDefault.deepCopy());
    }
}
