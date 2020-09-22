package com.example.converter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Currency {

    private String name;

    public Currency(@JsonProperty("currency_b") String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
