package lesson23.teamWork;

import lesson23.teamWork.entity.Product;
import lesson23.teamWork.repository.DatabaseService;

import java.io.IOException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws IOException, SQLException {

        // System.out.println(products);
        DatabaseService databaseService = new DatabaseService();

        //create table
        databaseService.CreateManufacturerTable();
        databaseService.CreateProductsTable();


       //databaseService.insertIntoManufacturerTable();
        //databaseService.insertIntoProductTable();

        System.out.println("Find all products");
        System.out.println("\t" + databaseService.findAllProducts());
        System.out.println("Find products by name");
        System.out.println("\t" + databaseService.findProductByName("bread"));
        System.out.println("Find products by findAllProductsByGivenManufacturer");
        System.out.println("\t" + databaseService.findProductByGivenManufacturer("VolFas"));
        System.out.println("Deleting .........");
        databaseService.deleteProduct(3);
        databaseService.deleteByManufacturer("Volfas");

    }


}
