# School Management System

Java console application for a school.

## Features
1. **SORT** – reads `Applicants_Form.txt`, sorts employees alphabetically with a recursive sorting algorithm and shows the first 20.
2. **SEARCH** – recursive search by name, returning Manager Type and Department.
3. **ADD RECORDS** – add a new employee with validated Manager Type and Department.
4. **CREATE BINARY TREE** – builds the school hierarchy using level-order insertion, then shows level-order traversal, tree height and node count.
5. **EXIT**

## Architecture (MVC)
| Layer | Package | Classes | Responsibility |
|---|---|---|---|
| Model | `ie.cct.schoolsystem.model` | `Employee`, `ManagerType`, `Department`, `EmployeeRepository`, `TreeNode`, `EmployeeTree` | Employee data, enums, sorting/searching, binary tree |
| View | `ie.cct.schoolsystem.view` | `ConsoleView` | Console input/output only |
| Controller | `ie.cct.schoolsystem.controller` | `MenuController`, `MenuOption` | Menu flow (enum-based), connects View and Model |

## How to run
Open the `SchoolSystem` folder in NetBeans (Maven project) and run `SchoolSystem.java`.
When asked for the file name, type `Applicants_Form.txt`.
