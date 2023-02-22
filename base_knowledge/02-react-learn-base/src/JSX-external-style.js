import React from "react";
import style from "./JSX-external-style.module.css";
import styled from "styled-components";

/**
 * 外联样式设置
 * - 全局外联样式 所有组件当中都可以直接进行使用,在index.js中引入全局样式
 * - 组件级别的外联样式，只有某一个组件可以进行使用。
 *    组件名.module.css,
 *    导入组件级别的样式：import style from "./JSX-external-style.module.css";
 * - CSS in JS 的方案：styled-components
 */

// 自定义标签
const SectionDiv = styled.div.attrs({ className: "box1 box2" })`
  width: 100px;
  height: 100px;
  background-color: hotpink;
`;

function App5() {
  return (
    <div>
      <Test />
      <p className={style.item}>使用组件级别的样式</p>
      <SectionDiv>hello world</SectionDiv>
    </div>
  );
}

const Test = () => {
  return <div className={"box"}>Text组件</div>;
};

export default App5;
