package com.example.converter.api;

import com.example.converter.model.Currency;
import com.example.converter.model.CurrencyLabel;
import com.example.converter.model.FxRate;
import com.example.converter.model.UserActivity;
import com.example.converter.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RequestMapping("api/v1/")
@RestController
public class MainController {

    private final MainService mainService;

    @Autowired
    public MainController(MainService mainService) {
        this.mainService = mainService;
    }

    @GetMapping({"currencyLabels"})
    public List<CurrencyLabel> getAllCurrencyLabels() {return mainService.getAllCurrencyLabels();}

    @PostMapping({"currencyLabelsAPI"})
    public void storeAllCurrencyLabelsFromAPI() {
        mainService.storeAllCurrencyLabelsFromAPI();
    }

    @GetMapping({"fxRates"})
    public List<FxRate> getAllFxRates() {
        return mainService.getAllFxRates();
    }

    @PostMapping({"fxRates"})
    public void storeAllFxRatesFromAPI() {
        mainService.storeAllFxRatesFromAPI();
    }

    @GetMapping({"User"})
    public FxRate getFxRate(@Valid @NotNull @RequestParam("currency") Currency currency) {
        return mainService.getFxRate(currency);
    }

    @PostMapping({"UserActivity"})
    public void storeUserActivity(@Valid @RequestBody UserActivity activity) {
        mainService.storeUserActivity(activity);
    }



}
