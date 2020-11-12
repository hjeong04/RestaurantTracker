package ui;

import model.Restaurant;
import model.RestaurantList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

// constructs a GUI for the Restaurant App
public class RestaurantGUI extends JPanel implements ListSelectionListener {
    private JList list;
    private DefaultListModel<String> listModel;
    private JFrame frame;

    private static final String JSON_STORE = "./data/restaurantList.json";
    private static final String addString = "Add";
    private static final String removeString = "Remove";
    private static final String loadString = "Load";
    private static final String saveString = "Save";

    private RestaurantList restaurantList;
    private JButton addButton;
    private JButton removeButton;
    private JButton loadButton;
    private JButton saveButton;
    private AddListener addListener;
    private JTextArea info;
    private JTextField name;
    private JTextField type;
    private JTextField location;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: establishes the components of the GUI
    public RestaurantGUI(JFrame frame) {
        super(new BorderLayout());
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        this.frame = frame;
        listModel = new DefaultListModel<>();

        JScrollPane listScrollPane = constructList();
        info = new JTextArea(10,20);
        JScrollPane infoScrollPane = new JScrollPane(info);
        info.setEditable(false);
        JSplitPane splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listScrollPane, infoScrollPane);

        initializeAddButton();
        removeButton = new JButton(removeString);
        initializeButtons(removeButton, removeString, new RemoveListener());
        loadButton = new JButton(loadString);
        initializeButtons(loadButton, loadString, new LoadListener());
        saveButton = new JButton(saveString);
        initializeButtons(saveButton, saveString, new SaveListener());
        initializeJTextFields(addListener);
        constructPanel(splitpane, addButton);
    }

    // MODIFIES: this
    // EFFECTS: creates the list and put it in a scroll pane.
    private JScrollPane constructList() {
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(10);
        return new JScrollPane(list);
    }

    // MODIFIES: this
    // EFFECTS: initializes the add button and addlistener
    private void initializeAddButton() {
        addButton = new JButton(addString);
        addListener = new AddListener(addButton);
        addButton.setActionCommand(addString);
        addButton.addActionListener(addListener);
        addButton.setEnabled(false);
    }

    private void initializeButtons(JButton button, String text, ActionListener actionListener) {
        button.setActionCommand(text);
        button.addActionListener(actionListener);
    }

    // MODIFIES: this
    // EFFECTS: constructs three JTextFields the correspond to name, type, location of restaurant
    private void initializeJTextFields(AddListener addListener) {
        name = new JTextField(20);
        name.addActionListener(addListener);
        name.getDocument().addDocumentListener(addListener);

        type = new JTextField(20);
        type.addActionListener(addListener);
        type.getDocument().addDocumentListener(addListener);

        location = new JTextField(10);
        location.addActionListener(addListener);
        location.getDocument().addDocumentListener(addListener);
    }

    // MODIFIES: this
    // EFFECTS: constructs panel and adds all the necessary components
    private void constructPanel(JSplitPane splitPane, JButton addButton) {
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));

        JPanel leftButtonPane = constructLeftButtonPane();
        buttonPane.add(leftButtonPane);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));

        JPanel panel = constructLabels();

        buttonPane.add(panel);
        buttonPane.add(addButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        add(splitPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
    }

    // a helper method for construct panel
    private JPanel constructLeftButtonPane() {
        JPanel leftButtonPane = new JPanel();
        leftButtonPane.setLayout(new BoxLayout(leftButtonPane, BoxLayout.PAGE_AXIS));
        leftButtonPane.add(removeButton);
        leftButtonPane.add(loadButton);
        leftButtonPane.add(saveButton);
        return leftButtonPane;
    }

    // a helper method for construct panel
    private JPanel constructLabels() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel nameLabel = new JLabel("Name");
        panel.add(nameLabel);
        panel.add(this.name);
        JLabel typeLabel = new JLabel("Type");
        panel.add(typeLabel);
        panel.add(this.type);
        JLabel locationLabel = new JLabel("Location");
        panel.add(locationLabel);
        panel.add(this.location);
        return panel;
    }

    // MODIFIES: this
    // EFFECTS: determines what happens when objects in the list are selected
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
                //No selection, disable fire button.
                removeButton.setEnabled(false);

            } else {
                //Selection, enable the fire button.
                removeButton.setEnabled(true);
                JList list = (JList)e.getSource();
                updateInfo(listModel.get(list.getSelectedIndex()));
            }
        }
    }

    private void updateInfo(String restaurantName) {
        if (restaurantList == null) {
            return;
        }
        for (Restaurant r: restaurantList.viewRestaurantList()) {
            if (r.equals(restaurantName)) {
                info = new JTextArea("\nName: " +  name.getText() + "\nType: " + type.getText() + "\nLocation: "
                        + location.getText() + "\nVisited?: Not yet!");
            }
        }
    }

    // removes the restaurant from the list
    class RemoveListener implements ActionListener {

        // EFFECTS: removes the selected element from the list and from the panel
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
            int index = list.getSelectedIndex();
            listModel.remove(index);

            int size = listModel.getSize();

            if (size == 0) {
                removeButton.setEnabled(false);

            } else { //Select an index.
                if (index == listModel.getSize()) {
                    //removed item in last position
                    index--;
                }

                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);

            }
            playSound("button.wav");
        }
    }

    //This listener is shared by the text field and the add button.
    class AddListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public AddListener(JButton button) {
            this.button = button;
        }

        // EFFECTS: adds the element into the list
        public void actionPerformed(ActionEvent e) {
            Restaurant restaurant = new Restaurant(name.getText(), type.getText(), location.getText());
            restaurantList.addRestaurant(restaurant);

            int index = list.getSelectedIndex(); //get selected index
            if (index == -1) { //no selection, so insert at beginning
                index = 0;
            } else {           //add after the selected item
                index++;
            }

            listModel.insertElementAt(name.getText(), index);

            //Reset the text field.
            RestaurantGUI.this.name.requestFocusInWindow();
            RestaurantGUI.this.name.setText("");
            RestaurantGUI.this.type.requestFocusInWindow();
            RestaurantGUI.this.type.setText("");
            RestaurantGUI.this.location.requestFocusInWindow();
            RestaurantGUI.this.location.setText("");

            //Select the new item and make it visible.
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);

            playSound("button1.wav");
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

        // a helper method for insertUpdate function
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

    // loads restaurant list from file
    class LoadListener implements ActionListener {

        // EFFECTS: once the button is pressed, loads restaurant list from file
        public void actionPerformed(ActionEvent e) {
            try {
                restaurantList = jsonReader.read();
                JOptionPane.showMessageDialog(frame, "Data has been loaded");
            } catch (IOException io) {
                System.out.println("Unable to read from file: " + JSON_STORE);
            }
            playSound("button2.wav");
        }
    }

    // saves restaurant list to file
    class SaveListener implements ActionListener {

        // EFFECTS: once the button is pressed, loads restaurant list from file
        public void actionPerformed(ActionEvent e) {
            try {
                jsonWriter.open();
                jsonWriter.write(restaurantList);
                jsonWriter.close();
                JOptionPane.showMessageDialog(frame, "Data has been saved");
            } catch (IOException io) {
                System.out.println("Unable to save from file: " + JSON_STORE);
            }
            playSound("button3.wav");
        }
    }

    // MODIFIES: this
    // EFFECTS: allows the sound to be played
    public void playSound(String soundName) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
        }
    }

}
