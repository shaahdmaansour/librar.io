import java.awt.event.ActionEvent;
import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.Date;
import javax.swing.*;
import java.awt.*;

public class SystemGUI {

    private JFrame mainFrame;
    private Library library;

    public SystemGUI() {
        int libraryCapacity = getLibraryCapacityFromUser();
        library = new Library(libraryCapacity);
        createMainFrame();
    }

    private int getLibraryCapacityFromUser() {
        String capacityInput = JOptionPane.showInputDialog(null, "Enter the capacity of the library:", "Welcome to our library system", JOptionPane.QUESTION_MESSAGE);


        if (capacityInput == null) {
            System.exit(0);
        }

        try {

            return Integer.parseInt(capacityInput);
        }

        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Default capacity set to 100.", "Error", JOptionPane.ERROR_MESSAGE);
            return 100;
        }
    }

    private void createMainFrame() {
        mainFrame = new JFrame("Library System");
        mainFrame.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton addBookButton = new JButton("Add Book");
        addBookButton.addActionListener(this::openAddBookWindow);
        JButton removeBookButton = new JButton("Remove Book");
        removeBookButton.addActionListener(this::openRemoveBookWindow);
        JButton borrowBookButton = new JButton("Borrow Book");
        borrowBookButton.addActionListener(this::openBorrowBookWindow);
        JButton returnBookButton = new JButton("Return Book");
        returnBookButton.addActionListener(this::openReturnBookWindow);
        JButton viewStatusButton = new JButton("View Library Status");
        viewStatusButton.addActionListener(this::openViewStatusWindow);

        buttonPanel.add(addBookButton);
        buttonPanel.add(removeBookButton);
        buttonPanel.add(borrowBookButton);
        buttonPanel.add(returnBookButton);
        buttonPanel.add(viewStatusButton);

        mainFrame.add(buttonPanel, BorderLayout.CENTER);

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(400, 300);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private void openAddBookWindow(ActionEvent e) {
        JFrame addBookFrame = new JFrame("Add Book");
        addBookFrame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField bookNameField = new JTextField();
        JComboBox<String> categoryComboBox = new JComboBox<>(new String[]{"Biology", "Maths", "History", "Chemistry", "Politics"});
        JButton addButton = new JButton("Add");
        addButton.addActionListener(actionEvent -> {
            String name = bookNameField.getText();
            String category = (String) categoryComboBox.getSelectedItem();

            if (!name.isEmpty()) {
                String bookId = library.addBook(name, category);

                if (bookId != null) {
                    JOptionPane.showMessageDialog(addBookFrame, "Book added successfully!\nBook ID: " + bookId);
                    addBookFrame.dispose();
                }

                else {
                    JOptionPane.showMessageDialog(addBookFrame, "Library is full. Book not added.");
                }
            }

            else {
                JOptionPane.showMessageDialog(addBookFrame, "Please enter book name.");
            }
        });

        inputPanel.add(new JLabel("Book Name:"));
        inputPanel.add(bookNameField);
        inputPanel.add(new JLabel("Book Category:"));
        inputPanel.add(categoryComboBox);
        inputPanel.add(new JPanel());
        inputPanel.add(addButton);

        addBookFrame.add(inputPanel, BorderLayout.CENTER);

        addBookFrame.setSize(300, 150);
        addBookFrame.setLocationRelativeTo(mainFrame);
        addBookFrame.setVisible(true);
    }

    private void openRemoveBookWindow(ActionEvent e) {
        JFrame removeBookFrame = new JFrame("Remove Book");
        removeBookFrame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField bookIdField = new JTextField();
        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(actionEvent -> {
            String bookId = bookIdField.getText();

            if (!bookId.isEmpty()) {

                if (library.containsBook(bookId)) { // Check if the book ID exists
                    library.removeBook(bookId);
                    JOptionPane.showMessageDialog(removeBookFrame, "Book removed successfully!");
                    removeBookFrame.dispose();
                }

                else {
                    JOptionPane.showMessageDialog(removeBookFrame, "There is no such book to be removed.");
                }
            }

            else {
                JOptionPane.showMessageDialog(removeBookFrame, "Please enter book ID to remove.");
            }
        });

        inputPanel.add(new JLabel("Book ID to Remove:"));
        inputPanel.add(bookIdField);
        inputPanel.add(new JPanel());
        inputPanel.add(removeButton);

        removeBookFrame.add(inputPanel, BorderLayout.CENTER);

        removeBookFrame.setSize(300, 120);
        removeBookFrame.setLocationRelativeTo(mainFrame);
        removeBookFrame.setVisible(true);
    }

    private void openBorrowBookWindow(ActionEvent e) {
        JFrame borrowBookFrame = new JFrame("Borrow Book");
        borrowBookFrame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField bookIdField = new JTextField();
        JTextField borrowingPeriodField = new JTextField();
        JButton borrowButton = new JButton("Borrow");
        borrowButton.addActionListener(actionEvent -> {
            String bookId = bookIdField.getText();
            String borrowingPeriodStr = borrowingPeriodField.getText();

            if (!bookId.isEmpty() && !borrowingPeriodStr.isEmpty()) {
                try {
                    int borrowingPeriod = Integer.parseInt(borrowingPeriodStr);


                    library.borrowBook(bookId, borrowingPeriod, 14);
                    JOptionPane.showMessageDialog(borrowBookFrame, "Book borrowed successfully!");
                    borrowBookFrame.dispose();
                }

                catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(borrowBookFrame, "Please enter a valid borrowing period.");
                }

                catch (Exception exc) {
                    JOptionPane.showMessageDialog(borrowBookFrame, "You can't borrow this book as it exceeded the borrowing period.");
                }
            }

            else {
                JOptionPane.showMessageDialog(borrowBookFrame, "Please enter book ID and borrowing period.");
            }
        });

        inputPanel.add(new JLabel("Book ID to Borrow:"));
        inputPanel.add(bookIdField);
        inputPanel.add(new JLabel("<html>Borrowing Period (in days):</html>"));
        inputPanel.add(borrowingPeriodField);
        inputPanel.add(new JPanel());
        inputPanel.add(borrowButton);

        borrowBookFrame.add(inputPanel, BorderLayout.CENTER);

        borrowBookFrame.setSize(300, 150);
        borrowBookFrame.setLocationRelativeTo(mainFrame);
        borrowBookFrame.setVisible(true);
    }

    private void openReturnBookWindow(ActionEvent e) {
        JFrame returnBookFrame = new JFrame("Return Book");
        returnBookFrame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField bookIdField = new JTextField();
        JButton returnButton = new JButton("Return");
        returnButton.addActionListener(actionEvent -> {
            String bookId = bookIdField.getText();

            if (!bookId.isEmpty()) {

                try {
                    library.returnBook(bookId);
                    JOptionPane.showMessageDialog(returnBookFrame, "Book returned successfully!");
                    returnBookFrame.dispose();
                }

                catch (Exception exc) {
                    JOptionPane.showMessageDialog(returnBookFrame, "Book is not currently borrowed.");
                }
            }

            else {
                JOptionPane.showMessageDialog(returnBookFrame, "Please enter book ID to return.");
            }
        });

        inputPanel.add(new JLabel("Book ID to Return:"));
        inputPanel.add(bookIdField);
        inputPanel.add(new JPanel());
        inputPanel.add(returnButton);

        returnBookFrame.add(inputPanel, BorderLayout.CENTER);

        returnBookFrame.setSize(300, 120);
        returnBookFrame.setLocationRelativeTo(mainFrame);
        returnBookFrame.setVisible(true);
    }

    private void openViewStatusWindow(ActionEvent e) {
        JFrame viewStatusFrame = new JFrame("View Library Status");
        viewStatusFrame.setLayout(new BorderLayout());

        CustomTextArea statusArea = new CustomTextArea();

        JScrollPane scrollPane = new JScrollPane(statusArea);

        PrintStream printStream = new PrintStream(new CustomOutputStream(statusArea));
        System.setOut(printStream);
        System.setErr(printStream);

        library.viewStatus(statusArea);

        viewStatusFrame.add(scrollPane, BorderLayout.CENTER);

        viewStatusFrame.setSize(400, 300);
        viewStatusFrame.setLocationRelativeTo(mainFrame);
        viewStatusFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SystemGUI::new);
    }

    public static class CustomTextArea extends JTextArea {
        private static final long serialVersionUID = 1L;
        private static final Color BACKGROUND_COLOR = new Color(240, 240, 240);
        private static final Color BORDER_COLOR = Color.BLACK;
        private StringBuilder buffer = new StringBuilder();

        public CustomTextArea() {
            setEditable(false);
            setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
            setLineWrap(true);
            setWrapStyleWord(true);
            setOpaque(true);
            setBackground(BACKGROUND_COLOR);
            setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        }

        @Override
        public synchronized void append(String str) {
            buffer.append(str);
            super.setText(buffer.toString());
            super.setCaretPosition(buffer.length());
        }
    }

    private static class CustomOutputStream extends OutputStream {
        private CustomTextArea textArea;

        public CustomOutputStream(CustomTextArea textArea) {
            this.textArea = textArea;
        }

        @Override
        public void write(int b) {
            textArea.append(String.valueOf((char) b));
        }
    }
}