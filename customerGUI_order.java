import javax.swing.*;
import java.sql.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;

public class customerGUI_order extends JFrame {
    private static final long serialVersionUID = 1L;

    // restaurant name
    private String RESTAURANT_NAME = "Restaurant Name";
    // order page container
    private JPanel ORDER_PAGE;
    // welcome statement
    private JPanel jp_welcome;
    private JLabel lbl_welcome;
    // button holder
    private JPanel button_holder;
    // entree button
    private JButton btn_entree;
    private JLabel lbl_entree;
    // sides button
    private JButton btn_sides;
    private JLabel lbl_sides;
    // desserts button
    private JButton btn_desserts;
    private JLabel lbl_desserts;
    // drinks button
    private JButton btn_drinks;
    private JLabel lbl_drinks;
    // recommendations button
    private JButton btn_rec;
    private JLabel lbl_rec;

    // Food pages
    private JPanel ENTREE_PAGE;
    private JPanel SIDES_PAGE;
    private JPanel DESSERTS_PAGE;
    private JPanel DRINKS_PAGE;
    private JPanel SIGNIN_PAGE;
    private JPanel RECS_PAGE; // TODO

    customerGUI_signin od_signin;
    customerGUI_recs od_recs;
    
    // default initializer
    public customerGUI_order(Connection con, customerGUI parent, int cartid) {
        
        this.ORDER_PAGE = new JPanel();
        this.ORDER_PAGE.setLayout(new BorderLayout());
        this.ORDER_PAGE.setBackground(new Color(238, 179, 28, 255));
        this.ORDER_PAGE.setBorder(new EmptyBorder(20,20,25,25));

        // ------- welcome statement code
        this.jp_welcome = new JPanel();
        this.jp_welcome.setLayout(new FlowLayout());
        this.lbl_welcome = new JLabel(String.format("Welcome to %s\n", RESTAURANT_NAME));
        this.lbl_welcome.setFont(new Font("Verdana", Font.PLAIN, 30));
        this.jp_welcome.setOpaque(false);
        this.jp_welcome.add(this.lbl_welcome);
        this.ORDER_PAGE.add(this.jp_welcome, BorderLayout.PAGE_START);
        // ------- end welcome statement code

        this.button_holder = new JPanel();
        GridLayout gl = new GridLayout(1,5);
        gl.setHgap(15);
        this.button_holder.setLayout(gl);
        this.button_holder.setOpaque(false);

        // ------- entree button code
        this.btn_entree = new JButton();
        this.lbl_entree = new JLabel();
        try {
            Image img = ImageIO.read(getClass().getResource("images/burger.png")).getScaledInstance(100, 
            100, Image.SCALE_SMOOTH);
            this.lbl_entree.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        this.lbl_entree.setText("Entrees");
        this.lbl_entree.setHorizontalTextPosition(JLabel.CENTER);
        this.lbl_entree.setVerticalTextPosition(JLabel.BOTTOM);
        this.btn_entree.setBackground(new Color(255, 247, 178, 255));
        this.btn_entree.add(this.lbl_entree);
        this.btn_entree.setActionCommand("Entrees");
        this.btn_entree.addActionListener(parent);
        this.button_holder.add(this.btn_entree);
        // ------- end entree button code

        // ------- sides button code
        this.btn_sides = new JButton();
        this.lbl_sides = new JLabel();
        try {
            Image img = ImageIO.read(getClass().getResource("images/fries.png")).getScaledInstance(100, 
            100, Image.SCALE_SMOOTH);
            this.lbl_sides.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        this.lbl_sides.setText("Sides");
        this.lbl_sides.setHorizontalTextPosition(JLabel.CENTER);
        this.lbl_sides.setVerticalTextPosition(JLabel.BOTTOM);
        this.btn_sides.add(this.lbl_sides);
        this.btn_sides.setBackground(new Color(255, 247, 178, 255));
        this.btn_sides.setActionCommand("Sides");
        this.btn_sides.addActionListener(parent);
        this.button_holder.add(btn_sides);
        // ------- end sides button code

        // ------- desserts button code
        this.btn_desserts = new JButton();
        this.lbl_desserts = new JLabel();
        try {
            Image img = ImageIO.read(getClass().getResource("images/ice_cream.png")).getScaledInstance(100, 
            100, Image.SCALE_SMOOTH);
            this.lbl_desserts.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        this.lbl_desserts.setText("Desserts");
        this.lbl_desserts.setHorizontalTextPosition(JLabel.CENTER);
        this.lbl_desserts.setVerticalTextPosition(JLabel.BOTTOM);
        this.btn_desserts.add(lbl_desserts);
        this.btn_desserts.setBackground(new Color(255, 247, 178, 255));
        this.btn_desserts.setActionCommand("Desserts");
        this.btn_desserts.addActionListener(parent);
        this.button_holder.add(btn_desserts);
        // ------- end desserts button code

        // ------- drinks button code
        this.btn_drinks = new JButton();
        this.lbl_drinks = new JLabel();
        try {
            Image img = ImageIO.read(getClass().getResource("images/drinks_icon.png")).getScaledInstance(100, 
            100, Image.SCALE_SMOOTH);
            this.lbl_drinks.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        this.lbl_drinks.setText("Drinks");
        this.lbl_drinks.setHorizontalTextPosition(JLabel.CENTER);
        this.lbl_drinks.setVerticalTextPosition(JLabel.BOTTOM);
        this.btn_drinks.add(this.lbl_drinks);
        this.btn_drinks.setBackground(new Color(255, 247, 178, 255));
        this.btn_drinks.setActionCommand("Drinks");
        this.btn_drinks.addActionListener(parent);
        this.button_holder.add(this.btn_drinks);
        // ------- end drinks button code

        // ------- recommendations button code
        this.btn_rec = new JButton();
        this.lbl_rec = new JLabel();
        try {
            Image img = ImageIO.read(getClass().getResource("images/recs_icon.png")).getScaledInstance(100,100, Image.SCALE_SMOOTH);
            this.lbl_rec.setIcon(new ImageIcon(img));
        } catch(Exception ex) {
            System.out.println(ex);
        }
        this.lbl_rec.setText("Recommendations");
        this.lbl_rec.setHorizontalTextPosition(JLabel.CENTER);
        this.lbl_rec.setVerticalTextPosition(JLabel.BOTTOM);
        this.btn_rec.add(this.lbl_rec);
        this.btn_rec.setBackground(new Color(255, 247, 178, 255));
        this.btn_rec.setActionCommand("Recs");
        this.btn_rec.addActionListener(parent);
        this.button_holder.add(this.btn_rec);
        // ------- end recommendations button code

        ORDER_PAGE.add(button_holder, BorderLayout.CENTER);

        customerGUI_orderfood od_entree = new customerGUI_orderfood(con, 1, cartid);
        this.ENTREE_PAGE = od_entree.GET_PANEL();
        customerGUI_orderfood od_sides = new customerGUI_orderfood(con, 2, cartid);
        this.SIDES_PAGE = od_sides.GET_PANEL();
        customerGUI_orderfood od_desserts = new customerGUI_orderfood(con, 3, cartid);
        this.DESSERTS_PAGE = od_desserts.GET_PANEL();
        customerGUI_orderfood od_drinks = new customerGUI_orderfood(con, 4, cartid);
        this.DRINKS_PAGE = od_drinks.GET_PANEL();
        this.od_signin = new customerGUI_signin(con, parent, cartid);
        this.od_recs = new customerGUI_recs(con, parent, cartid);
        //this.RECS_PAGE = od_recs.GET_PANEL();
    }
    public JPanel GET_PANEL(int i) {
        switch(i) {
            case 0: 
                return this.ORDER_PAGE; 
            case 1:
                return this.ENTREE_PAGE; 
            case 2: 
                return this.SIDES_PAGE;
            case 3: 
                return this.DESSERTS_PAGE;
            case 4: 
                return this.DRINKS_PAGE;
            case 5:
                this.SIGNIN_PAGE = this.od_signin.GET_PANEL(5);
                return this.SIGNIN_PAGE;
            case 6:
                this.SIGNIN_PAGE = this.od_signin.GET_PANEL(6);
                return this.SIGNIN_PAGE;
            case 7:
                this.RECS_PAGE = this.od_recs.GET_PANEL();
                return this.RECS_PAGE;
            default: 
                return this.ORDER_PAGE;
        }
    }
}