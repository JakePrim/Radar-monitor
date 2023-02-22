import React from "react";
import Flow3 from "./Flow3";

/**
 * 1. 单向数据流动定义：
 *
 */
function Flow2(props) {
  return (
    <div>
      Flow2组件
      <Flow3 {...props} />
    </div>
  );
}

export default Flow2;
