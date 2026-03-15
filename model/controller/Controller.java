package model.controller;
import model.exception.ToyLanguageException;
import model.repository.Repository;
import model.repository.RepositoryInterface;
import model.state.ExecutionStackInterface;
import model.state.ProgramState;
import model.state.SymbolTable;
import model.statement.Statement;
import model.value.RefValue;
import model.value.Value;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller implements ControllerInterface{
    RepositoryInterface repository;
    ExecutorService executor;

    public Controller(RepositoryInterface repo){
        repository = repo;
        executor = Executors.newFixedThreadPool(2);
    }

//    @Override
//    public ProgramState oneStep(ProgramState state) throws ToyLanguageException {
//        ExecutionStackInterface<Statement> executionStack = state.getStack();
//        if(executionStack.isEmpty()) throw new ToyLanguageException("Empty Stack");
//        Statement currentStatement = executionStack.pop();
//        return currentStatement.execute(state);
//    }

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
    public List<ProgramState> removeCompletedProgram(List<ProgramState> inProgramList) {
        return inProgramList.stream()
                .filter(prog->prog.isNotCompleted())
                .collect(Collectors.toList());
    }
    public Map<Integer, Value> conservativeGarbageCollector(List<ProgramState> prgList, Map<Integer, Value> heap) {
        List<Integer> addresses = prgList.stream()
                .flatMap(prg -> getAddrFromSymTable(prg.getSymbolTable().getAll().values()).stream())
                .collect(Collectors.toList());
        return heap.entrySet().stream()
                .filter(e -> addresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void oneStepForAllPrg(List<ProgramState> programStateList) throws ToyLanguageException {
        programStateList.forEach(prg->repository.logProgramState(prg));
        List<Callable<ProgramState>> callableList = programStateList.stream() //we create a... thing
                .map((ProgramState p)->(Callable<ProgramState>)(()-> {return p.oneStep();}))
                .collect(Collectors.toList());
        try {
            List<ProgramState> newProgramStateList = executor.invokeAll(callableList).stream()//we send all task to the thread pool - the things from before
                    .map(future -> {
                        try {
                            return future.get(); //wait to complete
                        } catch (Exception e) {
                            return null;
                        }
                    })
                    .filter(p -> p != null)
                    .collect(Collectors.toList());

            programStateList.addAll(newProgramStateList);
            programStateList.forEach(prg -> repository.logProgramState(prg));
            repository.setProgramList(programStateList);

        } catch (InterruptedException e) {
            throw new ToyLanguageException(e.getMessage());
        }

    }

//    public void oneStepForAllPrg(List<ProgramState> programStateList) throws ToyLanguageException {
//        List<Callable<ProgramState>> callables = programStateList.stream()
//                .map((ProgramState p) -> (Callable<ProgramState>)(() -> p.oneStep()))
//                .collect(Collectors.toList());
//
//        try {
//            List<ProgramState> newProgramStateList = executor.invokeAll(callables).stream()
//                    .map(future -> {
//                        try {
//                            return future.get(); // null if blocked
//                        } catch (Exception e) {
//                            return null;
//                        }
//                    })
//                    .collect(Collectors.toList());
//
//            // keep all threads in the pool, even blocked ones
//            List<ProgramState> updatedList = programStateList.stream()
//                    .map(p -> {
//                        ProgramState res = null;
//                        for (ProgramState newP : newProgramStateList) {
//                            if (newP != null && newP.getId() == p.getId()) {
//                                res = newP;
//                                break;
//                            }
//                        }
//                        return res != null ? res : p; // keep blocked thread
//                    })
//                    .collect(Collectors.toList());
//
//            // log progress
//            updatedList.forEach(prg -> repository.logProgramState(prg));
//            repository.setProgramList(updatedList);
//        } catch (InterruptedException e) {
//            throw new ToyLanguageException(e.getMessage());
//        }
//    }
//

    @Override
   // version 2
    public void allSteps() throws ToyLanguageException, IOException {
        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> programStateList = removeCompletedProgram(repository.getListProgramState());
        while (programStateList.size() > 0) {
            oneStepForAllPrg(programStateList);
            programStateList.get(0).getHeap().setContent(conservativeGarbageCollector(programStateList, programStateList.get(0).getHeap().getAll()));
            programStateList = removeCompletedProgram(repository.getListProgramState());
        }
        executor.shutdownNow();
        repository.setProgramList(programStateList);
    }


//    @Override
//    public void allSteps() throws ToyLanguageException, IOException {
//        executor = Executors.newFixedThreadPool(2);
//        List<ProgramState> programStateList = removeCompletedProgram(repository.getListProgramState());
//
//        boolean anyActive;
//        do {
//            anyActive = false;
//
//            oneStepForAllPrg(programStateList);
//
//            // update heap after one step
//            programStateList.get(0).getHeap().setContent(
//                    conservativeGarbageCollector(programStateList, programStateList.get(0).getHeap().getAll())
//            );
//
//            programStateList = removeCompletedProgram(repository.getListProgramState());
//
//            // check if any thread is active (stack not empty)
//            for (ProgramState prg : programStateList)
//                if (!prg.getStack().isEmpty())
//                    anyActive = true;
//
//        } while (!programStateList.isEmpty() && anyActive);
//
//        executor.shutdownNow();
//        repository.setProgramList(programStateList);
//    }

//    version 1
//    public void allSteps() throws ToyLanguageException, IOException {
//        ProgramState programState = repository.getCurrentProgramState();
//        Statement program = programState.getStack().peek();
//        program.typeCheck(new SymbolTable<>());
//        repository.logProgramState(programState);
//        if (programState==null)
//            throw new ToyLanguageException("No Program State");
//        while (!programState.getStack().isEmpty()){
//            oneStep(programState);
////            repository.logProgramState(programState);
//            programState.getHeap().setContent(unsafeGarbageCollector(getAddrFromSymTable(programState.getSymbolTable().getAll().values()), programState.getHeap().getAll()));
//            repository.logProgramState(programState);
//        }
//    }

    @Override
    public RepositoryInterface getRepo() {
        return repository;
    }


}
