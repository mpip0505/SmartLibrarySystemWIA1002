package smartlibrary;

public class BookBST {
    private Book root;

    // Public method for other members to call
    public void insert(int isbn, String title, String author) {
        root = ins(root, isbn, title, author);
    }
    
    // Public method to check if a title exists
     
    public boolean containsTitle(String title) {
        return checkTitleRecursive(root, title);
    }

    // Private recursive helper to check all nodes
    private boolean checkTitleRecursive(Book current, String title) {
        if (current == null) {
            return false; // Reached the end of a branch, title not found here
        }
        
        // If the current book's title matches (ignoring case), we found a duplicate
        if (current.title.equalsIgnoreCase(title)) {
            return true;
        }
        
        // Otherwise, keep searching in both the left and right subtrees
        return checkTitleRecursive(current.left, title) || checkTitleRecursive(current.right, title);
    }

    // Private recursive helper to find the correct spot in the tree
    private Book ins(Book current, int isbn, String title, String author) {
        // 1. If the current spot is empty, create the new book here
        if (current == null) {
            return new Book(isbn, title, author);
        }

        // 2. If the new ISBN is smaller, go to the left subtree
        if (isbn < current.isbn) {
            current.left = ins(current.left, isbn, title, author);
        } 
        // 3. If the new ISBN is larger, go to the right subtree
        else if (isbn > current.isbn) {
            current.right = ins(current.right, isbn, title, author);
        }

        // Return the (potentially updated) node pointer
        return current;
    }

    // Member B will use this getter to access the root for searching
    public Book getRoot() {
        return root;
    }
    
    
    
    // Logic to be placed inside the BookBST class
    public Book search(int targetIsbn) {
        return sea(root, targetIsbn); // Calls the recursive helper
    }

    private Book sea(Book current, int targetIsbn) {
        // Base Case: Book not found or ISBN matches
        if (current == null || current.isbn == targetIsbn) {
            return current;
        }

        // Logic: If target is smaller, search the left subtree
        if (targetIsbn < current.isbn) {
            return sea(current.left, targetIsbn);
        } 
        // Logic: If target is larger, search the right subtree
        else {
            return sea(current.right, targetIsbn);
        }
    }
    public void saveToFile(java.io.PrintWriter writer) {
        savePreOrder(root, writer);
    }

    private void savePreOrder(Book current, java.io.PrintWriter writer) {
        if (current != null) {
            writer.println(current.isbn + ";" + current.title + ";" + current.author + ";" + current.isBorrowed);
            savePreOrder(current.left, writer);
            savePreOrder(current.right, writer);
        }
    }
    // --- DISPLAY ALL LOGIC ---
    public void displayAll() {
        if (root == null) {
            System.out.println("[!] The catalogue is currently empty.");
            return;
        }
        System.out.println("\n--- Full Library Catalogue (Sorted by ISBN) ---");
        inOrder(root);
    }

    private void inOrder(Book current) {
        if (current != null) {
            inOrder(current.left);
            System.out.println("ISBN: " + current.isbn + " | Title: " + current.title + " | Author: " + current.author);
            inOrder(current.right);
        }
    }

    // --- CLEAR LOGIC ---
    public void clear() {
        root = null; // Let the Java Garbage Collector do the heavy lifting
    }
}
