package com.example.cardealership;

import java.util.Date;

public class HistoryAct {
    String email;
    Date actDate;
    String actDescription;

    public HistoryAct(String email, Date actDate, String actDescription) {
        this.email = email;
        this.actDate = actDate;
        this.actDescription = actDescription;
    }

    public long getId() {
        return 5;
    }
}
