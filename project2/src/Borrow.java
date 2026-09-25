public class Borrow {

    private int bookId;
    private int studentId;

    public Borrow(int bookId, int studentId) {
        this.bookId = bookId;
        this.studentId = studentId;
    }

    public int getBookId() {
        return bookId;
    }

    public int getStudentId() {
        return studentId;
    }
}