package com.example.converter.dao;

import com.example.converter.model.Currency;
import com.example.converter.model.CurrencyLabel;
import com.example.converter.model.FxRate;
import com.example.converter.model.UserActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository("currencyConverter")
public class MainDataAccessService implements CurrencyLabelDao, FxRateDao, UserActivityDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MainDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertCurrencyLabel(CurrencyLabel currencyLabel) {
        final String sql = "INSERT INTO currency_names (currency, nameLT, nameEN) " +
                "VALUES(?, ?, ?)";

        return jdbcTemplate.update(sql, currencyLabel.getCurrency(),
                currencyLabel.getNameLT(), currencyLabel.getNameEN());
    }

    @Override
    public int insertAllCurrencyLabels(List<CurrencyLabel> currencyLabels) {
        //String inSql = String.join(",", Collections.nCopies(currencyLabels.size(), "?"));

        final String sql = "INSERT INTO currency_names (currency, nameLT, nameEN) " +
                "VALUES(?, ?, ?)";

        List<Object[]> parameters = new ArrayList<Object[]>();

        for(CurrencyLabel label : currencyLabels) {
            parameters.add(new Object[] {label.getCurrency(), label.getNameLT(), label.getNameEN()});
        }

        jdbcTemplate.batchUpdate(sql, parameters);

        return 1;

    }

    @Override
    public int insertAllCurrencyLabelsFromAPI(LinkedList<CurrencyLabel> currencyLabels) {
        final String sql = "INSERT INTO currency_names (currency, nameLT, nameEN) " +
                "VALUES(?, ?, ?)";

        List<Object[]> parameters = new ArrayList<Object[]>();

        for(CurrencyLabel label : currencyLabels) {
            parameters.add(new Object[] {label.getCurrency(), label.getNameLT(), label.getNameEN()});
        }

        jdbcTemplate.batchUpdate(sql, parameters);

        return 1;
    }

    @Override
    public int insertAllFxRatesFromAPI(LinkedList<FxRate> fxRates) {
        final String sql = "INSERT INTO fxrates (last_updated, currency_a, amount_a, currency_b, amount_b) " +
                "VALUES(?, ?, ?, ?, ?)";

        List<Object[]> parameters = new ArrayList<Object[]>();

        for(FxRate label : fxRates) {
            parameters.add(new Object[] {label.getLastUpdated(), label.getCurrencyA(), label.getAmountA(),
            label.getCurrencyB(), label.getAmountB()});
        }

        jdbcTemplate.batchUpdate(sql, parameters);

        return 1;
    }

    @Override
    public List<CurrencyLabel> selectAllCurrencyLabels() {
        final String sql = "SELECT currency, nameLT, nameEN FROM currency_names";

        return jdbcTemplate.query(sql, (resultSet, i) -> {
            String currency = resultSet.getString("currency");
            String nameLT = resultSet.getString("nameLT");
            String nameEN = resultSet.getString("nameEN");
            return new CurrencyLabel(currency, nameLT, nameEN);
        });
    }

    @Override
    public List<FxRate> selectAllFxRates() {

        final String sql = "SELECT last_updated, currency_a, amount_a, currency_b, amount_b FROM fxrates";

        return jdbcTemplate.query(sql, (resultSet, i) -> {
            String lastUpdated = resultSet.getString("last_updated");
            String currencyA = resultSet.getString("currency_a");
            double amountA = Double.parseDouble(resultSet.getString("amount_a"));
            String currencyB = resultSet.getString("currency_b");
            double amountB = Double.parseDouble(resultSet.getString("amount_b"));
            return new FxRate(lastUpdated, currencyA, amountA, currencyB, amountB);
        });
    }


    @Override
    public int deleteCurrencyLabelByCurrency(String currency) {

        final String sql = "DELETE FROM currency_names WHERE currency = ?";
        Object[] args = new Object[] {currency};

        return jdbcTemplate.update(sql, args);
    }

    @Override
    public FxRate selectFxRateByCurrencies(Currency currency) {
        final String sql = "SELECT last_updated, currency_a, amount_a, currency_b, amount_b FROM fxrates " +
                "WHERE currency_b = ?";

        List<FxRate> result  = jdbcTemplate.query(sql, new Object[]{currency.getName()}, (resultSet, i) -> {
            String lastUpdated = resultSet.getString("last_updated");
            String currencyA = resultSet.getString("currency_a");
            double amountA = Double.parseDouble(resultSet.getString("amount_a"));
            String currencyB = resultSet.getString("currency_b");
            double amountB = Double.parseDouble(resultSet.getString("amount_b"));
            return new FxRate(lastUpdated, currencyA, amountA, currencyB, amountB);
        });

        if (result.isEmpty()){
            return new FxRate("NA", "NA", 0, "NA", 0);
        }

        return result.get(0);
    }

    @Override
    public int insertUserActivity(UserActivity activity) {
        final String sql = "INSERT INTO user_activity (date_time, amount, currency_a, currency_b, result) " +
                "VALUES(?, ?, ?, ?, ?)";

        return jdbcTemplate.update(sql, activity.getDateTime(), activity.getAmount(),
                activity.getCurrencyA(), activity.getCurrencyB(), activity.getResult());
    }
}
