package smartlibrary;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * LibraryGUI.java
 * A simple JavaFX front-end for the Smart Library system.
 * This class ONLY handles the display (buttons, text fields, labels).
 * It calls the same SmartLibrary methods your group already wrote.
 *
 * HOW TO RUN:
 *   Make sure this file is inside the same "smartlibrary" package folder.
 *   Change your Main.java to:  new LibraryGUI().main(args);
 *   OR just right-click LibraryGUI.java → Run in your IDE.
 */
public class LibraryGUI extends Application {

    // The same SmartLibrary object your group built
    private SmartLibrary library = new SmartLibrary();

    // A text area at the bottom that shows all output messages
    private TextArea outputArea = new TextArea();

    // -------------------------------------------------------
    // start() is called automatically by JavaFX when it launches
    // -------------------------------------------------------
    @Override
    public void start(Stage stage) {

        // ----- Top: title label -----
        Label titleLabel = new Label("Smart Library System");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // ----- Build each section as its own box -----
        VBox addSection     = buildAddSection();
        VBox searchSection  = buildSearchSection();
        VBox borrowSection  = buildBorrowSection();
        VBox returnSection  = buildReturnSection();
        VBox viewSection    = buildViewSection();

        // ----- Output area at the bottom -----
        outputArea.setEditable(false);
        outputArea.setPrefHeight(180);
        outputArea.setWrapText(true);
        outputArea.setStyle("-fx-font-family: monospace; -fx-font-size: 13px;");
        Label outputLabel = new Label("Output:");
        outputLabel.setStyle("-fx-font-weight: bold;");

        // ----- Put all sections in a vertical column -----
        VBox root = new VBox(15,
            titleLabel,
            new Separator(),
            addSection,
            new Separator(),
            searchSection,
            new Separator(),
            borrowSection,
            new Separator(),
            returnSection,
            new Separator(),
            viewSection,
            new Separator(),
            outputLabel,
            outputArea
        );
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f4f4f4;");

        // ----- Create and show the window -----
        Scene scene = new Scene(root, 520, 780);
        stage.setTitle("Smart Library");
        stage.setScene(scene);
        stage.show();

        print("Welcome! The library system is ready.");
    }

    // -------------------------------------------------------
    // SECTION 1 — Add Book
    // -------------------------------------------------------
    private VBox buildAddSection() {
        Label heading = sectionLabel("Add Book");

        TextField isbnField   = new TextField();
        TextField titleField  = new TextField();
        TextField authorField = new TextField();
        isbnField.setPromptText("ISBN (number)");
        titleField.setPromptText("Title");
        authorField.setPromptText("Author");

        Button addBtn = new Button("Add Book");
        addBtn.setStyle(buttonStyle("#4CAF50"));

        // What happens when the button is clicked:
        addBtn.setOnAction(e -> {
            try {
                int isbn = Integer.parseInt(isbnField.getText().trim());
                String title  = titleField.getText().trim();
                String author = authorField.getText().trim();

                if (title.isEmpty() || author.isEmpty()) {
                    print("[!] Please fill in all fields.");
                    return;
                }

                // Capture console output by redirecting — OR just call and read state:
                // We call the existing method directly.
                library.addBook(isbn, title, author);
                print("addBook(" + isbn + ", \"" + title + "\", \"" + author + "\") called.");

                // Clear the fields after adding
                isbnField.clear();
                titleField.clear();
                authorField.clear();

            } catch (NumberFormatException ex) {
                print("[!] ISBN must be a number.");
            }
        });

        HBox fields = new HBox(8, isbnField, titleField, authorField);
        fields.setAlignment(Pos.CENTER_LEFT);
        isbnField.setPrefWidth(90);
        titleField.setPrefWidth(160);
        authorField.setPrefWidth(140);

        return section(heading, fields, addBtn);
    }

    // -------------------------------------------------------
    // SECTION 2 — Search Book
    // -------------------------------------------------------
    private VBox buildSearchSection() {
        Label heading = sectionLabel("Search Book by ISBN");

        TextField isbnField = new TextField();
        isbnField.setPromptText("ISBN (number)");
        isbnField.setPrefWidth(150);

        Button searchBtn = new Button("Search");
        searchBtn.setStyle(buttonStyle("#2196F3"));

        searchBtn.setOnAction(e -> {
            try {
                int isbn = Integer.parseInt(isbnField.getText().trim());
                // searchBook() prints to console; we mirror it in our output area
                print("--- Searching ISBN: " + isbn + " ---");
                library.searchBook(isbn);
                isbnField.clear();
            } catch (NumberFormatException ex) {
                print("[!] ISBN must be a number.");
            }
        });

        HBox row = new HBox(8, isbnField, searchBtn);
        return section(heading, row);
    }

