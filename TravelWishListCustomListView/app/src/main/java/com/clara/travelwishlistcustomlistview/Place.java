package com.clara.travelwishlistcustomlistview;

import java.text.DateFormat;
import java.util.Date;

public class Place {
    private String name;
    private Date dateCreated;

    Place(String name) {
        this.name = name;
        this.dateCreated = new Date();
    }

    public String getName() {
        return name;
    }

    public String getDateCreated() {
        return DateFormat.getDateInstance().format(this.dateCreated);
    }
}
