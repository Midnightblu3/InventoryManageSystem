package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import javafx.event.ActionEvent;
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
import java.util.ResourceBundle;

/**
 * Controller for Modify Part Form
 * RUNTIME ERROR: I tried to the called the setText in the initialize method to set the load Data of the Part from MainFormController, it failed because in order for the data from MainFormController to carry over, I have to make another method loadData to use the selected Part
 from the table view in the MainFormController since initialize method will be finished before it can load any data.
 * FUTURE ENHANCEMENT: In the ModifyPartForm, we can add a button to switch to the next part in the list to modify.
 * @author Rui Huang
 */

public class ModifyPartForm implements Initializable {
    public RadioButton ModPartInHouseRb;
    public RadioButton ModPartOutsourceRb;
    public TextField ModPartIDTxf;
    public TextField ModPartNameTxf;
    public TextField ModPartInvTxf;
    public TextField ModPartPriceTxf;
    public TextField ModPartMaxTxf;
    public TextField ModPartMinTxf;
    public TextField ModPartMorCTxf;
    public Label ModPartMorCLb;
    public Button ModPartSaveBtn;
    public Button ModPartCancelBtn;
    private Part part;
    public Label ErrorText;
    private Stage stage;
    private Parent parent;

    /**
     * The method called when Save button is clicked in the Modify Part form
     * this method checks if all the inputs are valid and without logical errors and create a Part object to update the new part to the inventory list
     * @param actionEvent an event when button is clicked
     */

    public void OnModPartSaveBtn(ActionEvent actionEvent) throws IOException {
        String name;
        int stock=0;
        int min=0;
        int max=0;
        double price=0;
        boolean error=false;
        int machineId=0;
        String companyName="";
        String message="Exception: ";

        name = ModPartNameTxf.getText();
        if (name.isEmpty()) {
            error=true;
            message = message +"No data in Name field\n";
        }
        try {
            stock = Integer.parseInt(ModPartInvTxf.getText());
        }catch (NumberFormatException e){
            error=true;
            message=message+"Inv is not an Integer\n";
        }

        try {
            min = Integer.parseInt(ModPartMinTxf.getText());
        }catch (Exception e){
            error=true;
            message=message+"Min is not an Integer\n";
        }
        try {
            max = Integer.parseInt(ModPartMaxTxf.getText());
        }catch (Exception e){
            error=true;
            message=message+"Max is not an Integer\n";
        }
        try {
            price = Double.parseDouble(ModPartPriceTxf.getText());
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
        if (ModPartInHouseRb.isSelected()) {
            try {
                machineId = Integer.parseInt(ModPartMorCTxf.getText());
            }catch (NumberFormatException e){
                error=true;
                message=message+"Machine ID is not Integer\n";
            }
            if(!error) {
                try {
                    InHouse inHousePart = new InHouse(this.part.getId(), name, price, stock, min, max, machineId);
                    Inventory.updatePart(Inventory.getAllParts().indexOf(this.part), inHousePart);
                   Stage stage = (Stage) ((Button) (actionEvent.getSource())).getScene().getWindow();
                    Parent parent = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
                    stage.setScene(new Scene(parent));
                    stage.setTitle("Main Part Form");
                    stage.show();
                }catch (Exception e){
                    ErrorText.setText(e.toString());
                }
            }else{
                ErrorText.setText(message);
            }
        } else if (ModPartOutsourceRb.isSelected()) {
            companyName = ModPartMorCTxf.getText();
            if (companyName.isEmpty()) {
                error = true;
                message = message + "No data in Company Name\n";
            }
            if (!error) {
                try {
                    Outsourced outsourcedPart = new Outsourced(this.part.getId(), name, price, stock, min, max, companyName);
                    Inventory.updatePart(Inventory.getAllParts().indexOf(this.part), outsourcedPart);
                    stage = (Stage) ((Button) (actionEvent.getSource())).getScene().getWindow();
                    parent = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
                    stage.setScene(new Scene(parent));
                    stage.setTitle("Main Part Form");
                    stage.show();
                } catch (Exception e) {
                    ErrorText.setText(e.toString());
                }
            } else {
                ErrorText.setText(message);
            }
        }
    }
    /**
     * The method called when the cancel button in the modify part form is clicked
     * Nothing is saved and will go back to the main part form
     * @param actionEvent when the button was clicked
     * @throws IOException occurs when an IO operation fails
     */

    public void OnModPartCancelBtn(ActionEvent actionEvent) throws IOException {
         stage = (Stage) ((Button) (actionEvent.getSource())).getScene().getWindow();
         parent = FXMLLoader.load(getClass().getResource("/View/MainForm.fxml"));
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

    }

    /**
     * Set the label to Machine ID
     * @param actionEvent when the InHouse radio button is selected
     */

    public void ModOnInHouseRb(ActionEvent actionEvent) {
        ModPartMorCLb.setText("Machine ID");
    }

    /**
     * Set the label to Company Name
     * @param actionEvent when the Outsourced radio button is clicked
     */


    public void ModOntOutsourceRb(ActionEvent actionEvent) {
        ModPartMorCLb.setText(("Company Name"));

    }

    /**
     * Load all the data of the selected part from Main form's selected Items in the tableView
     * @param part the part selected from the tableView
     */
    public void loadData(Part part){
        this.part=part;
        if (part instanceof InHouse){
            ModPartInHouseRb.setSelected(true);
            ModPartMorCLb.setText("Machine ID");
            ModPartMorCTxf.setText(String.valueOf(((InHouse) part).getMachineId()));
        }else if (part instanceof Outsourced){
            ModPartOutsourceRb.setSelected(true);
            ModPartMorCLb.setText("Company Name");
            ModPartMorCTxf.setText(((Outsourced) part).getCompanyName());
        }
        ModPartIDTxf.setText(String.valueOf(part.getId()));
        ModPartNameTxf.setText(part.getName());
        ModPartInvTxf.setText(String.valueOf(part.getStock()));
        ModPartPriceTxf.setText(String.valueOf(part.getPrice()));
        ModPartMaxTxf.setText(String.valueOf(part.getMax()));
        ModPartMinTxf.setText(String.valueOf(part.getMin()));

    }
}
