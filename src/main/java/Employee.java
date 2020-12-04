import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that contains the information of an employee
 *
 * @author Justin Kenney
 */
public class Employee {

  /**
   * The name of this employee
   */
  private String name;
  /**
   * This employees username
   */
  private String username;
  /**
   * This employees password
   */
  private String password;
  /**
   * This employees email
   */
  private String email;

  /**
   * Constructor to create an Employee object
   *
   * @param name     employees name
   * @param username employees username
   * @param password employees password
   * @param email    employees email
   */
  public Employee(String name, String username, String password, String email) {
    this.name = name;
    this.username = username;
    this.password = password;
    this.email = email;
  }

  /**
   * Constructor to create an Employee object The username and email will be generated.
   *
   * @param name     employees name
   * @param password employees password
   */
  public Employee(String name, String password) {
    this.name = name;
    if (this.checkName(name)) {
      this.setUsername(name);
      this.setEmail(name);
    } else {
      this.username = "default";
      this.email = "user@oracleacademy.Test";
    }

    if (this.isValidPassword(password)) {
      this.password = password;
    } else {
      this.password = "pw";
    }
  }

  /**
   * This will create and set an email address for the employee based on their full name
   *
   * @param name employees full name
   */
  public void setEmail(String name) {
    String[] names = name.split(" ");
    this.email = (names[0] + "." + names[1]).toLowerCase() + "@oracleacademy.Test";
  }

  /**
   * This will create and set a username for this employee based on their full name
   *
   * @param name
   */
  public void setUsername(String name) {
    String[] names = name.split(" ");
    this.username = (names[0].charAt(0) + names[1]).toLowerCase();
  }

  /**
   * Checks if the name contains spaces
   *
   * @param name full name of the employee
   * @return true if name contains a space
   */
  boolean checkName(String name) {
    return name.contains(" ");
  }

  /**
   * Checks if the supplied password is valid using regex.
   *
   * @param password the password to validate
   * @return true if the password contains 1 uppercase, 1 lowercase, and 1 special character
   */
  boolean isValidPassword(String password) {
    if (password.isEmpty()) {
      return false;
    }
    // for every string in the array, test the password against this string of regex
    // return false if any match fails
    for (String regex : new String[]{".*[A-Z].*", ".*[a-z].*", ".*[~!@#$%^&*?_+].*"}) {
      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(password);
      if (!matcher.matches()) {
        System.out.println("not a valid password : " + regex);
        return false;
      }
    }
    return true;
  }

  /**
   * converts the employees data into a string of text
   *
   * @return string of employee data
   */
  @Override
  public String toString() {
    return "Employee Details\nName: " + name + "\nUsername: " + username + "\nEmail: " + email
        + "\nInitial Password: " + password;
  }

  /**
   * gets the employees name
   *
   * @return employee name
   */
  public String getName() {
    return name;
  }

  /**
   * gets the employees username
   *
   * @return employee username
   */
  public String getUsername() {
    return username;
  }

  /**
   * gets the employees password
   *
   * @return employee password
   */
  public String getPassword() {
    return password;
  }

  /**
   * gets the employees email
   *
   * @return employee email
   */
  public String getEmail() {
    return email;
  }
}
