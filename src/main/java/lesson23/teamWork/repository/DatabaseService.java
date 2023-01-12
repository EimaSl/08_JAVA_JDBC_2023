package lesson23.teamWork.repository;

import com.google.gson.Gson;
import lesson23.teamWork.entity.Product;

import lesson23.teamWork.service.GeneratePDF;
import lesson23.teamWork.service.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static lesson23.teamWork.utils.DatabaseUtils.dbConnection;

public class DatabaseService {


    private static final String SELECT_FROM_PRODUCTS = "SELECT * FROM products";
    private static final String SELECT_BY_NAME = "SELECT * FROM products WHERE name = '%s' ";
    private static final String SELECT_BY_GIVEN_MANUFACTURER = "SELECT * FROM products WHERE manufacturer = '%s';";
    public static final String DELETE_PRODUCTS_WHERE_ID = "DELETE FROM products WHERE manufacturer = '%d' ";
    public static final String DELETE_FROM_MANUFACTURERS_WHERE_ID = "DELETE FROM products WHERE name = '%s' ";
    public static final String DELETE_MANUFACTURE_WITH_PRODUCTS_WHERE_MANUFACTURE = "DELETE manufacturers, products from manufacturers" +
            " right  join products on products.manufacturer = manufacturers.name " +
            " where products.manufacturer = '%s'";
    public static final String UPDATE_PRODUCTS_WHERE_ID_D = "UPDATE products SET name = '%s', country = '%s', price= %d, manufacturer = '%s' where id= %d";
    public static final String UPDATE_MANUFACTURER_ID = "UPDATE manufacturers SET name ='%s', countryManufacturer = '%s', numberOfEmployees = %d where id=%d;";
    public static final String RETRIVE_ALL_DATA_FOR_DB = "select a.name,price,country,manufacturer,numberOfEmployees,manufacturerAddress from products as a inner join manufacturers as b on a.id=b.id;";


    private static List<Product> constructProductsList(ResultSet resultSet) throws SQLException {
        List<Product> products = new ArrayList<>();
        while (resultSet.next()) {
            Product product = new Product();
            product.setName_product(resultSet.getString("name"));
            product.setCountry_product(resultSet.getString("country"));
            product.setPrice_product(resultSet.getInt("price"));
            product.setManufacturer(resultSet.getString("manufacturer"));
            products.add(product);
        }
        return products;
    }

