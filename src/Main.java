import javax.swing.*;

public class Main {
    public static void main(String[] args) {
    	 SwingUtilities.invokeLater(() -> {
            MainMenu myLayout = new MainMenu();
            myLayout.setTitle("CSE 212 Jet Fighter");
            myLayout.setLocationRelativeTo(null);
            myLayout.setVisible(true);

    
    });
    }
}