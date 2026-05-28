# Smart Library System

A simple library management system built in Java using a Binary Search Tree (BST) for the book catalogue and a Stack for borrow history.

---

## Project files

| File | What it does |
|---|---|
| `Book.java` | The `Book` class (data: isbn, title, author, isBorrowed) and `HistoryNode` for the stack |
| `BookBST.java` | Binary Search Tree — stores and searches books by ISBN |
| `BorrowStack.java` | Stack (linked list) — tracks borrow history in LIFO order |
| `LibraryADT.java` | Interface that defines what the library must be able to do |
| `SmartLibrary.java` | Main logic — implements `LibraryADT`, handles all operations + file save/load |
| `LibraryGUI.java` | Optional JavaFX GUI front-end |
| `Main.java` | Entry point — runs the console menu |

---

## How to run

### Console version
Run `Main.java`. A text menu will appear in the terminal.

### GUI version (JavaFX)

**Step 1 — Install JDK 21**

Download the **ARM64 DMG Installer** (Apple Silicon) or **x64 DMG Installer** (Intel) from:
https://www.oracle.com/java/technologies/downloads/#java21

**Step 2 — Download JavaFX 21 SDK**

Download **JavaFX 21 SDK** for your Mac from:
https://gluonhq.com/products/javafx/

Extract it to your Downloads folder. The folder will be named something like `javafx-sdk-21.0.11`.

**Step 3 — Configure VS Code**

Add the JavaFX jars to `.vscode/settings.json`:

```json
{
    "java.project.referencedLibraries": [
        "/Users/YOUR_USERNAME/Downloads/javafx-sdk-21.0.11/lib/*.jar"
    ]
}
```

**Step 4 — Configure the run launcher**

Create `.vscode/launch.json` with the following (replace the path with your actual JavaFX folder name):

```json
{
    "version": "0.2.0",
    "configurations": [
        {
            "type": "java",
            "name": "LibraryGUI",
            "request": "launch",
            "mainClass": "smartlibrary.LibraryGUI",
            "vmArgs": "--module-path /Users/YOUR_USERNAME/Downloads/javafx-sdk-21.0.11/lib --add-modules javafx.controls"
        }
    ]
}
```

> **Tip:** To find your exact JavaFX folder name, run `ls ~/Downloads | grep javafx` in the terminal.

**Step 5 — Clean and reload**

Press `Cmd+Shift+P` → `Java: Clean Java Language Server Workspace` → **Reload and delete**.

Then run `LibraryGUI.java` using the Run and Debug panel.

---

## Features

| # | Feature | Menu option |
|---|---|---|
| 1 | Add a book (checks for duplicate ISBN and title) | 1 |
| 2 | Search for a book by ISBN | 2 |
| 3 | Borrow a book | 3 |
| 4 | Return a book | 4 |
| 5 | View borrow history (newest first) | 5 |
| 6 | Display full catalogue (sorted by ISBN) | 6 |
| 7 | Clear borrow history | 7 |
| 8 | Reset entire system | 8 |
| 9 | Save & exit | 9 |

---

## Data structures used

**BookBST — Binary Search Tree**
- Books are stored by ISBN
- Smaller ISBN goes left, larger goes right
- Search is O(log n) on average
- Used for: add, search, borrow, return, display

**BorrowStack — Stack (linked list)**
- Each borrow is pushed onto the top
- History is shown newest-first (LIFO — Last In, First Out)
- Internally built using `HistoryNode` objects chained together

---

## Data persistence

The system automatically saves and loads data from two plain text files:

- `books.txt` — one book per line: `isbn;title;author;isBorrowed`
- `history.txt` — one ISBN per line, oldest first

These files are created in the same folder as the program when you choose **Save & Exit** (option 9).

---

## Package

All files belong to the `smartlibrary` package. Make sure every `.java` file starts with:

```java
package smartlibrary;
```

and that all files sit inside a folder named `smartlibrary`.

---

## Requirements

- Java 11 or higher
- JavaFX SDK (only needed for the GUI version)
