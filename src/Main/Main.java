package Main;

import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The Main Class contains the main method to start the application
 * RUNTIME ERROR: Didn't use stage.setTitle() method to set the title of the Main form
 * FUTURE ENHANCEMENT: We can use main method to load data from a saved file/document
 * Javadoc:/Javadoc
 * @author Rui Huang
 */

public class Main extends Application {

    /**
     * Loading and initializing the main form
     * @param stage the Stage the hold the main form
     * @throws IOException occurs when an IO operation fails
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/View/MainForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Main Part Form");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * the entry point of any java program
     * @param args list of commands executed
     */

    public static void main(String[] args) {

        Product p1=new Product(1,"prod1",15.99, 16, 1, 999);
        Product p2=new Product(2,"prod2",25.99, 26, 1, 999);
        Product p3=new Product(3,"prod3",35.99, 36, 1, 999);
        Product p4=new Product(4,"prod4",45.99, 46, 1, 999);
        Inventory.addProduct(p1);
        Inventory.addProduct(p2);
        Inventory.addProduct(p3);
        Inventory.addProduct(p4);
        InHouse g=new InHouse(1,"rim",9.99, 15, 1, 999,1);
        Inventory.addPart(g);
        Inventory.addPart( new Outsourced(2,"door",10.99, 30, 1, 999, "GG"));
        Inventory.addPart( new InHouse(3,"gear",29.99, 50, 1, 999,2));
        Inventory.addPart( new Outsourced(4,"tank",40.99, 10, 1, 999, "Boss"));
        Inventory.addPart( new InHouse(5,"window",99.99, 45, 1, 999,3));
        Inventory.addPart( new Outsourced(6,"mirror",5.99, 40, 1, 999, "Hum"));
        for(Product product:Inventory.getAllProducts()){
            for(Part part:Inventory.getAllParts()){
                product.addAssociatedPart(part);
            }
        }

        launch();
    }
}