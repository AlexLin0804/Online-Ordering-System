import javax.swing.*;
import java.sql.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;

public class ManagerGUI_product extends JFrame {
    private static final long serialVersionUID = 1L;

    // PRODUCT page container
    private JPanel PRODUCT_PAGE;
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

    // Food pages
    private JPanel ENTREE_PAGE;
    private JPanel SIDES_PAGE;
    private JPanel DESSERTS_PAGE;
    private JPanel DRINKS_PAGE;
    
    // default initializer
    public ManagerGUI_product(ManagerGUI parent, Connection con) {
        
        this.PRODUCT_PAGE = new JPanel();
        this.PRODUCT_PAGE.setLayout(new BorderLayout());
        
        this.PRODUCT_PAGE.setBorder(new EmptyBorder(20,20,25,25));

        this.button_holder = new JPanel();
        GridLayout gl = new GridLayout(1,4);
        gl.setHgap(15);
        this.button_holder.setLayout(gl);
        this.button_holder.setOpaque(false);

        // ------- entree button code
        this.btn_entree = new JButton();
        this.lbl_entree = new JLabel();
        try {
            Image img = ImageIO.read(getClass().getResource("images/burger.png")).getScaledInstance(150, 
            150, Image.SCALE_SMOOTH);
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
            Image img = ImageIO.read(getClass().getResource("images/fries.png")).getScaledInstance(150, 
            150, Image.SCALE_SMOOTH);
            this.lbl_sides.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        this.lbl_sides.setText("Sides");
        this.lbl_sides.setHorizontalTextPosition(JLabel.CENTER);
        this.lbl_sides.setVerticalTextPosition(JLabel.BOTTOM);
        this.btn_sides.add(this.lbl_sides);
        this.btn_sides.setActionCommand("Sides");
        this.btn_sides.addActionListener(parent);
        this.button_holder.add(btn_sides);
        // ------- end sides button code

        // ------- desserts button code
        this.btn_desserts = new JButton();
        this.lbl_desserts = new JLabel();
        try {
            Image img = ImageIO.read(getClass().getResource("images/ice_cream.png")).getScaledInstance(150, 
            150, Image.SCALE_SMOOTH);
            this.lbl_desserts.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        this.lbl_desserts.setText("Desserts");
        this.lbl_desserts.setHorizontalTextPosition(JLabel.CENTER);
        this.lbl_desserts.setVerticalTextPosition(JLabel.BOTTOM);
        this.btn_desserts.add(lbl_desserts);
        
        this.btn_desserts.setActionCommand("Desserts");
        this.btn_desserts.addActionListener(parent);
        this.button_holder.add(btn_desserts);
        // ------- end desserts button code

        // ------- drinks button code
        this.btn_drinks = new JButton();
        this.lbl_drinks = new JLabel();
        try {
            Image img = ImageIO.read(getClass().getResource("images/drinks_icon.png")).getScaledInstance(150, 
            150, Image.SCALE_SMOOTH);
            this.lbl_drinks.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        this.lbl_drinks.setText("Drinks");
        this.lbl_drinks.setHorizontalTextPosition(JLabel.CENTER);
        this.lbl_drinks.setVerticalTextPosition(JLabel.BOTTOM);
        this.btn_drinks.add(lbl_drinks);
       
        this.btn_drinks.setActionCommand("Drinks");
        this.btn_drinks.addActionListener(parent);
        this.button_holder.add(btn_drinks);
        // ------- end drinks button code

        PRODUCT_PAGE.add(button_holder, BorderLayout.CENTER);

        //previous error was from calling get_panel with drink object, which can only return the drinks page.
        ManagerGUI_product_drink dk = new ManagerGUI_product_drink(con);
        ManagerGUI_product_entree en = new ManagerGUI_product_entree(con);
        ManagerGUI_product_side sd = new ManagerGUI_product_side(con);
        ManagerGUI_product_dessert ds = new ManagerGUI_product_dessert(con);
        this.ENTREE_PAGE = en.GET_PANEL(0);
        this.SIDES_PAGE = sd.GET_PANEL(0);
        this.DESSERTS_PAGE = ds.GET_PANEL(0);
        this.DRINKS_PAGE = dk.GET_PANEL(0);
    }


    public JPanel GET_PANEL(int i) {
        switch(i) {
            case 0: 
                return this.PRODUCT_PAGE; 
            case 1:
                return this.ENTREE_PAGE; 
            case 2: 
                return this.SIDES_PAGE;
            case 3: 
                return this.DESSERTS_PAGE;
            case 4: 
                return this.DRINKS_PAGE;
            default: 
                return this.PRODUCT_PAGE;
        }
    }
}   
