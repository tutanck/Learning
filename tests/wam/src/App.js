import React, { Component } from "react";
import "./App.css";

window.d3 = require("d3");
const functionPlot = require("function-plot");

class App extends Component {
  render() {
    functionPlot({
      target: "#root",
      data: [
        {
          fn: "3x^2",
          color: 'red'
        },
        {
          fn: "3",
          color: 'blue'
        },
        {
          fn: "3",
          color: 'green'
        }
      ]
    });
    return null;
  }
}

export default App;
