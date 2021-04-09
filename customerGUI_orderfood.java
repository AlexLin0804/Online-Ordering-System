import javax.swing.*;
import java.sql.*;
import java.awt.*; 
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.border.EmptyBorder;

public class customerGUI_orderfood extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    //food page containers
    private JPanel FOOD_PAGE;

    // food title
    private JPanel food_title;
    private JLabel title;
    private String food_type;
    private int food_type_int;

    // food body
    private JPanel food_body;
    private JPanel p1;
    private JPanel p2;
    private JPanel p3;
    private JPanel p4;
    private JPanel p5;
    private JPanel p6;
    private JPanel p7;

    private JButton e1;
    private JButton e2;
    private JButton e3;
    private JButton e4;
    private JButton e5;
    private JButton e6;
    private JButton e7;

    private ArrayList<JPanel> buttons;

    // cart holders
    private int CART_ID;
    private float CART_TOTAL;
    private ArrayList<Integer> Food;

    // sql connection
    private Connection conn;

    // helper functions
    private void update_food_body(String str) {
        this.food_body.removeAll();
        String str_getProducts = String.format("SELECT * FROM Products INNER JOIN Menus ON Menus.id=Products.productid WHERE Products.producttype = \'%s\' AND Menus.availability = '1'", str);
        try {
            Statement stmt = this.conn.createStatement();
            ResultSet result = stmt.executeQuery(str_getProducts);
            
            int i = 0;
            String food_desc = "";
            String food_price = "";
            while (result.next()){
                food_desc += result.getString("productname");
                food_price += result.getString("price");
                JLabel fd = new JLabel(food_desc);
                fd.setHorizontalAlignment(JLabel.CENTER);
                JLabel fp = new JLabel(food_price);
                fp.setHorizontalAlignment(JLabel.CENTER);
                buttons.get(i).add(fd);
                buttons.get(i).add(fp);
                buttons.get(i).setPreferredSize(new Dimension(150,50));
                this.food_body.add(this.buttons.get(i));
                i++;
                food_desc = "";
                food_price = "";
            }
            
        } catch(Exception e){
            System.out.println("Error accessing Database: SELECT * FROM PRODUCTS WHERE producttype = 'Entree'.");
        }
        this.FOOD_PAGE.add(this.food_body, BorderLayout.CENTER);
    }
    // sql get price
    private float get_price(int productid) {
        String str_getPrice = String.format("SELECT price FROM Products WHERE productid = \'%d\'", productid);
        try {
            Statement stmt = this.conn.createStatement();
            ResultSet result = stmt.executeQuery(str_getPrice);
            Float pr = 0.0f;
            while (result.next()){
                pr = result.getFloat("price");
            }
            return pr;
        } catch(Exception e){
            System.out.println(String.format("Error accessing Database: SELECT price FROM Products WHERE productid = \'%d\'", productid));
        }
        return -1.0f;
    }
    // sql add to cart
    private void add_to_cart(int cartid, int prodid) {
        System.out.println(food_type_int + ":");
	String input = String.format("UPDATE Carts Set food = array_append(food, \'%d\') WHERE cartid = \'%d\'", prodid, cartid);
        try {
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(input);
            System.out.println(String.format("Added product: %d to cartid: %d", prodid, cartid));
        } catch(Exception e){
            System.out.println(String.format("Error accessing Database: UPDATE Carts Set food = food || \'%d\' WHERE cartid = \'%d\'", prodid, cartid));
        }
    }

    private void make_buttons(JButton jb, JPanel jp) {
        jb.setForeground(Color.WHITE);
        jb.setBackground(new Color(159, 27, 27, 255));
        jb.addActionListener(this);
        jp.setBackground(new Color(255, 247, 178, 255));
        jp.setBorder(new EmptyBorder(25,25,25,25));
        jp.add(jb);
    }
    
    //INSERT INTO Carts
    //VALUES ('2', NULL, '3.23', '{1,2,3}');

    //default constructor
    public customerGUI_orderfood(Connection con, int i, int cartid) {
        this.conn = con;
        this.CART_ID = cartid;
        this.food_type_int = i;
        this.Food = new ArrayList<Integer>();

        //initializing JPanel and Layout
        this.FOOD_PAGE = new JPanel(new BorderLayout());
        this.FOOD_PAGE.setBackground(new Color(238, 179, 28, 255));
        this.FOOD_PAGE.setBorder(new EmptyBorder(25,25,25,25));
        this.title = new JLabel(String.format("Choose your %s", this.food_type));
        this.food_title = new JPanel(new FlowLayout());
        this.food_title.setOpaque(false);
        this.food_title.add(this.title);
        this.FOOD_PAGE.add(this.food_title, BorderLayout.PAGE_START);

        GridLayout gl = new GridLayout(4,3);
        gl.setHgap(20); gl.setVgap(20);
        this.food_body = new JPanel(gl);
        this.food_body.setBorder(new EmptyBorder(25,25,25,25));
        this.food_body.setOpaque(false);
        //creating buttons for food items
        this.buttons = new ArrayList<JPanel>();
        this.e1 = new JButton("Cart +");
        this.p1 = new JPanel(new GridLayout(1,3));
        make_buttons(e1, p1);
        this.buttons.add(p1);
        this.e2 = new JButton("Cart +");
        this.p2 = new JPanel(new FlowLayout());
        make_buttons(e2, p2);
        this.buttons.add(p2);
        this.e3 = new JButton("Cart +");
        this.p3 = new JPanel(new FlowLayout());
        make_buttons(e3, p3);
        this.buttons.add(p3);
        this.e4 = new JButton("Cart +");
        this.p4 = new JPanel(new FlowLayout());
        make_buttons(e4, p4);
        this.buttons.add(p4);
        this.e5 = new JButton("Cart +");
        this.p5 = new JPanel(new FlowLayout());
        make_buttons(e5, p5);
        this.buttons.add(p5);
        this.e6 = new JButton("Cart +");
        this.p6 = new JPanel(new FlowLayout());
        make_buttons(e6, p6);
        this.buttons.add(p6);
        this.e7 = new JButton("Cart +");
        this.p7 = new JPanel(new FlowLayout());
        make_buttons(e7, p7);
        this.buttons.add(p7);

        switch(i){
            case 1:
                this.food_type = "Entrees";
                this.food_title.remove(this.title);
                this.title = new JLabel(String.format("Choose your %s", this.food_type));
                this.food_title.add(title);
                update_food_body("Entree");
                break;
            case 2:
                this.food_type = "Sides";
                this.food_title.remove(this.title);
                this.title = new JLabel(String.format("Choose your %s", this.food_type));
                this.food_title.add(title);
                update_food_body("Side");
                break;
            case 3:
                this.food_type = "Desserts";
                this.food_title.remove(this.title);
                this.title = new JLabel(String.format("Choose your %s", this.food_type));
                this.food_title.add(title);
                update_food_body("Dessert");
                break;
            case 4:
                this.food_type = "Drinks";
                this.food_title.remove(this.title);
                this.title = new JLabel(String.format("Choose your %s", this.food_type));
                this.food_title.add(title);
                update_food_body("Drink");
                break;
            default: 
        }
    }
    public JPanel GET_PANEL() {
        return this.FOOD_PAGE;
    }

    //inherited ActionListener method
    // adds to the cart
    public void actionPerformed(ActionEvent e) {
	if (e.getSource() == this.e1) {
            if (this.food_type_int == 1){
                float pr = get_price(3);
                if (pr != -1.0f) {
                    CART_TOTAL += pr;
                    add_to_cart(this.CART_ID, 3);
                    this.Food.add(3);
                } 
            } else if (this.food_type_int == 2) {
                float pr = get_price(10);
                if (pr != -1.0f) {
                    CART_TOTAL += pr;
                    add_to_cart(this.CART_ID, 10);
                    this.Food.add(10);
                } 
            } else if (this.food_type_int == 3) {
                float pr = get_price(17);
                if (pr != -1.0f) {
                    CART_TOTAL += pr;
                    add_to_cart(this.CART_ID, 17);
                    this.Food.add(17);
                } 
            } else if (this.food_type_int == 4) {
                float pr = get_price(13);
                if (pr != -1.0f) {
                    CART_TOTAL += pr;
                    add_to_cart(this.CART_ID, 13);
                    this.Food.add(13);
                } 
            }
        } else if (e.getSource() == this.e2) {
            if (this.food_type_int == 1){
                float pr = get_price(6);
                if (pr != -1.0f) {
                    CART_TOTAL += pr;
                    add_to_cart(this.CART_ID, 6);
                    this.Food.add(6);
                } 
            } else if (this.food_type_int == 2) {
                float pr = get_price(11);
                if (pr != -1.0f) {
                    CART_TOTAL += pr;
                    add_to_cart(this.CART_ID, 11);
                    this.Food.add(11);
                } 
            } else if (this.food_type_int == 3) {
                float pr = get_price(18);
                if (pr != -1.0f) {
                    CART_TOTAL += pr;
                    add_to_cart(this.CART_ID, 18);
                    this.Food.add(18);
                } 
            } else if (this.food_type_int == 4) {
                float pr = get_price(16);
                if (pr != -1.0f) {
                    CART_TOTAL += pr;
                    add_to_cart(this.CART_ID, 16);
                    this.Food.add(16);
                } 
            }
        } else if (e.getSource() == this.e3) {
            if (this.food_type_int == 1){
                float pr = get_price(7);
                if (pr != -1.0f) {
                    CART_TOTAL += pr;
                    add_to_cart(this.CART_ID, 7);
                    this.Food.add(7);
                }
            } else if (this.food_type_int == 2) {
                float pr = get_price(8);
                if (pr != -1.0f) {
                    CART_TOTAL += pr;
                    add_to_cart(this.CART_ID, 8);
                    this.Food.add(8);
                } 
            } else if (this.food_type_int == 3) {
                float pr = get_price(19);
                if (pr != -1.0f) {
                    CART_TOTAL += pr;
                    add_to_cart(this.CART_ID, 19);
                    this.Food.add(19);
                } 
            } else if (this.food_type_int == 4) {
                float pr = get_price(12);
                if (pr != -1.0f) {
                    CART_TOTAL += pr;
                    add_to_cart(this.CART_ID, 12);
                    this.Food.add(12);
                } 
            }
        } else if (e.getSource() == this.e4) {
            if (this.food_type_int == 1){
                float pr = get_price(5);
                if (pr != -1.0f) {
                    CART_TOTAL += pr;
                    add_to_cart(this.CART_ID, 5);
                    this.Food.add(5);
                } 
            } else if (this.food_type_int == 2) {
                float pr = get_price(9);
                if (pr != -1.0f) {
                    CART_TOTAL += pr;
                    add_to_cart(this.CART_ID, 9);
                    this.Food.add(9);
                } 
            } else if (this.food_type_int == 3) {
                float pr = get_price(20);
                if (pr != -1.0f) {
                    CART_TOTAL += pr;
                    add_to_cart(this.CART_ID, 20);
                    this.Food.add(20);
                }
            } else if (this.food_type_int == 4) {
                float pr = get_price(14);
                if (pr != -1.0f) {
                    CART_TOTAL += pr;
                    add_to_cart(this.CART_ID, 14);
                    this.Food.add(14);
                }
            }
        } else if (e.getSource() == this.e5) {
            if (this.food_type_int == 1){
                float pr = get_price(4);
                if (pr != -1.0f) {
                    CART_TOTAL += pr;
                    add_to_cart(this.CART_ID, 4);
                    this.Food.add(4);
                } 
            } else if (this.food_type_int == 4) {
                float pr = get_price(15);
                if (pr != -1.0f) {
                    CART_TOTAL += pr;
                    add_to_cart(this.CART_ID, 15);
                    this.Food.add(15);
                } 
            }
        } else if (e.getSource() == this.e6) {
            if (this.food_type_int == 1){
                float pr = get_price(2);
                if (pr != -1.0f) {
                    CART_TOTAL += pr;
                    add_to_cart(this.CART_ID, 2);
                    this.Food.add(2);
                }
            }
        } else if (e.getSource() == this.e7) {
            if (this.food_type_int == 1){
                float pr = get_price(1);
                if (pr != -1.0f) {
                    CART_TOTAL += pr;
                    add_to_cart(this.CART_ID, 1);
                    this.Food.add(1);
                }
            }
        }
    }
}
