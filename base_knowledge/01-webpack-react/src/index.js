import React from "react";
import { render } from "react-dom";

///自定义组件
function App() {
  return <div>React Hello</div>;
}

render(<App />, document.getElementById("app"));
