import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;
import java.sql.ResultSet;

public class LibraryManagement {
    static Scanner sc= new Scanner(System.in);
    public static void addBook(){
        try{
            Connection con=DBConnection.getConnection();

            System.out.println("Enter book title:");
            String title=sc.nextLine();

            System.out.println("Enter Author:");
            String author=sc.nextLine();

            System.out.println("Enter Category:");
            String category=sc.nextLine();

            System.out.println("Enter Quantity:");
            int quantity = sc.nextInt();
            sc.nextLine();

            String sql="INSERT INTO book"+"(title,author,category,quantity)"+ "values(?,?,?,?)";
            PreparedStatement ps= con.prepareStatement(sql);

            ps.setString(1, title);
            ps.setString(2, author);
            ps.setString(3, category);
            ps.setInt(4, quantity);

            int rows= ps.executeUpdate();

            if(rows>0){
                System.out.println("Book Added Successfully!");

            }
            con.close();


        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void viewBooks() {

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM book";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            System.out.println("\n----- BOOK DETAILS -----");

            while (rs.next()) {

                System.out.println(
                    "Book ID: " + rs.getInt("book_id")
                    + " | Title: " + rs.getString("title")
                    + " | Author: " + rs.getString("author")
                    + " | Category: " + rs.getString("category")
                    + " | Quantity: " + rs.getInt("quantity")
                );
            }

            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
    public static void searchBook() {

        try {

            Connection con = DBConnection.getConnection();

            System.out.println("Enter Book Title to Search:");
            String title = sc.nextLine();

            String sql = "SELECT * FROM book WHERE title = ?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, title);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                System.out.println("\n----- BOOK FOUND -----");

                System.out.println("Book ID: " + rs.getInt("book_id"));
                System.out.println("Title: " + rs.getString("title"));
                System.out.println("Author: " + rs.getString("author"));
                System.out.println("Category: " + rs.getString("category"));
                System.out.println("Quantity: " + rs.getInt("quantity"));

            } else {

                System.out.println("Book Not Found!");
            }

            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public static void registerStudent() {

        try {

            Connection con = DBConnection.getConnection();

            System.out.println("Enter Student Name:");
            String name = sc.nextLine();

            System.out.println("Enter Register Number:");
            String regnum = sc.nextLine();

            System.out.println("Enter Department:");
            String department = sc.nextLine();

            String sql = "INSERT INTO student "
                    + "(name, regnum, department) "
                    + "VALUES (?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, regnum);
            ps.setString(3, department);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Student Registered Successfully!");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void borrowBook() {

        try {

            Connection con = DBConnection.getConnection();

            System.out.println("Enter Book ID:");
            int bookId = sc.nextInt();

            System.out.println("Enter Student ID:");
            int studentId = sc.nextInt();
            sc.nextLine();

            // Check book quantity
            String checkBook = "SELECT quantity FROM book WHERE book_id = ?";

            PreparedStatement bookPs = con.prepareStatement(checkBook);
            bookPs.setInt(1, bookId);

            ResultSet bookRs = bookPs.executeQuery();

            if (!bookRs.next()) {
                System.out.println("Book Not Found!");
                con.close();
                return;
            }

            int quantity = bookRs.getInt("quantity");

            if (quantity <= 0) {
                System.out.println("Book is Not Available!");
                con.close();
                return;
            }

            // Check student
            String checkStudent = "SELECT * FROM student WHERE student_id = ?";

            PreparedStatement studentPs = con.prepareStatement(checkStudent);
            studentPs.setInt(1, studentId);

            ResultSet studentRs = studentPs.executeQuery();

            if (!studentRs.next()) {
                System.out.println("Student Not Found!");
                con.close();
                return;
            }

            // Insert borrow record
            String sql = "INSERT INTO borrow "
                    + "(book_id, student_id, borrow_date, status) "
                    + "VALUES (?, ?, CURDATE(), ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, bookId);
            ps.setInt(2, studentId);
            ps.setString(3, "Borrowed");

            int rows = ps.executeUpdate();

            if (rows > 0) {

                // Decrease book quantity
                String updateBook =
                        "UPDATE book SET quantity = quantity - 1 WHERE book_id = ?";

                PreparedStatement updatePs = con.prepareStatement(updateBook);

                updatePs.setInt(1, bookId);

                updatePs.executeUpdate();

                System.out.println("Book Borrowed Successfully!");
            }

            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
    public static void returnBook() {

        try {

            Connection con = DBConnection.getConnection();

            System.out.println("Enter Borrow ID:");
            int borrowId = sc.nextInt();
            sc.nextLine();

            // Check whether the borrow record exists
            String checkSql = "SELECT book_id, status FROM borrow WHERE borrow_id = ?";

            PreparedStatement checkPs = con.prepareStatement(checkSql);
            checkPs.setInt(1, borrowId);

            ResultSet rs = checkPs.executeQuery();

            if (!rs.next()) {

                System.out.println("Borrow Record Not Found!");
                con.close();
                return;
            }

            int bookId = rs.getInt("book_id");
            String status = rs.getString("status");

            if (status.equals("Returned")) {

                System.out.println("Book Already Returned!");
                con.close();
                return;
            }

            // Update borrow record
            String updateBorrow =
                    "UPDATE borrow SET return_date = CURDATE(), status = 'Returned' "
                    + "WHERE borrow_id = ?";

            PreparedStatement borrowPs = con.prepareStatement(updateBorrow);

            borrowPs.setInt(1, borrowId);

            borrowPs.executeUpdate();

            // Increase book quantity
            String updateBook =
                    "UPDATE book SET quantity = quantity + 1 WHERE book_id = ?";

            PreparedStatement bookPs = con.prepareStatement(updateBook);

            bookPs.setInt(1, bookId);

            bookPs.executeUpdate();

            System.out.println("Book Returned Successfully!");

            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
    public static void viewBorrowedBooks() {

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT b.borrow_id, bk.title, s.name, "
                    + "b.borrow_date, b.return_date, b.status "
                    + "FROM borrow b "
                    + "JOIN book bk ON b.book_id = bk.book_id "
                    + "JOIN student s ON b.student_id = s.student_id";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            System.out.println("\n----- BORROWED BOOKS -----");

            while (rs.next()) {

                System.out.println("Borrow ID: " + rs.getInt("borrow_id"));
                System.out.println("Book: " + rs.getString("title"));
                System.out.println("Student: " + rs.getString("name"));
                System.out.println("Borrow Date: " + rs.getDate("borrow_date"));
                System.out.println("Return Date: " + rs.getDate("return_date"));
                System.out.println("Status: " + rs.getString("status"));
                System.out.println("--------------------------");
            }

            con.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
    public static void main(String[] args) {

    while (true) {

        System.out.println("\n========== LIBRARY MANAGEMENT SYSTEM ==========");
        System.out.println("1. Add Book");
        System.out.println("2. View Books");
        System.out.println("3. Search Book");
        System.out.println("4. Register Student");
        System.out.println("5. Borrow Book");
        System.out.println("6. Return Book");
        System.out.println("7. View Borrowed Books");
        System.out.println("8. Exit");
        System.out.println("==============================================");

        System.out.print("Enter your choice: ");

        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {

            case 1:
                addBook();
                break;

            case 2:
                viewBooks();
                break;

            case 3:
                searchBook();
                break;

            case 4:
                registerStudent();
                break;

            case 5:
                borrowBook();
                break;

            case 6:
                returnBook();
                break;

            case 7:
                viewBorrowedBooks();
                break;

            case 8:
                System.out.println("Thank you for using Library Management System!");
                System.exit(0);

            default:
                System.out.println("Invalid Choice! Please try again.");
        }
    }
}
}
