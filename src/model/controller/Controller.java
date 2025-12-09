package model.controller;
import model.exception.ToyLanguageException;
import model.repository.RepositoryInterface;
import model.state.ExecutionStackInterface;
import model.state.ProgramState;
import model.statement.Statement;
import model.value.RefValue;
import model.value.Value;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Controller implements ControllerInterface{
    RepositoryInterface repository;

    public Controller(RepositoryInterface repo){
        repository = repo;
    }

    @Override
    public ProgramState oneStep(ProgramState state) throws ToyLanguageException {
        ExecutionStackInterface<Statement> executionStack = state.getStack();
        if(executionStack.isEmpty()) throw new ToyLanguageException("Empty Stack");
        Statement currentStatement = executionStack.pop();
        return currentStatement.execute(state);
    }

    public Map<Integer, Value> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer, Value> heap){
        return heap.entrySet().stream()
                .filter(e -> symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public List<Integer> getAddrFromSymTable(Collection<Value> symTableValues){
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {RefValue v1 = (RefValue)v; return v1.getAddress();})
                .collect(Collectors.toList());
    }
//for the exception from exemple 10 -> garbage collector treats 1 as “invalid” because nothing in the symbol table currently points to it (v points to 3).

    @Override
    public void allSteps() throws ToyLanguageException, IOException {
        ProgramState programState = repository.getCurrentProgramState();
        repository.logProgramState(programState);
        if (programState==null)
            throw new ToyLanguageException("No Program State");
        while (!programState.getStack().isEmpty()){
            oneStep(programState);
//            repository.logProgramState(programState);
            programState.getHeap().setContent(unsafeGarbageCollector(getAddrFromSymTable(programState.getSymbolTable().getAll().values()), programState.getHeap().getAll()));
            repository.logProgramState(programState);
        }
    }


}
