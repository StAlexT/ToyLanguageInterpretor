package model.statement.Procedures;

import javafx.util.Pair;
import model.exception.ToyLanguageException;
import model.state.ProcTableInterface;
import model.state.ProgramState;
import model.state.SymbolTableInterface;
import model.statement.Statement;
import model.value.Value;

import java.util.List;

public class ProcNameStatement implements Statement {
    private final String procName; // just for display
    private final List<String> formalParams;
    private final Statement body;

    public ProcNameStatement(String procName, List<String> formalParams, Statement body) {
        this.procName = procName;
        this.formalParams = formalParams;
        this.body = body;
    }

    @Override
    public ProgramState execute(ProgramState state) throws ToyLanguageException {
        ProcTableInterface procTable = state.getProcTable();
        if (procTable.isDefined(procName))
            throw new ToyLanguageException("Procedure " + procName + " already defined");

        procTable.add(procName, new Pair<>(formalParams, body));
        return state;
    }

    @Override
    public SymbolTableInterface<String, model.type.Type> typeCheck(SymbolTableInterface<String, model.type.Type> typeEnv) throws ToyLanguageException {
        // Procedures don’t need type check for now, return same environment
        return typeEnv;
    }

    @Override
    public Statement deepCopy() {
        return new ProcNameStatement(procName, List.copyOf(formalParams), body.deepCopy());
    }

    @Override
    public String toString() {
        return "procedure " + procName + "(" + String.join(", ", formalParams) + ") " + body;
    }
}
