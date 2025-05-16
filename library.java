import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.sql.Date;
import java.util.Map;

class Library {
    private final int capacity;
    private final List<Book> books;
    public Object removeBook;

    public Library(int capacity) {
        this.capacity = capacity;
        this.books = new ArrayList<>();
    }

    public String addBook(String name, String category) {

        if (name.isEmpty()) {
            System.out.println("Book name can not be empty.");
            return null;
        }

        if (books.size() < capacity) {
            Book newBook = new Book(name, category);
            books.add(newBook);
            return newBook.getId();
        }

        else {
            System.out.println("Library is full.");
            return null;
        }
    }

    public boolean containsBook(String bookId) {

        for (Book book : books) {

            if (book.getId().equals(bookId)) {
                return true;
            }
        }
        return false;
    }

    public void removeBook(String id) {
        Book bookToRemove = findBookById(id);

        if (bookToRemove != null) {

            if (!bookToRemove.isBorrowed()) {
                books.remove(bookToRemove);
                System.out.println("Book removed successfully!");
            }

            else {
                System.out.println("Book is currently borrowed.");
            }
        }

        else {
            System.out.println("Book not found!");
        }
    }


    public void borrowBook(String id, int borrowingPeriod, int maxBorrowingPeriod) {
        Book bookToBorrow = findBookById(id);

        if (bookToBorrow != null) {

            if (!bookToBorrow.isBorrowed()) {

                if (borrowingPeriod <= maxBorrowingPeriod) {
                    bookToBorrow.setBorrowed(true);
                    bookToBorrow.setBorrowingPeriod(borrowingPeriod);
                    bookToBorrow.setBorrowingDate(new Date(maxBorrowingPeriod));
                    System.out.println("Book borrowed successfully!");
                }

                else {

                    try {
                        throw new Exception("Borrowing period exceeds the maximum limit.");
                    }

                    catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            else {
                System.out.println("Book is currently borrowed.");
            }
        }

        else {
            System.out.println("Book not found!");
        }
    }


    public void returnBook(String id) {
        Book bookToReturn = findBookById(id);

        if (bookToReturn != null) {

            if (bookToReturn.isBorrowed()) {
                bookToReturn.setBorrowed(false);
                System.out.println("Book returned successfully!");
            }

            else {

                try {
                    throw new Exception("Book is not currently borrowed.");
                }

                catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        else {
            System.out.println("Book not found!");
        }
    }

    public void viewStatus(SystemGUI.CustomTextArea statusArea) {
        int totalBorrowedBooks = 0;
        Map<String, Integer> categoryCount = new HashMap<>();
        System.out.println("Library Status:");

        for (Book book : books) {
            System.out.println("Book ID: " + book.getId());
            System.out.println("Name: " + book.getName());
            System.out.println("Category: " + book.getCategory());

            if (book.isBorrowed()) {
                System.out.println("Status: Borrowed");
                System.out.println("Borrowing Period: " + book.getBorrowingPeriod() + " days");
                System.out.println("Borrowing Date: " + book.getBorrowingDate());
                totalBorrowedBooks++;
            }

            else {
                System.out.println("Status: Not Borrowed");
            }
            System.out.println("--------------------");
            categoryCount.put(book.getCategory(), categoryCount.getOrDefault(book.getCategory(), 0) + 1);
        }
        System.out.println("Total number of books in each category:");

        for (Map.Entry<String, Integer> entry : categoryCount.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        System.out.println("--------------------");
        System.out.println("Total number of borrowed books: " + totalBorrowedBooks);
    }

    private Book findBookById(String id) {

        for (Book book : books) {

            if (book.getId().equals(id)) {
                return book;
            }
        }
        return null;
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }
}