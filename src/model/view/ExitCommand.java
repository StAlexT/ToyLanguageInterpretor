package model.view;

public class ExitCommand extends Command {

    public ExitCommand(String key, String desc) {
        super(key, desc); //calls command constructor
    }

    @Override
    public void execute() {
        System.exit(0);
    }
}
