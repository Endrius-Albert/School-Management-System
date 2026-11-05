package ie.cct.schoolsystem.view;

import java.util.Scanner;

public class ConsoleView {

    private Scanner scanner = new Scanner(System.in);

    public String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public void showMessage(String message) {
        System.out.println(message);
    }
}
