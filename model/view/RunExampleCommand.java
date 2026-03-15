package model.view;

import model.controller.Controller;
import model.exception.ToyLanguageException;
import model.state.ProgramState;
import model.state.SymbolTable;
import model.state.SymbolTableInterface;
import model.statement.Statement;

import java.io.IOException;

public class RunExampleCommand extends Command {
    private final Controller controller;

    public RunExampleCommand(String key, String desc, Controller control) {
        super(key, desc);
        this.controller = control;
    }

    @Override
    public void execute() {
        try {
            controller.allSteps();

        } catch (IOException | ToyLanguageException e) {
            System.out.println("Execution error: " + e.getMessage());
        }
    }



}
