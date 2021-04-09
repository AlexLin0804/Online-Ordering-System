import javax.swing.*;
import java.sql.*;
import java.awt.event.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class customerGUI_signin extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    Connection conn;
    int CART_ID;
    int CUST_ID;

    private JPanel SIGNIN_PAGE;
    private JLabel title;
    private JPanel signin_title;

    private JPanel login_panel;
    private JPanel create_panel;
    // sign in panel
    private JPanel username_panel;
    private JPanel password_panel;
    private JPanel buttons_panel;
    private JTextField txt_username;
    private JTextField txt_password;
    private JButton btn_signin;
    private JButton btn_create;
    // end sign in
    // create account panel
    private JPanel firstname_panel;
    private JTextField txt_firstname;
    private JPanel lastname_panel;
    private JTextField txt_lastname;
    private JPanel newusername_panel;
    private JTextField txt_newusername;
    private JPanel newpassword_panel;
    private JTextField txt_newpassword;
    private JPanel phone_panel;
    private JTextField txt_phone;
    private JPanel email_panel;
    private JTextField txt_email;
    private JButton btn_create_account;
    private JButton btn_back_to_signin;
    // end create account panel

    customerGUI parent;

    private void populate_signin() {
        this.login_panel = new JPanel();
        GridLayout gl = new GridLayout(3,1);
        gl.setVgap(20);
        this.login_panel.setLayout(gl);
        this.login_panel.setBackground(new Color(255, 247, 178, 255));
        this.login_panel.setBorder(new EmptyBorder(25,100,25,100));
        // ####### password panel
        this.password_panel = new JPanel(new GridLayout(2,1));
        this.password_panel.setOpaque(false);
        JLabel lbl_password = new JLabel("password");
        this.txt_password = new JTextField();
        this.password_panel.add(lbl_password);
        this.password_panel.add(this.txt_password);
        this.password_panel.setBorder(new EmptyBorder(30,20,30,20));
        // ####### username panel
        this.username_panel = new JPanel(new GridLayout(2,1));
        this.username_panel.setOpaque(false);
        this.username_panel.setBorder(new EmptyBorder(30,20,30,20));
        JLabel lbl_username = new JLabel("username");
        this.txt_username = new JTextField();
        this.username_panel.add(lbl_username);
        this.username_panel.add(this.txt_username);
        // ####### buttons panel
        this.buttons_panel = new JPanel(new GridLayout(3,1));
        this.buttons_panel.setOpaque(false);
        this.buttons_panel.setBorder(new EmptyBorder(10,100,10,100));
        // signin button
        this.btn_signin = new JButton("Sign In");
        //this.btn_signin.setContentAreaFilled(false);
        this.btn_signin.setBorderPainted(false);
        this.btn_signin.setForeground(Color.WHITE);
        this.btn_signin.setBackground(new Color(102, 204, 0, 255));
        this.btn_signin.setActionCommand("signin");
        this.btn_signin.addActionListener(this);
        // or create an account
        JLabel lbl_or = new JLabel("or create your account !", JLabel.CENTER);
        // create account button
        this.btn_create = new JButton("Create Account");
        //this.btn_create.setContentAreaFilled(false);
        this.btn_create.setBorderPainted(false);
        this.btn_create.setForeground(Color.WHITE);
        this.btn_create.setBackground(new Color(159, 27, 27, 255));
        this.btn_create.setActionCommand("create");
        this.btn_create.addActionListener(this.parent);
        this.buttons_panel.add(this.btn_signin);
        this.buttons_panel.add(lbl_or);
        this.buttons_panel.add(this.btn_create);
        // ####### end buttons panel
        this.login_panel.add(this.username_panel);
        this.login_panel.add(this.password_panel);
        this.login_panel.add(this.buttons_panel);
        // ###### end login panel
    }

    private void populate_create() {
        this.create_panel = new JPanel();
        GridLayout gl = new GridLayout(5,2);
        gl.setVgap(20);
        gl.setHgap(20);
        this.create_panel.setLayout(gl);
        this.create_panel.setBackground(new Color(255, 247, 178, 255));
        this.create_panel.setBorder(new EmptyBorder(25,30,25,30));
        // required panel
        JLabel lbl_required = new JLabel("* indicates required field");
        // firstname panel
        this.firstname_panel = new JPanel(new GridLayout(2,1));
        this.firstname_panel.setOpaque(false);
        JLabel lbl_firstname = new JLabel("First Name *");
        this.txt_firstname = new JTextField();
        this.firstname_panel.add(lbl_firstname);
        this.firstname_panel.add(this.txt_firstname);
        //this.firstname_panel.setBorder(new EmptyBorder(10,20,10,20));
        // lastname panel
        this.lastname_panel = new JPanel(new GridLayout(2,1));
        this.lastname_panel.setOpaque(false);
        JLabel lbl_lastname = new JLabel("Last Name *");
        this.txt_lastname = new JTextField();
        this.lastname_panel.add(lbl_lastname);
        this.lastname_panel.add(this.txt_lastname);
        //this.lastname_panel.setBorder(new EmptyBorder(10,20,10,20));
        // ####### new password panel
        this.newpassword_panel = new JPanel(new GridLayout(2,1));
        this.newpassword_panel.setOpaque(false);
        JLabel lbl_newpassword = new JLabel("password *");
        this.txt_newpassword = new JTextField();
        this.newpassword_panel.add(lbl_newpassword);
        this.newpassword_panel.add(this.txt_newpassword);
        //this.newpassword_panel.setBorder(new EmptyBorder(10,20,10,20));
        // ####### new username panel
        this.newusername_panel = new JPanel(new GridLayout(2,1));
        this.newusername_panel.setOpaque(false);
        //this.newusername_panel.setBorder(new EmptyBorder(10,20,10,20));
        JLabel lbl_newusername = new JLabel("username *");
        this.txt_newusername = new JTextField();
        this.newusername_panel.add(lbl_newusername);
        this.newusername_panel.add(this.txt_newusername);
        // email panel
        this.email_panel = new JPanel(new GridLayout(2,1));
        this.email_panel.setOpaque(false);
        JLabel lbl_email = new JLabel("email address");
        this.txt_email = new JTextField();
        this.email_panel.add(lbl_email);
        this.email_panel.add(this.txt_email);
        //this.email_panel.setBorder(new EmptyBorder(10,20,10,20));
        // phone panel
        this.phone_panel = new JPanel(new GridLayout(2,1));
        this.phone_panel.setOpaque(false);
        JLabel lbl_phone = new JLabel("phone number");
        this.txt_phone = new JTextField();
        this.phone_panel.add(lbl_phone);
        this.phone_panel.add(this.txt_phone);
        this.btn_back_to_signin = new JButton("Back to Sign In");
        this.btn_back_to_signin.setBorderPainted(false);
        this.btn_back_to_signin.setForeground(Color.WHITE);
        this.btn_back_to_signin.setBackground(new Color(159, 27, 27, 255));
        this.btn_back_to_signin.setActionCommand("back_signin");
        this.btn_back_to_signin.addActionListener(this.parent);
        this.btn_back_to_signin.setBorder(new EmptyBorder(25,25,25,25));
        this.btn_create_account = new JButton("Sign Up");
        this.btn_create_account.setBorderPainted(false);
        this.btn_create_account.setForeground(Color.WHITE);
        this.btn_create_account.setBackground(new Color(102, 204, 0, 255));;
        this.btn_create_account.addActionListener(this);
        this.btn_create_account.setBorder(new EmptyBorder(25,25,25,25));
        // add to login panel
        this.create_panel.add(lbl_required);
        this.create_panel.add(new JLabel(" "));
        this.create_panel.add(this.firstname_panel);
        this.create_panel.add(this.lastname_panel);
        this.create_panel.add(this.newusername_panel);
        this.create_panel.add(this.newpassword_panel);
        this.create_panel.add(this.email_panel);
        this.create_panel.add(this.phone_panel);
        this.create_panel.add(this.btn_back_to_signin);
        this.create_panel.add(this.btn_create_account);
    }

    public customerGUI_signin(Connection con, customerGUI parent, int cartid) {
        this.conn = con;
        this.parent = parent;
        this.CART_ID = cartid;
        // ####### title panel
        this.SIGNIN_PAGE = new JPanel(new BorderLayout());
        this.SIGNIN_PAGE.setBackground(new Color(238, 179, 28, 255));
        this.SIGNIN_PAGE.setBorder(new EmptyBorder(25,120,25,120));
        this.title = new JLabel("Sign In for the Best Experience !");
        this.signin_title = new JPanel(new FlowLayout());
        this.signin_title.setOpaque(false);
        this.signin_title.add(this.title);
        //this.SIGNIN_PAGE.add(this.signin_title, BorderLayout.PAGE_START);
        // ####### signin panel
        // ####### end username panel
        populate_signin();
        populate_create();
    }
    
    public JPanel GET_PANEL(int i) {
        if (i == 5) {
            this.SIGNIN_PAGE.setVisible(false);
            this.SIGNIN_PAGE.removeAll();
            this.SIGNIN_PAGE.add(this.signin_title, BorderLayout.PAGE_START);
            this.SIGNIN_PAGE.add(this.login_panel, BorderLayout.CENTER);
            this.SIGNIN_PAGE.setVisible(true);
        }
        else if (i == 6) {
            this.SIGNIN_PAGE.setVisible(false);
            this.SIGNIN_PAGE.removeAll();
            this.SIGNIN_PAGE.add(this.signin_title, BorderLayout.PAGE_START);
            this.SIGNIN_PAGE.add(this.create_panel, BorderLayout.CENTER);
            this.SIGNIN_PAGE.setVisible(true);
        }
        return this.SIGNIN_PAGE;
    }

    private void sign_in() {
        String username = this.txt_username.getText().trim();
        String password = this.txt_password.getText().trim();
        System.out.println("From text field username: ");
        System.out.println(username);
        try{
            Statement stmt = this.conn.createStatement();
            String sqlStatement = String.format("SELECT customerid, firstname, lastname FROM Customers WHERE trim(Customers.username)=\'%s\' AND trim(Customers.password)=\'%s\'", username, password);
            ResultSet result = stmt.executeQuery(sqlStatement);
            this.CUST_ID = -1;
            String name = "";
            while(result.next()) {
               this.CUST_ID = result.getInt("customerid");
               name += result.getString("firstname");
               name += " ";
               name += result.getString("lastname");
            }
            System.out.println(String.format("Customerid: %d", this.CUST_ID));
            if (this.CUST_ID == -1) {
                JOptionPane.showMessageDialog(null, "Incorrect Username or Password", "Sign In Incorrect", JOptionPane.INFORMATION_MESSAGE);
                return;
            } else {
                // update the cart to have the customer id
                try {
                    Statement stmt2 = this.conn.createStatement();
                    String sqlStatement2 = String.format("UPDATE Carts SET customerid=\'%d\' WHERE cartid=\'%d\'", this.CUST_ID, this.CART_ID);
                    stmt2.executeUpdate(sqlStatement2);
                    JOptionPane.showMessageDialog(null, String.format("Welcome %s !", name), "Sign In Successful", JOptionPane.INFORMATION_MESSAGE);
                    this.txt_username.setText("");
                    this.txt_password.setText("");
                } catch (Exception e) {
                    System.out.println("Error adding customer id to cart");
                }
            }
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Sorry we'll get the system up and running asap!", "Sign In Error", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Error signing in");
        }
    }

    private void create_account() {
        String username = this.txt_newusername.getText().trim();
        String password = this.txt_newpassword.getText().trim();
        String firstname = this.txt_firstname.getText().trim();
        String lastname = this.txt_lastname.getText().trim();
        String email = this.txt_email.getText().trim();
        String phone = this.txt_phone.getText().trim();
        if (phone.length() != 10 && phone.length() != 0) {
            JOptionPane.showMessageDialog(null, "You entered an invalid phone number!\n(user 10 numbers with no spaces)", "Account Creation Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        // get last customerid
        try {
            Statement stmt = this.conn.createStatement();
            String sqlStatement = "SELECT max(customerid) FROM Customers";
            ResultSet result = stmt.executeQuery(sqlStatement);
            this.CUST_ID = -1;
            while(result.next()) {
                this.CUST_ID = result.getInt("max") + 1;
            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Sorry we'll get the system up and running asap!", "Account Creation Error", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Error getting max customerid");
            return;
        }
        // check if username is taken
        try {
            Statement stmt = this.conn.createStatement();
            String sqlStatement = String.format("SELECT Count(*) FROM Customers WHERE trim(Customers.username)=\'%s\'", username);
            ResultSet result = stmt.executeQuery(sqlStatement);
            int count = 0;
            while(result.next()) {
                count = result.getInt("count");
            }
            if (count != 0) {
                JOptionPane.showMessageDialog(null, "Unfortunately, this username is taken, please choose another.", "Account Creation Error", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Sorry we'll get the system up and running asap!", "Account Creation Error", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Error checking if the username is taken");
        }
        // add customer entry
        try {
            Statement stmt = this.conn.createStatement();
            String sqlStatement = String.format("INSERT INTO Customers (customerid, lastname, firstname, username, password, email, phone) VALUES (%d, \'%s\', \'%s\', \'%s\', \'%s\', \'%s\', \'%s\');", this.CUST_ID, lastname, firstname, username, password, email, phone);
            stmt.executeUpdate(sqlStatement);
            JOptionPane.showMessageDialog(null, String.format("Welcome %s %s!", firstname, lastname), "Account Creation Successful", JOptionPane.INFORMATION_MESSAGE);
            this.txt_email.setText("");
            this.txt_firstname.setText("");
            this.txt_lastname.setText("");
            this.txt_newusername.setText("");
            this.txt_newpassword.setText("");
            this.txt_phone.setText("");
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Sorry we'll get the system up and running asap!", "Account Creation Error", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("Error adding customer entry");
        }
        // assign customerid to cart
        try {
            Statement stmt2 = this.conn.createStatement();
            String sqlStatement2 = String.format("UPDATE Carts SET customerid=\'%d\' WHERE cartid=\'%d\'", this.CUST_ID, this.CART_ID);
            stmt2.executeUpdate(sqlStatement2);
        } catch (Exception e) {
            System.out.println("Error adding customer id to cart");
        }
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn_signin) { // TODO
            sign_in();
        } else if (e.getSource() == btn_create_account) { // TODO
            create_account();
        }
    }

}
