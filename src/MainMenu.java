import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;
public class MainMenu extends JFrame {
	private JPanel panel;
	private JMenuBar menuBar;
	//private JLabel Background;
	private boolean isLogged=false;
	private JTextField username;
	private JPasswordField password;
	private JLabel UsernameLabel, PasswordLabel;
	private JButton quitButton, playTheGameButton;
	private JButton LoginButton,RegisterButton;
	private int ScreenWidth = 1000;//1
	private int ScreenHeight = 600;//2
	private DataManager DataManager;
	private JetFighter Gameplay;
	private JLabel WelcomeLabel;
	
	public MainMenu() {
		setSize(ScreenWidth, ScreenHeight);
		setLocationRelativeTo(null); // center
		
		JMenu FileMenu = new JMenu ("File");
        JMenuItem Register = new JMenuItem ("Register");
        FileMenu.add (Register);
        JMenuItem Login = new JMenuItem ("Login");
        FileMenu.add (Login);
        JMenuItem Play = new JMenuItem ("Play Game"); // arraylist? 
        FileMenu.add (Play);
        JMenuItem Score = new JMenuItem ("Scoretable");
        FileMenu.add (Score);
        JMenuItem Exit = new JMenuItem ("Exit");
        FileMenu.add (Exit);
        JMenu HelpMenu = new JMenu ("Help");
        JMenuItem About = new JMenuItem ("About");
		HelpMenu.add(About);
        menuBar=new JMenuBar();
        this.getContentPane().setBackground(new Color(123,50,250));
        
        menuBar.add(FileMenu);
        menuBar.add(HelpMenu);
        //menuBar.add(Background);
        menuBar.setBounds (0, 0, 635, 25);
        setJMenuBar(menuBar);
        

        
        
        Exit.addActionListener (new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				System.exit(0);
			}
		});
        
        Register.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		
        		JFrame panel = new JFrame();
        		panel.setVisible(true);
        		panel.setLayout(null);
        		panel.setTitle("Register Screen");
        		panel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);				
        		panel.setMinimumSize(new Dimension(325, 400));
        		panel.setResizable(false);	
        		panel.setLocationRelativeTo(null);
        		UsernameLabel = new JLabel("Enter a new name");
        		UsernameLabel.setBounds(80, 95, 150, 30); // Adjust the position and size as needed
                panel.add(UsernameLabel);
        		username=new JTextField();
        		username.setLocation(80,120);
        		username.setSize(150, 30);
        		username.setBackground(Color.white);
        		username.setForeground(Color.black);
        		panel.add(username);
        		PasswordLabel = new JLabel("Enter a new password");
                PasswordLabel.setBounds(80, 145, 150, 30); // Adjust the position and size as needed
                panel.add(PasswordLabel);
        		password=new JPasswordField();
        		password.setLocation(80,170);
        		password.setSize(150,30);
        		password.setBackground(Color.white);
        		password.setForeground(Color.black);
        		panel.add(password);
        		RegisterButton = new JButton("Register");	
        		RegisterButton.setBounds(150,300,100, 20);
        		RegisterButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String name = username.getText();
                        String pass = new String(password.getPassword());
                        DataManager dataManager = new DataManager();
                        dataManager.registerUser(name, pass);
                        JOptionPane.showMessageDialog(null, "User Created!");
                        panel.setVisible(false);
                    }
                });
        		panel.add(RegisterButton);
        		
        		
        	}
        	
        });
        
        Login.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		LoginWindow();
        		
        	}
        });
        
        
        Score.addActionListener(new java.awt.event.ActionListener() { //this is where we show Score Table
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		
        		JOptionPane.showMessageDialog(null, "It will be fixed");
        	}
        });
        About.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(java.awt.event.ActionEvent evt) {
        		JOptionPane.showMessageDialog(null, "Orhan Barış Uzel 20200702125");
        	}
        });
        Play.addActionListener (new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				//System.exit(0);
				if(!isLogged) {
					LoginWindow(); // if user tries to press "Play Game" without login
				}else {
					//JOptionPane.showMessageDialog(null, "Orhan Barış Uzel 20200702125");
					 SwingUtilities.invokeLater(() ->{
			            JFrame frame = new JFrame("Jet Fighter");
			            Gameplay = new JetFighter();
			            frame.add(Gameplay);
			            frame.pack();
			            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			            frame.setLocationRelativeTo(null);
			            frame.setVisible(true);
					setVisible(false);
					 });
				}
			}
		});
        	
        
	}
	public void LoginWindow(){
		
    		JFrame panel = new JFrame();
    		panel.setVisible(true);
    		panel.setLayout(null);
    		panel.setTitle("Login Screen");
    		panel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);				
    		panel.setMinimumSize(new Dimension(325, 400));
    		panel.setResizable(false);	
    		panel.setLocationRelativeTo(null);
    		UsernameLabel = new JLabel("Name");
    		UsernameLabel.setBounds(80, 95, 150, 30); // Adjust the position and size as needed
            panel.add(UsernameLabel);
    		username=new JTextField();
    		username.setLocation(80,120);
    		username.setSize(150, 30);
    		username.setBackground(Color.white);
    		username.setForeground(Color.black);
    		panel.add(username);
    		PasswordLabel = new JLabel("Password");
            PasswordLabel.setBounds(80, 145, 150, 30); // Adjust the position and size as needed
            panel.add(PasswordLabel);
    		password=new JPasswordField();
    		password.setLocation(80,170);
    		password.setSize(150,30);
    		password.setBackground(Color.white);
    		password.setForeground(Color.black);
    		panel.add(password);
    		LoginButton = new JButton("Login");	
    		LoginButton.setBounds(150,300,100, 20);
    		LoginButton.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    				isLogged= false;
    				String tempName = username.getText();
    				String pass = new String(password.getPassword());
    				DataManager dataManager = new DataManager();
        			boolean ToF= dataManager.checkUserIsExist(tempName,pass);
        			if(!ToF) {
        				isLogged= true;
        				JOptionPane.showMessageDialog(null,"Login Succesfull ");
        				panel.setVisible(false);
        				
        			}else {
        				JOptionPane.showMessageDialog(null, "Invalid username or password.", "PopUp Window", JOptionPane.ERROR_MESSAGE);
        			}
        			
    			}
    			
    		});
    				
    		panel.add(LoginButton);
    	
    }
}
