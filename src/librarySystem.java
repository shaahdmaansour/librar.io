import java.util.*;

public class LibrarySystem {
    private static SystemGUI.CustomTextArea statusArea;

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Welcome to our Library System");

            System.out.print("Enter the capacity of the library: ");
            int capacity = scanner.nextInt();
            Library library = new Library(capacity);
            final int MAX_BORROWING_PERIOD = 14;

            while (true) {
                System.out.println("\nSelect one of the following options:");
                System.out.println("1. Add a book");
                System.out.println("2. Remove a book");
                System.out.println("3. Borrow a book");
                System.out.println("4. Return a book");
                System.out.println("5. View library status");
                System.out.println("6. Exit");

                System.out.print("Your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1: {
                        System.out.println("\nAdding a Book");
                        System.out.print("Enter the book name: ");
                        String name = scanner.nextLine();
                        System.out.println("Enter the book category:");
                        System.out.println("1. Biology");
                        System.out.println("2. Maths");
                        System.out.println("3. History");
                        System.out.println("4. Chemistry");
                        System.out.println("5. Politics");
                        System.out.print("Your choice: ");
                        int categoryChoice = scanner.nextInt();
                        scanner.nextLine();
                        String category;
                        switch (categoryChoice) {
                            case 1:
                                category = "Biology";
                                break;
                            case 2:
                                category = "Maths";
                                break;
                            case 3:
                                category = "History";
                                break;
                            case 4:
                                category = "Chemistry";
                                break;
                            case 5:
                                category = "Politics";
                                break;
                            default:
                                System.out.println("Invalid category choice, book not added.");
                                continue;
                        }
                        String bookId = library.addBook(name, category);
                        if (bookId != null) {
                            System.out.println("Book added successfully!");
                            System.out.println("Book ID: " + bookId);
                        }
                        break;
                    }
                    case 2: {
                        System.out.println("\nRemoving a Book");
                        System.out.print("Enter the ID of the book to remove: ");
                        String removeId = scanner.nextLine();
                        library.removeBook(removeId);
                        break;
                    }
                    case 3: {
                        System.out.println("\nBorrowing a Book");
                        System.out.print("Enter the ID of the book to borrow: ");
                        String borrowId = scanner.nextLine();
                        System.out.print("Enter the borrowing period (in days): ");
                        int borrowingPeriod = scanner.nextInt();
                        library.borrowBook(borrowId, borrowingPeriod, MAX_BORROWING_PERIOD);
                        break;
                    }
                    case 4: {
                        System.out.println("\nReturning a Book");
                        System.out.print("Enter the ID of the book to return: ");
                        String returnId = scanner.nextLine();
                        library.returnBook(returnId);
                        break;
                    }
                    case 5:
                        System.out.println("\nLibrary Status");
                        library.viewStatus(statusArea);
                        break;
                    case 6: {
                        System.out.println("\nExiting the system. Thank you for using our Library System!");
                        System.exit(0);
                        break;
                    }
                    default:
                        System.out.println("\nInvalid choice!");
                }
            }
        }
    }
}