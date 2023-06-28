import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import App from "./App";
import reportWebVitals from "./reportWebVitals";

//引入redux
import { createStore } from "redux";
import { Provider } from "react-redux";
//引入reducer
// import CounterReducer from "./store/reducers/Counter.reducer";
import reducers from "./store/reducers";

// function reducer() {
//   return {
//     count: 0,
//   };
// }

//创建store 管理reducer
const store = createStore(reducers);

console.log(store.getState());

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <Provider store={store}>
      <App />
    </Provider>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();