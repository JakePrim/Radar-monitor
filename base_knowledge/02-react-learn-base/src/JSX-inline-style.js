import React, { Component, useState } from "react";
import Radium, { Style } from "radium";

/**
 * 1. 内联样式
 *  - 设置样式将键值对放到{}中
 *  - 内联样式默认无法支持伪类及媒体样式设置 radium 包可以解决这个问题,导入Radium 将组件包裹导出Radium(App)
 */

const styleObj = {
  width: 100,
  height: 100,
  backgroundColor: "red",
  ":hover": { backgroundColor: "blue" },
  "@media (max-width:1000px)": { width: 300 },
};

const buttonStyle = {
  base: {
    width: 150,
    height: 40,
    fontSize: 20,
    background: "#ffffff",
  },
  login: {
    background: "green",
    ":hover": { background: "red" },
  },
  logout: {
    background: "orange",
  },
};

function App4() {
  const [login, setLogin] = useState(false);
  return (
    <div>
      样式处理
      <p style={{ width: "100px" }}>内联样式</p>
      <div key={11} style={styleObj}>
        内联样式
      </div>
      <button
        key={1}
        style={[
          buttonStyle.base,
          login ? buttonStyle.login : buttonStyle.logout,
        ]}
        onClick={() => setLogin(!login)}
      >
        按钮
      </button>
    </div>
  );
}

export default Radium(App4);
