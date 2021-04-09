import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*; 
import java.awt.event.*;
import java.io.FileNotFoundException; 
import java.sql.*;

/*

Manager GUI - 4 pages
1. Dashboard
2. List of Products - Food type - Product
3. Menu
4. Account

*/
public class ManagerGUI extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    // sql connection
    private Connection conn;


    // order page containers
    private Container MANAGER_GUI;
    // nav bar 
    private JPanel NAV_BAR;
    private JButton btn_dashboard;
    private JButton btn_product;
    private JButton btn_account;
    // pages
    private JPanel DASHBOARD_PAGE;
    private JPanel PRODUCT_PAGE;
    private JPanel ACCOUNT_PAGE;

    private JPanel ENTREE_PAGE;
    private JPanel SIDES_PAGE;
    private JPanel DESSERTS_PAGE;
    private JPanel DRINKS_PAGE;

    

    // helper functions
    void clear_button(JButton jb) {
        jb.setOpaque(false);
        jb.setContentAreaFilled(false);
        jb.setBorderPainted(false);
        jb.setForeground(Color.WHITE);
    }

    // default initializer
    public ManagerGUI(Connection con) {

        this.conn = con;
        this.MANAGER_GUI = new Container();
        setTitle("Manager Portal");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(300, 90, 900, 600); 
        this.MANAGER_GUI = getContentPane();
        MANAGER_GUI.setLayout(new BorderLayout());

        // -------- nav bar code
        this.NAV_BAR = new JPanel();
        this.NAV_BAR.setLayout(new FlowLayout());
        //this.NAV_BAR.setBorder(new EmptyBorder(30,30,30,30));
        this.NAV_BAR.setBackground(new Color(135,206,235, 255));

        this.btn_dashboard = new JButton("Dashboard");
        this.btn_dashboard.setPreferredSize(new Dimension(150, 40));
        clear_button(this.btn_dashboard);
        this.btn_dashboard.addActionListener(this);

        this.btn_product = new JButton("Product");
        this.btn_product.setPreferredSize(new Dimension(100, 40));
        clear_button(this.btn_product);
        this.btn_product.addActionListener(this);
        

        this.btn_account = new JButton("Trending");
        this.btn_account.setPreferredSize(new Dimension(150, 40));
        clear_button(this.btn_account);
        this.btn_account.addActionListener(this);

        this.NAV_BAR.setPreferredSize(new Dimension(200, 50)); // width 100, height 30
        
        this.NAV_BAR.add(this.btn_dashboard); 
        this.NAV_BAR.add(this.btn_product); 
        this.NAV_BAR.add(this.btn_account); 

        MANAGER_GUI.add(this.NAV_BAR, BorderLayout.PAGE_START); // add nav bar to manager gui
        // ---------- end nav bar code

        ManagerGUI_product po = new ManagerGUI_product(this,this.conn);
        this.PRODUCT_PAGE = po.GET_PANEL(0);
        MANAGER_GUI.add(this.PRODUCT_PAGE, BorderLayout.CENTER);
        this.ENTREE_PAGE = po.GET_PANEL(1);
        this.SIDES_PAGE = po.GET_PANEL(2);
        this.DESSERTS_PAGE = po.GET_PANEL(3);
        this.DRINKS_PAGE = po.GET_PANEL(4);

        
        ManagerGUI_dashboard md = new ManagerGUI_dashboard(this.conn);
        this.DASHBOARD_PAGE = md.GET_PANEL();
        MANAGER_GUI.add(this.DASHBOARD_PAGE, BorderLayout.CENTER); 


        ManagerGUI_account ma = new ManagerGUI_account(this.conn);
        this.ACCOUNT_PAGE = ma.GET_PANEL();

        // show the whole page
        setVisible(true);
    }
    
    // ActionListener inherited function
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btn_dashboard){
            setVisible(false);
            //this.MANAGER_GUI.remove(this.PRODUCT_PAGE);
            //this.MANAGER_GUI.remove(this.ACCOUNT_PAGE);

            //FIXED: Issue with loading menu pages when backtracking was because removeAll() was not called
            this.MANAGER_GUI.removeAll();
            this.MANAGER_GUI.setLayout(new BorderLayout());
            this.MANAGER_GUI.add(this.NAV_BAR, BorderLayout.PAGE_START);
            this.MANAGER_GUI.add(this.DASHBOARD_PAGE, BorderLayout.CENTER);
            setVisible(true);
        }
        else if (e.getSource() == this.btn_product){
            setVisible(false);
            //this.MANAGER_GUI.remove(this.DASHBOARD_PAGE);
            //this.MANAGER_GUI.remove(this.ACCOUNT_PAGE);
            this.MANAGER_GUI.removeAll();
            this.MANAGER_GUI.setLayout(new BorderLayout());
            this.MANAGER_GUI.add(this.NAV_BAR, BorderLayout.PAGE_START);
            this.MANAGER_GUI.add(this.PRODUCT_PAGE, BorderLayout.CENTER);
            setVisible(true);
        }
        else if (e.getSource() == this.btn_account){
            setVisible(false);
            //this.MANAGER_GUI.remove(this.DASHBOARD_PAGE);
            //this.MANAGER_GUI.remove(this.PRODUCT_PAGE);
            this.MANAGER_GUI.removeAll();
            this.MANAGER_GUI.setLayout(new BorderLayout());
            this.MANAGER_GUI.add(this.NAV_BAR, BorderLayout.PAGE_START);
            this.MANAGER_GUI.add(this.ACCOUNT_PAGE, BorderLayout.CENTER);
            setVisible(true);
        }
        else if (e.getActionCommand() == "Entrees") {   
            setVisible(false);
            this.MANAGER_GUI.removeAll();
            this.MANAGER_GUI.setLayout(new BorderLayout());
            this.MANAGER_GUI.add(this.NAV_BAR, BorderLayout.PAGE_START);
            this.MANAGER_GUI.add(this.ENTREE_PAGE, BorderLayout.CENTER); 
            setVisible(true);
        } 
        else if (e.getActionCommand() == "Sides") {
           
            setVisible(false);
            this.MANAGER_GUI.removeAll();
            this.MANAGER_GUI.setLayout(new BorderLayout());
            this.MANAGER_GUI.add(this.NAV_BAR, BorderLayout.PAGE_START);
            this.MANAGER_GUI.add(this.SIDES_PAGE, BorderLayout.CENTER);
           
            setVisible(true);
        } 
        else if (e.getActionCommand() == "Desserts") {
            setVisible(false);
            this.MANAGER_GUI.removeAll();
            this.MANAGER_GUI.setLayout(new BorderLayout());
            this.MANAGER_GUI.add(this.NAV_BAR, BorderLayout.PAGE_START);
            this.MANAGER_GUI.add(this.DESSERTS_PAGE, BorderLayout.CENTER);
          
            setVisible(true);
        } 
        else if (e.getActionCommand() == "Drinks") {
            //System.out.println("Drinks button pressed");
            setVisible(false);
            this.MANAGER_GUI.removeAll();
            this.MANAGER_GUI.setLayout(new BorderLayout());
            this.MANAGER_GUI.add(this.NAV_BAR, BorderLayout.PAGE_START);
            this.MANAGER_GUI.add(this.DRINKS_PAGE, BorderLayout.CENTER);
            setVisible(true);
        }
    }
}