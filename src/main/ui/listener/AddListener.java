package ui.listener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//
public class AddListener implements ActionListener, DocumentListener {
    private boolean alreadyEnabled = false;
    private JButton button;

    // EFFECTS:
    public AddListener(JButton button) {
        this.button = button;
    }

    // MODIFIES:
    // EFFECTS:
    @Override
    public void actionPerformed(ActionEvent e) {
        // stub
    }

    @Override
    public void insertUpdate(DocumentEvent e) {

    }

    @Override
    public void removeUpdate(DocumentEvent e) {

    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }
}
