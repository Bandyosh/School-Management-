import java.sql.*;
import java.util.*;

public class Database {

    public static void connect(){

        try {

            String url = "jdbc:mysql://localhost:3306/demo";
            connection = DriverManager.getConnection(url, "newuser1", "han7dle6");
            myStmt = connection.createStatement();

        } catch (Exception e){
            console.error("Cannot Connect To Database\nProgram Shutting Down");
            console.end();
        }
    }

    public static void deleteStudent(String password){

        try {

            if (password.toUpperCase(Locale.ROOT).equals("ROOT")) {
                int id = zForm.Int(ANSI.orange(), "What is the Student ID of whom you are deleting?");
                String confirmation = zForm.String(ANSI.RED(), "You will delete " + getStudentName(id) + " permanently if you choose to continue (Press Y to Continue)");

                if(confirmation.toUpperCase(Locale.ROOT).equals("Y")){

                 String string = "DELETE FROM students WHERE id=" + id + " LIMIT 1";
                 myStmt.execute(string);
                 console.color(ANSI.GREEN(), "Student with ID: " + id + " has been deleted.");
                 zForm.Restart();

                } else{

                 console.error("Deletion Canceled");
                 zForm.Restart();

                }
            } else {
                console.error("Incorrect Password Provided");
                zForm.Restart();
            }
        } catch(Exception e){
            console.error("Student ID is not registered");
            zForm.Restart();
        }

    }

    public static void registerStudent(int id, String firstName, String lastName, String email){
        try {

            String string = "INSERT INTO `students` (`id`,`last_name`,`first_name`,`email`) VALUES (" + id + ", '" + lastName + "','" + firstName + "','"+ email+"')";

            myStmt.execute(string);

            console.color(ANSI.GREEN(), "Student: " + lastName + ", " + firstName + " with ID: " + id + " has been added into the School Database");

            zForm.Restart();

            ResultSet check = myStmt.executeQuery("SELECT email, COUNT(*) c FROM students GROUP BY email HAVING c > 1");

            if(check.next()) {
                myStmt.execute("DELETE c1 FROM students c1\n" +
                "INNER JOIN students c2 \n" +
                "WHERE\n" +
                "c1.id > c2.id AND \n" +
                "c1.email = c2.email;");
                System.out.println("Duplicate Emails Found: Deleting Student with newer ID");
                zForm.Restart();
            }

        } catch (Exception e){
            console.error("ID Already Exists: Duplicate Student?\n Could not add Student");
            zForm.Restart();
        }
    }

    public static void fetchStudent(int id){

        try{
           String string = "SELECT * FROM students WHERE id = " + id;
            ResultSet response = myStmt.executeQuery(string);
            response.next();
            console.color(ANSI.GREEN(), "Last Name: " + response.getString("last_name"));
            console.color(ANSI.GREEN(), "First Name: " + response.getString("first_name"));
            console.color(ANSI.GREEN(), "Email: " + response.getString("email"));
            console.color(ANSI.GREEN(), "ID: " + response.getString("id"));
            zForm.Restart();
        } catch(Exception e){

            console.error("Student ID not found");
            zForm.Restart();
        }

    }
    public static String getStudentName(int id){
        String name = "";

        try{

            String string = "SELECT * FROM students WHERE id = " + id;
            ResultSet response = myStmt.executeQuery(string);
            response.next();
            name = response.getString("last_name") + ", " + response.getString("first_name");

        } catch(Exception e){

            console.error("Student ID not found");
            zForm.Restart();
        }
        return name;
    }

    public static void fetchAll(String password){

        try {
            if (password.toUpperCase(Locale.ROOT).equals("ROOT")) {

                ResultSet response = myStmt.executeQuery("SELECT * from students");
                while(response.next()){
                    console.color(ANSI.GREEN(),"ID: " + response.getString("id") + ", Last Name: "+ response.getString("last_name") + ", First Name: " + response.getString("first_name") + ", Email: " + response.getString("email"));
                }
                zForm.Restart();

            } else{

                console.error("Incorrect Password");
                zForm.Restart();

            }
        } catch(Exception e){

           console.error("Unexpected Error\nProgram Shutting Down.");
        }
    }


   static Connection connection;
   static Statement myStmt ;
}
