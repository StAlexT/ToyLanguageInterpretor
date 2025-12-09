package model.repository;

import model.exception.ToyLanguageException;
import model.state.ProgramState;
import model.statement.Statement;
import model.value.StringValue;
import model.value.Value;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Repository implements RepositoryInterface{
    List<ProgramState> programStateList;
    String pathFile;

    public Repository(String pathOfFile){
        programStateList = new ArrayList<>();
        pathFile = pathOfFile;
    }

    @Override
    public ProgramState getCurrentProgramState() {
        if (programStateList.isEmpty())
            return null;
        return programStateList.getFirst();
    }

    @Override
    public void addProgramState(ProgramState state) {
        programStateList.add(state);
    }

    @Override
    public void logProgramState(ProgramState state) throws ToyLanguageException{
        try(PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(pathFile, true))))
        {

            logFile.println(state.getStack().toString());
//            logFile.println("ExeStack:");
//            List<Statement> stackElements = state.getStack().getAll();
//            for (int i = stackElements.size() - 1; i >= 0; i--) {
//                logFile.println(stackElements.get(i).toString());
//            }

            logFile.println(state.getSymbolTable().toString());
//            logFile.println("SymTable:");
//            for (Map.Entry<String, Value> entry : state.getSymbolTable().getAll().entrySet())
//                logFile.println(entry.getKey() + " --> " + entry.getValue().toString());

            logFile.println(state.getHeap().toString());

            logFile.println(state.getOutput().toString());
//            logFile.println("Out:");
//            for (Value val : state.getOutput().getAll())
//                logFile.println(val.toString());

            logFile.println("FileTable:");
            if (state.getFileTable() == null) {
                logFile.println("(null)");
            } else if (state.getFileTable().getAll().isEmpty()) {
                logFile.println("(empty)");
            } else {
                for (Map.Entry<StringValue, BufferedReader> entry : state.getFileTable().getAll().entrySet())
                    logFile.println(entry.getKey().getVal() + " -> open");
            }

//            logFile.println("Ref Table: ");
//            for (Map.Entry<Integer, Value> entry : state.getRefTable().getAll().entrySet())
//                logFile.println(entry.getKey() + "-->" + entry.getValue().toString());



            logFile.println("----------------------------------------------------");
        }
        catch (IOException e) {
            throw new ToyLanguageException("Error writing to log file: " + e.getMessage());
        }
    }
}
