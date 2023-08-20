package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Inventory class that save the list of parts and product in the shop
 * RUNTIME ERROR: didn't make class member static member, so I was unable to use this class to share data between different controllers
 * FUTURE ENHANCEMENT: Add sort method to sort list under certain condition
 * @author Rui Huang
 */
public class Inventory {
    /**
     * ObservableList that store the of Parts in the inventory
     */
    private static ObservableList<Part> allParts= FXCollections.observableArrayList();
    /**
     * ObservableList that store the of Products in the inventory
     */
    private static ObservableList<Product> allProducts=FXCollections.observableArrayList();

    /**
     * add part to inventory
     * @param part the part to be added
     */
    public static void  addPart(Part part){
        allParts.add(part);
    }

    /**
     * add product to inventory
     * @param product the product to be added
     */
    public static void addProduct(Product product){
        allProducts.add(product);
    }

    /**
     * lookup the part with id
     * @param partId id of the part that is going to be looked up
     * @return the part with the id
     */
    public static Part lookupPart(int partId){
        for(Part part:allParts){
            if(part.getId()==partId){
                return part;
            }
        }
        return null;
    }

    /**
     * lookup the product with id
     * @param productId id that we look up
     * @return the product with the id
     */
    public static Product lookupProduct(int productId){
        for (Product product:allProducts){
            if (product.getId()==productId){
                return product;
            }
        }
        return null;
    }

    /**
     * find a list of part contains the string partName
     * @param partName the string we use to look up with
     * @return a list of part contains the string partName
     */
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> filteredList = FXCollections.observableArrayList();
        for(Part part:allParts){
            if(part.getName().contains(partName)){
                filteredList.add(part);
            }
        }
        return filteredList;
    }

    /**
     * find a list of product contains the string productName
     * @param productName the string we use to look up with
     * @return a list of product contains the string productName
     */
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> filteredList = FXCollections.observableArrayList();
        for(Product product:allProducts){
            if(product.getName().contains(productName)){
                filteredList.add(product);
            }
        }
        return filteredList;
    }

    /**
     * update the part at index
     * @param index to locate the part in the arraylist
     * @param selectedPart the part to be set
     */
    public static void updatePart(int index, Part selectedPart){
        allParts.set(index,selectedPart);
    }

    /**
     * update the product at index
     * @param index to locate the product in the arrayList
     * @param selectedProduct the product to be set
     */

    public static void updateProduct(int index, Product selectedProduct){
        allProducts.set(index,selectedProduct);
    }

    /**
     * remove the part from the list
     * @param selectedPart part that will be removed
     * @return true if removed, false if unable to find the part
     */
    public static boolean deletePart(Part selectedPart){
       return allParts.remove(selectedPart);
    }
    /**
     * remove the product from the list
     * @param selectedProduct part that will be removed
     * @return true if removed, false if unable to find the product
     */

    public static boolean deleteProduct(Product selectedProduct){
        return allProducts.remove(selectedProduct);
    }

    /**
     * get all the parts in the inventory
     * @return observableList allparts
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /**
     * get all the product in the inventory
     * @return observableList allproduct
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }
}
