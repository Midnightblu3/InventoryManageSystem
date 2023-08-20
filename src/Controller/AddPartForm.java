package Controller;


import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller for Add part form
 * RUNTIME ERROR: Didn't set up an onAction event for the radio button so the Label didn't change for Machine ID or Company Name when the radio button is selected
 * FUTURE ENHANCEMENT: We can add create "a another Part" button to create another part without returning to the main page
 * @author Rui Huang
 */

public class AddPartForm implements Initializable {
    public RadioButton AddPartInHouseRb;
    public RadioButton AddPartOutsourceRb;
    public TextField AddPartIdTxf;
    public TextField AddPartNameTxf;
    public TextField AddPartInvTxf;
    public TextField AddPartPriceTxf;
    public TextField AddPartMaxTxf;
    public TextField AddPartMinTxf;
    public TextField AddPartMorCTxf;
    public Label AddPartMorCLb;
    public Button AddPartSaveBtn;
    public Button AddPartCancelBtn;
    public Label ErrorText;
    private Stage stage;
    private Parent parent;

    /**
     * The method called when Save button is clicked in the Add Part form
     * this method checks if all the inputs are valid and without logical errors and create a Part object to add to the inventory list
     * @param actionEvent an event when button is clicked
     */
    public void OnAddPartSave(ActionEvent actionEvent) {
        String name;
        int stock=0;
        int min=0;
        int max=0;
        double price=0;
        int machineId=0;
        String companyName;
        boolean error=false;
        String message="Exception: ";
            ArrayList<Integer> IdList = new ArrayList<Integer>();
            int id = 1;
            if (!Inventory.getAllParts().isEmpty()) {
                for (Part part : Inventory.getAllParts()) {
                    IdList.add(part.getId());
                }
                for (int i = IdList.size() + 1; i > 0; i--) {
                    if (!IdList.contains(i)) {
                        id = i;
                    }
                }
            }
            name = AddPartNameTxf.getText();
            if (name.isEmpty()) {
                error=true;
                message = message +"No data in Name field\n";
            }
            try {
                stock = Integer.parseInt(AddPartInvTxf.getText());
            }catch (NumberFormatException e){
                error=true;
                message=message+"Inv is not an Integer\n";
            }

        try {
            min = Integer.parseInt(AddPartMinTxf.getText());
        }catch (Exception e){
            error=true;
            message=message+"Min is not an Integer\n";
        }
        try {
            max = Integer.parseInt(AddPartMaxTxf.getText());
        }catch (Exception e){
            error=true;
            message=message+"Max is not an Integer\n";
        }
        try {
            price = Double.parseDouble(AddPartPriceTxf.getText());
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
                if (AddPartInHouseRb.isSelected()) {
                    try {
                        machineId = Integer.parseInt(AddPartMorCTxf.getText());
                    }catch (NumberFormatException e){
                        error=true;
                        message=message+"Machine ID is not Integer\n";
                    }
                    if(!error) {
                        try {
                            InHouse inHousePart = new InHouse(id, name, price, stock, min, max, machineId);
                            Inventory.addPart(inHousePart);
                            stage = (Stage) ((Button) (actionEvent.getSource())).getScene().getWindow();
                            parent = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
                            stage.setScene(new Scene(parent));
                            stage.setTitle("Main Part Form");
                            stage.show();
                        }catch (Exception e){
                            ErrorText.setText(e.toString());
                        }
                    }else{
                        ErrorText.setText(message);
                    }
                } else if (AddPartOutsourceRb.isSelected()) {
                    companyName = AddPartMorCTxf.getText();
                    if (companyName.isEmpty()){
                        error=true;
                        message=message+"No data in Company Name\n";
                    }
                    if(!error) {
                        try {
                            Outsourced outsourcedPart = new Outsourced(id, name, price, stock, min, max, companyName);
                            Inventory.addPart(outsourcedPart);
                            stage = (Stage) ((Button) (actionEvent.getSource())).getScene().getWindow();
                            parent = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
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

    }

    /**
     * The method called when the cancel button in the add part form is clicked
     * Nothing is saved and we will go back to the main part form
     * @param actionEvent when the button was clicked
     * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of exceptions produced by failed or interrupted I/O operations.
     */

    public void OnAddPartCancel(ActionEvent actionEvent) throws IOException {
        stage=(Stage)((Button)(actionEvent.getSource())).getScene().getWindow();
        parent= FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
        stage.setScene(new Scene(parent));
        stage.setTitle("Main Part Form");
        stage.show();
    }

    /**
     * The method called when this form is loaded
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AddPartInHouseRb.setSelected(true);
    }

    /**
     * Set the label to Machine ID
     * @param actionEvent when the InHouse radio button is selected
     */

    public void AddPartOnInHouseRb(ActionEvent actionEvent) {
        AddPartMorCLb.setText("Machine ID");
    }

    /**
     * Set the label to Company Name
     * @param actionEvent when the Outsourced radio button is clicked
     */

    public void AddPartOnOutsourceRb(ActionEvent actionEvent) {
        AddPartMorCLb.setText("Company Name");
    }
}
