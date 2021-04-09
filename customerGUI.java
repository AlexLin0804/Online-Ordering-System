import javax.swing.*;
import java.sql.*;
import javax.swing.border.EmptyBorder;
import java.awt.*; 
import java.awt.event.*;
import java.io.FileNotFoundException; 

/*
 * The customer GUI class implements 4 pages for the customer
 * - Order Online
 * - Order Online > Food Type
 * - Cart
 */
public class customerGUI extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    public final int CART_ID;
    customerGUI_cart cart;
    // order page containers
    private Container CUSTOMER_GUI;
    // nav bar 
    private JPanel NAV_BAR;
    private JButton btn_order;
    private JButton btn_cart;
    private JButton btn_signin;
    // pages
    private JPanel ORDER_PAGE;
    private JPanel CART_PAGE;
    private JPanel SIGNIN_PAGE;
    private JPanel ENTREE_PAGE;
    private JPanel SIDES_PAGE;
    private JPanel DESSERTS_PAGE;
    private JPanel DRINKS_PAGE;
    private JPanel RECS_PAGE;

    // sql connection
    private Connection conn;

    private customerGUI_order co;

    // helper functions
    void clear_button(JButton jb) {
        jb.setOpaque(false);
        jb.setContentAreaFilled(false);
        jb.setBorderPainted(false);
        jb.setForeground(Color.WHITE);
    }

    // default initializer
    public customerGUI(Connection con, int cartid) {
        this.conn = con;
        this.CART_ID = cartid;

        this.CUSTOMER_GUI = new Container();
        setTitle("Customer App");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	    setBounds(300, 90, 900, 600); 
        this.CUSTOMER_GUI = getContentPane();
		CUSTOMER_GUI.setLayout(new BorderLayout());

        // -------- nav bar code
        this.NAV_BAR = new JPanel();
        this.NAV_BAR.setLayout(new FlowLayout());
        //this.NAV_BAR.setBorder(new EmptyBorder(30,30,30,30));
        this.NAV_BAR.setBackground(new Color(159, 27, 27, 255));
        this.btn_order = new JButton("ORDER ONLINE");
        this.btn_order.setPreferredSize(new Dimension(150, 40));
        clear_button(this.btn_order);
        this.btn_order.addActionListener(this);
        this.btn_cart = new JButton("My Cart");
        clear_button(this.btn_cart);
        this.btn_cart.setPreferredSize(new Dimension(100, 40));
        this.btn_cart.addActionListener(this);
        this.btn_signin = new JButton("Sign In");
        this.btn_signin.setPreferredSize(new Dimension(250, 40));
        clear_button(this.btn_signin);
        this.btn_signin.addActionListener(this);
        this.NAV_BAR.setPreferredSize(new Dimension(200, 50)); // width 100, height 30
        this.NAV_BAR.add(this.btn_order); // add order button to nav bar
        this.NAV_BAR.add(this.btn_cart); // add cart button to nav bar
        this.NAV_BAR.add(this.btn_signin); // add sign in button to nav bar
        CUSTOMER_GUI.add(this.NAV_BAR, BorderLayout.PAGE_START); // add nav bar to customer gui
        // ---------- end nav bar code

        // ---------- Order Page code
        this.co = new customerGUI_order(this.conn, this, this.CART_ID);
        this.ORDER_PAGE = this.co.GET_PANEL(0);
        CUSTOMER_GUI.add(this.ORDER_PAGE, BorderLayout.CENTER);
        this.ENTREE_PAGE = this.co.GET_PANEL(1);
        this.SIDES_PAGE = this.co.GET_PANEL(2);
        this.DESSERTS_PAGE = this.co.GET_PANEL(3);
        this.DRINKS_PAGE = this.co.GET_PANEL(4);
        this.RECS_PAGE = this.co.GET_PANEL(7);
        // ---------- end order page code

        // ---------- Cart Page code
        cart = new customerGUI_cart(this.conn, this.CART_ID);
        cart.update_cart();
        this.CART_PAGE = cart.GET_PANEL();
        // ---------- end cart page code

        // show the whole order page
        setVisible(true);
    }
    
    // ActionListener inherited function
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btn_order){
            setVisible(false);
            this.CUSTOMER_GUI.removeAll();
            this.CUSTOMER_GUI.setLayout(new BorderLayout());
            this.CUSTOMER_GUI.add(this.NAV_BAR, BorderLayout.PAGE_START);
            this.CUSTOMER_GUI.add(this.ORDER_PAGE, BorderLayout.CENTER);
            System.out.println("btn_order clicked");
            setVisible(true);
        } else if (e.getSource() == this.btn_cart){
            setVisible(false);
            this.CUSTOMER_GUI.removeAll();
            this.CUSTOMER_GUI.setLayout(new BorderLayout());
            this.CUSTOMER_GUI.add(this.NAV_BAR, BorderLayout.PAGE_START);
            this.CUSTOMER_GUI.add(this.CART_PAGE, BorderLayout.CENTER);
            System.out.println("btn_cart clicked");
            cart.update_cart();
            setVisible(true);
        } else if (e.getSource() == this.btn_signin) {
            setVisible(false);
            this.CUSTOMER_GUI.removeAll();
            this.CUSTOMER_GUI.setLayout(new BorderLayout());
            this.SIGNIN_PAGE = this.co.GET_PANEL(5);
            this.CUSTOMER_GUI.add(this.NAV_BAR, BorderLayout.PAGE_START);
            this.CUSTOMER_GUI.add(this.SIGNIN_PAGE, BorderLayout.CENTER);
            setVisible(true);
        } else if (e.getActionCommand() == "Entrees") {
            System.out.println("Entrees button pressed");
            setVisible(false);
            this.CUSTOMER_GUI.removeAll();
            this.CUSTOMER_GUI.setLayout(new BorderLayout());
            this.CUSTOMER_GUI.add(this.NAV_BAR, BorderLayout.PAGE_START);
            this.CUSTOMER_GUI.add(this.ENTREE_PAGE, BorderLayout.CENTER);
            System.out.println("btn_entrees clicked");
	        cart.update_cart();
            setVisible(true);
        } else if (e.getActionCommand() == "Sides") {
            System.out.println("Sides button pressed");
            setVisible(false);
            this.CUSTOMER_GUI.removeAll();
            this.CUSTOMER_GUI.setLayout(new BorderLayout());
            this.CUSTOMER_GUI.add(this.NAV_BAR, BorderLayout.PAGE_START);
            this.CUSTOMER_GUI.add(this.SIDES_PAGE, BorderLayout.CENTER);
            System.out.println("btn_sides clicked");
	        cart.update_cart();
            setVisible(true);
        } else if (e.getActionCommand() == "Desserts") {
            System.out.println("Desserts button pressed");
            setVisible(false);
            this.CUSTOMER_GUI.removeAll();
            this.CUSTOMER_GUI.setLayout(new BorderLayout());
            this.CUSTOMER_GUI.add(this.NAV_BAR, BorderLayout.PAGE_START);
            this.CUSTOMER_GUI.add(this.DESSERTS_PAGE, BorderLayout.CENTER);
            System.out.println("btn_desserts clicked");
	        cart.update_cart();
            setVisible(true);
        } else if (e.getActionCommand() == "Drinks") {
            System.out.println("Drinks button pressed");
            setVisible(false);
            this.CUSTOMER_GUI.removeAll();
            this.CUSTOMER_GUI.setLayout(new BorderLayout());
            this.CUSTOMER_GUI.add(this.NAV_BAR, BorderLayout.PAGE_START);
            this.CUSTOMER_GUI.add(this.DRINKS_PAGE, BorderLayout.CENTER);
            System.out.println("btn_drinks clicked");
  	        cart.update_cart();
            setVisible(true);
        } else if (e.getActionCommand() == "Recs") {
            System.out.println("Recommendations button pressed");
            setVisible(false);
            this.CUSTOMER_GUI.removeAll();
            this.CUSTOMER_GUI.setLayout(new BorderLayout());
            this.RECS_PAGE = this.co.GET_PANEL(7);
            this.CUSTOMER_GUI.add(this.NAV_BAR, BorderLayout.PAGE_START);
            this.CUSTOMER_GUI.add(this.RECS_PAGE, BorderLayout.CENTER);
            System.out.println("btn_rec clicked");
  	        cart.update_cart();
            setVisible(true);
        } else if (e.getActionCommand() == "back_signin") {// TODO
            setVisible(false);
            this.CUSTOMER_GUI.removeAll();
            this.CUSTOMER_GUI.setLayout(new BorderLayout());
            this.SIGNIN_PAGE = this.co.GET_PANEL(5);
            this.CUSTOMER_GUI.add(this.NAV_BAR, BorderLayout.PAGE_START);
            this.CUSTOMER_GUI.add(this.SIGNIN_PAGE, BorderLayout.CENTER);
            setVisible(true);
        } else if (e.getActionCommand() == "create") { // TODO
            setVisible(false);
            this.CUSTOMER_GUI.removeAll();
            this.CUSTOMER_GUI.setLayout(new BorderLayout());
            this.SIGNIN_PAGE = this.co.GET_PANEL(6);
            this.CUSTOMER_GUI.add(this.NAV_BAR, BorderLayout.PAGE_START);
            this.CUSTOMER_GUI.add(this.SIGNIN_PAGE, BorderLayout.CENTER);
            setVisible(true);
        } else if (e.getActionCommand() == "back_to_orders") {
            setVisible(false);
            this.CUSTOMER_GUI.removeAll();
            this.CUSTOMER_GUI.setLayout(new BorderLayout());
            this.CUSTOMER_GUI.add(this.NAV_BAR, BorderLayout.PAGE_START);
            this.CUSTOMER_GUI.add(this.ORDER_PAGE, BorderLayout.CENTER);
            System.out.println("back_to_orders clicked");
            setVisible(true);
        }
    }
}
