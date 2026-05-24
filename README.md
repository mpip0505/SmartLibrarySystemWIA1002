# SmartLibrarySystemWIA1002

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
Run `LibraryGUI.java` directly, **or** change `Main.java` to:

```java
import javafx.application.Application;

public class Main {
    public static void main(String[] args) {
        Application.launch(LibraryGUI.class, args);
    }
}
```

> **Note:** JavaFX must be added to your project. In IntelliJ: File → Project Structure → Libraries → add the JavaFX SDK.

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
