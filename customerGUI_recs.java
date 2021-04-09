import javax.swing.*;
import java.sql.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class customerGUI_recs extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    Connection conn;

    int CART_ID;
    int CUST_ID;

    int NUM_PAST_ORDERS_TO_DISPLAY = 4;
    int NUM_DEALS_TO_DISPLAY = 6;

    JPanel RECS_PAGE;
    JPanel food_title;
    JButton back_button;
    JLabel title;

    JPanel recs_body;
    // order history
    JPanel order_history;
    JLabel lbl_no_signin;

    ArrayList<Integer> cartids;
    ArrayList<JButton> history_buttons;
    ArrayList<Array> deal_productids;
    ArrayList<Float> deal_prices;
    ArrayList<JButton> deal_buttons;

    JPanel deals;

    public void populate_order_history() {
        this.order_history.removeAll();
        this.cartids.clear();
        this.history_buttons.clear();
        GridLayout gl2 = new GridLayout(5,1);
        gl2.setVgap(10);
        this.order_history.setLayout(gl2);
        this.order_history.add(new JLabel("Order History", JLabel.CENTER));
        // get customer id from cart
        try {
            Statement stmt = this.conn.createStatement();
            String sqlStatement = String.format("SELECT customerid FROM Carts WHERE cartid=\'%d\'", this.CART_ID);
            ResultSet result = stmt.executeQuery(sqlStatement);
            this.CUST_ID = -1;
            while (result.next()) {
                this.CUST_ID = result.getInt("customerid");
            }
            System.out.println(String.format("Customer id %d", this.CUST_ID));
            if (this.CUST_ID == -1) {
                System.out.println("No customer id");
                this.lbl_no_signin = new JLabel("Sign In to access your past orders!", JLabel.CENTER);
                this.order_history.add(this.lbl_no_signin);
                return;
            }
        } catch (Exception e) {
            System.out.println("Error getting customer id from cart");
        }

        // get cardid from orderids
        try {
            Statement stmt;
            String sqlStatement;
            ResultSet result;
            stmt = this.conn.createStatement();
            sqlStatement = String.format("SELECT cartid FROM Orders WHERE customerid=\'%d\'", this.CUST_ID);
            result = stmt.executeQuery(sqlStatement);
            int j = 0;
            int cartnum = -1;
            while(result.next() && j < this.NUM_PAST_ORDERS_TO_DISPLAY) {
                cartnum = result.getInt("cartid");
                if (cartnum != -1) {
                    this.cartids.add(cartnum);
                }
                j++;
            }
        } catch(Exception ex) {
            System.out.println("Error getting cartids from orderids");
        }
        // get productids from cartids and add to page
        try {
            Statement stmt;
            String sqlStatement;
            ResultSet result;
            for (int i = 0; i < this.cartids.size(); i++) {
                JPanel past_order = new JPanel(new FlowLayout());
                past_order.setBorder(new EmptyBorder(5,5,2,5));
                past_order.setBackground(new Color(238, 179, 28, 255));
                JLabel lbl_ordernum = new JLabel(Integer.toString(i+1));
                lbl_ordernum.setBorder(new EmptyBorder(2,2,2,10));
                past_order.add(lbl_ordernum);
                stmt = this.conn.createStatement();
                sqlStatement = String.format("SELECT total, food FROM Carts WHERE cartid=\'%d\'", this.cartids.get(i));
                result = stmt.executeQuery(sqlStatement);
                Array a;
                String str_food;
                Float total = 0.0f;
                ArrayList<Integer> foods = new ArrayList<Integer>();
                JTextArea product_names = new JTextArea();
                product_names.setOpaque(false);
                product_names.setForeground(Color.WHITE);
                product_names.setEditable(false);
                while(result.next()) {
                    total = result.getFloat("total");
                    a = result.getArray("food");
                    str_food = a.toString();
                    str_food = str_food.substring(1,str_food.length()-1);
                    String[] arr = str_food.split(",");
                    for (String s: arr) {
                        foods.add(Integer.parseInt(s));
                    }
                    for (int j = 0; j < foods.size(); j++) {
                        Statement stmt2 = this.conn.createStatement();
                        String sqlStatement2 = String.format("SELECT productname FROM Products WHERE productid=\'%d\'", foods.get(j));
                        ResultSet result2 = stmt2.executeQuery(sqlStatement2);
                        while(result2.next()) {
                            product_names.append(result2.getString("productname"));
                            product_names.append(" \n");
                        }
                    }
                    foods.clear();
                }
                past_order.add(product_names);
                past_order.add(new JLabel(Float.toString(total)));
                JButton btn_reorder = new JButton("Add to Cart");
                btn_reorder.addActionListener(this);
                btn_reorder.setBorder(new EmptyBorder(2,10,2,2));
                this.history_buttons.add(btn_reorder);
                past_order.add(btn_reorder);
                this.order_history.add(past_order);
            }
        } catch(Exception ex) {
            System.out.println("Error getting product ids from cartid");
        }
    }

    public void populate_deals() {
        this.deals.removeAll();
        this.deal_buttons.clear();
        this.deal_prices.clear();
        this.deal_prices.clear();
        GridLayout gl4 = new GridLayout(this.NUM_DEALS_TO_DISPLAY+1,1);
        this.deals.setLayout(gl4);
        JLabel deals_title = new JLabel("Deals", JLabel.CENTER);
        this.deals.add(deals_title);
        // deals length
        try {
            String sqlStatemnt = "SELECT Count(*) FROM Deals";
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(sqlStatemnt);
            while(result.next()) {
                int j = result.getInt("count");
                if (j < this.NUM_DEALS_TO_DISPLAY) {
                    this.NUM_DEALS_TO_DISPLAY = j;
                }
            }
            System.out.println(String.format("Displaying %d deals", this.NUM_DEALS_TO_DISPLAY));
        } catch (Exception ex) {
            System.out.println("Error getting deals size");
        }
        // deals display
        try {
            String sqlStatement = "SELECT dealdescription, dealprice, product, dealname FROM Deals";
            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(sqlStatement);
            int j = 0;
            while (result.next() && j < this.NUM_DEALS_TO_DISPLAY) {
                JPanel deal_order = new JPanel(new FlowLayout());
                deal_order.setBorder(new EmptyBorder(5,5,2,5));
                deal_order.setBackground(new Color(159, 27, 27, 255));
                this.deal_productids.add(result.getArray("product"));
                // deal name
                JLabel dn = new JLabel(result.getString("dealname"), JLabel.LEFT);
                dn.setForeground(Color.WHITE);
                deal_order.add(dn);
                // deal description
                JLabel dd = new JLabel(result.getString("dealdescription"), JLabel.CENTER);
                dd.setForeground(Color.WHITE);
                deal_order.add(dd);
                // deal prices
                Float f = result.getFloat("dealprice");
                JLabel dp = new JLabel(Float.toString(f));
                dp.setForeground(Color.YELLOW);
                this.deal_prices.add(f);
                deal_order.add(dp);
                // add to cart button
                JButton db = new JButton("Add to Cart");
                db.setBackground(new Color(238, 179, 28, 255));
                this.deal_buttons.add(db);
                deal_order.add(db);

                // add to deals panel
                this.deals.add(deal_order);
                j++;
            }
        } catch (Exception ex) {
            System.out.println("Error adding deals to page");
        }
    }

    public customerGUI_recs(Connection con, customerGUI parent, int cartid) {
        this.conn = con;
        this.CART_ID = cartid;

        this.cartids = new ArrayList<Integer>();
        this.history_buttons = new ArrayList<JButton>();
        this.deal_productids = new ArrayList<Array>();
        this.deal_prices = new ArrayList<Float>();
        this.deal_buttons = new ArrayList<JButton>();

        //initializing JPanel and Layout
        this.RECS_PAGE = new JPanel(new BorderLayout());
        this.RECS_PAGE.setBackground(new Color(238, 179, 28, 255));
        this.RECS_PAGE.setBorder(new EmptyBorder(25,25,25,25));
        this.title = new JLabel("Your Recommendations");
        this.back_button = new JButton("Back");
        this.back_button.setActionCommand("back_to_orders");
        this.back_button.addActionListener(parent);
        this.food_title = new JPanel(new FlowLayout());
        this.food_title.setOpaque(false);
        this.food_title.add(this.title);
        this.food_title.add(this.back_button, FlowLayout.LEFT);
        this.RECS_PAGE.add(this.food_title, BorderLayout.PAGE_START);

        // recommendations body
        GridLayout gl = new GridLayout(1,2);
        gl.setHgap(20);
        this.recs_body = new JPanel(gl);
        this.recs_body.setOpaque(false);
        // order history
        GridLayout gl2 = new GridLayout(5,1);
        gl2.setVgap(10);
        this.order_history = new JPanel(gl2);
        this.order_history.setBackground(new Color(240,230,140));
        this.order_history.setBorder(new EmptyBorder(20,20,20,20));
        //populate_order_history();
        // deals
        this.deals = new JPanel(gl2);
        this.deals.setBackground(new Color(240,230,140));
        this.deals.setBorder(new EmptyBorder(25,25,25,25));
        //populate_deals();
        // add to the pages
        this.recs_body.add(this.order_history);
        this.recs_body.add(this.deals);
        this.RECS_PAGE.add(this.recs_body, BorderLayout.CENTER);
    }
    public JPanel GET_PANEL() {
        populate_order_history();
        populate_deals();
        return this.RECS_PAGE;
    }
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < this.history_buttons.size(); i++) {
            if (e.getSource() == this.history_buttons.get(i)) {
                try {
                    System.out.println(String.format("clicked past order button: %d", this.cartids.get(i)));
                    String sqlStatement2 = String.format("SELECT total, food FROM Carts WHERE cartid = \'%d\'", this.cartids.get(i));
                    Statement stmt2 = conn.createStatement();
                    ResultSet result2 = stmt2.executeQuery(sqlStatement2);
                    // add food to Carts
                    PreparedStatement psql = conn.prepareStatement("UPDATE Carts SET food = ? WHERE cartid = ?");
                    //int j = psql.getParameterMetaData().getParameterCount();
                    //System.out.println(j);
                    Array a;
                    Float total = 0.0f;
                    while(result2.next()) {
                        total = result2.getFloat("total");
                        a = result2.getArray("food");
                        psql.setArray(1,a);
                        System.out.println(a.toString());
                    }
                    psql.setInt(2,this.CART_ID);
                    psql.executeUpdate();
                    // adding total to carts
                    String sqlStatement4 = String.format("UPDATE Carts SET total = \'%f\' WHERE cartid=\'%d\'", total, this.CART_ID);
                    //send statement to DBMS
                    Statement stmt4 = conn.createStatement();
                    stmt4.executeUpdate(sqlStatement4);
                    System.out.println(total.toString());
                    JOptionPane.showMessageDialog(null, "Added to Cart!", "Yahoo", JOptionPane.INFORMATION_MESSAGE);
                } catch(Exception ex) {
                    System.out.println("Error updating cart");
                }
            }
        }
    }
}
