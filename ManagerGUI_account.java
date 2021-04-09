import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*; 
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.sql.*;
import javax.swing.BoxLayout;

// This page was planned to be the account page, but since login portal is not required,
// this page is now the trending page that show the most and least popular products. 

public class ManagerGUI_account extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    // trending page container
    private JPanel ACCOUNT_PAGE;
    // welcome statement
    private JLabel lbl_welcome;

    // most popular statement
    private JPanel most;
    private JLabel most_title;
    private JButton rec1;

    private JLabel most_1;
    private JLabel most_2;

    // Make extra space
    private JLabel space1;
    private JLabel space2;
    private JLabel space3;
  


    // least popular statement
    private JPanel least;
    private JLabel least_title;

    private JLabel least_1;
    private JLabel least_2;
    private JButton rec2;

    //trending array
    private int trends[];
    private String trendnames[];

    //sql connection
    private Connection conn;

    //helper functions

    //trending function
    private int[] getTrending() {
        int counts[] = new int[20]; //will hold a value for the number of occurences of each item
        int[] topcounts = new int[4]; //for the top and bottom 2 items

        int first = 0;
        int second = 0;
        int bot1 = Integer.MAX_VALUE; //worst
        int bot2 = Integer.MAX_VALUE; //2nd worst

        //execute an SQL COUNT statement for each product from the Orders table
        for (int i = 1; i <= 20; i++) {
            String str_getCount = String.format("SELECT COUNT(*) FROM Orders WHERE \'%d\' = ANY (food)", i);
            try {
                Statement stmt = this.conn.createStatement();
                ResultSet result = stmt.executeQuery(str_getCount);
                while (result.next()) {
                    counts[i-1] = result.getInt("count");
                }

                //updating top 2 and bottom 2 items
                if (counts[i-1] > first) {
                    //need to swap here
                    int tmp = topcounts[0];
                    second = first;
                    topcounts[1] = tmp;
                    first = counts[i-1];
                    topcounts[0] = i;
                }
                else if (counts[i-1] > second) {
                    second = counts[i-1];
                    topcounts[1] = i;
                }

                if (counts[i-1] < bot1) {
                    //need to swap
                    int tmp = topcounts[2];
                    bot2 = bot1;
                    topcounts[3] = tmp;
                    bot1 = counts[i-1];
                    topcounts[2] = i;
                }
                else if (counts[i-1] < bot2) {
                    bot2 = counts[i-1];
                    topcounts[3] = i;
                }

                //based on testing, the top item should be 18 and 9, and the bottom 2 should be 20 and 7
            }
            catch (Exception e) {
                System.out.println(String.format("Error accessing Database: SELECT COUNT(*) FROM Orders WHERE \'%d\' = ANY (food)", i));
            }
        }

        return topcounts;
    }

    //get product name from given productid
    String getProductName(int productid) {
        String pname = "";

        //run SQL SELECT statement to get productname matching the given id
        String str_getName = String.format("SELECT productname FROM Products WHERE productid = \'%d\'", productid);
        try {
            Statement stmt = this.conn.createStatement();
            ResultSet result = stmt.executeQuery(str_getName);
            while (result.next()) {
                pname = result.getString("productname");
            }
        }
        catch (Exception e) {
            System.out.println(String.format("Error accessing Database: SELECT productname FROM Products WHERE productid = \'%d\'", productid));
        }
        return pname;
    }

    
    // default initializer
    public ManagerGUI_account(Connection con) {
        this.conn = con;

        
        this.ACCOUNT_PAGE = new JPanel();
        BoxLayout layout = new BoxLayout(ACCOUNT_PAGE, BoxLayout.Y_AXIS);
        ACCOUNT_PAGE.setLayout(layout);

        // most popular title
        most = new JPanel();
        BoxLayout lmost = new BoxLayout(most, BoxLayout.Y_AXIS);
        most.setLayout(lmost);
        this.most_title = new JLabel(String.format("- Most Popular Products"));
        most_title.setFont(new Font("Verdana", Font.PLAIN, 40));

        //request trending
        trends = this.getTrending();
        trendnames = new String[trends.length];
        //get product names for the returned product ids
        for (int i = 0; i < trends.length; i++) {
            trendnames[i] = this.getProductName(trends[i]);
        }


        this.most_1 = new JLabel(String.format("1. " + trendnames[0]));
        most_1.setFont(new Font("Verdana", Font.PLAIN, 30));
        this.most_2 = new JLabel(String.format("2. " + trendnames[1]));
        most_2.setFont(new Font("Verdana", Font.PLAIN, 30));


        rec1 = new JButton("Recommendations");
        rec1.addActionListener(this); 

        // add to panel
        most.add(most_title);
        most.add(most_1);
        most.add(most_2);
        most.add(rec1);

        // space
        space1 = new JLabel("    ");
        space2 = new JLabel("    ");
        space3 = new JLabel("    ");
        


        // least popular title
        least = new JPanel();
        BoxLayout lleast = new BoxLayout(least, BoxLayout.Y_AXIS);
        least.setLayout(lleast);
        this.least_title = new JLabel(String.format("- Least Popular Products"),SwingConstants.CENTER);
        least_title.setFont(new Font("Verdana", Font.PLAIN, 40));


        this.least_1 = new JLabel(String.format("1. " + trendnames[2]));
        least_1.setFont(new Font("Verdana", Font.PLAIN, 30));  
        this.least_2 = new JLabel(String.format("2. " + trendnames[3]));
        least_2.setFont(new Font("Verdana", Font.PLAIN, 30));

        rec2 = new JButton("Recommendations");
        rec2.addActionListener(this); 

        // add to panel
        least.add(least_title);
        least.add(least_1);
        least.add(least_2);
        least.add(rec2);

        
        most.setAlignmentX(Component.CENTER_ALIGNMENT);
        least.setAlignmentX(Component.CENTER_ALIGNMENT);
        ACCOUNT_PAGE.add(most);
        ACCOUNT_PAGE.add(space1);
        ACCOUNT_PAGE.add(space2);
        ACCOUNT_PAGE.add(space3);
        ACCOUNT_PAGE.add(least);    
    }

    public JPanel GET_PANEL() {
        return this.ACCOUNT_PAGE;
    }

    // ActionListener inherited function
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == rec1){
            JOptionPane.showMessageDialog(null,"Try higher the price");
        }
        else if(e.getSource() == rec2){
            JOptionPane.showMessageDialog(null,"Try lower the price or make it into a deal");
        }
    }
}