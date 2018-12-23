package com.bing.lan.project.api.mail;

import java.util.Observable;

public class Product extends Observable {


    private String mName;
    private Float mPrice;

    String getName() {
        return mName;
    }

    Float getPrice() {
        return mPrice;
    }

    public void setPrice(Float price) {
        mPrice = price;
    }
}
