import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Controller {

    private ObservableList<Product> productLine = FXCollections.observableArrayList();

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
    public void initialize() {
        // Adds a list of strings (from the ProductTypes enum values) to the choice box on the product tab
        List<String> types = ItemType.stream().map(ItemType::name).sorted().collect(Collectors.toList());
        cbType.getItems().addAll(types);

        // Adds a list of integers (1-10 inclusive) to the combo box on the produce tab
        cbQuantity.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");

        // Allow users to add custom values, and select the first CB option to act as the default value
        cbQuantity.setEditable(true);
        cbQuantity.getSelectionModel().selectFirst();

        nameCol.setCellValueFactory(new PropertyValueFactory("name"));
        mfrCol.setCellValueFactory(new PropertyValueFactory("manufacturer"));
        typeCol.setCellValueFactory(new PropertyValueFactory("type"));

        //        productLine.addAll(getDBProducts());

        productView.setItems(productLine);
        produceView.setItems(productLine);

        testMultimedia();
    }

    private Product[] getDBProducts() {
        // connect to db and get products
        return null;
    }

    @FXML
    private void addProduct(ActionEvent event) {

        String sql = "Insert Into Product(type, manufacturer, name) VALUES (?, ?, ?)";

        // Database type and location
        final String JDBC_DRIVER = "org.h2.Driver";
        final String DB_URL = "jdbc:h2:./resources/ProductionDB";

        //  Database credentials
        final String USER = "";
        final String PASS = "";

        Connection connection;

        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            //STEP 2: Open a connection
            connection = DriverManager.getConnection(DB_URL, USER, PASS);

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            String type = cbType.getValue();
            String manu = textManufacturer.getText();
            String name = textProduct.getText();

//            preparedStatement.setString(1, type);
//            preparedStatement.setString(2, manu);
//            preparedStatement.setString(3, name);
//            preparedStatement.executeUpdate();

//            System.out.println("testing: "+type+" : "+ItemType.forName(type));
            productLine.add(new Widget(name, manu, ItemType.forName(type)));

            DBTablePrinter.printTable(connection, "Product");

            preparedStatement.close();
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        } catch (ClassNotFoundException exception2) {
            exception2.printStackTrace();
        }
    }

    @FXML
    private void recordProduction(ActionEvent e) {
        int quantity = Integer.valueOf(cbQuantity.getValue());
        Product product = produceView.getSelectionModel().getSelectedItem();

        for(int i = 0;i < quantity;i++) {
            productionView.appendText(new ProductionRecord(product, i).toString() + "\n");
        }
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