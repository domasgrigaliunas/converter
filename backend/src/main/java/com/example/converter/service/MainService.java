package com.example.converter.service;

import com.example.converter.dao.CurrencyLabelDao;
import com.example.converter.dao.FxRateDao;
import com.example.converter.dao.UserActivityDao;
import com.example.converter.model.Currency;
import com.example.converter.model.CurrencyLabel;
import com.example.converter.model.FxRate;
import com.example.converter.model.UserActivity;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.*;

@Service
public class MainService {

    private final CurrencyLabelDao currencyLabelDao;
    private final FxRateDao fxRateDao;
    private final UserActivityDao userActivityDao;

    @Autowired
    public MainService(@Qualifier("currencyConverter") CurrencyLabelDao currencyLabelDao, FxRateDao fxRateDao,
                       UserActivityDao userActivityDao) {
        this.currencyLabelDao = currencyLabelDao;
        this.fxRateDao = fxRateDao;
        this.userActivityDao = userActivityDao;
    }

    public int addCurrencyLabel(CurrencyLabel currencyLabel) {
        return currencyLabelDao.insertCurrencyLabel(currencyLabel);
    }

    public int deleteCurrencyLabelByCurrency(String currency) {
        return currencyLabelDao.deleteCurrencyLabelByCurrency(currency);
    }

    public List<CurrencyLabel> getAllCurrencyLabels() {
        return currencyLabelDao.selectAllCurrencyLabels();
    }

    public List<FxRate> getAllFxRates() {
        return fxRateDao.selectAllFxRates();
    }

    public FxRate getFxRate(Currency currency) {
        return fxRateDao.selectFxRateByCurrencies(currency);
    }

    public int storeAllCurrencyLabelsFromAPI() {

        final String url = "https://www.lb.lt/webservices/FxRates/FxRates.asmx/getCurrencyList";
        LinkedList<CurrencyLabel> allCurrencyLabels = new LinkedList<CurrencyLabel>();

        try
        {
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            DocumentBuilder b = f.newDocumentBuilder();
            Document doc = b.parse(url);
            doc.getDocumentElement().normalize();

            NodeList nodesList = doc.getElementsByTagName("CcyNtry");

            for (int temp = 0; temp < nodesList.getLength(); temp++) {
                Node currentNode = nodesList.item(temp);

                if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element currentElement = (Element) currentNode;
                    allCurrencyLabels.add(new CurrencyLabel(
                            currentElement.getElementsByTagName("Ccy").item(0).getTextContent(),
                            currentElement.getElementsByTagName("CcyNm").item(0).getTextContent(),
                            currentElement.getElementsByTagName("CcyNm").item(1).getTextContent()
                    ));
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return currencyLabelDao.insertAllCurrencyLabelsFromAPI(allCurrencyLabels);
    }

    public int storeAllFxRatesFromAPI() {

        final String url = "https://www.lb.lt/webservices/FxRates/FxRates.asmx/getCurrentFxRates?tp=EU";
        LinkedList<FxRate> allFxRates = new LinkedList<FxRate>();

        try
        {
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            DocumentBuilder b = f.newDocumentBuilder();
            Document doc = b.parse(url);
            doc.getDocumentElement().normalize();

            NodeList nodesList = doc.getElementsByTagName("FxRate");

            for (int temp = 0; temp < nodesList.getLength(); temp++) {
                Node currentNode = nodesList.item(temp);

                if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element currentElement = (Element) currentNode;

                    allFxRates.add(new FxRate(
                        currentElement.getElementsByTagName("Dt").item(0).getTextContent(),
                        currentElement.getElementsByTagName("Ccy").item(0).getTextContent(),
                        Double.parseDouble(currentElement.getElementsByTagName("Amt").item(0).getTextContent()),
                        currentElement.getElementsByTagName("Ccy").item(1).getTextContent(),
                        Double.parseDouble(currentElement.getElementsByTagName("Amt").item(1).getTextContent())
                    ));

                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }




        return fxRateDao.insertAllFxRatesFromAPI(allFxRates);
    }

    public int addAllCurrencyLabels(List<CurrencyLabel> currencyLabels) {

        // Get data here

        return currencyLabelDao.insertAllCurrencyLabels(currencyLabels);
    }

    public int storeUserActivity(UserActivity activity) {
        return userActivityDao.insertUserActivity(activity);
    }
}
