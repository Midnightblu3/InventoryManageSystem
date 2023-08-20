package Model;

/**
 * Outsourced Class extends Part class
 * RUNTIME ERROR: Used the wrong type for companyName, constructor failed when creating the class
 * FUTURE ENHANCEMENT: can make a compareTo method to be used to sort the Part with companyName
 * @author Rui Huang
 */

public class Outsourced extends Part{
    /**
     * Company name of the part stored as String
     */
    private String companyName;

    /**
     * Constructor of the Outsourced Part
     * @param id used to update part id
     * @param name used to update part name
     * @param price used to update part price
     * @param stock used to update part number in the stock
     * @param min used to update minimum number of this part the store holds
     * @param max used to update the maximum number os this part the store can hold
     * @param companyName used to update the company name of the outsourced part
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName=companyName;
    }

    /**
     * return the company name of the part as a String
     * @return
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * set the company name of the part with the String parameter companyName
     * @param companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}
