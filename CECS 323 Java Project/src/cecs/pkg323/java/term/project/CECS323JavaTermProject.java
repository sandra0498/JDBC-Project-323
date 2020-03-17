package cecs.pkg323.java.term.project;

import java.sql.*;
import java.util.Scanner;
import java.util.ArrayList;

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
    static final String displayFormat="%-30s%-30s%-30s%-30s\n";
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

 //Constructing the database URL connection string
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
        
        //While Loop
        //Will loop the Main Menu of what the User can do with the Program
        //Will exit out of Loop at the end when User says no to continuing 
        String answer = "yes";
        ResultSet rs = null;
        while(answer.equalsIgnoreCase("yes")) {
            //Menu options
            System.out.println("What would you like to do?");
            System.out.println("(1)Select Data\n(2)Select Specific Data\n(3)Insert Data\n(4)Remove Book");

            //Gets the user's input on what option they would like to use
            int choice = in.nextInt();
            switch(choice){
                //Case 1: Select Data
                //Will show all data from any of the tables that the user chooses
                case 1:
                    System.out.println("Which data would you like to select?\n(1)Writing Groups\n(2)Publishers\n(3)Books");

                    //User chooses a table to select from
                    int select = in.nextInt();
                    switch(select){
                        //Case 1: User selects data from Writing Groups Table
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

                        //Case 2: User selects data from Publishers Table
                        case 2:
                            ////List all publishers
                            //Select publisherName, publisherAddress, publisherPhone, publisherEmail
                            //From Publishers
                            System.out.println("Creating another statement...");
                            String thirdSQL = "SELECT publisherName, publisherAddress, publisherPhone, publisherEmail FROM Publishers";
                            PreparedStatement selectStat = conn.prepareStatement(thirdSQL);
                            
                            ResultSet resultPublishers = selectStat.executeQuery();

                            System.out.printf(displayFormat, "publisherName", "publisherAddress", "publisherPhone", "publisherEmail");
                            while (resultPublishers.next()) {
                                //Retrieve by column name
                                String name = resultPublishers.getString("publisherName");
                                String address = resultPublishers.getString("publisherAddress");
                                String phone = "" + resultPublishers.getInt("publisherPhone");
                                String email = resultPublishers.getString("publisherEmail");

                                //Display values
                                System.out.printf(displayFormat, 
                                    dispNull(name), dispNull(address), dispNull(phone), dispNull(email));
                            }
                            break;

                        //Case 3: User selects data from Books Table
                        case 3:
                            ////List all book titles
                            //Select groupName, bookTitle, publisherName, yearPublished, numberPages
                            //From Books;
                            System.out.println("Creating another statement...");
                            stmt = conn.createStatement();
                            String fifthSQL = "SELECT groupName, bookTitle, publisherName, yearPublished, numberPages FROM Books";
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

                //Case 1: Select Specific Data
                //Will show all data from any of the tables that the user chooses
                //This is for more specific searches if a user is looking for specific books, publishers, or writing groups
                case 2:
                    System.out.println("Which specific data would you like to select from?\n(1)Writing Groups\n(2)Publishers\n(3)Books");

                    int table = in.nextInt();
                    Scanner input = new Scanner(System.in);
                    switch(table){
                        //Case 1: User selects specific data from Writing Groups Table
                        case 1:
                            //List all data for a group specified by the user
                            //Select groupName, headWriter, yearFormed, subject 
                            //From WritingGroups
                            //Where groupName = 'userInput';
                            System.out.println("Creating another statement...");
                            String secondSQL = "SELECT groupName, headWriter, yearFormed, subject FROM WritingGroup\n"
                                + "WHERE groupName = ?";
                            System.out.println(secondSQL);
                            System.out.print("Insert:");
                            String userInput = input.nextLine();
                            PreparedStatement pStmt = conn.prepareStatement(secondSQL);
                            pStmt.setString(1, userInput);
                            rs = pStmt.executeQuery();

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

                        //Case 2: User selects specific data from Publishers Table
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
                            System.out.print("Insert:");
                            userInput = input.nextLine();
                            pStmt.setString(1, userInput);
                            rs = pStmt.executeQuery();

                            System.out.printf(displayFormat, "publisherName", "publisherAddress", "publisherPhone", "publisherEmail");
                            while (rs.next()) {
                                //Retrieve by column name
                                String pName = rs.getString("publisherName");
                                String pAddress = rs.getString("publisherAddress");
                                String pPhone = "" + rs.getInt("publisherPhone");
                                String pEmail = rs.getString("publisherEmail");

                                //Display values
                                System.out.printf(displayFormat, 
                                    dispNull(pName), dispNull(pAddress), dispNull(pPhone), dispNull(pEmail));
                            }
                            break;

                        //Case 3: User selects specific data from Books Table
                        case 3:
                            ////List all data for a book specified by the user
                            //Select groupName, bookTitle, publisherName, yearPublished, numberPages
                            //From Books
                            //Where bookTitle = 'userInput';
                            System.out.println("Creating another statement...");
                            String sixthSQL = "SELECT groupName, bookTitle, publisherName, yearPublished, numberPages FROM Books\n"
                                + "WHERE bookTitle = ?";
                            System.out.println(sixthSQL);
                            System.out.print("Insert:");
                            pStmt = conn.prepareStatement(sixthSQL);
                            userInput = input.nextLine();
                            pStmt.setString(1, userInput);
                            rs = pStmt.executeQuery();                            
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
                            break;
                    }
                    break;

                //Case 3: Insert Data
                //Will allow the user to insert data for a book or a publisher
                //If the user inserts data for a book, it will show a list of all the publishers available 
                    //for the user to choose from or they can abort the inserting book process
                //If the user inserts data for a publisher, it will also ask the user for the name of the 
                    //publisher that they would like to update with the new one they are inserting
                case 3:
                    System.out.println("What data would you like to insert?\n(1)Books\n(2)Publishers");

                        int insert = in.nextInt();
                        switch(insert){
                            //Case 1: Insert new book
                            case 1:
                                ////Insert new book
                                //Insert Into Books (groupName, bookTitle, publisherName, yearPublished, numberPages)
                                //Values ('userInputs');
                                PreparedStatement insertBook = conn.prepareStatement("insert into books(groupName, "
                                    + "bookTitle, publisherName, yearPublished, numberPages) Values(?,?,?,?,?)");
                                
                                System.out.println("Name of group? Please choose a number, or choose a number outside the range to cancel.");
                                
                                ////List all group name
                                //Select groupName From WritingGroups
                                String temSQL = "SELECT groupName FROM WritingGroups";
                                PreparedStatement selectSt = conn.prepareStatement(temSQL);
                            
                                ResultSet resultGroup = selectSt.executeQuery();

                                ArrayList<String> groups = new ArrayList<>();
                                System.out.printf(displayFormat, "groupName");
                                while (resultGroup.next()) {
                                    //Retrieve by column name
                                    String name = resultGroup.getString("groupName");
                                    
                                    groups.add(name);
                                }
                                
                                //For loop will print and iterates through the names of publishers for the user to choose from
                                for (int i = 0; i < groups.size(); i++) {
                                    System.out.println((i + 1) + ". " + groups.get(i));
                                }
                                
                                int groupName = in.nextInt();
                                if (groupName <= 0 || groupName > groups.size()) {
                                    break;
                                }
                                
                                String group = groups.get(groupName - 1);
                                System.out.println("Which book title would you want to insert?");
                                String bookTitle = in.nextLine();
                                System.out.println("Name of publisher? Please choose a number, or choose a number outside the range to cancel.");
                                
                                ////List all publishers name
                                //Select publisherName From Publishers
                                String thirdSQL = "SELECT publisherName FROM Publishers";
                                PreparedStatement selectStat = conn.prepareStatement(thirdSQL);
                            
                                ResultSet resultPublishers = selectStat.executeQuery();

                                ArrayList<String> names = new ArrayList<>();
                                System.out.printf(displayFormat, "publisherName");
                                while (resultPublishers.next()) {
                                    //Retrieve by column name
                                    String name = resultPublishers.getString("publisherName");
                                    
                                    names.add(name);
                                }
                                
                                //For loop will print and iterates through the names of publishers for the user to choose from
                                for (int i = 0; i < names.size(); i++) {
                                    System.out.println((i + 1) + ". " + names.get(i));
                                }
                                
                                int nameChoice = in.nextInt();
                                if (nameChoice <= 0 || nameChoice > names.size()) {
                                    break;
                                }
                                
                                String pub = names.get(nameChoice - 1);
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
                                System.out.println("Number of rows affected :" + resultNum);
                                break;

                            //Insert new Publisher and update 
                            case 2:
                                ////Insert new publisher and update all books published by one publisher to be published by new publisher. 
                                //Insert Into Publishers (publisherName, publisherAddress, publisherPhone, publisherEmail)
                                //Values ('userInputs');
                                PreparedStatement insertPublisher = conn.prepareStatement("insert into publishers(publisherName, "
                                    + "publisherAddress, publisherPhone, publisherEmail) Values(?,?,?,?)");
                                
                                System.out.println("Name of publisher to insert?");
                                String name = in.nextLine();
                                System.out.println("Address of publisher?");
                                String address = in.nextLine();
                                System.out.println("Phone number of publisher?");
                                int phone = in.nextInt();
                                System.out.println("Publisher's email?");
                                String email = in.nextLine();
                                
                                insertPublisher.setString(1, name);
                                insertPublisher.setString(2, address);
                                insertPublisher.setInt(3, phone);
                                insertPublisher.setString(4, email);
                                int resultNumber = insertPublisher.executeUpdate();
                                
                                System.out.println("Number of rows affected :" + resultNumber);
                                
                                //Update Books
                                //Set publisherName = 'newPublisherName'
                                //Where publisherName = 'userInput';
                                PreparedStatement updateBook = conn.prepareStatement("update books set publisherName = '"
                                    + name +"' where publisherName = ?");
                                
                                System.out.println("Which publisher would you like to replace? To cancel the process, choose a number outside the range.");
                                
                                String tempSQL = "SELECT publisherName FROM Publishers";
                                selectStat = conn.prepareStatement(tempSQL);
                            
                                ResultSet currentPublishers = selectStat.executeQuery();

                                ArrayList<String> current = new ArrayList<>();
                                System.out.printf(displayFormat, "publisherName");
                                while (currentPublishers.next()) {
                                    //Retrieve by column name
                                    String old = currentPublishers.getString("publisherName");
                                    
                                    current.add(old);
                                }
                                
                                //For loop will print and iterates through the names of publishers for the user to choose from
                                for (int i = 0; i < current.size(); i++) {
                                    System.out.println((i + 1) + ". " + current.get(i));
                                }
                                
                                int oldPublisher = in.nextInt();
                                if (oldPublisher <= 0 || oldPublisher > current.size()) {
                                    break;
                                }                                
                                
                                String replace = current.get(oldPublisher - 1);

                                updateBook.setString(1, replace);
                                int resultRow = updateBook.executeUpdate();
                                System.out.println("Number of rows affected :" + resultRow);
                                break;
                        }                     
                    break;

                case 4:
                    System.out.println("Which book would you like to delete from the table? To cancel, please choose a number outside the range of the menu.");
                    PreparedStatement statement = conn.prepareStatement("delete from books where bookTitle = ? and groupName = ?");
                    
                    String tempSQL = "SELECT groupName, bookTitle FROM Books";
                    PreparedStatement selectStat = conn.prepareStatement(tempSQL);
                            
                   rs = selectStat.executeQuery();

                    ArrayList<String> titleList = new ArrayList<>();
                    ArrayList<String> groupList = new ArrayList<>();
                    while (rs.next()) {
                        //Retrieve by column name
                        String book = rs.getString("bookTitle");
                        String groupres = rs.getString("groupName");
                                    
                        titleList.add(book);
                        groupList.add(groupres);
                    }
                                
                    //For loop will print and iterates through the names of publishers for the user to choose from
                    for (int i = 0; i < titleList.size(); i++) {
                        System.out.println((i + 1) + "." + titleList.get(i) + "\t\t" + groupList.get(i));
                    }
                                
                    int remove = in.nextInt();
                    if (remove > 0 || remove < titleList.size()) {
                        String groupres = groupList.get(remove - 1);
                        String title = titleList.get(remove - 1);
                        statement.setString(1, title);
                        statement.setString(2, groupres);
                        int result = statement.executeUpdate();
                        System.out.println("Number of rows affected ::" + result);
                    }

 
                    ////Remove a book specified by the user
                    //Delete From Books
                    //Where bookTitle = 'userInput';
                    
                    break;
            }
            
            //Asks the user if they want to continue using the program
            System.out.println("Would you like to continue using the program? Yes or No");
            answer = in.nextLine();
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
