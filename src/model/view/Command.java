package model.view;

public abstract class Command { // here we have the "key" aka whatever number you chose and the things it will be show / its not an interface if it was every comand would need to implemnt this itself
    private final String key;
    private final String description;

    public Command(String key, String description) {
        this.key = key;
        this.description = description;
    }

    public String getKey() { return key; }
    public String getDescription() { return description; }

    public abstract void execute();
}

