package com.example.converter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class CurrencyLabel {

    @NotBlank
    private final String currency;
    @NotBlank
    private String nameLT;
    @NotBlank
    private String nameEN;


    public CurrencyLabel(@JsonProperty("currency") String currency,
                         @JsonProperty("nameLT") String nameLT,
                         @JsonProperty("nameEN") String nameEN) {
        this.currency = currency;
        this.nameLT = nameLT;
        this.nameEN = nameEN;

    }

    public String getCurrency() {
        return currency;
    }

    public String getNameLT() {
        return nameLT;
    }

    public void setNameLT(String nameLT) {
        this.nameLT = nameLT;
    }

    public String getNameEN() {
        return nameEN;
    }

    public void setNameEN(String nameEN) {
        this.nameEN = nameEN;
    }
}
