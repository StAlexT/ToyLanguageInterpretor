package model.controller;
import model.exception.ToyLanguageException;
import model.repository.RepositoryInterface;
import model.state.ExecutionStackInterface;
import model.state.ProgramState;
import model.statement.Statement;
import java.io.IOException;

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

    @Override
    public void allSteps() throws ToyLanguageException, IOException {
        ProgramState programState = repository.getCurrentProgramState();
        repository.logProgramState(programState);
        if (programState==null)
            throw new ToyLanguageException("No Program State");
        while (!programState.getStack().isEmpty()){
            oneStep(programState);
            repository.logProgramState(programState);
        }
    }


}
