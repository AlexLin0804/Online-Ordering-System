import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*; 
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.sql.*;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;

public class ManagerGUI_dashboard extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    // restaurant name
    private String MANAGER_NAME = "Manager Name";
    // order page container
    private JPanel DASHBOARD_PAGE;
    // welcome statement
    private JLabel lbl_welcome;

    private JLabel logo;


    
    // default initializer
    public ManagerGUI_dashboard(Connection con) {
        this.DASHBOARD_PAGE = new JPanel();
        this.DASHBOARD_PAGE.setLayout(new BorderLayout());
        this.lbl_welcome = new JLabel(String.format("Welcome back! %s", MANAGER_NAME),SwingConstants.CENTER);
        lbl_welcome.setFont(new Font("Verdana", Font.PLAIN, 30));
        DASHBOARD_PAGE.add(lbl_welcome, BorderLayout.PAGE_START);

        this.logo = new JLabel("",SwingConstants.CENTER);
        try {
            Image img = ImageIO.read(getClass().getResource("images/logo.png")).getScaledInstance(300, 
            300, Image.SCALE_SMOOTH);
            this.logo.setIcon(new ImageIcon(img));
        } 
        catch (Exception ex) {
            System.out.println(ex);
        }
        
        DASHBOARD_PAGE.add(logo, BorderLayout.CENTER);
    }

    public JPanel GET_PANEL() {
        return this.DASHBOARD_PAGE;
    }

    // ActionListener inherited function
    public void actionPerformed(ActionEvent e) {

    }
}