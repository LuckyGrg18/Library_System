import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Library {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        // create some books and users
        Book book1 = new Book(12, "Atomic Habits",10, "James Clear");
        try {
            Connection conn = DatabaseConnection.connect();
            String query = "INSERT INTO book" +
                    "(bookNumber, bookName, authorName, bookquantity) " +
                    "VALUES (?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1,book1.getBookNumber());
            ps.setString(2, book1.getBookName());
            ps.setString(3, book1.getBookAuthor());
            ps.setInt(4,book1.getBookQuantity());
            if(ps.executeUpdate() > 0){
                System.out.println("book added to database");
            }else{
                System.out.println("Failed to add");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        User user1 = new User("Sunil", "sunil-57", 5566);




        //show available options: available books, borrow book, return book, exit
        System.out.println("Welcome to the Library");
        System.out.println("Enter 1: Show available books ");
        System.out.println("Enter 2: Borrow Book");
        System.out.println("Enter 3: Return Book");
        System.out.println("Enter 4: Update Book");
        System.out.println("Enter 5: Delete Book");
        System.out.println("Enter 6: Exit");
        System.out.println("Choose an option: ");
        int option = input.nextInt();
        if(option == 1){
            System.out.println("Available books");
            try {
                Connection conn = DatabaseConnection.connect();
                ArrayList<Book> bookList = new ArrayList<>();
                String query = "SELECT booknumber,bookName, bookquantity,authorName FROM book";
                PreparedStatement ps = conn.prepareStatement(query);
                ResultSet bookSet = ps.executeQuery();
                while(bookSet.next()){
//                    int bookNumber = bookSet.getInt("booknumber");
//                    String bookName = bookSet.getString("bookname");
//                    int bookQuantity = bookSet.getInt("bookquantity");
//                    String authorName = bookSet.getString("authorName");
                    Book book = new Book(
                            bookSet.getInt("booknumber"),
                            bookSet.getString("bookname"),
                            bookSet.getInt("bookquantity"),
                            bookSet.getString("authorName"));
                    bookList.add(book);
                }
                for(Book book: bookList){
                    System.out.println("Book Number: "+book.getBookNumber());
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }else if(option == 2)
        {
            System.out.println("working on borrowing books");
            //TODO kun book borrow garne ho tyo chahi dinu parxha
            //TODO borrow garera sakesi message dekhauna parxa
            System.out.println("Before: "+ book1.getBookQuantity());
            user1.borrowBook(book1, user1);
            System.out.println("After: " + book1.getBookQuantity());
        }

        else if(option == 3)
        {
            System.out.println("working on returning books");
        }
        else if(option == 4)
        {
            System.out.println("Enter Book id: ");
            int bookid = input.nextInt();
            System.out.println("Enter book Number");
            int bookNumber = input.nextInt();
            try {
                Connection conn = DatabaseConnection.connect();
                String query = "UPDATE `book` SET `bookNumber`= ? WHERE `bookid`= ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setInt(1,bookNumber);
                ps.setInt(2,bookid);
                if(ps.executeUpdate() > 0){
                    System.out.println("book updated");
                }else{
                    System.out.println("Failed to update");
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        else if(option == 5)
        {
            System.out.println("Enter Book id: ");
            int bookid = input.nextInt();
            try {
                Connection conn = DatabaseConnection.connect();
                String query = "DELETE from book WHERE bookID = ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setInt(1,bookid);
                if(ps.executeUpdate() > 0){
                    System.out.println("book deleted");
                }else{
                    System.out.println("Failed to delete");
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        else if(option == 6)
        {

        }
        else
        {
            System.out.println("invalid option");
        }
        //TODO need to figure out where to keep the books and users?
    }
}