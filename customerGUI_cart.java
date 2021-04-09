import javax.swing.*;
import java.sql.*;
import javax.swing.border.EmptyBorder;
import java.awt.*; 
import java.awt.event.*;
import javax.swing.border.LineBorder;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class customerGUI_cart extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    private JPanel CART_PAGE;

    // cart title
    private JPanel cart_title;
    private JLabel lbl_title;
    private JButton btn_update_cart;

    // cart body
    private JPanel cart_body;
    // cart items
    private JPanel cart_items_holder;
    private JPanel cart_items;

    // cart body options
    private JPanel cart_body_options;
    private JPanel cart_options;
    private JPanel cart_options_holder;
    private JPanel options_summary;
    private JTextArea options_summary_text;
    private ArrayList<JButton> extra_options;
    private ArrayList<JButton> absent_options;

    // summary and buttons
    private JPanel summary_buttons;
    // order summary
    private JPanel order_summary;
    private JTextArea order_summary_text;
    private String total_string;
    private String products_string;
    // buttons
    private JPanel buttons;
    private JButton btn_place;
    private JButton btn_cancel;

    private ArrayList<Integer> al;
    private ArrayList<String> Product_Names;
    private ArrayList<Float> Product_Prices;
    // cart item buttons
    private ArrayList<JButton> cart_buttons;
    private ArrayList<JButton> cart_buttons_options;
    
    // sql connection
    Connection conn;

    private int CART_ID;
    private int current_cart_spot;
    private int current_prodID;

    // helper functions

    public void clear_cart() {
        this.products_string = "";
        this.order_summary_text.setText(products_string);
        this.total_string = "";
        String input = String.format("UPDATE Carts Set food = \'{}\', total = \'0.0\' WHERE cartid = \'%d\'", this.CART_ID);
        try {
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(input);
        } catch(Exception ec){
            System.out.println(String.format("Error accessing Database: Deleting food and total from cartid: \'%d\'.", this.CART_ID));
        }
        this.Product_Names.clear();
        this.Product_Prices.clear();
        this.order_summary_text.selectAll();
        this.order_summary_text.replaceSelection("");
        this.cart_items.removeAll();
    }

    public void clear_options() {
	this.cart_options.removeAll();
	this.extra_options.clear();
	this.absent_options.clear();

    }

    public void update_cart() { // gets data from the cart database
        this.order_summary.remove(this.order_summary_text);
        this.order_summary_text.selectAll();
        this.order_summary_text.replaceSelection("");
        System.out.println(this.CART_ID);
        String str_getTotal = String.format("SELECT total FROM Carts WHERE cartid = \'%d\'", this.CART_ID);
        String str_getProd = String.format("SELECT Food FROM Carts WHERE cartid = \'%d\'", this.CART_ID);
        this.total_string = "";
        this.products_string = "";
        this.Product_Names.clear();
        this.Product_Prices.clear();
        this.al.clear();
        this.cart_body.setVisible(false);
        this.cart_buttons.clear();
        this.cart_buttons_options.clear();
        this.cart_items_holder.remove(cart_items);
        this.cart_items.removeAll();
        try {
            // get food[] product ids from cart
            Statement stmt1 = this.conn.createStatement();
            ResultSet result1 = stmt1.executeQuery(str_getProd);
            Array a;
            String str = "";
            while (result1.next()) {
                a = result1.getArray("food");
                str += a.toString();
            }
	    //System.out.println(a.toString());
            if (str.equals("{}")) {
                this.order_summary_text.append("TOTAL: $0.00");
                this.order_summary.add(order_summary_text);
                this.cart_body.setVisible(true);
                clear_cart();
                return;
            }
            str = str.substring(1,str.length()-1);
            String[] arr = str.split(",");
            for (String s: arr) {
                this.al.add(Integer.parseInt(s));
            }
            for (int i = 0; i < this.al.size(); i++)
                System.out.println(al.get(i));
            // get product names
            for (int i = 0; i < al.size(); i++) {
                String str_getProd_Name = String.format("SELECT productname FROM Products WHERE productid = \'%d\'", al.get(i));
                Statement stmt2 = this.conn.createStatement();
                ResultSet result2 = stmt2.executeQuery(str_getProd_Name);
                while(result2.next()) {
                    Product_Names.add(result2.getString("productname"));
                }
            }
            for (int i = 0; i < Product_Names.size(); i++)
                System.out.println(Product_Names.get(i));
            // get product prices
            for (int i = 0; i < al.size(); i++) {
                String str_getProd_Price = String.format("SELECT price FROM Products WHERE productid = \'%d\'", al.get(i));
                Statement stmt3 = this.conn.createStatement();
                ResultSet result3 = stmt3.executeQuery(str_getProd_Price);
                while(result3.next()) {
                    Product_Prices.add(result3.getFloat("price"));
                }
            }
            float new_total = 0.0f;
            System.out.println(String.format("New total: %f", new_total));
            for (int i = 0; i < Product_Prices.size(); i++)
                new_total += Product_Prices.get(i);
            String str_update_total = String.format("UPDATE Carts Set total = \'%f\' WHERE cartid = \'%d\'", new_total, this.CART_ID);
            Statement stmt4 = this.conn.createStatement();
            stmt4.executeUpdate(str_update_total);
            for (int i = 0; i < Product_Prices.size(); i++){
                this.products_string = Product_Names.get(i); 
                this.products_string += Float.toString(Product_Prices.get(i));
                this.products_string += "\n";
                this.order_summary_text.append(products_string); 
           }
            
	     //Update Notes
	    String str_getNote = String.format("SELECT notes FROM carts WHERE cartid = \'%d\'", this.CART_ID);
            Statement stmt6 = this.conn.createStatement();
            ResultSet result6 = stmt6.executeQuery(str_getNote);
	    Array arrayT;
	    String currentNote = "";
	    while (result6.next()) {
	        arrayT = result6.getArray("notes");
	        currentNote += arrayT.toString();
	    }

	    String newNote = "\'" + currentNote + "\'";
	    String str_updateNote = String.format("UPDATE carts SET notes = %s WHERE cartid = \'%d\'", newNote,this.CART_ID);
	    Statement stmt5 = this.conn.createStatement();
            stmt5.executeUpdate(str_updateNote);
            

	    // get cart total
            Statement stmt = this.conn.createStatement();
            ResultSet result = stmt.executeQuery(str_getTotal);
            while (result.next()){
                this.total_string += Float.toString(result.getFloat("total"));
            }
            this.order_summary_text.append("TOTAL: " + total_string);
            this.order_summary.add(order_summary_text);
            
            // add the products to the big space
            for (int i = 0; i < al.size(); i++) {
                JPanel jp = new JPanel(new FlowLayout());
                jp.setBackground(Color.WHITE);
                
                JButton b2 = new JButton(" + ");
                b2.setBackground(new Color(238, 179, 28, 255));
                b2.addActionListener(this);
                this.cart_buttons_options.add(b2);
                jp.add(b2);
                this.cart_items.add(jp);
 
                jp.add(new JLabel(this.Product_Names.get(i)));
                jp.add(new JLabel(Float.toString(this.Product_Prices.get(i))));
                JButton b = new JButton(" - ");
                b.setBackground(new Color(238, 179, 28, 255));
                b.addActionListener(this);
                this.cart_buttons.add(b);
                jp.add(b);
                this.cart_items.add(jp);
            }
            this.cart_items_holder.add(this.cart_items);
            this.cart_body.setVisible(true);
            
        } catch(Exception e){
            System.out.println("Error accessing Database: SELECT Food FROM Carts");
        }
    }

     public void update_options(int productID) {
	clear_options();
	this.current_prodID = productID; 
        System.out.println(productID);
        String str_getIngred = String.format("SELECT ingredientname FROM ingredients WHERE ingredientid = \'%d\'", productID);
        //System.out.println(str_getIngred);
        //this.cart_options_holder.remove(cart_options);
        this.cart_options.removeAll();
      try {
            // get list of ingredients from productID
            Statement stmt1 = this.conn.createStatement();
            ResultSet result1 = stmt1.executeQuery(str_getIngred);
            String str = "";
            while (result1.next()) {
                String listIngred = result1.getString("ingredientname");
	    	str += listIngred.trim();
	    }
            String[] listofIngred = str.split(",");
      	    // Setting up the panels and buttons for each ingredient 
	    for (int i = 0; i < listofIngred.length; i++) {
                JPanel jp = new JPanel(new FlowLayout());
                jp.setBackground(Color.WHITE);
                jp.add(new JLabel(listofIngred[i]));
		jp.setPreferredSize(new Dimension(50,200));
               	jp.setFont(new java.awt.Font("Tahoma", 0, 9));
		 
		JButton b2 = new JButton("Extra");
                b2.setBackground(new Color(238, 179, 28, 255));
               	b2.setFont(new java.awt.Font("Tahoma", 0, 9));
                b2.addActionListener(this);
                this.extra_options.add(b2);
                jp.add(b2);
                this.cart_options.add(jp);
 
                JButton b = new JButton("None");
                b.setBackground(new Color(238, 179, 28, 255));
               	b.setFont(new java.awt.Font("Tahoma", 0, 9));
                b.addActionListener(this);
                this.absent_options.add(b);
                jp.add(b);
                this.cart_options.add(jp);
            }
            this.cart_body_options.add(this.cart_options);
            //this.cart_body.setVisible(true);
            


	} catch(Exception e){
            System.out.println("Error accessing Database: SELECT ingredientname FROM ingredients");
        }
	update_cart();
     }   


    public customerGUI_cart(Connection con, int cartid) {
        this.conn = con;
        this.CART_ID = cartid;
        this.al = new ArrayList<Integer>();
        this.Product_Names = new ArrayList<String>();
        this.Product_Prices = new ArrayList<Float>();
	this.current_cart_spot = -1;
	this.current_prodID = -1;

        String str_getCart = String.format("INSERT INTO Carts VALUES (\'%d\', NULL, \'0.0\', '{}')", this.CART_ID);
        try {
            Statement stmt = this.conn.createStatement();
            stmt.executeUpdate(str_getCart);
        } catch(Exception e){
            System.out.println("Error accessing Database: Initializing the cart entry.");
        }

        this.CART_PAGE = new JPanel();
        this.CART_PAGE.setLayout(new BorderLayout());
        this.CART_PAGE.setBackground(new Color(238, 179, 28, 255));
        this.CART_PAGE.setBorder(new EmptyBorder(20,20,25,25));

        this.cart_buttons = new ArrayList<JButton>();
        this.cart_buttons_options = new ArrayList<JButton>();
	this.extra_options = new ArrayList<JButton>();
	this.absent_options = new ArrayList<JButton>();

        // ------- cart title code
        this.cart_title = new JPanel(new FlowLayout());
        this.lbl_title = new JLabel("Your Cart");
        this.lbl_title.setFont(new Font("Verdana", Font.PLAIN, 30));
        this.lbl_title.setBorder(new EmptyBorder(0,0,0,50));
        this.cart_title.setOpaque(false);
        this.cart_title.add(this.lbl_title);
        this.btn_update_cart = new JButton("Refresh");
        this.btn_update_cart.setBackground(new Color(159, 27, 27, 255));
        this.btn_update_cart.setForeground(Color.WHITE);
        this.btn_update_cart.addActionListener(this);
        // Button btn_update_cart is not neccessary anymore with page updating on its own
        this.btn_update_cart.setVisible(false);
        this.cart_title.add(this.btn_update_cart);
        this.CART_PAGE.add(this.cart_title, BorderLayout.PAGE_START);
        // -------- end cart title code

        // -------- cart body code
        GridBagLayout gl1 = new GridBagLayout();
        GridBagConstraints c1 = new GridBagConstraints();
        c1.weightx = 0.40;
        this.cart_body = new JPanel(gl1);
        this.cart_body.setOpaque(false);

        // -------- cart items code
        //this.cart_items_scroll = new JScrollPane();
        //this.cart_items_scroll.setPreferredSize(new Dimension(500,400));
        //this.cart_items_scroll.setBackground(new Color(255, 247, 178, 255));
        this.cart_items_holder = new JPanel();
        this.cart_items_holder.setBackground(new Color(240,230,140));
        this.cart_items_holder.setBorder(new EmptyBorder(25,25,25,25));
        this.cart_items_holder.setPreferredSize(new Dimension(380,320));
        GridLayout gl2 = new GridLayout(7,1);
        gl2.setHgap(20); gl2.setVgap(15);
        this.cart_items = new JPanel(gl2); 
        this.cart_items.setOpaque(false);
        // -------- end cart items code
        
	// -------- cart item options code
        GridBagLayout gl4 = new GridBagLayout();
        this.cart_body_options = new JPanel(gl4);
        this.cart_body_options.setBackground(new Color(240, 230, 140));
        this.cart_body_options.setPreferredSize(new Dimension(240,320));
        //this.cart_options_holder = new JPanel();
        //this.cart_options_holder.setBackground(new Color(240,230,140));
        //this.cart_options_holder.setBorder(new EmptyBorder(25,25,25,25));
        //this.cart_options_holder.setPreferredSize(new Dimension(380,320));
        GridLayout gl5 = new GridLayout(7,1);
        gl5.setHgap(20); gl5.setVgap(5);
        this.cart_options = new JPanel(gl5); 
        this.cart_options.setOpaque(false);
        

	// -------- end cart item options code

        // -------- summary and buttonss code
        this.summary_buttons = new JPanel(new GridLayout(2,1));
        this.summary_buttons.setOpaque(false);
        //this.summary_buttons.setPreferredSize(new Dimension(250,400));
        //this.summary_buttons.setBorder(new EmptyBorder(20,20,25,25));

        // -------- order summary code
        this.order_summary = new JPanel(new BorderLayout());
        this.order_summary.setBackground(new Color(255, 247, 178, 255));
        this.order_summary.setBorder(new EmptyBorder(10,10,10,10));
        this.order_summary.add(new JLabel("Order Summary"), BorderLayout.PAGE_START);
        // -------- order summary text code
        this.order_summary_text = new JTextArea();
        this.order_summary_text.setBorder(new EmptyBorder(5,5,5,5));
        this.products_string = "";
        this.order_summary_text.setText(products_string);
        this.order_summary.add(order_summary_text, BorderLayout.CENTER);
        this.order_summary_text.setEditable(false);
        this.order_summary_text.setBorder(new LineBorder(new Color(159, 27, 27, 255), 3));
        this.order_summary_text.setOpaque(false);
        // -------- end order summary text code
        // -------- end order summary code

        // -------- buttons code
        GridLayout gl3 = new GridLayout(2,1);
        gl3.setVgap(20);
        this.buttons = new JPanel(gl3);
        //this.buttons.setBorder(new EmptyBorder(20,20,20,20));
        this.buttons.setOpaque(false);
        this.btn_place = new JButton("Place Order");
        this.btn_place.setBackground(new Color(173,255,47,255));
        this.btn_place.addActionListener(this);
        this.btn_cancel = new JButton("Cancel");
        this.btn_cancel.setBackground(new Color(250,128,114,255));
        this.btn_cancel.addActionListener(this);
        this.buttons.add(btn_place);
        this.buttons.add(btn_cancel);

        // -------- end buttons code
        // -------- end summary and buttons code
        // -------- end cart body code

        update_cart();
        // add the cart items
        this.cart_items_holder.add(this.cart_items);
        this.cart_body.add(this.cart_items_holder, c1);

        // add the options table
        this.cart_body_options.add(this.cart_options, c1);
        c1.weightx = 0.25;
        this.cart_body.add(this.cart_body_options, c1);

        // add the order summary and buttons
        c1.weightx = 0.20;
        this.summary_buttons.add(this.order_summary);
        this.summary_buttons.add(this.buttons);

        // add the whole cart body
        this.cart_body.add(this.summary_buttons, c1); // second to final step
        this.CART_PAGE.add(this.cart_body, BorderLayout.CENTER); // final step
   
	try { 
            //Update Notes
            String newNote = "\'{\"\"}\'";
	    //newNote = newNote.substring(0,newNote.length()-3) + "}\'";
	    //System.out.println(":" + newNote);
            String str_updateNote = String.format("UPDATE carts SET notes = %s WHERE cartid = \'%d\'", newNote,this.CART_ID);
	    Statement stmt5 = this.conn.createStatement();
            stmt5.executeUpdate(str_updateNote);
    	} catch (Exception e) {
	    System.out.println("Error : setting notes in carts");
	}
    }

    public JPanel GET_PANEL() {
        return this.CART_PAGE;
    }

    // ActionListener inherited function
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btn_cancel) {
            clear_cart();
        } else if (e.getSource() == this.btn_update_cart) {
            update_cart();
        } else if (e.getSource() == this.btn_place) { // TO-DO add to orders
            try {
                String sqlStatement = "SELECT Count(*) FROM Orders";
                //send statement to DBMS
                Statement stmt = conn.createStatement();
                ResultSet result = stmt.executeQuery(sqlStatement);
                int orderid = 0;
                while(result.next()) {
                    orderid = result.getInt("count") + 1;
                }
                System.out.println("Adding to orderid:");
                System.out.println(orderid);
                // get customerid
                String sqlStatement3 = String.format("SELECT customerid FROM Carts WHERE cartid=\'%d\'", this.CART_ID);
                Statement stmt3 = conn.createStatement();
                ResultSet result3 = stmt3.executeQuery(sqlStatement3);
                int custid = -1;
                while(result3.next()) {
                    custid = result3.getInt("customerid");
                }
                System.out.println("Adding to customerid:");
                System.out.println(custid);
                String sqlStatement2 = String.format("SELECT Food FROM Carts WHERE cartid = \'%d\'", this.CART_ID);
                //send statement to DBMS
                Statement stmt2 = conn.createStatement();
                ResultSet result2 = stmt2.executeQuery(sqlStatement2);
                java.util.Date utilDate = new java.util.Date();
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                String add_order = "INSERT INTO Orders VALUES (?,?,?,?,?)";
                PreparedStatement psql = conn.prepareStatement(add_order);
                psql.setInt(1,orderid);
                if (custid == -1)
                    psql.setNull(2,java.sql.Types.NULL);
                else
                    psql.setInt(2,custid);
                psql.setInt(3,this.CART_ID);
                psql.setDate(4,sqlDate);
                Array a;
                while(result2.next()) {
                    a = result2.getArray("food");
                    psql.setArray(5,a);
                }
                psql.executeUpdate();
                JOptionPane.showMessageDialog(null, "Your Order has Been Placed!", "Congradulations", JOptionPane.INFORMATION_MESSAGE);
            } catch(Exception ec){
                System.out.println("Error adding to order");
            }
        } else if (this.cart_buttons.contains(e.getSource())) {
            for (int i = 0; i < this.cart_buttons.size(); i++) {
                if (e.getSource() == this.cart_buttons.get(i)) {
                    float new_total = 0.0f;
                    for (int j = 0; j < this.Product_Prices.size(); j++) {
                        if (j != i)
                            new_total += this.Product_Prices.get(i);
                    }
		            System.out.println("REMOVED");
                    String input = String.format("UPDATE Carts Set food = array_remove(food,\'%d\'), total = \'%f\' WHERE cartid = \'%d\'",this.al.get(i),new_total,this.CART_ID);
                    try {
                        Statement stmt = this.conn.createStatement();
                        stmt.executeUpdate(input);
                        System.out.println(String.format("Removing item: %d",i));
                        update_cart();
                    } catch(Exception ec){
                        System.out.println(String.format("Error removing item :: UPDATE Carts Set food = array_remove(food,\'%d\'), total = \'%f\' WHERE cartid = \'%d\'",this.al.get(i),new_total,this.CART_ID));
                    }
                }
            }
        } else if (this.cart_buttons_options.contains(e.getSource())) {
            for (int i = 0; i < this.cart_buttons_options.size(); i++) {
                if (e.getSource() == this.cart_buttons_options.get(i)) {
		    System.out.println("OPTIONS");
                    //String input = String.format("SELECT array_position(food,\'%d\') FROM carts WHERE cartid = \'%d\'",this.al.get(i),this.CART_ID);
                    String input = String.format("SELECT food FROM carts WHERE cartid = \'%d\'",this.CART_ID);
                    
		    try {
                        current_cart_spot = i;
			Statement stmt = this.conn.createStatement();
                        //stmt.executeUpdate(this.conn.createStatement());
			ResultSet foodArray = stmt.executeQuery(input);
			Array tempArray;
			String strOfFoods = "";
			while (foodArray.next()) {
			    tempArray = foodArray.getArray("food");
			    strOfFoods += tempArray.toString();
			}
			System.out.println(strOfFoods);
			strOfFoods = strOfFoods.substring(1,strOfFoods.length() - 1);
			String[] prodArray = strOfFoods.split(",");
			System.out.println(prodArray[i]);
			this.current_prodID = Integer.parseInt(prodArray[i]);
			update_options(Integer.parseInt(prodArray[i]));
	            } catch(Exception ec){
                        System.out.println(String.format("Error getting productID :: SELECT food FROM carts WHERE cartid = \'%d\'",this.CART_ID));
		    }
                }
 
	   }	

	} else if (this.extra_options.contains(e.getSource())) {
            for (int i = 0; i < this.extra_options.size(); i++) {
		if (e.getSource() == this.extra_options.get(i))
		{
		    System.out.println("EXTRA");
		    String notesStr = "{";
                    String input = String.format("SELECT notes FROM carts WHERE cartid = \'%d\'",this.CART_ID);
        	    String str_getIngred = String.format("SELECT ingredientname FROM ingredients WHERE ingredientid = \'%d\'", this.current_prodID);
        	    String str_getFood = String.format("SELECT productname FROM products WHERE productid = \'%d\'", this.current_prodID);
		    //System.out.println("curr:" + current_prodID);
		    try {
			Statement stmt = this.conn.createStatement();
                        //stmt.executeUpdate(this.conn.createStatement());
			ResultSet foodArray = stmt.executeQuery(input);
			Array tempArray;
			String str = "";
			while (foodArray.next()) {
			    tempArray = foodArray.getArray("notes");
			    str += tempArray.toString();
			}
			System.out.println("currNote:" + str);
			str = str.substring(0,str.length() - 1);
			String[] notesArray = str.split(",");
			str = "\'" + str;
	                if (str.length() > 6) {
			    str = str.substring(0,str.length()-2) + ", }\'";
			    str = str.replaceAll("\"","");
			}
			// Getting list of ingredients for current product
			Statement stmt1 = this.conn.createStatement();
           		ResultSet result1 = stmt1.executeQuery(str_getIngred);
        		String str1 = "";
            		while (result1.next()) {
                		String listIngred = result1.getString("ingredientname");
	    			str1 += listIngred.trim();
	                }
            		String[] listofIngred = str1.split(",");
			// Getting product name
			Statement stmt2 = this.conn.createStatement();
			ResultSet foodname = stmt.executeQuery(str_getFood);
			Array tempArray2;
			String str2 = "";
			while (foodname.next()) {
			    tempArray2 = foodname.getArray("productname");
			    str2 += tempArray2.toString();
			}
			System.out.println("Curr Prod:" + str2);
			
			i = i % listofIngred.length;
			int t = 0;
			String tempProd = "";		
			System.out.println("TOP I)" + i + " t)" + t);
			for (String x: listofIngred)
			{
			    if (t == i+1)
			    {
				tempProd = x;
			    }
			}
			System.out.println("END");

			String currProd = "";
			currProd += str2 + ": Extra " + listofIngred[i];		
			String newNote;
			if (str.length() == 4)
				newNote = "\'{";
			else
				newNote = str.substring(0,str.length()-2);
			newNote += currProd + "}\'";

			// Writing to new notes in Carts
			System.out.println("NewNote:" + newNote);
			String str_updateNote = String.format("UPDATE carts SET notes = %s WHERE cartid = \'%d\'", newNote,this.CART_ID);
	    		System.out.println(str_updateNote);
			Statement stmt5 = this.conn.createStatement();
            		stmt5.executeUpdate(str_updateNote);
		
			update_cart();
			this.extra_options.get(i).setEnabled(false);
	            } catch(Exception ec){
                        System.out.println(String.format("Error adding preferences",this.CART_ID));
		    }
 

		}
	    }

	} else {
            for (int i = 0; i < this.absent_options.size(); i++) {
		if (e.getSource() == this.absent_options.get(i))
		{
		    System.out.println("NONE");
		    String notesStr = "{";
                    String input = String.format("SELECT notes FROM carts WHERE cartid = \'%d\'",this.CART_ID);
        	    String str_getIngred = String.format("SELECT ingredientname FROM ingredients WHERE ingredientid = \'%d\'", this.current_prodID);
        	    String str_getFood = String.format("SELECT productname FROM products WHERE productid = \'%d\'", this.current_prodID);
		    //System.out.println("curr:" + current_prodID);
		    try {
			Statement stmt = this.conn.createStatement();
                        //stmt.executeUpdate(this.conn.createStatement());
			ResultSet foodArray = stmt.executeQuery(input);
			Array tempArray;
			String str = "";
			while (foodArray.next()) {
			    tempArray = foodArray.getArray("notes");
			    str += tempArray.toString();
			}
			System.out.println("currNote:" + str);
			str = str.substring(0,str.length() - 1);
			String[] notesArray = str.split(",");
			str = "\'" + str;
	                if (str.length() > 6) {
			    str = str.substring(0,str.length()-2) + ", }\'";
			    str = str.replaceAll("\"","");
			}
			// Getting list of ingredients
			Statement stmt1 = this.conn.createStatement();
           		ResultSet result1 = stmt1.executeQuery(str_getIngred);
        		String str1 = "";
            		while (result1.next()) {
                		String listIngred = result1.getString("ingredientname");
	    			str1 += listIngred.trim();
	                }
            		String[] listofIngred = str1.split(",");

			// Getting current Product
			Statement stmt2 = this.conn.createStatement();
			ResultSet foodname = stmt.executeQuery(str_getFood);
			Array tempArray2;
			String str2 = "";
			while (foodname.next()) {
			    tempArray2 = foodname.getArray("productname");
			    str2 += tempArray2.toString();
			}
			System.out.println("Curr Prod:" + str2);
			
			i = i % listofIngred.length;
			int t = 0;
			String tempProd = "";		
			System.out.println("TOP I)" + i + " t)" + t);
			for (String x: listofIngred)
			{
			    if (t == i+1)
			    {
				tempProd = x;
			    }
			}
			System.out.println("END");

			String currProd = "";
			currProd += str2 + ": None " + listofIngred[i];		
			String newNote;
			if (str.length() == 4)
				newNote = "\'{";
			else
				newNote = str.substring(0,str.length()-2);
			newNote += currProd + "}\'";

			// Writing to the new Notes
			System.out.println("NewNote:" + newNote);
			String str_updateNote = String.format("UPDATE carts SET notes = %s WHERE cartid = \'%d\'", newNote,this.CART_ID);
	    		System.out.println(str_updateNote);
			Statement stmt5 = this.conn.createStatement();
            		stmt5.executeUpdate(str_updateNote);
		
			update_cart();
			this.extra_options.get(i).setEnabled(false);
		   

	            } catch(Exception ec){
                        System.out.println(String.format("Error adding preferences",this.CART_ID));
		    }                  

 
		}
	    }
	}
    }
}
