# School Management System

Java console application for a school. It reads the staff list from a text file and lets the user sort it, search it, add new staff and view the school hierarchy as a binary tree.

## How to run
1. Open the `SchoolSystem` folder in NetBeans (Maven project, JDK 17).
2. Run `SchoolSystem.java`.
3. When asked for the file name, type `Applicants_Form.txt`.

```
Please enter the filename to read: Applicants_Form.txt
File read successfully
46 employees loaded.

Do You wish to SORT or SEARCH:
1. SORT
2. SEARCH
3. ADD RECORDS
4. Create a binary tree
5. EXIT
```

## Features
| Option | What it does |
|---|---|
| **SORT** | Sorts all staff alphabetically with a recursive Merge Sort and shows the first 20 names. |
| **SEARCH** | Finds staff by name with a recursive Binary Search and shows their Manager Type, Department, Position, Email and Salary. Typing only part of a name (e.g. `John`) returns every match. |
| **ADD RECORDS** | Adds new staff (first name, last name, Manager Type, Department). Every input is checked, and all records added in the session are listed at the end. |
| **Create a binary tree** | Builds the staff hierarchy with level-order insertion, then shows the level-order traversal, the tree height and the total number of nodes. |
| **EXIT** | Closes the program. |

## Architecture (MVC)
| Layer | Package | Classes | Responsibility |
|---|---|---|---|
| Model | `ie.cct.schoolsystem.model` | `Employee`, `ManagerType`, `Department`, `EmployeeRepository`, `TreeNode`, `EmployeeTree` | Staff data, enums, sorting, searching and the binary tree |
| View | `ie.cct.schoolsystem.view` | `ConsoleView` | Console input and output only |
| Controller | `ie.cct.schoolsystem.controller` | `MenuController`, `MenuOption` | Menu flow based on the `MenuOption` enum; connects View and Model |

For example, in SEARCH the controller asks for a name through the view, calls `lookUp` in the model (binary search) and passes the result back to the view, which prints the staff profile. The model never prints anything itself.

## Algorithms and design choices
**Recursive Merge Sort** – it always runs in O(n log n), while Quick Sort can drop to O(n²) on data that is already almost sorted, which happens here after new staff are added. It is also stable, so people with the same name keep the order from the file. Bubble, Selection and Insertion Sort are O(n²) and do not scale well.

**Recursive Binary Search** – since the list is already sorted, each step discards half of it: O(log n), or at most 6 comparisons for 46 names. Linear search is O(n), and a HashMap would only find exact names without using the sorted list. Equal names sit next to each other after sorting, so the search also collects neighbours with the same name.

**Binary tree with level-order insertion** – each new node goes into the first free position, left before right, using a queue. Staff are inserted from the highest role to the lowest (the order of the `ManagerType` enum), so the Principal is always the root. This keeps the tree complete and balanced, whereas a binary search tree ordered by name would turn into a chain when the data is already sorted.
Height is counted in levels: an empty tree has height 0 and a single node has height 1. With 46 staff the tree has height 6.

**Enums** – `MenuOption`, `ManagerType` and `Department` keep the valid values in one place. The menu is printed from `MenuOption.values()` and handled with a `switch` on the enum, and `fromString` is used to validate user input.

## Data file
`Applicants_Form.txt` is based on the sample file provided and adapted to a school: the same nine columns, with school departments and roles and corrected emails. The `Company` column is not used.

## Validation
- If the file is not found or the name is empty, the program asks again.
- Lines with missing columns or an invalid salary, department or role are skipped and counted.
- Menu choices must be a number from the menu.
- Names can only contain letters, spaces, `-` and `'`, and must have at least one letter.
- Manager Type and Department must exist in the enums (letter case and extra spaces are ignored).
- The school can only have one Principal, and the same full name cannot be added twice.

## Testing
63 test cases were run, covering file loading, sorting, searching, adding records, the binary tree and the menu, and all of them pass. Some examples:

| Test | Expected result | Status |
|---|---|---|
| Sort 46 staff | Alphabetical order, nothing lost or duplicated | Pass |
| Search `John` | 3 matches | Pass |
| Search a name that does not exist | Not-found message | Pass |
| Add a second Principal | Rejected | Pass |
| Insert 1 / 3 / 7 / 20 nodes | Height 1 / 2 / 3 / 5 | Pass |
| Build tree with all staff | Principal at the root, height 6, 46 nodes | Pass |

## Future improvements
- Edit and remove staff records.
- Save new records back to the file, since they are currently kept in memory only.
- Give each employee a unique ID instead of relying on the full name.
- Keep the insertion queue between inserts, so the tree stays fast with very large staff lists.
