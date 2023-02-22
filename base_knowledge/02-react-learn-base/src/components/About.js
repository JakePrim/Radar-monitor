import React from "react";
import PropsTypes from "prop-types";

/**
 * 函数组件 获取传递的参数
 */
function About(props) {
  return (
    <div>
      这是About组件
      <p>{props.a}</p>
      <p>{props.b}</p>
      {props.children}
    </div>
  );
}

//1. 通过defaultProps
About.defaultProps = {
  name: "jake",
  age: 18
};

//2. 参数类型设置
About.propTypes = {
  name: PropsTypes.string,
  age: PropsTypes.number
};

export default About;
