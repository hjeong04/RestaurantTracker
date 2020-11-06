package ui;

import model.Restaurant;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/* ListDemo.java requires no other files. */
public class RestaurantGUI extends JPanel implements ListSelectionListener {
    private JList list;
    private DefaultListModel<Restaurant> listModel;

    private static final String addString = "Add";
    private static final String removeString = "Remove";
    private JButton removeButton;
    private JTextField name;
    private JTextField type;
    private JTextField location;

    public RestaurantGUI() {
        super(new BorderLayout());

        listModel = new DefaultListModel<>();

        //Create the list and put it in a scroll pane.
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);

        JButton addButton = new JButton(addString);
        AddListener addListener = new AddListener(addButton);
        addButton.setActionCommand(addString);
        addButton.addActionListener(addListener);
        addButton.setEnabled(false);

        removeButton = new JButton(removeString);
        removeButton.setActionCommand(removeString);
        removeButton.addActionListener(new RemoveListener());

        name = new JTextField(10);
        name.addActionListener(addListener);
        name.getDocument().addDocumentListener(addListener);
//        //String name = listModel.getElementAt(
//                list.getSelectedIndex()).toString();

        type = new JTextField(10);
        type.addActionListener(addListener);
        type.getDocument().addDocumentListener(addListener);
//        String type = listModel.getElementAt(
//                list.getSelectedIndex()).toString();

        location = new JTextField(10);
        location.addActionListener(addListener);
        location.getDocument().addDocumentListener(addListener);
//        String location = listModel.getElementAt(
//                list.getSelectedIndex()).toString();
        constructPanel(listScrollPane, addButton);

    }

    //Create a panel that uses BoxLayout.

    private void constructPanel(JScrollPane listScrollPane, JButton addButton) {
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.add(removeButton);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        JLabel nameLabel = new JLabel("Name");
        buttonPane.add(nameLabel);
        buttonPane.add(this.name);
        JLabel typeLabel = new JLabel("Type");
        buttonPane.add(typeLabel);
        buttonPane.add(this.type);
        JLabel locationLabel = new JLabel("Location");
        buttonPane.add(locationLabel);
        buttonPane.add(this.location);
        buttonPane.add(addButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
    }

    class RemoveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
            int index = list.getSelectedIndex();
            listModel.remove(index);

            int size = listModel.getSize();

            if (size == 0) { //Nobody's left, disable firing.
                removeButton.setEnabled(false);

            } else { //Select an index.
                if (index == listModel.getSize()) {
                    //removed item in last position
                    index--;
                }

                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }
        }
    }

    //This listener is shared by the text field and the hire button.
    class AddListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public AddListener(JButton button) {
            this.button = button;
        }

        //Required by ActionListener.
        public void actionPerformed(ActionEvent e) {
            String name = RestaurantGUI.this.name.getText();

            //User didn't type in a unique name...
            if (name.equals("") || alreadyInList(name)) {
                Toolkit.getDefaultToolkit().beep();
                RestaurantGUI.this.name.requestFocusInWindow();
                RestaurantGUI.this.name.selectAll();
                return;
            }

            int index = list.getSelectedIndex(); //get selected index
            if (index == -1) { //no selection, so insert at beginning
                index = 0;
            } else {           //add after the selected item
                index++;
            }

            listModel.insertElementAt(createRestaurant(), index);
            //If we just wanted to add to the end, we'd do this:
            //listModel.addElement(employeeName.getText());

            //Reset the text field.
            RestaurantGUI.this.name.requestFocusInWindow();
            RestaurantGUI.this.name.setText("");

            //Select the new item and make it visible.
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }

        private Restaurant createRestaurant() {
            return new Restaurant(name.getText(), type.getText(), location.getText());
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }


        //This method tests for string equality. You could certainly
        //get more sophisticated about the algorithm.  For example,
        //you might want to ignore white space and capitalization.
        protected boolean alreadyInList(String name) {
            return listModel.contains(name);
        }

        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    //This method is required by ListSelectionListener.
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
                //No selection, disable fire button.
                removeButton.setEnabled(false);

            } else {
                //Selection, enable the fire button.
                removeButton.setEnabled(true);
            }
        }
    }
}