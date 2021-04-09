import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*; 
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.*; 
import java.sql.*;

public class ManagerGUI_product_side extends JFrame implements ItemListener, ActionListener {
    private static final long serialVersionUID = 1L;

    // sql connection
    private Connection conn;

    // restaurant name
    private String PAGE_NAME = "Sides";
    // Entree container
    private JPanel ENTREES_PAGE;
    // welcome statement
    private JLabel lbl_welcome;

    private JPanel e1;
    private JPanel e2;
    private JPanel e3;
    private JPanel e4;
   

    private JLabel change;
    private JLabel to;  
    
    // ComboBox
    String[] opt = new String[] {"","Price", "Availability"};
    private JComboBox<String> c1;
    private JComboBox<String> c2;
    private JComboBox<String> c3;
    private JComboBox<String> c4;


    // JTextField t
    private JTextField t1; 
    private JTextField t2;  
    private JTextField t3;  
    private JTextField t4;  
    
 

    // Submit Button
    private JButton b1;
    private JButton b2;
    private JButton b3;
    private JButton b4;

    // if true, change price
    // if false, change avaliability
    Boolean Price_e1 = false;
    Boolean Price_e2 = false;
    Boolean Price_e3 = false;
    Boolean Price_e4 = false;

    // Help button
    private JButton help;
    private JPanel help_panel;

    private void update_price(int id, float price){
        
        String update_query = String.format("UPDATE products SET price = \'%f\' WHERE productid = \'%d\'", price, id);
        try {
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(update_query);
            JOptionPane.showMessageDialog(null,"Updated");
        } 
        catch(Exception e){
            System.out.println(String.format("Error accessing Database: "));
        }
           
    }

    private void update_ava(int id, int availability){
        System.out.println("AVA");
        String update_query = String.format("UPDATE menus SET availability = \'%d\' WHERE id = \'%d\'", availability, id);
        try {
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(update_query);
            JOptionPane.showMessageDialog(null,"Updated");
        }
        catch(Exception e){
            System.out.println(String.format("Error accessing Database: "));
        }
    }



    // default initializer
    public ManagerGUI_product_side(Connection con) {
        this.conn = con;
        
        this.ENTREES_PAGE = new JPanel();
        this.ENTREES_PAGE.setLayout(new GridLayout(14,1));
        this.lbl_welcome = new JLabel(String.format("Edit %s", PAGE_NAME),SwingConstants.CENTER);
        lbl_welcome.setFont(new Font("Verdana", Font.PLAIN, 30));
        ENTREES_PAGE.add(lbl_welcome);

        // e1 
        e1 = new JPanel();
        change = new JLabel("French Fries: Change ");
        c1 = new JComboBox<>(opt);
        to = new JLabel(" to ");
        t1 = new JTextField("",20);
        b1 = new JButton("Submit");
      
        e1.add(change);
        e1.add(c1);
        e1.add(to);
        e1.add(t1);
        e1.add(b1);
        ENTREES_PAGE.add(e1);
        c1.addItemListener(this);
        b1.addActionListener(this); 


        // e2 
        e2 = new JPanel();
        change = new JLabel("Onion Rings: Change ");
        c2 = new JComboBox<>(opt);
        to = new JLabel(" to ");
        t2 = new JTextField("",20);
        b2 = new JButton("Submit");
      
        e2.add(change);
        e2.add(c2);
        e2.add(to);
        e2.add(t2);
        e2.add(b2);
        ENTREES_PAGE.add(e2);
        c2.addItemListener(this);
        b2.addActionListener(this); 

        // e3 
        e3 = new JPanel();
        change = new JLabel("Mac and Cheese: Change ");
        c3 = new JComboBox<>(opt);
        to = new JLabel(" to ");
        t3 = new JTextField("",20);
        b3 = new JButton("Submit");
      
        e3.add(change);
        e3.add(c3);
        e3.add(to);
        e3.add(t3);
        e3.add(b3);
        ENTREES_PAGE.add(e3);
        c3.addItemListener(this);
        b3.addActionListener(this); 

        // e4
        e4 = new JPanel();
        change = new JLabel("Mashed Potatoes: Change ");
        c4 = new JComboBox<>(opt);
        to = new JLabel(" to ");
        t4 = new JTextField("",20);
        b4 = new JButton("Submit");
      
        e4.add(change);
        e4.add(c4);
        e4.add(to);
        e4.add(t4);
        e4.add(b4);
        ENTREES_PAGE.add(e4);
        c4.addItemListener(this);
        b4.addActionListener(this); 

         // help
        help_panel = new JPanel();
        help = new JButton("Help");
        help.addActionListener(this);


        help_panel.add(help); 
        ENTREES_PAGE.add(help_panel);
    }

