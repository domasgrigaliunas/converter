import React, { Component } from "react";
import axios from "axios";
import { getCurrentDate } from "../utils";

import "./Converter.css";

class Converter extends Component {
  state = {
    result: null,
    fromCurrency: "EUR",
    toCurrency: "USD",
    amount: 1,
    currencies: [],
    currencyLabels: [],
    conversionData: [],
  };

  // Initializes the currencies with values from the api
  componentDidMount() {
    axios
      //.get("http://api.openrates.io/latest")
      .get("http://localhost:8080/api/v1/currencyLabels")
      .then((response) => {
        // Initialized with 'EUR' because the base currency is 'EUR'
        // and it is not included in the response
        const currencyAr = [];
        const currencyLabelsAr = [];

        response.data.forEach(function (entry) {
          console.log(entry);
          currencyLabelsAr.push(entry);
          currencyAr.push(entry.currency);
        });
        /*
        for (const key in response.data) {
          currencyAr.push(key);
        }
        */

        console.log(response.data);

        this.setState({
          currencies: currencyAr.sort(),
          currencyLabels: currencyLabelsAr,
        });
      })
      .catch((err) => {
        console.log("Opps", err.message);
      });
  }

  // Event handler for the conversion
  convertHandler = () => {
    console.log(this.state.fromCurrency + " " + this.state.toCurrency);

    if (this.state.fromCurrency !== this.state.toCurrency) {
      if (
        !(this.state.fromCurrency !== "EUR" && this.state.toCurrency !== "EUR")
      ) {
        axios
          .get(
            //`http://api.openrates.io/latest?base=${this.state.fromCurrency}&symbols=${this.state.toCurrency}`
            `http://localhost:8080/api/v1/User?currency=${
              this.state.fromCurrency === "EUR"
                ? this.state.toCurrency
                : this.state.fromCurrency
            }`
          )
          .then((response) => {
            if (this.state.fromCurrency === "EUR") {
              const result =
                (this.state.amount * response.data.amountB).toFixed(5) +
                " " +
                response.data.currency_b;
              //result = result.toFixed(5) + " " + response.data.currencyB;
              console.log(result);
              this.setState({ result: result });
            } else {
              const result =
                (this.state.amount / response.data.amountB).toFixed(5) +
                " " +
                response.data.currency_b;
              this.setState({ result: result });
            }
            console.log(
              getCurrentDate() +
                " " +
                this.state.amount +
                " " +
                this.state.fromCurrency +
                " " +
                this.state.toCurrency +
                " " +
                this.state.result
            );

            axios({
              method: "post",
              url: "http://localhost:8080/api/v1/UserActivity",
              headers: {},
              data: {
                dateTime: String(getCurrentDate()),
                amount: String(this.state.amount),
                currencyA: String(this.state.fromCurrency),
                currencyB: String(this.state.toCurrency),
                result: String(this.state.result),
              },
            });

            //   const result =
            //     this.state.amount * response.data.rates[this.state.toCurrency];
            //   this.setState({ result: result.toFixed(5) });
          })

          .catch((err) => {
            console.log("Opps", err.message);
          });
      } else {
        this.setState({ result: "Please select one currency as EUR" });
      }
    } else {
      this.setState({ result: "You cant convert the same currency!" });
    }

    // var params = new URLSearchParams();
    // params.append("dateTime", String(getCurrentDate()));
    // params.append("amount", String(this.state.amount));
    // params.append("currencyA", String(this.state.fromCurrency));
    // params.append("currencyB", String(this.state.toCurrency));
    // params.append("result", String(this.state.result));

    // axios
    //   .post("http://localhost:8080/api/v1/UserActivity", params)
    //   .catch((err) => {
    //     console.log("Opps", err.message);
    //   });

    // axios
    //   .post("http://localhost:8080/api/v1/UserActivity", {
    //     headers: {
    //       dateTime: String(getCurrentDate()),
    //       amount: String(this.state.amount),
    //       currencyA: String(this.state.fromCurrency),
    //       currencyB: String(this.state.toCurrency),
    //       result: String(this.state.result),
    //     },
    //   })
    //   .catch((err) => {
    //     console.log("Opps", err.message);
    //   });
  };

  // Updates the states based on the dropdown that was changed
  selectHandler = (event) => {
    if (event.target.name === "from") {
      this.setState({ fromCurrency: event.target.value });
    }
    if (event.target.name === "to") {
      this.setState({ toCurrency: event.target.value });
    }
    //console.log(this.state.fromCurrency + " " + this.state.toCurrency);
  };

  render() {
    console.log(this.state.currencyLabels);
    return (
      <div className="Converter">
        <h2>
          <span>EUR </span> Converter{" "}
          <span role="img" aria-label="money">
            &#x1f4b5;
          </span>{" "}
        </h2>
        <div className="Form">
          From
          <input
            name="amount"
            type="text"
            value={this.state.amount}
            onChange={(event) => this.setState({ amount: event.target.value })}
          />
          <select
            name="from"
            onChange={(event) => this.selectHandler(event)}
            value={this.state.fromCurrency}
          >
            {this.state.currencyLabels.map((cur) => (
              <option key={cur.currency}>{cur.currency}</option>
            ))}
          </select>
          To
          <select
            name="to"
            onChange={(event) => this.selectHandler(event)}
            value={this.state.toCurrency}
          >
            {this.state.currencies.map((cur) => (
              <option key={cur}>{cur}</option>
            ))}
          </select>
          <button onClick={this.convertHandler}>Convert</button>
        </div>
        {this.state.result && <h3>{this.state.result}</h3>}
        <div></div>
      </div>
    );
  }
}

export default Converter;
