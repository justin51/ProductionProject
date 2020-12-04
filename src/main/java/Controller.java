import product.devices.AudioPlayer;
import product.devices.MonitorType;
import product.devices.MoviePlayer;
import product.devices.MultimediaControl;
import product.devices.Screen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import product.ItemType;
import product.Product;
import product.devices.Widget;

/**
 * The FXML Controlling class. Used to handle GUI events
 *
 * @author Justin Kenney
 */
public class Controller {

  /**
   * A list of available products to be displayed on the Product and Produce Tabs of the GUI
   */
  private final ObservableList<Product> productLine = FXCollections.observableArrayList();

  @FXML
  private Tab productTab;

  @FXML
  private Tab produceTab;

  @FXML
  private Tab productionTab;

  @FXML
  private ChoiceBox<String> cbType;

  @FXML
  private TextField textProduct;

  @FXML
  private TextField textManufacturer;

  @FXML
  private ComboBox<String> cbQuantity;

  @FXML
  private TableView<Product> productView;

  @FXML
  private Label produceStatus;

  @FXML
  private Label productStatus;

  @FXML
  private TableColumn<?, ?> nameCol;

  @FXML
  private TableColumn<?, ?> mfrCol;

  @FXML
  private TableColumn<?, ?> typeCol;

  @FXML
  private ListView<Product> produceView;

  @FXML
  private TextArea productionView;

  @FXML
  private TextField createNameText;

  @FXML
  private TextField createPasswordText;

  @FXML
  private TextField loginUsernameText;

  @FXML
  private TextField loginPasswordText;

  @FXML
  private Label employeeStatus;

  /**
   * A list containing the available ProductionRecords to be displayed in the Production Log Tab of
   * the GUI
   */
  private ArrayList<ProductionRecord> productionLog;

  /**
   * A list of available Employees
   */
  private ArrayList<Employee> employees;

  /**
   * The Employee currently logged in. null if there is not a current user
   */
  private Employee currentEmployee;

  /**
   * Database object used to access the ProductionDB
   */
  private Database productionDB;

  @FXML
  public void initialize() {
    // create a Database object that connects to the ProductionDB
    productionDB = new Database("org.h2.Driver", "jdbc:h2:./resources/ProductionDB");

    // Adds a list of strings (from the ProductTypes enum values) to the choice box on the product tab
    List<String> types = ItemType.stream().map(ItemType::name).sorted()
        .collect(Collectors.toList());
    cbType.getItems().addAll(types);

    // Adds a list of integers (1-10 inclusive) to the combo box on the produce tab
    cbQuantity.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");

    // Allow users to add custom quantity values & select the first CB option to act as the default value
    cbQuantity.setEditable(true);
    cbQuantity.getSelectionModel().selectFirst();

    produceStatus.setTextFill(Color.RED);
    productStatus.setTextFill(Color.RED);

    setupProductLineTable();

    // Update the selection models to display the products in productLine
    productView.setItems(productLine);
    produceView.setItems(productLine);

    // Loads the Products and ProductionRecords stored in the DB
    this.loadProductList();
    this.loadProductionLog();

    // First option will be selected by default
    produceView.getSelectionModel().selectFirst();

    // Loads the Employees from EmployeeData.txt into the list
    this.loadEmployees();

    testMultimedia();
  }

