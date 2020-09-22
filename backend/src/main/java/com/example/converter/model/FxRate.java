package com.example.converter.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FxRate {

    private String lastUpdated;
    private String currencyA;
    private double amountA;
    private String currencyB;
    private double AmountB;

    public FxRate(@JsonProperty("last_updated") String lastUpdated,
                  @JsonProperty("currency_a") String currencyA,
                  @JsonProperty("amount_a") double amountA,
                  @JsonProperty("currency_b") String currencyB,
                  @JsonProperty("amount_b") double AmountB) {
        this.lastUpdated = lastUpdated;
        this.currencyA = currencyA;
        this.amountA = amountA;
        this.currencyB = currencyB;
        this.AmountB = AmountB;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getCurrencyA() {
        return currencyA;
    }

    public void setCurrencyA(String currencyA) {
        this.currencyA = currencyA;
    }

    public double getAmountA() {
        return amountA;
    }

    public void setAmountA(double amountA) {
        this.amountA = amountA;
    }

    public String getCurrencyB() {
        return currencyB;
    }

    public void setCurrencyB(String currencyB) {
        this.currencyB = currencyB;
    }

    public double getAmountB() {
        return AmountB;
    }

    public void setAmountB(double amountB) {
        this.AmountB = amountB;
    }
}
