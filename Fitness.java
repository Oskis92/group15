import java.io.*;
import java.sql.*;
import org.sqlite.SQLiteConfig;
import java.lang.NumberFormatException;
import java.util.*;
import java.text.SimpleDateFormat;

public class Fitness {
   public static final String DB_URL = "jdbc:sqlite:/home/oskis/Documents/TIG058/program/FitnessAB/db";
   public static final String DRIVER = "org.sqlite.JDBC";  
   public static void main(String[] args) throws IOException {
      Connection conn = null;
      try {
         Class.forName(DRIVER);
         SQLiteConfig config = new SQLiteConfig();    
         config.enforceForeignKeys(true);
         conn = DriverManager.getConnection(DB_URL,config.toProperties());  
      } catch (Exception e) {
         System.out.println( e.toString() );
         System.exit(0);
      }
      
      Scanner sc = new Scanner(System.in);
      String choose;
      mainScreen();
      choose = sc.next();
      
      while (choose != "q") {
      switch (choose) {
      case "q":
         System.out.println("You chose: Q - Quit");
         System.out.println("Thank you for testing our program! :-)");
         System.exit(0);
         
      break;
      
      case "L":
         System.out.println("You chose: L - Log in");
         System.out.println("Personal number?");
         Scanner scMemberID = new Scanner(System.in);
         String mid = scMemberID.nextLine();
         
         System.out.println("Password?");
         Scanner scPassword = new Scanner(System.in);
         String pw = scPassword.next();
         
         try {
            Statement st = conn.createStatement();
            
            //TO DO!!fetch db personalNr -> midint.Equals(PersonalNr) ->  ->fetch db Password -> pw.Equals(Password)
            if (mid != null && pw != null) {
               String validate = "Select * from Member where PersonalNr='" + mid + "' and Password='" + pw + "'";
               ResultSet rs = st.executeQuery(validate);

               if (rs.next()) {
                  //enter
                  System.out.println("Welcome " + mid + "!");
                  
               }
               else {
                  //dont enter + error message
                  System.out.println("Invalid Username or Password");
                  System.exit(0);
               }
         } 
            
         }
         catch (Exception e) {
            System.out.println( e.toString() );
         }
         finally {
            System.out.println("Login completed");
                  BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
         boolean fortsatt = true;
      
         while (fortsatt) {
      
         //System.out.println("A - New member");
         //create membership duration
    	   System.out.println("B - New Enrollment");
         System.out.println("C - Choose course to enroll");
  	      System.out.println("D - Check membership validity");
         System.out.println("E - View Rooms");
    	   System.out.println("F - View Courses");
         System.out.println("G - View Instructors");
  	      System.out.println("H - View Memberlist");
         System.out.println("I - Cancel enrolled course");
         System.out.println("J - Cancel course fully");
    	   System.out.println("Q - Quit");
         
         String val2 = input.readLine();
         char val = val2.charAt(0);   
         
         switch (val) {
            
            case 'A':
            //hidden at the moment, maybe needed for demonstration.
            System.out.println("Enter Level (Gold, Silver or Bronze)");
             String Level = input.readLine();
            System.out.println("Enter first name");
             String FirstName = input.readLine();
            System.out.println("Enter last name");
             String LastName = input.readLine();
            System.out.println("Enter personal number");
             long PersonalNr = Long.parseLong(input.readLine());
            System.out.println("Enter phone number");
             int PhoneNr = Integer.parseInt(input.readLine());
            System.out.println("Enter mail");
             String Mail = input.readLine();
            System.out.println("Enter new Password");
             String Password = input.readLine();
           
            try {
            
               String inserta = "INSERT INTO Member (Level, FirstName, LastName, PersonalNr,PhoneNr, Mail, Password) VALUES (?,?,?,?,?,?,?)";
               PreparedStatement pstmt = conn.prepareStatement(inserta);
               pstmt.setString(1, Level);
               pstmt.setString(2, FirstName);
               pstmt.setString(3, LastName);
               pstmt.setLong(4, PersonalNr);
               pstmt.setInt(5, PhoneNr);
               pstmt.setString(6, Mail);
               pstmt.setString(7, Password);
               pstmt.executeUpdate();
               pstmt.close();
            }
            
            /*System.out.println ("When do you want the membership to start? (type like yyyymmdd)");
            String StartDate = input.readLine();
            System.out.println ("When do you want the membership to end? (type like yyyymmdd)");
            String ExpirationDate = input.readLine();
            
            try {
               String insertaa = ("INSERT INTO Membership (ExpirationDate, StartDate) VALUES (?,?)");
               PreparedStatement pstmt = conn.prepareStatement(insertaa);
               pstmt.setInt(1, StartDate);
               pstmt.setString(2, ExpirationDate);
               pstmt.executeUpdate();
               pstmt.close();*/
            
            catch (java.sql.SQLException e1){
               System.out.println(e1.getMessage());
                        }
            break;

            case 'B':
            System.out.println("Enter MomentID");
             int MomentID = Integer.parseInt(input.readLine());
            System.out.println("Enter CourseID");
             int CourseID = Integer.parseInt(input.readLine());
            System.out.println("Which time will it take place?");
             int Time = Integer.parseInt(input.readLine());
            System.out.println("Enter IntructorID");
             int IntructorID = Integer.parseInt(input.readLine());
            System.out.println("How long will it last?");
             int Duration = Integer.parseInt(input.readLine());
            System.out.println("Which room will be used");
             int RoomNr = Integer.parseInt(input.readLine());
            
            try {
               String inserte = "INSERT INTO CourseMoment(MomentID, CourseID, Time, InstructorID, Duration, RoomNr) VALUES(?,?,?,?,?,?)";
               PreparedStatement pstmt = conn.prepareStatement(inserte);
               pstmt.setInt(1, MomentID);
               pstmt.setInt(2, CourseID);
               pstmt.setInt(3, Time);
               pstmt.setInt(4, IntructorID);
               pstmt.setInt(5, Duration);
               pstmt.setInt(6, RoomNr);
               pstmt.executeUpdate();
               pstmt.close();
            }
            
            catch (java.sql.SQLException e2){
               System.out.println(e2.getMessage());
            }
            break;
            
            case 'C':
            System.out.println("Enter the MomentID that you would like to attend");
             int MomID = Integer.parseInt(input.readLine());
            System.out.println("Enter your Memberid");
             int MemID = Integer.parseInt(input.readLine());
            
            try {
               String insertc = "INSERT INTO CourseEnrollment(MomentID, MemberID) VALUES(?,?)";
               PreparedStatement pstmt = conn.prepareStatement(insertc);
               pstmt.setInt(1, MomID);
               pstmt.setInt(2, MemID);
               pstmt.executeUpdate();
               pstmt.close();
            }
            
            catch (java.sql.SQLException e2){
               System.out.println(e2.getMessage());
            }
            break;
            
            case 'D':
               
               System.out.println("MemberID?");
               Scanner scMemberAccess = new Scanner(System.in);
               String access = scMemberAccess.nextLine();
               int memIDAccess = Integer.parseInt(access);
               
               

               try {
                  Statement st = conn.createStatement();
                  ResultSet rs = st.executeQuery("select * from Membership where MemberID='"+ memIDAccess + "' AND date('now') < ExpirationDate");
                  
                  if (!rs.next() ) {
                     System.out.println("Membership invalid");
                  } else {
                     System.out.println("Membership valid");
                  }     
                              
               }
               catch (java.sql.SQLException e) {
               System.out.println(e);
               }
               break;
      
            case 'E':

               try {
               String selecte = "select RoomNr,Capacity,Location from Room";        
               Statement stmt = conn.createStatement();
               ResultSet rs = stmt.executeQuery(selecte);
               while (rs.next()) {
                  System.out.println(rs.getString("RoomNr") + " " + rs.getString("Capacity") + " " + rs.getString("Location"));
               }
               stmt.close();
               rs.close();
               
            }
            catch (java.sql.SQLException e5) {
               System.out.println(e5);
            }
            break;
            case 'F':

               try {
               String selectf= "select CourseID, CourseName from Course";        
               Statement stmt = conn.createStatement();
               ResultSet rs = stmt.executeQuery(selectf);
               while (rs.next()) {
                  System.out.println(rs.getString("CourseID") + " " + rs.getString("CourseName"));
               }
               stmt.close();
               rs.close();
               
            }
            catch (java.sql.SQLException e5) {
               System.out.println(e5);
            }
            break;
            
            case 'G':

               try {
               String selectg = "select InstructorID, FirstName, Lastname from Instructor";        
               Statement stmt = conn.createStatement();
               ResultSet rs = stmt.executeQuery(selectg);
               while (rs.next()) {
                  System.out.println(rs.getString("InstructorID") + " " + rs.getString("FirstName") + " " + rs.getString("LastName"));
               }
               stmt.close();
               rs.close();
               
            }
            catch (java.sql.SQLException e5) {
               System.out.println(e5);
            }
            break;
            
            case 'H':

               try {
               String selecth = "select MemberID, PersonalNr, Level, FirstName, LastName, PhoneNr, Mail, Password from Member";        
               Statement stmt = conn.createStatement();
               ResultSet rs = stmt.executeQuery(selecth);
               while (rs.next()) {
                  System.out.println(rs.getString("MemberID") + " " + rs.getString("PersonalNr") + " " + rs.getString("FirstName") + " " + rs.getString("LastName") + " " + rs.getString("PhoneNr") + " " + rs.getString("Mail"));
               }
               stmt.close();
               rs.close();
               
            }
            catch (java.sql.SQLException e5) {
               System.out.println(e5);
            }
            break;
            
            case 'I':
            System.out.println("Enter the MomentID  you would like to cancel:");
             int IMomID = Integer.parseInt(input.readLine());
            System.out.println("Enter your Memberid:");
             int IMemID = Integer.parseInt(input.readLine());
            
               try {
                  String inserti = "DELETE FROM CourseEnrollment WHERE MomentID = ? AND MemberID = ?";
                  PreparedStatement pstmt = conn.prepareStatement(inserti);
                  pstmt.setInt(1, IMomID);
                  pstmt.setInt(2, IMemID);
                  pstmt.executeUpdate();
                  pstmt.close();
               }
            
            catch (java.sql.SQLException e2){
               System.out.println(e2.getMessage());
            }
            break;
            
            case 'J':
            System.out.println("Enter the MomentID that you would like to cancel fully");
             int JMomID = Integer.parseInt(input.readLine());
            System.out.println("Please type Cancelled to confirm");
             String Cancel = input.readLine();
             
             if (Cancel.equals("Cancelled")) {
                try {
                  String insertj = "UPDATE CourseMoment SET Status = ? WHERE MomentID = ?";
                  PreparedStatement pstmt = conn.prepareStatement(insertj);
                  pstmt.setString(1, Cancel);
                  pstmt.setInt(2, JMomID);
                  pstmt.executeUpdate();
                  pstmt.close();
                  
                  System.out.println("Course " + JMomID + " cancelled succesfully.");
                  //System.out.println("Cancellation notifications has been sent to participants");
               }
            
               catch (java.sql.SQLException e2){
                  System.out.println(e2.getMessage());
               }
            
            } else {
               System.out.println("Course not cancelled or invalid selection.");
            }
           
            break;
            
            case 'Q':
            System.out.println("Avslutar programmet");
            fortsatt = false;
            System.exit(0);
            break;
            
            default:
            System.out.println("Bokstav finns inte! \nDet måste vara en versal. Testa en ny eller samma bokstav");
            break;
         }
      }
         }
         
         break;
      
      case "U":
            
            Scanner scCreateUser = new Scanner(System.in);
            
            System.out.println("Enter Level (Gold, Silver or Bronze)");
             String Level = scCreateUser.nextLine();
            System.out.println("Enter first name");
             String FirstName = scCreateUser.nextLine();
            System.out.println("Enter last name");
             String LastName = scCreateUser.nextLine();
            System.out.println("Enter personal number");
             int PersonalNr = Integer.parseInt(scCreateUser.nextLine());
            System.out.println("Enter phone number");
             int PhoneNr = Integer.parseInt(scCreateUser.nextLine());
            System.out.println("Enter mail");
             String Mail = scCreateUser.nextLine();
            System.out.println("Enter new Password");
             String Password = scCreateUser.nextLine();
           
            try {
            
               String inserta = "INSERT INTO Member (Level, FirstName, LastName, PersonalNr,PhoneNr, Mail, Password) VALUES (?,?,?,?,?,?,?)";
               PreparedStatement pstmt = conn.prepareStatement(inserta);
               pstmt.setString(1, Level);
               pstmt.setString(2, FirstName);
               pstmt.setString(3, LastName);
               pstmt.setLong(4, PersonalNr);
               pstmt.setInt(5, PhoneNr);
               pstmt.setString(6, Mail);
               pstmt.setString(7, Password);
               pstmt.executeUpdate();
               pstmt.close();
            }
            
            /*System.out.println ("When do you want the membership to start? (type like yyyymmdd)");
            String StartDate = input.readLine();
            System.out.println ("When do you want the membership to end? (type like yyyymmdd)");
            String ExpirationDate = input.readLine();
            
            try {
               String insertaa = ("INSERT INTO Membership (ExpirationDate, StartDate) VALUES (?,?)");
               PreparedStatement pstmt = conn.prepareStatement(insertaa);
               pstmt.setInt(1, StartDate);
               pstmt.setString(2, ExpirationDate);
               pstmt.executeUpdate();
               pstmt.close();*/
            
            catch (java.sql.SQLException e1){
               System.out.println(e1.getMessage());
                        }
            finally {
               choose = "L";
            }
            break;
      
      }
      }         

   }
public static void mainScreen() {
      System.out.println("Welcome to FitnessAB! \n"
                         + "Please sign in or create a user: \n"
                         + "L - Log in \n"
                         + "U - Create a new user \n"
                         + "q - Quit");
}
}