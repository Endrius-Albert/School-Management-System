package ie.cct.schoolsystem.model;

public class Employee {

    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private double salary;
    private Department department;
    private String position;
    private ManagerType managerType;

    public Employee(String firstName, String lastName, String gender, String email,
                    double salary, Department department, String position,
                    ManagerType managerType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.salary = salary;
        this.department = department;
        this.position = position;
        this.managerType = managerType;
    }

    // Constructor used when the user adds a new employee from the menu
    public Employee(String firstName, String lastName, Department department,
                    ManagerType managerType) {
        this(firstName, lastName, "", "", 0.0, department, "", managerType);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public double getSalary() {
        return salary;
    }

    public Department getDepartment() {
        return department;
    }

    public String getPosition() {
        return position;
    }

    public ManagerType getManagerType() {
        return managerType;
    }

    @Override
    public String toString() {
        return String.format("%-22s | %-20s | %-18s", getFullName(), managerType, department);
    }
}
