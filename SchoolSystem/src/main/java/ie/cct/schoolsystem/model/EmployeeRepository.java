package ie.cct.schoolsystem.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {

    private List<Employee> employees = new ArrayList<>();
    private int invalidLines = 0;

    // Reads the file, skipping the header and blank lines.
    // Throws IOException if the file cannot be found or read.
    public void loadFromFile(String filename) throws IOException {
        employees.clear();
        invalidLines = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine(); // header line

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                Employee employee = parseLine(line);
                if (employee != null) {
                    employees.add(employee);
                } else {
                    invalidLines++;
                }
            }
        }
    }

    // Converts one line of the file into an Employee.
    // Returns null if the line is incomplete or has invalid data.
    private Employee parseLine(String line) {
        String[] parts = line.split(",", -1);
        if (parts.length < 8) {
            return null;
        }

        String firstName = parts[0].trim();
        String lastName = parts[1].trim();
        String gender = parts[2].trim();
        String email = parts[3].trim();
        String position = parts[6].trim();
        Department department = Department.fromString(parts[5]);
        ManagerType managerType = ManagerType.fromString(parts[7]);

        if (firstName.isEmpty() || department == null || managerType == null) {
            return null;
        }

        double salary;
        try {
            salary = Double.parseDouble(parts[4].trim());
        } catch (NumberFormatException e) {
            return null;
        }

        return new Employee(firstName, lastName, gender, email, salary,
                department, position, managerType);
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public int size() {
        return employees.size();
    }

    public int getInvalidLines() {
        return invalidLines;
    }
}
