package ie.cct.schoolsystem.model;

public class TreeNode {

    private String name;
    private ManagerType managerType;
    private Department department;
    private TreeNode left;
    private TreeNode right;

    public TreeNode(String name, ManagerType managerType, Department department) {
        this.name = name;
        this.managerType = managerType;
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public ManagerType getManagerType() {
        return managerType;
    }

    public Department getDepartment() {
        return department;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return String.format("%-22s | %-20s | %-18s", name, managerType, department);
    }
}
