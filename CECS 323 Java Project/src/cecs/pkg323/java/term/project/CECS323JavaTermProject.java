package cecs.pkg323.java.term.project;

import java.sql.*;
import java.util.Scanner;

/**
 *
 * @author Mimi Opkins with some tweaking from Dave Brown
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
            
            //STEP 4: Execute a query
            //List all writing groups
            //Select groupName, headWriter, yearFormed, subject 
            //From WritingGroups;
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String firstSQL;
            firstSQL = "SELECT groupname, headWriter, yearFormed,subject FROM WritingGroup";
            System.out.println(firstSQL);
            ResultSet rs = stmt.executeQuery(firstSQL);

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
            
            //List all data for a group specified by the user
            //Select groupName, headWriter, yearFormed, subject 
            //From WritingGroups
            //Where groupName = 'userInput';
            System.out.println("Creating another statement...");
            String secondSQL = "SELECT groupName, headWriter, yearFormed, subject FROM WritingGroup\n"
                    + "WHERE groupName = ?";
            System.out.println(secondSQL);
            PreparedStatement pStmt = conn.prepareStatement(secondSQL);
            Scanner input = new Scanner(System.in);
            System.out.println("Please input the group name: ");
            String userInput = input.nextLine();
            pStmt.setString(1, userInput);
            rs = pStmt.executeQuery();
            
            // rs = the whole tuple 
            
            //iterates through the whole tuple to get each row 
            while(rs.next()){
            // each string represents a row 
            String groupName = rs.getString("groupName");
            String writer = rs.getString("headWriter");
            String year = ""+ rs.getInt("yearFormed");
            String subject = rs.getString("subject");
            
            //prints out each row 
            System.out.printf(displayFormat, 
                dispNull(groupName), dispNull(writer), dispNull(year), dispNull(subject));
            }
            
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
            ////Insert new book
            //Insert Into Books (groupName, bookTitle, publisherName, yearPublished, numberPages)
            //Values ('userInputs');
            
            ////Insert new publisher and update all books published by one publisher to be published by new publisher. 
            //Insert Into Publishers (publisherName, publisherAddress, publisherPhone, publisherEmail)
            //Values ('userInputs');

            //Update Books
            //Set publisherName = 'newPublisherName'
            //Where publisherName = 'userInput';
            
            ////Remove a book specified by the user
            //Delete From Books
            //Where bookTitle = 'userInput';
            
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