    public JPanel GET_PANEL(int i) {
        return ENTREES_PAGE;
    }

    // ItemListener inherited function (Combo Box)
    public void itemStateChanged(ItemEvent e) 
    { 
        if (e.getSource() == c1) { 
            String combox1 = "";
            combox1 = (String)c1.getSelectedItem();
            if(combox1 == "Price"){
                Price_e1 = true;
            }
            else{
                Price_e1 = false;
            }
            //System.out.println(combox1);
        }
        else if(e.getSource() == c2){
            String combox2 = "";
            combox2 = (String)c2.getSelectedItem();
            if(combox2 == "Price"){
                Price_e2 = true;
            }
            else{
                Price_e2 = false;
            }
            //System.out.println(combox2);
        }
        else if(e.getSource() == c3){
            String combox3 = "";
            combox3 = (String)c3.getSelectedItem();
            if(combox3 == "Price"){
                Price_e3 = true;
            }
            else{
                Price_e3 = false;
            }
            //System.out.println(combox3);
        }
        else if(e.getSource() == c4){
            String combox4 = "";
            combox4 = (String)c4.getSelectedItem();
            if(combox4 == "Price"){
                Price_e4 = true;
            }
            else{
                Price_e4 = false;
            }
            //System.out.println(combox4);
        }
    } 

    // ActionListener inherited function (Submission Buttons)
    public void actionPerformed(ActionEvent e) {
         if (e.getSource() == b1) { 
            if(Price_e1){
                String text1 = "";
                text1 = (String)t1.getText();
                update_price(8,Float.parseFloat(text1));
                //System.out.println(text1);
            }
            else{
                String text1 = "";
                text1 = (String)t1.getText();
                if (text1.toUpperCase().equals("T") || text1.toUpperCase().equals("TRUE") || text1.equals("1")) {
                        update_ava(8,1);
                }
                else{
                        update_ava(8,0);
                }
            }
            
        }
        else if (e.getSource() == b2) { 
            if(Price_e2){
                String text2 = "";
                text2 = (String)t2.getText();
                update_price(9,Float.parseFloat(text2));
                //System.out.println(text2);
            }
            else{
                String text2 = "";
                text2 = (String)t2.getText();
                if (text2.toUpperCase().equals("T") || text2.toUpperCase().equals("TRUE") || text2.equals("1")) {
                        update_ava(9,1);
                }
                else{
                        update_ava(9,0);
                }
            }
        }
        else if (e.getSource() == b3) { 
           if(Price_e3){
                String text3 = "";
                text3 = (String)t3.getText();
                update_price(10,Float.parseFloat(text3));
                //System.out.println(text3);
            }
            else{
                String text3 = "";
                text3 = (String)t3.getText();
                if (text3.toUpperCase().equals("T") || text3.toUpperCase().equals("TRUE") || text3.equals("1")) {
                        update_ava(10,1);
                }
                else{
                        update_ava(10,0);
                }
            }
        }
        else if (e.getSource() == b4) { 
           if(Price_e4){
                String text4 = "";
                text4 = (String)t4.getText();
                update_price(11,Float.parseFloat(text4));
                //System.out.println(text4);
            }
            else{
                String text4 = "";
                text4 = (String)t4.getText();
                if (text4.toUpperCase().equals("T") || text4.toUpperCase().equals("TRUE") || text4.equals("1")) {
                        update_ava(11,1);
                }
                else{
                        update_ava(11,0);
                }
            }
        }

        else if(e.getSource() == help){
            JOptionPane.showMessageDialog(null,"Enter T/F for availability");
            JOptionPane.showMessageDialog(null,"Enter price in the format of 00.00");
        }
    }
}