  /**
   * Stores all of the Employees into EmployeeData.txt
   */
  public void saveEmployees() {
    try {
      FileWriter writer = new FileWriter("EmployeeData.txt");

      // for every employee write there details to a new line in the file
      // The password is reversed for storage as extra security.
      for (Employee e : this.employees) {
        writer.write(e.getName() + ","
            + e.getUsername() + "," + e.getEmail() + ","
            + this.reverseString(e.getPassword()) + "\n");
      }

      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Parses comma separated data from EmployeeData.txt to create Employee objects and store them in
   * this.employees
   */
  public void loadEmployees() {
    this.employees = new ArrayList<>();

    try {
      // creates a BufferedReader to read from EmployeeData
      BufferedReader br = new BufferedReader(new FileReader("EmployeeData.txt"));

      while (br.ready()) {
        String line = br.readLine();
        // skip to the next line if there is nothing there or is null
        if (line == null) {
          continue;
        }
        if (line.isEmpty() || line.isBlank()) {
          continue;
        }

        // Create an Employee object from the line of data and add it to the list
        // Comma Separated Values: 1st=name, 2nd=username, 3rd=password, 4th=email
        String[] values = line.split(",");
        Employee employee = new Employee(values[0], values[1], this.reverseString(values[3]),
            values[2]);
        this.employees.add(employee);
      }
    } catch (FileNotFoundException e) {
      this.createEmployeeData();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * This will create an EmployeeData.txt if one does not already exist
   */
  private void createEmployeeData() {
    try {
      File file = new File("EmployeeData.txt");
      if (file.createNewFile()) {
        System.out.println("Created EmployeeData file");
      }
    } catch (IOException e) {
      System.out.println("An error occurred while creating EmployeeData.");
      e.printStackTrace();
    }
  }

  /**
   * Search the list of products to find one with the provided id
   *
   * @param id the products id
   * @return The name of the product. "N/A" if one is not found
   */
  private String getProductNameById(int id) {
    for (Product product : this.productLine) {
      if (product.getId() == id) {
        return product.getName();
      }
    }
    return "N/A";
  }

  /**
   * Recursively reverse the provided string
   *
   * @param string The string to be reversed
   * @return A string in reverse order of the one provided
   */
  private String reverseString(String string) {
    if (string.isEmpty()) {
      return string;
    }
    return reverseString(string.substring(1)) + string.charAt(0);
  }

  /**
   * This will update the Production Log Tab to display all of the ProductionRecords
   */
  void showProduction() {
    // clears the current production log
    this.productionView.clear();

    // write a line to the production log tab for every ProdcutionRecord
    for (ProductionRecord record : this.productionLog) {
      this.productionView.appendText(
          "Prod. Num: " + record.getProductionNum() + " product.Product Name: "
              + getProductNameById(
              record.getProductID()) + " Serial Num: " + record.getSerialNum() + " Date: " + record
              .getProdDate() + "\n");
    }
  }

  /**
   * Loads ProductionRecords from the Database into the productionLog list
   */
  void loadProductionLog() {
    this.productionLog = new ArrayList<>();

    // gets the resultset containing everything in the ProductionRecord Table
    ResultSet productionSet = productionDB.getResultSet("SELECT * FROM Productionrecord");
    try {
      while (productionSet.next()) {
        int productionNum = productionSet.getInt(1);
        int productId = productionSet.getInt(2);
        String serialNum = productionSet.getString(3);
        Date date = new Date(productionSet.getTimestamp(4).getTime());

        // creates a ProductionRecord object and adds it to the list
        ProductionRecord record = new ProductionRecord(productionNum, productId, serialNum, date);
        this.productionLog.add(record);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    // update production log
    showProduction();

    // todo I think this can be handled in Database. I need to cache the result set?
    // close the connection to the database
    productionDB.closeConnection();
  }

  /**
   * Loads Products from the Database into the productLine list
   */
  private void loadProductList() {
    ResultSet productSet = productionDB.getResultSet("SELECT * FROM Product");
    try {
      while (productSet.next()) {
        String mfr = productSet.getString(4);
        String name = productSet.getString(2);
        String type = productSet.getString(3);
        int id = productSet.getInt(1);

        // Create a Widget object to store the product data and add it to the list
        productLine.add(new Widget(name, mfr, ItemType.forName(type), id));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    // close the database connection
    productionDB.closeConnection();
  }

  /**
   * Sets up the table displayed on the Product tab
   */
  private void setupProductLineTable() {
    nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
    mfrCol.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
    typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
  }

  /**
   * Gathers information from the options on the Product Tab to create a new Product object. The
   * Product is then added to the productLine list and the database. Ensures that the fields are
   * filled out before product creation.
   */
  @FXML
  private void addProduct() {
    if (textProduct.getText().isBlank()) {
      productStatus.setText("Make sure NAME is not blank");
      return;
    } else if (textManufacturer.getText().isBlank()) {
      productStatus.setText("Make sure MANUFACTURER is not blank");
      return;
    } else if (cbType.getValue() == null) {
      productStatus.setText("Make sure an item type is selected");
      return;
    }

    String type = cbType.getValue();
    String mfr = textManufacturer.getText();
    String name = textProduct.getText();

    // creates a Widget to store the new Product data and adds it to the list
    Widget widget = new Widget(name, mfr, ItemType.forName(type));
    productLine.add(widget);

    // adds the product to the database
    productionDB.addToProductDB(type, name, mfr);
  }

  /**
   * This will create a traceable audit of the Production Log by saving ProductionRecord and the
   * Employee responsible for producing it. Each production run of a product is saved to
   * ProductionAudit.txt
   *
   * @param product  The Product that was produced
   * @param quantity The amount produced
   */
  private void saveProductionAudit(Product product, int quantity) {
    try {
      FileWriter writer = new FileWriter("ProductionAudit.txt", true);

      // EX:  Justin Kenney: x5 (Name: iPod, Manufacturer: Banana, Type: AM)
      writer.write(this.currentEmployee.getName() + ": x" + quantity + " (" + product.toString()
          .replaceAll("\n", ", ") + ")" + "\n");
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Creates ProductionRecords for the selected product and quantity. the records will be added to
   * the database and the list, and the audit log will be updated
   */
  @FXML
  private void recordProduction() {
    int quantity;
    Product product;

    // handle potential errors
    try {
      quantity = Integer.parseInt(cbQuantity.getValue());
      product = produceView.getSelectionModel().getSelectedItem();
    } catch (NumberFormatException ex) {
      produceStatus.setText("Make sure the quantity is a number");
      return;
    } catch (Exception ex) {
      produceStatus.setText("An error occurred when parsing a quantity or selecting a product");
      return;
    }
    if (quantity < 1) {
      produceStatus.setText("At least 1 product must be created");
      return;
    }

    // List to store the soon to be creted ProductionRecord objects
    ArrayList<ProductionRecord> productionRun = new ArrayList<>();

    // Create a production records [quantity] times and add it to the list and database
    for (int i = 0; i < quantity; i++) {
      ProductionRecord record = new ProductionRecord(product, i);
      productionRun.add(record);
    }
    productionDB.addToProductionDB(productionRun);
    productionLog.addAll(productionRun);
    // update the audit log
    this.saveProductionAudit(product, quantity);
    // refresh gui list
    showProduction();

    produceStatus.setText("");
  }

  /**
   * Enables all Tabs
   */
  public void enable() {
    productTab.setDisable(false);
    produceTab.setDisable(false);
    productionTab.setDisable(false);
  }

  /**
   * Disables all but Tabs except the Employee Tab
   */
  public void disable() {
    productTab.setDisable(true);
    produceTab.setDisable(true);
    productionTab.setDisable(true);
  }

  /**
   * Removes the current employee and disables all but the Employee Tab
   */
  public void logout() {
    this.currentEmployee = null;
    this.disable();
  }

  /**
   * Checks to see if the supplied username and password match the credentials of an employee in the
   * list
   *
   * @param username The Employees account username
   * @param password The Employees account password
   * @return code associated with verification and Employee object of the username and pass 0 if no
   * account was found 1 if an account was found but was not the correct password 2 if the account
   * was found and password matches
   */
  public Pair<Employee, Integer> verifyEmployee(String username, String password) {
    for (Employee e : this.employees) { // for every employee
      if (e.getUsername().equals(username)) { // if the usernames match
        if (e.getPassword().equals(password)) { // if the passwords match
          return new Pair<>(e, 2); // same accoutn and password
        } else {
          return new Pair<>(e, 1); // same username, diff passwords
        }
      }
    }
    return new Pair<>(null, 0); // no account found
  }

  /**
   * Uses data from the username and password textfields to verify a user and switch to their
   * 'account' This will logout the current user
   */
  @FXML
  public void login() {
    String username = loginUsernameText.getText().toLowerCase();
    String password = loginPasswordText.getText();

    // logout the current user
    this.logout();
    loginPasswordText.clear();

    // update the user with a status message as to why the login did not work
    Pair<Employee, Integer> verification = this.verifyEmployee(username, password);
    switch (verification.getValue()) {
      case 0:
        employeeStatus.setText("Username '" + username + "' not found");
        break;
      case 1:
        employeeStatus.setText("Incorrect password supplied for username '" + username + "'");
        break;
      case 2:
        this.loginEmployee(verification.getKey());
        break;
      default:
        employeeStatus.setText("Error verifying login credentials");
        break;
    }
  }

  /**
   * This will login an Employee and update the status text
   *
   * @param employee The Employee to login
   */
  public void loginEmployee(Employee employee) {
    employeeStatus.setText(employee.toString().replaceFirst("\n", "- ").replaceAll("\n", ", "));
    this.currentEmployee = employee;
    this.enable();
  }

  /**
   * Creates an employee object using the data supplied in the gui Employee Tab
   */
  @FXML
  public void createEmployee() {
    String fullName = createNameText.getText();
    String suppliedPassword = createPasswordText.getText();

    // make sure there is at least 2 words
    if (fullName.split(" ").length < 2) {
      employeeStatus.setText("Unable to create account. Please enter your FIRST & LAST name");
      return;
    }

    // create an object from the data supplied in the gui
    Employee employee = new Employee(fullName, suppliedPassword);

    for (Employee e : this.employees) {
      if (e.getUsername().equals(employee.getUsername())) {
        // i want to build a recursive function that will create a unique username instead of not creating an account
        // the function will add a count to the end of the username until a unique name is reached
        // Ex: jkenney2
        employeeStatus.setText("Could not create account. Username '" + employee.getUsername()
            + "' already exists. Try adding a number to the end of your last name");
        return;
      }
    }

    // add the employee to the list, save the employee to the databse, and log them in
    this.employees.add(employee);
    this.saveEmployees();
    this.loginEmployee(employee);

    this.createNameText.clear();
    this.createPasswordText.clear();
  }

  public static void testMultimedia() {
    AudioPlayer newAudioProduct = new AudioPlayer("DP-X1A", "Onkyo",
        "DSD/FLAC/ALAC/WAV/AIFF/MQA/Ogg-Vorbis/MP3/AAC", "M3U/PLS/WPL");
    Screen newScreen = new Screen("720x480", 40, 22);
    MoviePlayer newMovieProduct = new MoviePlayer("DBPOWER MK101", "OracleProduction", newScreen,
        MonitorType.LCD);
    ArrayList<MultimediaControl> productList = new ArrayList<>();
    productList.add(newAudioProduct);
    productList.add(newMovieProduct);
    for (MultimediaControl p : productList) {
      System.out.println(p);
      p.play();
      p.stop();
      p.next();
      p.previous();
    }
  }
}