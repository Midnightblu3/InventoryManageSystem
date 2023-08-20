package Controller;

import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller for Main Form
 * RUNTIME ERROR: Didn't use the Action Event.getSource().getScene().getWindows() to locate the Stage before setting the new Stage with the Parent. The application crashed with error saying the stage is null therefore can't set the stage.
 * FUTURE ENHANCEMENT: We can add a details button to display a part or product's details
 * @author Rui Huang
 */
public class MainFormController  implements Initializable {


    public TextField MainPartSearchTxf;
    public Button MainAddPartBtn;
    public Button MainModPartBtn;
    public Button MainDelPartBtn;
    public TableView<Part> MainPartTable;
    public TableColumn<Part,Integer> MainPartIdCol;
    public TableColumn<Part,String> MainPartNameCol;
    public TableColumn<Part,Integer> MainPartInvCol;
    public TableColumn<Part,Double> MainPartPriceCol;
    public TextField MainProdSearchTxf;
    public Button MainAddProdBtn;
    public Button MainModProdBtn;
    public Button MainDelProdBtn;
    public TableView<Product> MainProductTable;
    public TableColumn<Part,Integer> MainProdIdCol;
    public TableColumn<Part,String> MainProdNameCol;
    public TableColumn<Part,Integer> MainProdInvCol;
    public TableColumn<Part,Double> MainProdPriceCol;
    public Button MainExitBtn;
    public Label ErrorText;
    private Stage stage;
    private Parent parent;

    /**
     * The method called when this form is loaded
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MainPartTable.setItems(Inventory.getAllParts());
        MainProductTable.setItems(Inventory.getAllProducts());
        MainPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        MainPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        MainPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        MainPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        MainProdIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        MainProdNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        MainProdPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        MainProdInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }

    /**
     * Taking the user to add part page
     * @param actionEvent when button is clicked
     * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations.
     */

    public void OnAddPart(ActionEvent actionEvent) throws IOException {
        stage=(Stage)((Button)(actionEvent.getSource())).getScene().getWindow();
        parent= FXMLLoader.load(getClass().getResource("/View/AddPartForm.fxml"));
        stage.setTitle("Add Part Form");
        stage.setScene(new Scene(parent));
        stage.show();
    }

    /**
     * Load the selected part to be edited in the modify part page
     * @param actionEvent when button is clicked
     * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations.
     */

    public void OnModifyPart(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader= new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/ModifyPartForm.fxml"));
        loader.load();
        ModifyPartForm modifyPartForm=loader.getController();
        try {
            modifyPartForm.loadData(MainPartTable.getSelectionModel().getSelectedItem());
            stage = (Stage) ((Button) (actionEvent.getSource())).getScene().getWindow();
            parent = loader.getRoot();
            stage.setScene(new Scene(parent));
            stage.setTitle("Modify Part Form");
            stage.show();
        }catch (NullPointerException e){
            ErrorText.setText("No part selected");
        }
    }

    /**
     * Remove the selected Part, use confirmation alert window to confirm
     * @param actionEvent when removed button is clicked
     */

    public void OnDeletePart(ActionEvent actionEvent) {
        Alert alert= new Alert(Alert.AlertType.CONFIRMATION,"Are you sure?");
        Optional<ButtonType> result=alert.showAndWait();
        if(result.isPresent() && result.get()==ButtonType.OK) {
            Part part=MainPartTable.getSelectionModel().getSelectedItem();
            if(part!=null){
                Inventory.deletePart(part);
            }
        }

    }

    /**
     * Taking the user to add product page
     * @param actionEvent when button is clicked
     * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations.
     */


    public void OnAddProduct(ActionEvent actionEvent) throws IOException {
        stage=(Stage)((Button)(actionEvent.getSource())).getScene().getWindow();
        parent= FXMLLoader.load(getClass().getResource("/View/AddProductForm.fxml"));
        stage.setScene(new Scene(parent));
        stage.setTitle("Add Product Form");
        stage.show();
    }
    /**
     * Load the selected product to be edited in the modify product page
     * @param actionEvent when button is clicked
     * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations.
     */


    public void OnModifyProduct(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader= new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/ModifyProductForm.fxml"));
        loader.load();
        ModifyProductForm modifyProductForm=loader.getController();
        try {
            modifyProductForm.loadData(MainProductTable.getSelectionModel().getSelectedItem());
            stage = (Stage) ((Button) (actionEvent.getSource())).getScene().getWindow();
            parent = loader.getRoot();
            stage.setScene(new Scene(parent));
            stage.setTitle("Modify Product Form");
            stage.show();
        }catch (NullPointerException e){
            ErrorText.setText("No product selected");
        }
    }


    /**
     * Remove the selected Product, use confirmation alert window to confirm, will only remove the product with no associated part
     * @param actionEvent when removed button is clicked
     */

    public void OnDeleteProduct(ActionEvent actionEvent) {
        Alert alert= new Alert(Alert.AlertType.CONFIRMATION,"Are you sure?");
        Optional<ButtonType> result=alert.showAndWait();
        if(result.isPresent() && result.get()==ButtonType.OK) {
            Product product =MainProductTable.getSelectionModel().getSelectedItem();
            if(product!=null){
                if(product.getAllAssociatedParts().isEmpty()){
                    Inventory.deleteProduct(product);}else{
                    ErrorText.setText("This product has parts");
                }
            }
        }

    }

    /**
     * exit the application use confirmation alert window to confirm
     * @param actionEvent when exist button is clicked
     */

    public void OnExit(ActionEvent actionEvent) {
        Alert alert= new Alert(Alert.AlertType.CONFIRMATION,"Are you sure?");
        Optional<ButtonType> result=alert.showAndWait();
        if(result.isPresent() && result.get()==ButtonType.OK) {
            System.exit(0);
        }
    }

    /**
     * Search the part with matching IDs or name containing the text from the text Field
     * @param keyEvent when key is typed in the text field
     */

    public void PartSearchTxfChanged(KeyEvent keyEvent) {
        String input=MainPartSearchTxf.getText();
        if (input.matches("-?\\d+")){
            MainPartTable.setItems(Inventory.getAllParts());
            Part part=Inventory.lookupPart(Integer.parseInt(input));
            if(part!=null){
                ErrorText.setText("");
                MainPartTable.getSelectionModel().select(part);
            }else {
                ErrorText.setText("No part found");
            }
        }else{
            if(Inventory.lookupPart(input).isEmpty()){
                MainPartTable.setItems(Inventory.lookupPart(input));
                ErrorText.setText("No part found");
            }else {
                ErrorText.setText("");
                MainPartTable.setItems(Inventory.lookupPart(input));
            }
        }
    }

    /**
     * Search the product with matching IDs or name containing the text from the text Field
     * @param keyEvent when key is typed in the text field
     */

    public void ProdSearchTxfChanged(KeyEvent keyEvent) {
        String input=MainProdSearchTxf.getText();
        if (input.matches("-?\\d+")){
            MainProductTable.setItems(Inventory.getAllProducts());
            Product product=Inventory.lookupProduct(Integer.parseInt(input));
            if(product!=null){
                ErrorText.setText("");
                MainProductTable.getSelectionModel().select(product);
            }else {
                ErrorText.setText("No product found");
            }
        }else{
            if(Inventory.lookupProduct(input).isEmpty()){
                MainProductTable.setItems(Inventory.lookupProduct(input));
                ErrorText.setText("No product found");
            }else{
                ErrorText.setText("");
                MainProductTable.setItems(Inventory.lookupProduct(input));}
        }
    }
}