package ie.cct.schoolsystem.controller;

import ie.cct.schoolsystem.model.EmployeeRepository;
import ie.cct.schoolsystem.view.ConsoleView;
import java.io.IOException;

public class MenuController {

    private ConsoleView view = new ConsoleView();
    private EmployeeRepository repository = new EmployeeRepository();

    public void start() {
        loadFile();
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
}
