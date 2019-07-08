package net.eazypg.tasksolution;

import java.util.Date;

public class PGDetails {

    public String name, phone, pgName, pincode;
    public Date dateCreated;

    public PGDetails(String name, String phone, String pgName, String pincode, Date dateCreated) {
        this.name = name;
        this.phone = phone;
        this.pgName = pgName;
        this.pincode = pincode;
        this.dateCreated = dateCreated;
    }

    public PGDetails() {
    }
}
