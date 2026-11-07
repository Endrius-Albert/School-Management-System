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

    public void sortByName() {
        employees = mergeSort(employees);
    }

    private List<Employee> mergeSort(List<Employee> list) {
        if (list.size() <= 1) {
            return list;
        }

        int middle = list.size() / 2;
        List<Employee> left = mergeSort(new ArrayList<>(list.subList(0, middle)));
        List<Employee> right = mergeSort(new ArrayList<>(list.subList(middle, list.size())));

        return joinSorted(left, right);
    }

    private List<Employee> joinSorted(List<Employee> left, List<Employee> right) {
        List<Employee> result = new ArrayList<>();
        int i = 0;
        int j = 0;

        while (i < left.size() && j < right.size()) {
            if (nameOrder(left.get(i), right.get(j)) <= 0) {
                result.add(left.get(i));
                i++;
            } else {
                result.add(right.get(j));
                j++;
            }
        }

        while (i < left.size()) {
            result.add(left.get(i));
            i++;
        }

        while (j < right.size()) {
            result.add(right.get(j));
            j++;
        }

        return result;
    }

    private int nameOrder(Employee a, Employee b) {
        return a.getFullName().compareToIgnoreCase(b.getFullName());
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
