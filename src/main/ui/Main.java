package ui;


import javax.swing.*;

public class Main {

    // referenced ListDemo project from https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
    // EFFECTS: creates and shows GUI
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("RestaurantList");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JComponent newContentPane = new RestaurantGUI(frame);
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
