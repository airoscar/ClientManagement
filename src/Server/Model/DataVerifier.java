package Server.Model;

import Shared.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DataVerifier {

    /**
     * Constructor for the DataVerifier class. This class is used to verify the values contained
     * within a Person object. Receives a Person object and verifies if all the fields
     * contain valid values. If there are invalid entries, tries to fix them

     */
    public DataVerifier() {
    }

    /**
     * Verify format of input data, make necessary changes to format if needed to maintain format consistency. </br>
     * Returns null if the input data does not meet requirement. </br>
     * Returns a re-formatted array of String type ready that meet format requirements.
     *
     * @return
     */
    public Person verifyInput(Person person) throws Exception {

        if (person.getFirstName().length() > 20) {
            throw new InputNameAddressException();
        }
        if (person.getLastName().length() > 20) {
            throw new InputNameAddressException();

        }
        if (person.getAddress().length() > 50) {
            throw new InputNameAddressException();
        }
        if (fixPostalFormat(person.getPostalCode()) == null) {
            throw new InputPostalException();
        } else {
            person.setPostalCode(fixPostalFormat(person.getPostalCode()));
        }
        if (fixPhoneNumber(person.getPhoneNumber()) == null) {
            throw new InputPhoneNumberException();
        } else {
            person.setPhoneNumber(fixPhoneNumber(person.getPhoneNumber()));
        }
        if (person.getClientType().equalsIgnoreCase("C") || person.getClientType().equalsIgnoreCase("R")) {
            person.setClientType(person.getClientType().toUpperCase());
        } else {
            throw new InputClientTypeException();
        }
        return person;

    }

    /**
     * Checks if the postal code is in proper format. </br>
     * Returns null if the input is not recognized as a postal format. </br>
     * If the input is postal format, but does not have a space in the middle, insert a space in the middle. </br>
     * Return the properly formatted postal code.
     *
     * @param postal
     * @return
     */
    private String fixPostalFormat(String postal) {
        String regex = "^[A-Z,a-z][0-9][A-Z,a-z]\\s?[0-9][A-Z,a-z][0-9]$";
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(postal);

        if (!matcher.matches()) {   //not a postal format, return null
            return null;
        }

        if (postal.length() == 6) {  //if no space in the middle, add space
            postal = postal.substring(0, 3) + " " + postal.substring(3, 6);
        }

        postal = postal.toUpperCase();

        return postal;  //return verified postal code
    }

    /**
     * Checks if the phone number is in proper format. </br>
     * Returns null if the number is not formatted correctly. </br>
     * If dashes are missing, add dashes in appropriated locations.
     *
     * @param number
     * @return
     */
    private String fixPhoneNumber(String number) {
        String regex = "^[0-9][0-9][0-9]-?[0-9][0-9][0-9]-?[0-9][0-9][0-9][0-9]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(number);

        if (!matcher.matches()) {   //not a phone number format, return null
            return null;
        }

        if (number.length() == 10) {  //if number is missing two dashes
            number = number.substring(0, 3) + "-" + number.substring(3, 6) + "-" + number.substring(6, 10);
        } else if (number.length() == 11 && number.charAt(3) != '-') { //number missing one dash at index 3
            number = number.substring(0, 3) + "-" + number.substring(3, 11);
        } else if (number.length() == 11 && number.charAt(7) != '-') { //number missing one dash at index 7
            number = number.substring(0, 7) + "-" + number.substring(7, 11);
        }

        return number;
    }
}
