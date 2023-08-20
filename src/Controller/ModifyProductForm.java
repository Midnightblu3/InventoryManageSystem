package Controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller for Modify Product Form
 * RUNTIME ERROR: Didn't initialize associatedPart using FXCollection.obserableArrayList() method, when add button was click, null point error happened for the associatedPart
 * FUTURE ENHANCEMENT: We can add a "remove all parts" button to clear the Associated Parts List
 * @author Rui Huang
 */

public class ModifyProductForm implements Initializable {
    public TextField ModProdIdTxf;
    public TextField ModProdNameTxf;
    public TextField ModProdInvTxf;
    public TextField ModProdPriceTxf;
    public TextField ModProdMaxTxf;
    public TextField ModProdMinTxf;
    public TextField ModProdSearchTxf;
    public TableView<Part> ModProdPartTable;
    public TableColumn<Part,Integer> ModProdPartIdCol;
    public TableColumn<Part,String> ModProdPartNameCol;
    public TableColumn<Part,Integer> ModProdInvCol;
    public TableColumn<Part,Double> ModProdPriceCol;
    public Button ModProdAddBtn;
    public TableView<Part> ModProdAssoPartTable;
    public TableColumn<Part,Integer> ModProdAssoPartIdCol;
    public TableColumn<Part,String> ModProdAssoPartNameCol;
    public TableColumn<Part,Integer> ModProdAssoInvCol;
    public TableColumn<Part,Double> ModProdAssoPriceCol;
    public Button ModProdRemoveBtn;
    public Button ModProdSaveBtn;
    public Button ModProdCancelBtn;
    public Label ErrorText;
    private Stage stage;
    private Parent parent;
    private Product product;
    private ObservableList<Part> associatedPart;

    /**
     * Add button Event handler that add parts to the associated part list
     * @param actionEvent when button is clicked
     */

    public void OnModProdAddBtn(ActionEvent actionEvent) {
        Part part=ModProdPartTable.getSelectionModel().getSelectedItem();
        if(part!=null) {
            this.associatedPart.add(part);
        }else{
            ErrorText.setText("No Parts selected");
        }
    }

    /**
     * removed parts from associated part list
     * @param actionEvent when remove button is clicked
     */

    public void OnModProdRemoveBtn(ActionEvent actionEvent) {
        Alert alert= new Alert(Alert.AlertType.CONFIRMATION,"Are you sure?");
        Optional<ButtonType> result=alert.showAndWait();
        if(result.isPresent() && result.get()==ButtonType.OK) {
            associatedPart.remove(ModProdAssoPartTable.getSelectionModel().getSelectedItem());
        }

    }
    /**
     * Save the product as a new product and update the product to the inventory with the data user entered and the list of parts added to the product
     * @param actionEvent button is clicked
     * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations.
     */

    public void OnModProdSaveBtn(ActionEvent actionEvent) throws IOException {
        String name;
        int stock=0;
        int min=0;
        int max=0;
        double price=0;
        boolean error=false;
        String message="Exception: ";

        name = ModProdNameTxf.getText();
        if (name.isEmpty()) {
            error=true;
            message = message +"No data in Name field\n";
        }
        try {
            stock = Integer.parseInt(ModProdInvTxf.getText());
        }catch (NumberFormatException e){
            error=true;
            message=message+"Inv is not an Integer\n";
        }

        try {
            min = Integer.parseInt(ModProdMinTxf.getText());
        }catch (Exception e){
            error=true;
            message=message+"Min is not an Integer\n";
        }
        try {
            max = Integer.parseInt(ModProdMaxTxf.getText());
        }catch (Exception e){
            error=true;
            message=message+"Max is not an Integer\n";
        }
        try {
            price = Double.parseDouble(ModProdPriceTxf.getText());
        }catch (Exception e){
            error=true;
            message=message+"Price is not a double\n";
        }
        if(min>max){
            error=true;
            message=message+"Max must be greater than Min\n";
        }
        if(stock>max||stock<min){
            error=true;
            message=message+"Inventory must be between Min and Max\n";
        }
        if(!error) {
            try {
                Product newProduct= new Product(product.getId(),name,price,stock,min,max);
                if(!associatedPart.isEmpty()) {
                    for (Part part : associatedPart) {
                        newProduct.addAssociatedPart(part);
                    }
                }
                Inventory.updateProduct(Inventory.getAllProducts().indexOf(product),newProduct);
                stage=(Stage)((Button)(actionEvent.getSource())).getScene().getWindow();
                parent= FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
                stage.setScene(new Scene(parent));
                stage.show();
            }catch (Exception e){
                ErrorText.setText(e.toString());
            }
        }else{
            ErrorText.setText(message);
        }
    }

    /**
     * Go back to main form without saving or updating any product
     * @param actionEvent when cancel button is clicked
     * @throws IOException
     */

    public void OnModProdCancelBtn(ActionEvent actionEvent) throws IOException {
        stage=(Stage)((Button)(actionEvent.getSource())).getScene().getWindow();
        parent= FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
        stage.setScene(new Scene(parent));
        stage.show();
    }

    /**
     * Initialize the Modify Product form, making the Table columns bind to the correct field
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        associatedPart= FXCollections.observableArrayList();
    }

    /**
     * Load all the data of the selected product from Main form's selected Items in the tableView
     * @param product the producted selected from the tableView
     */
    public void loadData(Product product){
        this.product=product;
        for(Part part:product.getAllAssociatedParts()){
            this.associatedPart.add(part);
        }
        ModProdPartTable.setItems(Inventory.getAllParts());
        ModProdAssoPartTable.setItems(associatedPart);
        ModProdPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        ModProdPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ModProdPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        ModProdInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ModProdAssoPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        ModProdAssoPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ModProdAssoPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        ModProdAssoInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ModProdIdTxf.setText(String.valueOf(product.getId()));
        ModProdNameTxf.setText(product.getName());
        ModProdInvTxf.setText(String.valueOf(product.getStock()));
        ModProdPriceTxf.setText(String.valueOf(product.getPrice()));
        ModProdMaxTxf.setText(String.valueOf(product.getMax()));
        ModProdMinTxf.setText(String.valueOf(product.getMin()));

    }

    /**
     * Search the parts with matching ID or naming containing the string in text Field
     * @param keyEvent when a key is entered in the text field
     */

    public void ModProdSearchTxfChanged(KeyEvent keyEvent) {
        String input=ModProdSearchTxf.getText();
        if (input.matches("-?\\d+")){
            ModProdPartTable.setItems(Inventory.getAllParts());
            ModProdPartTable.getSelectionModel().select(Inventory.lookupPart(Integer.parseInt(input)));
        }else{
            ModProdPartTable.setItems(Inventory.lookupPart(input));
        }
    }
}
