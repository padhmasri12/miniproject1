public class Book {

    private int bookId;
    private String title;
    private String author;
    private String category;
    private int quantity;

    public Book(String title, String author, String category, int quantity) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public int getQuantity() {
        return quantity;
    }
}