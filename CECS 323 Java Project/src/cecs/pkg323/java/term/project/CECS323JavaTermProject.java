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
            
            insertingDataChoice(in);
            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT groupname, headWriter, yearFormed,subject FROM WritingGroup";
            ResultSet rs = stmt.executeQuery(sql);

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
    
    public static void insertingDataChoice(Scanner in){
        System.out.println("Where would you like to insert data?\n(1)Books\n(2)Publishers");
        
        int choice = in.nextInt();
        String databaseChoice = "";
        switch(choice){
            case 1:
                databaseChoice = "books";
            
            case 2:
                databaseChoice = "publishers";
        }
        
    }
}//end FirstExample}