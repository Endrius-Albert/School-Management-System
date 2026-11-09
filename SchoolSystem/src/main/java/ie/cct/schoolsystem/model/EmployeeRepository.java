package ie.cct.schoolsystem.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {

    private List<Employee> employees = new ArrayList<>();
    private int invalidLines = 0;
    private boolean inOrder = false;
    private List<Employee> freshlyAdded = new ArrayList<>();

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
        inOrder = true;
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

    // Returns every employee whose name starts with the typed text
    public List<Employee> lookUp(String typed) {
        List<Employee> found = new ArrayList<>();
        if (typed == null || typed.trim().isEmpty()) {
            return found;
        }
        if (!inOrder) {
            sortByName();
        }

        String query = typed.trim();
        int hit = binarySearch(query, 0, employees.size() - 1);
        if (hit == -1) {
            return found;
        }

        // Matches are next to each other in a sorted list
        int first = hit;
        while (first > 0 && matchOrder(employees.get(first - 1), query) == 0) {
            first--;
        }
        int last = hit;
        while (last < employees.size() - 1 && matchOrder(employees.get(last + 1), query) == 0) {
            last++;
        }

        for (int k = first; k <= last; k++) {
            found.add(employees.get(k));
        }
        return found;
    }

    private int binarySearch(String query, int low, int high) {
        if (low > high) {
            return -1;
        }

        int mid = (low + high) / 2;
        int result = matchOrder(employees.get(mid), query);

        if (result == 0) {
            return mid;
        } else if (result < 0) {
            return binarySearch(query, mid + 1, high);
        } else {
            return binarySearch(query, low, mid - 1);
        }
    }

    // Compares only the beginning of the name, with the same length as the query
    private int matchOrder(Employee employee, String query) {
        String name = employee.getFullName();
        if (name.length() > query.length()) {
            name = name.substring(0, query.length());
        }
        return name.compareToIgnoreCase(query);
    }

    // Adds a new employee and sorts the list again so the search keeps working
    public void addStaff(Employee employee) {
        employees.add(employee);
        freshlyAdded.add(employee);
        sortByName();
    }

    public List<Employee> getFreshlyAdded() {
        return freshlyAdded;
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
