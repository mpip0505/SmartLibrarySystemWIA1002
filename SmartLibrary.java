//package smartlibrary;

import java.io.*;
import java.util.Scanner;

public class SmartLibrary implements LibraryADT {
    // Information Hiding: Internal structures are private
    private BookBST catalogue = new BookBST();
    private BorrowStack history = new BorrowStack();

    // Constructor: Automatically loads data when the system starts
    public SmartLibrary() {
        loadData();
    }
    
    @Override
    public void addBook(int isbn, String title, String author) {
        // 1. Check for Duplicate ISBN
        if (catalogue.search(isbn) != null) {
            System.out.println("\n[!] Error: ISBN " + isbn + " already exists.");
            return;
        }

        // 2. Check for Duplicate Title
        if (catalogue.containsTitle(title)) {
            System.out.println("\n[!] Error: Book titled \"" + title + "\" already exists.");
            return;
        }

        // 3. Success
        catalogue.insert(isbn, title, author);
        System.out.println("Book added to catalogue successfully.");
    }

    @Override
    public void searchBook(int isbn) {
        Book b = catalogue.search(isbn);
        if (b != null) {
            System.out.println("\n--- Book Found ---");
            System.out.println("Title : " + b.title);
            System.out.println("Author: " + b.author);
        } else {
            System.out.println("\n[!] Error: Book with ISBN " + isbn + " not found.");
        }
    }

    @Override
    public void borrowBook(int isbn) {
        Book b = catalogue.search(isbn);
        if (b == null) {
            System.out.println("[!] Error: Book not found.");
            return;
        }

        if (b.isBorrowed) {
            System.out.println("[!] Error: \"" + b.title + "\" is already borrowed and has not been returned yet.");
        } else {
            b.isBorrowed = true; // Change state
            history.push(b);
            System.out.println("Success! You have borrowed: " + b.title);
        }
    }
    
    public void returnBook(int isbn) {
        Book b = catalogue.search(isbn);
        if (b == null) {
            System.out.println("[!] Error: This book does not belong to our library.");
            return;
        }

        if (!b.isBorrowed) {
            System.out.println("[!] Notice: This book was already on the shelf.");
        } else {
            b.isBorrowed = false; // Change state
            System.out.println("Success! Thank you for returning: " + b.title);
        }
    }

    @Override
    public void viewLatestHistory() {
        history.show();
    }
    @Override
    public void displayAllBooks() {
        catalogue.displayAll();
    }

    @Override
    public void clearHistory() {
        history.clear();
    }

    @Override
    public void clearAllBooks() {
        catalogue.clear();
        history.clear(); // History must be cleared too, as it references deleted books
        System.out.println("[System] All library data has been cleared.");
    }

