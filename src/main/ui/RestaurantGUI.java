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
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private JButton addButton;
    private JButton removeButton;
    private JButton loadButton;
    private JButton saveButton;
    private AddListener addListener;
    private JTextArea info;
    private JTextField name;
    private JTextField type;
    private JTextField location;
    private JSplitPane splitPane;

    // EFFECTS: establishes the components of the GUI
    public RestaurantGUI(JFrame frame) {
        super(new BorderLayout());
        restaurantList = new RestaurantList("RestaurantList");
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        this.frame = frame;
        listModel = new DefaultListModel<>();

        JScrollPane listScrollPane = constructList();
        info = new JTextArea(10,20);
        JScrollPane infoScrollPane = new JScrollPane(info);
        info.setEditable(false);
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listScrollPane, infoScrollPane);

        initializeAddButton();
        removeButton = new JButton(removeString);
        initializeButtons(removeButton, removeString, new RemoveListener());
        loadButton = new JButton(loadString);
        initializeButtons(loadButton, loadString, new LoadListener());
        saveButton = new JButton(saveString);
        initializeButtons(saveButton, saveString, new SaveListener());
        initializeJTextFields(addListener);
        constructPanel(splitPane, addButton);
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

    // MODIFIES: button
    // EFFECTS: initializes the buttons by setting action command and adding action listener
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

    // MODIFIES: this, spiltpane, addButton
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

    // EFFECTS: constructs the left component of the button pane with remove, load, save buttons
    private JPanel constructLeftButtonPane() {
        JPanel leftButtonPane = new JPanel();
        leftButtonPane.setLayout(new BoxLayout(leftButtonPane, BoxLayout.PAGE_AXIS));
        leftButtonPane.add(removeButton);
        leftButtonPane.add(loadButton);
        leftButtonPane.add(saveButton);
        return leftButtonPane;
    }

    // EFFECTS: constructs the part of the button pane with JTextFields
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
    // EFFECTS: enables remove function and prints out the right component of split pane
    //          when the selected value changes
    @Override
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

    // MODIFIES: this
    // EFFECTS: prints out the necessary information that correspond to the name of the restaurant
    //          in the right component of the split pane
    private void updateInfo(String restaurantName) {
        if (restaurantList == null) {
            return;
        }
        for (Restaurant r: restaurantList.viewRestaurantList()) {
            if (r.getName().equals(restaurantName)) {
                splitPane.setRightComponent(new JTextArea("\nName: " +  r.getName() + "\nType: " + r.getType()
                        + "\nLocation: " + r.getLocation() + "\nVisited?: Not yet!"));
            }
        }
    }

    // a listener for the remove button
    class RemoveListener implements ActionListener {

        // MODIFIES: this
        // EFFECTS: removes the selected element from the list and from the panel and plays the button sound
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
            int index = list.getSelectedIndex();
            listModel.remove(index);

            Restaurant r = restaurantList.viewRestaurantList().get(index);
            restaurantList.removeRestaurant(r);


            int size = listModel.getSize();

            if (size == 0) {
                removeButton.setEnabled(false);
                splitPane.setRightComponent(new JTextArea(""));

            } else { //Select an index.
                if (index == listModel.getSize()) {
                    //removed item in last position
                    index--;
                }
                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);

            }
            playSound("data" + File.separator + "button.wav");
        }
    }

    // a listener for the add button
    class AddListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public AddListener(JButton button) {
            this.button = button;
        }

        // MODIFIES: this
        // EFFECTS: adds the elements into the restaurantlist, inserts element into the listmodel, clears the text
        //          field, makes the new item visible and plays the button sound
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

            playSound("data" + File.separator + "button.wav");

        }

        @Override
        // MODIFIES: this
        // EFFECTS: gives notification that there was an insert into the document.
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        @Override
        // MODIFIES: this
        // EFFECTS: Gives notification that a portion of the document has been removed
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        @Override
        // MODIFIES: this
        // EFFECTS: gives notification that an attribute or set of attributes changed.
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        // a helper method for insertUpdate function
        // MODIFIES: this
        // EFFECTS: enables the button
        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        // a helper method for removeUpdate function
        // MODIFIES: this
        // EFFECTS: if text field is empty, enables button and return true
        //          false otherwise
        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    // a listener for the load button
    class LoadListener implements ActionListener {

        // MODIFIES: this
        // EFFECTS: once the button is pressed, loads restaurant list from file and plays the button sound
        public void actionPerformed(ActionEvent e) {
            try {
                restaurantList = jsonReader.read();
                for (Restaurant r : restaurantList.viewRestaurantList()) {
                    listModel.insertElementAt(r.getName(), 0);
                }
                JOptionPane.showMessageDialog(frame, "Data has been loaded");
            } catch (IOException io) {
                System.out.println("Unable to read from file: " + JSON_STORE);
            }
            playSound("data" + File.separator + "button1.wav");
        }
    }

    // a listener for the save button
    class SaveListener implements ActionListener {

        // MODIFIES: this
        // EFFECTS: once the button is pressed, loads restaurant list from file and plays the button sound
        public void actionPerformed(ActionEvent e) {
            try {
                jsonWriter.open();
                jsonWriter.write(restaurantList);
                jsonWriter.close();
                JOptionPane.showMessageDialog(frame, "Data has been saved");
            } catch (IOException io) {
                System.out.println("Unable to save from file: " + JSON_STORE);
            }
            playSound("data" + File.separator + "button1.wav");
        }
    }

    // MODIFIES: this
    // EFFECTS: allows the sound to be played
    public void playSound(String soundName) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
        }
    }

}
