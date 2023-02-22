import React, { Component } from "react";
import About from "./About";
import Header from "./Header";

/**
 * 组件如何传递数据
 * 1 在组件身上添加属性 传递数据
 * 2 将数据统一管理，然后利用 扩展操作符 直接传递参数
 * 3 函数组件：在函数组件内可以接收到外部的数据，内部直接访问即可{name,age}
 * 4 类组件： 在类组件的内部存在一个props属性，外部传递的数据都放在props保存
 * 5 默认值设置: 函数组件，设置默认的参数值：组件名.defaultProps = {xx:xx}, 类组件：static defaultProps = {}
 * 6 参数类型校验: 组件名.propTypes = {xx:PropTypes.xx}
 * 7 向组件传递JSX：类组件，传递的JSX是props.children属性
 */

const obj = {
  name: "react hello",
  age: 40
};

const obj1 = {
  a: 111,
  b: 222
};

function T2() {
  return (
    <div>
      组件中的数据传递
      <Header name={"react"} age={100} />
      <Header {...obj} />
      <About a={10} b={100} />
      <About {...obj1} />
      <Header name={"test"} />
      <Header>
        <p>组件中的P标签</p>
        <span>这是span</span>
      </Header>
      <About>
        <p>这是About组件的标签</p>
      </About>
    </div>
  );
}

export default T2;
