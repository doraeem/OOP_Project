import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

// enumeration for gender
enum Gender
  { MALE, FEMALE }

// population class
class Population implements Serializable {
   private String name;
   private int age;
   private Gender gender;
   private String phone;
   private String address;

  // constructor
public Population(String name, int age, Gender gender,String phone,String address) 
{
    this.name = name;
    this.age = age;
    this.gender = gender;
    this.phone = phone;
    this.address = address;
  }

  // getters
  public String getName() 
  {
     return name;
  }
  public int getAge() 
  {
     return age;
  }
  public Gender getGender() 
  { 
    return gender;
  }
  public String getPhone()
  {
    return phone;
  }
  public String getAddress()
  {
    return address;
  }


public void add(Population populations) {
}
}

// GUI class
class PopulationGUI extends JFrame implements ActionListener {
   private JTextField nameField;
   private JTextField ageField;
   private JTextField phoneField;
   private JTextField addressField;
   private JRadioButton maleButton;
   private JRadioButton femaleButton;
   private JButton saveButton;
   private JButton viewButton;
   private ArrayList<Population> populations;
  

  // constructor
public PopulationGUI() {
    // create GUI components
    nameField = new JTextField(10);
    ageField = new JTextField(10);
    phoneField = new JTextField(10);
    addressField = new JTextField(10);
    maleButton = new JRadioButton("Male");
    femaleButton = new JRadioButton("Female");
    saveButton = new JButton("Save");
    viewButton = new JButton("View");

    // create gender button group
    ButtonGroup genderGroup = new ButtonGroup();
    genderGroup.add(maleButton);
    genderGroup.add(femaleButton);

    // create panel for input fields
    JPanel inputPanel = new JPanel();
    inputPanel.add(new JLabel("Name:"));
    inputPanel.add(nameField);
    inputPanel.add(new JLabel("Age:"));
    inputPanel.add(ageField);
    inputPanel.add(new JLabel("Phone:"));
    inputPanel.add(phoneField);
    inputPanel.add(new JLabel("Address:"));
    inputPanel.add(addressField);
    inputPanel.add(maleButton);
    inputPanel.add(femaleButton);

    // create panel for buttons
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(saveButton);
    buttonPanel.add(viewButton);

    // set layout and add panels to frame
    setLayout(new BorderLayout());
    add(inputPanel, BorderLayout.NORTH);
    add(buttonPanel, BorderLayout.SOUTH);

    // set action listeners for buttons
    saveButton.addActionListener(this);
    viewButton.addActionListener(this);

    // initialize array list for population
    populations = new ArrayList<Population>();
  }

  // action performed event handler
  public void actionPerformed(ActionEvent event) {
    // save button clicked
    if (event.getSource() == saveButton) {
      // get input fields
      String name = nameField.getText();
      int age = Integer.parseInt(ageField.getText());
      String phone = phoneField.getText();
      String address = addressField.getText();
      Gender gender = maleButton.isSelected() ? Gender.MALE : Gender.FEMALE;

      // create new population object
      Population population = new Population(name, age, gender,phone, address);
        // add population to array list
      populations.add(population);

  // clear input fields
      nameField.setText("");
      ageField.setText("");
      phoneField.setText("");
      addressField.setText("");
      maleButton.setSelected(false);
      femaleButton.setSelected(false);
   }
// view button clicked
    else if (event.getSource() == viewButton) {
  // serialize array list of populations to file
    try {
      FileOutputStream fileOut = new FileOutputStream("population.ser");
      ObjectOutputStream out = new ObjectOutputStream(fileOut);
      out.writeObject(populations);
      out.close();
      fileOut.close();
   } catch (IOException e) {
     e.printStackTrace();
  }

  // deserialize array list of populayions from file
   try {
     FileInputStream fileIn = new FileInputStream("population.ser");
     ObjectInputStream in = new ObjectInputStream(fileIn);
     populations = (ArrayList<Population>) in.readObject();
     in.close();
     fileIn.close();
   } catch (IOException e) {
     e.printStackTrace();
   } catch (ClassNotFoundException e) {
     e.printStackTrace();
   }

  // create data for JTable
    String[][] data = new String[populations.size()][5];
    for (int i = 0; i < populations.size(); i++) {
       Population p = populations.get(i);
       data[i][0] = p.getName();
       data[i][1] = p.getPhone();
       data[i][2] = p.getAddress();
       data[i][3] = Integer.toString(p.getAge());
       data[i][4] = p.getGender() == Gender.MALE ? "Male" : "Female";
    }

  // create column names for JTable
    String[] columnNames = { "Name", "Age", "Gender","Phone","Address"};

  // create JTable with data and column names
    JTable table = new JTable(data, columnNames);

  // add JTable to scroll pane
    JScrollPane scrollPane = new JScrollPane(table);

  // add scroll pane to frame and show
    add(scrollPane, BorderLayout.CENTER);
    setVisible(true);
  }
 }

public static void main(String[] args) {
    PopulationGUI gui = new PopulationGUI();
    gui.setTitle("Census Paper");
    gui.setSize(600, 500);
    gui.setLocationRelativeTo(null);
    gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    gui.setVisible(true);
  }
}