   // --- FILE I/O LOGIC ---
    private void loadData() {
        // 1. Load Books
        try (BufferedReader br = new BufferedReader(new FileReader("books.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim(); // Remove extra spaces
                if (line.isEmpty()) continue; // Skip completely blank lines

                // Inside the while loop for books:
                String[] parts = line.split(";");
                if (parts.length >= 3) {
                    int isbn = Integer.parseInt(parts[0]);
                    catalogue.insert(isbn, parts[1], parts[2]);

                    // If the file has the 4th column (status), update the book
                    if (parts.length == 4) {
                        Book b = catalogue.search(isbn);
                        if (b != null) b.isBorrowed = Boolean.parseBoolean(parts[3]);
                    }
                }
            }
            System.out.println("[System] Books loaded successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("[System] No existing book data found. Starting fresh.");
        } catch (IOException e) {
            System.out.println("[!] Error reading books data.");
        }

        // 2. Load History
        try (BufferedReader br = new BufferedReader(new FileReader("history.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim(); // Remove extra spaces
                if (line.isEmpty()) continue; // Skip completely blank lines

                try {
                    int isbn = Integer.parseInt(line);
                    Book b = catalogue.search(isbn); // Find the actual book object
                    if (b != null) {
                        history.push(b);
                    }
                } catch (NumberFormatException e) {
                    // Silently skip any badly formatted history lines
                }
            }
            System.out.println("[System] History loaded successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("[System] No existing history data found.");
        } catch (IOException e) {
            System.out.println("[!] Error reading history data.");
        }
    }

    private void saveData() {
        // 1. Save Books
        try (PrintWriter out = new PrintWriter(new FileWriter("books.txt"))) {
            catalogue.saveToFile(out);
        } catch (IOException e) {
            System.out.println("[!] Error saving books.");
        }

        // 2. Save History
        try (PrintWriter out = new PrintWriter(new FileWriter("history.txt"))) {
            history.saveToFile(out);
        } catch (IOException e) {
            System.out.println("[!] Error saving history.");
        }
    }

    // --- CONSOLE INTERFACE LOGIC ---
   public void runMenu() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- SMART LIBRARY MENU ---");
            System.out.println("1. Add Book");
            System.out.println("2. Search Book");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");        // New
            System.out.println("5. View History");       // Shifted
            System.out.println("6. Display Catalogue");  // Shifted
            System.out.println("7. Clear History");      // Shifted
            System.out.println("8. Reset System");       // Shifted
            System.out.println("9. Save & Exit");        // New Exit Number
            System.out.print("Choice: ");

            try {
                int choice = Integer.parseInt(sc.nextLine());
                if (choice == 9) { // Now choice 9 is exit
                    saveData();
                    System.out.println("Data saved. Goodbye!");
                    break;
                }
                handleChoice(choice, sc);
            } catch (NumberFormatException e) {
                System.out.println("[!] Invalid input. Enter a number (1-9).");
            }
        }
        sc.close();
    }

   private void handleChoice(int choice, Scanner sc) {
        switch (choice) {
            case 1: // Add Book
                try {
                    System.out.print("Enter ISBN: ");
                    int isbn = Integer.parseInt(sc.nextLine());
                    System.out.print("Enter Title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter Author: ");
                    String author = sc.nextLine();
                    addBook(isbn, title, author);
                } catch (NumberFormatException e) {
                    System.out.println("[!] Error: ISBN must be a number.");
                }
                break;

            case 2: // Search Book
                try {
                    System.out.print("Enter ISBN to search: ");
                    searchBook(Integer.parseInt(sc.nextLine()));
                } catch (NumberFormatException e) {
                    System.out.println("[!] Error: Please enter a valid numeric ISBN.");
                }
                break;

            case 3: // Borrow Book
                try {
                    System.out.print("Enter ISBN to borrow: ");
                    borrowBook(Integer.parseInt(sc.nextLine()));
                } catch (NumberFormatException e) {
                    System.out.println("[!] Error: Please enter a valid numeric ISBN.");
                }
                break;

            case 4: // Return Book (NEW)
                try {
                    System.out.print("Enter ISBN to return: ");
                    returnBook(Integer.parseInt(sc.nextLine()));
                } catch (NumberFormatException e) {
                    System.out.println("[!] Error: Please enter a valid numeric ISBN.");
                }
                break;

            case 5: // View History
                viewLatestHistory();
                break;

            case 6: // Display Full Catalogue
                displayAllBooks();
                break;

            case 7: // Clear History
                System.out.print("Are you sure you want to wipe borrowing history? (y/n): ");
                if (sc.nextLine().equalsIgnoreCase("y")) {
                    clearHistory();
                }
                break;

            case 8: // Reset Entire System
                System.out.println("!!! WARNING: THIS WILL DELETE ALL BOOKS AND HISTORY !!!");
                System.out.print("Are you absolutely sure? (y/n): ");
                if (sc.nextLine().equalsIgnoreCase("y")) {
                    clearAllBooks();
                }
                break;

            default:
                System.out.println("[!] Invalid option. Please choose 1-9.");
        }
    }
}