    public void CreateManufacturerTable() {
        String sql = "CREATE TABLE IF NOT EXISTS manufacturers " +
                "(id INT not NULL AUTO_INCREMENT, " +
                "name VARCHAR (60) NOT NULL," +
                "countryManufacturer VARCHAR(60) NOT NULL," +
                "numberOfEmployees INT NOT NULL," +
                "manufacturerAddress VARCHAR(80) NOT NULL, " +
                " PRIMARY KEY (id))";
        try {
            Statement statement = dbConnection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void CreateProductsTable() {
        String sql = "CREATE TABLE if not exists products " +
                "(id INTEGER not NULL AUTO_INCREMENT, " +
                "name VARCHAR(60) NOT NULL," +
                "country VARCHAR(60) NOT NULL," +
                " price INT NOT NULL, " +
                " manufacturer VARCHAR(60) NOT NULL, " +
                "PRIMARY KEY (id)," +
                "FOREIGN KEY(id) REFERENCES manufacturers(id))";
        try {
            Statement statement = dbConnection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void insertIntoProductTable() throws IOException, SQLException {

        Statement statement = dbConnection.createStatement();
        JsonReader jsonReader = new JsonReader();
        List<Product> products = jsonReader.convertJsonToList("src/main/resources/jsonTemwork.json");

        for (Product product : products) {
            String name = product.getName_product();
            String country = product.getCountry_product();
            int price = product.getPrice_product();
            String manufacture = product.getManufacturer();

            String sql = "INSERT INTO products (name,country,price, manufacturer) VALUES ('%s','%s','%d','%s');";
            statement.executeUpdate(String.format(sql, name, country, price, manufacture));

        }
    }

    public void insertIntoManufacturerTable() throws SQLException, IOException {
        Statement statement = dbConnection.createStatement();
        JsonReader jsonReader = new JsonReader();
        List<Product> products = jsonReader.convertJsonToList("src/main/resources/jsonTemwork.json");

        for (Product product : products) {
            String name = product.getManufacturer();
            String country = product.getCountry_product();
            int numberOfEmployees = product.getManufacturer_emp_count();
            String manufacturerAddress = product.getManufacturer_address();

            String sql = "INSERT INTO manufacturers (name,countryManufacturer,numberOfEmployees,manufacturerAddress) VALUES ('%s','%s','%d','%s');";
            statement.executeUpdate(String.format(sql, name, country, numberOfEmployees, manufacturerAddress));

        }
    }

    public List<Product> findAllProducts() {
        Statement statement = null;
        List<Product> products;
        try {
            statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_FROM_PRODUCTS);

            products = constructProductsList(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    public List<Product> findProductByName(String name) throws SQLException {
        Statement statement = null;
        List<Product> productsByName = new ArrayList<>();
        try {
            statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format(SELECT_BY_NAME, name));
            productsByName = constructProductsList(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productsByName;
    }

    // - findAllProductsByGivenManufacturer,
    public List<Product> findProductByGivenManufacturer(String name) throws SQLException {
        Statement statement = null;
        List<Product> productsByName = new ArrayList<>();
        try {
            statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format(SELECT_BY_GIVEN_MANUFACTURER, name));
            productsByName = constructProductsList(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productsByName;
    }

    // - deleteProduct,
    public void deleteProduct(Integer id) {
        Statement statement;
        try {

            dbConnection.createStatement().executeUpdate(DELETE_PRODUCTS_WHERE_ID, id);

            System.out.println("Product deleted with id =" + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // - deleteManufacturer,
    public void deleteByManufacturer(String name) {
        Statement statement;
        try {
            dbConnection.createStatement().executeUpdate(String.format(DELETE_FROM_MANUFACTURERS_WHERE_ID, name));
            System.out.println("Manufacturer deleted id =" + name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // - deleteManufacturer (note: istrinant gamintoja reiktu istrinti ir jam priklausancius produktus).
    public void deleteManufacturerIncludedRelatedProducts(String manufacture) {
        Statement statement;
        try {
            dbConnection.createStatement().executeUpdate(String.format(DELETE_MANUFACTURE_WITH_PRODUCTS_WHERE_MANUFACTURE, manufacture));
            System.out.println("Manufacturer deleted including products, Manufacture name: " + manufacture);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // - drop Tables
    public void dropTables() {
        Statement statement;
        try {
            dbConnection.createStatement().executeUpdate(String.format("DROP TABLE products"));
            dbConnection.createStatement().executeUpdate(String.format("DROP TABLE manufacturers"));
            System.out.println("SQL clean up");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // - update Product
    public void updateProductsById(String name, String country, Integer price, String manufacturer, Integer id) {
        Statement statement;
        try {
            dbConnection.createStatement().executeUpdate(String.format(UPDATE_PRODUCTS_WHERE_ID_D, name, country, price, manufacturer, id));
            System.out.println("Product with id = " + id + " been updated");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // - update Manufacturer
    public void updateManufacturerById(String name, String countryManufacture, Integer numberOfEmploees, Integer id) {
        Statement statement;
        try {
            dbConnection.createStatement().executeUpdate(String.format(UPDATE_MANUFACTURER_ID, name, countryManufacture, numberOfEmploees, id));
            System.out.println("Manufacture with id = " + id + " been updated");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // - create newJsonFileFromSql
    public ResultSet RetrieveData() throws SQLException {
        Statement statement;
        ResultSet rs = dbConnection.createStatement().executeQuery(RETRIVE_ALL_DATA_FOR_DB);
        return rs;
    }

    public List<Product> writeNewJsonFile() throws SQLException {

        ResultSet resultSet = RetrieveData();
        List<Product> list = new ArrayList<>();
        while (resultSet.next()) {

            Product product1 = new Product();

            product1.setName_product(resultSet.getString("name"));
            product1.setPrice_product(resultSet.getInt("price"));
            product1.setCountry_product(resultSet.getString("country"));
            product1.setManufacturer(resultSet.getString("manufacturer"));
            product1.setManufacturer_emp_count(resultSet.getInt("numberOfEmployees"));
            product1.setManufacturer_address(resultSet.getString("manufacturerAddress"));
            list.add(product1);
        }


        String json = new Gson().toJson(list);
        try {
            FileWriter file = new FileWriter("src/main/resources/output.json");
            file.write(json);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void createNewPdf() throws MalformedURLException, SQLException, FileNotFoundException {
        GeneratePDF generatePDF = new GeneratePDF();
        generatePDF.CreateMPdf();
    }




}

















