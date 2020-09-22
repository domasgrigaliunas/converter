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
    conversionData: [],
  };

  // Saves user activity to DB
  sendUserActivity() {
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
  }

  // Initializes names of currencies to dropdowns
  componentDidMount() {
    axios
      .get("http://localhost:8080/api/v1/currencyLabels")
      .then((response) => {
        const currencyAr = [];
        response.data.forEach(function (entry) {
          currencyAr.push(entry.currency);
        });

        this.setState({
          currencies: currencyAr,
        });
      })
      .catch((err) => {
        console.log("Error: ", err.message);
      });
  }

  // Event handler for the conversion
  convertHandler = () => {
    if (this.state.fromCurrency !== this.state.toCurrency) {
      if (
        !(this.state.fromCurrency !== "EUR" && this.state.toCurrency !== "EUR")
      ) {
        axios
          .get(
            `http://localhost:8080/api/v1/User?currency=${
              this.state.fromCurrency === "EUR"
                ? this.state.toCurrency
                : this.state.fromCurrency
            }`
          )
          .then((response) => {
            if (response.data.last_updated !== "NA") {
              if (this.state.fromCurrency === "EUR") {
                const result =
                  (this.state.amount * response.data.amountB).toFixed(5) +
                  " " +
                  response.data.currency_b;
                this.setState({ result: result });
              } else {
                const result =
                  (this.state.amount / response.data.amountB).toFixed(5) +
                  " " +
                  response.data.currency_b;
                this.setState({ result: result });
              }
            } else {
              this.setState({ result: "There is no data for this conversion" });
            }

            this.sendUserActivity();
          })

          .catch((err) => {
            console.log("Error: ", err.message);
          });
      } else {
        this.setState({ result: "Please select one currency as EUR" });
        this.sendUserActivity();
      }
    } else {
      this.setState({ result: "You cannot convert the same currency!" });
      this.sendUserActivity();
    }
  };

  // Updates the states based on the dropdown that was changed
  selectHandler = (event) => {
    if (event.target.name === "from") {
      this.setState({ fromCurrency: event.target.value });
    }
    if (event.target.name === "to") {
      this.setState({ toCurrency: event.target.value });
    }
  };

  render() {
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
            {this.state.currencies.map((cur) => (
              <option key={cur}>{cur}</option>
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
        <div className="extraData"></div>
      </div>
    );
  }
}

export default Converter;
