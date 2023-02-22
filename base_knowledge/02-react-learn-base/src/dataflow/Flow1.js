import React from "react";
import Flow2 from "./Flow2";

function Flow1(props) {
  return (
    <>
      <h2>单向数据流动</h2>
      <p>Flow1组件</p>
      <Flow2 {...props} />
    </>
  );
}

export default Flow1;
