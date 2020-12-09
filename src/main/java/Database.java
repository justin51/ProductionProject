import java.sql.*;
import java.util.ArrayList;
import utils.DBTablePrinter;

/**
 * Class that contains database information and allows access to the database
 *
 * @author Justin Kenney
 */
public class Database {

  // Database type and location
  /**
   * name of the databse driver
   */
  private String JDBC_DRIVER;
  /**
   * name of the database url
   */
  private String DB_URL;

  //  Database credentials
  /**
   * username to the database
   */
  private String username;
  /**
   * password to the database
   */
  private String password;

  /**
   * the Connection object used to access the database
   */
  private Connection connection;
  /**
   * the statement that holds the sql query and is sent to the database
   */
  private PreparedStatement preparedStatement;

  /**
   * Constructor for creating a Database object
   *
   * @param driver name of the database driver
   * @param url    database location
   */
  public Database(String driver, String url) {
    this.JDBC_DRIVER = driver;
    this.DB_URL = url;
    this.username = "";
    this.password = "";
  }

  /**
   * Constructor for creating a database object
   *
   * @param driver   name of the database driver
   * @param url      location of the database
   * @param username username fo the database
   * @param password password for the database
   */
  public Database(String driver, String url, String username, String password) {
    this.JDBC_DRIVER = driver;
    this.DB_URL = url;
    this.username = username;
    this.password = password;
  }

  /**
   * Opens a connection to the database using the username and password
   */
  private void openConnection() {
    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      //STEP 2: Open a connection
      connection = DriverManager.getConnection(DB_URL, this.username, this.password);
    } catch (SQLException | ClassNotFoundException exception) {
      exception.printStackTrace();
    }
  }

  /**
   * closes the connection to the database
   */
  public void closeConnection() {
    if(preparedStatement != null) {
      try {
        preparedStatement.close();
      } catch (SQLException exception) {
        exception.printStackTrace();
      }
    }
    if(connection !=  null) {
      try {
        connection.close();
      } catch (SQLException exception) {
        exception.printStackTrace();
      }
    }
  }

  /**
   * This method will execute a supplied sql statement and return the resultset
   *
   * @param sql sql statement to be executed
   * @return ResultSet of the executed query
   */
  public ResultSet getResultSet(String sql) throws SQLException {
    openConnection();
    preparedStatement = connection.prepareStatement(sql);
    return preparedStatement.executeQuery();
  }

  /**
   * Add a product to the Product Table in the Database
   *
   * @param type         product type
   * @param name         product name
   * @param manufacturer product manufacturer
   */
  public void addToProductDB(String type, String name, String manufacturer) {
    openConnection();
    try {

      String sql = "Insert Into PRODUCT(type, name, manufacturer) VALUES (?, ?, ?)";
      preparedStatement = connection.prepareStatement(sql);

      preparedStatement.setString(1, type);
      preparedStatement.setString(2, name);
      preparedStatement.setString(3, manufacturer);
      preparedStatement.executeUpdate();

      DBTablePrinter.printTable(connection, "PRODUCT");
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      closeConnection();
    }
  }

  /**
   * Adds a list of ProductionRecords to the database
   *
   * @param records list of records to be added
   */
  void addToProductionDB(ArrayList<ProductionRecord> records) {
    openConnection();
    try {

      String sql = "Insert Into PRODUCTIONRECORD(PRODUCTION_NUM, PRODUCT_ID, SERIAL_NUM, DATE_PRODUCED) VALUES (?, ?, ?, ?)";
      preparedStatement = connection.prepareStatement(sql);

      // for each record prepare a statement to insert the record into the db and execute it
      for (ProductionRecord rec : records) {
        preparedStatement.setInt(1, rec.getProductionNum());
        preparedStatement.setInt(2, rec.getProductID());
        preparedStatement.setString(3, rec.getSerialNum());
        preparedStatement.setTimestamp(4, new Timestamp(rec.getProdDate().getTime()));
        preparedStatement.executeUpdate();
      }

      DBTablePrinter.printTable(connection, "PRODUCT");
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      closeConnection();
    }
  }

  void executeSQLStatement(String sql) {
    openConnection();
    try {
      preparedStatement = connection.prepareStatement(sql);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      this.closeConnection();
    }
  }

  /**
   * gets the database driver
   *
   * @return database driver
   */
  public String getDriver() {
    return JDBC_DRIVER;
  }

  /**
   * Sets the database driver
   *
   * @param JDBC_DRIVER database driver
   */
  public void setDriver(String JDBC_DRIVER) {
    this.JDBC_DRIVER = JDBC_DRIVER;
  }

  /**
   * gets the database location
   *
   * @return database location
   */
  public String getURL() {
    return DB_URL;
  }

  /**
   * sets the database location
   *
   * @param DB_URL database location
   */
  public void setURL(String DB_URL) {
    this.DB_URL = DB_URL;
  }

  /**
   * gets the database username
   *
   * @return database username
   */
  public String getUsername() {
    return username;
  }

  /**
   * sets the database username
   *
   * @param username username to be set
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * gets the database password
   *
   * @return database password
   */
  public String getPassword() {
    return password;
  }

  /**
   * sets the database password
   *
   * @param password database password to be set
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * gets the database connection
   *
   * @return database connection
   */
  public Connection getConnection() {
    return connection;
  }

  /**
   * sets the connection of the database
   *
   * @param connection the connection to be set
   */
  public void setConnection(Connection connection) {
    this.connection = connection;
  }

  /**
   * gets the prepared statement
   *
   * @return the prepared statement
   */
  public PreparedStatement getPreparedStatement() {
    return preparedStatement;
  }

  /**
   * Sets the prepared statement
   *
   * @param preparedStatement statement to be set
   */
  public void setPreparedStatement(PreparedStatement preparedStatement) {
    this.preparedStatement = preparedStatement;
  }
}
