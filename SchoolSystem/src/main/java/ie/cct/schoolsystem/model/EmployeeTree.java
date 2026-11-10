package ie.cct.schoolsystem.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class EmployeeTree {

    private TreeNode root;

    // Inserts staff from the highest role to the lowest, so the Principal sits at the top
    public void plantByRank(List<Employee> staff) {
        for (ManagerType role : ManagerType.values()) {
            for (Employee e : staff) {
                if (e.getManagerType() == role) {
                    insert(e);
                }
            }
        }
    }

    // Level-order insertion: the new node goes into the first free spot, left before right
    public void insert(Employee e) {
        TreeNode node = new TreeNode(e.getFullName(), e.getManagerType(), e.getDepartment());

        if (root == null) {
            root = node;
            return;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();

            if (current.getLeft() == null) {
                current.setLeft(node);
                return;
            }
            queue.add(current.getLeft());

            if (current.getRight() == null) {
                current.setRight(node);
                return;
            }
            queue.add(current.getRight());
        }
    }

    // Level-order traversal, grouping the nodes of each level together
    public List<List<TreeNode>> levels() {
        List<List<TreeNode>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            int nodesOnLevel = queue.size();
            List<TreeNode> level = new ArrayList<>();

            for (int k = 0; k < nodesOnLevel; k++) {
                TreeNode current = queue.poll();
                level.add(current);

                if (current.getLeft() != null) {
                    queue.add(current.getLeft());
                }
                if (current.getRight() != null) {
                    queue.add(current.getRight());
                }
            }
            result.add(level);
        }
        return result;
    }

    public int height() {
        return heightFrom(root);
    }

    private int heightFrom(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(heightFrom(node.getLeft()), heightFrom(node.getRight()));
    }

    public int headcount() {
        return countFrom(root);
    }

    private int countFrom(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + countFrom(node.getLeft()) + countFrom(node.getRight());
    }
}
