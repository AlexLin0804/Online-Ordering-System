import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*; 
import java.awt.event.*;
import java.io.FileNotFoundException;

public class ManagerGUI_menu extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    // restaurant name
    private String MANAGER_NAME = "Menu";
    // order page container
    private JPanel MENU_PAGE;
    // welcome statement
    private JLabel lbl_welcome;
    
    // default initializer
    public ManagerGUI_menu() {
        this.MENU_PAGE = new JPanel();
        this.MENU_PAGE.setLayout(new BorderLayout());
        this.lbl_welcome = new JLabel(String.format("Welcome back! %s", MANAGER_NAME));
        MENU_PAGE.add(lbl_welcome, BorderLayout.PAGE_START);

    }

    public JPanel GET_PANEL() {
        return this.MENU_PAGE;
    }

    // ActionListener inherited function
    public void actionPerformed(ActionEvent e) {

    }
}