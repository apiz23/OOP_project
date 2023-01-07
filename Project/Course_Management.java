package Project;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import javax.swing.JOptionPane;

public class Course_Management {
    public static void main(String[] args) throws IOException {
        do {
            LoginMenu login = new LoginMenu();
            login.userType(login.Login_menu());
        }while(JOptionPane.showConfirmDialog(null, "Do you want to end this system?", "Confirm", JOptionPane.YES_NO_OPTION) == 1);
    }
}

class LoginMenu {
    String enteredUsername,enteredPassword;
    int Login_menu() throws NumberFormatException {

        int loginInput;

        do {
            loginInput = Integer.parseInt(JOptionPane.showInputDialog("\n\n--------Course Management System--------\n\n1) Student Login\n2) Lecturer Login\n3) Admin Login\n0) Exit\n\nEnter Selection: \n\n"));
        } while(!(loginInput >= 0 && loginInput < 4) );

        if(loginInput == 0) System.exit(0);
        do{}while (!validateLogin(loginInput));
        return loginInput;
    }

    boolean validateLogin(int loginUser){
        // Prompt the user for a username and password
        enteredUsername = JOptionPane.showInputDialog("Enter your username:");
        JPasswordField pf = new JPasswordField();
        int okCxl = JOptionPane.showConfirmDialog(null, pf, "Enter Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        // Read the username and password pairs from the file
        if(okCxl == JOptionPane.OK_OPTION) {
            String pss = new String(pf.getPassword());
            try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\SCSM11\\Desktop\\Coding\\Java_OOP\\src\\Project\\Acc\\" + loginUser + ".txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    // Split the line into a username and password using the delimiter ','
                    String[] parts = line.split(",");

                    String username = parts[0];
                    String password = parts[1];

                    // Check if the entered username and password match this pair
                    if (enteredUsername.equals(username) && pss.equals(password)) {
                        JOptionPane.showMessageDialog(null, "Login successful!");
                        return true;
                    }
                }
                // If we reach this point, the entered username and password were invalid
                JOptionPane.showMessageDialog(null, "Invalid username or password.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }return false;
    }

    void userType(int loginInput) throws IOException {
        switch (loginInput) {
            case 1 -> {
                Student student = new Student(enteredUsername, enteredPassword);
                CourseRegistration course = new CourseRegistration();
                course.choosingSubj(student.getUsername());
            }
            case 2 -> {
                Lecturer lect = new Lecturer(enteredUsername, enteredPassword);
                lect.printCourse(enteredUsername);
            }
            case 3 -> {
                new Administrator(enteredUsername, enteredPassword);
                Course_administrator admin = new Course_administrator();
                String[] options = {"Add", "Delete"};
                int optionSelected = JOptionPane.showOptionDialog(null, "Choosing between add or delete course:", "Course Management", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

                if (optionSelected == 0) {admin.adding_course();}
                else if (optionSelected == 1) {admin.deleting_course();}
            }
        }
    }
}

class User {
    protected String username,password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }
}

class Student extends User{

    public Student(String username, String password) {super(username, password);}

}

class Lecturer extends User{
    public Lecturer(String username, String password) {
        super(username, password);
    }

    void printCourse(String name) throws IOException {

        String[] lectureName = {"Cik Rosni binti Ramle","En Abdul Halim bin Omar","En Fawwaz bin Mohd Nasir"};

        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\SCSM11\\Desktop\\Coding\\Java_OOP\\src\\Project\\Lecture\\" + name + ".txt"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }br.close();
        JOptionPane.showMessageDialog(null,((name.equals("LA01"))? lectureName[0] : (name.equals("LA02"))? lectureName[1] : lectureName[2]) + ":\n\n" + sb, "Subject Hold", JOptionPane.INFORMATION_MESSAGE);
    }
}

class Administrator extends User{
    public Administrator(String username, String password) {
        super(username, password);
    }
}

class Course{
    ArrayList<String> subjectChosen = new ArrayList<>();
    ArrayList<String> subjectName = new ArrayList<>();
    ArrayList<String> subjectNameTemp = new ArrayList<>();
    ArrayList<Integer> subjectSize = new ArrayList<>();
    ArrayList<Integer> subjectSizeTemp = new ArrayList<>();
    final String courseName = "Information Technology";

}

class CourseRegistration extends Course{

    void choosingSubj(String name) throws IOException {

        String line;
        int subjectInp;

        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\SCSM11\\Desktop\\Coding\\Java_OOP\\src\\Project\\Course\\SubjectName.txt"));
        while((line = br.readLine()) != null){
            String[] seperate = line.split(",");
            subjectName.add(seperate[0]);
            subjectNameTemp.add(seperate[0]);
            subjectSize.add(Integer.valueOf(seperate[1]));
            subjectSizeTemp.add(Integer.valueOf(seperate[1]));
        }

        do {
            System.out.println("Code  Subject Name \t\t\t\t    Size \t Credit Hour");
            for (int i = 0; i < subjectName.size(); i++) {
                System.out.println((i + 1) + ".    " + subjectName.get(i) + "   " + subjectSize.get(i) + "\t\t\t 3");
            }
            subjectInp = Integer.parseInt(JOptionPane.showInputDialog(null, "Please choose the subject\n"));
            if (JOptionPane.showConfirmDialog(null, "Do you confirm?", "Confirm", JOptionPane.YES_NO_OPTION) == 0) {

                subjectChosen.add(subjectName.get(subjectInp - 1));
                if(Objects.equals(subjectSize.get(subjectInp - 1), subjectSizeTemp.get(subjectInp - 1))) {
                    Integer temp = subjectSizeTemp.get(subjectInp - 1);
                    temp--;
                    subjectSizeTemp.set(subjectInp - 1, temp);
                }else{
                    Integer temp = subjectSizeTemp.get(subjectInp);
                    temp--;
                    subjectSizeTemp.set(subjectInp, temp);
                }
                BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\SCSM11\\Desktop\\Coding\\Java_OOP\\src\\Project\\Course\\SubjectName.txt"));
                for (int i = 0; i < subjectSizeTemp.size(); i++) {writer.write(subjectNameTemp.get(i) + "," + subjectSizeTemp.get(i) + "\n");}
                writer.close();
                subjectName.remove(subjectInp - 1);
                subjectSize.remove(subjectInp - 1);
            }

            System.out.print("\033[H\033[2J");
            System.out.flush();

            BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\SCSM11\\Desktop\\Coding\\Java_OOP\\src\\Project\\Course\\SubjectNameTemp.txt"));
            for(int i = 0; i < subjectNameTemp.size(); i++) {
                writer.write(subjectNameTemp.get(i) + "," + subjectSizeTemp.get(i) + "\n");
            }writer.close();

        } while (JOptionPane.showConfirmDialog(null, "Do you want to add more subject?", "Confirm", JOptionPane.YES_NO_OPTION) == 0);
        printSlip(name);
    }

    void printSlip(String name) {

        int count = 0;
        File file = new File("C:\\Users\\SCSM11\\Desktop\\Coding\\Java_OOP\\src\\Project\\Course\\Subject_Slip.txt");

        PrintWriter out;
        try {
            out = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        out.println("Detail information:\n\nUsername : " + name + "\nCourse Name : " + courseName + "\nSection : 3\n-------------------------------------------\n");

        out.println("No\tCourse\t\t \t\t\t\tCredit Hour");
        for (String s : subjectChosen) {
            out.println((count+1) + "\t" + s + "\t\t3");
            count++;
        }out.println("\n-------------------------------------------\nTotal Credit Hour: " + (count)*3);
        out.close();
    }
}

class Course_administrator extends Course{

    void adding_course() throws IOException {
        String courseAdd = JOptionPane.showInputDialog(null, "Enter the new name course: ");
        FileWriter fileWriter = new FileWriter("C:\\Users\\SCSM11\\Desktop\\Coding\\Java_OOP\\src\\Project\\Course\\SubjectName.txt", true);

        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        bufferedWriter.write(courseAdd.toUpperCase() + " ".repeat(Math.max(0, (27 - courseAdd.length()))) + ",30");

        bufferedWriter.close();
        fileWriter.close();
    }

    void deleting_course() throws IOException {
        String courseDel = JOptionPane.showInputDialog(null, "Enter name of the course: ");

        File file = new File("C:\\Users\\SCSM11\\Desktop\\Coding\\Java_OOP\\src\\Project\\Course\\SubjectName.txt");

        File tempFile = File.createTempFile("temp", ".txt");

        BufferedReader reader = new BufferedReader(new FileReader(file));

        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String line;
        while ((line = reader.readLine()) != null) if (!line.contains(courseDel.toUpperCase())) {
            writer.write(line);
            writer.newLine();
        }

        reader.close();
        writer.close();
        file.delete();
        tempFile.renameTo(file);
    }
}