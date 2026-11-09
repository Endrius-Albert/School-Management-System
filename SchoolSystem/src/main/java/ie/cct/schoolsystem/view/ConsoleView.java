package ie.cct.schoolsystem.view;

import ie.cct.schoolsystem.controller.MenuOption;
import ie.cct.schoolsystem.model.Employee;
import java.util.List;
import java.util.Scanner;

public class ConsoleView {

    private Scanner scanner = new Scanner(System.in);

    public void showMenu() {
        System.out.println();
        System.out.println("Do You wish to SORT or SEARCH:");
        for (MenuOption option : MenuOption.values()) {
            System.out.println(option.getNumber() + ". " + option.getLabel());
        }
    }

    public String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public int readInt(String prompt) {
        while (true) {
            String input = readLine(prompt);
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void printStaffTable(List<Employee> list, int limit) {
        int count = Math.min(limit, list.size());

        System.out.println(String.format("%-4s %-22s | %-20s | %-18s",
                "#", "Name", "Manager Type", "Department"));
        System.out.println("-".repeat(71));

        for (int i = 0; i < count; i++) {
            System.out.println(String.format("%-4d %s", i + 1, list.get(i)));
        }
    }

    public void printProfile(Employee e) {
        System.out.println("-----------------------------------");
        System.out.println("Name:         " + e.getFullName());
        System.out.println("Manager Type: " + e.getManagerType());
        System.out.println("Department:   " + e.getDepartment());
        System.out.println("Position:     " + e.getPosition());
        System.out.println("Email:        " + e.getEmail());
        System.out.println("Salary:       EUR " + String.format("%.2f", e.getSalary()));
    }

    public void printOptions(String heading, Object[] options) {
        System.out.println(heading);
        for (Object option : options) {
            System.out.println("  - " + option);
        }
    }
}
