package ie.cct.schoolsystem.controller;

import ie.cct.schoolsystem.model.Department;
import ie.cct.schoolsystem.model.Employee;
import ie.cct.schoolsystem.model.EmployeeRepository;
import ie.cct.schoolsystem.model.EmployeeTree;
import ie.cct.schoolsystem.model.ManagerType;
import ie.cct.schoolsystem.view.ConsoleView;
import java.io.IOException;
import java.util.List;

public class MenuController {

    private static final int NAMES_ON_SCREEN = 20;
    private static final int TREE_MINIMUM = 20;

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
        String typed = view.readLine("Enter the name to search: ");
        if (typed.isEmpty()) {
            view.showMessage("No name typed.");
            return;
        }

        List<Employee> found = repository.lookUp(typed);
        if (found.isEmpty()) {
            view.showMessage("No staff member found with the name \"" + typed + "\".");
            return;
        }

        view.showMessage(found.size() + " match(es) found for \"" + typed + "\":");
        for (Employee e : found) {
            view.printProfile(e);
        }
    }

    private void addRecords() {
        boolean more = true;

        while (more) {
            String first = askName("First name: ");
            String last = askName("Last name: ");

            if (repository.isOnStaff(first + " " + last)) {
                view.showMessage(first + " " + last + " is already on the staff list.");
                more = askAnother();
                continue;
            }

            view.printOptions("Manager types:", ManagerType.values());
            ManagerType role = askRole();

            view.printOptions("Departments:", Department.values());
            Department dept = askDepartment();

            repository.addStaff(new Employee(first, last, dept, role));
            view.showMessage("Record added. The list now has " + repository.size() + " employees.");

            more = askAnother();
        }

        if (repository.getFreshlyAdded().isEmpty()) {
            view.showMessage("No new staff added.");
            return;
        }
        view.showMessage("Staff added this session:");
        view.printStaffTable(repository.getFreshlyAdded(), repository.getFreshlyAdded().size());
    }

    private boolean askAnother() {
        String answer = view.readLine("Add another record? (y/n): ");
        return answer.toLowerCase().startsWith("y");
    }

    private String askName(String prompt) {
        while (true) {
            String typed = view.readLine(prompt);
            if (isProperName(typed)) {
                return typed;
            }
            view.showMessage("Please use letters only.");
        }
    }

    private boolean isProperName(String text) {
        boolean hasLetter = false;
        for (int k = 0; k < text.length(); k++) {
            char c = text.charAt(k);
            if (Character.isLetter(c)) {
                hasLetter = true;
            } else if (c != ' ' && c != '-' && c != '\'') {
                return false;
            }
        }
        return hasLetter;
    }

    private ManagerType askRole() {
        while (true) {
            String typed = view.readLine("Manager type: ");
            ManagerType role = ManagerType.fromString(typed);
            if (role == null) {
                view.showMessage("\"" + typed + "\" is not a valid manager type. Please choose one from the list.");
            } else if (role == ManagerType.PRINCIPAL && repository.roleTaken(role)) {
                view.showMessage("The school already has a Principal. Please choose another manager type.");
            } else {
                return role;
            }
        }
    }

    private Department askDepartment() {
        while (true) {
            String typed = view.readLine("Department: ");
            Department dept = Department.fromString(typed);
            if (dept != null) {
                return dept;
            }
            view.showMessage("\"" + typed + "\" is not a valid department. Please choose one from the list.");
        }
    }

    private void createBinaryTree() {
        List<Employee> staff = repository.getEmployees();
        if (staff.size() < TREE_MINIMUM) {
            view.showMessage("At least " + TREE_MINIMUM + " records are needed to build the tree.");
            return;
        }

        EmployeeTree tree = new EmployeeTree();
        tree.plantByRank(staff);

        view.showMessage("Staff structure by hierarchy:");
        view.printTreeLevels(tree.levels());
        view.showMessage("Tree height: " + tree.height());
        view.showMessage("Total nodes: " + tree.headcount());
    }
}
