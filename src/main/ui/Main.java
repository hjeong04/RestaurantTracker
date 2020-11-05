package ui;


import javax.swing.*;

public class Main {

    // referenced ListDemo project from https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
    // EFFECTS: creates and shows GUI
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("RestaurantList");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JComponent newContentPane = new RestaurantGUI();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        createAndShowGUI();
    }
}
