import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App";
import App2 from "./JSX-event";
import App3 from "./JSX-loop";
import App4 from "./JSX-inline-style";
import reportWebVitals from "./reportWebVitals";
import { StyleRoot } from "radium";
import App5 from "./JSX-external-style";
import "./style.css"; //全局的外联样式
import T1 from "./components/T1";
import T2 from "./components/T2";
import ComponentState from "./ComponentState";
import Flow1 from "./dataflow/Flow1";
import Flow from "./dataflow/Flow";
import From from "./form/From";

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <StyleRoot>
      <From />
    </StyleRoot>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
