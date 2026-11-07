package ie.cct.schoolsystem.controller;

import ie.cct.schoolsystem.model.EmployeeRepository;
import ie.cct.schoolsystem.view.ConsoleView;
import java.io.IOException;

public class MenuController {

    private static final int NAMES_ON_SCREEN = 20;

    private ConsoleView view = new ConsoleView();
    private EmployeeRepository repository = new EmployeeRepository();

    public void start() {
        loadFile();

        boolean running = true;
        while (running) {
            view.showMenu();
            int choice = view.readInt("Enter your choice: ");
            MenuOption option = MenuOption.fromNumber(choice);

            if (option == null) {
                view.showMessage("Invalid option. Please choose a number from the menu.");
                continue;
            }

            view.showMessage(option.getLabel() + " selected");

            switch (option) {
                case SORT:
                    sortEmployees();
                    break;
                case SEARCH:
                    searchEmployee();
                    break;
                case ADD_RECORDS:
                    addRecords();
                    break;
                case CREATE_BINARY_TREE:
                    createBinaryTree();
                    break;
                case EXIT:
                    running = false;
                    view.showMessage("Goodbye!");
                    break;
            }
        }
    }

    // Keeps asking for a filename until the file is read successfully
    private void loadFile() {
        boolean loaded = false;

        while (!loaded) {
            String filename = view.readLine("Please enter the filename to read: ");
            try {
                repository.loadFromFile(filename);
                loaded = true;
                view.showMessage("File read successfully");
                view.showMessage(repository.size() + " employees loaded.");
                if (repository.getInvalidLines() > 0) {
                    view.showMessage(repository.getInvalidLines() + " invalid lines were skipped.");
                }
            } catch (IOException e) {
                view.showMessage("File not found or could not be read. Please try again.");
            }
        }
    }

    private void sortEmployees() {
        repository.sortByName();
        view.showMessage("Employees sorted alphabetically. Showing the first "
                + NAMES_ON_SCREEN + ":");
        view.printStaffTable(repository.getEmployees(), NAMES_ON_SCREEN);
    }

    private void searchEmployee() {
    }

    private void addRecords() {
    }

    private void createBinaryTree() {
    }
}
