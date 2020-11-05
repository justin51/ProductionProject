import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReadDatabase {

    public static void main(String[] args) {

        final String JDBC_DRIVER = "org.h2.Driver";
        final String DB_URL = "jdbc:h2:./resources/ProductionDB";

        //  Database credentials
        final String USER = "";
        final String PASS = "";
        Connection conn;
        Statement stmt;

        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 2: Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 3: Execute a query
            stmt = conn.createStatement();

            String sql = "SELECT * FROM JOBS";

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println(rs.getString(1)+", "+rs.getString(2)+", "+rs.getString(3)+", "+rs.getString(4));
            }

            // STEP 4: Clean-up environment
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}