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
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
/**
 * Controller for Add Product Form
 * RUNTIME ERROR: Didn't check if AddProdPartTable.getSelectionModel().getSelectedItem() returned a Null object before adding the object to the AddProdAssoPartTable ObservableList, causing the list to hold a Null object when add part button is clicked
 * FUTURE ENHANCEMENT: When can use checkbox to select mutiple Parts to add at once
 * @author Rui Huang
 */

public class AddProductForm implements Initializable {
    public TextField AddProdIDTxf;
    public TextField AddProdNameTxf;
    public TextField AddProdInvTxf;
    public TextField AddProdPriceTxf;
    public TextField AddProdMaxTxf;
    public TextField AddProdMinTxf;
    public TextField AddProdSearchTxf;
    public TableView<Part> AddProdPartTable;
    public TableColumn<Part,Integer> AddProdPartIdCol;
    public TableColumn<Part,String> AddProdPartNameCol;
    public TableColumn<Part,Integer> AddProdInvCol;
    public TableColumn<Part,Double> AddProdPriceCol;
    public TableView<Part> AddProdAssoPartTable;
    public TableColumn<Part,Integer> AddProdAssoPartIDCol;
    public TableColumn<Part,String> AddProdAssoPartNameCol;
    public TableColumn<Part,Integer> AddProdAssoInvCol;
    public TableColumn<Part,Double> AddProdAssoPriceCol;
    public Button AddProdRemoveBtn;
    public Button AddProdSaveBtn;
    public Button AddProdCancelBtn;
    public Button AddProdAddPartBtn;
    public Label ErrorText;
    private Stage stage;
    private Parent parent;
    private ObservableList<Part> associatedPart;

    /**
     * Initialize the Add Product form, making the Table columns bind to the correct field
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        associatedPart= FXCollections.observableArrayList();
        AddProdPartTable.setItems(Inventory.getAllParts());
        AddProdAssoPartTable.setItems(associatedPart);
        AddProdPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        AddProdPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        AddProdPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        AddProdInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AddProdAssoPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        AddProdAssoPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        AddProdAssoPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        AddProdAssoInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }

    /**
     * Add button Event handler that add parts to the associated part list
     * @param actionEvent when button is clicked
     */

    public void OnAddProdAddPartBtn(ActionEvent actionEvent) {
        Part part=AddProdPartTable.getSelectionModel().getSelectedItem();
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

    public void OnAddProdRemoveBtn(ActionEvent actionEvent) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                associatedPart.remove(AddProdAssoPartTable.getSelectionModel().getSelectedItem());
            }
        }catch (NullPointerException e){
            ErrorText.setText("No Parts Selected");
        }

    }

    /**
     * Save the product as a new product with the data user entered and the list of parts added to the product
     * @param actionEvent button is clicked
     * @throws IOException occurs when an IO operation fails
     */

    public void OnAddProdSaveBtn(ActionEvent actionEvent) throws IOException {
        String name;
        int stock=0;
        int min=0;
        int max=0;
        double price=0;
        boolean error=false;
        String message="Exception: ";
        ArrayList<Integer> IdList=new ArrayList<Integer>();
        int id=1;
        if(!Inventory.getAllProducts().isEmpty()){
            for(Product product:Inventory.getAllProducts()){
                IdList.add(product.getId());
            }
            for (int i=IdList.size()+1;i>0;i--){
                if(!IdList.contains(i)){
                    id=i;
                }
            }
        }
        name = AddProdNameTxf.getText();
        if (name.isEmpty()) {
            error=true;
            message = message +"No data in Name field\n";
        }
        try {
            stock = Integer.parseInt(AddProdInvTxf.getText());
        }catch (NumberFormatException e){
            error=true;
            message=message+"Inv is not an Integer\n";
        }

        try {
            min = Integer.parseInt(AddProdMinTxf.getText());
        }catch (Exception e){
            error=true;
            message=message+"Min is not an Integer\n";
        }
        try {
            max = Integer.parseInt(AddProdMaxTxf.getText());
        }catch (Exception e){
            error=true;
            message=message+"Max is not an Integer\n";
        }
        try {
            price = Double.parseDouble(AddProdPriceTxf.getText());
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
                Product newProduct= new Product(id,name,price,stock,min,max);
                if(!associatedPart.isEmpty()) {
                    for (Part part : associatedPart) {
                        newProduct.addAssociatedPart(part);
                    }
                }
                Inventory.addProduct(newProduct);
                stage=(Stage)((Button)(actionEvent.getSource())).getScene().getWindow();
                parent= FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
                stage.setScene(new Scene(parent));
                stage.setTitle("Main Part Form");
                stage.show();
            }catch (Exception e){
                    ErrorText.setText(e.toString());
            }
        }else{
            ErrorText.setText(message);
        }
    }

    /**
     * Go back to main form without saving or creating any product
     * @param actionEvent when cancel button is clicked
     * @throws IOException
     */

    public void OnAddProdCancelBtn(ActionEvent actionEvent) throws IOException {
        stage=(Stage)((Button)(actionEvent.getSource())).getScene().getWindow();
        parent= FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
        stage.setScene(new Scene(parent));
        stage.setTitle("Main Part Form");
        stage.show();
    }

    /**
     * Search the parts with matching ID or naming containing the string in text Field
     * @param keyEvent when a key is entered in the text field
     */

    public void AddProdSearchTxfChanged(KeyEvent keyEvent) {
        String input=AddProdSearchTxf.getText();
        if (input.matches("-?\\d+")){
            AddProdPartTable.setItems(Inventory.getAllParts());
            AddProdPartTable.getSelectionModel().select(Inventory.lookupPart(Integer.parseInt(input)));
        }else{
            AddProdPartTable.setItems(Inventory.lookupPart(input));
        }
    }
}
