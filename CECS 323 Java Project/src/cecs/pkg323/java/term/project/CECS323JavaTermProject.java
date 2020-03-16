package cecs.pkg323.java.term.project;

import java.sql.*;
import java.util.Scanner;

/**
 * @author Michael, Sandra, Nicki
 */
public class CECS323JavaTermProject {
    //  Database credentials
    static String USER;
    static String PASS;
    static String DBNAME;
    //This is the specification for the printout that I'm doing:
    //each % denotes the start of a new field.
    //The - denotes left justification.
    //The number indicates how wide to make the field.
    //The "s" denotes that it's a string.  All of our output in this test are 
    //strings, but that won't always be the case.
    static final String displayFormat="%-15s%-15s%-15s%-15s\n";
// JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    static String DB_URL = "jdbc:derby://localhost:1527/";
//            + "testdb;user=";
/**
 * Takes the input string and outputs "N/A" if the string is empty or null.
 * @param input The string to be mapped.
 * @return  Either the input string or "N/A" as appropriate.
 */
    public static String dispNull (String input) {
        //because of short circuiting, if it's null, it never checks the length.
        if (input == null || input.length() == 0)
            return "N/A";
        else
            return input;
    }
    
    public static void main(String[] args) {
        //Prompt the user for the database name, and the credentials.
        //If your database has no credentials, you can update this code to 
        //remove that from the connection string.
        Scanner in = new Scanner(System.in);
        System.out.print("Name of the database (not the user account): ");
        DBNAME = in.nextLine();
       // System.out.print("Database user name: ");
        //USER = in.nextLine();
//        System.out.print("Database password: ");
//        PASS = in.nextLine();
//        //Constructing the database URL connection string
        DB_URL = DB_URL + DBNAME;
        Connection conn = null; //initialize the connection
        Statement stmt = null;  //initialize the statement that we're using
        try {
            String pathtoconnector= "com.mysql.cj.jdbc.Driver";
            
            //STEP 2: Register JDBC driver
            Class.forName(pathtoconnector);

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL);
            
            
            
            String answer = "yes";
            ResultSet rs = null;
            while(answer.equalsIgnoreCase("yes")) {
                System.out.println("What would you like to do?");
                System.out.println("(1)Select Data\n(2)Select Specific Data\n(3)Insert Data\n(4)Remove Data");
        
                int choice = in.nextInt();
                switch(choice){
                    case 1:
                        System.out.println("Which data would you like to select?\n(1)Writing Groups\n(2)Publishers\n(3)Books");
        
                        int select = in.nextInt();
                        switch(select){
                            case 1:
                                //STEP 4: Execute a query
                                //List all writing groups
                                //Select groupName, headWriter, yearFormed, subject 
                                //From WritingGroups;
                                System.out.println("Creating statement...");
                                stmt = conn.createStatement();
                                String firstSQL;
                                firstSQL = "SELECT groupname, headWriter, yearFormed,subject FROM WritingGroup";
                                System.out.println(firstSQL);
                                rs = stmt.executeQuery(firstSQL);

                                //STEP 5: Extract data from result set
                                System.out.printf(displayFormat, "groupname", "headwriter", "yearformed", "subject");
                                while (rs.next()) {
                                    //Retrieve by column name
                                    String group = rs.getString("groupname");
                                    String head = rs.getString("headwriter");
                                    String year = "" + rs.getInt("yearformed");
                                    String subject = rs.getString("subject");

                                    //Display values
                                    // System.out.println("goes up to here!!!");
                                    System.out.printf(displayFormat, 
                                        dispNull(group), dispNull(head), dispNull(year), dispNull(subject));
                                }
                                break;
            
                            case 2:
                                ////List all publishers
                                //Select publisherName, publisherAddress, publisherPhone, publisherEmail
                                //From Publishers
                                System.out.println("Creating another statement...");
                                stmt = conn.createStatement();
                                String thirdSQL = "SELECT publisherName, publisherAddress, publisherPhone, publisherEmail FROM Publishers;";
                                System.out.println(thirdSQL);
                                rs = stmt.executeQuery(thirdSQL);

                                System.out.printf(displayFormat, "publisherName", "publisherAddress", "publisherPhone", "publisherEmail");
                                while (rs.next()) {
                                    //Retrieve by column name
                                    String name = rs.getString("publisherName");
                                    String address = rs.getString("publisherAddress");
                                    String phone = "" + rs.getInt("publisherPhone");
                                    String email = rs.getString("publisherEmail");

                                    //Display values
                                    System.out.printf(displayFormat, 
                                        dispNull(name), dispNull(address), dispNull(phone), dispNull(email));
                                }
                                break;
                                
                            case 3:
                                ////List all book titles
                                //Select groupName, bookTitle, publisherName, yearPublished, numberPages
                                //From Books;
                                System.out.println("Creating another statement...");
                                stmt = conn.createStatement();
                                String fifthSQL = "SELECT groupName, bookTitle, publisherName, yearPublished, numberPages FROM Books;";
                                System.out.println(fifthSQL);
                                rs = stmt.executeQuery(fifthSQL);

                                System.out.printf(displayFormat, "groupName", "bookTitle", "publisherName", "yearPublished", "numberPages");
                                while (rs.next()) {
                                    //Retrieve by column name
                                    String gName = rs.getString("groupName");
                                    String title = rs.getString("bookTitle");
                                    String pName = rs.getString("publisherName"); 
                                    String year = "" + rs.getInt("yearPublished");
                                    String numPages = "" + rs.getInt("numberPages");

                                    //Display values
                                    System.out.printf(displayFormat, 
                                        dispNull(gName), dispNull(title), dispNull(pName), dispNull(year), dispNull(numPages));
                                }
                                break;
                        }
                        break;
            
                    case 2:
                        System.out.println("Which specific data would you like to select from?\n(1)Writing Groups\n(2)Publishers\n(3)Books");
        
                        int table = in.nextInt();
                        Scanner input = new Scanner(System.in);
                        switch(table){
                            case 1:
                                //List all data for a group specified by the user
                                //Select groupName, headWriter, yearFormed, subject 
                                //From WritingGroups
                                //Where groupName = 'userInput';
                                System.out.println("Creating another statement...");
                                String secondSQL = "SELECT groupName, headWriter, yearFormed, subject FROM WritingGroups\n"
                                    + "WHERE groupName = ?";
                                System.out.println(secondSQL);
                                PreparedStatement pStmt = conn.prepareStatement(secondSQL);
                                String userInput = input.nextLine();
                                pStmt.setString(1, userInput);
                                rs = pStmt.executeQuery(secondSQL);

                                System.out.printf(displayFormat, "groupname", "headwriter", "yearformed", "subject");
                                while (rs.next()) {
                                    //Retrieve by column name
                                    String group = rs.getString("groupname");
                                    String head = rs.getString("headwriter");
                                    String year = "" + rs.getInt("yearformed");
                                    String subject = rs.getString("subject");

                                    //Display values
                                    System.out.printf(displayFormat, 
                                        dispNull(group), dispNull(head), dispNull(year), dispNull(subject));
                                }
                                break;
                                
                            case 2:
                                ////List all data for a publisher specified by the user
                                //Select publisherName, publisherAddress, publisherPhone, publisherEmail
                                //From Publishers
                                //Where publisherName = 'userInput';
                                System.out.println("Creating another statement...");
                                String fourthSQL = "SELECT publisherName, publisherAddress, publisherPhone, publisherEmail FROM Publishers\n"
                                    + "WHERE publisherName = ?";
                                System.out.println(fourthSQL);
                                pStmt = conn.prepareStatement(fourthSQL);
                                userInput = input.nextLine();
                                pStmt.setString(1, userInput);
                                rs = pStmt.executeQuery(fourthSQL);

                                System.out.printf(displayFormat, "groupname", "headwriter", "yearformed", "subject");
                                while (rs.next()) {
                                    //Retrieve by column name
                                    String group = rs.getString("groupname");
                                    String head = rs.getString("headwriter");
                                    String year = "" + rs.getInt("yearformed");
                                    String subject = rs.getString("subject");

                                    //Display values
                                    System.out.printf(displayFormat, 
                                        dispNull(group), dispNull(head), dispNull(year), dispNull(subject));
                                }
                                break;
                                
                            case 3:
                                ////List all data for a book specified by the user
                                //Select groupName, bookTitle, publisherName, yearPublished, numberPages
                                //From Books
                                //Where bookTitle = 'userInput';
                                System.out.println("Creating another statement...");
                                String sixthSQL = "SELECT groupName, bookTitle, publisherName, yearPublished, numberPages FROM Books\n"
                                    + "WHERE bookTitle = ?";
                                System.out.println(sixthSQL);
                                pStmt = conn.prepareStatement(sixthSQL);
                                userInput = input.nextLine();
                                pStmt.setString(1, userInput);
                                rs = pStmt.executeQuery(sixthSQL);

                                System.out.printf(displayFormat, "groupname", "bookTitle", "publisherName", "yearPublished", "numberPages");
                                while (rs.next()) {
                                    //Retrieve by column name
                                    String gName = rs.getString("groupName");
                                    String title = rs.getString("bookTitle");
                                    String pName = rs.getString("publisherName"); 
                                    String year = "" + rs.getInt("yearPublished");
                                    String numPages = "" + rs.getInt("numberPages");

                                    //Display values
                                    System.out.printf(displayFormat, 
                                        dispNull(gName), dispNull(title), dispNull(pName), dispNull(year), dispNull(numPages));
                                }
                                System.out.println("Number of rows affected :: " + result);

                                break;
                        }
                        break;
                    
                    case 3:

                    ////Insert new book
                    //Insert Into Books (groupName, bookTitle, publisherName, yearPublished, numberPages)
                    //Values ('userInputs');
                    PreparedStatement insertBook = conn.prepareStatement("insert into books(groupName, "
                            + "bookTitle, publisherName, yearPublished, numberPages) Values(?,?,?,?,?)");
                    System.out.println("Name of group to insert?");
                    String group = in.nextLine();
                    
                    System.out.println("Which book title would you want to insert?");
                    String bookTitle = in.nextLine();
                    
                    System.out.println("Name of publisher ?");
                    String pub = in.nextLine();
                    
                    System.out.println("Year published?");
                    int year = in.nextInt();
                    
                    System.out.println("Number of pages?");
                    int num = in.nextInt();
                    
                    insertBook.setString(1, group);
                    insertBook.setString(2, bookTitle);
                    insertBook.setString(3, pub);
                    insertBook.setInt(4, year);
                    insertBook.setInt(5, num);
                    
                    int resultNum = insertBook.executeUpdate();
                    
                     System.out.println("Number of rows affected ::" + resultNum);
                    ////Insert new book
                    //Insert Into Books (groupName, bookTitle, publisherName, yearPublished, numberPages)
                    //Values ('userInputs');
                    PreparedStatement insertBook = conn.prepareStatement("insert into books(groupName, "
                            + "bookTitle, publisherName, yearPublished, numberPages) Values(?,?,?,?,?)");
                    System.out.println("Name of group to insert?");
                    String group = in.nextLine();
                    
                    System.out.println("Which book title would you want to insert?");
                    String bookTitle = in.nextLine();
                    
                    System.out.println("Name of publisher ?");
                    String pub = in.nextLine();
                    
                    System.out.println("Year published?");
                    int year = in.nextInt();
                    
                    System.out.println("Number of pages?");
                    int num = in.nextInt();
                    
                    insertBook.setString(1, group);
                    insertBook.setString(2, bookTitle);
                    insertBook.setString(3, pub);
                    insertBook.setInt(4, year);
                    insertBook.setInt(5, num);
                    
                    int resultNum = insertBook.executeUpdate();
                    
                     System.out.println("Number of rows affected ::" + resultNum);

                        ////Insert new publisher and update all books published by one publisher to be published by new publisher. 
                        //Insert Into Publishers (publisherName, publisherAddress, publisherPhone, publisherEmail)
                        //Values ('userInputs');

                        //Update Books
                        //Set publisherName = 'newPublisherName'
                        //Where publisherName = 'userInput';
                        break;
                    
                    case 4:
                        ////Remove a book specified by the user
                        //Delete From Books
                        //Where bookTitle = 'userInput';
                        System.out.println("What is the book title that you would like to delete?");
                        PreparedStatement statement = conn.prepareStatement("delete from books where bookTitle = ?");
                        String title = in.nextLine();
                        statement.setString(1, title);
                        int result = statement.executeUpdate();
                  
                        System.out.println("Number of rows affected :: " + result);
                        break;
                }
            }
            
          
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch(SQLSyntaxErrorException see){
           Exception sse = new SQLSyntaxErrorException("Wrong syntax");
           sse.getMessage();
        }
        catch (SQLException se) {
            //Handle errors for JDBC
            System.out.println("Could not retrieve database metadata " + se.getMessage());
        } 
        catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            // this block will be entered after all the inserts/deletes 
            //when data is displayed 
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
                
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }//end main
}//end FirstExample}
