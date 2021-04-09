import java.sql.*;
import javax.swing.JOptionPane;

public class testdriver {
    public static void main(String args[]){
    	 dbSetup my = new dbSetup();
	    //Building the connection
	     Connection conn = null;
	     try {
	        Class.forName("org.postgresql.Driver");
	        conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/db907_group12_project2",
	           my.user, my.pswd);
	     } catch (Exception e) {
	        e.printStackTrace();
	        System.err.println(e.getClass().getName()+": "+e.getMessage());
	        System.exit(0);
	     }//end try catch


	     ManagerGUI test = new ManagerGUI(conn);
        
    }
}
