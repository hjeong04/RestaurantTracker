package ui;


import ui.listener.AddListener;
import ui.listener.RemoveListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

// referenced ListDemo project from https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
// the necessary elements in creating a GUI
public class RestaurantGUI extends JPanel implements ListSelectionListener {

    private JList list;
    private DefaultListModel listModel;

    private static final String addString = "Add";
    private static final String removeString = "Remove";
    private JButton removeButton;
    private JButton addButton;
    private JTextField restaurantName;
    private JTextField foodType;
    private JTextField location;
    private AddListener addListener;

    // EFFECTS: initializes the RestaurantGUI class
    public RestaurantGUI() {
        super(new BorderLayout());

        listModel = new DefaultListModel();
        initializeList();
        initializeAddButton();
        initializeRemoveButton();
        initializeJTextField();
        initializePanel();
    }

    // MODIFIES: this
    // EFFECTS: initializes the list
    private void initializeList() {
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);
    }

    // MODIFIES: this
    // EFFECTS: initializes the add button
    private void initializeAddButton() {
        addButton = new JButton(addString);
        addListener = new AddListener(addButton);
        addButton.setActionCommand(addString);
        addButton.addActionListener(addListener);
        addButton.setEnabled(false);
    }


    // MODIFIES: this
    // EFFECTS: initializes the remove button
    private void initializeRemoveButton() {
        removeButton = new JButton(removeString);
        removeButton.setActionCommand(removeString);
        removeButton.addActionListener(new RemoveListener());
    }

    // MODIFIES: this
    // EFFECTS: initializes all three JTextFields
    private void initializeJTextField() {
        restaurantName = new JTextField(10);
        restaurantName.addActionListener(addListener);
        restaurantName.getDocument().addDocumentListener(addListener);
        String name = listModel.getElementAt(list.getSelectedIndex()).toString();

        foodType = new JTextField(10);
        foodType.addActionListener(addListener);
        foodType.getDocument().addDocumentListener(addListener);
        //String type = listModel.getElementAt(list.getSelectedIndex()).toString();

        location = new JTextField(10);
        location.addActionListener(addListener);
        location.getDocument().addDocumentListener(addListener);
        //String location = listModel.getElementAt(list.getSelectedIndex()).toString();
    }

    // MODIFIES: this
    // EFFECTS: constructs a panel that uses BoxLayout
    private void initializePanel() {
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.add(removeButton);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(restaurantName);
        buttonPane.add(foodType);
        buttonPane.add(location);
        buttonPane.add(addButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {

    }
}
