package ie.cct.schoolsystem.model;

public enum ManagerType {
    PRINCIPAL("Principal"),
    DEPUTY_PRINCIPAL("Deputy Principal"),
    HEAD_OF_DEPARTMENT("Head of Department"),
    SENIOR_TEACHER("Senior Teacher"),
    TEACHER("Teacher"),
    COORDINATOR("Coordinator"),
    ADMINISTRATOR("Administrator"),
    ASSISTANT("Assistant");

    private final String displayName;

    ManagerType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    // Returns null when the text does not match any manager type
    public static ManagerType fromString(String text) {
        if (text == null) {
            return null;
        }
        for (ManagerType type : ManagerType.values()) {
            if (type.displayName.equalsIgnoreCase(text.trim())) {
                return type;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
