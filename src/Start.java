import java.util.Locale;

public class Start {
    public Start(){
        console.clear();
        Database.connect();
        String response = zForm.String(ANSI.CYAN(), "Do you want to register a new student (r), find a student (f), delete a student (d), or see all students (s)? ").toUpperCase(Locale.ROOT);
        if(response.equals("S")){
            String password = zForm.String(ANSI.orange(), "Enter Administration Password: ");
            Database.fetchAll(password);
        } else{
            if(response.equals("F")){
                int id = zForm.Int(ANSI.orange(), "Enter Student ID");
                Database.fetchStudent(id);
            } else{
                if(response.equals("R")){
                    int id = zForm.Int(ANSI.orange(), "New Student ID? ");
                    String lastName = zForm.String(ANSI.orange(), "New Student Last Name? ");
                    String firstName = zForm.String(ANSI.orange(), "New Student First Name? ");
                    String email = zForm.String(ANSI.orange(), "New Student Email?");
                    Database.registerStudent(id, firstName, lastName, email);
                } else{
                    if(response.equals("D")){
                        String password = zForm.String(ANSI.orange(), "Enter Administration Password");
                        Database.deleteStudent(password);
                    } else {
                        console.error("Invalid Input(s)");
                        zForm.Restart();
                    }
                }
            }
        }
    }
}
