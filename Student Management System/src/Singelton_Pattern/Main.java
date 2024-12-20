package Singelton_Pattern;

import javax.swing.*;

/**
 *
 * @author ABDO
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Run GUI in Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            GUI gui = new MainGUI(); //we must create object of the interface to be able to use its methods and its implementation
            gui.MainGUI();
        });
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            DBconnection.getInstance().closeConnection();
        }));

    }

}
