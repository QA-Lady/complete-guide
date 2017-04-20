package utils.dataModel;

public class CustomerData {
    private final String firstname;
    private final String lastname;
    private final String address;
    private final String city;
    private final String zip;
    private final String country;
    private final int stateByIndex;
    private final String email;
    private final String phone;
    private final String passw;

    public CustomerData(String firstname, String lastname, String address, String city, String zip, String country, int stateByIndex, String email, String phone, String passw) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.country = country;
        this.stateByIndex = stateByIndex;
        this.email = email;
        this.phone = phone;
        this.passw = passw;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getZip() {
        return zip;
    }

    public String getCountry() {
        return country;
    }

    public int getStateByIndex() {
        return stateByIndex;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassw() {
        return passw;
    }
}
