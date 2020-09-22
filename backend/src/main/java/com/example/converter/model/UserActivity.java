package com.example.converter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserActivity {

    private String dateTime;
    private String amount;
    private String currencyA;
    private String currencyB;
    private String result;

    public UserActivity(@JsonProperty("dateTime") String dateTime,
                        @JsonProperty("amount") String amount,
                        @JsonProperty("currencyA") String currencyA,
                        @JsonProperty("currencyB") String currencyB,
                        @JsonProperty("result") String result) {
        this.dateTime = dateTime;
        this.amount = amount;
        this.currencyA = currencyA;
        this.currencyB = currencyB;
        this.result = result;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrencyA() {
        return currencyA;
    }

    public void setCurrencyA(String currencyA) {
        this.currencyA = currencyA;
    }

    public String getCurrencyB() {
        return currencyB;
    }

    public void setCurrencyB(String currencyB) {
        this.currencyB = currencyB;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
