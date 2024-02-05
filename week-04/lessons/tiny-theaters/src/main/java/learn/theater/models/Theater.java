package learn.theater.models;

import java.util.Objects;

public class Theater {
    private int theaterId;
    private String name;
    private String address;
    private String phone;
    private String emailAddress;

    public int getTheaterId() {
        return theaterId;
    }

    public void setTheaterId(int theaterId) {
        this.theaterId = theaterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Theater theater = (Theater) o;

        if (theaterId != theater.theaterId) return false;
        if (!Objects.equals(name, theater.name)) return false;
        if (!Objects.equals(address, theater.address)) return false;
        if (!Objects.equals(phone, theater.phone)) return false;
        return Objects.equals(emailAddress, theater.emailAddress);
    }

    @Override
    public int hashCode() {
        int result = theaterId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (emailAddress != null ? emailAddress.hashCode() : 0);
        return result;
    }
}
