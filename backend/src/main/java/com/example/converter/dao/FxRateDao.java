package com.example.converter.dao;


import com.example.converter.model.Currency;
import com.example.converter.model.FxRate;

import java.util.LinkedList;
import java.util.List;

public interface FxRateDao {

    int insertAllFxRatesFromAPI(LinkedList<FxRate> fxRates);

    List<FxRate> selectAllFxRates();

    FxRate selectFxRateByCurrencies(Currency currency);

}
