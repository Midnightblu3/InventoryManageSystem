package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Product class store all the information about the product including the parts in the product
 * RUNTIME ERROR: didn't use Fxcollection Class to call observableArrayList() to initialize the observableList, causing error when returning the associatedPart
 * FUTURE ENHANCEMENT: Can add a compareTo method to compare the number of parts to another product
 * @author Rui Huang
 */
public class Product {
    /**
     * ObservableList that store all the parts for the product
     */
    private ObservableList<Part> associatedParts= FXCollections.observableArrayList();
    /**
     * int that stores the id
     */
    private int id;
    /**
     * String for the part name
     */
    private String name;
    /**
     * double that stores the price
     */
    private double price;
    /**
     * int that stores the number of product
     */
    private int stock;
    /**
     * int store the minimum number of product in the store
     */
    private int min;
    /**
     * max store the maximum number of product in the store
     */
    private int max;

    /**
     * Construct for Product that initalize the id,name,price, number in stock and min and max for the product
     * @param id update id
     * @param name update name
     * @param price update price
     * @param stock update number in stock
     * @param min update the minimum number
     * @param max update the maximum number
     */

    public Product( int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * get product id
     * @return id as an int
     */

    public int getId() {
        return id;
    }

    /**
     * set id for the product
     * @param id update id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * get name of product
     * @return name as a String
     */
    public String getName() {
        return name;
    }

    /**
     * set name
     * @param name update name with a String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * get price of the product
     * @return price as a double
     */
    public double getPrice() {
        return price;
    }

    /**
     * set price of the product
     * @param price update price as a double
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * get number of product in stock
     * @return stock number as int
     */
    public int getStock() {
        return stock;
    }

    /**
     * set number of products in stock
     * @param stock set the stock number as int
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * get minimum number of the product
     * @return min as int
     */
    public int getMin() {
        return min;
    }

    /**
     * set minimum number of the product
     * @param min update min
     */
    public void setMin(int min) {
        this.min = min;
    }
    /**
     * get maximum number of the product
     * @return max as int
     */

    public int getMax() {
        return max;
    }

    /**
     * set maximum number of the product
     * @param max update max as int
     */

    public void setMax(int max) {
        this.max = max;
    }

    /**
     * add parts to the list of parts of the product
     * @param part the part to be added
     */
    public void addAssociatedPart(Part part){
        this.associatedParts.add(part);
    }

    /**
     * remove part from observable list
     * @param selectedAssociatedPart the part that is going to be removed
     * @return true for removed successfully false for not finding the part
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
       return this.associatedParts.remove(selectedAssociatedPart);
    }

    /**
     * return the list of part that is associated with this product
     * @return associatePart list
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return this.associatedParts;
    }
}
