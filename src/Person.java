// ENSF 519-2 Java Project I
// Person Management System
// Oscar Chen & Savith Jayasekera
// November 5 2018

/**
 * Class which represents individual clients.
 */
public class Person {

    private String dataID;
    private String firstName;
    private String lastName;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private String clientType;

    /**
     * Default constructor
     */
    public Person() {

    }

    /**
     * Constructor that takes dataID attribute.
     * @param dataID
     */
    public Person(String dataID) {
        this.dataID = dataID;
    }

    /**
     * Constructor which constructs with all attributes.
     * @param dataID
     * @param firstName
     * @param lastName
     * @param address
     * @param postalCode
     * @param phoneNumber
     * @param clientType
     */
    public Person(String dataID, String firstName, String lastName,
                  String address, String postalCode, String phoneNumber, String clientType) {
        this(firstName, lastName, address, postalCode, phoneNumber, clientType);
        this.dataID = dataID;
    }

    /**
     * Constructor which constructs with all attributes except for id (primary key in database)
     * @param firstName
     * @param lastName
     * @param address
     * @param postalCode
     * @param phoneNumber
     * @param clientType
     */
    public Person(String firstName, String lastName,
                  String address, String postalCode, String phoneNumber, String clientType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.clientType = clientType;
    }

    public String getDataID() {
        return this.dataID;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getAddress() {
        return this.address;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getClientType() {
        return clientType;
    }

    public void setDataID(String dataID) {
        this.dataID = dataID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String toString() {
        return this.dataID + ", " + this.firstName + ", " + this.lastName + ", " + this.address + ", " + this.postalCode
                + ", " + this.phoneNumber + ", " + clientType;
    }

}
