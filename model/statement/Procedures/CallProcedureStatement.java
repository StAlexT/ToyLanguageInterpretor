package model.statement.Procedures;
import javafx.util.Pair;
import model.exception.ToyLanguageException;
import model.expression.Expression;
import model.state.ProgramState;
import model.state.ProcTableInterface;
import model.state.SymbolTable;
import model.state.SymbolTableInterface;
import model.statement.Statement;
import model.type.Type;
import model.value.Value;

import java.util.ArrayList;
import java.util.List;

public class CallProcedureStatement implements Statement {
    private final String procName;
    private final List<Expression> arguments;

    public CallProcedureStatement(String procName, List<Expression> arguments) {
        this.procName = procName;
        this.arguments = arguments;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ToyLanguageException {
        ProcTableInterface procTable = state.getProcTable();

        if (!procTable.isDefined(procName))
            throw new ToyLanguageException("Procedure " + procName + " not defined");

        Pair<List<String>, Statement> proc = procTable.get(procName);
        List<String> formalParams = proc.getKey();
        Statement body = proc.getValue();

        if (formalParams.size() != arguments.size())
            throw new ToyLanguageException("Procedure " + procName + " expects " + formalParams.size() +
                    " arguments, got " + arguments.size());

        // Evaluate arguments in caller's current table
        List<Value> argValues = new ArrayList<>();
        SymbolTableInterface<String, Value> callerTable = state.getCurrentSymTable();
        for (Expression expr : arguments)
            argValues.add(expr.eval(callerTable, state.getHeap()));

        // Create new local SymTable for the procedure
        SymbolTableInterface<String, Value> localTable = new SymbolTable<>();
        for (int i = 0; i < formalParams.size(); i++)
            localTable.add(formalParams.get(i), argValues.get(i));

        // Push local table and procedure body
        state.pushSymTable(localTable);
        System.out.println("Procedure " + procName + " local symtable: " + state.getCurrentSymTable());



        state.getStack().push(new ReturnStatement());
        state.getStack().push(body);

        return state;
    }

    @Override
    public SymbolTableInterface<String, Type> typeCheck(SymbolTableInterface<String, Type> typeEnv) throws ToyLanguageException {
        return null;
    }

    @Override
    public Statement deepCopy() {
        return new CallProcedureStatement(procName, new ArrayList<>(arguments));
    }

    @Override
    public String toString() {
        List<String> argStrs = new ArrayList<>();
        for (Expression expr : arguments) argStrs.add(expr.toString());
        return "call " + procName + "(" + String.join(", ", argStrs) + ")";
    }
}

