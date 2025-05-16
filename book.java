import java.util.Random;
import java.sql.Date;

class Book {
    private static final Random random = new Random();
    private final String id;
    private final String name;
    private final String category;
    private boolean borrowed;
    private int borrowingPeriod;
    private Date borrowingDate;

    public Book(String name, String category) {
        this.id = String.format("%04d", random.nextInt(10000));
        this.name = name;
        this.category = category;
        this.borrowed = false;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public void setBorrowed(boolean borrowed) {
        this.borrowed = borrowed;
    }

    public int getBorrowingPeriod() {
        return borrowingPeriod;
    }

    public void setBorrowingPeriod(int borrowingPeriod) {
        this.borrowingPeriod = borrowingPeriod;
    }

    public Date getBorrowingDate() {
        return borrowingDate;
    }

    public void setBorrowingDate(Date borrowingDate) {
        this.borrowingDate = borrowingDate;
    }
}