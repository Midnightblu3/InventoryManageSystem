package Model;

/**
 * InHouse Class extend Part class
 * RUNTIME ERROR: didn't initialize MachineId in the constructor so getMachineId failed when it is called
 * FUTURE ENHANCEMENT: We can have inHouse part hold a list of parts that are related to this part, for example a type of tire is for related to a type of rim
 * @author Rui Huang
 */
public class InHouse extends Part{
    /**
     * Private Variable MachineId that store the Machine ID of a In House Part
     */
    private int MachineId;

    /**
     * Contructor of the In House Part. Use super to call the ancestor's constructor method. Method has below parameters
     * @param id a string that will be used to update the id of the part
     * @param name a string will be used to update the name of the part
     * @param price a double will be used to that update the price of the part
     * @param stock a int that will be used to update the number of the part in stock
     * @param min a int will be used to update the minimum number of part the store can hold
     * @param max a int will be used to update the maximum number of part the sotre can hold
     * @param MachineId a int will be used to update the MachineId of the inHouse Part
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int MachineId) {
        super(id, name, price, stock, min, max);
        this.MachineId=MachineId;
    }

    /**
     * This method is able to set the value of the MachineId Variable
     * @param MachineId will be used to update the MachineId Variable
     */
    public void setMachineId(int MachineId){
        this.MachineId=MachineId;
    }

    /**
     * This method will return the value of MachineID variable as an int
     * @return MachineID of the part as an int
     */
    public int getMachineId() {
        return MachineId;
    }
}
