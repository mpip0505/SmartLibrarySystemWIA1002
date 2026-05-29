package smartlibrary;

public class BorrowStack {
    private HistoryNode top; // The "Top" of our manual stack

    public BorrowStack() {
        this.top = null;
    }

    // Manual Push: O(1) complexity
    public void push(Book b) {
        if (b == null) return;
        
        HistoryNode newNode = new HistoryNode(b);
        newNode.next = top; // New node points to the old top
        top = newNode;      // New node becomes the current top
    }

    // Manual Show: Iterates through the linked list to show history
    public void show() {
        if (top == null) {
            System.out.println("\n[!] History is empty.");
            return;
        }

        System.out.println("\n--- Borrowing History (LIFO Order) ---");
        HistoryNode current = top;
        while (current != null) {
            System.out.println("[ISBN: " + current.data.isbn + "] " + current.data.title);
            current = current.next; // Move to the next (older) activity
        }
    }
    // --- FILE I/O LOGIC FOR STACK ---
    public void saveToFile(java.io.PrintWriter writer) {
        saveRecursive(top, writer);
    }

    private void saveRecursive(HistoryNode current, java.io.PrintWriter writer) {
        if (current == null) return;
        
        // Go to the bottom of the stack first
        saveRecursive(current.next, writer);
        
        // Write the oldest records first, so when we push() them back, 
        // the newest ends up on top where it belongs.
        writer.println(current.data.isbn);
    }
    public void clear() {
        top = null;
        System.out.println("[System] Borrowing history has been wiped.");
    }
}
