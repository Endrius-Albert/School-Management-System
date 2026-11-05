package ie.cct.schoolsystem.model;

public enum Department {
    MATHEMATICS("Mathematics"),
    SCIENCE("Science"),
    ENGLISH("English"),
    LANGUAGES("Languages"),
    COMPUTER_SCIENCE("Computer Science"),
    ARTS("Arts"),
    PHYSICAL_EDUCATION("Physical Education"),
    ADMINISTRATION("Administration"),
    FINANCE("Finance"),
    STUDENT_SUPPORT("Student Support");

    private final String displayName;

    Department(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    // Returns null when the text does not match any department
    public static Department fromString(String text) {
        if (text == null) {
            return null;
        }
        for (Department dept : Department.values()) {
            if (dept.displayName.equalsIgnoreCase(text.trim())) {
                return dept;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