    // -------------------------------------------------------
    // SECTION 3 — Borrow Book
    // -------------------------------------------------------
    private VBox buildBorrowSection() {
        Label heading = sectionLabel("Borrow Book");

        TextField isbnField = new TextField();
        isbnField.setPromptText("ISBN (number)");
        isbnField.setPrefWidth(150);

        Button borrowBtn = new Button("Borrow");
        borrowBtn.setStyle(buttonStyle("#FF9800"));

        borrowBtn.setOnAction(e -> {
            try {
                int isbn = Integer.parseInt(isbnField.getText().trim());
                print("--- Borrowing ISBN: " + isbn + " ---");
                library.borrowBook(isbn);
                isbnField.clear();
            } catch (NumberFormatException ex) {
                print("[!] ISBN must be a number.");
            }
        });

        HBox row = new HBox(8, isbnField, borrowBtn);
        return section(heading, row);
    }

    // -------------------------------------------------------
    // SECTION 4 — Return Book
    // -------------------------------------------------------
    private VBox buildReturnSection() {
        Label heading = sectionLabel("Return Book");

        TextField isbnField = new TextField();
        isbnField.setPromptText("ISBN (number)");
        isbnField.setPrefWidth(150);

        Button returnBtn = new Button("Return");
        returnBtn.setStyle(buttonStyle("#9C27B0"));

        returnBtn.setOnAction(e -> {
            try {
                int isbn = Integer.parseInt(isbnField.getText().trim());
                print("--- Returning ISBN: " + isbn + " ---");
                library.returnBook(isbn);
                isbnField.clear();
            } catch (NumberFormatException ex) {
                print("[!] ISBN must be a number.");
            }
        });

        HBox row = new HBox(8, isbnField, returnBtn);
        return section(heading, row);
    }

    // -------------------------------------------------------
    // SECTION 5 — View / History / Clear buttons
    // -------------------------------------------------------
    private VBox buildViewSection() {
        Label heading = sectionLabel("View & Manage");

        Button catalogueBtn   = new Button("Display All Books");
        Button historyBtn     = new Button("View History");
        Button clearHistBtn   = new Button("Clear History");
        Button resetBtn       = new Button("Reset Everything");

        catalogueBtn.setStyle(buttonStyle("#607D8B"));
        historyBtn.setStyle(buttonStyle("#00BCD4"));
        clearHistBtn.setStyle(buttonStyle("#FF5722"));
        resetBtn.setStyle(buttonStyle("#F44336"));

        catalogueBtn.setOnAction(e -> {
            print("--- Displaying all books ---");
            library.displayAllBooks();
        });

        historyBtn.setOnAction(e -> {
            print("--- Borrow history ---");
            library.viewLatestHistory();
        });

        clearHistBtn.setOnAction(e -> {
            // Ask the user to confirm before clearing
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to clear borrow history?",
                ButtonType.YES, ButtonType.NO);
            confirm.setTitle("Confirm");
            confirm.showAndWait().ifPresent(btn -> {
                if (btn == ButtonType.YES) {
                    library.clearHistory();
                    print("[System] History cleared.");
                }
            });
        });

        resetBtn.setOnAction(e -> {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "WARNING: This will delete ALL books and history!",
                ButtonType.YES, ButtonType.NO);
            confirm.setTitle("Are you sure?");
            confirm.showAndWait().ifPresent(btn -> {
                if (btn == ButtonType.YES) {
                    library.clearAllBooks();
                    print("[System] System reset.");
                }
            });
        });

        HBox row = new HBox(8, catalogueBtn, historyBtn, clearHistBtn, resetBtn);
        row.setAlignment(Pos.CENTER_LEFT);
        return section(heading, row);
    }

    // -------------------------------------------------------
    // Helper: print a message to the output box
    // Note: SmartLibrary methods print to System.out (the console).
    //       You will still see their output in the console window.
    //       This method adds extra context lines to the GUI output box.
    // -------------------------------------------------------
    private void print(String message) {
        outputArea.appendText(message + "\n");
    }

    // -------------------------------------------------------
    // Helper: create a styled section heading label
    // -------------------------------------------------------
    private Label sectionLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #333;");
        return label;
    }

    // -------------------------------------------------------
    // Helper: wrap content into a neat white box (VBox)
    // -------------------------------------------------------
    private VBox section(Label heading, javafx.scene.Node... children) {
        VBox box = new VBox(8);
        box.getChildren().add(heading);
        for (javafx.scene.Node child : children) {
            box.getChildren().add(child);
        }
        box.setPadding(new Insets(12));
        box.setStyle("-fx-background-color: white; -fx-border-color: #ddd; "
                   + "-fx-border-radius: 6; -fx-background-radius: 6;");
        return box;
    }

    // -------------------------------------------------------
    // Helper: simple coloured button style string
    // -------------------------------------------------------
    private String buttonStyle(String colour) {
        return "-fx-background-color: " + colour + "; "
             + "-fx-text-fill: white; "
             + "-fx-font-weight: bold; "
             + "-fx-background-radius: 4; "
             + "-fx-cursor: hand;";
    }

    // -------------------------------------------------------
    // main() — entry point (needed if you run this file directly)
    // -------------------------------------------------------
    public static void main(String[] args) {
        launch(args);
    }
}