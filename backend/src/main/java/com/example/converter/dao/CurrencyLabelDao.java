package com.example.converter.dao;

import com.example.converter.model.CurrencyLabel;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public interface CurrencyLabelDao {

    int insertCurrencyLabel(CurrencyLabel currencyLabel);

    int insertAllCurrencyLabels(List<CurrencyLabel> currencyLabels);

    int insertAllCurrencyLabelsFromAPI(LinkedList<CurrencyLabel> currencyLabels);

    List<CurrencyLabel> selectAllCurrencyLabels();

    int deleteCurrencyLabelByCurrency(String currency);
}
