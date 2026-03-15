package model.view;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu { //yay, map of the press-number -> what-command
    private final Map<String, Command> commands = new HashMap<>();

    public void addCommand(Command c) {
        commands.put(c.getKey(), c);
    }

    private void printMenu() {
        for (Command com : commands.values()) {
            System.out.printf("%s : %s%n", com.getKey(), com.getDescription());
        }
    }

    public void show() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            System.out.print("Input the option: ");
            String key = scanner.nextLine();
            Command com = commands.get(key);
            if (com == null) {
                System.out.println("Invalid Option");
                continue;
            }
            com.execute();
        }
    }
}
